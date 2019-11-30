package app;

// Building Class contains attributes for each instance

public class Building {

    public int buildingNum;
    public int executedTime;
    public int totalTime;

    // Constructor for BUilding which sets the attributes when instance is created
    public Building(int buildingNum, int executedTime, int totalTime) {
        this.buildingNum = buildingNum;
        this.executedTime = executedTime;
        this.totalTime = totalTime;
    }

    //@param Bulding object
    // Method to compute if caller is greater than the param
    public Boolean isGreaterThan(Building bldngObj) {
        return (this.executedTime > bldngObj.executedTime
                || (this.executedTime == bldngObj.executedTime && this.buildingNum > bldngObj.buildingNum));
    }
}