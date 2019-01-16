import edu.rit.pjmr.Combiner;
import edu.rit.pjmr.Mapper;
import edu.rit.pjmr.TextId;
import java.util.Arrays; 
import java.util.*;
import edu.rit.util.Instance;
/**
 * This file takes the input from the input file provided and extracts the county information and
 * maps it into a combiner to further reduce it.
 *
 *
 * @author Arjun Gupta 
 * @version 04-December-2018
 */
public class DivIndexMapper extends Mapper<TextId, String, State, DivIndexVBL> {
    int year;
    Set<String> set = new HashSet<String>(); 
    DivIndexVBL z;

    /**
     * initiates the process by allocating the values
     * @param args arguments to the class file
     * @param combiner reference to the combiner used
     */
    public void start(String[] args, Combiner<State, DivIndexVBL> combiner) {
        try{
        //assign the values of min and max distance to the class
            year = Integer.parseInt(args[0]);
            
            if(!args[1].equals("")){

                String[] states = args[1].split(",");

                for(String state : states)
                    set.add(state);

            }
        }    

        catch(Exception e){
            System.out.println("error");
        }    

    }

    /**
     * maps the input into the combiner by extracting the data from the string line provided
     * @param textId the string number from the file provided to the DivIndex
     * @param s the string line from the file provided to the DivIndex
     * @param combiner reference to the combiner object used to reduce
     */
    @Override
    public void map(TextId textId, String s, Combiner<State, DivIndexVBL> combiner) {
        String[] list = s.split(",");


        String stName = list[3];
        String ctName = list[4];

        if(set.size() != 0 && !set.contains(stName)){
            return;
        }

        //store values
        int y = Integer.parseInt(list[5]);
        int age = Integer.parseInt(list[6]);
        
        
        
        //for(String state:states)
        State p = new State(stName, stName);
        State q = new State(stName, ctName);
        if (y == year && age == 0) {
            int white = Integer.parseInt(list[10]) + Integer.parseInt(list[11]);
            int black = Integer.parseInt(list[12]) + Integer.parseInt(list[13]);
            int asian = Integer.parseInt(list[16]) + Integer.parseInt(list[17]);
            int indian = Integer.parseInt(list[14]) + Integer.parseInt(list[15]);
            int hawaiian = Integer.parseInt(list[18]) + Integer.parseInt(list[19]);
            int two = Integer.parseInt(list[20]) + Integer.parseInt(list[21]);
            int total = Integer.parseInt(list[7]);

            z = new DivIndexVBL(white, black, asian, indian, hawaiian, two, total);
            combiner.add(p, z);
            combiner.add(q, z);
                
        }
        
        

    }
}