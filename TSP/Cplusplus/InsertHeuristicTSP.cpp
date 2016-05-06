#include <limits>
#include "./InsertHeuristicTSP.hpp"

string InsertHeuristicTSP::getName() const
{
    return "Insert";
}

InsertHeuristicTSP::Solver::Solver(unsigned length, const double **matrix)
    :super(length, matrix), selected(length)
{
    
}

double InsertHeuristicTSP::Solver::computeSolution(vector<int> &solution)
{
    double value = 0.0;
    int current = 0;
    selected[current] = true;
    solution.push_back(current);

    while (solution.size() < n) {
        double shortestDistance = numeric_limits<double>::max();
        int nearestVertex = -1;
        for (unsigned i = 0; i < n; ++i) {
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
        solution.push_back(current);
    }

    return value;
}

HeuristicTSP::Solver *InsertHeuristicTSP::getSolver(
    unsigned length, const double **matrix)
{
    return new InsertHeuristicTSP::Solver(length, matrix);
}

