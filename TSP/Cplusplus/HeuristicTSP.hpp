#include <vector>
#include <string>

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
    int n;
    const double **distance;
    virtual void initialize(int length, const double **matrix) final;

public:
    virtual double computeSolution(
        const double **matrix, std::vector<int> &solution) = 0;
    virtual std::string getName() = 0;
    virtual ~HeuristicTSP() {}
};
