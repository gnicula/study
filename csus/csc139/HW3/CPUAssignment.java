import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

// BEGIN public class ProcInfo
public class CPUAssignment {

    public class ProcInfo {

        protected int pid;
        protected int arrivalTime;
        protected int burstTime;
        protected int priority;
        protected int runTime;
        protected int waitTime;
        protected boolean isRunning;

        public ProcInfo(int pid, int arrivalTime, int burstTime, int priority) {
            this.pid = pid;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
            runTime = 0;
            waitTime = 0;
            isRunning = false;
        }

        public ProcInfo(ProcInfo other) {
            this.pid = other.pid;
            this.arrivalTime = other.arrivalTime;
            this.burstTime = other.burstTime;
            this.priority = other.priority;
            runTime = other.runTime;
            waitTime = other.waitTime;
            isRunning = other.isRunning;
        }

    } // END public class ProcInfo

    // Constructs a ProcInfo object from a line of text.
    // One line per process with the following information:
    // Process number, Arrival Time, CPU burst time, Priority
    private ProcInfo parseToProcessInfo(String line) {
        String[] spltLine = line.split(" ");
        return new ProcInfo(Integer.parseInt(spltLine[0]), Integer.parseInt(spltLine[1]),
                Integer.parseInt(spltLine[2]), Integer.parseInt(spltLine[3]));
    }

    // Deep copies a list of processes.
    // Needed for extra work so it can run twice on the same input.
    private List<ProcInfo> cloneList(List<ProcInfo> originalList) {
        List<ProcInfo> clonedList = new ArrayList<ProcInfo>(originalList.size());
        for (ProcInfo pi : originalList) {
            clonedList.add(new ProcInfo(pi));
        }
        return clonedList;
    }
    // Common interface for Process Priority Queues.
    // Used for simplyfying benchmarking code.
    private interface ProcPriorityQueue {
        public boolean isEmpty();

        public int size();

        public ProcInfo element();

        public ProcInfo elementAt(int index);

        public void offer(ProcInfo pi);

        public ProcInfo remove();
    }

    // This is a PriorityQueue implemented with heap structure
    // maintained in an array so that children indexes are
    // [2 * parent, 2* parent +1]
    class ProcPriorityQueue_WithHeap implements ProcPriorityQueue {
        private ProcInfo[] heap;
        // Needed for different scheduling comparison rules.
        private Comparator<ProcInfo> comp;
        private int heapSize, capacity;

        public ProcPriorityQueue_WithHeap(int cap, Comparator<ProcInfo> cmp) {
            // For simplicity of parent <-> child index mapping head is at index 1.
            capacity = cap + 1;
            comp = cmp;
            heapSize = 0;
            heap = new ProcInfo[capacity];
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public int size() {
            return heapSize;
        }

        // O(1) - returns head
        public ProcInfo element() {
            if (isEmpty()) {
                System.out.println("No elements, heap empty.");
                return null;
            }
            return heap[1];
        }

        // Elements are not iterated in the comparing order.
        public ProcInfo elementAt(int index) {
            if (isEmpty()) {
                System.out.println("No elements, heap empty.");
                return null;
            }
            // TODO: Add checks for out of bounds index
            return heap[index + 1];
        }

        // Insertion: O(log(size))
        public void offer(ProcInfo pi) {
            heap[++heapSize] = pi;
            // Start from last element (the inserted one)
            int pos = heapSize;
            while (pos != 1 && comp.compare(pi, heap[pos / 2]) < 0) {
                heap[pos] = heap[pos / 2];
                pos /= 2;
            }
            heap[pos] = pi;
        }

        // Removal: O(log(size))
        public ProcInfo remove() {
            ProcInfo item, temp;
            if (isEmpty()) {
                System.out.println("No elements, heap empty.");
                return null;
            }
            // Store the head and last element
            item = heap[1];
            temp = heap[heapSize];
            --heapSize;

            // start from child of head
            int parent = 1;
            int child = 2;
            while (child <= heapSize) {
                if (child < heapSize && comp.compare(heap[child], heap[child + 1]) > 0) {
                    child++;
                }
                if (comp.compare(temp, heap[child]) <= 0) {
                    // Found insertion point in heap, break from while.
                    break;
                }
                heap[parent] = heap[child];
                parent = child;
                child *= 2;
            }
            heap[parent] = temp;

            return item;
        }
    } // END class ProcPriorityQueue_WithHeap

    // Naive PriorityQueue implemented with unsorted array
    // For extra work benchmark
    class ProcPriorityQueue_UnsortedArray implements ProcPriorityQueue {
        private ArrayList<ProcInfo> array;
        // Needed for different scheduling comparison rules.
        private Comparator<ProcInfo> comp;
        private int capacity;

        public ProcPriorityQueue_UnsortedArray(int cap, Comparator<ProcInfo> cmp) {
            capacity = cap;
            comp = cmp;
            array = new ArrayList<ProcInfo>(capacity);
        }

        public boolean isEmpty() {
            return array.isEmpty();
        }

        public int size() {
            return array.size();
        }

        // O(size), linear
        public ProcInfo element() {
            if (isEmpty()) {
                System.out.println("No elements, heap empty.");
                return null;
            }
            return Collections.min(array, comp);
        }

        // Elements are not iterated in the comparing order.
        public ProcInfo elementAt(int index) {
            if (isEmpty()) {
                System.out.println("No elements, heap empty.");
                return null;
            }
            return array.get(index);
        }

        // O(1)
        public void offer(ProcInfo pi) {
            array.add(pi);
        }

        // O(size)
        public ProcInfo remove() {
            ProcInfo pi = element();
            array.remove(pi);
            return pi;
        }
    } // END class ProcPriorityQueue_UnsortedArray

    // Comparator for RR
    class RRComparator implements Comparator<ProcInfo> {
        @Override
        public int compare(ProcInfo p1, ProcInfo p2) {
            int diffArrival = p1.arrivalTime - p2.arrivalTime;
            if (diffArrival == 0) {
                // Insert the new arrival at the end of the queue
                // before inserting the process whose time quantum expired.
                int diffRun = p1.runTime - p2.runTime;
                if (diffRun == 0) {
                    return p1.pid - p2.pid;
                }
                return diffRun;
            }
            return diffArrival;
        }
    }

    // Comparator for SJF
    class SJFComparator implements Comparator<ProcInfo> {
        @Override
        public int compare(ProcInfo p1, ProcInfo p2) {
            int diffBurst = p1.burstTime - p2.burstTime;
            if (diffBurst == 0) {
                int diffArrival = p1.arrivalTime - p2.arrivalTime;
                if (diffArrival == 0) {
                    return p1.pid - p2.pid;
                }
                return diffArrival;
            }
            return diffBurst;
        }
    }

    // Comparator for PR, works for both versions
    class PRComparator implements Comparator<ProcInfo> {
        @Override
        public int compare(ProcInfo p1, ProcInfo p2) {
            int diffPriority = p1.priority - p2.priority;
            if (diffPriority == 0) {
                return p1.pid - p2.pid;
            }
            return diffPriority;
        }
    }

    // Helper method to simplify common code between scheduling algorithms
    // Updates queued processes waiting time.
    private void updateWaitingTime(int cpuTime, int deltaTime, ProcPriorityQueue processList) {
        // Update waiting times.
        for (int i = 0; i < processList.size(); ++i) {
            ProcInfo pi = processList.elementAt(i);
            if (pi.arrivalTime < cpuTime) {
                // System.out.print(cpuTime + " add wait " + pi.pid + " " + (cpuTime -
                // pi.arrivalTime) + "\n");

                // Note: For non preemptive algos this is always deltaTime because
                // cpuTime - pi.arrivalTime = deltaTime + eligibilityTime(>=0)
                pi.waitTime += Math.min(cpuTime - pi.arrivalTime, deltaTime);
            }
        }
    }

    // Helper method that checks arrival times and updates
    // the elligible, ready to run processes queue.
    private void addArrivedProcesses(int cpuTime, List<ProcInfo> allProcesses, ProcPriorityQueue pQueue) {
        ListIterator<ProcInfo> iter = allProcesses.listIterator();
        while (iter.hasNext()) {
            ProcInfo pi = iter.next();
            if (pi.arrivalTime <= cpuTime) {
                pi.waitTime = cpuTime - pi.arrivalTime;
                pQueue.offer(pi);
                iter.remove();
            }
        }
    }

    // Helper method to compute avg waiting time of a list of finished processes.
    // This can also be done based only on the input and output.
    private float CalcAvgWaitingTime(List<ProcInfo> pInfoList) {
        int sumTime = 0;
        for (ProcInfo pi : pInfoList) {
            sumTime += pi.waitTime;
            // System.out.print("wait " + pi.pid + " " + pi.waitTime + "\n");
        }
        return (float) sumTime / pInfoList.size();
    }

    // Scheduling Algorithm Round Robin
    private void roundRobin(PrintWriter output, List<ProcInfo> pList,
            int timeQuant) {
        LinkedList<ProcInfo> finishedList = new LinkedList<ProcInfo>();
        ProcPriorityQueue processQueue = new ProcPriorityQueue_WithHeap(pList.size(),
                new RRComparator());

        // NOTE: Not sure if it's " " or "\t" between these in the testcases.
        output.println("RR " + timeQuant);
        int cpuTime = 0;

        while (pList.size() > 0 || processQueue.size() > 0) {
            // First add all eligible processes to the current queue.
            addArrivedProcesses(cpuTime, pList, processQueue);
            // Check if there's some process to execute.
            if (processQueue.isEmpty()) {
                // Simulate CPU idling with CPU fixed tick.
                ++cpuTime;
                continue;
            }
            ProcInfo currentProcess = processQueue.remove();
            currentProcess.isRunning = true;
            output.printf("%d\t%d\n", cpuTime, currentProcess.pid);

            // Compute the cpuTime increase to next event.
            int deltaTime = timeQuant;
            if (currentProcess.runTime + timeQuant > currentProcess.burstTime) {
                deltaTime = currentProcess.burstTime - currentProcess.runTime;
            }
            // Update cpuTime and current process running time
            cpuTime += deltaTime;
            currentProcess.runTime += deltaTime;
            // Update waiting times.
            updateWaitingTime(cpuTime, deltaTime, processQueue);
            // Process changes needed for event, ex. switch or finish processes.
            if (currentProcess.runTime < currentProcess.burstTime) {
                // Interrupt current process and move to back of queue.
                currentProcess.isRunning = false;
                currentProcess.arrivalTime = cpuTime;
                processQueue.offer(currentProcess);
            } else {
                // Current process finished, remove from process list.
                finishedList.addLast(currentProcess);
            }
        }
        float avgWait = CalcAvgWaitingTime(finishedList);
        output.printf("AVG Waiting Time: %.2f\n", avgWait);
        output.close();
    } // END Scheduling Algorithm Round Robin

    // Scheduling Shortest Job First
    private void shortestJobFirst(PrintWriter output,
            List<ProcInfo> pList) {
        LinkedList<ProcInfo> finishedList = new LinkedList<ProcInfo>();
        ProcPriorityQueue processList = new ProcPriorityQueue_WithHeap(pList.size(),
                new SJFComparator());

        output.println("SJF");
        int cpuTime = 0;
        while (pList.size() > 0 || processList.size() > 0) {
            // First add all eligible processes to the current queue.
            addArrivedProcesses(cpuTime, pList, processList);
            if (processList.isEmpty()) {
                // Simulate CPU idling.
                ++cpuTime;
                continue;
            }
            ProcInfo currentProcess = processList.remove();
            currentProcess.isRunning = true;
            output.printf("%d\t%d\n", cpuTime, currentProcess.pid);

            // Compute the cpuTime increase to next event.
            int deltaTime = currentProcess.burstTime;
            // Update cpuTime and current process running time
            cpuTime += deltaTime;
            currentProcess.runTime += deltaTime;
            // Update waiting times.
            updateWaitingTime(cpuTime, deltaTime, processList);
            // Process changes needed for event, ex. switch or finish processes.
            // SJC runs processes to completion so there's no switch.

            // Current process always finished, remove from process list.
            finishedList.addLast(currentProcess);
        }
        float avgWait = CalcAvgWaitingTime(finishedList);
        output.printf("AVG Waiting Time: %.2f\n", avgWait);
        output.close();
    } // END Scheduling Shortest Job First

    // Scheduling algorithm Priority Scheduling No Preemption
    private void PrioritySchedulingWithoutPreemption(PrintWriter output,
            List<ProcInfo> pList) {
        LinkedList<ProcInfo> finishedList = new LinkedList<ProcInfo>();
        ProcPriorityQueue processList = new ProcPriorityQueue_WithHeap(pList.size(),
                new PRComparator());

        output.println("PR_noPREMP");
        int cpuTime = 0;
        while (pList.size() > 0 || processList.size() > 0) {
            // First add all eligible processes to the current queue.
            addArrivedProcesses(cpuTime, pList, processList);
            if (processList.isEmpty()) {
                // Simulate CPU idling.
                ++cpuTime;
                continue;
            }
            ProcInfo currentProcess = processList.remove();
            currentProcess.isRunning = true;
            output.printf("%d\t%d\n", cpuTime, currentProcess.pid);

            // Compute the cpuTime increase to next event.
            int deltaTime = currentProcess.burstTime;
            // Update cpuTime and current process running time
            cpuTime += deltaTime;
            currentProcess.runTime += deltaTime;
            // Update waiting times.
            updateWaitingTime(cpuTime, deltaTime, processList);
            // Process changes needed for event, ex. switch or finish processes.
            // SJC runs processes to completion so there's no switch.

            // Current process always finished, remove from process list.
            finishedList.addLast(currentProcess);
        }
        float avgWait = CalcAvgWaitingTime(finishedList);
        output.printf("AVG Waiting Time: %.2f\n", avgWait);
        output.close();
    } // END Scheduling algorithm Priority Scheduling No Preemption

    // Scheduling algorithm Priority Scheduling With Preemption
    private void PrioritySchedulingWithPreemption(PrintWriter output, List<ProcInfo> pList, boolean isExtraWork) {
        LinkedList<ProcInfo> finishedList = new LinkedList<ProcInfo>();
        ProcPriorityQueue processList = new ProcPriorityQueue_WithHeap(pList.size(),
                new PRComparator());
        // For extra work add functionality to change the PriorityQueue type.
        if (isExtraWork) {
            processList = new ProcPriorityQueue_UnsortedArray(pList.size(), new PRComparator());
        }

        output.println("PR_withPREMP");
        int cpuTime = 0;
        int lastPid = -1;
        while (pList.size() > 0 || processList.size() > 0) {
            // First add all eligible processes to the current queue.
            addArrivedProcesses(cpuTime, pList, processList);
            if (processList.isEmpty()) {
                // Simulate CPU idling.
                ++cpuTime;
                continue;
            }
            ProcInfo currentProcess = processList.remove();
            currentProcess.isRunning = true;
            if (lastPid != currentProcess.pid) {
                lastPid = currentProcess.pid;
                output.printf("%d\t%d\n", cpuTime, currentProcess.pid);
            }

            // Compute the cpuTime increase to next event.
            int deltaTime = 1;
            // Update cpuTime and current process running time
            cpuTime += deltaTime;
            currentProcess.runTime += deltaTime;
            // Update waiting times.
            updateWaitingTime(cpuTime, deltaTime, processList);

            // Process changes needed for event, ex. switch or finish processes.
            if (currentProcess.runTime < currentProcess.burstTime) {
                // Interrupt current process and move to back of queue.
                currentProcess.isRunning = false;
                currentProcess.arrivalTime = cpuTime;
                processList.offer(currentProcess);
            } else {
                // Current process finished, remove from process list.
                finishedList.addLast(currentProcess);
            }
        }
        float avgWait = CalcAvgWaitingTime(finishedList);
        output.printf("AVG Waiting Time: %.2f\n", avgWait);
        output.close();
    } // END Scheduling algorithm Priority Scheduling With Preemption

    // This method is used to generate input for extra work.
    // It directly generates the process list for simplicity.
    private List<ProcInfo> GenerateTestProcessList(int numProcs) {
        List<ProcInfo> processList = new LinkedList<CPUAssignment.ProcInfo>();
        Random r = new Random(42);
        for (int i = 1; i <= numProcs; ++i) {
            int pid = i;
            int arrivalTime = r.nextInt(100);
            int burstTime = r.nextInt(30);
            int priority = r.nextInt(10);
            processList.add(new ProcInfo(pid, arrivalTime, burstTime, priority));
        }
        return processList;
    }

    public void run(PrintWriter outputWriter, String schedAlgo,
            List<ProcInfo> processList, boolean isExtraWork) {
        if (schedAlgo.contains("RR")) {
            String[] splitAlgo = schedAlgo.split(" ");
            roundRobin(outputWriter, processList, Integer.valueOf(splitAlgo[1]));
        } else if (schedAlgo.contains("SJF")) {
            shortestJobFirst(outputWriter, processList);
        } else if (schedAlgo.contains("PR_noPREMP")) {
            PrioritySchedulingWithoutPreemption(outputWriter, processList);
        // NOTE: test_cases/input8.txt contains space(s) after PR_withPREMP
        // Initially I used equalsWithCase and that failed.
        } else if (schedAlgo.contains("PR_withPREMP")) {
            PrioritySchedulingWithPreemption(outputWriter, processList, isExtraWork);
        } else {
            throw new IllegalArgumentException("Invalid scheduling algorithm name: '" + schedAlgo + "'");
        }
    }

    public static void main(String[] args) {
        try {
            List<ProcInfo> processList = new LinkedList<CPUAssignment.ProcInfo>();
            PrintWriter outputWriter = new PrintWriter("output.txt");
            CPUAssignment SingleCPU = new CPUAssignment();
            String schedAlgo = new String();
            boolean isExtraWork = false;
            int numProcs = 20000;

            // Check if we're doing Extra Work benchmarking
            if (args.length > 0 && args[0].equals("-extra")) {
                // Generate benchmark testcase and set algo to PR_withPREMP.
                processList = SingleCPU.GenerateTestProcessList(numProcs);
                schedAlgo = "PR_withPREMP";
                isExtraWork = true;
            } else {// Normal run on "./input.txt"
                // Program should read an input file named “input.txt” and write
                // the results into an output file named “output.txt”
                BufferedReader inputReader = new BufferedReader(new FileReader("input.txt"));
                // Read the name of the scheduling algo and number of processes.
                // Assume each comes on a new line.
                schedAlgo = inputReader.readLine();
                int numOfProcesses = Integer.parseInt(inputReader.readLine());
                // Populate process list from each line in the file.
                for (int i = 0; i < numOfProcesses; i++) {
                    processList.add(SingleCPU.parseToProcessInfo(inputReader.readLine()));
                }
                inputReader.close();
            }

            long startTime, elapsed;
            if (isExtraWork) {
                // Clone the process list so it can run a second time.
                List<ProcInfo> clonedProcessList = SingleCPU.cloneList(processList);
                startTime = System.nanoTime();
                SingleCPU.run(outputWriter, schedAlgo, clonedProcessList, true);
                // Nano to miliseconds
                elapsed = (Long) ((System.nanoTime() - startTime) / 1000000);
                System.out.println("Priority Queue with unsorted array: " + elapsed + "ms");
            }

            // Run and benchmark the algorithm. Always runs with Priority Queue with heap.
            startTime = System.nanoTime();
            SingleCPU.run(outputWriter, schedAlgo, processList, false);
            // Nano to miliseconds
            elapsed = (Long) ((System.nanoTime() - startTime) / 1000000);
            
            if (isExtraWork) {
                System.out.println("Priority Queue with heap time: " + elapsed + "ms");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}