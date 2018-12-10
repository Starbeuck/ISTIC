#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Mon Oct  8 16:18:10 2018

@author: solenn
"""

import tools

#cst rotation
cst_rot = [[0,  36,   3,  41,  18],
                [1,  44,  10,  45,   2],
                [62,  6,  43,  15,  61],
                [28, 55,  25,  21,  56],
                [27, 20,  39,   8,  14]]

#cst tour
RC = [0x0000000000000001, 0x0000000000008082, 0x800000000000808A, 0x8000000080008000, 0x000000000000808B,
      0x0000000080000001, 0x8000000080008081, 0x8000000000008009, 0x000000000000008A, 0x0000000000000088,
      0x0000000080008009, 0x000000008000000A, 0x000000008000808B, 0x800000000000008B, 0x8000000000008089,
      0x8000000000008003, 0x8000000000008002, 0x8000000000000080, 0x000000000000800A, 0x800000008000000A,
      0x8000000080008081, 0x8000000000008080, 0x0000000080000001, 0x8000000080008008]

#Rotate of nb_rotation to the left
def rotate(value, nb_rotation):
  nb_rotation = nb_rotation % 64
  return ((value >> (64 - nb_rotation)) + (value << nb_rotation)) % (1 << 64)

#fonction tour
def roundStep(A, RC):
    #init
    B = [[0, 0, 0, 0, 0],
         [0, 0, 0, 0, 0],
         [0, 0, 0, 0, 0],
         [0, 0, 0, 0, 0],
         [0, 0, 0, 0, 0]]
    C = [0, 0, 0, 0, 0]
    D = [0, 0, 0, 0, 0]
    
    #theta
    for x in range(5):
      C[x] = A[x][0] ^ A[x][1] ^ A[x][2] ^ A[x][3] ^ A[x][4]
    for x in range(5):
      D[x] = C[(x - 1) % 5] ^ rotate(C[(x + 1) % 5], 1)
    for y in range(5):
      for x in range(5):
        A[x][y] = A[x][y] ^ D[x]

    #rho et pi
    for y in range(5):
      for x in range(5):
        B[y][(2 * x + 3 * y) % 5] = rotate(A[x][y], cst_rot[x][y])
    
    #chi
    for y in range(5):
      for x in range(5):
        A[x][y] = B[x][y] ^ ((~B[(x + 1) % 5][y]) & B[(x + 2) % 5][y])
    
    #iota
    A[0][0] = A[0][0] ^ RC

    return A

#fonction f de keccak
def keccakFct(A):
    for tour in range(24):
        A = roundStep(A, RC[tour])
    return A

def split(texte, k=48):
    #cut word into small word of 2 letters
    cut = [texte[i:i+k] for i in range(0, len(texte), k)]
    return "\n".join(cut)

#converti un bloc en matrice 5x5
def convertBlocToTable(bloc, r):
    out = [[0, 0, 0, 0, 0],
           [0, 0, 0, 0, 0],
           [0, 0, 0, 0, 0],
           [0, 0, 0, 0, 0],
           [0, 0, 0, 0, 0]]
    i=0
    for y in range(5):
        for x in range(5):
            lane = 0
            for w in range(8):
                a = bloc >> (r-64*i)-((w+1)*8) & 0xff
                lane = lane | a << 8*(w)
            out[x][y] = lane
            i += 1
            if i*64 == r:
                return out
    return out
                
#padding
def padding(msg, msg_len_bits, r):
    if msg_len_bits == 0: #empty message
        return [((0x06 << r-8) | 0x80), r]
    
    assert msg_len_bits % 8 == 0, "Not multiple of 8!!"
    rest = r - msg_len_bits % r
    if rest == 0:
        return msg
    if rest == 8:
        return [(msg << rest) | 0x86, msg_len_bits + rest]
    else:
        return [(msg << rest) | ((0x06 << rest-8) | 0x80), msg_len_bits + rest]

#fonction à appeler pour effectuer un hachage
def sha3(msg, mode):
    #init
    A = [[0, 0, 0, 0, 0],
         [0, 0, 0, 0, 0],
         [0, 0, 0, 0, 0],
         [0, 0, 0, 0, 0],
         [0, 0, 0, 0, 0]]

    if mode == 224:
        r = 1152
    elif mode == 256:
        r = 1088
    elif mode == 384:
        r = 832
    elif mode == 512:
        r = 576
    else:
        raise ValueError("SHA3 = 224, 256, 384 ou 512")
    
    #convert ASCII->hexa 
    msg_len_bits = len(msg) * 4
    if msg:
        msg = int(msg, 16)
        
    #add du padding
    msg = padding(msg, msg_len_bits, r)
    msg_len_bits = msg[1]
    msg = msg[0]

    #generate blocs
    msg = tools.generateBlocMsg(msg, msg_len_bits, r)
    
    #absorption    
    for S in msg:  
        bloc_table = convertBlocToTable(S, r)
        for x in range(5):
          for y in range(5):
            A[x][y] = A[x][y] ^ bloc_table[x][y]
        A = keccakFct(A)
    
    #print du resultat
    hache = ""
    for y in range(5):
        for x in range(5):
            l = []
            n = str(hex(A[x][y]))[2:-1]
            if len(n) != 16:
                n = '0'*(16-len(n)) + n
            for i in range(len(n)/2):
                l.append(n[i*2:i*2+2])
            l.reverse()
            hache += ''.join(l)
    
    return hache[:mode/4]