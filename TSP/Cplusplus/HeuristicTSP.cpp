
#include "./HeuristicTSP.hpp"
#include "./FarNodeInsertHeuristicTSP.hpp"
#include "./InsertHeuristicTSP.hpp"
#include "./DecreasingArcHeuristicTSP.hpp"
#include <iostream>
using namespace std;

HeuristicTSP::Solver::Solver(unsigned length, const double **matrix)
    :n(length), distance(matrix)
{
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
        cerr << "No such heuristic" << endl;
        exit(EXIT_FAILURE);
        break;
    }
    return NULL;
}

double HeuristicTSP::operator () (
    unsigned length, const double **matrix, std::vector<int> &solution)
{
    double value;
    Solver *s = this->getSolver(length, matrix);
    value = s->computeSolution(solution);

    delete s;
    return value;
}
