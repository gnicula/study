import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

public class GNicula_MemoryManagement {

    private static int numPages;
    private static int numFrames;
    private static int numRequests;
    private static int[] pageRequests;
    private static int[] frames;

    // reads 'filename' and populates [numPages, numFrames, numRequests,
    // pageRequests]
    public static void readInputParams(String filename) {
        try {
            Scanner inputScanner = new Scanner(new File(filename));
            numPages = inputScanner.nextInt();
            numFrames = inputScanner.nextInt();
            numRequests = inputScanner.nextInt();
            pageRequests = new int[numRequests];
            for (int i = 0; i < numRequests; i++) {
                pageRequests[i] = inputScanner.nextInt();
            }
            inputScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Returns the frame id (index) of the frame that holds the given page.
    private static int getFrameNumber(int pageNumber) {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == pageNumber) {
                return i;
            }
        }
        return -1;
    }

    // Returns the index of the frame to use for FIFO algorithm
    private static int getFIFOFrameToUse(Deque<Integer> requestQueue) {
        int frameToReplace = getFrameNumber(-1);

        if (frameToReplace != -1) {
            // Found an empty frame, return it.
            return frameToReplace;
        }
        // Return the index of the frame holding the first page loaded.
        // Remove the page from the head of the deque since it's being unloaded.
        return getFrameNumber(requestQueue.poll());
    }

    public static void main(String[] args) {
        // Read all data from input file.
        readInputParams("input.txt");
        PrintWriter writer;
        try {
            writer = new PrintWriter(new File("output.txt"));
            // Execute the FIFO simulation.
            runFIFO(writer);
            // Flush and close the output file.
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void runFIFO(PrintWriter writer) {
        frames = new int[numFrames];
        Arrays.fill(frames, -1); // Initialize all frames as empty
        Deque<Integer> requestQueue = new ArrayDeque<>();
        int pageFaults = 0;

        writer.println("FIFO");
        for (int i = 0; i < numRequests; i++) {
            int requestedPage = pageRequests[i];
            int frameNumber = getFrameNumber(requestedPage);
            if (frameNumber != -1) {
                writer.printf("Page %d already in Frame %d%n", requestedPage, frameNumber);
            } else {
                ++pageFaults;
                int frameToReplace = getFIFOFrameToUse(requestQueue);
                if (frames[frameToReplace] == -1) {
                    writer.printf("Page %d loaded into Frame %d%n", requestedPage, frameToReplace);
                } else {
                    int pageToUnload = frames[frameToReplace];
                    writer.printf("Page %d unloaded from Frame %d, Page %d loaded into Frame %d%n",
                            pageToUnload, frameToReplace, requestedPage, frameToReplace);
                }
                frames[frameToReplace] = requestedPage;
                requestQueue.add(requestedPage);
            }
        }
        writer.printf("%d page faults%n", pageFaults);
    }
}
