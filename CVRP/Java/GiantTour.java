package cvrp;

import util.CustomList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import tsp.HeuristicTSP;
import tsp.FarNodeInsertHeuristicTSP;

public class GiantTour {
    private VRPinstance vrp;

    private int clientCount;
    private double[][] distance;
    private int depotPosition;

    private List<VRProute> routes;
    private VRPcustomer[] customers;

    public GiantTour(VRPinstance vrp) {
        this.vrp = vrp;
        clientCount = vrp.getN();
        distance = vrp.getMatrix();
        customers = new VRPcustomer[clientCount];
        routes = new ArrayList<VRProute>();
    }

    public double[][] getMatrix() {
        return distance;
    }

    public int getN() {
        return clientCount;
    }

    private int[] computeGT() {
        HeuristicTSP gtHeuristic = new FarNodeInsertHeuristicTSP();
        List<Integer> gtList = new ArrayList<Integer>();
        gtHeuristic.computeSolution(distance, gtList);
        int[] gt = new int[gtList.size()];
        for (int i = 0; i < gtList.size(); ++i) {
            gt[i] = gtList.get(i);
            if (gt[i] == 0)
                depotPosition = i;
        }
        return gt;
    }

    private double computeSaving(VRProute r1, VRProute r2) {
        int c1 = r1.getLastCustomer().getNumber();
        int c2 = r2.getFirstCustomer().getNumber();
        return distance[0][c1] + distance[c2][0] - distance[c1][c2];
    }

    public void buildSolution() {
        int[] gt = computeGT();
        VRProute currentRoute = null;

        for (int q = 1; q < gt.length; ++q) {
            int i = (depotPosition + q) % gt.length;
            int cnum = gt[i];

            VRPcustomer c = new VRPcustomer(cnum, vrp.getDemand(cnum));
            double cost = distance[cnum][0] + distance[0][cnum];
            VRProute r = new VRProute(c, cost, vrp.getCapacity());

            if (currentRoute == null) {
                routes.add(r);
                currentRoute = r;
            } else {
                if (currentRoute.isMergeableWith(r)) {
                    double saving = computeSaving(currentRoute, r);
                    currentRoute.mergeWith(r, saving);
                } else {
                    routes.add(r);
                    currentRoute = r;
                }
            }
        }
    }

    /****************************************************
     * Print functions to debug
     ****************************************************/
    public void printMatrix() {
        int N = getN();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.err.print(distance[i][j] + " ");
            }
            System.err.println("");
        }
    }

    public void printSolution() {
    	System.out.println("Solution : " + routes.size());
        double totalCost = 0;
        int count = 0;
    	for (int i = 0; i < routes.size(); i++) {
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
        GiantTour gt = null;

        if (args.length < 1)
            usage();

        try {
            gt = new GiantTour(new VRPinstance(args[0]));
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not Found");
	}

	System.out.println("GiantTour sur : " + args[0]);
        gt.buildSolution();
        gt.printSolution();
    }

    public static void usage() {
        System.out.println("Usage: GiantTour path/to/instance");
        System.exit(1);
    }
}
