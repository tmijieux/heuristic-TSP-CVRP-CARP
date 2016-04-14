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

    public double computeSolution(double[][] matrix, List<Integer> solution) {
        this.initialize(matrix);
        double value = 0.0;

        return value;
    }
}
