#ifndef INSERTHEURISTICTSP_H
#define INSERTHEURISTICTSP_H

#include <string>
#include "./HeuristicTSP.hpp"

using namespace std;

class InsertHeuristicTSP : public HeuristicTSP {

private:
    class Solver : public HeuristicTSP::Solver {
    private:
        typedef HeuristicTSP::Solver super;
        vector<bool> selected;
    public:
        Solver(unsigned length, const double **matrix);
        double computeSolution(vector<int> &solution) override;
    };
    virtual HeuristicTSP::Solver *getSolver(
        unsigned length, const double **matrix) override;

public:
    string getName() const override;
    InsertHeuristicTSP() {}
};

#endif //INSERTHEURISTICTSP_H
