package carp;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class CARPinstance {
	
	
	
	/**
	 * 
	 */

	private java.util.ArrayList<Arc> arcs= new java.util.ArrayList<Arc>();
	
	/* Capacity of a vehicle */
	private int capacity = -1;

	/* number of vertices */
	private int nbVertices;
	
	/* number of arcs */
	private int nbArcs;
	
	
	public CARPinstance(FileReader f) {
		Scanner scan = new Scanner(f);
		scan.nextLine(); // name
		scan.nextLine(); // comment		
		scan.next(); // vertices
		scan.next(); // :
		nbVertices = scan.nextInt();
		System.out.println("nb vertices : " + nbVertices);
		//scan.nextLine(); // passage a la ligne
		scan.next(); // nb arcs
		scan.next(); // : 
		nbArcs = scan.nextInt(); // nb arcs
		System.out.println("nb arcs = " + nbArcs);
		scan.nextLine(); // vehiculos
		scan.nextLine(); // vehiculos
		scan.nextLine(); // vehiculos
		scan.next();
		scan.next();
		capacity = scan.nextInt();
		System.out.println("Capacity = " + capacity);
		scan.nextLine(); // passage a la ligne	
		scan.nextLine(); // passage a la ligne	
		scan.nextLine(); // passage a la ligne	
		scan.nextLine(); // passage a la ligne	
		
		for(int i=0;i<nbArcs;i++){
			arcs.add(new Arc(scan.nextLine()));	
		}
		
		scan.close();
	}


	public CARPinstance(File f) throws java.io.FileNotFoundException {
		this(new FileReader(f));
	}

	public CARPinstance(String filename) throws java.io.FileNotFoundException {
		this(new FileReader(filename));
	}


	public void addArc(Arc a){
		arcs.add(a);
	}
	
	
	public java.util.ArrayList<Arc> getArcs() {
		return arcs;
	}


	public int getCapacity() {
		return capacity;
	}


	public int getNbVertices() {
		return nbVertices;
	}


	public int getNbArcs() {
		return nbArcs;
	}


	public static void main(String args[]) {

		CARPinstance instance = null;
		try {
			instance = new CARPinstance("bccm/val1A.dat");
		} catch (java.io.FileNotFoundException e) {
			System.out.println("File not found");
		}

		for(Arc a : instance.getArcs()){
			System.out.println(a);
		}
		
	}

}
