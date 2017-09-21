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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

public class TableActivity extends AppCompatActivity {

    ListView viewList;
    String listName;
    String currentTeam;
    String filterAgeOverall;
    String countryFilter;
    String enabledPos;

    ListView lv;
    Context context;

    ArrayList prgmName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
        String tableString = prefs.getString("table", "");
        String[] table = tableString.split("\\*");

        Arrays.sort(table, Collections.reverseOrder());

        table = sortNegativeScore(table);

        String[] tablePositions = new String[table.length];
        for(int i=0;i<table.length;i++){
            tablePositions[i] = Integer.toString(i+1);
            if(table[i].split("0")[0].equals("")){
                table[i] = table[i].substring(1, table[i].length());
            }
        }

        context=this;

        lv=(ListView) findViewById(R.id.listViewCustomTable);
        lv.setAdapter(new CustomTableAdapter(this, table, tablePositions));
//            lv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));


    }

    public String[] sortNegativeScore(String[] table){
//      Points    ?   Goal Difference ?   Scored Goals    ?   Conceded Goals  ?   NAME  ?   Played Matches  ?   Win ?   Draw    ?   Lose
        for(int i = 0; i<table.length-1;i++){
            String[] tempString1 = table[i].split("\\?");
            String[] tempString2 = table[i+1].split("\\?");
            if(tempString1[0].equals(tempString2[0]) && tempString1[1].split("--")[0].contains("-")){
                if(!tempString1[1].equals(tempString2[1])){
                    String tempTable = table[i];
                    table[i] = table[i+1];
                    table[i+1]=tempTable;
                }
            }
        }
        return table;
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
}
