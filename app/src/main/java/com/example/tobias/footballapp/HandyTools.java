package com.example.tobias.footballapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Tobias on 20-9-2017.
 */

public class HandyTools {

    public HandyTools() {

    }

    public String getLineUp(String[] players, String[] positions){
        String lineup = "";
        List<String> list= new ArrayList<String>(Arrays.asList(players));
        for(int i=0; i<positions.length;i++){
            for(int j = 0;j<list.size();j++){
                if(list.get(j).contains("?"+positions[i]+"?")){
                    lineup = lineup+list.get(j)+"*";
//                    testTemp = testTemp+" "+listA.get(j).split("\\?")[3];
                    list.remove(j);
                    break;
                }
            }
        }
        return lineup;
    }

    public String[] filterPlayers(String[] players, String filter){
        if (!filter.equals("-NONE-")){
            String singlePlayer = "";
            for (int i = 0; i < players.length; i++) {
                if(players[i].contains(filter)){
                    singlePlayer = singlePlayer + players[i] + "*";
                }
            }
            players = singlePlayer.split("\\*");
        }
        return players;
    }

    public String checkFormation(String[] formationArray){
        String pos1="";
        String pos2="";
        String pos3="";
        String pos4="";

        if(formationArray[0].equals("3")) {
            pos1 = "LB CB RB";
        } else if(formationArray[0].equals("4")){
            pos1 = "LB CB CB RB";
        } else if (formationArray[0].equals("5")){
            pos1 = "LB CB CB CB RB";
        }

        if(formationArray[1].equals("1")) {
            pos2 = "CDM";
        } else if(formationArray[1].equals("2")){
            pos2 = "CM CM";
        } else if (formationArray[1].equals("3")){
            pos2 = "CM CM CM";
        } else if (formationArray[1].equals("4")){
            pos2 = "LM CM CM RM";
        }

        if(formationArray.length==3){
            if (formationArray[2].equals("1")) {
                pos3 = "ST";
            } else if (formationArray[2].equals("2")) {
                pos3 = "LW RW";
            } else if (formationArray[2].equals("3")) {
                pos3 = "LW ST RW";
            }
            pos4 = "";
        } else {
            if (formationArray[2].equals("1")) {
                pos3 = "CAM";
            } else if (formationArray[2].equals("2")) {
                pos3 = "LM RM";
            } else if (formationArray[2].equals("3")) {
                pos3 = "LM CAM RM";
            } else if (formationArray[2].equals("4")) {
                pos3 = "LM CM CM RM";
            }
            if (formationArray[3].equals("1")) {
                pos4 = "ST";
            } else if (formationArray[3].equals("2")) {
                pos4 = "ST ST";
            } else if (formationArray[3].equals("3")) {
                pos4 = "LW ST RW";
            }
        }

        String positions = "GK "+pos1+" "+pos2+" "+pos3+" "+pos4;
        String space = " ";
        if(pos4.equals("")){
            space="";
        }
        positions = positions+space+positions;
        return positions;
    }


    public String getMatchResults(String allTeams, String allPlayers, String teamA, String teamB){
        Random rand = new Random();

         String[] allTeamsArray = allTeams.split("\\*");
//        String team1 = allTeamsArray[rand.nextInt(allTeamsArray.length)].split("\\?")[0];
//        String team2 = allTeamsArray[rand.nextInt(allTeamsArray.length)].split("\\?")[0];
//        String[] teamAArray = allTeamsArray[rand.nextInt(allTeamsArray.length)].split("\\?");
//        String[] teamBArray = allTeamsArray[rand.nextInt(allTeamsArray.length)].split("\\?");
//        String teamA = teamAArray[0];
//        String teamB = teamBArray[0];
//        String teamA = "Xav Atlantic";
//        String teamB = "Xav Atlantic";

        int numA = 0;
        int numB = 0;

        for(int ii = 0; ii<allTeamsArray.length;ii++){
            if (allTeamsArray[ii].split("\\?").equals(teamA)){
                numA = ii;
            }
            if (allTeamsArray[ii].split("\\?").equals(teamB)){
                numB = ii;
            }
        }

        String[] teamAArray = allTeamsArray[numA].split("\\?");
        String[] teamBArray = allTeamsArray[numB].split("\\?");

//        String[] teamAArray = allTeamsArray[0].split("\\?");
//        String[] teamBArray = allTeamsArray[0].split("\\?");

        String[] allPlayersArray = allPlayers.split("\\*");
//        String[] playersA = filterPlayers(allPlayersArray, "Xav Atlantic");
//        String[] playersB = filterPlayers(allPlayersArray, "Elefune");
        String[] playersA = filterPlayers(allPlayersArray, teamA);
        String[] playersB = filterPlayers(allPlayersArray, teamB);
        String formationA = teamAArray[6];
        String formationB = teamBArray[6];

        String[] positionsA = checkFormation(formationA.split("-")).split(" ");
        String[] positionsB = checkFormation(formationB.split("-")).split(" ");
        String testyS="";
        for(int testy = 0;testy<positionsA.length;testy++){
            testyS = testyS+" "+positionsA[testy];
        }

        String lineUpA =getLineUp(playersA,positionsA);
        String lineUpB =getLineUp(playersB,positionsB);



        String[] filteredPlayersA = lineUpA.split("\\*");
        String[] filteredPlayersB = lineUpB.split("\\*");


        String temp = "";
        for(int t = 0;t<filteredPlayersA.length;t++){
            if(filteredPlayersA[t].split("\\?")[3].equals("GK")){
                temp = temp+"\n";
            }
            temp = temp+filteredPlayersA[t].split("\\?")[0]+" ";
        }

        MatchClass match = new MatchClass(filteredPlayersA, filteredPlayersB, formationA, formationB);
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        String[] scoreScorers = match.getScore().split("\\?");
        String goalScorers = "";
        try {
            goalScorers = scoreScorers[1];
        } catch(Exception e){

        }

        return teamA + " " + scoreScorers[0] + " " + teamB + "\n" + goalScorers;
    }




}
