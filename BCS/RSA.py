import random
    
def code (message, e, N):
    return

def decode(message, d, N):
    return

"""convert string to ascii """
def stringToAscii(message):
    ascii = [];
    for m in message:   
        tmp = ord(m)
        ascii.append(tmp)
    return ascii
        
""" convert ascii to string """
def asciiToString(tableau):
    string = '';
    for i in tableau:
        tmp = chr(i)
        string += tmp
    return string
    
  
"""
x  chiffre a elever a la puisance
n puissance
m cest le modulo
"""
def expomodul(a, b, N):
    res = 1;
    while (b>0):
        if(b%2 == 1):
            res = (res * a)%N
        b = b/2;
        a = (a*a)%N
    return res

"""
calcul inverse modulaire
e = 3 
e = 2 ** 16
"""    
def egcd(a, b):
    if a == 0:
        return (b, 0, 1)
    else:
        g, y, x = egcd(b % a, a)
        return (g, x - (b // a) * y, y)

def modinv(a, m):
    g, x, y = egcd(a, m)
    if g != 1:
        raise Exception('modular inverse does not exist')
    else:
        return x % m
        
def expo_modulaire(e, p, q):
    res = (p-1)*(q-1)
    return modinv(e, res)
    
"""
regarde si x est un nombre premier
"""
def pgp(x):
    if(expomodul(2,x-1,x) == expomodul(3,x-1,x) == expomodul(5,x-1,x) == expomodul(7,x-1,x) == 1):
        return True
    else:
        return False

"""
genere p ou q
"""
def gen_prime(n):
    p = random.randint(2**(n-1),2**n-1)
    if(p%2 == 0):
        p = p+1
    estPrime = pgp(p)
    if(estPrime):
        return p
    else:
        return gen_prime(n)
    
