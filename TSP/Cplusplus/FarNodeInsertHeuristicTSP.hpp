#ifndef FARNODEINSERTHEURISTICTSP_H
#define FARNODEINSERTHEURISTICTSP_H

#include <string>
#include <vector>
#include <utility>

#include "./HeuristicTSP.hpp"

using namespace std;

class FarNodeInsertHeuristicTSP : public HeuristicTSP {
private:
    vector<bool> selected;
    vector<int> bestInsertPosition;
    double bestScore;
    int bestScoreVertex;
    int maxScoreVertex;
    double value;

    pair<int,int> mostDistantVertex();
    void computeScore(vector<int> &solution);

public:
    string getName() const override;
    double computeSolution(
        int length, const double **matrix, vector<int> &solution);
    FarNodeInsertHeuristicTSP();
};

#endif //FARNODEINSERTHEURISTICTSP_H
