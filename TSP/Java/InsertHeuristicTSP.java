package tsp;
import java.util.List;

/**
 *
 * This heuristic iteratively appends a customer
 * to the current solution until a tour is obtained
 *
 */

public class InsertHeuristicTSP extends HeuristicTSP
{
    public String getName()
    {
        return "Insert";
    }

    public double computeSolution(double[][] matrix, List<Integer> solution)
    {
        this.initialize(matrix);
        double value = 0.0;
        boolean[] selected = new boolean[n];
        int current = 0;
        selected[current] = true;
        solution.add(current);

        while (solution.size() < n) {
            double shortestDistance = Double.MAX_VALUE;
            int nearestVertex = -1;
            for (int i = 0; i < n; ++i) {
                if (!selected[i]) {
                    if (distance[current][i] < shortestDistance) {
                        nearestVertex = i;
                        shortestDistance = distance[current][i];
                    }
                }
            }
            current = nearestVertex;
            selected[current] = true;
            value += shortestDistance;
            solution.add(current);
        }
        return value;
    }
}
