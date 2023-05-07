/* Name: Gabriele Nicula
 * Class: csc139
 * ID: 219969192
 * Tested on: MacBook Air M1, Java version 16.0.1 2021-04-20
 * 			  ecs-pa-coding2.ecs.csus.edu Java version 11.0.18 2023-01-17 LTS
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
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
    
    // Returns the next timeStep this page will be requested. 
    private static int getNextRequestForPage(int pageNumber, int timeStep) {
    	for (int i = timeStep + 1; i < numRequests; ++i) {
    		if (pageRequests[i] == pageNumber) {
    			return i;
    		}
    	}
    	return Integer.MAX_VALUE;
    }

    // Returns the index of the frame to use for FIFO algorithm.
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
    
    // Returns the index of the frame to use for the Optimal algorithm.
    private static int getOptimalFrameToUse(Map<Integer, Integer> requestMap) {
    	int frameToReplace = getFrameNumber(-1);
    	
    	if (frameToReplace != -1) {
    		// Found an empty frame, return it.
    		return frameToReplace;
    	}
    	
    	frameToReplace = 0;
    	for (int i = 1; i < frames.length; ++i) {
    		if (requestMap.get(frames[frameToReplace]) < requestMap.get(frames[i])) {
    			frameToReplace = i;
    			if (requestMap.get(frames[frameToReplace]) == Integer.MAX_VALUE) {
    				return frameToReplace;
    			}
    		}
    	}
    	return frameToReplace;
    }
    
    // Returns the index of the frame to use for the LRU algorithm.
    private static int getLRUFrameToUse(Map<Integer, Integer> requestMap) {
    	int frameToReplace = getFrameNumber(-1);
    	
    	if (frameToReplace != -1) {
    		// Found an empty frame, return it.
    		return frameToReplace;
    	}
    	
    	frameToReplace = 0;
    	for (Map.Entry<Integer, Integer> entry : requestMap.entrySet()) {
    		if (entry.getValue() < requestMap.get(frameToReplace)) {
    			frameToReplace = entry.getKey();
    		}
    	}
    	return frameToReplace;
    }
    
    public static void runFIFO(PrintWriter writer) {
        frames = new int[numFrames];
        Arrays.fill(frames, -1); // Initialize all frames as empty.
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
    
    public static void runOptimal(PrintWriter writer) {
    	 frames = new int[numFrames];
         Arrays.fill(frames, -1); // Initialize all frames as empty.
         Map<Integer, Integer> requestMap = new HashMap<Integer, Integer>();
         int pageFaults = 0;
         int timeStep = 0;
         
         writer.println("Optimal");
         for (int i = 0; i < numRequests; i++) {
             int requestedPage = pageRequests[i];
             int frameNumber = getFrameNumber(requestedPage);
             // System.out.println("Requested page: " + requestedPage);
             if (frameNumber != -1) {
                 writer.printf("Page %d already in Frame %d%n", requestedPage, frameNumber);
             } else {
                 ++pageFaults;
                 int frameToReplace = getOptimalFrameToUse(requestMap);
                 if (frames[frameToReplace] == -1) {
                     writer.printf("Page %d loaded into Frame %d%n", requestedPage, frameToReplace);
                 } else {
                     int pageToUnload = frames[frameToReplace];
                     writer.printf("Page %d unloaded from Frame %d, Page %d loaded into Frame %d%n",
                             pageToUnload, frameToReplace, requestedPage, frameToReplace);
                     requestMap.remove(pageToUnload);
                 }
                 frames[frameToReplace] = requestedPage;
             }
             requestMap.put(requestedPage, getNextRequestForPage(requestedPage, timeStep));
             ++timeStep;
         }
         writer.printf("%d page faults%n", pageFaults);
    }
    
    public static void runLRU(PrintWriter writer) {
        frames = new int[numFrames];
        Arrays.fill(frames, -1); // Initialize all frames as empty.
        Map<Integer, Integer> requestMap = new HashMap<Integer, Integer>();
        int pageFaults = 0;
        int timeStep = 0;
        
        writer.println("LRU");
        for (int i = 0; i < numRequests; i++) {
            int requestedPage = pageRequests[i];
            int frameNumber = getFrameNumber(requestedPage);
            if (frameNumber != -1) {
                writer.printf("Page %d already in Frame %d%n", requestedPage, frameNumber);
                requestMap.put(frameNumber, timeStep);
            } else {
                ++pageFaults;
                int frameToReplace = getLRUFrameToUse(requestMap);
                if (frames[frameToReplace] == -1) {
                    writer.printf("Page %d loaded into Frame %d%n", requestedPage, frameToReplace);
                } else {
                    int pageToUnload = frames[frameToReplace];
                    writer.printf("Page %d unloaded from Frame %d, Page %d loaded into Frame %d%n",
                            pageToUnload, frameToReplace, requestedPage, frameToReplace);
                }
                frames[frameToReplace] = requestedPage;
                requestMap.put(frameToReplace, timeStep);
            }
            ++timeStep;
        }
        writer.printf("%d page faults%n", pageFaults);
    }

    public static void main(String[] args) {
        // Read all data from input file.
        readInputParams("input.txt");
        PrintWriter writer;
        try {
            writer = new PrintWriter(new File("output.txt"));
            // Execute the FIFO simulation.
            runFIFO(writer);
            writer.println();
            // Execute the Optimal simulation.
            runOptimal(writer);
            writer.println();
            // Execute the LRU simulation.
            runLRU(writer);
            // Flush and close the output file.
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
