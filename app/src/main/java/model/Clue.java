package model;

public class Clue {
    private boolean isPublic=false;//线索是否公开
    private String clueDescription;

    public Clue(String clue,boolean isPublic){
        this.clueDescription=clue;
        this.isPublic=isPublic;
    }

    public void setPublic(){
        this.isPublic=true;
    }

    public boolean getState(){
        return isPublic;
    }

    public String getDescription(){
        return clueDescription;
    }
}
