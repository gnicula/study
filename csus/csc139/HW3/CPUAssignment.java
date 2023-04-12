import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ListIterator;
// For extra work benchmarking
import java.util.PriorityQueue;

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
    }

    // This is a PQ implemented with heap structure maintained in an array
    // parent -> [2 * parent, 2* parent +1]
    class ProcPriorityQueue_WithHeap {
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

        public void clear() {
            heap = new ProcInfo[capacity];
            heapSize = 0;
        }

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

        public ProcInfo remove() {
            int parent, child;
            ProcInfo item, temp;
            if (isEmpty()) {
                System.out.println("Heap is empty");
                return null;
            }
            // Store the head and last element
            item = heap[1];
            temp = heap[heapSize];
            --heapSize;

            // start from child of head
            parent = 1;
            child = 2;
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
    }

    class RRProcessComparator implements Comparator<ProcInfo> {
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

    class SJFProcessComparator implements Comparator<ProcInfo> {
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

    class PR_noPREMPProcessComparator implements Comparator<ProcInfo> {
        @Override
        public int compare(ProcInfo p1, ProcInfo p2) {
            int diffPriority = p1.priority - p2.priority;
            if (diffPriority == 0) {
                return p1.pid - p2.pid;
            }
            return diffPriority;
        }
    }

    private void roundRobin(PrintWriter output, LinkedList<ProcInfo> pList,
            int timeQuant) {
        LinkedList<ProcInfo> finishedList = new LinkedList<ProcInfo>();
        // PriorityQueue<ProcInfo> processList = new
        // PriorityQueue<ProcInfo>(pList.size(),
        // new RRProcessComparator());
        ProcPriorityQueue_WithHeap processList = new ProcPriorityQueue_WithHeap(pList.size(),
                new RRProcessComparator());
        for (ProcInfo pi : pList) {
            processList.offer(pi);
        }

        // NOTE: Not sure if it's " " or "\t" between these in the testcases.
        output.println("RR " + timeQuant);
        int cpuTime = 0;

        while (processList.size() > 0) {
            if (cpuTime < processList.element().arrivalTime) {
                // Simulate CPU idling with CPU fixed tick.
                ++cpuTime;
                continue;
            }
            ProcInfo currentProcess = processList.remove();
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
            for (int i = 0; i < processList.size(); ++i) {
                ProcInfo pi = processList.elementAt(i);
                if (pi.arrivalTime < cpuTime) {
                    // System.out.print(cpuTime + " add wait " + pi.pid + " " + (cpuTime -
                    // pi.arrivalTime) + "\n");
                    pi.waitTime += Math.min(cpuTime - pi.arrivalTime, deltaTime);
                }
            }
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
    }

    private void shortestJobFirst(PrintWriter output,
            LinkedList<ProcInfo> pList) {
        LinkedList<ProcInfo> finishedList = new LinkedList<ProcInfo>();
        // PriorityQueue<ProcInfo> processList = new
        // PriorityQueue<ProcInfo>(pList.size(),
        // new SJFProcessComparator());
        ProcPriorityQueue_WithHeap processList = new ProcPriorityQueue_WithHeap(pList.size(),
                new SJFProcessComparator());

        output.println("SJF");
        int cpuTime = 0;
        while (pList.size() > 0 || processList.size() > 0) {
            // First add all eligible processes to the current queue.
            ListIterator<ProcInfo> iter = pList.listIterator();
            while (iter.hasNext()) {
                ProcInfo pi = iter.next();
                if (pi.arrivalTime <= cpuTime) {
                    pi.waitTime = cpuTime - pi.arrivalTime;
                    processList.offer(pi);
                    iter.remove();
                }
            }
            if (processList.size() == 0) {
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
            for (int i = 0; i < processList.size(); ++i) {
                ProcInfo pi = processList.elementAt(i);
                if (pi.arrivalTime < cpuTime) {
                    // System.out.print(cpuTime + " add wait " + pi.pid + " " + (cpuTime -
                    // pi.arrivalTime) + "\n");
                    // Note: For SJC this is always deltaTime because
                    // cpuTime - pi.arrivalTime = deltaTime + eligibilityTime(>=0)
                    pi.waitTime += Math.min(cpuTime - pi.arrivalTime, deltaTime);
                }
            }
            // Process changes needed for event, ex. switch or finish processes.
            // SJC runs processes to completion so there's no switch.

            // Current process always finished, remove from process list.
            finishedList.addLast(currentProcess);
        }
        float avgWait = CalcAvgWaitingTime(finishedList);
        output.printf("AVG Waiting Time: %.2f\n", avgWait);
        output.close();
    }

    private void PrioritySchedulingWithoutPreemption(PrintWriter output,
            LinkedList<ProcInfo> pList) {
        LinkedList<ProcInfo> finishedList = new LinkedList<ProcInfo>();
        // PriorityQueue<ProcInfo> processList = new
        // PriorityQueue<ProcInfo>(pList.size(),
        // new PR_noPREMPProcessComparator());
        ProcPriorityQueue_WithHeap processList = new ProcPriorityQueue_WithHeap(pList.size(),
                new PR_noPREMPProcessComparator());

        output.println("PR_noPREMP");
        int cpuTime = 0;
        while (pList.size() > 0 || processList.size() > 0) {
            // First add all eligible processes to the current queue.
            ListIterator<ProcInfo> iter = pList.listIterator();
            while (iter.hasNext()) {
                ProcInfo pi = iter.next();
                if (pi.arrivalTime <= cpuTime) {
                    pi.waitTime = cpuTime - pi.arrivalTime;
                    processList.offer(pi);
                    iter.remove();
                }
            }
            if (processList.size() == 0) {
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
            for (int i = 0; i < processList.size(); ++i) {
                ProcInfo pi = processList.elementAt(i);
                if (pi.arrivalTime < cpuTime) {
                    // System.out.print(cpuTime + " add wait " + pi.pid + " " + (cpuTime - pi.arrivalTime) + "\n");
                    // Note: For non preemptive algos this is always deltaTime because
                    // cpuTime - pi.arrivalTime = deltaTime + eligibilityTime(>=0)
                    pi.waitTime += Math.min(cpuTime - pi.arrivalTime, deltaTime);
                }
            }
            // Process changes needed for event, ex. switch or finish processes.
            // SJC runs processes to completion so there's no switch.

            // Current process always finished, remove from process list.
            finishedList.addLast(currentProcess);
        }
        float avgWait = CalcAvgWaitingTime(finishedList);
        output.printf("AVG Waiting Time: %.2f\n", avgWait);
        output.close();
    }

    private void PrioritySchedulingWithPreemption(PrintWriter output, LinkedList<ProcInfo> pList) {
        LinkedList<ProcInfo> finishedList = new LinkedList<ProcInfo>();
        // PriorityQueue<ProcInfo> processList = new PriorityQueue<ProcInfo>(pList.size(), new PR_noPREMPProcessComparator());
        ProcPriorityQueue_WithHeap processList = new ProcPriorityQueue_WithHeap(pList.size(),
                new PR_noPREMPProcessComparator());

        output.println("PR_withPREMP");
        int cpuTime = 0;
        int lastPid = -1;
        while (pList.size() > 0 || processList.size() > 0) {
            // First add all eligible processes to the current queue.
            ListIterator<ProcInfo> iter = pList.listIterator();
            while (iter.hasNext()) {
                ProcInfo pi = iter.next();
                if (pi.arrivalTime <= cpuTime) {
                    pi.waitTime = cpuTime - pi.arrivalTime;
                    processList.offer(pi);
                    iter.remove();
                }
            }
            if (processList.size() == 0) {
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
            for (int i = 0; i < processList.size(); ++i) {
                ProcInfo pi = processList.elementAt(i);
                if (pi.arrivalTime < cpuTime) {
                    // System.out.print(cpuTime + " add wait " + pi.pid + " " + (cpuTime -
                    // pi.arrivalTime) + "\n");
                    pi.waitTime += Math.min(cpuTime - pi.arrivalTime, deltaTime);
                }
            }
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
    }

    // This can also be done based only on the input and output.
    private float CalcAvgWaitingTime(LinkedList<ProcInfo> pInfoList) {
        int sumTime = 0;
        for (ProcInfo pi : pInfoList) {
            sumTime += pi.waitTime;
            // System.out.print("wait " + pi.pid + " " + pi.waitTime + "\n");
        }
        return (float) sumTime / pInfoList.size();
    }

    // One line per process with the following information:
    // Process number, Arrival Time, CPU burst time, Priority
    private ProcInfo parseToProcessInfo(String line) {
        String[] spltLine = line.split(" ");
        return new ProcInfo(Integer.parseInt(spltLine[0]), Integer.parseInt(spltLine[1]),
                Integer.parseInt(spltLine[2]), Integer.parseInt(spltLine[3]));
    }

    public void run(PrintWriter outputWriter, String schedAlgo,
            LinkedList<ProcInfo> processList) {
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
            PrioritySchedulingWithPreemption(outputWriter, processList);
        } else {
            throw new IllegalArgumentException("Invalid scheduling algorithm name: '" + schedAlgo + "'");
        }
    }

    public static void main(String[] args) {
        try {
            // Program should read an input file named “input.txt” and write
            // the results into an output file named “output.txt”
            BufferedReader inputReader = new BufferedReader(new FileReader("input.txt"));
            PrintWriter outputWriter = new PrintWriter("output.txt");

            // Read the name of the scheduling algo and number of processes.
            // Assume each comes on a new line.
            String schedAlgo = inputReader.readLine();
            int numOfProcesses = Integer.parseInt(inputReader.readLine());
            LinkedList<ProcInfo> processList = new LinkedList<CPUAssignment.ProcInfo>();

            CPUAssignment SingleCPU = new CPUAssignment();

            for (int i = 0; i < numOfProcesses; i++) {
                processList.add(SingleCPU.parseToProcessInfo(inputReader.readLine()));
            }
            inputReader.close();

            SingleCPU.run(outputWriter, schedAlgo, processList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}