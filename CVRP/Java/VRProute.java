package cvrp;

import util.CustomList;

public class VRProute {
    private CustomList<VRPcustomer> customers;
    private double cost;
    private int capacity;
    private int totalDemand;
    private int length;

    public VRProute(VRPcustomer firstCustomer, double initialCost, int capacity) {
    	customers = new CustomList<VRPcustomer>();
    	customers.add(firstCustomer);
    	cost = initialCost;
        this.capacity = capacity;
    	totalDemand = firstCustomer.getDemand();
        length = 1;
    }

    public void mergeWith(VRProute r, double saving) {
        System.err.println("--- Gonna Merge: [" + this + "] with: [" + r + "]");
        this.totalDemand += r.getTotalDemand();
        this.customers.append(r.getCustomers());
        this.cost += r.getCost() - saving;
        this.length += r.getLength();
        System.err.println("We have this now: " + this + "----");
    }

    public boolean isMergeableWith(VRProute r) {
        int newTotalDemand = this.totalDemand + r.getTotalDemand();
        System.err.println("this.totalDemand = " + this.totalDemand);
        System.err.println("r.getTotalDemand = " + r.getTotalDemand());
        System.err.println("newTotalDemand = " + newTotalDemand);
        System.err.println("this.capacity = " + this.capacity);

        return newTotalDemand <= this.capacity;
    }

    /******************************************
     * getters                                *
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
        return length;
    }

    public CustomList<VRPcustomer> getCustomers() {
        return customers;
    }

    public String toString() {
        return "Route: "+ customers.toString() + " totalDemand: " +
            totalDemand + " cost: "+ cost + " capacity: " + capacity;
    }
}
