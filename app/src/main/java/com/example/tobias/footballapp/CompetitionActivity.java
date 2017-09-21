package com.example.tobias.footballapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class CompetitionActivity extends AppCompatActivity {

    MakeCompetition competition;

    Spinner weekSpinner;

    ListView viewList;

    String currentSpinner;
    String prevSpinner;

//    Context context;

//    String[] allDays;

    String sharedTeams;
    String sharedPlayers;

    HandyTools handyTools = new HandyTools();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
//        context=do_output(CompetitionActivity;


        Bundle extras = getIntent().getExtras();
        String loadWeek = extras.getString("loadWeek");
        TextView titleView = (TextView) findViewById(R.id.weekTitleView);
        titleView.setText(loadWeek);



        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
        String teamsString = prefs.getString("teams", "");
        String[] teams = teamsString.split("\\*");


        sharedTeams = prefs.getString("teams", "");
        sharedPlayers = prefs.getString("players", "");


        competition = new MakeCompetition(teams);
        List<String> z = competition.getCompetition();
        String[] allDays = competition.setCompetition(getString(R.string.schedule));

        int q = allDays.length;

//        getWeekMatches(1);

        weekSpinner =(Spinner) findViewById(R.id.weekSpinner);
//        int swek = getIndex(weekSpinner,loadWeek);
        weekSpinner.setSelection(getIndex(weekSpinner,loadWeek));


        getWeekMatches(Integer.valueOf(loadWeek.split(" ")[1]) , allDays);
        currentSpinner = weekSpinner.getSelectedItem().toString();
        prevSpinner = weekSpinner.getSelectedItem().toString();

        spinnerLoad();

//        createTable();
    }

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }

    private class PlayWeekListener implements AdapterView.OnItemClickListener {
        // get page of player by clicking on one of the player names
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            TextView titleView = (TextView) view;
            String match = titleView.getText().toString();
//            Toast.makeText(CompetitionActivity.this, match, Toast.LENGTH_SHORT).show();

            String[] matches = match.split(" vs ");
            String matchResult = handyTools.getMatchResults(sharedTeams, sharedPlayers, matches[0], matches[1]);
            Toast.makeText(CompetitionActivity.this, matchResult, Toast.LENGTH_SHORT).show();

            updateTable(matches[0], matches[1], matchResult);

        }
    }

    public void updateTable(String teamA, String teamB, String matchResult){
        // Team A Score-Score Team B
        // Goalscorers
        String[] resultSplit = matchResult.split(" - ");
        int scoreA = Integer.valueOf(resultSplit[0].substring(resultSplit[0].length() - 1));
        int scoreB = Integer.valueOf(resultSplit[1].split(" ")[0]);
//        int scoreB = Integer.valueOf(resultSplit[1].substring(1));
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
        String tableString = prefs.getString("table", "");
        String[] table = tableString.split("\\*");
        for(int i = 0; i<table.length;i++){
            if(table[i].contains(teamA)){
                String[] teamSplit = table[i].split("\\?");
                String replaceTableString = "ERROR";
                if(scoreA > scoreB){
                    replaceTableString = updateTableString(table[i], teamSplit, 3, scoreA, scoreB);
                    tableString = tableString.replace(table[i],replaceTableString);
                } else if(scoreA == scoreB){
                    replaceTableString = updateTableString(table[i], teamSplit, 1, scoreA, scoreB);
                    tableString = tableString.replace(table[i],replaceTableString);
                } else {
                    replaceTableString = updateTableString(table[i], teamSplit, 0, scoreA, scoreB);
                    tableString = tableString.replace(table[i],replaceTableString);
                }
            } else if (table[i].contains(teamB)){
                String[] teamSplit = table[i].split("\\?");
                String replaceTableString;
                if(scoreB > scoreA){
                    replaceTableString = updateTableString(table[i], teamSplit, 3, scoreB, scoreA);
                    tableString = tableString.replace(table[i],replaceTableString);
                } else if(scoreB == scoreA){
                    replaceTableString = updateTableString(table[i], teamSplit, 1, scoreB, scoreA);
                    tableString = tableString.replace(table[i],replaceTableString);
                } else {
                    replaceTableString = updateTableString(table[i], teamSplit, 0, scoreB, scoreA);
                    tableString = tableString.replace(table[i],replaceTableString);
                }
            }
        }

        editor.putString("table",tableString);
        editor.commit();
    }

    public String updateTableString(String tableString, String[] teamSplit, int points, int goalA, int goalB){
//      Points    ?   Goal Difference ?   Scored Goals    ?   Conceded Goals  ?   NAME  ?   Played Matches  ?   Win ?   Draw    ?   Lose
        String wdlString;
        int wdlInt;
        if(points==3){
            wdlString = "--Win";
            wdlInt = 6;
        } else if (points==1){
            wdlString = "--Draw";
            wdlInt = 7;
        } else {
            wdlString = "--Lose";
            wdlInt = 8;
        }
//      Points    ?   Goal Difference ?   Scored Goals    ?   Conceded Goals  ?   NAME  ?   Played Matches  ?   Win ?   Draw    ?   Lose
        // POINTS[0]
        String replacePoints = Integer.toString(Integer.valueOf(teamSplit[0].split("--")
                [0])+points)+"--Points";
        if(replacePoints.length()==9){
            replacePoints = "0"+replacePoints;
        }
        tableString = tableString.replace(teamSplit[0],replacePoints);
        // GD[1]
        String replaceGD = Integer.toString(Integer.valueOf(teamSplit[1].split("--")
                [0])+goalA-goalB)+"--GD";
        tableString = tableString.replace(teamSplit[1],replaceGD);
        //GF[2]
        String replaceGF = Integer.toString(Integer.valueOf(teamSplit[2].split("--")
                [0])+goalA)+"--GF";
        tableString = tableString.replace(teamSplit[2],replaceGF);
        //GA[3]
        String replaceGA = Integer.toString(Integer.valueOf(teamSplit[3].split("--")
                [0])+goalB)+"--GA";
        tableString = tableString.replace(teamSplit[3],replaceGA);
        // PLAYED[5]
        String replacePlayed = Integer.toString(Integer.valueOf(teamSplit[5].split("--")
                [0])+1)+"--Played";
        tableString = tableString.replace(teamSplit[5],replacePlayed);
        // WON[6]/DRAW[7]/LOST[8]
        String replaceWDL = Integer.toString(Integer.valueOf(teamSplit[wdlInt].split("--")
                [0])+1)+wdlString;
        tableString = tableString.replace(teamSplit[wdlInt],replaceWDL);

        return tableString;
    }

    public void getWeekMatches(int num, String[] allDays){
        String[] days = allDays[num-1].split("\\?");
        int weeks = allDays.length;
        String weekString = "Choose Matchday*";
        for(int i = 1; i<weeks+1; i++) {
            weekString = weekString + "Week " + Integer.toString(i)+"*";
        }
        String[] weekArray = weekString.split("\\*");
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, weekArray);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, weekArray);

        weekSpinner.setAdapter(spinnerAdapter);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, days);
        viewList = (ListView) findViewById(R.id.matchesList);
        assert viewList != null;
        viewList.setAdapter(adapter);

        AdapterView.OnItemClickListener playWeekListener = new PlayWeekListener();

        viewList.setOnItemClickListener(playWeekListener);

    }

    public void test(){
        currentSpinner = weekSpinner.getSelectedItem().toString();
        if(!currentSpinner.equals(prevSpinner)) {
            Intent intent = new Intent(this, CompetitionActivity.class);
            intent.putExtra("loadWeek",currentSpinner);
            this.startActivity(intent);
            prevSpinner = weekSpinner.getSelectedItem().toString();
            finish();

//            String[] allDays = competition.setCompetition(getString(R.string.schedule));
//            int newWeek = Integer.valueOf(weekSpinner.getSelectedItem().toString().split(" ")[1]);
//            Toast.makeText(this, newWeek, Toast.LENGTH_SHORT).show();
//            getWeekMatches(2, allDays);
        }

    }

    public void spinnerLoad(){
//        String currSpin = weekSpinner.getSelectedItem().toString();
//        weekSpinner.setOnItemClickListener(PlayWeekListener);
//        Toast.makeText(CompetitionActivity.this, currSpin, Toast.LENGTH_SHORT).show();

        weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String msupplier=weekSpinner.getSelectedItem().toString();

//                Log.e("Selected item : ",msupplier);
//                Toast.makeText(CompetitionActivity.this, msupplier, Toast.LENGTH_SHORT).show();
                String[] allDays = competition.setCompetition(getString(R.string.schedule));
//                Intent intent = new Intent(this.CompetitionActivity.class, CompetitionActivity.class);
//                this.startActivity(intent);
//                getWeekMatches(1, allDays);
                test();
//                getWeekMatches(Integer.valueOf(msupplier), allDays);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Toast.makeText(CompetitionActivity.this, " hoi ", Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub

            }
        });

    }

}
