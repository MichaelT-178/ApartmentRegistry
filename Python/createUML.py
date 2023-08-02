import os
#Clears the terminal screen of past text before running
os.system('clear')


object_name = input("Name of the object (Ex. \"User\"): ")

# Where the Transportation class variables go
# NOTE THE USE OF SPACES. - SPACE name SPACE : SPACE type
uml_fields = (
    """
- phoneNumber: String 
- moveInDate: String 
- moveOutDate: String 
- numOfRooms: Int
- email: String 
- apartmentComplex: String 
    """
)

def create_constructor(object_name, all_lists):
    constructor_str = f"<<constructor>> {object_name}("

    for a_list in all_lists:
        constructor_str += ''.join([f"{x.split()[1]} : {x.split()[2]}, " for x in a_list])

    return constructor_str.strip()[:-1] + ")\n"

def create_uml_getters_and_setters(a_list):
    theString = ""

    for var in a_list:

        name = var.split()[1]
        
        theString += f"+ get{name[0].upper() + name[1:]}() : {var.split()[2]}\n"

    for var in a_list:
        name = var.split()[1]
    
        theString += f"+ set{name[0].upper() + name[1:]}({name} : {var.split()[2]})\n"

    return f"{theString}+ toString() : Sting\n"

transportation_list = [x.strip().replace(":", "") for x in uml_fields.split("\n") if "-" in x]

print("\nWhat class do you want to create a UML Diagram for?")


the_constructor = create_constructor(object_name, [transportation_list])
get_and_set = create_uml_getters_and_setters(transportation_list)


def write_to_clipboard(output):
    """ This function copies the new getter and setter string to clipboard on mac"""
    import subprocess #This is a built in library
    process = subprocess.Popen('pbcopy', env={'LANG': 'en_US.UTF-8'}, stdin=subprocess.PIPE)
    process.communicate(output.encode('utf-8'))



theStr = f"{the_constructor}{get_and_set}"
write_to_clipboard(theStr)

print(f"\nUML Diagram for the '{object_name}' class. Everything below has been copied to your clipboard!\n")
print(theStr)
