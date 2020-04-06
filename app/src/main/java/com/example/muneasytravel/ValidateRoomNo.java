package com.example.muneasytravel;

import java.util.ArrayList;

/***********************************************************************************************************
 * ValidateRoomNo implements ValidateUserInputInterface, which will be used for validating user input
 */
public class ValidateRoomNo implements ValidateUserInputInterface {

    private String room;
    private ArrayList<Building> buildings;
    private Integer foundBuildingIndex;

    public ValidateRoomNo(String room, ArrayList<Building> buildings) {
        this.room = room;
        this.buildings = buildings;
        this.foundBuildingIndex = -1;
    }

    public Integer getFoundBuildingIndex() {
        return foundBuildingIndex;
    }

    public void setFoundBuildingIndex(Integer foundBuildingIndex) {
        this.foundBuildingIndex = foundBuildingIndex;
    }

    @Override
    public boolean validate() {
            String[] buildingPrefix = room.split("(?<=\\D)(?=\\d)", 2); // splitting the building prefix and the rest of the room number (Example: EN1038B)
            // buildingPrefix[0] will have the building prefix. (Example: EN)
            // buildingPrefix[1] will have the rest of the room no.. (Example: 1038B)
            for (int i = 0; i < buildings.size(); i++) {
                if (buildingPrefix[0].trim().equals(buildings.get(i).getBuildingName())) { // matched building prefix
                    setFoundBuildingIndex(i); // setting where in the building list index it was matched
                    return true;
                }
            }
            return false;
        }
}
