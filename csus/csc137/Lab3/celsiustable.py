def fahrenheit_to_celsius(fahrenheit):
    celsius = (fahrenheit - 32) * 5 / 9
    return round(celsius)

def signed_8bit_hex(value):
    if value >= 0:
        hex_value = hex(value)[2:].zfill(2).upper()
    else:
        hex_value = hex(256 + value)[2:].zfill(2).upper()
    return hex_value

print("Fahrenheit\tCelsius")
print("======================")
for fahrenheit in range(0, 128):
    celsius = fahrenheit_to_celsius(fahrenheit)
    fahrenheit_hex = signed_8bit_hex(fahrenheit)
    celsius_hex = signed_8bit_hex(celsius)
    print(f"{fahrenheit_hex}\t\t{celsius_hex}")

for fahrenheit in range(-128, 0):
    celsius = fahrenheit_to_celsius(fahrenheit)
    fahrenheit_hex = signed_8bit_hex(fahrenheit)
    celsius_hex = signed_8bit_hex(celsius)
    print(f"{fahrenheit_hex}\t\t{celsius_hex}")

