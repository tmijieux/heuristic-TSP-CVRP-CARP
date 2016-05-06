#ifndef DECREASINGARCHEURISTICTSP_H
#define DECREASINGARCHEURISTICTSP_H

#include <string>
#include <vector>
#include "./HeuristicTSP.hpp"

using namespace std;

class DecreasingArcHeuristicTSP : public HeuristicTSP {
private:
    typedef HeuristicTSP super;
    class Solver : public HeuristicTSP::Solver {
    private:
        typedef HeuristicTSP::Solver super;
        class Arc {
        private:
            int source;
            int target;
            double distance;
        public:
            double getDistance();
            int getSource();
            int getTarget();
            operator string();
            static bool compare(Arc *a, Arc *b);
            Arc(int i, int j, const double **distance);
        };
        vector<int> toEdges;
        vector<int> fromEdges;
        vector<Arc*> sortedEdges;
        void computeSortedEdges();
        double selectEdges();
        void buildSolution(vector<int> &solution);
        bool addingThisOneIsProblematic(Arc *e);
        
    public:
        Solver(unsigned length, const double **matrix);
        double computeSolution(vector<int> &solution) override;
    };
    virtual HeuristicTSP::Solver *getSolver(
        unsigned length, const double **matrix) override;
public:
    string getName() const override;
    DecreasingArcHeuristicTSP() {}
};

#endif //DECREASINGARCHEURISTICTSP_H

