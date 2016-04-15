
#include "./HeuristicTSP.hpp"

void HeuristicTSP::initialize(int length, const double **matrix)
{
    n = length;
    distance = matrix;
}
