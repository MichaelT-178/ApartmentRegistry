import os
#Clears the terminal screen of past text before running
os.system('clear')

print("\nCopy and paste your uml fields into the doc string thats starts at line 9")
print("Whitespace is irrelevant")

object_name = input("\nName of the class (Ex. \"User\"): ")

# Where the Transportation class variables go
uml_fields = (
    """
- userId : String  

- firstName : String  

- lastName : String  

- email : String  

- username: String 

- password: String 

- phoneNumber : String 

- recieveTexts : boolean  

- complexName : String  

- roomNumber : String  
    """
)

def create_instance_variables(a_list):

    the_var_strings = ""

    for i in range(len(a_list)):
        if i == 0:
            the_var_strings += f"\n\t/** FILL THIS IN */\n"
        else:
            the_var_strings += f"\t/** FILL THIS IN */\n"

        var = a_list[i].split()

        the_var_strings += f"\tprivate {var[2]} {var[1]};\n"

    return the_var_strings


def create_constructor(object_name, all_lists):

    construct_str = f"\n\t/**\n\t * Creates a {object_name} object\n"

    for a_list in all_lists:
        for var in a_list:
            construct_str += f"\t * @param {var.split()[1]} FILL THIS IN\n"

    construct_str += "\t */\n"
    construct_str += f"\tpublic {object_name}("

    for a_list in all_lists:
        construct_str += ''.join([f"{x.split()[2]} {x.split()[1]}, " for x in a_list])


    construct_str = construct_str.rstrip()[:-1] + f") {{\n"

    for a_list in all_lists:
        for var in a_list:
            construct_str += f"\t\tthis.{var.split()[1]} = {var.split()[1]};\n" 

    construct_str += f"\t}}\n\n"

    return construct_str

def create_getters_and_setters(a_list, lines, object_name):

    method_string = ""

    s1 = " " if (lines == 1) else "\n"
    s2 = "" if (lines == 1) else "\t"

    for var in a_list:
        split_var = var.split()

        name = split_var[1]
        data_type = split_var[2]

        #Character if on first iteration or not

        method_string += (
        f"\t/**\n"
        f"\t * Getter method for the {name} attribute\n"
        f"\t * @return The {name} attribute of the {object_name}\n"
        "\t */\n"
        f"\tpublic {data_type} get{name[0].upper() + name[1:]}() {{{s1}" 
            f"{s2}{s2}return this.{name};{s1}" 
        f"{s2}}}\n\n")

    for var in a_list:

        split_var = var.split()

        name = split_var[1]
        data_type = split_var[2]

        method_string += (
        f"\t/**\n"
        f"\t * Set the {name} attribute of the {object_name}\n"
        f"\t * @param {name} The {name} parameter to be set\n"
        "\t */\n"
        f"\tpublic void set{name[0].upper() + name[1:]}({data_type} {name}) {{{s1}" 
            f"{s2}{s2}this.{name} = {name};{s1}" 
        f"{s2}}}\n\n")

    return f"{method_string}"

transportation_list = [x.strip().replace(":", "") for x in uml_fields.split("\n") if "-" in x]


the_constructor = create_constructor(object_name, [transportation_list])

num_lines = int(input("Type '1' for single line or '2' for multiline getters and setters: "))



instance_variables = create_instance_variables(transportation_list)
the_constructor = create_constructor(object_name, [transportation_list])
get_and_set = create_getters_and_setters(transportation_list, num_lines, object_name)

def write_to_clipboard(output):
    """ This function copies the new getter and setter string to clipboard on mac"""
    import subprocess #This is a built in library
    process = subprocess.Popen('pbcopy', env={'LANG': 'en_US.UTF-8'}, stdin=subprocess.PIPE)
    process.communicate(output.encode('utf-8'))

toString = (
        "/**\n"
        f"\t * Return the string of a {object_name} object\n"
        f"\t * @return the string of a {object_name} object\n"
        "\t */\n"
        "\t@Override\n"
        "\tpublic String toString() {\n" 
            f"\t\t//FILL THIS IN;\n" 
            f"\t\treturn String.format(\"%s\", \"\");\n" 
        "\t}\n").lstrip()

toString = f"\t{toString}"

headerComment = (
f"""
/**
* @author FILL THIS IN
* @date FILL THIS IN
* @section CSC 331 - 002
* @purpose Creates a {object_name} object.
*/
 """
 ).lstrip()


# Formatted object Name
f_name = object_name[0].upper() + object_name[1:]

theStr = f"{headerComment}public class {f_name} {{\n{instance_variables}{the_constructor}{get_and_set}{toString}\n}}"
write_to_clipboard(theStr)


print(f"\nUML Diagram for the '{object_name}' class. Everything below has been copied to your clipboard!\n")
print(theStr)


print("\nTHE CLASS HAS BEEN COPIED TO YOUR CLIPBOARD!\n")