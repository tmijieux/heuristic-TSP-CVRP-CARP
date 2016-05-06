#ifndef HEURISTICTSP_H
#define HEURISTICTSP_H

#include <vector>
#include <string>
using namespace std;

/**
 * Interface defining the behavior of a TSP heuristic
 */

class HeuristicTSP {
    /** apply the heuristic to the TSP problem given as a matrix
     *
     * @param matrix : TSP data
     * @param solution an empty list that will be filled with the
     *        solution by the method
     * @return the value of the solution found
     */

protected:
    class Solver {
    protected:
        unsigned n; // problem size
        const double **distance;
    public:
        virtual double computeSolution(vector<int> &solution) = 0;
        Solver(unsigned length, const double **matrix);
        virtual ~Solver() {}
    };
    virtual Solver *getSolver(unsigned length, const double **matrix) = 0;

public:
    enum mode_t {
        HEURISTIC_INSERT = 1,
        HEURISTIC_DECREASING_ARC = 2,
        HEURISTIC_FAR_NODE_INSERT = 3,
    };

    double operator () (
        unsigned length, const double **matrix, std::vector<int> &solution);

    virtual std::string getName() const = 0;
    virtual ~HeuristicTSP() {}
    HeuristicTSP() {}

    static HeuristicTSP *getInstance(mode_t mode);
};

#endif //HEURISTICTSP_H
