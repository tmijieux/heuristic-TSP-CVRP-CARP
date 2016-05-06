#ifndef FARNODEINSERTHEURISTICTSP_H
#define FARNODEINSERTHEURISTICTSP_H

#include <string>
#include <vector>
#include <utility>

#include "./HeuristicTSP.hpp"

using namespace std;

class FarNodeInsertHeuristicTSP : public HeuristicTSP {
private:
    typedef HeuristicTSP super;
    class Solver : public HeuristicTSP::Solver {
    private:
        typedef HeuristicTSP::Solver super;

        vector<bool> selected;
        vector<int> bestInsertPosition;

        double bestScore;
        int bestScoreVertex;
        int maxScoreVertex;
        double value;

    public:
        pair<int,int> mostDistantVertex();
        void computeScore(vector<int> &solution);
        double computeSolution(vector<int> &solution);
        Solver(unsigned length, const double **matrix);
    };

    virtual HeuristicTSP::Solver *getSolver(
        unsigned length, const double **matrix) override;

public:
    string getName() const override;
    FarNodeInsertHeuristicTSP() {}
};

#endif //FARNODEINSERTHEURISTICTSP_H
