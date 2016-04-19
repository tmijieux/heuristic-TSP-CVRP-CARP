package cvrp;

import util.CustomList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ClarkWright {

    //change Integer with a new round//tourné class

    private VRPinstance vrp;
    private List<VRProute> routes; 
    private List<VRProute> finalRoutes; //Holds the routes that cannot be merged anymore
    private List<VRPsaving> toBeMerged;

    private int clientCount;
    double [][] distance;


    ClarkWright(VRPinstance vrp) {
	this.vrp = vrp;
	this.clientCount = vrp.getN();
	this.distance = vrp.getMatrix();
	finalRoutes = new ArrayList<VRProute>();

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

    public void keepOnlyMergeable() {
  		VRPsaving cS;
  		VRProute r1, r2;

    	for (int i = 0; i < toBeMerged.size(); i++) {
    		cS = toBeMerged.get(i);
    		r1 = cS.getFirstRoute();
    		r2 = cS.getSecondRoute();
    		if (r1.isMergeableWith(r2)) 
    			toBeMerged.remove(cS);
    	}
    }


    public void buildSolution(){
		routes = new ArrayList<VRProute>();
		this.createRouteForEachCustomer();

		toBeMerged = new ArrayList<VRPsaving>();
		int rSize = routes.size();


		while ((rSize = routes.size()) > 0) {

			for (int i = 0; i < rSize; i++) {
				VRProute r1 = routes.get(i);
				VRProute r2 = routes.get(rSize - i);

				toBeMerged.add(new VRPsaving(r1, r2)); 
				toBeMerged.add(new VRPsaving(r2, r1)); /* Should't be done if the distance matrix is symetric */
			}
			this.keepOnlyMergeable();
			Collections.sort(toBeMerged);
			VRPsaving cS = toBeMerged.get(0);
			VRProute r1 = cS.getFirstRoute();
			VRProute r2 = cS.getSecondRoute();
			r1.mergeWith(r2);
		}
    }
    
    public void printSolution(){
    	System.out.println("Solution");
    	for( int i = 0; i < routes.size(); i++){
    		System.out.println("Route " + i);
    		(routes.get(i)).toString();
    	}
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
	cw.buildSolution();
	cw.printSolution();
		

	
	
	
    }


    
    public static  void usage() {
	System.out.println("Usage: ClarkWright path/to/instance");
	System.exit(1);
    }
    
    
}
