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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    ListView viewList;
    String listName;
    String currentTeam;
    String filterAgeOverall;
    String countryFilter;
    String enabledPos;

    ListView lv;
    Context context;

    ArrayList prgmName;
    public static int[] prgmImages={R.drawable.achilles,R.drawable.xav_atlantic,R.drawable.elefune,
            R.drawable.fc_havtar,R.drawable.drakar_north,R.drawable.rivergod,
            R.drawable.fc_wulfbosch,R.drawable.lions_club,R.drawable.thorwoed_city,R.drawable.achilles,R.drawable.xav_atlantic,R.drawable.elefune,
            R.drawable.fc_havtar,R.drawable.drakar_north,R.drawable.rivergod,
            R.drawable.fc_wulfbosch,R.drawable.lions_club,R.drawable.thorwoed_city};
    public static String[] prgmNameList={"Achilles","Xav Atlantic","Elefune","FC Havtar",
            "Drakar North", "Rivergod", "FC Wulfbosch", "Lions Club","Thorwoed City","Achilles","Xav Atlantic","Elefune","FC Havtar",
            "Drakar North", "Rivergod", "FC Wulfbosch", "Lions Club","Thorwoed City"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle extras = getIntent().getExtras();
        listName = extras.getString("listName");
        currentTeam = extras.getString("currentTeam");
        filterAgeOverall = extras.getString("filter");
        countryFilter = extras.getString("countryFilter");
        enabledPos = extras.getString("enabledPos");


        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);
        String savedPlayer = prefs.getString(listName, "");
        String[] savedPlayers = savedPlayer.split("\\*");


        String teamString = prefs.getString("teams", "");
        setSortedTextView(currentTeam, teamString);

        savedPlayers = filterPlayers(savedPlayers, currentTeam);
        savedPlayers = filterPlayers(savedPlayers, countryFilter);
        savedPlayers = posFilter(savedPlayers, enabledPos);

        if(!savedPlayers[0].equals("")) {
            savedPlayers = sortPlayers(savedPlayers);

            String tempShirtsString = "";
            for(int z=0;z<savedPlayers.length;z++){
                String[] tempSinglePlayer = savedPlayers[z].split("\\?");
                String das = tempSinglePlayer[0];
                savedPlayers[z] = tempSinglePlayer[0];
                tempShirtsString=tempShirtsString+tempSinglePlayer[tempSinglePlayer.length-1]+"?";//nr6
            }

            List<Integer> shirtIDList = new ArrayList<>();
//            tempShirtsString = tempShirtsString.toLowerCase().replace(" ","_");
            String[] shirtsArray = tempShirtsString.split("\\?");
            for(int s=0;s<shirtsArray.length;s++){
//                String q = shirtsArray[s];
                shirtIDList.add(getShirts(shirtsArray[s]));
            }

//            Integer[] shirtList = shirtIDList.toArray(new Integer[shirtIDList.size()]);

            int[] shirtArray = toIntArray(shirtIDList);
//            int x = savedPlayers.length;
//            int y = shirtArray.length;
//            int z = 1;

//            // add players to arraylist to display in listview
//            ArrayList<String> players = new ArrayList<>(Arrays.asList(savedPlayers));
//            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, players);
//            viewList = (ListView) findViewById(R.id.playersList);
//            assert viewList != null;
//            viewList.setAdapter(adapter);
//
//
////            viewList.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));
//
//            AdapterView.OnItemClickListener playListener = new PlayersListActivity.PlayListener();
//
//            viewList.setOnItemClickListener(playListener);


            String a = savedPlayers[0];

            context=this;

            lv=(ListView) findViewById(R.id.listViewCustom);
            lv.setAdapter(new CustomAdapter(this, savedPlayers,shirtArray));
//            lv.setAdapter(new CustomAdapter(this, prgmNameList,prgmImages));

        } else {
            Toast.makeText(this, "No players met criteria", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }

    }

    int[] toIntArray(List<Integer> list){
        int[] ret = new int[list.size()];
        for(int i = 0;i < ret.length;i++)
            ret[i] = list.get(i);
        return ret;
    }

    public int getShirts(String teamShirt){
        int shirtID;
        switch(teamShirt) {
            case "Achilles":
                shirtID = R.drawable.achilles;
                break;
            case "Club Lux":
                shirtID = R.drawable.club_lux;
                break;
            case "Dragos FC":
                shirtID = R.drawable.dragos_fc;
                break;
            case "Drakar North":
                shirtID = R.drawable.drakar_north;
                break;
            case "Elefune":
                shirtID = R.drawable.elefune;
                break;
            case "FC Araia":
                shirtID = R.drawable.fc_araia;
                break;
            case "FC Hague":
                shirtID = R.drawable.fc_hague;
                break;
            case "FC Havtar":
                shirtID = R.drawable.fc_havtar;
                break;
            case "FC Wulfbosch":
                shirtID = R.drawable.fc_wulfbosch;
                break;
            case "Fourmation":
                shirtID = R.drawable.fourmation;
                break;
            case "Full Crescent Club":
                shirtID = R.drawable.full_crescent_club;
                break;
            case "Lions Club":
                shirtID = R.drawable.lions_club;
                break;
            case "Mota Noa FC":
                shirtID = R.drawable.mota_noa_fc;
                break;
            case "Phoenix Football":
                shirtID = R.drawable.phoenix_football;
                break;
            case "Rivergod":
                shirtID = R.drawable.rivergod;
                break;
            case "Thorwoed City":
                shirtID = R.drawable.thorwoed_city;
                break;
            case "Xaris Plaza":
                shirtID = R.drawable.xaris_plaza;
                break;
            case "Xav Atlantic":
                shirtID = R.drawable.xav_atlantic;
                break;
            default:
                shirtID = R.drawable.achilles_away;
                break;
        }
        return shirtID;
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
                players[i] = age + "?" + savedPlayerStatus[4] + " - " + savedPlayerStatus[0] +
                        " - " + savedPlayerStatus[3] + "?" + savedPlayerStatus[6];
            } else if(filterAgeOverall.equals("overall")){
                players[i] = savedPlayerStatus[4] + " - " + savedPlayerStatus[0] + " - " +
                        savedPlayerStatus[3] + " - " + age + "?" + savedPlayerStatus[6];
            } else {
                int potential = Integer.valueOf(savedPlayerStatus[4]);
                if(Integer.valueOf(age)<27) {
                    potential = Integer.valueOf(savedPlayerStatus[4]) + 27 - Integer.valueOf(age);
                }
                players[i] = Integer.toString(potential) + " - " + savedPlayerStatus[0] + " - " +
                        savedPlayerStatus[3] + " - " + age + "?" + savedPlayerStatus[6];
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
                String[] savedPlayerStatus = players[j].split("\\?");
//                players[j] = savedPlayerStatus[2] + " - " + savedPlayerStatus[1] +
//                        " - " + savedPlayerStatus[3] + " - " + savedPlayerStatus[0]+"yr";
                players[j] = savedPlayerStatus[1] + " - " + savedPlayerStatus[0] + "yr" +
                        "?" + savedPlayerStatus[2];
            }
        }
        return players;
    }

    public void setSortedTextView(String currentTeam, String teamString){
        TextView sortedSentence = (TextView) findViewById(R.id.sortedView);

//        String formation = "";
        String[] teams = teamString.split("\\*");
        for(int i = 0;i<teams.length;i++){
            if(teams[i].contains(currentTeam)){
                String formation = teams[i].split("\\?")[6];
                sortedSentence.setText(formation);
                break;
            }
        }
    }
}

