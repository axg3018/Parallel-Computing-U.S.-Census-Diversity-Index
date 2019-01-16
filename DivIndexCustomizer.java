import edu.rit.pjmr.Customizer;

/**
 * this file orders the output in an descending order format.
 *
 *
 * @author Arjun Gupta 
 * @version 04-December-2018
 */
public class DivIndexCustomizer extends Customizer<State, DivIndexVBL> {

    /**
     * comparator to arrange the combiner objects
     * @param key_1 key from combiner
     * @param value_1 value of the key
     * @param key_2 another key from combiner
     * @param value_2 value of the key
     * @return true if it key1 should come before key2, else false
     */
    public boolean comesBefore(State key_1, DivIndexVBL value_1, State key_2, DivIndexVBL value_2) {

        String x = key_1.getState();
        String y = key_2.getState(); 
        String p = key_1.getCounty();
        String q = key_2.getCounty();




        if (x.compareTo(y) < 0)
            return true;
        else if(x.equals(y)){
            if (x.equals(p))
                return true;
            else if(y.equals(q))
                return false;
            else if (value_1.div > value_2.div) 
                return true;
            
            else
                return false;

        }

        else
            return false;
        

        
    }
}