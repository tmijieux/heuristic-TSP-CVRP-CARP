package cvrp;

public class VRPsaving implements Comparable<VRPsaving>{
    private VRProute r1,r2;
    private double saving;
    private int jointDemand;

    public VRPsaving(VRProute r1, VRProute r2, double saving) {
        this.r1 = r1;
        this.r2 = r2;
        this.saving = saving;
        jointDemand = r1.getTotalDemand() + r2.getTotalDemand();
    }

    public double getSaving() {
        return saving;
    }

    public VRProute getFirstRoute() {
        return r1;
    }

    public VRProute getSecondRoute() {
        return r2;
    }

    public int compareTo(VRPsaving other) {
        if (this.saving > other.getSaving())
            return -1;
        else if (this.saving < other.getSaving())
            return 1;
        else
            return 0;
    }

    public String toString() {
        return "Saving :" + saving + " jointDemand : " + jointDemand;
    }
}

