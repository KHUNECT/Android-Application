package org.androidtown.khunect_01;

public class ListViewItem {
    private String titleStr;
    private String descStr;
    private String id;


    public void setId(String _id){
        id = _id;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setDesc(String desc) {
        descStr = desc;
    }

    public String getId(){
        return this.id;
    }

    public String getTitle() {
        return this.titleStr;
    }

    public String getDesc() {
        return this.descStr;
    }
}