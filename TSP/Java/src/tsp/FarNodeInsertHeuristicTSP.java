package tsp;
import java.util.List;

/**
 * 
 * This heuristic iteratively appends a customer
 * to the current solution until a tour is obtained
 *
 */
public class FarNodeInsertHeuristicTSP implements HeuristicTSP {
    
    class WTFException extends RuntimeException {};

    int n;
    double[][] distance;
    boolean[] selected;
    double[] score;

    int[] bestInsertPosition;
    double bestScore;
    int bestScoreVertex;
    
    int maxScoreVertex;
    double value = 0.0;
    
    public String getName()
    {
        return "FarNodeInsert";
    }
    
    private int[] mostDistantVertex()
    {
        int [] max_ij = new int [2];
        double max = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < i; ++j) {
                 if (distance[i][j] + distance[j][i] > max) {
                     max = distance[i][j] + distance[j][i];
                     max_ij[0] = i;
                     max_ij[1] = j;
                     value = max;
                 }
            }
        }
        return max_ij;
    }

    private void computeScore(List<Integer> solution)
    {
        bestScore = 0.0;
        for (int i = 0; i < n; ++i) {
            score[i] = 0.0;
            if (!selected[i]) {
                for (int k = 0; k < solution.size() - 1; ++k) {
                    int from = solution.get(k);
                    int to = solution.get(k+1);
                    
                    double localScore = 0.0;
                    double [][]d = distance;
                    
                    localScore = value - d[from][to] + d[from][i] + d[i][to];
                    
                    if (localScore > score[i]) {
                        score[i] = localScore;
                        bestInsertPosition[i] = k+1;
                    }
                }

                if (score[i] > bestScore) {
                    bestScore = score[i];
                    bestScoreVertex = i;
                }
                if (bestScore == 0)
                        throw new WTFException();

            }
        }
    }
    
    public double computeSolution(double[][] mat, List<Integer> solution)
    {
        distance = mat;
        n = mat.length;
        selected = new boolean[n];
        score = new double[n];
        bestInsertPosition = new int[n];
        
        int [] disVert = mostDistantVertex();

        solution.add(disVert[0]); selected[disVert[0]] = true;
        solution.add(disVert[1]); selected[disVert[1]] = true;
        
        System.out.println(disVert[0]);
        System.out.println(disVert[1]);
        System.out.println(value);
        
        while (solution.size() < n) {
            computeScore(solution);
            solution.add(bestInsertPosition[bestScoreVertex], bestScoreVertex);
            selected[bestScoreVertex] = true;
            value = bestScore;
            System.out.println(bestScoreVertex);
            //System.out.println("value = "+value);
        }
        return value;
    }
}
