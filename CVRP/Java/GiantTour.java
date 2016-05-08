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
    private int n;
    private double[][] distance;
    private int capacity;

    private List<VRProute> routes;
    private VRPcustomer[] customers;

    private int demand(int i) {
        return vrp.getDemand(i);
    }

    public GiantTour(VRPinstance vrp) {
        this.vrp = vrp;
        n = clientCount = vrp.getN();
        distance = vrp.getMatrix();
        customers = new VRPcustomer[clientCount];
        routes = new ArrayList<VRProute>();
        capacity = vrp.getCapacity();
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
        int[] v = new int[gtList.size()];

        int depotPosition = -1;
        for (int i = 0; i < gtList.size(); ++i) {
            gt[i] = gtList.get(i);
            if (gt[i] == 0)
                depotPosition = i;
        }

        for (int i = 0; i < gt.length; ++i)
            v[i] = gt[(i + depotPosition) % gt.length];

        return v;
    }

    private double computeSaving(VRProute r1, VRProute r2) {
        return new VRPmerge(
            distance,
            r1.getLastCustomer(),
            r2.getFirstCustomer()
        ).getSaving();
    }

    private void buildGraph(int v[], Graph g) {
        double routeDemand[][] = new double[n][];
        double cost[][] = new double[n][];

        for (int i = 0; i < n; ++i) {
            routeDemand[i] = new double[n];
            cost[i] = new double[n];
        }

        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                if (i == j) {
                    routeDemand[i][j] = demand(v[i]);
                    cost[i][j] = distance[0][v[i]];

                } else { // j > i
                    routeDemand[i][j] = routeDemand[i][j-1] + demand(v[j]);

                    cost[i][j] = cost[i][j-1] + distance[v[j-1]][v[j]] +
                        distance[v[j]][0] - distance[v[j-1]][0];

                    if (routeDemand[i][j] <= capacity)
                        g.addEdge(v[i], v[j], cost[i][j]);
                }
            }
        }
    }

    public void buildSolution() {
        int[] v = computeGT();
        Graph g = new Graph(v.length);
        buildGraph(v, g);

        for (int i = 0; i < v.length; ++i)
            System.err.println("v["+i+"] = " + v[i]);

        List<Integer> shortestPath;
        shortestPath = new ShortestPath(g, v[0], v[n-1]).getValue();

        for (int i = 0; i < shortestPath.size(); ++i)
            System.err.println("shortestPath["+i+"] = " + shortestPath.get(i));

        int k = 1;
        while (shortestPath.size() > 0) {
            VRProute r = new VRProute(vrp.getCapacity());
            int next = shortestPath.remove(0);
            while (v[k] != next) {
                r.addCustomer(v[k], distance, vrp);
                ++ k;
            }

            r.addCustomer(v[k], distance, vrp);
            ++ k;

            routes.add(r);
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
