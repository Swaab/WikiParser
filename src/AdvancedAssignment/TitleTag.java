package AdvancedAssignment;

/**
 * Created by Daniël on 11-12-2016.
 */
public class TitleTag extends Tag {

    public String title;

    public TitleTag(String left, String right, String split){
        super(left,right,split);
        title = "";
    }

    /**
     * Checks the line for the title tag checks what kind of page it is. If the content can be splitted on ":", it means it is a "soecial" page.
     * Then only special page we're looking for is the 'Categorie' page.
     * If the content ends with a ":", the page is only a redirect page, so we dont need it.
     * If none of these cases are applicable, its a normal page, that we also want.
     * @param line
     * @return:  1: no title found ; 2: normal page ; 3: categorie page
     */
    public int checkLineForTag(String line){
        int startIndex = line.indexOf(leftTag);
        if (startIndex > -1){
            int contentIndex = startIndex + leftTag.length();
            String content = line.substring(contentIndex, line.indexOf(rightTag));
            String[] specialPage = content.split(":");
            if(specialPage.length > 1){
                if (specialPage[0].equals("Categorie")){ // NOTE: this is DUTCH!
                    title = specialPage[1];
                    return 3;
                }
            } else {
                if (!content.contains(":")){ // : at the end of the title makes it a redirect
                    title = content;
                    return 2;
                }
            }
        }
        return 1;
    }
}
