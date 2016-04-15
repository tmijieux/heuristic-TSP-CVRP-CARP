#include <limits>
#include "./InsertHeuristicTSP.hpp"

string InsertHeuristicTSP::getName() const
{
    return "Insert";
}

double InsertHeuristicTSP::computeSolution(
    int length, const double **matrix, vector<int> &solution)
{
    this->initialize(length, matrix);
    double value = 0.0;
    bool *selected = new bool[length];
    int current = 0;
    selected[current] = true;
    solution.push_back(current);
    
    while (solution.size() < n) {
        double shortestDistance = numeric_limits<double>::max();
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
        solution.push_back(current);
    }
    
    delete selected;
    return value;
}


