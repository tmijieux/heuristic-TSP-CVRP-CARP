package cvrp;

import util.CustomList;

public class VRProute {

    private CustomList<VRPcustomer> customers;
    private double cost;
    private int capacity;
    private int totalDemand;

    public VRProute(VRPcustomer firstCustomer,double initialCost, int capacity) {
	customers = new CustomList<VRPcustomer>();
	customers.add(firstCustomer);
	cost = initialCost;
	totalDemand = firstCustomer.getDemand();
	    
    }

    public void addCustomer(VRPcustomer c){
	//TODO verify that the added demand (from c) doesn't exceed the capacity
	//add the client (update the total demand and cost)
    }


    /* 
     * Computes and return the savings if we want to "merge" the VRProute r with this VRProute
     */ 
    
    public int computeSaving(VRProute r) {
	return 0;
    }
    
    
}








