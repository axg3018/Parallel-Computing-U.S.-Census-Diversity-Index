import edu.rit.pjmr.Reducer;

/**
 * This file reduces the diversity index data in the container and prints the data to the console as well
 *
 * @author Arjun Gupta 
 * @version 4-December-2018
 */
public class DivIndexReducer extends Reducer<State, DivIndexVBL> {
   
    double diversityIndex;
    String stateName, countyName;
    private String current = "";
    
    /** prints the data in the container to the console and counts the items available
     * @param key key in the container
     * @param value value from the container for the key
     */
    public void reduce(State key, DivIndexVBL value) {
        
        
        diversityIndex = value.div;
        stateName = key.getState();
        countyName = key.getCounty();
        if(countyName == stateName){
            System.out.printf ("%s\t\t%.5g%n", stateName, diversityIndex);
        }
           
        else
            System.out.printf ("\t%s\t%.5g%n", countyName, diversityIndex);
        

    }

}