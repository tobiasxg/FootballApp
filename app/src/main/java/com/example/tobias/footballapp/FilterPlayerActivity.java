package com.example.tobias.footballapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static android.icu.lang.UCharacter.toLowerCase;

public class FilterPlayerActivity extends AppCompatActivity {

    CheckBox gk;
    CheckBox lb;
    CheckBox cb;
    CheckBox rb;
    CheckBox cdm;
    CheckBox cm;
    CheckBox cam;
    CheckBox lm;
    CheckBox rm;
    CheckBox lw;
    CheckBox st;
    CheckBox rw;

    EditText countryET;
//    EditText clubET;

    Spinner clubSpinner;
    Spinner countrySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_player);

        gk = (CheckBox) findViewById(R.id.checkboxGK);
        lb = (CheckBox) findViewById(R.id.checkboxLB);
        cb = (CheckBox) findViewById(R.id.checkboxCB);
        rb = (CheckBox) findViewById(R.id.checkboxRB);
        cdm = (CheckBox) findViewById(R.id.checkboxCDM);
        cm = (CheckBox) findViewById(R.id.checkboxCM);
        cam = (CheckBox) findViewById(R.id.checkboxCAM);
        lm = (CheckBox) findViewById(R.id.checkboxLM);
        rm = (CheckBox) findViewById(R.id.checkboxRM);
        lw = (CheckBox) findViewById(R.id.checkboxLW);
        st = (CheckBox) findViewById(R.id.checkboxST);
        rw = (CheckBox) findViewById(R.id.checkboxRW);

//        countryET = (EditText) findViewById(R.id.filterCountry);
//        clubET = (EditText) findViewById(R.id.filterClub);

        clubSpinner = (Spinner) findViewById(R.id.clubSpinner);
        countrySpinner = (Spinner) findViewById(R.id.countrySpinner);

        countrySpinner.setAdapter(setSpinners("players", 5));
        clubSpinner.setAdapter(setSpinners("teams", 0));
    }

    public ArrayAdapter<String> setSpinners(String listName, int num){
        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
        String sharedPrefString = prefs.getString(listName, "");
        String[] prefArray = sharedPrefString.split("\\*");
        String temp = "-NONE-*";
        for(int i = 0; i<prefArray.length; i++) {
            temp = temp + prefArray[i].split("\\?")[num] + "*";
        }
        prefArray = temp.split("\\*");
        prefArray = new HashSet<String>(Arrays.asList(prefArray)).toArray(new String[0]);
        Arrays.sort(prefArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, prefArray);
        return adapter;
    }
    
    public void searchPlayersOverall(View view){
        String enabledPos = getAllChecks();
        String club = clubSpinner.getSelectedItem().toString();
        String country = countrySpinner.getSelectedItem().toString();
        Intent intent = new Intent(this, ListActivity.class);
//        Intent intent = new Intent(this, PlayersListActivity.class);
        intent.putExtra("listName", "players");
        intent.putExtra("currentTeam", club);
        intent.putExtra("filter", "overall");
        intent.putExtra("countryFilter", country);
        intent.putExtra("enabledPos", enabledPos);
        this.startActivity(intent);
    }

    public void searchPlayersAge(View view){
        String enabledPos = getAllChecks();
        String club = clubSpinner.getSelectedItem().toString();
        String country = countrySpinner.getSelectedItem().toString();
        Intent intent = new Intent(this, ListActivity.class);
//        Intent intent = new Intent(this, PlayersListActivity.class);
        intent.putExtra("listName", "players");
        intent.putExtra("currentTeam", club);
        intent.putExtra("filter", "age");
        intent.putExtra("countryFilter", country);
        intent.putExtra("enabledPos", enabledPos);
        this.startActivity(intent);
    }

    public void searchPlayersPotential(View view){
        String enabledPos = getAllChecks();
        String club = clubSpinner.getSelectedItem().toString();
        String country = countrySpinner.getSelectedItem().toString();
        Intent intent = new Intent(this, ListActivity.class);
//        Intent intent = new Intent(this, PlayersListActivity.class);
        intent.putExtra("listName", "players");
        intent.putExtra("currentTeam", club);
        intent.putExtra("filter", "potential");
        intent.putExtra("countryFilter", country);
        intent.putExtra("enabledPos", enabledPos);
        this.startActivity(intent);
    }
    public String getAllChecks(){
        String enabledPos = "?";
        enabledPos = getCheckString(enabledPos, gk);
        enabledPos = getCheckString(enabledPos, lb);
        enabledPos = getCheckString(enabledPos, cb);
        enabledPos = getCheckString(enabledPos, rb);
        enabledPos = getCheckString(enabledPos, cdm);
        enabledPos = getCheckString(enabledPos, cm);
        enabledPos = getCheckString(enabledPos, cam);
        enabledPos = getCheckString(enabledPos, lm);
        enabledPos = getCheckString(enabledPos, rm);
        enabledPos = getCheckString(enabledPos, lw);
        enabledPos = getCheckString(enabledPos, st);
        enabledPos = getCheckString(enabledPos, rw);
        return enabledPos;
    }

    public String getCheckString(String enabledPos, CheckBox checkBox){
        if(checkBox.isChecked()){
            enabledPos = enabledPos + checkBox.getText() + "?";
        }
        return enabledPos;
    }

    public void testFilters(View view){
        Button testButton = (Button) findViewById(R.id.testFilterButton);
        testButton.setText(getAllChecks());

        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
//        String allPlayers = prefs.getString("playersVIP","");
//        editor.putString("playersVIP",allPlayers.replace("?CBM?","?CDM?"));
//        editor.commit();


//        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
//        SharedPreferences.Editor editor = prefs.edit();
        String dbTeams ="Xav Atlantic?"+    "Atlantica?"+   "1910?70?Toby Garriz?Navy Red?4-2-1-3?82*" +
                        "FC Havtar?"+       "Havtar?"+      "1910?90?Peter van der Haven?Blue Navy?4-1-4-1?83*" +
                        "FC Wulfbosch?"+    "Wulfbosch?"+   "1910?80?Nik Howls?Green?4-2-3-1?83*" +
                        "Achilles?"+        "Topato?"+      "1910?80?Giorgi Hile?White Black?4-3-3?81*" +
                        "Drakar North?"+    "Drakar?"+      "1910?80?Eluf Issakson?Black Red?4-2-3-1?82*" +
                        "Rivergod?"+        "Riveria?"+     "1910?60?Geoffry Maren?Navy Blue?4-1-2-3?81*" +
                        "Elefune?"+         "Topato?"+      "1910?70?Olaf Släger?Yellow White?5-4-1?78*" +
                        "FC Araia?"+        "Araia?"+       "1910?60?Kevin De Röver?Green Yellow?4-1-3-2?77*" +
                        "Phoenix Football?"+"Araia?"+       "1910?50?Felix Fuegon?Black Red?4-4-2?77*"+
                        "Xaris Plaza?"+     "Xaris?"+       "1910?40?Olivier Lemaster?Navy Red?4-1-4-1?78*" +
                        "Thorwoed City?"+   "Thorwoed?"+    "1910?60?William Reed?Orange Red?4-1-3-2?78*" +
                        "Full Crescent Club?"+"Lunarome?"+  "1910?30?Josuah Lemus?Black Yellow?4-1-2-3?77*" +
                        "Dragos FC?"+       "Drakar?"+      "1910?25?Mario Costa?Black Brown?4-2-3-1?75*" +
                        "Fourmation?"+      "Cater?"+       "1910?25?Ricardo Mireles?Green BLue?4-4-2?74*"+
                        "FC Hague?"+        "Hague?"+       "1910?60?Daniel D'Orange?Red White?4-4-2?75*" +
                        "Lions Club?"+      "Lionarden?"+   "1910?20?Leo Raor?Orange Black?4-3-3?75*" +
                        "Club Lux?"+        "Lux?"+         "1910?25?Philippe Edison?Yellow Black?5-3-2?74*" +
                        "Mota Noa FC?"+     "Mota Noa?"+    "1910?25?Noah Bionica?Yellow Black?4-1-4-1?74*";
        editor.putString("teams",dbTeams);
        editor.commit();
    }
}
