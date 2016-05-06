#include <iostream>
#include "./TestTSP.hpp"
#include "./TSPData.hpp"

using namespace std;

void TestTSP::printSolution(vector<int> &solution)
{
    if (solution.size() == 0) {
        cout << "empty solution" << endl;
        return;
    }
    for (unsigned k = 0; k < solution.size()-1; ++k) {
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
    size_t pos = filename.find_last_of(".");
    if (pos != string::npos && filename.substr(pos) == ".tsp") {
        fstream *f = new fstream(filename);
        if (!*f) {
            perror(filename.c_str());
            return;
        }
        fileList.push_back(f);
    } else {
        cerr << "File '" << filename
             <<"' not loaded (extension is not .tsp)"
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
list<double> *TestTSP::testHeuristic(HeuristicTSP &heur)
{
    list<double> *listValues = new list<double>;

    for (auto f = fileList.begin(); f != fileList.end(); ++f) {
        vector<int> soluce;
        TSPData data(**f);
        double val;

        val = heur(data.getSize(), data.getMatrix(), soluce);
        printSolution(soluce);
        listValues->push_back(val);
    }
    return listValues;
}
