#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Mon Nov  5 18:55:45 2018

@author: solenn
"""

import SHA3, tools
    
def hmac(msg, key, iv):
    key = key + 208*'0'# paddinf key to get 1088 bits (208=(1088-256)/4)
    k1 = tools.xorBetweenList(tools.toHexaList(key), tools.toHexaList('36'*136)) # k XOR ipad (0x36...36) = k1
    k1 = ''.join(format(x, 'x') for x in k1) 
    k2 = tools.xorBetweenList(tools.toHexaList(key), tools.toHexaList('5c'*136)) # k XOR opad (0x5c...5c) = k2
    k2 = ''.join(format(x, 'x') for x in k2)
    
    blocs_list = tools.genBlocMsgHMAC(msg) #split into blocks of 1088 bits  
    blocs_list[-1] = SHA3.padding(int(blocs_list[-1], 16), len(blocs_list[-1])*4, 1088)[0] #add padding
    blocs_list[-1] = "%0272x" % blocs_list[-1] #convert str
    
    k1_iv = k1 + iv
    res = SHA3.sha3(k1_iv, 256) # sha3 k1||iv
    
    for bloc in blocs_list:
        input_bloc = bloc + res
        res = SHA3.sha3(input_bloc, 256) #sha3 following block
    
    k2_iv = k2 + iv
    res_k2_iv = SHA3.sha3(k2_iv, 256) #sha3 k2||iv
    
    mac = res + res_k2_iv
    res_mac = SHA3.sha3(mac, 256) #ending sha3
    return res_mac