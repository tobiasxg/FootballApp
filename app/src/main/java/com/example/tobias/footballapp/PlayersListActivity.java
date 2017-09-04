package com.example.tobias.footballapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class PlayersListActivity extends AppCompatActivity {

    ListView viewList;
    String listName;
    String currentTeam;
    String filterAgeOverall;
    String countryFilter;
    String enabledPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players_list);

        Bundle extras = getIntent().getExtras();
        listName = extras.getString("listName");
        currentTeam = extras.getString("currentTeam");
        filterAgeOverall = extras.getString("filter");
        countryFilter = extras.getString("countryFilter");
        enabledPos = extras.getString("enabledPos");

        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
        String savedPlayer = prefs.getString(listName, "");
        String[] savedPlayers = savedPlayer.split("\\*");

        savedPlayers = filterPlayers(savedPlayers, currentTeam);
        savedPlayers = filterPlayers(savedPlayers, countryFilter);
        savedPlayers = posFilter(savedPlayers, enabledPos);

        if(!savedPlayers[0].equals("")) {
            savedPlayers = sortPlayers(savedPlayers);

            // add players to arraylist to display in listview
            ArrayList<String> players = new ArrayList<>(Arrays.asList(savedPlayers));
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, players);
            viewList = (ListView) findViewById(R.id.playersList);
            assert viewList != null;
            viewList.setAdapter(adapter);


//            viewList.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));

            AdapterView.OnItemClickListener playListener = new PlayListener();

            viewList.setOnItemClickListener(playListener);
        } else {
            Toast.makeText(this, "No players met criteria", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }

    }

    private class PlayListener implements AdapterView.OnItemClickListener {
        // get page of player by clicking on one of the player names
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            TextView titleView = (TextView) view;
            String playername = titleView.getText().toString();
            Intent i = new Intent(getApplicationContext(), PlayerAccountActivity.class);
            i.putExtra("playername", playername);
            i.putExtra("listName", listName);
            startActivity(i);
        }
    }

    public String getAge(String birthday){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String[] birthdaySplit = birthday.split("-");
        String[] dateSplit = date.split("-");
        int endYearAge = Integer.valueOf(dateSplit[2])-Integer.valueOf(birthdaySplit[2]);
        if(Integer.valueOf(dateSplit[1])<Integer.valueOf(birthdaySplit[1])){
            endYearAge = endYearAge-1;
        } else if(Integer.valueOf(dateSplit[1])==Integer.valueOf(birthdaySplit[1]) &&
                Integer.valueOf(dateSplit[0])<Integer.valueOf(birthdaySplit[0])){
            endYearAge = endYearAge-1;
        }
        return Integer.toString(endYearAge);
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

    public String[] posFilter(String[] players, String filter){
        if (!filter.equals("?")){
            String singlePlayer = "";
            for (int i = 0; i < players.length; i++) {
                if(filter.contains("?"+players[i].split("\\?")[3]+"?")){
                    singlePlayer = singlePlayer + players[i] + "*";
                }
            }
            players = singlePlayer.split("\\*");
        }
        return players;
    }

    public String[] sortPlayers(String[] players){
        for (int i = 0; i < players.length; i++) {
            String[] savedPlayerStatus = players[i].split("\\?");
            String age = getAge(savedPlayerStatus[2]);
            if (filterAgeOverall.equals("age")) {
//                String age = getAge(savedPlayerStatus[2]);
                players[i] = age + " - " + savedPlayerStatus[0] + " - " + savedPlayerStatus[4] + " - " + savedPlayerStatus[3];
            } else if(filterAgeOverall.equals("overall")){
                players[i] = savedPlayerStatus[4] + " - " + savedPlayerStatus[0] + " - " + savedPlayerStatus[3];
            } else {
                int potential = Integer.valueOf(savedPlayerStatus[4]);
                if(Integer.valueOf(age)<27) {
                    potential = Integer.valueOf(savedPlayerStatus[4]) + 27 - Integer.valueOf(age);
                }
                players[i] = Integer.toString(potential) + " - " + savedPlayerStatus[0] + " - " + savedPlayerStatus[3];
            }
        }

        Arrays.sort(players);
//        if(filterAgeOverall.equals("overall")) {
        if(!filterAgeOverall.equals("age")){
            for (int i = 0; i < players.length / 2; i++) {
                String temp = players[i];
                players[i] = players[players.length - i - 1];
                players[players.length - i - 1] = temp;
            }
        } else {
            for (int j = 0; j < players.length; j++) {
                String[] savedPlayerStatus = players[j].split(" - ");
                players[j] = savedPlayerStatus[2] + " - " + savedPlayerStatus[1] +
                        " - " + savedPlayerStatus[3] + " - " + savedPlayerStatus[0]+"yr";
            }
        }
        return players;
    }
}
