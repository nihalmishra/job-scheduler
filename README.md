# **Job Scheduler using Min Heap and Red Black Tree**

## **Project Description**

Wayne Enterprises is developing a new city. They are constructing many buildings and plan to use software to keep track of all buildings under construction in this new city. A building record has the following fields:
buildingNum: unique integer identifier for each building.
executedTime: total number of days spent so far on this building.
totalTime: the total number of days needed to complete the construction of the building.

The needed operations are:

1. PrintBuilding(buildingNum) prints the triplet (buildingNum,executedTime,totalTime).
2. PrintBuilding(buildingNum1, buildingNum2) prints all triplets bn, executedTime, totalTime for which buildingNum1 <= bn <= buildingNum2.
3. Insert(buildingNum,totalTime) where buildingNum is different from existing building numbers and executedTime = 0.

A min heap should be used to store (buildingNum,executedTime,totalTime) triplets ordered by executedTime. You will need a suitable mechanism to handle duplicate executedTimes in your min heap. An RBT should be used store (buildingNum,executedTime,totalTime) triplets ordered by buildingNum.

Wayne Enterprises works on one building at a time. When it is time to select a building to work on, the building with the lowest executedTime (ties are broken by selecting the building with the lowest buildingNum) is selected. The selected building is worked on until complete or for 5 days, whichever happens first. If the building completes during this period its number and day of completion is output and it is removed from the data structures. Otherwise, the building’s executedTime is updated. In both cases, Wayne Construction selects the next building to work on using the selection rule.

## Development Environment

IDE: Visual Studio Code, Java 8  
Version Control: Git, remote repository on GitHub
