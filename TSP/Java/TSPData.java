package tsp;

import java.io.*;
import java.util.Scanner;
import java.util.Locale;

/**
 * TPPData : utilities to load TSPData into a matrix from a TSP instance in the
 * format TSPLIB with euclidian distances
 * 
 * Data are stored as doubles. Infinite distances are stored as Double.MAX_VALUE
 */
public class TSPData {

    private double matrix[][] = null;
    // private String name=null;
    private int size;

    public TSPData(FileReader f) {
        Scanner scan = new Scanner(f);
        scan.useLocale(Locale.US);
        
        scan.nextLine(); // name
        scan.nextLine(); // comment
        scan.nextLine(); // type
        size = Integer.parseInt(scan.nextLine().split(":")[1].trim());
        scan.nextLine(); // edge type
        scan.nextLine(); // node coord section

        matrix = new double[size][];
        for (int i = 0; i < size; i++) {
            matrix[i] = new double[size];
        }

        double x[] = new double[size];
        double y[] = new double[size];

        for (int i = 0; i < size; i++) {
            scan.nextInt();
            x[i] = scan.nextDouble();
            y[i] = scan.nextDouble();
        }

        scan.close();
        System.out.println(size);
        for (int i = 0; i < size; ++i)  {
            System.out.println(x[i] + " " +y[i]);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j)
                    matrix[i][j] = Double.MAX_VALUE;
                else 
                    matrix[i][j] = Math.hypot(x[i] - x[j], y[i] - y[j]);
            }
        }
    }

    public TSPData(File f) throws java.io.FileNotFoundException {
        this(new FileReader(f));
    }

    public TSPData(String filename) throws java.io.FileNotFoundException {
        this(new FileReader(filename));
    }
	
    public TSPData(double[][] matrix, int size) {
        this.matrix = matrix;
        this.size = size;
    }

    /**
     * returns the matrix read
     * 
     * @return the matrix
     */
    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * returns the number $n$ of customers
     * 
     * @return N
     */
    public int getN() {
        return size;
    }

    public static void main(String args[]) {
    
        TSPData tspd = null;
        try {
            tspd = new TSPData("../data/instances/a280.tsp");
        } catch (java.io.FileNotFoundException e) {
            System.out.println("File not found");
        }
    
        double[][] matrix = tspd.getMatrix();
        for (int i = 0; i < tspd.getN(); i++) {
            System.out.print(matrix[i][0] + " ");
        }
    
    }

}
