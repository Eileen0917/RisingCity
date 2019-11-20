public class Building {
    int buildingNum;
    int totalTime;
    int executedTime;

    public Building(int bNum, int totalTime) {
        this.buildingNum = bNum;
        this.executedTime = 0;
        this.totalTime = totalTime;
    }

    public int getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(int buildingNum) {
        this.buildingNum = buildingNum;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getExecutedTime() {
        return executedTime;
    }

    public void setExecutedTime(int executedTime) {
        this.executedTime = executedTime;
    }
}