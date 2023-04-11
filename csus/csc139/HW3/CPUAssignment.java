import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class CPUAssignment {

    public class ProcessInfo {

        protected int pid;
        protected int arrivalTime;
        protected int burstTime;
        protected int priority;
        protected int runTime;
        protected int waitTime;
        protected boolean isRunning;

        public ProcessInfo(int pid, int arrivalTime, int burstTime, int priority) {
            this.pid = pid;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
            runTime = 0;
            waitTime = 0;
            isRunning = false;
        }
    }

    class RRProcessComparator implements Comparator<ProcessInfo> {
        @Override
        public int compare(ProcessInfo p1, ProcessInfo p2) {
            int diffArrival = p1.arrivalTime - p2.arrivalTime;
            if (diffArrival == 0) {
                int diffRun = p1.runTime - p2.runTime;
                if (diffRun == 0) {
                    return p1.pid - p2.pid;
                }
                return diffRun;
            }
            return diffArrival;
        }
    }

    class ProcessShortestJobComparator implements Comparator<ProcessInfo> {
        @Override
        public int compare(ProcessInfo o1, ProcessInfo o2) {
            return (o1.burstTime - o1.runTime) - (o2.burstTime - o2.runTime);
        }
    }

    private void roundRobin(PrintWriter output, LinkedList<ProcessInfo> pList,
            int timeQuantum) {
        LinkedList<ProcessInfo> finishedList = new LinkedList<ProcessInfo>();
        PriorityQueue<ProcessInfo> processList = new PriorityQueue<ProcessInfo>(pList.size(), new RRProcessComparator());
        processList.addAll(pList);

        output.println("RR " + timeQuantum);
        int cpuTime = 0;
        while (processList.size() > 0) {
            if (cpuTime < processList.element().arrivalTime) {
                // CPU idling
                ++cpuTime;
                continue;
            }
            ProcessInfo currentProcess = processList.remove();
            currentProcess.isRunning = true;
            output.printf("%d\t%d\n", cpuTime, currentProcess.pid);

            // Compute the cpuTime increase to next event.
            int deltaTime = timeQuantum;
            if (currentProcess.runTime + timeQuantum > currentProcess.burstTime) {
                deltaTime = currentProcess.burstTime - currentProcess.runTime;
            }
            // Update cpuTime and current process running time
            cpuTime += deltaTime;
            currentProcess.runTime += deltaTime;
            // Update waiting times.
            for (ProcessInfo pi: processList) {
                pi.waitTime += deltaTime;
            }

            // Process changes needed for event, ex. switch or finish processes.
            if (currentProcess.runTime < currentProcess.burstTime) {
                // Interrupt current process and move to back of queue.
                currentProcess.isRunning = false;
                currentProcess.arrivalTime = cpuTime;
                processList.offer(currentProcess);
            } else {
                // current process finished, remove from process list.
                finishedList.addLast(currentProcess);
            }
        }
        float avgWait = CalcAvgWaitingTime(finishedList);
        output.printf("AVG Waiting Time: %.2f\n", avgWait);
        output.close();
    }

    private void shortestJobFirst(PrintWriter outputwriter,
            LinkedList<ProcessInfo> processList) {
        int cpuTime = 0;
        int processDoneCounter = 0;
        System.out.println("SJF");
        processList.sort(new RRProcessComparator());
        LinkedList<ProcessInfo> activeProcesses = new LinkedList<ProcessInfo>();

        while (processDoneCounter < processList.size()) {
            if (cpuTime == processList.getFirst().arrivalTime) {
                activeProcesses.add(processList.getFirst());
                processList.removeFirst();
                activeProcesses.sort(new ProcessShortestJobComparator());
            } else {
                cpuTime++;
                processList.getFirst();
            }
        }
    }

    // One line per process with the following information:
    // Process number, Arrival Time, CPU burst time, Priority
    private ProcessInfo parseToProcessInfo(String line) {
        String[] splitLine = line.split(" ");
        return new ProcessInfo(Integer.parseInt(splitLine[0]), Integer.parseInt(splitLine[1]),
                Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]));
    }

    private float CalcAvgWaitingTime(LinkedList<ProcessInfo> pInfoList) {
        int sumTime=0;
        for(ProcessInfo pi: pInfoList) {
            sumTime += pi.waitTime;
            System.out.print("wait " + pi.pid + " " + pi.waitTime + "\n");
        }
        return (float)sumTime / pInfoList.size();
    }

    public void run(BufferedReader inputReader, PrintWriter outputWriter, String schedAlgo,
            LinkedList<ProcessInfo> processList) {
        if (schedAlgo.contains("RR")) {
            String[] splitAlgo = schedAlgo.split(" ");
            roundRobin(outputWriter, processList, Integer.valueOf(splitAlgo[1]));
        } else if (schedAlgo.equalsIgnoreCase("SJF")) {
            shortestJobFirst(outputWriter, processList);
        } else if (schedAlgo.equalsIgnoreCase("PR_noPREMP")) {

        } else if (schedAlgo.equalsIgnoreCase("PR_withPREMP")) {

        }
    }

    public static void main(String[] args) {
        try {
            // Program should read an input file named “input.txt” and write
            // the results into an output file named “output.txt”
            File inputFile = new File("test_cases/input10.txt");
            BufferedReader inputReader = new BufferedReader(new FileReader(inputFile));
            PrintWriter outputWriter = new PrintWriter("output.txt");

            // Read the name of the scheduling algo
            String schedAlgo = inputReader.readLine();
            int numOfProcesses = Integer.parseInt(inputReader.readLine());
            LinkedList<ProcessInfo> processList = new LinkedList<CPUAssignment.ProcessInfo>();

            CPUAssignment programm = new CPUAssignment();

            for (int i = 0; i < numOfProcesses; i++) {
                processList.add(programm.parseToProcessInfo(inputReader.readLine()));
            }
            programm.run(inputReader, outputWriter, schedAlgo, processList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}