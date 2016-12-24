package AdvancedAssignment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Daniël on 15-12-2016.
 */
public class CategoryTag {

    public String catorgeries;
    public Pattern leftTag;
    public Pattern rightTag;
    public int leftTagSize;
    public int rightTagSize;

    public CategoryTag(String left, String right){
        leftTag = Pattern.compile(left);
        rightTag = Pattern.compile(right);
        leftTagSize = left.length();
        rightTagSize = right.length();
        catorgeries = "";
    }

    /**
     * The catogery tag needs to be checked with a mather, because the brackets ( [[,]] ) gave problems fot the indexOf methods regex for strings.
     * @param line
     * @return
     */
    public void checkLineForTag(String line) {
        Matcher m = leftTag.matcher(line);
        if (m.find()) {
            int start = m.start();
            line = line.substring(start);
            start = 0;
            Matcher n = rightTag.matcher(line);
            if (n.find()){
                int end = n.start();
                catorgeries += line.substring(start+leftTagSize-2,end).split("\\|")[0] + ";";
            }
        }
    }
}
