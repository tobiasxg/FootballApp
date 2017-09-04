package com.example.tobias.footballapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CompetitionActivity extends AppCompatActivity {

    MakeCompetition competition;

    Spinner weekSpinner;

    ListView viewList;

    String currentSpinner;
    String prevSpinner;

//    Context context;

//    String[] allDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
//        context=do_output(CompetitionActivity;

        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
        String teamsString = prefs.getString("teams", "");
        String[] teams = teamsString.split("\\*");


        competition = new MakeCompetition(teams);
        List<String> z = competition.getCompetition();
        String[] allDays = competition.setCompetition(getString(R.string.schedule));

//        getWeekMatches(1);

        weekSpinner =(Spinner) findViewById(R.id.weekSpinner);
        getWeekMatches(1, allDays);
//        int num = 1;
//        String[] days = allDays[num-1].split("\\?");
//        int weeks = days.length;
//        String weekString = "";
//        for(int i = 1; i<weeks; i++) {
//            weekString = weekString + "Week " + Integer.toString(i)+"*";
//        }
//        String[] weekArray = weekString.split("\\*");
//        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, weekArray);
//
//        weekSpinner.setAdapter(spinnerAdapter);
//
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, days);
//        viewList = (ListView) findViewById(R.id.matchesList);
//        assert viewList != null;
//        viewList.setAdapter(adapter);
//
//        AdapterView.OnItemClickListener playWeekListener = new PlayWeekListener();
//
//        viewList.setOnItemClickListener(playWeekListener);

        currentSpinner = weekSpinner.getSelectedItem().toString();
        prevSpinner = weekSpinner.getSelectedItem().toString();

        spinnerLoad();
    }

    private class PlayWeekListener implements AdapterView.OnItemClickListener {
        // get page of player by clicking on one of the player names
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            TextView titleView = (TextView) view;
            String match = titleView.getText().toString();
            Toast.makeText(CompetitionActivity.this, match, Toast.LENGTH_SHORT).show();
        }
    }

    public void getWeekMatches(int num, String[] allDays){
        String[] days = allDays[num-1].split("\\?");
        int weeks = days.length;
        String weekString = "";
        for(int i = 1; i<weeks; i++) {
            weekString = weekString + "Week " + Integer.toString(i)+"*";
        }
        String[] weekArray = weekString.split("\\*");
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
            this.startActivity(intent);
            prevSpinner = weekSpinner.getSelectedItem().toString();
            finish();
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
        Toast.makeText(CompetitionActivity.this, msupplier, Toast.LENGTH_SHORT).show();
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





//    public List getCompetition(String[] teams){
//        int numTeams = teams.length;
//
//        List<String> matchesList = new ArrayList<>();
//        for(int i = 0;i<numTeams;i++){
//            for(int j = i+1;j<numTeams;j++) {
//                matchesList.add(teams[i]+" vs "+teams[j]);
//            }
//        }
//
//        return matchesList;
//
//    }


}
