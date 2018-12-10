#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Sat Nov 24 19:01:53 2018

@author: solenn
"""

import SHA3

#generate mask
masks = [(1 << i) - 1 for i in range(1153)]

# list1 XOR list2
def xorBetweenList(l1, l2):
    xor_list = []
    for i in range(16):
        xor_list.append(l1[i] ^ l2[i])
    return xor_list

#convert string -> list hex
def toHexaList(s):
    lst = []
    for ch in s:
        hv = int(ch, 16)
        lst.append(hv)
    return lst

#split msg -> list of blocs (size_bloc bits)
def generateBlocMsg(msg, msg_len, size_bloc):
    rtn_list = []
    nb_blocs = msg_len / size_bloc
    for i in range(nb_blocs):
        rtn_list.append(msg >> (msg_len - size_bloc*(i+1)) & masks[size_bloc])
    return rtn_list

#split msg in blocs of 8 bytes
def genBlocMsg(msg):
    blocs_list = []
    for i in range(len(msg)/16):
        blocs_list.append(msg[i*16:i*16+16])
    return blocs_list

def genBlocMsgHMAC(msg):
    nb_bloc = len(msg)/272+1
    msg_list = []
    for i in range(nb_bloc):
        msg_list.append(msg[i*272:(i+1)*272])
    return msg_list

#converti une matrice en suite d'octets en string
def convertTableToString(table):
    res = ""
    for y in range(5):
        for x in range(5):
            l = []
            n = str(hex(table[x][y]))[2:-1]
            if len(n) != 16:
                n = '0'*(16-len(n)) + n
            for i in range(len(n)/2):
                l.append(n[i*2:i*2+2])
            l.reverse()
            res += ' '.join(l) + ' '
    
    return SHA3.split(res.upper()) 

# mdp => 2 IVs + 2 clés
def derivationKeyAndIv(mdp):
    mdp += "3c7b42424521871eafdf56456abbce54db45c1c43c51df07516c67221bd46510" #add sel
    iv1 = SHA3.sha3(mdp, 256)[:16] # 64 bits pour midori
    iv2 = SHA3.sha3(iv1, 256) 
    k1 = SHA3.sha3(iv2, 256)[:32] # 128 bits pour midori
    k2 = SHA3.sha3(k1, 256)
    return {"iv1":iv1, "iv2":iv2, "k1":k1, "k2":k2}