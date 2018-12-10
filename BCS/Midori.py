#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Mon Nov  5 17:25:30 2018

@author: solenn
"""

import tools

sb0 = [0xc, 0xa, 0xd, 0x3, 0xe, 0xb, 0xf, 0x7, 0x8, 0x9, 0x1, 0x5, 0x0, 0x2, 0x4, 0x6]

alpha = [[0,0,0,1,0,1,0,1,1,0,1,1,0,0,1,1],
          [0,1,1,1,1,0,0,0,1,1,0,0,0,0,0,0],
          [1,0,1,0,0,1,0,0,0,0,1,1,0,1,0,1],
          [0,1,1,0,0,0,1,0,0,0,0,1,0,0,1,1],
          [0,0,0,1,0,0,0,0,0,1,0,0,1,1,1,1],
          [1,1,0,1,0,0,0,1,0,1,1,1,0,0,0,0],
          [0,0,0,0,0,0,1,0,0,1,1,0,0,1,1,0],
          [0,0,0,0,1,0,1,1,1,1,0,0,1,1,0,0],
          [1,0,0,1,0,1,0,0,1,0,0,0,0,0,0,1],
          [0,1,0,0,0,0,0,0,1,0,1,1,1,0,0,0],
          [0,1,1,1,0,0,0,1,1,0,0,1,0,1,1,1],
          [0,0,1,0,0,0,1,0,1,0,0,0,1,1,1,0],
          [0,1,0,1,0,0,0,1,0,0,1,1,0,0,0,0],
          [1,1,1,1,1,0,0,0,1,1,0,0,1,0,1,0],
          [1,1,0,1,1,1,1,1,1,0,0,1,0,0,0,0]]

def subCell(state):
    sub_state = []
    for i in range(len(state)):
        sub_state.append(sb0[state[i]])
    return sub_state

def shuffleCell(state):
    return [state[0],state[10], state[5], state[15], state[14], state[4], state[11], state[1],
            state[9], state[3], state[12], state[6], state[7], state[13], state[2], state[8]]

def invShuffleCell(state):
    return [state[0], state[7], state[14], state[9], state[5], state[2], state[11], state[12],
            state[15], state[8], state[1], state[6], state[10], state[13], state[4], state[3]]
    
def mixColumn(state):
    mix_state = []
    i= 0
    while i < 16:
        mix_state.append(state[i+1] ^ state[i+2] ^ state[i+3])
        mix_state.append(state[i] ^ state[i+2] ^ state[i+3])
        mix_state.append(state[i] ^ state[i+1] ^ state[i+3])
        mix_state.append(state[i] ^ state[i+1] ^ state[i+2])
        i += 4
    return mix_state

#padding (PKCS#7)
def padding(msg):
    if len(msg) %2 != 0:
       raise ValueError("Size should be multiple of 2.")
    if not msg: #empty message
        return "08"*8
    elif not len(msg) % 16: #if size_msg % taille bloc (16) add '0808080808080808'
        return msg + "08"*8
    elif len(msg) % 16 == 14: #if only one byte to pad adding "0101010101010101"
        return msg + "01"*9
    else: #padding
        len_pad = 16 - len(msg) % 16
        pad = '%02X' % (len_pad/2)
        return msg + pad*(len_pad/2)

#encrypt with key
def encryptBloc(msg, key):
    msg = tools.toHexaList(msg)
    key = tools.toHexaList(key)
    k0 = key[:16]
    k1 = key[16:]        
    wk = tools.xorBetweenList(k0,k1)    
    state =  tools.xorBetweenList(msg, wk)   
    for tour in range(15):
        state = mixColumn(shuffleCell(subCell(state)))
        if tour%2:
            rk = tools.xorBetweenList(alpha[tour], k1)
        else:
            rk = tools.xorBetweenList(alpha[tour], k0) 
        state = tools.xorBetweenList(state, rk)
    state = tools.xorBetweenList(subCell(state), wk)  
    return ''.join(format(x, 'x') for x in state)

#decrypt with key
def decryptBloc(cipher, key):
    ciph = tools.toHexaList(cipher)
    key = tools.toHexaList(key)
    k0 = key[:16]
    k1 = key[16:]     
    wk = tools.xorBetweenList(k0,k1)  
    state =  tools.xorBetweenList(ciph, wk)
    
    tour = 14
    while tour >= 0:
        state = invShuffleCell(mixColumn(subCell(state)))
        if tour%2:
            rk = tools.xorBetweenList(alpha[tour], k1)
        else:
            rk = tools.xorBetweenList(alpha[tour], k0) 
        state = tools.xorBetweenList(state, invShuffleCell(mixColumn(rk)))
        tour -= 1
    
    state = subCell(state)
    state = tools.xorBetweenList(state, wk)
    
    return ''.join(format(x, 'x') for x in state)

#encrypt to msg
def encrypt(msg, key):
    blocs_list = tools.gen_bloc_msg(padding(msg))
    encrypted_blocs = []
    for bloc in blocs_list:
        encrypted_blocs.append(encryptBloc(bloc, key))
    return encrypted_blocs

#decrypt of msg
def decrypt(cipher, key):
    plain_list = []
    for bloc in cipher:
        plain_list.append(decryptBloc(bloc, key))
    return plain_list

#remove padding
def removePadding(plain_list):
    if plain_list[-1][-2:] != plain_list[-1][-4:-2]:
        raise ValueError("Padding Error")
    else:
        if plain_list[-1][-2:] == '01':
            plain_list[-2] = plain_list[-2][:-2]
            return ''.join(plain_list[:-1])
        plain_list[-1] = plain_list[-1][:-2*int(plain_list[-1][-2:])]
        return ''.join(plain_list)