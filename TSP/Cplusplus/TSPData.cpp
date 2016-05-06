#include "./TSPData.hpp"

using namespace std;

TSPData::TSPData(fstream &in)
{
    string s;

    size = -1; // plus facile à débugguer si bug lecture
    getline(in, s); // ligne de blabla
    getline(in, s); // ligne de blabla
    getline(in, s); // ligne de blabla
    in >> s; // blabla
    in >> size; // lecture de la taille
    cout << size << endl;

    if (size < 0)
        throw string("Probably bad .tsp file");

    matrix = new double* [size];
    for (int i = 0; i < size; i++)
        matrix[i] = new double[size];

    getline(in, s); // blabla
    getline(in, s); // blabla
    getline(in, s); // blabla

    double *x = new double[size]; // lecture des coordonnees
    double *y = new double[size];

    for (int i = 0; i < size; i++) {
        in >> s; // numero de ligne
        in >> x[i];
        in >> y[i];
        cout << x[i] << " " << y[i] << endl;
    }

    for (int i = 0; i < size; ++i) { // calcul des distances
        for (int j = 0; j < size; ++j) {
            if (i == j)
                matrix[i][j] = std::numeric_limits<double>::max();
            else
                matrix[i][j] = hypot(x[i] - x[j], y[i] - y[j]);
        }
    }

    delete[] y;
    delete[] x;
}

TSPData::~TSPData()
{
    for (int i = 0; i < size; ++i)
        delete[] matrix[i];
    delete[] matrix;
}

const double **TSPData::getMatrix() const
{
    return (const double**) matrix;
}

int TSPData::getSize() const
{
    return size;
}

double TSPData::getVal(int i, int j) const
{
    return matrix[i][j];
}

TSPData::MatrixLine TSPData::operator[](int i) const
{
    return MatrixLine(matrix, i);
}

TSPData::MatrixLine::MatrixLine(double **matrix, int line):
    line(line), matrix(matrix)
{
}

double TSPData::MatrixLine::operator[](int j)
{
    return matrix[line][j];
}

