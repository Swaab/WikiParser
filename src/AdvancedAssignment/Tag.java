package AdvancedAssignment;

import java.text.SimpleDateFormat;

/**
 * Created by Daniël on 11-12-2016.
 * Most basic form of a tag.
 */
public class Tag {
    public String leftTag ;
    public String rightTag;
    public String splitString;
    public double index;

    public Tag(String leftTag, String rightTag, String splitString){
        this.leftTag = leftTag;
        this.rightTag = rightTag;
        this.splitString = splitString;
        index =0;
    }

    /**
     * With normal tags, like page and text, the left and right tag are split over a lot of lines.
     * So we make sure we can check on those seperatly.
     * @param line
     * @param tag: left or right tag
     * @return
     */
    public int checkLineForTag(String line, String tag){
        if (line.indexOf(tag) > -1){
            return 1;
        }
        return 0;
    }

    /**
     * @param line
     * @return
     */
    public int checkLineForLeftTag(String line){
        int result = checkLineForTag(line,leftTag);
        // Used for checking the progress of the scanning.
        /*if (result == 1 && leftTag.equals("<page>")) {
            index++;
            if (index % 500 == 0) {
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
                System.out.println("at index: " + index + "  " + timeStamp);
            }
        }
        */
        return result;

    }

    public int checkLineForRightTag(String line){
        return checkLineForTag(line, rightTag);
    }


}
