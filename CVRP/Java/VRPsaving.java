package cvrp;


public class VRPsaving implements Comparable<VRPsaving>{
	private VRProute r1,r2;
	private int saving;
	private int jointDemand;

	public VRPsaving(VRProute r1, VRProute r2, int saving) {
		this.r1 = r1;
		this.r2 = r2;
		this.saving = saving;
		jointDemand = r1.getTotalDemand() + r2.getTotalDemand();
	}

		public VRPsaving(VRProute r1, VRProute r2) {
		this.r1 = r1;
		this.r2 = r2;
		this.saving = computeSaving(r1,r2);
		jointDemand = r1.getTotalDemand() + r2.getTotalDemand();
	}


    /* 
     * Computes and return the savings if we want to "merge" the VRProute r1 with r2
     */
  	private int computeSaving(VRProute r1, VRProute r2) {
  		return 0;
  	}


	public int getSaving() {
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
			return 1;
		else if (this.saving < other.getSaving()) 
			return -1;
		else 
			return 0;
	}
}

