package tsp;
import java.util.List;

public class MainTSP {

    private static void usage()
    {
        System.out.println("Usage: TestTSP -{h1,h2,e,l} instanceName timeLimit");
        System.exit(1);
    }
    
    private static char stringtoCode(String option)
    {
        switch (option) {
        case "-h1":            
        case "--heuristic1":
            return 'h';
        
        case "-h2":
        case "--heuristic2":
            return 'H';
        
        case "-h3":
        case "--heuristic3":
            return 'i';

        case "-e":
        case "--exact":
            return 'e';

        case "-l1":
        case "--lowerbound":
            return 'l';

        default:
            break;
        }
        return '0';
    }

    
    /** run the test
     *
     * Syntax : TestTSP -{h1,h2,e,l} instanceName timeLimit
     *
     * h1 : closest neighbor
     * h2 : arc insertion heuristic
     * e  : branch and bound
     * l  : lower bound
     *
     * Parameter timeLimit is only used when the first parameter is e
     *
     * */
    public static void main(String args[])
    {
        if (args.length < 2) {
            usage();
        }

        List<Double> listRes; // list of results
        TestTSP tt = new TestTSP();
        tt.loadFile(args[1]);
        
        switch (stringtoCode(args[0])) {
        case 'h' : 
            computeAndPrintResult(tt, new InsertHeuristicTSP());
            break;
        case 'H' : 
            computeAndPrintResult(tt, new DecreasingArcHeuristicTSP());
            break;
        case 'i' : 
            computeAndPrintResult(tt, new FarNodeInsertHeuristicTSP());
            break;
        default :  // error
            usage();
        }
    }

    private static void computeAndPrintResult(TestTSP tt, HeuristicTSP h)
    {
        List<Double> listRes;
        listRes = tt.testHeuristic(h);
        System.out.println(
            "Heuristic "+h.getName()+": "+
            TestTSP.avgVal(listRes) + " on average"
        );
    }

}

