#include <iostream>
#include <fstream>
#include <vector>
#include <list>
#include <dirent.h>
#include "./HeuristicTSP.hpp"

/**
 *
 * Utility class to test algorithms for the TSP.
 * Instances are loaded using methods load*.
 * The different methods can then be run on all these instances.
 *
 */

class TestTSP {
private:
    std::vector<std::fstream*> fileList;
                                               
    static void printSolution(std::vector<int> &solution);
    void loadFileList(DIR *directory);
    
public:
    /**
     * Load the files of the directory into the list of files
     * @param directoryName le nom du répertoire
     */
    void loadFileList(std::string directoryName);
    
    /**
     * Load the file of name fileName into the list of instances
     * @param fileName the name of the file to load
     */
    void loadFile(std::string fileName);
    

   /**
     * Test the heuristic procedure.
     * The method will be run on each instance previously loaded.
     * The value found for each instance is put in the list in the same
     * order the instances were entered.
     */
    std::list<double> *testHeuristic(HeuristicTSP &h);
        
    /**
     * Returns the average value of the list
     */
    static double avgVal(std::list<double> &results);

    TestTSP();
};
