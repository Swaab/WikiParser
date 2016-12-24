package AdvancedAssignment;

/**
 *
 * Created by Daniël on 15-12-2016.
 */
public class LinkTag extends Tag {

    public String links;
    public String middleTag = "\\|";

    public LinkTag(String left, String right, String split){
        super(left,right,split);
        links = "";
    }

    /**
     * Splits the line on the left tag, and then split each element on the right tag. This will make sure we end up with all the content between the two tags
     * @param line
     * @return
     */
    public void checkLineForTag(String line) {
        String[] check = line.split(leftTag);
        if (check.length > 1) {
            for (int i = 1; i < check.length; i++) {
                String[] tmp = check[i].split(rightTag);
                String content = "";
                if (tmp.length > 0) {
                    content = tmp[0];
                    // link with ":" is a special link, that we dont want
                    if (!content.contains(":")) {
                        links += content.split(middleTag)[0] + ";";
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }
}
