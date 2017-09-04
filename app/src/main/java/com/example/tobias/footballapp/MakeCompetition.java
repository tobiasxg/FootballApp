package com.example.tobias.footballapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 28-8-2017.
 */

public class MakeCompetition {

    private int numTeams;
    private String[] teams;
    private List<String> matchesList = new ArrayList<>();
    String vsDays;

        public MakeCompetition(String[] teams){
            numTeams = teams.length;
//            teams = sharedTeams;
            this.teams = teams;
        }

        public List getCompetition(){
//            List<String> matchesList = new ArrayList<>();
            for(int i = 0;i<numTeams;i++){
                for(int j = i+1;j<numTeams;j++) {
                    matchesList.add(teams[i].split("\\?")[0]+" vs "+teams[j].split("\\?")[0]);
                }
            }

            return matchesList;
        }

        public String[] setCompetition(String vsDays){
            this.vsDays = vsDays;

//            vsDays.replace("vs","");
//            String q = "vsDays";

            vsDays = vsDays.replace("vs"," vs ");
//            matchesList.size();
            String vsDaysReversed = reverseDays();
            vsDays=vsDays+vsDaysReversed;
            for(int k = 18;k>0;k--){
                String kString = Integer.toString(k);
                String kStringRev = new StringBuilder(Integer.toString(k)).reverse().toString();
                String replacement = teams[k-1].split("\\?")[0];
                vsDays = vsDays.replace(kString, replacement);
                vsDays = vsDays.replace(kStringRev, replacement);
            }
            return vsDays.split("\\*");
        }

        public String reverseDays(){
            String[] vsDaysSplit = vsDays.split("\\*");
            String vsDaysReversed="";
            for(int i = 0;i<vsDaysSplit.length;i++){
                vsDaysReversed = vsDaysReversed+"*"+new StringBuilder(vsDaysSplit[i]).reverse().toString();
            }
            vsDaysReversed = vsDaysReversed.replace("sv"," vs ");
            return vsDaysReversed;
        }

}
