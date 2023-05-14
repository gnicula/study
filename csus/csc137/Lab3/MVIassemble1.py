import sys

def assemble_mvi_opcode(instruction):
    opcode = "11"  # MVI opcode (fixed value)
    register_select = {
        "R3": "11",
        "R2": "10",
        "R1": "01",
        "R0": "00"
    }
    
    # Split the instruction into its components
    parts = instruction.split()
    register = parts[1]
    immediate_value = parts[2]
    
    # Check if immediate value is in hexadecimal format
    if immediate_value.startswith("0x"):
        immediate_value = int(immediate_value, 16)
    else:
        immediate_value = int(immediate_value)
    
    immediate_value = immediate_value & 0xFF  # Ensure 8-bit range (-128 to 127)
    immediate_value = format(immediate_value, "08b")  # Convert to 8-bit binary string
    
    # Combine the opcode, immediate value, and register select bits
    opcode = opcode + immediate_value + register_select[register]
    
    # Convert the binary opcode to hexadecimal
    opcode_hex = format(int(opcode, 2), "03X")
    
    return opcode_hex

# Check if the input instruction is provided as a command line argument
if len(sys.argv) < 2:
    print("Usage: python mvi_assembler.py [INSTRUCTION]")
else:
    instruction = " ".join(sys.argv[1:])  # Combine command line arguments into a single string
    opcode = assemble_mvi_opcode(instruction)
    print(opcode)

