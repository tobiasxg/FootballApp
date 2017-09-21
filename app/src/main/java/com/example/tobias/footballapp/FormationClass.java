package com.example.tobias.footballapp;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Tobias on 12-9-2017.
 */

public class FormationClass {

    List<String> gk = new ArrayList<>();
    List<String> def = new ArrayList<>();
    List<String> mid1 = new ArrayList<>();
    List<String> mid2 = new ArrayList<>();
    List<String> atk = new ArrayList<>();

    int defNum;
    int mid1Num;
    int mid2Num;
    int atkNum;

    boolean longFormation = false;

    List<String> players = new ArrayList<>();

    public FormationClass(String formation, String[] players) {
        String[] posNums = formation.split("-");
        defNum = Integer.valueOf(posNums[0]);
        mid1Num = Integer.valueOf(posNums[1]);
        if(posNums.length>3) {
            mid2Num = Integer.valueOf(posNums[2]);
            atkNum = Integer.valueOf(posNums[3]);
        } else {
            longFormation = true;
            mid2Num = 0;
            atkNum = Integer.valueOf(posNums[2]);
        }

        gk.add("GK");
        def(defNum);
        mid1(mid1Num);
        mid2(mid2Num);
        atk(atkNum);

        for(int i = 0; i<players.length;i++) {
            String[] savedPlayerStatus = players[i].split("\\?");
            players[i] = savedPlayerStatus[4] + "?" + savedPlayerStatus[0] + "?" +
                    savedPlayerStatus[3];
        }

        this.players = Arrays.asList(players);
        Arrays.sort(players);

//        fillPlayersPos();
        fillPlayersPosTemp();
    }

    public List<String> getGK(){
        return gk;
    }

    public List<String> getDef(){
        return def;
    }

    public List<String> getMid1(){
        return mid1;
    }

    public List<String> getMid2() {
        if(longFormation){
            return atk;
        } else {
            return mid2;
        }
    }

    public List<String> getAtk(){
        if(longFormation){
            return mid2;
        } else {
            return atk;
        }
    }


    public void fillPlayersPos(){
        for(int i = players.size()-1;i>=0;i--){
            String[] player = players.get(i).split("\\?");
            String pos = player[2];
            boolean ongoing = true;
            if(ongoing) {
                for (int d = 0; d < def.size(); d++) {
                    if (def.get(d).contains(pos) &&
                            !def.get(d).contains("?CHECK?")) {
                        def.set(d, def.get(d) + "?" + player[1] + "?" + player[0] + "?CHECK?");
                        ongoing = false;
                        break;
                    }
                }
            }
            if(ongoing) {
                for (int m1 = 0; m1 < mid1.size(); m1++) {
                    if (mid1.get(m1).contains(pos) &&
                            !mid1.get(m1).contains("?CHECK?")) {
                        mid1.set(m1, mid1.get(m1) + "?" + player[1] + "?" + player[0] + "?CHECK?");
                        ongoing = false;
                        break;
                    }
                }
            }
            if(ongoing){
                for(int m2 = 0;m2<mid2.size();m2++){
                    if(mid2.get(m2).contains(pos) &&
                            !mid2.get(m2).contains("?CHECK?")){
                        mid2.set(m2,mid2.get(m2)+"?"+player[1]+"?"+player[0]+"?CHECK?");
                        ongoing = false;
                        break;
                    }
                }
            }
            if(ongoing) {
                for (int a = 0; a < atk.size(); a++) {
                    if (atk.get(a).contains(pos) &&
                            !atk.get(a).contains("?CHECK?")) {
                        atk.set(a, atk.get(a) + "?" + player[1] + "?" + player[0] + "?CHECK?");
                        break;
                    }
                }
            }
            if(ongoing) {
                if (gk.get(0).contains(pos) &&
                        !gk.get(0).contains("?CHECK?")) {
                    gk.set(0,"GK" + "?" + player[1] + "?" + player[0] + "?CHECK?");
                }
            }
        }
//        String a = gk.get(0);
//        String b = def.get(0);
//        String c = def.get(1);
//        String d = def.get(2);
//        String e = def.get(3);
//        String f = mid1.get(0);
//        String g = mid1.get(1);
//        String h = mid2.get(0);
//        String i = atk.get(0);
//        String j = atk.get(1);
//        String k = atk.get(2);
//        String z = atk.get(0);

//        def.get()

    }


    public void fillPlayersPosTemp(){
        for(int i = players.size()-1;i>=0;i--){
            String[] player = players.get(i).split("\\?");
            String pos = player[2];
            boolean ongoing = true;

            for(int num = 0;num<4; num++){
                String nummedPos;
                String xx = player[1];
                boolean swek = false;
                boolean xtemp = xx.equals("Toviyah Garou");
                if(xtemp&&num==1){
                    String temp="";
                    swek = true;
                }
                if(ongoing) {
                    for (int a = 0; a < atk.size(); a++) {

//                        if(swek &&a==1){
//                            swek = true;
//                            swek = true;
//                        }
                        try{
                            nummedPos = atk.get(a).split("-")[num];
                        } catch (Exception e){
                            nummedPos = "FALSE";
//                            break;
                        }
//                        if(swek &&a==1){
//                            swek = true;
//                            swek = true;
//                        }
                        if (nummedPos.contains(pos) &&
                                atk.get(a).contains(pos) &&
                                !atk.get(a).contains("?CHECK?")) {
                            atk.set(a, atk.get(a) + "?" + player[1] + "?" + player[0] + "?CHECK?");
                            ongoing = false;
                            break;
                        }
                    }
                }

                if(ongoing){
                    for (int m2 = 0;m2<mid2.size();m2++){
                        try{
                            nummedPos = mid2.get(m2).split("-")[num];
                        } catch (Exception e){
                            nummedPos = "FALSE";
//                            break;
                        }
                        if (nummedPos.contains(pos) &&
                                mid2.get(m2).contains(pos) &&
                                !mid2.get(m2).contains("?CHECK?")){
                            mid2.set(m2,mid2.get(m2)+"?"+player[1]+"?"+player[0]+"?CHECK?");
                            ongoing = false;
                            break;
                        }
                    }
                }

                if(ongoing) {
                    for (int m1 = 0; m1 < mid1.size(); m1++) {
                        try{
                            nummedPos = mid1.get(m1).split("-")[num];
                        } catch (Exception e){
                            nummedPos = "FALSE";
                            break;
                        }
                        if (nummedPos.contains(pos) &&
                                mid1.get(m1).contains(pos) &&
                                !mid1.get(m1).contains("?CHECK?")) {
                            mid1.set(m1, mid1.get(m1) + "?" + player[1] + "?" + player[0] + "?CHECK?");
                            ongoing = false;
                            break;
                        }
                    }
                }

                if(ongoing) {
                    for (int d = 0; d < def.size(); d++) {
                        try{
                            nummedPos = def.get(d).split("-")[num];
                        } catch (Exception e){
                            nummedPos = "FALSE";
//                            break;
                        }
                        if (nummedPos.contains(pos) &&
                                def.get(d).contains(pos) &&
                                !def.get(d).contains("?CHECK?")) {
                            def.set(d, def.get(d) + "?" + player[1] + "?" + player[0] + "?CHECK?");
                            break;
                        }
                    }
                }

                if(ongoing) {
                    if (gk.get(0).contains(pos) &&
                            !gk.get(0).contains("?CHECK?")) {
                        gk.set(0,"GK" + "?" + player[1] + "?" + player[0] + "?CHECK?");
                    }
                }
            }
        }
    }

    public void def(int num){
        switch(num){
            case 5:
                def.add("LB");
                def.add("CB");
                def.add("CB");
                def.add("CB");
                def.add("RB");
                break;
            case 4:
                def.add("LB");
                def.add("CB");
                def.add("CB");
                def.add("RB");
                break;
            case 3:
                def.add("LB-CB");
                def.add("CB");
                def.add("RB-CB");
                break;
        }
    }

    public void mid1(int num){
        switch(num){
            case 5:
                mid1.add("CM-LM-CAM");
                mid1.add("CM-CDM-CAM");
                mid1.add("CM-CDM-CAM");
                mid1.add("CM-CDM-CAM");
                mid1.add("CM-RM-CAM");
                break;
            case 4:
                mid1.add("CM-LM-CAM");
                mid1.add("CM-CDM-CAM");
                mid1.add("CM-CDM-CAM");
                mid1.add("CM-RM-CAM");
                break;
            case 3:
                mid1.add("CM-CDM-CAM-LM");
                mid1.add("CM-CDM-CAM");
                mid1.add("CM-CDM-CAM-RM");
                break;
            case 2:
                mid1.add("CM-CDM");
                mid1.add("CM-CDM");
                break;
            case 1:
                mid1.add("CDM-CM");
                break;
        }
    }

    public void mid2(int num){
        switch(num){
            case 5:
                mid2.add("CM-LM-CAM");
                mid2.add("CM-CAM");
                mid2.add("CM-CAM");
                mid2.add("CM-CAM");
                mid2.add("CM-RM-CAM");
                break;
            case 4:
                mid2.add("CM-LM-CAM");
                mid2.add("CM-CAM");
                mid2.add("CM-CAM");
                mid2.add("CM-RM-CAM");
                break;
            case 3:
                mid2.add("LM-CAM");
                mid2.add("CAM-CM");
                mid2.add("RM-CAM");
                break;
            case 2:
                mid2.add("CM-CAM-LM");
                mid2.add("CM-CAM-RM");
                break;
            case 1:
                mid2.add("CAM-CM");
                break;
        }
    }

    public void atk(int num){
        switch(num){
            case 4:
                atk.add("LW-LM");
                atk.add("ST");
                atk.add("ST");
                atk.add("RW-RM");
                break;
            case 3:
                atk.add("LW-LM");
                atk.add("ST");
                atk.add("RW-RM");
                break;
            case 2:
                atk.add("ST");
                atk.add("ST");
                break;
            case 1:
                atk.add("ST");
                break;
        }
    }
}
