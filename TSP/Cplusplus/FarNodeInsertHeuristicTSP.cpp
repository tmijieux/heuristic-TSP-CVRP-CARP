#include "./FarNodeInsertHeuristicTSP.hpp"
#include <limits>


FarNodeInsertHeuristicTSP::Solver::Solver(
    unsigned length, const double **matrix)
    :super(length, matrix), selected(length),
     bestInsertPosition(length), value(0.0)
{
    for (unsigned i = 0; i < length; ++i) {
        selected[i] = false;
        bestInsertPosition[i] = 0;
    }
}

pair<int,int> FarNodeInsertHeuristicTSP::Solver::mostDistantVertex()
{
    int max_i, max_j;
    double max = 0.;

    for (unsigned i = 0; i < n; ++i) {
        for (unsigned j = 0; j < i; ++j) {
            if (distance[i][j] + distance[j][i] > max) {
                max = distance[i][j] + distance[j][i];
                max_i = i;
                max_j = j;
                value = max;
            }
        }
    }
    return make_pair(max_i, max_j);
}

void FarNodeInsertHeuristicTSP::Solver::computeScore(vector<int> &solution)
{
    bestScore = 0.;
    for (unsigned i = 0; i < n; ++i) {
        if (!selected[i]) {
            double vertexScore = numeric_limits<double>::max();
            for (unsigned k = 0; k < solution.size() - 1; ++k) {
                int from = solution[k];
                int to = solution[k+1];

                double localScore = 0.;
                const double **d = distance;
                localScore = value - d[from][to] + d[from][i] + d[i][to];

                if (localScore < vertexScore) {
                    vertexScore = localScore;
                    bestInsertPosition[i] = k+1;
                }
            }

            if (vertexScore > bestScore) {
                bestScore = vertexScore;
                bestScoreVertex = i;
            }
            if (bestScore == 0)
                throw "WTFException";
        }
    }
}

static void vector_insert(vector<int> &v, int i, int value)
{
    vector<int>::iterator it = v.begin() + i;
    v.insert(it, value);
}

double FarNodeInsertHeuristicTSP::Solver::computeSolution(vector<int> &solution)
{
    pair<int,int> p = mostDistantVertex();

    solution.push_back(p.first); selected[p.first] = true;
    solution.push_back(p.second); selected[p.second] = true;
    solution.push_back(p.first);

    while (solution.size() < n+1) {
        computeScore(solution);
        vector_insert(
           solution, bestInsertPosition[bestScoreVertex], bestScoreVertex);
        selected[bestScoreVertex] = true;
        value = bestScore;
    }

    solution.pop_back();
    return value;
}

string FarNodeInsertHeuristicTSP::getName() const
{
    return "FarNodeInsert";
}

HeuristicTSP::Solver *FarNodeInsertHeuristicTSP::getSolver(
    unsigned length, const double **matrix)
{
    return new FarNodeInsertHeuristicTSP::Solver(length, matrix);
}
