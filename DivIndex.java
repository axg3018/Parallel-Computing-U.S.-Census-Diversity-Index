import edu.rit.pjmr.PjmrJob;
import edu.rit.pjmr.TextFileSource;
import edu.rit.pjmr.TextId;
import java.util.*;

/**
 * Parallel Java Map Reduce (PJMR) job to analyze the census data and calculate the diversity index for every county 
 * in a given state or states for a given year. The program will also calculate the diversity index for the overall 
 * population of each state. 
 * <P>
 * Usage: java pj2 jar=<I>jar</I> threads=<I>NT</I> DivIndex <I>nodes</I> <I>file</I> <I>year</I> [ <I>state</I> ... ]
 * <P>
 * <I>jar</I> name of the JAR file containing all of the program's Java class files
 * <I>NT</I> the number of mapper threads per mapper task; if omitted, the default is 1
 * <I>nodes</I> comma-separated list of cluster node names on which to run the analysis. 
                One or more node names must be specified
 * <I>file</I> name of the file on each node's local hard disk containing the census data to be analyzed
 * <I>year</I> year to be analyzed, should be an integer between 0 and 10
 * <I>state</I> state name to be analyzed.There can be zero or more states.               
 *
 *
 * @author Arjun Gupta 
 * @version 04 December 2018
 */
public class DivIndex extends PjmrJob<TextId, String, State, DivIndexVBL> {

    /**
     * Starting point of the program
     *
     * @param args input arguments to the program
     *             comma-separated node names, the name of the file containing the data, year, and optional statename
     */
    public void main(String[] args) throws Exception {

        try{

            if (ValidateInputParameters(args)) {
                String file = args[1];
                int NT = Math.max(threads(), 1);
                String[] nodes = args[0].split(",");
                String s = "";
                Set<String> x = new HashSet<String>(); 

                for(int i = 3; i < args.length; i++){
                    s = s + "," + args[i];
                    if (!x.add(args[i])){
                        usage();
                    }
                }

                for (String node : nodes)
                    mapperTask(node)
                            .source(new TextFileSource(file))
                            .mapper(NT, DivIndexMapper.class, args[2], s);

                reducerTask()
                        .runInJobProcess()
                        .customizer(DivIndexCustomizer.class)
                        .reducer(DivIndexReducer.class);

                startJob();
            }
        }
        
        catch(Exception e){

            usage();

        }    
    }

    /**
     * Validate all the input parameters and then proceed
     * @param args input string arguments
     * @return returns true if the inputs are valid
     * @throws Exception throws exception if the input arguments are not correct.
     */
    private boolean ValidateInputParameters(String[] args) throws Exception {
        

        if (args.length < 3)
            usage();

        if (Integer.parseInt(args[2]) < 1 ||Integer.parseInt(args[2]) > 10)
            usage();


        return true;
    }

    /**
     * Print a usage message and exit.
     */
    private static void usage(){
        
        System.err.println ("Usage: java pj2 jar=<jar> threads=<NT> DivIndex <nodes> <file> <year> [ <state> ... ]");
        System.err.println ("<jar> = name of the JAR file containing all of the program's Java class files");
        System.err.println ("<NT> = the number of mapper threads per mapper task; if omitted, the default is 1");
        System.err.println ("<nodes> = comma-separated list of cluster node names on which to run the analysis. One or more node names must be specified");
        System.err.println ("<file> = name of the file on each node's local hard disk containing the census data to be analyzed");
        System.err.println ("<year> = year to be analyzed, should be an integer between 0 and 10");
        System.err.println ("<state> = state name to be analyzed. There can be zero or more state names.");
        terminate(1);
    }

}