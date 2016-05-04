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
        this.capacity = capacity;
    	totalDemand = firstCustomer.getDemand();

    }

    public void addCustomer(VRPcustomer c){
	//TODO verify that the added demand (from c) doesn't exceed the capacity
	//add the client (update the total demand and cost)
    }

    public void mergeWith(VRProute r) {
        System.out.println("-----Gonna Merge : " + this + " With : " + r);
        this.totalDemand += r.getTotalDemand();
        this.customers.append(r.getCustomers());
        System.out.println("We have this now : " + this + "----");
    }

    public boolean isMergeableWith(VRProute r){
        System.out.println(
            "totalDemand = " + totalDemand + "getTotalDemand = " +
            r.getTotalDemand() + " = " + (totalDemand + r.getTotalDemand())
        );
        return (totalDemand + r.getTotalDemand() )<= capacity;
    }


    /******************************************
     * getters
     ******************************************/
    public double getCost() {
        return this.cost;
    }

    public VRPcustomer getFirstCustomer() {
        return customers.getFirst();
    }

    public  VRPcustomer getLastCustomer() {
        return customers.getLast();
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

    public String toString() {
        return customers.toString() + " totalDemand: " + totalDemand +
            " cost: "+ cost + " capacity: " + capacity;
    }
}








