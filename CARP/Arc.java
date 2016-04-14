package carp;

import java.util.Scanner;

public class Arc {

	private int source;
	private int target;
	private int cost;
	private int demand;

	public int getSource() {
		return source;
	}

	public int getTarget() {
		return target;
	}

	public int getCost() {
		return cost;
	}

	public int getDemand() {
		return demand;
	}

	public Arc(int source, int target, int cost, int demand) {
		this.source = source;
		this.target = target;
		this.cost = cost;
		this.demand = demand;
	}

	public Arc(String line) {
		Scanner s = new Scanner(line);
		// s.useDelimiter(Pattern.compile("[\\D+]"));
		while (!s.hasNextInt())
			s.next();
		source = s.nextInt();
		while (!s.hasNextInt())
			s.next();
		target = s.nextInt();
		while (!s.hasNextInt())
			s.next();
		cost = s.nextInt();
		while (!s.hasNextInt())
			s.next();
		demand = s.nextInt();
		s.close();
	}

	@Override
	public String toString() {
		return "(" + source + "," + target + ")\tc = " + cost + "\td = " + demand;
	}

}
