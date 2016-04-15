#ifndef INSERTHEURISTICTSP_H
#define INSERTHEURISTICTSP_H

#include <string>
#include "./HeuristicTSP.hpp"

using namespace std;

class InsertHeuristicTSP : public HeuristicTSP {
public:
    string getName() const override;
    double computeSolution(
        int length, const double **matrix, vector<int> &solution) override;
};

#endif //INSERTHEURISTICTSP_H
