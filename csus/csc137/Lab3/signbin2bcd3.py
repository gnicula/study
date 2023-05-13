def twos_comp(val, bits):
    """Converts a two's complement value to its signed equivalent."""
    if val & (1 << (bits - 1)):
        val = val - (1 << bits)
    return val

def dec_to_bcd(val):
    """Converts a decimal value to its BCD equivalent."""
    return (val // 100) << 8 | ((val % 100) // 10) << 4 | val % 10

# Print table headers
print(f"{'i7':^4}{'i6':^4}{'i5':^4}{'i4':^4}{'i3':^4}{'i2':^4}{'i1':^4}{'i0':^4}"
      f" | {'a3':^4}{'a2':^4}{'a1':^4}{'a0':^4}"
      f"{'b3':^4}{'b2':^4}{'b1':^4}{'b0':^4}"
      f"{'c3':^4}{'c2':^4}{'c1':^4}{'c0':^4}"
      f"{'sign':^4}")

# Generate truth table
for i in range(-128, 128):
    i_bits = twos_comp(i, 8)
    sign = int(i < 0)
    bcd = dec_to_bcd(abs(i))
    a = bcd >> 8
    b = (bcd >> 4) & 0xF
    c = bcd & 0xF
    print(f"{i_bits >> 7 & 1:^4}{i_bits >> 6 & 1:^4}{i_bits >> 5 & 1:^4}{i_bits >> 4 & 1:^4}"
          f"{i_bits >> 3 & 1:^4}{i_bits >> 2 & 1:^4}{i_bits >> 1 & 1:^4}{i_bits & 1:^4}"
          f" | {a >> 3 & 1:^4}{a >> 2 & 1:^4}{a >> 1 & 1:^4}{a & 1:^4}"
          f"{b >> 3 & 1:^4}{b >> 2 & 1:^4}{b >> 1 & 1:^4}{b & 1:^4}"
          f"{c >> 3 & 1:^4}{c >> 2 & 1:^4}{c >> 1 & 1:^4}{c & 1:^4}"
          f"{sign:^4}")

