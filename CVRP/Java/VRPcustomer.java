package cvrp;


public class VRPcustomer {
    int customerNumber;
    int demand;

    public VRPcustomer(int number, int demand) {
	this.demand = demand;
	this.customerNumber = number;
    }


    public int getDemand() {
	return demand;
    }

    public String toString() {
    	return Integer.toString(customerNumber);
    }

    public int getNumber() {
        return customerNumber;
    }
}
