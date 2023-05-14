// Student Name: Gabriele Nicula
// Student ID: 219969192
// Lab 3 :: CPU Lab 2

import java.io.*;
import java.util.*;

public class miscas {

  public final int OPMASK = 0x00000FFF;

  public interface Instruction {
    public int toMachineCode();
  }

  public class ThreeOpInstruction implements Instruction {
    private final String name;
    private final int opcode;
    private final int rd;
    private final int rn;
    private final int rm;

    public ThreeOpInstruction(String name, int opcode, int rd, int rn, int rm) {
      this.name = name;
      this.opcode = opcode;
      this.rd = rd;
      this.rn = rn;
      this.rm = rm;
    }

    public int getRd() {
      return rd;
    }

    public int getRn() {
      return rn;
    }

    public int getRm() {
      return rm;
    }

    public int toMachineCode() {
      int opBits = opcode << 6;
      int rnBits = rn << 4;
      int rmBits = rm << 2;
      int rdBits = rd;
      int machineCode = opBits | rnBits | rmBits | rdBits;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return name + " r" + rd + " r" + rn + " r" + rm;
    }

  }

  public class ADD_Instruction extends ThreeOpInstruction {

    public ADD_Instruction(int rd, int rn, int rm) {
      super("add", 0x0, rd, rn, rm);
    }

  }

  public class SUB_Instruction extends ThreeOpInstruction {

    public SUB_Instruction(int rd, int rn, int rm) {
      super("sub", 0x1, rd, rn, rm);
    }

  }

  public class AND_Instruction extends ThreeOpInstruction {

    public AND_Instruction(int rd, int rn, int rm) {
      super("and", 0x2, rd, rn, rm);
    }

  }

  public class OR_Instruction extends ThreeOpInstruction {

    public OR_Instruction(int rd, int rn, int rm) {
      super("or", 0x3, rd, rn, rm);
    }

  }

  public class NOT_Instruction implements Instruction {

    private final int opcode;
    private final int rd;
    private final int rn;

    public NOT_Instruction(int rd, int rn) {
      this.rd = rd;
      this.rn = rn;
      opcode = 0x04;
    }

    public int toMachineCode() {
      int opBits = opcode << 6;
      int rnBits = rn << 4;
      int machineCode = opBits | rnBits | rd;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return "not r" + rd + " r" + rn;
    }
  }

  public class SHL_Instruction implements Instruction {

    private final int opcode;
    private final int rd;

    public SHL_Instruction(int rd) {
      this.rd = rd;
      opcode = 0x05;
    }

    public int toMachineCode() {
      int opBits = opcode << 6;
      int machineCode = opBits | rd;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return "shl r" + rd;
    }
  }

  public class ASR_Instruction implements Instruction {

    private final int opcode;
    private final int rd;

    public ASR_Instruction(int rd) {
      this.rd = rd;
      opcode = 0x06;
    }

    public int toMachineCode() {
      int opBits = opcode << 6;
      int machineCode = opBits | rd;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return "asr r" + rd;
    }
  }

  public class MOV_Instruction implements Instruction {

    private final int opcode;
    private final int rd;
    private final int rn;

    public MOV_Instruction(int rd, int rn) {
      this.rd = rd;
      this.rn = rn;
      opcode = 0x08;
    }

    public int toMachineCode() {
      int opBits = opcode << 6;
      int rnBits = rn << 4;
      int machineCode = opBits | rnBits | rd;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return "mov r" + rd + " r" + rn;
    }
  }

  public class ST_Instruction implements Instruction {

    private final int opcode;
    private final int rd;
    private final int rn;

    public ST_Instruction(int rd, int rn) {
      this.rd = rd;
      this.rn = rn;
      opcode = 0x0C;
      System.out.println("ST rn: " + rn);
    }

    public int toMachineCode() {
      int opBits = opcode << 6;
      int rnBits = rn << 4;
      int machineCode = opBits | rnBits | rd;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return "st r" + rd + " [r" + rn + "]";
    }
  }

  public class LD_Instruction implements Instruction {

    private final int opcode;
    private final int rd;
    private final int rn;

    public LD_Instruction(int rd, int rn) {
      this.rd = rd;
      this.rn = rn;
      opcode = 0x0D;
    }

    public int toMachineCode() {
      int opBits = opcode << 6;
      int rnBits = rn << 4;
      int machineCode = opBits | rnBits | rd;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return "ld r" + rd + " [r" + rn + "]";
    }
  }

  public class BNZ_Instruction implements Instruction {
    private final int opcode;
    private final int offset;

    public BNZ_Instruction(int offset) {
      this.offset = offset;
      opcode = 0x10;
    }

    @Override
    public int toMachineCode() {
      int opBits = opcode << 6;
      int offBits = offset & 0x000003FF;
      int machineCode = opBits | offBits;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return "bnz " + offset;
    }
  }

  public class BCS_Instruction implements Instruction {
    private final int opcode;
    private final int offset;

    public BCS_Instruction(int offset) {
      this.offset = offset & 0x000003FF;
      opcode = 0x20;
    }

    @Override
    public int toMachineCode() {
      int opBits = opcode << 6;
      int offBits = offset & 0x000003FF;
      int machineCode = opBits | offBits;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return "bcs " + offset;
    }
  }

  public class MVI_Instruction implements Instruction {

    private final int opcode;
    private final int rd;
    private final int value;

    public MVI_Instruction(int rd, int value) {
      this.rd = rd;
      this.value = value & 0xFF;
      opcode = 0x30;
    }

    public int toMachineCode() {
      int opBits = opcode << 6;
      int valueBits = value << 2;
      int machineCode = opBits | valueBits | rd;
      return machineCode & OPMASK;
    }

    @Override
    public String toString() {
      return "mvi r" + rd + " " + value;
    }
  }

  private class AssemblyParser {
    private final String filename;
    private final SortedMap<Integer, String> instructionStatements;
    private final ArrayList<Instruction> instructions;
    private int numParseErrors;

    public AssemblyParser(String filename) {
      this.filename = filename;
      this.instructionStatements = new TreeMap<Integer, String>();
      this.instructions = new ArrayList<Instruction>();
      numParseErrors = 0;
    }

    public ArrayList<Instruction> getInstructions() {
      return instructions;
    }

    public void parse() throws IOException {

      try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        int address = 0;
        int lineNo = 0;
        String line;
        while ((line = br.readLine()) != null) {
          ++lineNo;
          line = line.trim();
          // Ignore comments
          int semicolonIndex = line.indexOf(';');
          if (semicolonIndex != -1) {
            line = line.substring(0, semicolonIndex).trim();
          }
          // System.out.println("While Line " + line);

          // Parse instruction
          boolean isCorrectInstruction = true;
          if (!line.isEmpty()) {
            String[] tokens = line.split("\\s+");
            String opcode = tokens[0].toLowerCase();
            if (opcode.equals("add") || opcode.equals("sub")
                || opcode.equals("and") || opcode.equals("or")
                || opcode.equals("not") || opcode.equals("shl")
                || opcode.equals("asr") || opcode.equals("mov")
                || opcode.equals("st") || opcode.equals("ld")
                || opcode.equals("bnz") || opcode.equals("bcs")
                || opcode.equals("mvi")) {
              boolean checkRegCount = true;
              boolean checkRegNames = true;
              switch (opcode) {
                case "add":
                case "sub":
                case "and":
                case "or":
                  checkRegCount = tokens.length == 4;
                  if (!checkRegCount) {
                    System.err
                        .println("Invalid number of registers for instruction <"
                            + tokens[0] + "> on line <" + lineNo + ">");
                    ++numParseErrors;
                    isCorrectInstruction = false;
                  } else {
                    checkRegNames = checkRegisterNames(tokens, 3);
                    if (!checkRegNames) {
                      System.err.println(" on line <" + lineNo + ">");
                      ++numParseErrors;
                      isCorrectInstruction = false;
                    }
                  }
                  break;
                case "not":
                case "mov":
                  checkRegCount = tokens.length == 3;
                  if (!checkRegCount) {
                    System.err
                        .println("Invalid number of registers for instruction <"
                            + tokens[0] + "> on line <" + lineNo + ">");
                    ++numParseErrors;
                    isCorrectInstruction = false;
                  } else {
                    checkRegNames = checkRegisterNames(tokens, 2);
                    if (!checkRegNames) {
                      System.err.println(" on line <" + lineNo + ">");
                      ++numParseErrors;
                      isCorrectInstruction = false;
                    }
                  }
                  break;
                case "shl":
                case "asr":
                  checkRegCount = tokens.length == 2;
                  if (!checkRegCount) {
                    System.err
                        .println("Invalid number of registers for instruction <"
                            + tokens[0] + "> on line <" + lineNo + ">");
                    ++numParseErrors;
                    isCorrectInstruction = false;
                  } else {
                    checkRegNames = checkRegisterNames(tokens, 1);
                    if (!checkRegNames) {
                      System.err.println(" on line <" + lineNo + ">");
                      ++numParseErrors;
                      isCorrectInstruction = false;
                    }
                  }
                  break;
                case "st":
                case "ld":
                  checkRegCount = tokens.length == 3;
                  if (!checkRegCount) {
                    System.err
                        .println("Invalid number of registers for instruction <"
                            + tokens[0] + "> on line <" + lineNo + ">");
                    ++numParseErrors;
                    isCorrectInstruction = false;
                  } else {
                    // check and remove square brackets.
                    if (tokens[2].charAt(0) != '[' 
                      || tokens[2].charAt(tokens[2].length()-1) != ']') {
                      System.err
                        .println("Invalid address register for instruction <"
                          + tokens[0] + "> on line <" + lineNo + ">");
                      ++numParseErrors;
                      isCorrectInstruction = false;
                    } else {
                      tokens[2] = tokens[2].substring(1, tokens[2].length() - 1);
                      checkRegNames = checkRegisterNames(tokens, 1);
                      if (!checkRegNames) {
                        System.err.println(" on line <" + lineNo + ">");
                        ++numParseErrors;
                        isCorrectInstruction = false;
                      }
                    }
                  }
                  break;
                case "bnz":
                case "bcs":
                  boolean checkLength = tokens.length == 2;
                  if (!checkLength) {
                    System.err.println(
                        "Invalid argument count for bnz instruction on line <"
                            + lineNo + ">");
                    ++numParseErrors;
                    isCorrectInstruction = false;
                  }
                  break;
                case "mvi":
                  if (tokens.length != 3) {
                    System.err.println(
                        "Invalid argument count for bnz instruction on line <"
                            + lineNo + ">");
                    ++numParseErrors;
                    isCorrectInstruction = false;
                  }
                  break;
              }
              if (isCorrectInstruction) {
                line = line.replaceAll("\\[", "").replaceAll("\\]", "");
                // System.out.println("Adding line: " + line);
                instructionStatements.put(lineNo, line);
                address++;
              }
            } else {
              System.err.println("Invalid opcode: <" + opcode + "> on line <"
                  + lineNo + ">");
              ++numParseErrors;
            }
          }
        }
      }
      // System.out.println("Instruction statements size " +
      // instructionStatements.size());
      // Second pass: generate machine code
      for (Integer lineNo : instructionStatements.keySet()) {
        String line = instructionStatements.get(lineNo);
        // System.out.println(line);
        line = line.trim();
        // Parse instruction
        String[] tokens = line.split("\\s+");
        String opcode = tokens[0].toLowerCase();
        switch (opcode) {
          case "add":
            instructions.add(new ADD_Instruction(parseRegister(tokens[1]),
                parseRegister(tokens[2]), parseRegister(tokens[3])));
            break;
          case "sub":
            instructions.add(new SUB_Instruction(parseRegister(tokens[1]),
                parseRegister(tokens[2]), parseRegister(tokens[3])));
            break;
          case "and":
            instructions.add(new AND_Instruction(parseRegister(tokens[1]),
                parseRegister(tokens[2]), parseRegister(tokens[3])));
            break;
          case "or":
            instructions.add(new OR_Instruction(parseRegister(tokens[1]),
                parseRegister(tokens[2]), parseRegister(tokens[3])));
            break;
          case "not":
            instructions.add(new NOT_Instruction(parseRegister(tokens[1]),
                parseRegister(tokens[2])));
            break;
          case "shl":
            instructions.add(new SHL_Instruction(parseRegister(tokens[1])));
            break;
          case "asr":
            instructions.add(new ASR_Instruction(parseRegister(tokens[1])));
            break;
          case "mov":
            instructions.add(new MOV_Instruction(parseRegister(tokens[1]),
                parseRegister(tokens[2])));
            break;
          case "st":
            instructions.add(new ST_Instruction(parseRegister(tokens[1]),
                parseRegister(tokens[2])));
            break;
          case "ld":
            instructions.add(new LD_Instruction(parseRegister(tokens[1]),
                parseRegister(tokens[2])));
            break;
          case "bnz":
            instructions.add(new BNZ_Instruction(Integer.decode(tokens[1])));
            break;
          case "bcs":
            instructions.add(new BCS_Instruction(Integer.decode(tokens[1])));
            break;
          case "mvi":
            instructions.add(new MVI_Instruction(parseRegister(tokens[1]),
              Integer.decode(tokens[2])));
            break;
          default:
            throw new IllegalArgumentException("Invalid opcode: " + opcode);
        }
      }

      if (numParseErrors > 0) {
        System.err.println("There were " + numParseErrors + " parsing errors.");
      }
      boolean outOfBoundsError = false;
      if (instructions.size() > 64) {
        System.err.println(
            "Too many instructions: object file is larger than 64 byte " +
                "system memory.");
        outOfBoundsError = true;
      }
      // If any errors were detected, exit!
      if (numParseErrors > 0 || outOfBoundsError) {
        System.exit(1);
      }
    }

    private void writeMachineOutput(String outputFileName) {
      try {
        BufferedWriter writer = new BufferedWriter(
            new FileWriter(outputFileName));
        writer.write("v2.0 raw");
        writer.newLine();
        for (Instruction instruction : instructions) {
          writer.write(String.format("%03X", instruction.toMachineCode()));
          writer.newLine();
        }
        writer.close();
      } catch (IOException e) {
        System.err.println("Error writing to file: " + e.getMessage());
      }
    }

    private int parseRegister(String regName) {
      switch (regName.toLowerCase()) {
        case "r0":
          return 0;
        case "r1":
          return 1;
        case "r2":
          return 2;
        case "r3":
          return 3;
        default:
          return -1;
      }
    }

    private boolean checkRegisterNames(String[] instructionTokens, int numReg) {
      for (int i = 1; i < numReg + 1; ++i) {
        int regValue = parseRegister(instructionTokens[i]);
        if (regValue == -1) {
          System.err
              .print("Invalid register name <" + instructionTokens[i] + ">");
          return false;
        }
      }
      return true;
    }

  } // END AssemblyParser

  private AssemblyParser createParser(String filename) {
    return new AssemblyParser(filename);
  }

  public static void printMachineCode(ArrayList<Instruction> instructions) {
    System.err.println("*** MACHINE PROGRAM ***");
    for (int i = 0; i < instructions.size(); i++) {
      Instruction instruction = instructions.get(i);
      String address = String.format("%02X", i);
      String machineCode = String.format("%02X", instruction.toMachineCode());
      System.err
          .println(address + ":" + machineCode + "\t" + instruction.toString());
    }
  }

  public static void main(String[] args) throws IOException {

    // Check that the correct number of arguments were provided
    // NOTE: there's and extra q in the doc provided usage string.
    // USAGE: java Fiscas.javaq <source file> <object file> [-l]
    // -l : print listing to standard error
    if (args.length < 2 || args.length > 3) {
      System.out.println("USAGE: java miscas <source file> <object file> [-l]");
      System.out.println("\t-l : print listing to standard error");
      return;
    }

    // Parse the command line arguments
    String sourceFile = args[0];
    String objectFile = args[1];
    boolean printListing = false;
    if (args.length == 3 && args[2].equals("-l")) {
      printListing = true;
    }

    // Create the Fiscas object
    miscas as = new miscas();
    AssemblyParser assembler = as.createParser(sourceFile);

    // Assemble the source file
    assembler.parse();

    // Now write the object file
    assembler.writeMachineOutput(objectFile);

    // Print the symbol table if requested
    if (printListing) {
      printMachineCode(assembler.getInstructions());
    }
  }
}
