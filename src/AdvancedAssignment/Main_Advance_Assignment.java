package AdvancedAssignment;

/**
 * Created by Daniël on 27-10-2016.
 */


public class Main_Advance_Assignment {

    public static String fileName = "nlwiki.xml";

    public static void main(String[] args) throws Exception {
        PageScanner ps = new PageScanner(fileName);
        ps.startScanning();

    }
}
