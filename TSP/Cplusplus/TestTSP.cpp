#include <iostream>
#include "./TestTSP.hpp"
#include "./TSPData.hpp"

using namespace std;

void TestTSP::printSolution(vector<int> &solution)
{
    for (int k = 0; k < solution.size() - 1; ++k) {
        int s = solution[k];
        int t = solution[k+1];
        cout << s << " " << t << endl;
    }
    cout << solution[solution.size() - 1] << " " << solution[0] << endl;
}

void TestTSP::loadFileList(DIR *directory)
{
    struct dirent *entry;
    while ((entry = readdir(directory)) != NULL) {
        std::string filename(entry->d_name);
        loadFile(filename);
    }
}

void TestTSP::loadFile(std::string filename)
{
    if (filename.substr(filename.find_last_of(".")) == ".tsp") {
        fileList.push_back(new fstream(filename));
    } else {
        cerr << "File " << filename
             <<" not loaded (extension is not .tsp)"
             << endl;
    }
}
    
double TestTSP::avgVal(list<double> &results) {
    double avg = 0.0;
    for (auto ele = results.begin(); ele != results.end(); ++ele) {
        avg += *ele;
    }
    return avg / (double) results.size();
}

TestTSP::TestTSP()
{
    
}

/**
 * Test the heuristic procedure.
 * The method will be run on each instance previously loaded.
 * The value found for each instance is put in the list in the same
 * order the instances were entered.
 */
list<double> *TestTSP::testHeuristic(HeuristicTSP &h)
{
    list<double> *listValues = new list<double>;
    
    for (auto f = fileList.begin(); f != fileList.end(); ++f) {
        vector<int> soluce;
        TSPData data(**f);
        double val;
        
        val = h.computeSolution(data.getMatrix(), soluce);
        printSolution(soluce);
        listValues->push_back(val);
    }
    return listValues;
}

