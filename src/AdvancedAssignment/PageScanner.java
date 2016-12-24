package AdvancedAssignment;

import java.io.*;

/**
 *
 * Created by Daniël on 15-12-2016.
 * Main class that controls the scanning of the document.
 * It creates the input/output streams and creates the tag where we chech the text on
 * A tag is a specific combination of characters that helps us recognizing the different parts in the xml and
 * extract the right information at the rightime.
 */
public class PageScanner {
    public Tag startPageTag;
    public Tag textTag;
    public TitleTag titleTag;
    public InfoboxTag inforboxTag;
    public LinkTag linkTag;
    public CategoryTag categoryTag;
    public PrintWriter ac;
    public PrintWriter ai;
    public PrintWriter ao;
    public PrintWriter cp;
    public BufferedReader br;
    public int pageCounter; // 2649231 = total number of pages

    public PageScanner(String fileName) throws Exception{
        initializeTags();
        pageCounter = 0;
        startPageTag = new Tag("<page>","</page>", "");
        textTag = new Tag("<text","</text>","\t");
        ac = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Output/article-category.txt"), "UTF-8"));
        ai = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Output/article-infobox.txt"), "UTF-8"));
        ao = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Output/article-outlink.txt"), "UTF-8"));
        cp = new PrintWriter(new OutputStreamWriter(new FileOutputStream("Output/category-parent.txt"), "UTF-8"));
        br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
    }

    /**
     * Scans through all the lines untill it reaches the end of a document.
     * The structure for every round is:  Page > Tirle > Text > (Link | Infobox) | Categorie > /Text > /Page
     * Case 0. Page: Search for the beginning of a new page.
     * Case 1. TiTle: Check until Title is found. Check title for the kind of page. If normal or category page continue and set pageKind.
     * Case 2. Text: We need to find the beginning and end of the text, because only links/infoboxes and catogeries inside this block should be included.
     * Case 2. (Link | Infobox) | Categorie : Depending on the kind of page we check we look in every line for a infobox, links and or categories, until we reach end text
     * case 3. Page: look for the end of the page. Rest the needed variables and write the paga data to the files.
     * @throws Exception
     */
    public void startScanning() throws Exception{
        String line;
        int tag = 0;
        boolean inTextArea = false;
        int pageKind = 0;
        while ((line = br.readLine()) != null) {
            switch (tag){
                case 0: {tag = startPageTag.checkLineForLeftTag(line);} break;
                case 1: {
                    pageKind = titleTag.checkLineForTag(line);
                    if (pageKind > 1) {
                        tag = 2;
                    }
                } break;
                case 2: {
                    if (!inTextArea){
                        if (textTag.checkLineForLeftTag(line) == 1){
                            inTextArea = true;
                        }
                    }
                    // not with else, because an infobox can be within the line of a text tag.
                    if (inTextArea){
                        if (pageKind==2) {
                            inforboxTag.checkLineForTag(line);
                            linkTag.checkLineForTag(line);
                        }
                        categoryTag.checkLineForTag(line);
                        if (textTag.checkLineForRightTag(line) == 1){
                            tag = 3;
                        }
                    }
                } break;
                case 3: {
                    int page = startPageTag.checkLineForRightTag(line);
                    if (page == 1) {
                        writeToFill(pageKind);
                        inTextArea = false;
                        pageKind = 0;
                        tag = 0;
                        pageCounter++;
                    }
                } break;
            }
        }
        // System.out.println("Counter: " + pageCounter);
        cp.close();
        ac.close();
        ai.close();
        ao.close();
        br.close();
    }

    /**
     * These tags need to be reset after the scanning of each page.
     */
    public void initializeTags(){
        titleTag = new TitleTag("<title>","</title>","\t");
        inforboxTag = new InfoboxTag("{{Infobox ","}}", "::::;");
        linkTag = new LinkTag("\\[\\[","\\]\\]", "");
        categoryTag = new CategoryTag("\\[\\[Categorie:","\\]\\]"); // NOTE: this is DUTCH!!
    }

    /**
     * write all the data to the files.
     * The data is flushed directly so that we can limit the memory usages in the Tag objects.
     * After flusing the tags that hold information regarding a page should be reset.
     * @param pageIsCatogery : this variable is 2 or 3. 2 means where in a normal page, 3 means that it's a category page
     */
    public void writeToFill(int pageIsCatogery){
        if (pageIsCatogery == 3){
            cp.println(titleTag.title + "\t"+categoryTag.catorgeries);
            cp.flush();
        } else {
            ac.println(titleTag.title+"\t"+categoryTag.catorgeries.substring(0,categoryTag.catorgeries.length()));
            ac.flush();
            ai.println(titleTag.title+inforboxTag.infoboxData);
            ai.flush();
            ao.println(titleTag.title+"\t"+linkTag.links);
            ao.flush();
        }
        initializeTags();
    }
}
