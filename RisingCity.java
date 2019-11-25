import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class RisingCity {
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static int globalCounter = 0;
    private static Boolean keepWorking = true;
    private static int runningBuildingCounter;
    private static Building runningBuilding;
    private static MinHeap heap = new MinHeap();
    private static RedBlackTree rbt = new RedBlackTree();

    public static void main(String[] args) {
        runningBuildingCounter = 0;
        runningBuilding = null;

        // open the input file and make it read only one line a time.
        openReadFile(args[0]);
        String[] nextLine = readFile();
        openWriteFile();

        do {
            // do the input command when the day is equal to global counter.
            if (Integer.valueOf(nextLine[0]).equals(globalCounter)) {

                switch (nextLine[1]) {
                case "Insert":
                    Building newBuilding = new Building(Integer.valueOf(nextLine[2]), Integer.valueOf(nextLine[3]));
                    String result = heap.insert(newBuilding);
                    if (result.equals("duplicated")) {
                        String str = "You insert duplicated building number.";
                        writeFile(str);
                        stopReadAndWriteFile();
                        return;
                    }
                    rbt.insert(newBuilding);
                    break;

                case "PrintBuliding":
                    if (nextLine[4] == "") {
                        Building b = rbt.search(Integer.valueOf(nextLine[2]));
                        if (b == null) {
                            String str = "(0,0,0)";
                            writeFile(str);
                        } else {
                            String str = "(" + b.getBuildingNum() + ", " + b.getExecutedTime() + ", " + b.getTotalTime()
                                    + ")";
                            writeFile(str);
                        }
                    } else {
                        ArrayList<Building> list = new ArrayList<Building>();
                        list = rbt.searchInRange(list, Integer.valueOf(nextLine[2]), Integer.valueOf(nextLine[4]));
                        if (list.size() == 0) {
                            System.out.println("(0,0,0)");
                        } else {
                            String str = new String();
                            for (Building b : list) {
                                str += "(" + b.getBuildingNum() + "," + b.getExecutedTime() + "," + b.getTotalTime()
                                        + "),";
                            }
                            str = str.substring(0, str.length() - 1);
                            writeFile(str);
                        }
                    }
                    break;
                }

                // read next line of input to be next working command.
                nextLine = readFile();
            }

            // check if there is any building finished.
            if (runningBuilding != null) {
                checkFinished();
            }

            // if already finished a building for 5 days, find a new one.
            if (runningBuildingCounter == 0) {
                setBuilding();
            }

            // construct the selected building.
            constructBuilding();

            // a day pass.
            globalCounter += 1;
        } while (nextLine[1] != "" || keepWorking);

        // finish while loop and close reader and writer.
        stopReadAndWriteFile();
    }

    private static void setBuilding() {
        runningBuilding = heap.findConstructBuilding();

        // if runningBuilding == null, all building is finished.
        if (runningBuilding == null) {
            keepWorking = false;
            return;
        }

        if (runningBuilding.getExecutedTime() + 5 >= runningBuilding.getTotalTime()) {
            runningBuildingCounter = runningBuilding.getTotalTime() - runningBuilding.getExecutedTime();
        } else {
            runningBuildingCounter = 5;
        }
    }

    private static void constructBuilding() {
        if (runningBuildingCounter != 0 && runningBuilding != null) {
            heap.increaseKey(runningBuilding, 1);
            runningBuildingCounter -= 1;
        }
    }

    private static void checkFinished() {
        if (runningBuilding.getExecutedTime() == runningBuilding.getTotalTime()) {
            heap.remove(runningBuilding);
            rbt.remove(runningBuilding);

            String str = "(" + runningBuilding.getBuildingNum() + "," + globalCounter + ")";
            writeFile(str);

            runningBuilding = heap.findConstructBuilding();
            if (runningBuilding == null) {
                keepWorking = false;
            }
        }
    }

    private static String[] readFile() {
        String day = "0";
        String command = "";
        String bNum = "";
        String bNum2 = "";
        String totalTime = "";

        try {
            String line = reader.readLine();
            if (line != null) {
                day = line.substring(0, line.indexOf(":"));
                command = line.substring(line.indexOf(":") + 2, line.indexOf("("));
                line = line.substring(line.indexOf(":")).trim();

                switch (command) {
                case "Insert":
                    bNum = line.substring(line.indexOf('(') + 1, line.indexOf(","));
                    totalTime = line.substring(line.indexOf(',') + 1, line.indexOf(")"));
                    break;

                case "PrintBuliding":
                    if (!line.contains(",")) {
                        bNum = line.substring(line.indexOf('(') + 1, line.indexOf(")"));
                    } else {
                        bNum = line.substring(line.indexOf('(') + 1, line.indexOf(","));
                        bNum2 = line.substring(line.indexOf(',') + 1, line.indexOf(")"));
                    }
                    break;

                default:
                    System.out.println("invalid command");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[] { day, command, bNum, totalTime, bNum2 };
    }

    private static void openReadFile(String file) {
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void openWriteFile() {
        try {
            writer = new BufferedWriter(new FileWriter("output_file.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(String str) {
        try {
            writer.write(str);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void stopReadAndWriteFile() {
        try {
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}