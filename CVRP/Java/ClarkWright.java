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
    double [][] distance;

    ClarkWright(VRPinstance vrp) {
        this.vrp = vrp;
        this.clientCount = vrp.getN();
        this.distance = vrp.getMatrix();
        finalRoutes = new ArrayList<VRProute>();
        mergeList = new ArrayList<VRPmerge>();
    }

    public double[][] getMatrix() {
        return	distance;
    }

    public int getN() {
        return vrp.getN();
    }

    private double computeSaving(VRProute r1, VRProute r2) {
    	int c1 = r1.getLastCustomer().getNumber();
    	int c2 = r2.getFirstCustomer().getNumber();
        double savingValue = 0;

        savingValue = r1.getCost() + r2.getCost();

        return savingValue;
    }

    public void createRouteForEachCustomer() {
        routes = new ArrayList<VRProute>();
        //On compte pas le dépot dans les clients (depot =0)
        for (int i = 1; i < this.clientCount; i++) {
            VRPcustomer c = new VRPcustomer(i,vrp.getDemand(i));
            double cost = distance[0][i] + distance[i][0];
            VRProute r = new VRProute(c, cost, vrp.getCapacity());

            routes.add(r);
            //System.out.println("Creation client numero: " + r.toString());
        }
    }

    public void initMergeList() {
    	VRPmerge merge;
    	VRProute r1, r2;
    	double saving;
    	int rSize = routes.size();

    	for (int i = 0; i <  rSize; i++) {
            for (int j = 0; j < rSize; j++) {
                if (i == j)
                    continue;
                r1 = routes.get(i);
                r2 = routes.get(j);
                saving = computeSaving(r1,r2);
                merge = new VRPmerge(r1, r2, saving);

                mergeList.add(merge);
            }
    	}
    	Collections.sort(mergeList);
    }

    public boolean doMerge(VRPmerge merge) {
    	VRProute r1 = merge.getFirstRoute();
    	VRProute r2 = merge.getSecondRoute();

    	if (r1.isMergeableWith(r2)) {
            r1.mergeWith(r2);
            return true;
    	}
    	else {
            //put route in the final route and delete all merge related to it
            return false;
    	}
    }

    public void updateMergeList(VRPmerge mergeDone, boolean successfulyMerged) {
    	VRProute r1;
    	VRProute r;

    	VRPmerge m;
    	List<VRPmerge> toBeRemoved = new ArrayList<VRPmerge>();

    	if (successfulyMerged) {
            r1 = mergeDone.getFirstRoute();
            int size = mergeList.size();
            for (int i = 0; i < size; i++) {
                m = mergeList.get(i);
                r = m.getFirstRoute();
                if (r.equals(r1)) {
                    toBeRemoved.add(m);
                }

            }
    	}

        toBeRemoved.add(mergeDone);
        int size = toBeRemoved.size();
        for (int i = 0; i < size; i++) {
            mergeList.remove(toBeRemoved.get(i));
        }
    }

    public void buildSolution(){
        this.createRouteForEachCustomer();
        this.initMergeList();
        printRoutes();

        boolean success;
        while (mergeList.size() > 0){
            System.out.println("FirstMerge : " + mergeList.get(0));
            //	printMergeList();
            success = doMerge(mergeList.get(0));
            updateMergeList(mergeList.get(0), success);
        }
    }



    /****************************************************
     * Print functions to debug
     ****************************************************/
    public void printMatrix() {
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                System.out.print(distance[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public void printSolution(){
    	System.out.println("Solution : " + routes.size());
    	for( int i = 0; i < routes.size() - 1; i++){
            System.out.println("Route " + i);
            VRProute r = routes.get(i);
            System.out.println(r);
    	}
    }

    public void printRoutes() {
    	for (int i = 0; i < routes.size(); i++) {
            System.out.println("Route num " + i);
            System.out.println((routes.get(i)).toString());
        }
    }

    public void printMergeList() {
    	int size = mergeList.size();
    	System.out.println("Merge Liste (" + size + " elements)");
    	/*for (int i = 0; i < size; i ++) {
          System.out.println("i = " + i + " -> \n" + mergeList.get(i));
          }*/
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

    public static  void usage() {
        System.out.println("Usage: ClarkWright path/to/instance");
        System.exit(1);
    }
}
