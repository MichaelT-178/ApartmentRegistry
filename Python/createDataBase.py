import os
from random import randint;os.system("clear")

def getIds():
    return [i for i in range(1, 407)]

def getFirstNames():
    first_names = []
    with open("firstnames.txt", 'r') as fn_file:
        for fn_line in fn_file:
            first_names.append(fn_line.strip())
    
    return first_names

def getLastNames():
    last_names = []
    with open("lastnames.txt", 'r') as ln_file:
        for ln_line in ln_file:
            last_names.append(ln_line.strip())
    
    return last_names

def getEmails():
    sites = ["yahoo.com", "gmail.com", "uncw.edu", "outlook.com", "icloud.com"]
    emailList = []

    for i in range(len(getFirstNames())):
        nums = f"{randint(1, 9)}{randint(1, 9)}{randint(1, 9)}{randint(1, 9)}"
        email = f"{getFirstNames()[i].lower()}{getLastNames()[i].lower()}{nums}@{sites[randint(0, 4)]}"

        if email in emailList:
            print(email)
        else:
            emailList.append(email)

    return emailList


def getUserNames():

    user_names = []

    for i in range(len(getFirstNames())):
        user_name = f"{getFirstNames()[i]}{getLastNames()[i][0]}"

        if user_name in user_names:
            print(user_name)
        else:
            user_names.append(user_name)
    
    return user_names

def getPasswords():
    alphabet = ['a','b','c','d','e','f','g','h','i','j','k','l','m',
                'n','o','p','q','r','s','t','u','v','w','x','y','z']

    specialChars = ['!', '%', '$', '*']

    passwords = []

    for i in range(406):
        pw = ""

        for _ in range(randint(4, 8)):
            randNum = randint(0, 1)
            aNum = randint(0, 25)

            if randNum:
                pw += alphabet[aNum].lower()
            else:
                pw += alphabet[aNum].upper()

        for _ in range(randint(3, 5)):
            pw += str(randint(0, 9))

        pw += specialChars[randint(0, 3)]

        if pw in passwords:
            print(f"BAD PASSWORD: {pw}")

        passwords.append(pw)
   
    return passwords

def generatePhoneNumber():

    start = f"{randint(1, 9)}{randint(0, 1)}{randint(1, 9)}"
    middle = f"{randint(1, 9)}{randint(1, 9)}{randint(1, 9)}"
    end = f"{randint(1, 9)}{randint(1, 9)}{randint(0, 3)}{randint(1, 9)}"

    number = f"{start}-{middle}-{end}"

    return number


def getPhoneNumberList():

    count = 0 
    nums = [] 

    while (count != 406):
        num = generatePhoneNumber()

        if num not in nums:
            nums.append(num)
            count += 1
        else:
            pass
    
    return nums

def getApartmentNames():
    apart_names = []

    for _ in range(49):
        apart_names.append("The Creek")
    
    for _ in range(41):
        apart_names.append("Seahawk Cove")
    
    for _ in range(59):
        apart_names.append("Quad Apartments")
    
    for _ in range(66):
        apart_names.append("The Lofts")
    
    for _ in range(73):
        apart_names.append("Campus Edge")

    for _ in range(53):
        apart_names.append("Wilmington Commons")

    for _ in range(37):
        apart_names.append("Seahawks Retreat")

    for _ in range(28):
        apart_names.append("Cypress Pointe")

    return apart_names

def getRoomNumbers():
    room_num = []

    for i in range(49):
        room_num.append(f"Room {i + 1}")
    
    for m in range(41):
        room_num.append(f"Room {m + 1}")
    
    for k in range(59):
        room_num.append(f"Room {k + 1}")
    
    for l in range(66):
        room_num.append(f"Room {l + 1}")
    
    for ce in range(73):
        room_num.append(f"Room {ce + 1}")

    for wc in range(53):
        room_num.append(f"Room {wc + 1}")

    for sr in range(37):
        room_num.append(f"Room {sr + 1}")

    for cy in range(28):
        room_num.append(f"Room {cy + 1}")

    return room_num

def getOrderHistory():
    orders = []
    tiers = ["Standard", "Deluxe", "Gold"]
    
    for _ in range(49):
        rn = randint(0, 2)
        creek_price = [1609,3994,5749]
        orders.append(f"The Creek%{tiers[rn]}%{creek_price[rn]}")
    
    for _ in range(41):
        rn1 = randint(0, 2)
        cove_price = [1206,3507,5373]
        orders.append(f"Seahawk Cove%{tiers[rn1]}%{cove_price[rn1]}")
    
    for _ in range(59):
        rn2 = randint(0, 2)
        quad_price = [1826,3310,5957]
        orders.append(f"Quad Apartments%{tiers[rn2]}%{quad_price[rn2]}")
    
    for _ in range(66):
        rn3 = randint(0, 2)
        loft_price = [1478,3139,5093]
        orders.append(f"The Lofts%{tiers[rn3]}%{loft_price[rn3]}")
    
    for _ in range(73):
        rn4 = randint(0, 2)
        edge_price = [1971,3434,5772]
        orders.append(f"Campus Edge%{tiers[rn4]}%{edge_price[rn4]}")

    for _ in range(53):
        rn5 = randint(0, 2)
        common_price = [1574,3743,5373]
        orders.append(f"Wilmington Commons%{tiers[rn5]}%{common_price[rn5]}")

    for _ in range(37):
        rn6 = randint(0, 2)
        retreat_price = [1327,3935,5388]
        orders.append(f"Seahawk Retreat%{tiers[rn6]}%{retreat_price[rn6]}")

    for _ in range(28):
        rn7 = randint(0, 2)
        pointe_price = [1140,3653,5208]
        orders.append(f"Cypress Pointe%{tiers[rn7]}%{pointe_price[rn7]}")

    return orders


the_file = "/Users/michaeltotaro/eclipse-workspace/ApartmentRegistry/src/main/UserDB.txt"

with open(the_file, 'w') as wfile:
    for i in range(406):
        a_id = getIds()[i]
        f = getFirstNames()[i]
        l = getLastNames()[i]
        em = getEmails()[i]
        un = getUserNames()[i]
        pw = getPasswords()[i]
        pn = getPhoneNumberList()[i]
        an = getApartmentNames()[i]
        rm = getRoomNumbers()[i]
        od = getOrderHistory()[i]

        print(i)
        wfile.write(f"{a_id},{f},{l},{em},{un},{pw},{pn},{an},{rm},{od}\n")