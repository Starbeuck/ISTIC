#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Mon Nov 12 17:32:26 2018

@author: solenn
"""

import CBC, HMAC, SHA3, getopt, sys, tools, os, datetime

##################################### MESSAGE ##################################
def encryptThenMac(msg, mdp):
    deriv_items = tools.derivationKeyAndIv(mdp.encode('hex'))
    cipher = CBC.encrypt(msg.encode('hex'), deriv_items["k1"], deriv_items["iv1"])
    cipher = ''.join(cipher)
    mac = HMAC.hmac(cipher, deriv_items["k2"], deriv_items["iv2"])
    return cipher + mac

def verifyMacAndDecrypt(cipher, mdp):
    deriv_items = tools.derivationKeyAndIv(mdp.encode('hex'))
    mac = cipher[-64:]
    cipher = cipher[:-64]
    if mac == HMAC.hmac(cipher, deriv_items["k2"], deriv_items["iv2"]):
        plain = CBC.decrypt(cipher, deriv_items["k1"], deriv_items["iv1"])
        return ''.join(plain).decode("hex")
    else:
        return ""
    
################################ FILE ###################################
def encryptFile(mdp, inputfile, outputfile):
    deriv_items = tools.derivationKeyAndIv(mdp.encode('hex'))
    f = open(inputfile, "r")
    c = open(outputfile, "w")
    
    file_size = os.stat(inputfile).st_size 
    print "Input file size:", file_size / 1024, "Ko"
    start = datetime.datetime.now()
    
    key = deriv_items["k2"] + 208*'0'# padding de la clé pour atteindre 1088 bits (208=(1088-256)/4)
    k1 = tools.xorBetweenList(tools.toHexaList(key), tools.toHexaList('36'*136)) # XOR de k avec ipad (0x36...36) pour créer k1
    k1 = ''.join(format(x, 'x') for x in k1) 
    k2 = tools.xorBetweenList(tools.toHexaList(key), tools.toHexaList('5c'*136)) # XOR de k avec opad (0x5c...5c) pour créer k2
    k2 = ''.join(format(x, 'x') for x in k2)
    
    k1_iv = k1 + deriv_items["iv2"]
    res = SHA3.sha3(k1_iv, 256) # hachage de k1||iv
        
    for i in range(file_size/1000): #chiffrement par bloc de 1000 caractères
    
        msg = f.read(1000)
        cipher = CBC.encrypt(msg.encode('hex'), deriv_items["k1"], deriv_items["iv1"])
        cipher = ''.join(cipher)
        c.write(cipher)
        input_bloc = cipher + res
        res = SHA3.sha3(input_bloc, 256) # hachage des blocs suivants
    
    msg = f.read() #lecture du dernier bloc incomplet
    cipher = CBC.encrypt(msg.encode('hex'), deriv_items["k1"], deriv_items["iv1"])
    cipher = ''.join(cipher)
    c.write(cipher)
    input_bloc = cipher + res
    res = SHA3.sha3(input_bloc, 256)
    
    k2_iv = k2 + deriv_items["iv2"]
    res_k2_iv = SHA3.sha3(k2_iv, 256) # hachage de k2||iv
    
    mac = res + res_k2_iv
    res_mac = SHA3.sha3(mac, 256) #hachage final
    
    c.write(res_mac)
    
    print "Execution time:", datetime.datetime.now() - start
    
    c.close()
    f.close()
    
def verifyMacAndDecryptFile(mdp, inputfile, outputfile):
    deriv_items = tools.derivationKeyAndIv(mdp.encode('hex'))
    f = open(inputfile, "r")
    c = open(outputfile, "w")
    
    f.seek(-64,2) #lire les 64 derniers caractères
    true_mac = f.read(64)
    f.seek(0,0)
    
    file_size = os.stat(inputfile).st_size 
    print "Input file size:", file_size / 1024, "Ko"
    start = datetime.datetime.now()
    
    key = deriv_items["k2"] + 208*'0'# padding de la clé pour atteindre 1088 bits (208=(1088-256)/4)
    k1 = tools.xorBetweenList(tools.toHexaList(key), tools.toHexaList('36'*136)) # XOR de k avec ipad (0x36...36) pour créer k1
    k1 = ''.join(format(x, 'x') for x in k1) 
    k2 = tools.xorBetweenList(tools.toHexaList(key), tools.toHexaList('5c'*136)) # XOR de k avec opad (0x5c...5c) pour créer k2
    k2 = ''.join(format(x, 'x') for x in k2)
    
    k1_iv = k1 + deriv_items["iv2"]
    res = SHA3.sha3(k1_iv, 256) # hachage de k1||iv
        
    for i in range(file_size/2016): #2016 = 1000*2 + 16(padding)
        cipher = f.read(2016)
        input_bloc = cipher + res
        res = SHA3.sha3(input_bloc, 256) # hachage des blocs suivants
    
    cipher = f.read()[:-64] #lecture du dernier bloc incomplet - le mac
    input_bloc = cipher + res
    res = SHA3.sha3(input_bloc, 256)
    
    k2_iv = k2 + deriv_items["iv2"]
    res_k2_iv = SHA3.sha3(k2_iv, 256) #hachage de k2||iv
    
    mac = res + res_k2_iv
    res_mac = SHA3.sha3(mac, 256) #hachage final
    
    if true_mac == res_mac:
        f.seek(0,0)
        for i in range(file_size/2016):
            cipher = f.read(2016)
            plain = CBC.decrypt(cipher, deriv_items["k1"], deriv_items["iv1"]) 
            c.write(''.join(plain).decode("hex"))
        cipher = f.read()[:-64]
        plain = CBC.decrypt(cipher, deriv_items["k1"], deriv_items["iv1"]) 
        c.write(''.join(plain).decode("hex"))
    else:
        print "Wrong MAC..."
    print "Execution time:", datetime.datetime.now() - start
    
    c.close()
    f.close()

def usage():
    print """Usage: %s [-e | -d] -k key [-m msg|--fichier file to encrypt]
    -e: to encrypt and mac a message
    -d: to verify and decrypt a cipher
    -k: password
    -m: message to encrypt or cipher to decrypt
    --fichier: file to encrypt""" % sys.argv[0]
    
def main(argv):
    mode = ""
    key = ""
    msg = ""
    fichier = ""
    try:                                
        opts, args = getopt.getopt(argv, "hedk:m:", ["help", "encrypt", "decrypt", "key=", "message=", "fichier="])
    except getopt.GetoptError:
        usage()
        sys.exit(2)

    for opt, arg in opts:
        if opt in ("-h", "--help"):
            usage()                     
            sys.exit()                  
        elif opt in ("-e", "--encrypt"):
            mode = "encrypt"                         
        elif opt in ("-d", "--decrypt"):
            mode = "decrypt"
        elif opt in ("-k", "--key"):
            key = arg
        elif opt in ("-m", "--message"):
            msg = arg
        elif opt in ("-f", "--fichier"):
            fichier = arg
            
    if mode and key and (msg or fichier):
        if mode == "encrypt":
            if msg:
                print encryptThenMac(msg, key)
                return 0
            if fichier:
                 encryptFile(key, fichier, "encrypt.txt")
        else:
            if msg:
                print verifyMacAndDecrypt(msg, key)
                return 0
            if fichier:
                verifyMacAndDecryptFile(key, fichier, "decrypt.txt")
                return 0
    
    else:
        usage()
        sys.exit()
    
if __name__ == "__main__":
    main(sys.argv[1:])
