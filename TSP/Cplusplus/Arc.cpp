#include "./DecreasingArcHeuristicTSP.hpp"

DecreasingArcHeuristicTSP::Solver::Arc::Arc(int i, int j, const double **distance_)
    :source(i), target(j), distance(distance_[i][j])
{
}

bool DecreasingArcHeuristicTSP::Solver::Arc::compare(Arc *a, Arc *b)
{
    return a->distance < b->distance;
}

double DecreasingArcHeuristicTSP::Solver::Arc::getDistance()
{
    return distance;
}

int DecreasingArcHeuristicTSP::Solver::Arc::getSource()
{
    return source;
}

int DecreasingArcHeuristicTSP::Solver::Arc::getTarget()
{
    return target;
}

DecreasingArcHeuristicTSP::Solver::Arc::operator string()
{
    return "(" + to_string(source) + "," + to_string(target) + ")";
}
