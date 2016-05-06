package cvrp;

import util.CustomList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ClarkWright {
    private VRPinstance vrp;
    private static List<VRProute> routes;

    //Holds the routes that cannot be merged anymore
    private List<VRProute> finalRoutes;
    private List<VRPmerge> mergeList;
    private int clientCount;
    private VRPcustomer[] customers;
    private VRProute[] routeStartingByCustomer;
    private VRProute[] routeEndingByCustomer;
    double[][] distance;

    ClarkWright(VRPinstance vrp) {
        this.vrp = vrp;
        clientCount = vrp.getN();
        distance = vrp.getMatrix();
        customers = new VRPcustomer[clientCount];
        finalRoutes = new ArrayList<VRProute>();
        mergeList = new ArrayList<VRPmerge>();

        routeEndingByCustomer = new VRProute[clientCount];
        routeStartingByCustomer = new VRProute[clientCount];
    }

    public double[][] getMatrix() {
        return distance;
    }

    public int getN() {
        return clientCount;
    }

    public void createRouteForEachCustomer() {
        routes = new ArrayList<VRProute>();
        // On compte pas le dépot dans les clients (depot = 0)
        for (int i = 1; i < this.clientCount; i++) {
            VRPcustomer c = new VRPcustomer(i, vrp.getDemand(i));
            customers[i] = c;
            double cost = distance[0][i] + distance[i][0];
            VRProute r = new VRProute(c, cost, vrp.getCapacity());
            routeEndingByCustomer[i] = r;
            routeStartingByCustomer[i] = r;
            routes.add(r);
            System.err.println("Creation client numero: " + r.toString());
        }
    }

    public void initMergeList() {
    	VRPmerge merge;
    	VRProute r1, r2;
    	for (int i = 1; i < clientCount; i++) {
            for (int j = 1; j < clientCount; j++) {
                if (i == j)
                    continue;
                merge = new VRPmerge(distance, customers[i], customers[j]);
                mergeList.add(merge);
            }
    	}
    	Collections.sort(mergeList);
    }

    public boolean doMerge(VRPmerge merge) {
        int c1 = merge.getCustomer1().getNumber();
        int c2 = merge.getCustomer2().getNumber();

    	VRProute r1 = routeEndingByCustomer[c1];
        VRProute r2 = routeStartingByCustomer[c2];
        if (r1 == r2)
            return false;

    	if (r1 != null && r2 != null && r1.isMergeableWith(r2)) {
            r1.mergeWith(r2, merge.getSaving());
            routes.remove(r2);
            routeEndingByCustomer[c1] = null;
            routeStartingByCustomer[c2] = null;
            routeEndingByCustomer[r2.getLastCustomer().getNumber()] = r1; // NICKEL
            return true;
    	}
        return false;
    }

     public void buildSolution() {
        createRouteForEachCustomer();
        initMergeList();
        printRoutes();
        boolean success;
        while (mergeList.size() > 0){
            VRPmerge m = mergeList.get(0);
            System.err.println("Next merge is: " + m);
            success = doMerge(m);
            if (success)
                System.err.println(m + " -- SUCCESSFULL");
            else
                System.err.println(m + " -- FAILURE");
            mergeList.remove(0);
        }
    }

    /****************************************************
     * Print functions to debug
     ****************************************************/
    public void printMatrix() {
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                System.err.print(distance[i][j] + " ");
            }
            System.err.println("");
        }
    }

    public void printSolution(){
    	System.out.println("Solution : " + routes.size());
        double totalCost = 0;
        int count = 0;
    	for(int i = 0; i < routes.size(); i++){
            System.out.println("Route " + i);
            VRProute r = routes.get(i);
            System.out.println(r);
            totalCost += r.getCost();
            count += r.getLength();
    	}
        System.out.println("total cost: "+totalCost);
        System.out.println("Expected client count: "+(clientCount-1));
        System.out.println("total client count: "+count);
    }

    public void printRoutes() {
    	for (int i = 0; i < routes.size(); i++) {
            System.err.println("Route num " + i);
            System.err.println((routes.get(i)).toString());
        }
    }

    /*******************************************************
     * Main
     *******************************************************/
    public static void main(String args[]) {
        ClarkWright cw = null;
        
        if (args.length < 1)
            usage();
        
        try {
            cw = new ClarkWright(new VRPinstance(args[0]));
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not Found");
	}

	System.out.println("Clark & Wright sur : " + args[0]);
        cw.buildSolution();
        cw.printSolution();
    }

    public static void usage() {
        System.out.println("Usage: ClarkWright path/to/instance");
        System.exit(1);
    }
}
