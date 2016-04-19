package cvrp;

import util.CustomList;

public class ClarkWright {

    //change Integer with a new round//tourné class

    private VRPinstance vrp;
    private CustomList<VRProute> routes;
    private int clientCount;
    double [][] distance;


    ClarkWright(VRPinstance vrp) {
	this.vrp = vrp;
	this.clientCount = vrp.getN();
	this.distance = vrp.getMatrix();
    }
    

    public double[][] getMatrix() {
	return	distance;
    }
    
    public int getN() {
	return vrp.getN();
    }
    





    public void createRouteForEachCustomer() {
	for (int i = 1; i < this.clientCount; i++) {
	    VRPcustomer c = new VRPcustomer(i,vrp.getDemand(i));
	    double cost = distance[0][i] + distance[i][0];
	    VRProute r = new VRProute(c, cost, vrp.getCapacity());
	}
    }


    public void buildSolution(){
	
	routes = new CustomList<VRProute>();
	this.createRouteForEachCustomer();
    }
    
    


    
    public static void main(String args[]) {
	ClarkWright cw = null;
	
	if (args.length < 1)
	    usage();
	try {
	    cw = new ClarkWright(new VRPinstance(args[0]));
	} catch (java.io.FileNotFoundException e) {
	    System.out.println("File not Found");
	}
	System.out.println("Matrice de distances");
	double[][] matrix = cw.getMatrix();
	for (int i = 0; i < cw.getN(); i++) {
	    System.out.print(matrix[i][0] + " ");
	}

	System.out.println("Clark & Wright sur : " + args[0]);

	
	
	
    }


    
    public static  void usage() {
	System.out.println("Usage: ClarkWright path/to/instance");
	System.exit(1);
    }
    
    
}
