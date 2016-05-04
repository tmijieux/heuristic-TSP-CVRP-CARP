
#include "./HeuristicTSP.hpp"
#include "./FarNodeInsertHeuristicTSP.hpp"
#include "./InsertHeuristicTSP.hpp"
#include "./DecreasingArcHeuristicTSP.hpp"

void HeuristicTSP::initialize(int length, const double **matrix)
{
    n = length;
    distance = matrix;
}

HeuristicTSP *HeuristicTSP::getInstance(HeuristicTSP::mode_t mode)
{
    switch (mode) {
    case mode_t::HEURISTIC_INSERT:
        return new InsertHeuristicTSP();
        break;
    case mode_t::HEURISTIC_DECREASING_ARC:
        return new DecreasingArcHeuristicTSP();
        break;
    case mode_t::HEURISTIC_FAR_NODE_INSERT:
        return new FarNodeInsertHeuristicTSP();
        break;
    default:
        break;
    }
    return NULL;
}
