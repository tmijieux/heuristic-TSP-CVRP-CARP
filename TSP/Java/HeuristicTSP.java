package tsp;
import java.util.List;

/**
   Interface defining the behavior of a TSP heuristic

 */

public abstract class HeuristicTSP
{
    /** apply the heuristic to the TSP problem given as a matrix
     *
     * @param matrix : TSP data
     * @param solution an empty list that will be filled with the
     *        solution by the method
     * @return the value of the solution found
     */

    protected int n;
    protected double[][] distance;

    protected final void initialize(double[][] matrix)
    {
        n = matrix.length;
        distance = matrix;
    }

    public abstract double computeSolution(
        double[][] matrix, List<Integer> solution);
    public abstract String getName();
}
