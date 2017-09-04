package com.example.tobias.footballapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashSet;

public class CreatePlayerActivity extends AppCompatActivity {

    EditText name;
    EditText height;
    EditText born;
    EditText position;
    EditText overall;
    EditText country;
//    EditText team;

    Spinner clubSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_player);

        clubSpinner = (Spinner) findViewById(R.id.clubSpinnerPlayer);
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

    public void setTest(View view){
        name = (EditText) findViewById(R.id.nameEdit);
        height = (EditText) findViewById(R.id.heightEdit);
        born = (EditText) findViewById(R.id.bornEdit);
        position = (EditText) findViewById(R.id.positionEdit);
        overall = (EditText) findViewById(R.id.overallEdit);
        country = (EditText) findViewById(R.id.countryEdit);
//        team = (EditText) findViewById(R.id.teamEdit);
//        name.setText("Toviyah Garou");
//        height.setText("186");
//        born.setText("29/09/1996");
//        position.setText("RM");
//        overall.setText("90");
//        country.setText("Topan");
//        team.setText("Xav Atlantic");
        name.setText("Leandro Romero");
        height.setText("170");
        born.setText("31-03-1990");
        position.setText("CM");
        overall.setText("87");
        country.setText("Argentina");
//        team.setText("FC Wulfbosch");
    }

    public void savePlayer(View view){
        name = (EditText) findViewById(R.id.nameEdit);
        height = (EditText) findViewById(R.id.heightEdit);
        born = (EditText) findViewById(R.id.bornEdit);
        position = (EditText) findViewById(R.id.positionEdit);
        overall = (EditText) findViewById(R.id.overallEdit);
        country = (EditText) findViewById(R.id.countryEdit);
//        team = (EditText) findViewById(R.id.teamEdit);
        String pName = name.getText().toString();
        String pHeight = height.getText().toString();
        String pBorn = born.getText().toString();
        String pPosition = position.getText().toString();
        String pOverall = overall.getText().toString();
        String pCountry = country.getText().toString();
//        String pTeam = team.getText().toString();
        String pTeam = clubSpinner.getSelectedItem().toString();

        String[] pBirthdates = pBorn.split("-");
        if(pName != null && pBorn != null && pPosition != null &&
                pOverall != null && pTeam != null &&
                isInteger(pOverall) && isInteger(pBirthdates[0]) &&
                isInteger(pBirthdates[1]) && isInteger(pBirthdates[2]) &&
                pBirthdates[0].length() == 2 && pBirthdates[1].length() == 2 &&
                pBirthdates[1].length() == 2 &&
                pHeight != null && pCountry != null &&
                isInteger(pHeight)){

            saveToPref(pName, pHeight, pBorn, pPosition, pOverall, pCountry, pTeam);

//            Toast.makeText(this, "Player " + pName + " added", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, PlayersListActivity.class);
//            intent.putExtra("listName", "players");
//            intent.putExtra("currentTeam", "NULL");
            intent.putExtra("listName", "players");
            intent.putExtra("currentTeam", pTeam);
            intent.putExtra("filter", "overall");
            intent.putExtra("countryFilter", "-NONE-");
            intent.putExtra("enabledPos", "?");
            startActivity(intent);
            finish();
        }
    }

//    Save new player to database
    public void saveToPref(String pName, String pHeight, String pBirthdate, String pPosition,
                           String pOverall, String pCountry, String pTeam){
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
        String playersOld = prefs.getString("players", "");

        if (!playersOld.contains("*"+pName+"?")){
            String playersNew = playersOld + pName + '?' + pHeight + '?' + pBirthdate + '?'
                    + pPosition + '?' + pOverall + '?' + pCountry + '?' + pTeam + '*';
            editor.putString("players", playersNew);
            editor.commit();
            Toast.makeText(this, "Player added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Player exists", Toast.LENGTH_SHORT).show();
        }
    }

//    Check if String matches integer
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
