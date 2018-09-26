import random
    
def code (message, key):
    e, N = key;
    code = []
    string = ''
    ascii = stringToAscii(message)
    for i in ascii:
        code.append(expomodul(i,e,N))
        string = string + str(expomodul(i,e,N)) + " "
    return string

def decode(message, key):
    
    d, N = key
    tabcode = []
    string = '';
    tmp = message.split(' ')
    message = (int(m) for m in tmp)
    for m in message:
        tabcode.append(expomodul(m,d,N))
    string = asciiToString(tabcode)
    return string

   
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
x chiffre a elever a la puisance
y puissance
N cest le modulo
"""
def expomodul(x, y, n):
    """puissance modulaire: (x**y)%n avec x, y et n entiers"""
    result = 1
    while y>0:
        if y&1>0:
            result = (result*x)%n
        y >>= 1
        x = (x*x)%n    
    return result
    
"""
calcul inverse modulaire _ donne d
e = 3 
e = 2 ** 16
"""    
def gcd(a, b):
    while b != 0:
        a, b = b, a % b
    return a

    
'''
Euclid's extended algorithm for finding the multiplicative inverse of two numbers
'''
def multiplicative_inverse(e, phi):
    d = 0
    x1 = 0
    x2 = 1
    y1 = 1
    temp_phi = phi
    
    while e > 0:
        temp1 = temp_phi/e
        temp2 = temp_phi - temp1 * e
        temp_phi = e
        e = temp2
        
        x = x2- temp1* x1
        y = d - temp1 * y1
        
        x2 = x1
        x1 = x
        d = y1
        y1 = y
    
    if temp_phi == 1:
        return d + phi
    
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
    estPrimeP = pgp(p)
    if(estPrimeP):
        return p
    else:
        return gen_prime(n)
        
def gen_keypair(size):
    
    p = gen_prime(size)
    q = gen_prime(size)
    
    while ( p == q ):
        q = gen_prime(size)
        
    #n = pq
    n = p * q
    
    #Phi is the totient of n
    phi = (p-1) * (q-1)
    
    #Choose an integer e such that e and phi(n) are coprime
    e = random.randrange(1, phi)
    
    #Use Euclid's Algorithm to verify that e and phi(n) are comprime
    g = gcd(e, phi)
    while g != 1:
        e = random.randrange(1, phi)
        g = gcd(e, phi)
        
    #Use Extended Euclid's Algorithm to generate the private key
    d = multiplicative_inverse(e, phi)
    
    #Return public and private keypair
    #Public key is (e, n) and private key is (d, n)
    return ((e, n), (d, n))
    
#transform str to key
def strToKey(str):
    str = str.replace('(',"")
    str = str.replace(')',"")
    str = str.split(',')
    key = [int(s) for s in str]
    return key
    
     
""" ROUTINE """
def new_RSARoutine():
    print("1_ Encrypt a message !")
    print("2_ Decrypt a message !")
    choice = raw_input("What do you want to do ?")
    choice = int(choice)
    if(choice == 1):
        size = int(raw_input("Length of your key :"))
        public, private = gen_keypair(size)
        print "Your public key is ", public ," and your private key is ", private
        message = raw_input("Enter a message to encrypt with your public key: ")
        print "Your encrypted message is: ", code(message, public)
    elif(choice == 2):
        private = raw_input("What is your private key ? ex (d, N) ->")
        private = strToKey(private)
        message = raw_input("Enter a message to decrypt with your private key: ")
        print "Your encrypted message is: ", decode(message, private)
    else:
        print "It's not 1 or 2"
         
new_RSARoutine()
   
