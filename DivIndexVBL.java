import edu.rit.io.InStream;
import edu.rit.io.OutStream;
import edu.rit.pj2.Tuple;
import edu.rit.pj2.Vbl;

import java.io.IOException;

/**
 * variable reduction class to reduce the diversity index reduction. it will just replace the data if the
 * key is already present with the new one considering the new data found will be the latest data

 *
 * @author Arjun Gupta 
 * @version 04-December-2018
 */
public class DivIndexVBL extends Tuple implements Vbl {

    private int white, black, asian, two, indian, hawaiian, total;
    double div;


    /*
     * constructor
     */
    DivIndexVBL() {
        this.white = 0;
        this.black = 0;
        this.asian = 0;
        this.indian = 0;
        this.hawaiian = 0;
        this.two = 0;
        this.total = 0;
        this.div = 0;
    }
    

    /**
     * constructor with arguments
     * @param val1 aphelion distance
     * @param val2 perihelion distance
     */
    DivIndexVBL(int a, int b, int c, int d, int e, int f, int g) {
        this.white = a;
        this.black = b;
        this.asian = c;
        this.indian = d;
        this.hawaiian = e;
        this.two = f;
        this.total = g;
        this.div = ((((double)this.white)*((double)this.total - (double)this.white)) + (((double)this.black)*((double)this.total - (double)this.black)) + (((double)this.asian)*((double)this.total - (double)this.asian)) + (((double)this.indian)*((double)this.total - (double)this.indian)) + (((double)this.hawaiian)*((double)this.total - (double)this.hawaiian)) + (((double)this.two)*((double)this.total - (double)this.two)))/((double)this.total*(double)this.total);
    }

    /**
     * clones and sends a clone of the object
     * @return clonned object
     */
    public Object clone() {
        try {
            DivIndexVBL vbl = (DivIndexVBL) super.clone();
            vbl.white = this.white;
            vbl.black = this.black;
            vbl.asian = this.asian;
            vbl.indian = this.indian;
            vbl.hawaiian = this.hawaiian;
            vbl.two = this.two;
            vbl.total = this.total;
            vbl.div = this.div;
            return vbl;
        } catch (Exception ex) {
            throw new RuntimeException("DivIndexVBL clone error");
        }

    }

    /**
     * write's the content of the object in stream
     * @param outStream output stream to write to
     * @throws IOException if the stream cannot be used
     */
    @Override
    public void writeOut(OutStream outStream) throws IOException {
        outStream.writeInt(white);
        outStream.writeInt(black);
        outStream.writeInt(asian);
        outStream.writeInt(indian);
        outStream.writeInt(hawaiian);
        outStream.writeInt(two);
        outStream.writeInt(total);
        outStream.writeDouble(div);
    }

    /**
     * reads the content of the object from the stream
     * @param inStream input stream to read from
     * @throws IOException if the stream cannot be used to read
     */
    @Override
    public void readIn(InStream inStream) throws IOException {
        this.white = inStream.readInt();
        this.black = inStream.readInt();
        this.asian = inStream.readInt();
        this.indian = inStream.readInt();
        this.hawaiian = inStream.readInt();
        this.two = inStream.readInt();
        this.total = inStream.readInt();
        this.div = inStream.readDouble();
    }

    /**
     * sets another VBL value to this class
     * @param vbl another DivIndex VBL
     */
    @Override
    public void set(Vbl vbl) {
        DivIndexVBL newVBL = (DivIndexVBL) vbl;
        this.white = newVBL.white;
        this.black = newVBL.black;
        this.asian = newVBL.asian;
        this.indian = newVBL.indian;
        this.hawaiian = newVBL.hawaiian;
        this.two = newVBL.two;
        this.total = newVBL.total;
        this.div = newVBL.div;
    }

    /**
     * Reduces and sets another VBL object value to current object
     * @param vbl another DivIndex VBL
     */
    @Override
    public void reduce(Vbl vbl) {
        DivIndexVBL temp = (DivIndexVBL) vbl;
        this.white += temp.white;
        this.black += temp.black;
        this.asian += temp.asian;
        this.indian += temp.indian;
        this.hawaiian += temp.hawaiian;
        this.two += temp.two;
        this.total += temp.total;
        this.div = ((((double)this.white)*((double)this.total - (double)this.white)) + (((double)this.black)*((double)this.total - (double)this.black)) + (((double)this.asian)*((double)this.total - (double)this.asian)) + (((double)this.indian)*((double)this.total - (double)this.indian)) + (((double)this.hawaiian)*((double)this.total - (double)this.hawaiian)) + (((double)this.two)*((double)this.total - (double)this.two)))/((double)this.total*(double)this.total);
    }

}