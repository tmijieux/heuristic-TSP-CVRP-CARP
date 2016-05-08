package cvrp;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.PriorityQueue;

class ShortestPath {
    private Graph g;
    private int n;
    double distance[];
    int predecessor[];
    boolean visited[];
    int from;
    int to;

    private class DistanceComparator implements Comparator<Integer> {
        private double[] distance;
        public DistanceComparator(double[] distance)
        {
            this.distance = distance;
        }

        private int normalize(double i)
        {
            return (int) (i / Math.abs(i));
        }

        public int compare(Integer a, Integer b)
        {
            int a_ = a.intValue();
            int b_ = b.intValue();
            return normalize(distance[a_] - distance[b_]);
        }
    };

    public ShortestPath(Graph g, int from, int to)
    {
        this.g = g;
        n = g.getSize();
        visited = new boolean[n];
        distance = new double[n];
        predecessor = new int[n];

        for (int i = 0; i < n; ++i) {
            distance[i] = Double.MAX_VALUE;
            predecessor[i] = -1;
            visited[i] = false;
        }

        this.from = from;
        this.to = to;
    }

    private void relache(int current, int dest, double dist)
    {
        if (distance[dest] > distance[current] + dist) {
            distance[dest] = distance[current] + dist;
            predecessor[dest] = current;
        }
    }

    public List<Integer> getValue()
    {
        Comparator<Integer> comparator = new DistanceComparator(distance);
        PriorityQueue<Integer> pq;
        pq = new PriorityQueue<Integer>(n, comparator);

        distance[from] = 0;
        int current = from;
        pq.add(current);
        while (current != to && pq.size() > 0)  {
            current = pq.remove();
            visited[current] = true;

            for (Graph.Edge e : g.getSuccessors(current)) {
                if (visited[e.destination]) {
                    continue;
                }

                relache(current, e.destination, e.distance);

                // add / update the position in the priority queue
                pq.remove(e.destination);
                pq.add(e.destination);
            }
        }

        List<Integer> path = new LinkedList<Integer>();
        while (current != from) {
            path.add(0, current);
            current = predecessor[current];
        }
        return path;
    }
}
