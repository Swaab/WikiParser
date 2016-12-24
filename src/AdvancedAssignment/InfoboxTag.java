package AdvancedAssignment;

/**
 *
 * Created by Daniël on 14-12-2016.
 */
public class InfoboxTag extends Tag{
    public boolean inInfobox;
    public String name;
    public String middleTag = "|";
    public String infoboxData;

    public InfoboxTag(String left, String right, String split){
        super(left,right,split);
        inInfobox = false;
        infoboxData = "";
    }

    /**
     * Checks a line for the start of an infobox OR for a attribute inside the infobox untill it hits the end of the infobox
     * 1. If not in the infobox check for the infobox tag
     * 2. If we are in the infobox make sure the data format is correct and add it to the infobox data.
     * @param line
:     */
    public void checkLineForTag(String line){
        if (inInfobox){
            int startIndex = line.indexOf(middleTag);
            if (startIndex > -1) {
                int contentIndex = startIndex + middleTag.length();
                String content = line.substring(contentIndex);
                String[] split = content.split("=");
                if (split.length > 1) {
                    // removes all the with space around the elements
                    infoboxData += (split[0].replaceAll("\\s","")+"::::="+split[1]).replace("::::= ","::::=");
                } else {
                    infoboxData += split[0].replaceAll("\\s","")+"::::=";
                }
                infoboxData +="::::;";
            } else {
                // Check for the end of the inbox
                startIndex = line.indexOf(rightTag);
                if (startIndex > -1) {
                    inInfobox = false;
                }
            }
        } else {
            int startIndex = line.indexOf(leftTag);
            if (startIndex > -1) {
                int contentIndex = startIndex + leftTag.length();
                name = line.substring(contentIndex);
                inInfobox = true;
                infoboxData += "\tInfobox "+ name + ":::::";
            }
        }
    }
}
