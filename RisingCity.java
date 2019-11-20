import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RisingCity {
    private static BufferedReader reader;
    private static int globalCounter = 0;
    private static Boolean keepWorking = true;
    private static int runningBuildingCounter;
    private static Building runningBuilding;
    private static MinHeap heap = new MinHeap();

    public static void main(String[] args) {
        runningBuildingCounter = 0;
        runningBuilding = null;

        openFile();
        String[] nextLine = readFile();

        do {
            if (Integer.valueOf(nextLine[0]).equals(globalCounter)) {
                switch (nextLine[1]) {
                case "Insert":
                    Building newBuilding = new Building(Integer.valueOf(nextLine[2]), Integer.valueOf(nextLine[3]));
                    heap.insert(newBuilding);
                    // rbt.add(newBuilding);
                    break;

                case "PrintBuliding":
                    // print
                    System.out.println("ready to print");
                    break;
                }

                nextLine = readFile();
            }

            if (runningBuildingCounter == 0) {
                setBuilding();
            }

            constructBuilding();
            globalCounter += 1;
        } while (nextLine[1] != "" || keepWorking);
    }

    private static void setBuilding() {
        runningBuilding = heap.findConstructBuilding();

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
            if (runningBuilding.getExecutedTime() == runningBuilding.getTotalTime()) {
                // rbt.delete(runningBuilding);
                heap.remove(runningBuilding);

                // TODO: write to output file
                System.out.println("finish building.");

                runningBuilding = heap.findConstructBuilding();
                if (runningBuilding == null) {
                    keepWorking = false;
                    return;
                }
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

    private static void openFile() {
        try {
            reader = new BufferedReader(new FileReader("input.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}