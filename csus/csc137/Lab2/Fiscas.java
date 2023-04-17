import java.io.*;
import java.util.*;

public class Fiscas {

  public interface Instruction {
    public byte toMachineCode();
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

    public byte toMachineCode() {
      int opBits = opcode << 6;
      int rnBits = rn << 4;
      int rmBits = rm << 2;
      int rdBits = rd;
      int machineCode = opBits | rnBits | rmBits | rdBits;
      return (byte) machineCode;
    }

    @Override
    public String toString() {
      return name + " r" + rd + " r" + rn + " r" + rm;
    }

    public static boolean checkInstructionLength(String[] line) {
      if (line.length != 4) {
        return false;
      }
      return true;
    }
  }

  public class ADD_Instruction extends ThreeOpInstruction {

    public ADD_Instruction(int rd, int rn, int rm) {
      super("add", 0x0, rd, rn, rm);
    }

  }

  public class AND_Instruction extends ThreeOpInstruction {

    public AND_Instruction(int rd, int rn, int rm) {
      super("and", 0x1, rd, rn, rm);
    }

  }

  public class NOT_Instruction implements Instruction {

    private final int opcode;
    private final int rd;
    private final int rn;

    public NOT_Instruction(int rd, int rn) {
      this.rd = rd;
      this.rn = rn;
      opcode = 0x02;
    }

    @Override
    public byte toMachineCode() {
      int opBits = opcode << 6;
      int rnBits = rn << 4;
      int machineCode = opBits | rnBits | rd;
      return (byte) machineCode;
    }

    @Override
    public String toString() {
      return "not r" + rd + " r" + rn;
    }

    public static boolean checkInstructionLength(String[] line) {
      if (line.length != 3) {
        return false;
      }
      return true;
    }
  }

  public class BNZ_Instruction implements Instruction {
    private final int opcode;
    private final int address;
    private final String label;

    public BNZ_Instruction(int address, String label) {
      this.address = address;
      this.label = label;
      opcode = 0x03;
    }

    @Override
    public byte toMachineCode() {
      int opBits = opcode << 6;
      int machineCode = opBits | address;
      return (byte) machineCode;
    }

    @Override
    public String toString() {
      return "bnz " + label;
    }

    public static boolean checkInstructionLength(String[] line) {
      if (line.length != 2) {
        return false;
      }
      return true;
    }
  }

  private class AssemblyParser {
    private final String filename;
    private final Map<String, Integer> symbolTable;
    private final SortedMap<Integer, String> instructionStatements;
    private final ArrayList<Instruction> instructions;
    private int numParseErrors;

    public AssemblyParser(String filename) {
      this.filename = filename;
      this.symbolTable = new HashMap<>();
      this.instructionStatements = new TreeMap<Integer, String>();
      this.instructions = new ArrayList<Instruction>();
      numParseErrors = 0;
    }

    public ArrayList<Instruction> getInstructions() {
      return instructions;
    }

    public void parse() throws IOException {
      // First pass: build symbol table
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
          if (!line.isEmpty()) {
            // Check for label definition
            int colonIndex = line.indexOf(':');
            if (colonIndex != -1) {
              String label = line.substring(0, colonIndex).trim();
              if (!symbolTable.containsKey(label)) {
                symbolTable.put(label, address);
              } else {
                System.err.println("Label <" + label + "> on line <" + lineNo
                    + "> is already defined.");
              }
              // Remove label from line
              line = line.substring(colonIndex + 1).trim();
            }

            // Parse instruction
            boolean isCorrectInstruction = true;
            if (!line.isEmpty()) {
              String[] tokens = line.split("\\s+");
              String opcode = tokens[0].toLowerCase();
              if (opcode.equals("add") || opcode.equals("and")
                  || opcode.equals("not") || opcode.equals("bnz")) {
                boolean checkRegCount = true;
                boolean checkRegNames = true;
                switch (opcode) {
                case "add":
                case "and":
                  checkRegCount = ThreeOpInstruction
                      .checkInstructionLength(tokens);
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
                  checkRegCount = NOT_Instruction
                      .checkInstructionLength(tokens);
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
                case "bnz":
                  boolean checkLength = BNZ_Instruction
                      .checkInstructionLength(tokens);
                  if (!checkLength) {
                    System.err.println(
                        "Invalid argument count for bnz instruction on line <"
                            + lineNo + ">");
                    ++numParseErrors;
                    isCorrectInstruction = false;
                  }
                  break;
                }
                if (isCorrectInstruction) {
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
        case "and":
          instructions.add(new AND_Instruction(parseRegister(tokens[1]),
              parseRegister(tokens[2]), parseRegister(tokens[3])));
          break;
        case "not":
          instructions.add(new NOT_Instruction(parseRegister(tokens[1]),
              parseRegister(tokens[2])));
          break;
        case "bnz":
          String label = tokens[1];
          // System.out.println("debug label " + label);
          if (symbolTable.containsKey(label)) {
            instructions
                .add(new BNZ_Instruction(symbolTable.get(label), label));
          } else {
            System.err.println(
                "Label <" + label + "> on line <" + lineNo + "> is undefined");
            ++numParseErrors;
          }
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
      for (String lbl : symbolTable.keySet()) {
        // System.out.println("byte value " + symbolTable.get(lbl).byteValue());
        if (symbolTable.get(lbl) > 63) {
          System.err.println(
              "Invalid address <" + String.format("%02X", symbolTable.get(lbl))
                  + "> for label <" + lbl + ">.");
          outOfBoundsError = true;
        }
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
          writer.write(String.format("%02X", instruction.toMachineCode()));
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

    public Map<String, Integer> getSymbolTable() {
      return symbolTable;
    }
  } // END AssemblyParser

  private AssemblyParser createParser(String filename) {
    return new AssemblyParser(filename);
  }

  private static void printSymbolTable(Map<String, Integer> symbolTable) {
    System.err.println("*** LABEL LIST ***");
    List<Map.Entry<String, Integer>> entries = new ArrayList<>(
        symbolTable.entrySet());
    Collections.sort(entries, Map.Entry.comparingByValue());
    for (Map.Entry<String, Integer> entry : entries) {
      System.err.printf("%-8s%02X%n", entry.getKey(), entry.getValue());
    }
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
      System.out.println("USAGE: java Fiscas <source file> <object file> [-l]");
      System.out.println("\t-l : print listing to standard error");
      return;
    }

    // Parse the command line arguments
    String sourceFile = args[0];
    String objectFile = args[1];
    boolean printSymbolTable = false;
    if (args.length == 3 && args[2].equals("-l")) {
      printSymbolTable = true;
    }

    // Create the Fiscas object
    Fiscas as = new Fiscas();
    AssemblyParser assembler = as.createParser(sourceFile);

    // Assemble the source file
    assembler.parse();

    // Now write the object file
    assembler.writeMachineOutput(objectFile);

    // Print the symbol table if requested
    if (printSymbolTable) {
      Map<String, Integer> symbolTable = assembler.getSymbolTable();
      printSymbolTable(symbolTable);
      printMachineCode(assembler.getInstructions());
    }
  }
}
