#include <iostream>
#include <algorithm>
#include "./DecreasingArcHeuristicTSP.hpp"

DecreasingArcHeuristicTSP::Solver::Solver(unsigned length, const double **matrix)
    :super(length, matrix), toEdges(length), fromEdges(length)
{
    for (unsigned i = 0; i < length; ++i) {
        toEdges[i] = -1;
        fromEdges[i] = -1;
    }
}

void DecreasingArcHeuristicTSP::Solver::computeSortedEdges()
{
    for (unsigned i = 0; i < n; ++i) {
        for (unsigned j = 0; j < n; ++j) {
            if (i == j)
                continue;
            sortedEdges.push_back(new Arc(i, j, distance));
        }
    }
    std::sort(sortedEdges.begin(), sortedEdges.end(), Arc::compare);
}

bool DecreasingArcHeuristicTSP::Solver::addingThisOneIsProblematic(Arc *e)
{
    vector<bool> alreadySeen(n, false);
    int start = e->getSource();
    int current = e->getTarget();

    if (toEdges[start] != -1 || fromEdges[current] != -1) {
        /** il y a déja un arc selectionné
         *  en provenance de getSource(e)
         *  ou a destination de getTarget(e)
         */
        return true;
    }
    toEdges[start] = current;
    unsigned length = 0;
    while (current != -1 && !alreadySeen[current]) {
        alreadySeen[current] = true;
        current = toEdges[current];
        ++ length;
    }
    
    // on defait notre "simulation"
    toEdges[start] = -1;
    if (current == -1) {
        // aucun circuit en partant de l'arc e --> pas de problème
        return false;
    }

    // un circuit a été trouvé, il faut vérifier sa longueur:
    return length != n;
}

double DecreasingArcHeuristicTSP::Solver::selectEdges()
{
    double value = 0.0;
    unsigned selectedCount = 0, i = 0;

    while (selectedCount < n) {
        if (i >= sortedEdges.size()) {
            cerr << "selectedCount = " << selectedCount << endl;
            break;
        }
        Arc *e = sortedEdges[i];
        if (!addingThisOneIsProblematic(e)) {
            ++ selectedCount;
            toEdges[e->getSource()] = e->getTarget();
            fromEdges[e->getTarget()] = e->getSource();
            value += e->getDistance();
        }
        ++ i;
    }
    return value;
}

void DecreasingArcHeuristicTSP::Solver::buildSolution(vector<int> &solution)
{
    unsigned i = 0;
    while (i < n && toEdges[i] == -1)
        ++i;

    while (solution.size() < n) {
        solution.push_back(i);
        i = toEdges[i];
    }
}

double DecreasingArcHeuristicTSP::Solver::computeSolution(vector<int> &solution)
{
    computeSortedEdges();
    double value = selectEdges();
    buildSolution(solution);
    return value;
}

string DecreasingArcHeuristicTSP::getName() const
{
    return "DecreasingArc";
}

HeuristicTSP::Solver *DecreasingArcHeuristicTSP::getSolver(
    unsigned length, const double **matrix)
{
    return new DecreasingArcHeuristicTSP::Solver(length, matrix);
}
