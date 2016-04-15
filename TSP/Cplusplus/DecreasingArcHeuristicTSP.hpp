#ifndef DECREASINGARCHEURISTICTSP_H
#define DECREASINGARCHEURISTICTSP_H

#include <string>
#include <vector>
#include "./HeuristicTSP.hpp"

using namespace std;

class DecreasingArcHeuristicTSP : public HeuristicTSP {
public:
    string getName() const override;
    double computeSolution(
        int length, const double **matrix, vector<int> &solution) override;
};

#endif //DECREASINGARCHEURISTICTSP_H

