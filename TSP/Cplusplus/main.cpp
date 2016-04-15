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

#include <string.h>
#include <errno.h>

#include "./TSPData.hpp"

using namespace std;
static void usage(const char *argv0)
{
    cerr << "usage: " << argv0 << " instance_filename.tsp"<< endl;
    exit(EXIT_FAILURE);
}

int main(int argc, char * argv[])
{
    if (argc != 2)
        usage(argv[0]);
    fstream toto(argv[1], fstream::in);
    if (!toto) {
        perror(argv[1]);
        exit(EXIT_FAILURE);
    }

    try {
        TSPData data(toto);
        
    } catch (string message) {
        cerr << message << endl;
    }
    toto.close();
        
    return 0;
}
