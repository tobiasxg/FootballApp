package com.example.tobias.footballapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateTeamActivity extends AppCompatActivity {

    EditText name;
    EditText city;
    EditText year;
    EditText money;
    EditText coach;
    EditText colours;
    EditText crest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
    }

    public void placeTeam(View view){
        name = (EditText) findViewById(R.id.nameEdit);
        city = (EditText) findViewById(R.id.cityEdit);
        year = (EditText) findViewById(R.id.yearEdit);
        money = (EditText) findViewById(R.id.moneyEdit);
        coach = (EditText) findViewById(R.id.coachEdit);
        colours = (EditText) findViewById(R.id.coloursEdit);
        crest = (EditText) findViewById(R.id.crestEdit);
        name.setText("Xav Atlantic");
        city.setText("Atlantica");
        year.setText("1902");
        money.setText("70");
        coach.setText("Toby Garriz");
        colours.setText("Navy Red");
        crest.setText("1");
    }

    public void savePlayer(View view){
        name = (EditText) findViewById(R.id.nameEdit);
        city = (EditText) findViewById(R.id.cityEdit);
        year = (EditText) findViewById(R.id.yearEdit);
        money = (EditText) findViewById(R.id.moneyEdit);
        coach = (EditText) findViewById(R.id.coachEdit);
        colours = (EditText) findViewById(R.id.coloursEdit);
        crest = (EditText) findViewById(R.id.crestEdit);
        String tName = name.getText().toString();
        String tCity = city.getText().toString();
        String tYear = year.getText().toString();
        String tMoney = money.getText().toString();
        String tCoach = coach.getText().toString();
        String tColours = colours.getText().toString();
        String tCrest = crest.getText().toString();

        if(tName != null && tYear != null && tMoney != null &&
                tCity != null && tCrest != null &&
                isInteger(tYear) && isInteger(tMoney) &&
                isInteger(tCrest) &&
                tCoach != null && tColours != null){

            saveToPref(tName, tCity, tYear, tMoney, tCoach, tColours, tCrest);

            Intent intent = new Intent(this, PlayersListActivity.class);
            intent.putExtra("listName", "teams");
            intent.putExtra("currentTeam", "NULL");
            startActivity(intent);
            finish();
        }
    }

    public void saveToPref(String tName, String tCity, String tYear, String tMoney, String tCoach, String tColours, String tCrest){
        // gets players
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
        String teamsOld = prefs.getString("teams", "");

        if (!teamsOld.contains("*"+tName+"?")){
            // add movie that been saved with seperator
            String playersNew = teamsOld + tName + '?' + tCity + '?' +
                    tYear + '?' + tMoney + '?' + tCoach + '?' +
                    tColours + '?' + tCrest + '*';
            editor.putString("teams", playersNew);
            editor.commit();
            Toast.makeText(this, "Team " + tName + " added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Team exists", Toast.LENGTH_SHORT).show();
        }
    }

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
