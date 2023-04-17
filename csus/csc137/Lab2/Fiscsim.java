// Student Name: Gabriele Nicula
// Student ID: 219969192
// CPU Lab 1

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Fiscsim {

  private static String versionline = "";
  private static int maxCycles = 20;
  private static boolean showDisassembly = false;
  public static final int MAX_ROM_CAPACITY = 64;

  private static class CPU {
    private byte r0;
    private byte r1;
    private byte r2;
    private byte r3;
    private int pc;
    private int cycle;
    private int z;

    public CPU() {
      r0 = 0;
      r1 = 0;
      r2 = 0;
      r3 = 0;
      pc = 0;
      cycle = 0;
      z = 0;
    }

    private int getRegister(int opcodeRegister) {
      switch (opcodeRegister) {
      case 0:
        return r0;
      case 1:
        return r1;
      case 2:
        return r2;
      case 3:
        return r3;
      default:
        System.err
            .println("Trying to read a value from an invalid register ID: <"
                + opcodeRegister + ">");
        return -1;
      }
    }

    private void setRegister(int opcodeRegister, byte toWrite) {
      switch (opcodeRegister) {
      case 0:
        r0 = toWrite;
        break;
      case 1:
        r1 = toWrite;
        break;
      case 2:
        r2 = toWrite;
        break;
      case 3:
        r3 = toWrite;
        break;
      default:
        System.err.println("Trying to set a value to an invalid register ID: <"
            + opcodeRegister + ">");
      }
    }

    public void execute(byte[] program) {
      while (cycle < maxCycles) {
        String disassembledInstruction = "Disassembly: ";
        // Fetch opcode from memory.
        byte opcode = program[pc];
        // Decode opcode
        int instr = (opcode & 0xC0) >> 6; // Extract instruction code from the
                                          // two most significant bits
        int rn = (opcode & 0x30) >> 4; // Extract register rn from bits 5-4
        int rm = (opcode & 0x0C) >> 2; // Extract register rm from bits 3-2
        int rd = opcode & 0x03; // Extract register rd from bits 1-0
        int address = opcode & 0x3F; // Mask for 6 bit BNZ address (5-0)

        int result = 0;
        // Execute instruction
        switch (instr) {
        case 0: // ADD
          result = getRegister(rn) + getRegister(rm);
          setRegister(rd, (byte) result);
          z = getRegister(rd) == 0 ? 1 : 0;
          ++pc;
          disassembledInstruction += "add " + "r" + rd + " r" + rn + " r" + rm;
          break;
        case 1: // AND
          result = getRegister(rn) & getRegister(rm);
          setRegister(rd, (byte) result);
          z = getRegister(rd) == 0 ? 1 : 0;
          ++pc;
          disassembledInstruction += "and " + "r" + rd + " r" + rn + " r" + rm;
          break;
        case 2: // NOT
          result = ~getRegister(rn) & 0xFF;
          setRegister(rd, (byte) result);
          z = getRegister(rd) == 0 ? 1 : 0;
          ++pc;
          disassembledInstruction += "not " + "r" + rd + " r" + rn;
          break;
        case 3: // BNZ
          if (z == 0) {
            pc = address;
          } else {
            ++pc;
          }
          // NOTE: It is not clear from the assignment if the address printed
          // in the disassembly must be in hex format or decimal. Ex. <bnz 7>.
          // Leaving it on not formated hex, switch by uncommenting the decimal
          // version or the formatted version if so desired.
          // Hex
          disassembledInstruction += "bnz " + String.format("%X", address);
          // disassembledInstruction += "bnz " + String.format("0x%02X", address);
          // Decimal
          // disassembledInstruction += "bnz " + address;
          break;
        default:
          System.err.printf("Unknown instruction code: %d\n", instr);
          break;
        }
        ++cycle;
        System.err.println(toString());
        if (showDisassembly) {
          System.err.println(disassembledInstruction);
          System.err.println();
        }
      }
    }

    public String toString() {
      return String.format(
          "Cycle:%d State:PC:%02X Z:%d R0: %02X R1: %02X R2: %02X R3: %02X",
          cycle, pc, z, r0, r1, r2, r3);
    }
  }

  public static void main(String[] args) {
    if (args.length < 1 || args.length > 3) {
      System.err.println(
          "USAGE:  java Fiscsim.java  <object file> [<cycles>] [-l]\n"
          + "      -d : print disassembly listing with each cycle\n"
          + "      if cycles are unspecified the CPU will run for 20 cycles");
      System.exit(1);
    }
    maxCycles = 20;
    showDisassembly = false;

    String fileName = args[0];
    String arg1 = args.length > 1 ? args[1] : null;
    if (arg1 != null) {
      if (arg1.equals("-d")) {
        showDisassembly = true;
      } else {
        maxCycles = Integer.parseInt(arg1);
      }
    }
    String arg2 = args.length > 2 ? args[2] : null;
    if (arg2 != null) {
      if (arg2.equals("-d")) {
        showDisassembly = true;
      } else {
        maxCycles = Integer.parseInt(arg2);
      }
    }

    CPU cpu = new CPU();
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      byte[] program = new byte[MAX_ROM_CAPACITY];
      String line;
      versionline = br.readLine();
      int i = 0;
      while ((line = br.readLine()) != null) {
        byte opcode = (byte) Integer.parseInt(line, 16);
        program[i] = opcode;
        ++i;
      }
      // for (int j = 0; j < i; ++j) {
      // System.out.printf("%02X\n", program[j]);
      // }
      cpu.execute(program);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
