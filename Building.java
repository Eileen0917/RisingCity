public class Building {
    public Building(int starttimeseek) {
        this.startSeekTime = starttimeseek;
    }

    int buildingNum;
    int totalTime;
    int executedTime;
    int startSeekTime;

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