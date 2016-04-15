#ifndef TSPDATA_H
#define TSPDATA_H

/*
 * TSPData.hpp
 *
 *  Created on: 22 janv. 2015
 *      Author: fclautiaux
 */

#include <iostream>
#include <fstream>
#include <cmath>
#include <limits>

using namespace std;

class TSPData{
private :
    /* taille de la matrice (size * size) */
    int size;
    /* matrice de distances*/
    double **matrix;

public:
    class MatrixLine {
    private:
        int line;
        double  **matrix;
        MatrixLine(double **matrix, int line);
    public:
        double operator [](int j);
        friend class TSPData;
    };

    /* initialise la donnée TSP avec un fichier d'entrée */
    TSPData(fstream &in);
    ~TSPData();

    /* retourne la matrice de doubles */
    const double **getMatrix() const;

    /* retourne la taille du probleme lu */
    int getSize() const;

    /* retourne la distance entre i et j */
    double getVal(int i, int j) const;
    MatrixLine operator[](int i) const;
};


#endif //TSPDATA_H
