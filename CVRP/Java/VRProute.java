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


    public int getTotalDemand() {
        return totalDemand;
    }


    public int getLength() {
        return 0;//todo
    }

    public CustomList<VRPcustomer> getCustomers() {
        return customers;
    }

    public void mergeWith(VRProute r) {
        this.totalDemand += r.getTotalDemand();
        this.customers.append(r.getCustomers());
    }

    public boolean isMergeableWith(VRProute r){
        return (totalDemand + r.getTotalDemand() )<= capacity;
    }
    
    public String toString() {
        return customers.toString();
    }
    
}








