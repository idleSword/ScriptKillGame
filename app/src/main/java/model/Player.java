package model;

import java.util.ArrayList;

public class Player {
    private String role;//扮演角色
    private double lon,lat;//经纬度
    public ArrayList<Integer> clueList=new ArrayList<Integer>();

    public Player(String role){//构造函数
        this.role=role;
    }

    public void addClue(Integer id){
        clueList.add(id);
    }



}
