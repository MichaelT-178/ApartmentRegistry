import os; os.system('clear')


def c(amount_depricated):
    return "DECREMENTED" if amount_depricated else "INCREMENTED"

f = "./eclipse-workspace/ApartmentRegistry/src/main/UserDB.txt"
#f = "./ApartmentRegistry/resources/UserDB.txt"


creek = 0
cove = 0
quad = 0
lofts = 0
edge = 0
commons = 0
retreat = 0
pointe = 0

with open(f, 'r') as file:

    for line in file:
        l = line.strip().split(",")
        name = l[9].split("%")[0]

        match name:
            case "The Creek":
                creek += 1
            case "Seahawk Cove":
                cove += 1
            case "Quad Apartments":
                quad += 1
            case "The Lofts":
                lofts += 1
            case "Campus Edge":
                edge += 1
            case "Wilmington Commons":
                commons += 1
            case "Seahawk Retreat":
                retreat += 1
            case "Cypress Pointe":
                pointe += 1
            case _ :
                print("YEET" + name)

    print("\n\nModified Values in UserDB.txt")
    if 55 - creek != 6:
        print(f"Id: 1 | CREEK | Total: 55 | Og Rooms Available: 6 | Rooms Available now: {55 - creek} | {c(55 - creek < 6)}")
    elif 45 - cove != 4:
        print(f"Id: 2 | COVE | Total: 45 | Og Rooms Available: 4 | Rooms Available now: {45 - cove} | {c(45 - cove < 4)}")
    elif 65 - quad != 6:
        print(f"Id: 3 | QUAD | Total: 65 | Og Rooms Available: 6 | Rooms Available now: {65 - quad} | {c(65 - quad < 6)} ")
    elif 70 - lofts != 4:
        print(f"Id: 4 | LOFTS | Total: 70 | Og Rooms Available: 4 | Rooms Available now: {70 - lofts} | {c(70 - lofts < 4)}")
    elif 80 - edge != 7:
        print(f"Id: 5 | EDGE | Total: 80 | Og Rooms Available: 7 | Rooms Available now: {80 - edge} | {80 - edge < 7}")
    elif 60 - commons != 7:
        print(f"Id: 6 | COMMONS | Total: 60 | Og Rooms Available: 7 | Rooms Available now: {60 - commons} | {60 - commons < 7}")
    elif 40 - retreat != 3:
        print(f"Id: 7 | RETREAT | Total: 40 | Og Rooms Available: 3 | Rooms Available now: {40 - retreat} | {40 - retreat < 3}")
    elif 30 - pointe != 2:
        print(f"Id: 8 | POINTE | Total: 30 | Og Rooms Available: 2 | Rooms Available now: {30 - pointe} | {30 - pointe < 2}")
    else:
        print("•None")


ftwo = "./eclipse-workspace/ApartmentRegistry/src/main/ApartmentData.txt"
#ftwo = "./ApartmentRegistry/resources/ApartmentData.txt"

total = 0
print("\n\nModified Values in ApartmentData.txt")
with open(ftwo, 'r') as file:
    for line in file:
        l = line.strip().split(",")

        r = int(l[7]) - int(l[8])

        if "The Creek" == l[1]:
            if int(l[8]) != 6:
                print(f"The Creek | OG: 6 |   Now: {l[8]}")
        elif "Seahawk Cove" == l[1]:
            if int(l[8]) != 4:
                print(f"Seahawk Cove    | OG: 4 |   Now: {l[8]}")
        elif "Quad Apartments" == l[1]:
            if int(l[8]) != 6:
                print(f"Quad Apartments   | OG: 6 |    Now: {l[8]}")
        elif "The Lofts" == l[1]:
            if int(l[8]) != 4:
                print(f"The Lofts       | OG: 4 |  Now: {l[8]}")
        elif "Campus Edge" == l[1]:
            if int(l[8]) != 7:
                print(f"Campus Edge     | OG: 7 |   Now: {l[8]}")
        elif "Wilmington Commons" == l[1]:
            if int(l[8]) != 7:
                print(f"Wilmington Commons | OG: 7 |  Now: {l[8]}")
        elif "Seahawk Retreat" == l[1]:
            if int(l[8]) != 3:
                print(f"Seahawk Retreat | OG: 3 |  Now: {l[8]}")
        elif "Cypress Pointe" == l[1]:
            if int(l[8]) != 2:
                print(f"Cypress Pointe  | OG: 2 |  Now: {l[8]}")
        else:
            #do nothing
            print("COol")

        total += r

print("\n\nTotal Rooms Rented in Each file")
print("UserDB.txt: ", end="")
print(creek + cove +  quad + lofts + edge + commons + retreat + pointe )
print("ApartmentData.txt: ", end="")
print(total)
            
print()
        
        

# Brad Rugg
# Markus Hawn
# Gillian Fisk
# Nick Beal
# Kirk Wayne
# Myshell Totaro
# Bruce Kent

