package cvrp;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class Graph {
    public class Edge implements Comparable<Edge> {
        public int destination;
        public double distance;

        public Edge(int dst, double dist) {
            destination = dst;
            distance = dist;
        }
        public int compareTo(Edge o) {
            if (this.destination == o.destination &&
                this.distance == o.distance)
                return 0;
            return 1;
        }
    };
    
    int size;
    private ArrayList<Set<Edge>> successors;

    public Graph(int n) {
        this.size = n;
        successors = new ArrayList<Set<Edge>>(n);
        for (int i = 0; i < n; ++i)
            successors.add(new ConcurrentSkipListSet<Edge>());
    }

    public void addEdge(int i, int j, double distance) {
        successors.get(i).add(new Edge(j, distance));
    }

    public int getSize() {
        return size;
    }

    /* ne me modifier pas! merci! */
    public /*const ><*/ Set<Edge> getSuccessors(int i) {
        return successors.get(i);
    }
}
