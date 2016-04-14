package tsp;

class Arc implements Comparable<Arc>
{
    private int source;
    private int target;
    private double distance;
    
    public double getDistance()
    {
        return distance;
    }

    public Arc(int source, int target, double [][] dM)
    {
        this.source = source;
        this.target = target;
        distance = dM[source][target];        
    }

    public int getSource()
    {
        return source;
    }

    public int getTarget()
    {
        return target;
    }

    public String toString()
    {
        return "(" + source + "," + target + ")";
    }

    private int normalize(double lol)
    {
        return (int) (lol / Math.abs(lol));
    }
    
    public int compareTo(Arc other)
    {
        return normalize(this.getDistance() - other.getDistance());
    }
}
