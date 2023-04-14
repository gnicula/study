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
            return name + " R" + rd + " R" + rm + " R" + rn;
        }

        // public static ADDInstruction fromAssembly(String line) {
        //     String[] tokens = line.toUpperCase().split("\\s+");
        //     if (tokens.length != 4 || !tokens[0].equals("ADD")) {
        //         throw new IllegalArgumentException("Invalid ADD instruction: " + line);
        //     }
        //     int rd = parseRegister(tokens[1]);
        //     int rn = parseRegister(tokens[2]);
        //     int rm = parseRegister(tokens[3]);
        //     return new ADDInstruction(rd, rn, rm);
        // }        
    }

    public class ADD_Instruction extends ThreeOpInstruction {
    
        public ADD_Instruction(int rd, int rn, int rm) {
            super("ADD", 0x0, rd, rn, rm);
        }
            
    }

    public class AND_Instruction extends ThreeOpInstruction {
    
        public AND_Instruction(int rd, int rn, int rm) {
            super("AND", 0x1, rd, rn, rm);
        }
            
    }

    private class AssemblyParser {
        private final String filename;
        private final Map<String, Integer> symbolTable;
        private final ArrayList<String> instructionStatements;
        private final ArrayList<Instruction> instructions;
    

        
        public AssemblyParser(String filename) {
            this.filename = filename;
            this.symbolTable = new HashMap<>();
            this.instructionStatements = new ArrayList<String>();
            this.instructions = new ArrayList<Instruction>();
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
                    if (!line.isEmpty()) {
                        // Check for label definition
                        int colonIndex = line.indexOf(':');
                        if (colonIndex != -1) {
                            String label = line.substring(0, colonIndex).trim();
                            symbolTable.put(label, address);
                            // Remove label from line
                            line = line.substring(colonIndex + 1).trim();
                        }
    
                        // Ignore comments
                        int semicolonIndex = line.indexOf(';');
                        if (semicolonIndex != -1) {
                            line = line.substring(0, semicolonIndex).trim();
                        }
    
                        // Parse instruction
                        if (!line.isEmpty()) {
                            String[] tokens = line.split("\\s+");
                            String opcode = tokens[0].toUpperCase();
                            if (opcode.equals("ADD") || opcode.equals("AND") || opcode.equals("NOT") || opcode.equals("BNZ")) {
                                // TODO: switch and check syntax
                                instructionStatements.add(line);
                                address++;
                            } else {
                                throw new IllegalArgumentException("Invalid opcode: " + opcode + " on line " + lineNo); 
                            }
                        }
                    }
                }
            }
    
            // Second pass: generate machine code
            for (String line: instructionStatements) {
                System.out.println(line);
                line = line.trim();
                // Parse instruction
                String[] tokens = line.split("\\s+");
                String opcode = tokens[0].toUpperCase();
                switch (opcode) {
                    case "ADD":
                        instructions.add(new ADD_Instruction(
                            parseRegister(tokens[1]),
                            parseRegister(tokens[2]),
                            parseRegister(tokens[3])));
                        break;
                    case "AND":
                        instructions.add(new AND_Instruction(
                            parseRegister(tokens[1]),
                            parseRegister(tokens[2]),
                            parseRegister(tokens[3])));
                    break;
                    case "NOT":
                        // ...
                        break;
                    case "BNZ":
                        // ...
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid opcode: " + opcode);
                }
            }
        }
    
        private int parseRegister(String regName) {
            switch (regName.toUpperCase()) {
                case "R0":
                    return 0;
                case "R1":
                    return 1;
                case "R2":
                    return 2;
                case "R3":
                    return 3;
                default:
                    throw new IllegalArgumentException("Invalid register name: " +
                    regName);
            }
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
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(symbolTable.entrySet());
        Collections.sort(entries, Map.Entry.comparingByValue());
        for (Map.Entry<String, Integer> entry : entries) {
            System.err.printf("%-8s%02X%n", entry.getKey(), entry.getValue());
        }
    }

    public static void printMachineCode(ArrayList<Instruction> instructions) {
        System.out.println("*** MACHINE PROGRAM ***");
        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);
            String address = String.format("%02X", i);
            String machineCode = String.format("%02X", instruction.toMachineCode());
            System.err.println(address + ":" + machineCode + "\t" + instruction.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        
        // Check that the correct number of arguments were provided
        // NOTE: there's and extra q in the doc provided usage string.
        // USAGE:  java Fiscas.javaq <source file> <object file> [-l]
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
        Fiscas fiscas = new Fiscas();
        AssemblyParser assembler = fiscas.createParser(sourceFile);
        
        // Assemble the source file
        assembler.parse();
        
        // Print the symbol table if requested
        if (printSymbolTable) {
            Map<String, Integer> symbolTable = assembler.getSymbolTable();
            printSymbolTable(symbolTable);
            printMachineCode(assembler.getInstructions());
        }
    }
}
