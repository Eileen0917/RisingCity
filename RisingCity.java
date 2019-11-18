import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RisingCity {
    private static BufferedReader reader;
    private static int globalCounter = 0;

    public static void main(String[] args) {
        openFile();

        // Boolean finished = false;
        String[] nextLine = readFile();
        // MinHeap heap = new MinHeap();

        do {
            if (Integer.valueOf(nextLine[0]).equals(globalCounter)) {
                switch (nextLine[1]) {
                case "Insert":
                    // do insert to min heap and RBT
                    System.out.println("ready to insert");
                    break;

                case "PrintBuliding":
                    // print
                    System.out.println("ready to print");
                    break;
                }

                nextLine = readFile();
                // build city and check finished?
                globalCounter += 1;
            } else {
                // build city and check finished?
                globalCounter += 1;
            }
        } while (nextLine[1] != "");
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