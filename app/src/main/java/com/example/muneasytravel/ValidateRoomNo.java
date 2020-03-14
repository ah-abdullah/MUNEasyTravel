package com.example.muneasytravel;

import java.util.ArrayList;

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
            String[] buildingPrefix = room.split("(?<=\\D)(?=\\d)", 2); // splitting the building prefix and the rest of the room number
            // buildingPrefix[0] will have the building prefix.
            for (int i = 0; i < buildings.size(); i++) {
                if (buildingPrefix[0].trim().equals(buildings.get(i).getBuildingName())) {
                    setFoundBuildingIndex(i);
                    return true;
                }
            }
            return false;
        }
}
