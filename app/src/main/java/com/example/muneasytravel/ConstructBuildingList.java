package com.example.muneasytravel;

import java.util.ArrayList;

/****************************************************************************************************************
 * ConstructBuildingList implements BuildingListInterface, which will be used for constructing Building ArrayList
 */
public class ConstructBuildingList implements BuildingListInterface {

    private ArrayList<Building> buildings;

    public ConstructBuildingList(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    @Override
    public void setBuildingArrayList() {
        buildings.add(new Building("AA", 47.571119, -52.732470, 5));
        buildings.add(new Building("EN", 47.5749547, -52.7353382, 4));
        buildings.add(new Building("ED", 47.571138, -52.735965, 5));
        buildings.add(new Building("BN", 47.575305, -52.734239,4));
        buildings.add(new Building("BT", 47.572472, -52.733165, 2));
        buildings.add(new Building("CL", 47.575979, -52.731578, 2));
        buildings.add(new Building("CS", 47.572119, -52.732740)); // only 1 floor
        buildings.add(new Building("ER", 47.573991, -52.734271, 5));
        buildings.add(new Building("HH", 47.571609, -52.731290, 3));
        buildings.add(new Building("MU", 47.572126, -52.730651,2));
        buildings.add(new Building("PA", 47.572083, -52.731621));
        buildings.add(new Building("SN", 47.572083, -52.731621, 4));
        buildings.add(new Building("PE", 47.571072, -52.733971, 4));
        buildings.add(new Building("QC", 47.577405, -52.730332, 4));
        buildings.add(new Building("UC", 47.573277, -52.735325, 5));
        buildings.add(new Building("A", 47.571119, -52.732470, 5));
        buildings.add(new Building("C", 47.573114, -52.733526, 4));
        buildings.add(new Building("H", 47.571497, -52.742860, 3));
        buildings.add(new Building("J", 47.575434, -52.732859,4));
    }
}
