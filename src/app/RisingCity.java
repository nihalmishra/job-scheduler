package app;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

// RisingCity class is contains the main method and is the driver class for the project

public class RisingCity {

    private static String INPUT_FILE;
    private static final String OUT_FILE = "output_file.txt";

    public static int GLOBAL_TIMER = -1; // global time counter
    public static Building currBuilding = null; // current building being constructed

    public static RedBlackTree<Integer> rbt = new RedBlackTree<Integer>();
    public static MinHeap heap = new MinHeap();
    public static HashMap<Integer, Building> lookup = new HashMap<>(); // maintain pointer between MinHeap and RedBlackTree
    public static List<String> lineList = new ArrayList<String>(); // list to store each line of INPUT_FILE

    public static BufferedReader reader = null;
    public static BufferedWriter writer = null;
    
    public static int lineCount = 0; // counter for number of lines processed in INPUT_FILE

    public static void main(final String[] args) throws IOException {

        INPUT_FILE = "./src/resources/Sample_input1.txt";
        // INPUT_FILE = args[0]; // input file from commandline
        try {
            reader = new BufferedReader(new FileReader(INPUT_FILE));
        } catch (final Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        writer = new BufferedWriter(new FileWriter(OUT_FILE));

        String line;

        // read each line from INPUT_FILE and store it in a list
        while ((line = reader.readLine()) != null) {
            lineList.add(line);
            // System.out.println(line);
        }

        // driver loop which processes each line of file and works on the current
        // building
        do {
            GLOBAL_TIMER++;
            String curLine = null;

            // If current building exists, then continue construction
            if (currBuilding != null) {
                currBuilding.executedTime += 1;
            }

            // if global timer and input command timer are equal then process command
            if (lineCount < lineList.size() && GLOBAL_TIMER == Integer
                    .parseInt(lineList.get(lineCount).substring(0, lineList.get(lineCount).indexOf(":")))) {
                curLine = lineList.get(lineCount);
                lineCount++;
            }

            // Handle Insert command and insert Building into Heap and RedBlackTree
            if (curLine != null && curLine.contains("Insert")) {
                handleInsertCmd(curLine);
            }
            // Handle Print command and print the building(s)
            else if (curLine != null && curLine.contains("PrintBuilding")) {
                handlePrintCmd(curLine);
            }

            // prints building completion time when current building is completed
            printCompletedBuilding();

        } while (heap.getSize() != 0 || currBuilding != null || lineCount != lineList.size());

        reader.close();
        writer.close();
    }

    // @param: insert line to be processed
    // inserts building to heap and RedBlackTree
    private static void handleInsertCmd(final String curLine) {
        final String arg1 = curLine.substring(curLine.indexOf("(") + 1, curLine.indexOf(","));
        final int buildingNum = Integer.parseInt(arg1);
        final String arg2 = curLine.substring(curLine.indexOf(",") + 1, curLine.indexOf(")"));
        final int totalTime = Integer.parseInt(arg2);
        final int executedTime = 0;
        final Building newBuilding = new Building(buildingNum, executedTime, totalTime);
        heap.insert(newBuilding);
        rbt.insert(buildingNum);
        lookup.put(buildingNum, newBuilding);
    }

    // @param: Buffered writer obj, line to be processed
    // Prints the building triplet (buildingNum, execTime, totalTime)
    private static void handlePrintCmd(final String curLine) throws IOException {
        if (curLine.contains(",")) {
            final String arg1 = curLine.substring(curLine.indexOf("(") + 1, curLine.indexOf(","));
            final String arg2 = curLine.substring(curLine.indexOf(",") + 1, curLine.indexOf(")"));
            final int buildingNum1 = Integer.parseInt(arg1);
            final int buildingNum2 = Integer.parseInt(arg2);
            printBuildingsInRange(buildingNum1, buildingNum2);
        } else {
            final String arg = curLine.substring(curLine.indexOf("(") + 1, curLine.indexOf(")"));
            final int buildingNum = Integer.parseInt(arg);
            printBuilding(buildingNum);
        }
    }

    // @param: building number to be printed
    // search for Building in RedBlackTree and print it
    private static void printBuilding(final int buildingNum) throws IOException {
        
        Building building = null;
        if (rbt.search(buildingNum) != null) {
            building = lookup.get(buildingNum);
            // System.out.print("G:" + GLOBAL_TIMER + "(" + building.buildingNum + "," +
            // building.executedTime + "," + building.totalTime + ")");
            writer.write("(" + building.buildingNum + "," + building.executedTime + "," + building.totalTime + ")");
        }

        // If no building exists print (0,0,0)
        if (building == null) {
            // System.out.print("(0,0,0)");
            writer.write("(0,0,0)");
        }
        // System.out.println();
        writer.write("\n");
    }

    // @param: range from buildingNum1 to buildingNum2
    // print all buildings in given range
    private static void printBuildingsInRange(final int buildingNum1, final int buildingNum2) throws IOException {
        int numNodes = 0;
        Building building = null;
        
        // list of active buildings i.e. buildings which are being costructed
        List<Integer> activeBldngList = new ArrayList<>();
        activeBldngList = rbt.searchInRange(buildingNum1, buildingNum2);

        for (Integer i : activeBldngList) {
            if (!activeBldngList.isEmpty()) {
                numNodes++;
                building = lookup.get(i);
                if (numNodes == 1)
                    // System.out.print("G:" + GLOBAL_TIMER + "(" + building.buildingNum + "," +
                    // building.executedTime + "," + building.totalTime + ")");
                    writer.write(
                            "(" + building.buildingNum + "," + building.executedTime + "," + building.totalTime + ")");
                else
                    // System.out.print(",(" + building.buildingNum + "," + building.executedTime +
                    // "," + building.totalTime + ")");
                    writer.write(
                            ",(" + building.buildingNum + "," + building.executedTime + "," + building.totalTime + ")");
            }
        }

        // If no building exists print (0,0,0)
        if (activeBldngList.isEmpty()) {
            //System.out.print("(0,0,0)");
            writer.write("(0,0,0)");
        }
        //System.out.println();
        writer.write("\n");
    }

    // print if building completed else get new building from Heap 
    private static void printCompletedBuilding() throws IOException {
        if (currBuilding == null && heap.getSize() != 0) {
            currBuilding = heap.remove();
        } else if (currBuilding != null) {
            if (currBuilding.executedTime == currBuilding.totalTime) {
                // System.out.println("(" + currBuilding.buildingNum + "," + GLOBAL_TIMER + ")");

                // do not print new line if last building is completed
                if (lineCount == lineList.size() && heap.getSize() == 0) {
                    writer.write("(" + currBuilding.buildingNum + "," + GLOBAL_TIMER + ")");
                } else
                    writer.write("(" + currBuilding.buildingNum + "," + GLOBAL_TIMER + ")\n");

                // remove from RedBlackTree and get new building to be constructed from Heap
                final RedBlackNode<Integer> rbNode = new RedBlackNode<Integer>(currBuilding.buildingNum);
                rbt.remove(rbNode);
                currBuilding = heap.remove();
            }
            // if building has been worked on for 5 days then re-insert into Heap and get
            // new building to be constructed
            else if (currBuilding.executedTime % 5 == 0) {
                heap.insert(currBuilding);
                currBuilding = heap.remove();
            }
        }
    }
}