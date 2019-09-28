package org.spider.sportsfete.SportDetails;

/**
 * Created by akashj on 6/2/17.
 */


import android.text.Editable;
import android.text.Html.TagHandler;

import org.xml.sax.XMLReader;

public class MyTagHandler implements TagHandler{
    boolean first= true;
    String parent=null;
    int index=1;
    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

        if(tag.equals("ul")) parent="ul";
        else if(tag.equals("ol")) parent="ol";
        if(tag.equals("li")){
            if(parent.equals("ul")){
                if(first){
                    output.append("\n\t•");
                    first= false;
                }else{
                    first = true;
                }
            }
            else{
                if(first){
                    output.append("\n\t"+index+". ");
                    first= false;
                    index++;
                }else{
                    first = true;
                }
            }
        }
    }
}