#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Mon Nov 05 16:48:35 2018

@author: solenn
"""

import Midori, tools
    
def encrypt(msg, key, iv): 
    blocs_list = tools.genBlocMsg(Midori.padding(msg))
    cipher = []
    for bloc in blocs_list:
        input_bloc = tools.xorBetweenList(tools.toHexaList(bloc), tools.toHexaList(iv))
        input_bloc = ''.join(format(x, 'x') for x in input_bloc)
        iv = Midori.encryptBloc(input_bloc, key)
        cipher.append(iv)
    return cipher

def decrypt(cipher, key, iv, rm_pad=True):
    blocs_list = tools.genBlocMsg(cipher)
    plain_list = []
    for bloc in blocs_list:
        decrypted_bloc = Midori.decryptBloc(bloc, key)
        plain_bloc = tools.xorBetweenList(tools.toHexaList(decrypted_bloc), tools.toHexaList(iv))
        iv = bloc
        plain_list.append(''.join(format(x, 'x') for x in plain_bloc))
    if rm_pad:
        return Midori.removePadding(plain_list)
    else:
        return plain_list


