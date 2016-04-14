package tsp;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This heuristic sorts the arcs by increasing value and
 * considers each arc in turn for insertion
 * An arc is inserted if and only if it does not create a subtour.
 * The method stops when a tour is obtained.
 */
public class DecreasingArcHeuristicTSP extends HeuristicTSP
{
    int[] toEdges;
    int[] fromEdges;

    public String getName()
    {
        return "DecreasingArc";
    }

    private boolean addingThisOneIsProblematic(Arc e)
    {
        boolean[] alreadySeen = new boolean[n];
        int start = e.getSource();
        int current = e.getTarget();

        if (toEdges[start] != -1 || fromEdges[current] != -1) {
            /** il y a déja un arc selectionné
             *  en provenance de getSource(e)
             *  ou a destination de getTarget(e)
             */
            return true;
        }

        /* on simule la selection de l'arc
           pour tester s'il introduit un cycle */
        toEdges[start] = current;
        int length = 0;
        while (current != -1 && !alreadySeen[current]) {
            alreadySeen[current] = true;
            current = toEdges[current];
            ++ length;
        }

        // on defait notre "simulation"
        toEdges[start] = -1;
        if (current == -1) {
            // aucun circuit en partant de l'arc e
            return false;
        }

        // un circuit a été trouvé, il faut vérifier sa longueur:
        return (length != n);
    }

    private List<Arc> sortedEdgeList()
    {
        List<Arc> edges = new ArrayList<Arc>(n * (n-1));
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i == j)
                    continue;
                edges.add(new Arc(i, j, distance));
            }
        }

        Collections.sort(edges);
        return edges;
    }

    private double selectEdges(List<Arc> edges)
    {
        double value = 0.0;
        /**
           nombre d'arètes selectionnés dans le grand tour
        */
        int selectedCount = 0;

        int i = 0;
        while (selectedCount < n)
        {
            if (i >= edges.size()) {
                System.err.println("selected count = "+selectedCount);
                break;
            }
            Arc e = edges.get(i);
            if (!addingThisOneIsProblematic(e)) {
                ++ selectedCount;
                toEdges[e.getSource()] = e.getTarget();
                fromEdges[e.getTarget()] = e.getSource();
                value += e.getDistance();
                System.out.println(e.getSource() +" "+e.getTarget());
            }
            ++ i;
        }
        System.exit(0);
        return value;
    }

    private void buildSolution(List<Integer> solution)
    {
        int i = 0;
        while (i < n && toEdges[i] == -1)
            ++i;

        while (solution.size() < n)
        {
            solution.add(i);
            i = toEdges[i];
        }
    }

    public double computeSolution(
        double[][] matrix, List<Integer> solution)
    {
        this.initialize(matrix);

        toEdges = new int[n];
        fromEdges = new int[n];
        for (int i = 0; i < n; ++i) {
            toEdges[i] = -1;
            fromEdges[i] = -1;
        }

        List<Arc> edges = sortedEdgeList();
        double value = selectEdges(edges);
        buildSolution(solution);

        toEdges = null;
        fromEdges = null;
        distance = null;
        return value;
    }
}

