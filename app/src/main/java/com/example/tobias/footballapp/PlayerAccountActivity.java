package com.example.tobias.footballapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerAccountActivity extends AppCompatActivity {

    TextView name;
    TextView height;
    TextView born;
    TextView position;
    TextView overall;
    TextView country;
    TextView team;

    Button toTeamPlayers;
    Button vipPlayers;

    String currentTeam;
    String typeOfList;
    String playername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_account);

        Bundle extras = getIntent().getExtras();

        typeOfList = extras.getString("listName");

        playername = extras.getString("playername");
        if(typeOfList.equals("players")) {
            playername = playername.split(" - ")[1];
            vipPlayers = (Button) findViewById(R.id.vipButton);
            vipPlayers.setEnabled(true);
            vipPlayers.setVisibility(toTeamPlayers.VISIBLE);
        } else{
            toTeamPlayers = (Button) findViewById(R.id.playerButton);
            toTeamPlayers.setEnabled(true);
            toTeamPlayers.setVisibility(toTeamPlayers.VISIBLE);
        }

        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
//        String savedplayer = prefs.getString("players", "");
        String savedplayer = prefs.getString(typeOfList, "");
        // put all players in array by splitting on the separator
        String[] savedplayers = savedplayer.split("\\*");

        for(int i=0;i<savedplayers.length;i++){
            if(savedplayers[i].contains(playername)) {
                String q = savedplayers[i];
                Toast.makeText(this, q, Toast.LENGTH_SHORT).show();
                String[] savedPlayerStatus = savedplayers[i].split("\\?");
                fillAccount(savedPlayerStatus);
            }
        }
        if(typeOfList.equals("teams")) {
            changeTags();
        }
    }

    public void fillAccount(String[] savedPlayerStatus){
        name = (TextView) findViewById(R.id.nameText);
        height = (TextView) findViewById(R.id.heightText);
        born = (TextView) findViewById(R.id.bornText);
        position = (TextView) findViewById(R.id.positionText);
        overall = (TextView) findViewById(R.id.overallText);
        country = (TextView) findViewById(R.id.countryText);
        team = (TextView) findViewById(R.id.teamText);
        name.setText(savedPlayerStatus[0]);
        height.setText(savedPlayerStatus[1]);
        if(typeOfList.equals("players")) {
            String birthday = savedPlayerStatus[2];
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
            born.setText(Integer.toString(endYearAge)+" ("+savedPlayerStatus[2]+")");
        } else {
            born.setText(savedPlayerStatus[2]);
        }

        position.setText(savedPlayerStatus[3]);
        overall.setText(savedPlayerStatus[4]);
        country.setText(savedPlayerStatus[5]);
        team.setText(savedPlayerStatus[6]);
        currentTeam = savedPlayerStatus[0];
    }

    public void toAllTeamPlayers(View view){
        Intent intent = new Intent(this, PlayersListActivity.class);
//        intent.putExtra("listName", "teamplayers");
        intent.putExtra("listName", "players");
        intent.putExtra("currentTeam", currentTeam);
        intent.putExtra("filter", "overall");
        intent.putExtra("countryFilter", "NULL");
        intent.putExtra("enabledPos", "NULL");
        this.startActivity(intent);

    }

    public void deleteAccount(View view){
//        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
//        String savedplayer = prefs.getString(typeOfList, "");
//        String[] savedplayers = savedplayer.split("\\*");
//
//        String dbPlayers = "";
//        for(int i=0;i<savedplayers.length;i++){
//            if(!savedplayers[i].contains(playername)) {
//                dbPlayers = dbPlayers+savedplayers[i]+'*';
//            }
//        }
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(typeOfList,dbPlayers);
//        editor.commit();
//        Intent intent = new Intent(this, PlayersListActivity.class);
//        intent.putExtra("listName", "players");
//        intent.putExtra("currentTeam", "NULL");
//        this.startActivity(intent);
//        finish();

        String oldName = name.getText()+"?"+height.getText()+"?"+
                born.getText().toString().split("\\(")[1].replace(")","")+"?"+
                position.getText()+"?"+overall.getText()+"?"+country.getText()+"?"+
                team.getText()+"*";

        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
//        String players = prefs.getString("players", "");
//        players = players.replace(oldName,"");
        String vipPlayers = prefs.getString("playersVIP", "");
        vipPlayers = vipPlayers.replace(oldName,"");
//        vipPlayers = vipPlayers.replace("Léon K'ara?172?11-11-1986?CM?85?Topan?FC Wulfbosch*",
//                "Léon K'ara?172?11-11-1986?CM?88?Topan?FC Wulfbosch*");
        editor.putString("playersVIP",vipPlayers);
        editor.commit();

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
//        deleteButton.setText(oldName);
        deleteButton.setText(vipPlayers);
    }

    public void changeTags(){
        TextView naTag = (TextView) findViewById(R.id.tvName);
        TextView ctTag = (TextView) findViewById(R.id.tvCity);
        TextView yeTag = (TextView) findViewById(R.id.tvYear);
        TextView poTag = (TextView) findViewById(R.id.tvPos);
        TextView coTag = (TextView) findViewById(R.id.tvCoach);
        TextView clTag = (TextView) findViewById(R.id.tvColour);
        TextView crTag = (TextView) findViewById(R.id.tvCrest);
        naTag.setText("Team");
        ctTag.setText("City");
        yeTag.setText("Founded");
        poTag.setText("Wealth");
        coTag.setText("Coach");
        clTag.setText("Colours");
        crTag.setText("Formation");
    }

    public void setPlayer(View view){
        EditText changeET = (EditText) findViewById(R.id.changeEdit);
        changeET.setText(name.getText().toString()+"?"+height.getText().toString()+"?"+
                born.getText().toString().split("\\(")[1].split("\\)")[0]+"?"+position.getText().toString()+"?"+
                overall.getText().toString()+"?"+country.getText().toString()+"?"+
                team.getText().toString());
    }

    public void changePlayer(View view){
        EditText changeET = (EditText) findViewById(R.id.changeEdit);
        String prev = name.getText().toString()+"?"+height.getText().toString()+"?"+
                born.getText().toString().split("\\(")[1].split("\\)")[0]+"?"+position.getText().toString()+"?"+
                overall.getText().toString()+"?"+country.getText().toString()+"?"+
                team.getText().toString();

        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
//        String savedplayer = prefs.getString("players", "");
        String vip = prefs.getString("playersVIP", "");
//        String[] savedplayers = savedplayer.split("\\*");
        SharedPreferences.Editor editor = prefs.edit();
        vip = vip.replace(prev, changeET.getText().toString());
        editor.putString("playersVIP", vip);
        editor.commit();

        changeET.setText("");

    }

    public void saveAsVIP(View view){
        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
        String savedplayer = prefs.getString("players", "");
        String vip = prefs.getString("playersVIP", "");
        String[] savedplayers = savedplayer.split("\\*");
        if(vip.contains("*"+playername+"?")){
            Toast.makeText(getApplicationContext(), "Player already a VIP", Toast.LENGTH_SHORT).show();
        } else {
            for(int i = 0; i<savedplayers.length;i++) {
                if (savedplayers[i].contains(playername + "?")) {
                    vip = vip + savedplayers[i] + "*";
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("playersVIP", vip);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Player is a VIP", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }
}
