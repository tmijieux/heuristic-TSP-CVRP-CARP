package cvrp;

public class VRPmerge implements Comparable<VRPmerge>{
    private VRPcustomer c1, c2;
    private double saving;
    private double[][] distance;

    private void computeSaving() {
    	int c1n = c1.getNumber();
    	int c2n = c2.getNumber();
        saving = distance[0][c1n] + distance[c2n][0] - distance[c1n][c2n];
    }

    public VRPmerge(double[][] distance, VRPcustomer c1, VRPcustomer c2) {
        this.distance = distance;
        this.c1 = c1;
        this.c2 = c2;
        computeSaving();
    }

    public double getSaving() {
        return saving;
    }

    public int compareTo(VRPmerge other) {
        if (this.saving > other.getSaving())
            return -1;
        else if (this.saving < other.getSaving())
            return 1;
        else
            return 0;
    }

    public String toString() {
        return "[MERGE]: c1 :" + c1 + " --> c2 :" + c2 +
            "(saving :" + saving + ")";
    }

    VRPcustomer getCustomer1()
    {
        return c1;
    }
    
    VRPcustomer getCustomer2()
    {
        return c2;
    }
}

