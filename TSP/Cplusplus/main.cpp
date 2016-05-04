//============================================================================
// Name        : TSP.cpp
// Author      : F.C.
// Version     :
// Copyright   : Free for all
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <iostream>
#include <fstream>
#include <cstdlib>

#include <unistd.h>
#include <getopt.h>

#include <string.h>
#include <errno.h>

#include "./TSPData.hpp"
#include "./TestTSP.hpp"

using namespace std;

struct option options[] = {
    { "help", 0, NULL, 'H' },
    { "heuristic", 1, NULL, 'h' },
    { "exact", 0, NULL, 'e' },
    { "lowerbound", 0, NULL, 'l' },
    { 0 },
};

static void print_help()
{
    cout << "Help: -hX heuristic X (X = 1,2 or 3)" << endl;
}

static HeuristicTSP::mode_t parse_options(int &argc, char **&argv)
{
    typedef HeuristicTSP::mode_t mode_t;
    mode_t mode = mode_t::HEURISTIC_INSERT;

    char c;
    while ((c = getopt_long(argc, argv, "hHel", options, NULL)) != -1) {
        switch (c) {
            case 'h':
                if (optarg) {
                    mode = (mode_t) atoi(optarg);
                }
                break;
            case 'H':
                print_help();
                break;
            case 'e':
                cerr << "No exact value implemented yet\n" << endl;
                exit(EXIT_FAILURE);
                break;
            case 'l':
                cerr << "No lowerbound method implemented yet\n" << endl;
                exit(EXIT_FAILURE);
                break;
            default:
                break;
        };
    }
    argc -= optind;
    argv += optind;

    return mode;
}

static void usage(const char *argv0)
{
    cerr << "usage: " << argv0 << " instance_filename.tsp"<< endl;
    exit(EXIT_FAILURE);
}

static void computeAndPrintResult(TestTSP &tt, HeuristicTSP &h)
{
    list<double> *listRes = NULL;
    listRes = tt.testHeuristic(h);
    if (listRes->size() > 0)
        cout << "Heuristic "<<h.getName()<<" "<<TestTSP::avgVal(*listRes) << endl;
    delete listRes;
}

int main(int argc, char * argv[])
{
    if (argc != 2)
        usage(argv[0]);
    TestTSP tt;
    HeuristicTSP::mode_t m = parse_options(argc, argv);

    for (int i = 0; i < argc; ++i)
        tt.loadFile(argv[i]);

    HeuristicTSP *h = HeuristicTSP::getInstance(m);
    computeAndPrintResult(tt, *h);

    delete h;

    return 0;
}

