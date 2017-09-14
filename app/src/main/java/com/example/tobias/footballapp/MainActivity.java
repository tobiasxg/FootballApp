package com.example.tobias.footballapp;

import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void addPlayer(View view){
        Intent intent = new Intent(this, CreatePlayerActivity.class);
        this.startActivity(intent);
    }

    public void toAllPlayers(View view){
        Intent intent = new Intent(this, FilterPlayerActivity.class);
//        Intent intent = new Intent(this, PlayersListActivity.class);
//        intent.putExtra("listName", "players");
//        intent.putExtra("currentTeam", "NULL");
        this.startActivity(intent);
    }

    public void toAllTeams(View view){
        Intent intent = new Intent(this, PlayersListActivity.class);
        intent.putExtra("listName", "teams");
        intent.putExtra("currentTeam", "-NONE-");
        intent.putExtra("filter", "NULL");
        intent.putExtra("countryFilter", "-NONE-");
        intent.putExtra("enabledPos", "NULL");
        this.startActivity(intent);
    }

    public void addTeam(View view){
        Intent intent = new Intent(this, CreateTeamActivity.class);
        this.startActivity(intent);
    }

    public void clearPrefs(View view){
//        SharedPreferences prefs = this.getSharedPreferences("settings",this.MODE_PRIVATE);

        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
        String vips = prefs.getString("playersVIP", "");
        editor.putString("players",vips);
        editor.commit();

//        getApplicationContext().getSharedPreferences("settings", 0).edit().clear().commit();
        Toast.makeText(getApplicationContext(), "Cleared player database", Toast.LENGTH_SHORT).show();
//        setDatabaseAfterClear();
    }

    public void getSharedButton(View view){



        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        String allTeams = prefs.getString("teams", "");
        String[] allTeamsArray = allTeams.split("\\*");
        String allPlayers = prefs.getString("players", "");
        String[] allPlayersArray = allPlayers.split("\\*");

        int avgRating = 0;
        String ratingTeams = "";
        String neededPos = "";
//        rnd=1;
        for(int n=0;n<18;n++) {
            avgRating = getAvgRating(allTeamsArray, allPlayersArray, n);
//        but.setText(compensateRating());
//        String newDB = compensateRating();
            ratingTeams=ratingTeams+allTeamsArray[n].split("\\?")[0]+" "+Integer.toString(avgRating)+"  ";

        }
        TextView tempText = (TextView) findViewById(R.id.tempText);
        tempText.setText(ratingTeams);
//        tempText.setText(allTeams);


        Random rand = new Random();
//        int rnd = 0;
        Button but = (Button) findViewById(R.id.sharedButton);
//        if(but.getText().length()>2){
        int rnd = rand.nextInt(18);
//        } else {
//            but.getText().toString();
//        }

        String[] posArray = getRemainingPositions(rnd);
        for (int j = 0; j < posArray.length; j++) {
            neededPos = neededPos + posArray[j] + " ";
        }
//        Button but = (Button) findViewById(R.id.sharedButton);
        but.setText(allTeamsArray[rnd].split("\\?")[0] + " " +
                Integer.toString(getAvgRating(allTeamsArray, allPlayersArray, rnd)) + " " + neededPos);



//        String extra = "Josh Basset?185?08-08-1998?CB?80?Samoa?Drakar North*"+
//                "Hamidou Dama?180?11-09-1995?ST?77?Cameroen?Elefune*"+
//                "Ramón Olivera?178?15-07-2001?LM?78?Argentina?FC Havtar*"+
//                "Diego Montoya?177?06-03-1995?RM?80?Spain?FC Araia*"+
//                "Potlako Letele?189?29-10-1997?GK?76?South-Africa?Xav Atlantic*"+
//                "Pedro Jesús?187?18-06-1999?CB?73?Brazil?FC Wulfbosch*"+
//                "Aias Hector?183?01-02-1999?CAM?75?Germany?Xav Atlantic*"+
//                "Enrique Jurado?179?02-12-1995?RW?80?Uruguay?Uruguay?Rivergod*"+
//                "Altair Anthes?172?07-11-1998?CAM?77?Greece?Rivergod*"+
//                "Renato Pereira?176?13-07-1998?LM?74?Brazil?Xaris Plaza*"+
//                "Seydou Haba?176?30-07-1998?RW?80?Cameroen?Thorwoed City*"+
//                "Mathias Holm?180?11-07-1992?LM?83?Denmark?Elefune*"+
//                "Sonny Bijen?170?06-07-1991?RW?84?Netherlands?Full Crescent Club*";
//        int x = 1;
//        SharedPreferences.Editor editor = prefs.edit();
//        String newAllPlayers = allPlayers.replace("Miguel Ramírez?189?11-06-1999?CB?78?",
//                "Miguel Ramírez?189?11-06-1996?CB?78?");
//        newAllPlayers = newAllPlayers.replace("Matteo Lenz?191?09-02-1998?CB?80?",
//                "Matteo Lenz?191?09-02-1996?CB?80?");
//        newAllPlayers = newAllPlayers.replace("Aias Hector?183?01-02-1999?CAM?80?",
//                "Aias Hector?183?01-02-1999?CAM?78?");
//        newAllPlayers = newAllPlayers.replace("Alejandro Lagos?193?22-05-1997?CM?82?",
//                "Alejandro Lagos?193?22-05-1996?CM?80?");
//        newAllPlayers = newAllPlayers.replace("Kigongo Rabemananjara?186?13-06-1997?RM?82?",
//                "Kigongo Rabemananjara?186?13-06-1991?RM?85?");
//        newAllPlayers = newAllPlayers.replace("Benito Jurado?172?15-07-1999?LM?80?",
//                "Benito Jurado?172?15-07-1990?LM?85?");
//        newAllPlayers = newAllPlayers.replace("Gael Westfall?189?03-05-1997?ST?82?",
//                "Gael Westfall?189?03-05-1995?ST?82?");
//        newAllPlayers = newAllPlayers.replace("Julian Drayton?183?14-10-1999?LW?76?",
//                "Julian Drayton?183?14-10-1998?LW?79?");
//        newAllPlayers = newAllPlayers.replace("Romain Fontaine?185?25-06-1999?ST?80?",
//                "Romain Fontaine?185?25-06-1991?ST?85?");
//
//        editor.putString("playersVIP",newAllPlayers);
//        editor.commit();
//        }
    }

    public int getAvgRating(String[] allTeamsArray, String[] allPlayersArray, int rnd){
        int counter=0;
        int sumRating = 0;

        String[] teamPlayers = filterPlayers(allPlayersArray, allTeamsArray[rnd].split("\\?")[0]);
        for(int k=0;k<teamPlayers.length;k++) {
            counter=counter+1;
            sumRating = sumRating + Integer.valueOf(teamPlayers[k].split("\\?")[4]);
        }
        int avgRating = sumRating/counter;

        return avgRating;
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

    public String[] getRemainingPositions(int teamIndex){
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        String allTeams = prefs.getString("teams", "");
        String[] allTeamsArray = allTeams.split("\\*");
        String allPlayers = prefs.getString("players", "");
        String[] allPlayersArray = allPlayers.split("\\*");

//        int i = 9;
        String positions = getNeededPosition(teamIndex);
        String[] posArray = positions.split(" ");

        String team = allTeamsArray[teamIndex].split("\\?")[0];

        String allPos = "";
        String[] remainingPlayers = filterPlayers(allPlayersArray, team);
        for (int d = 0; d < remainingPlayers.length; d++) {
            String pos = remainingPlayers[d].split("\\?")[3];
            allPos = allPos+pos+" ";
        }

        String[] allPosArray = allPos.split(" ");
        ArrayList<String> posList = new ArrayList<>(Arrays.asList(posArray));
        for(int k = 0;k<allPosArray.length;k++){
            int index = posList.indexOf(allPosArray[k]);
            try {
                posList.remove(index);
            } catch (Exception e){
                String err="error";
            }
        }
        String[] newPosArray = posList.toArray(new String[posList.size()]);

        String neededPos = "";
        for(int l = 0;l<newPosArray.length;l++){
            neededPos = neededPos+newPosArray[l]+" ";
        }
        return neededPos.split(" ");
    }


    public void createRandomPlayer(View view){

        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
        String allTeams = prefs.getString("teams", "");
        String[] allTeamsArray = allTeams.split("\\*");
        for(int i=0;i<allTeamsArray.length;i++) {
            String[] posArray = getRemainingPositions(i);
            for(int j=0;j<posArray.length;j++) {
                randomPlayer(i,posArray[j]);
            }
        }
    }


    public void randomPlayer(int indexTeam, String playerPos){
//        String names = getString(R.string.names);
//        String[] namesArray = names.split(" ");
//        String surnames = getString(R.string.surnames);
//        String[] surnamesArray = surnames.split(" ");
//        String countries = "Topan Maxico Brazil Argentina England Ireland Wales Norway Denmark " +
//                "Spain Netherlands Belgium Germany Brazil Ghana Zimbabwe South-Africa Venezuela " +
//                "Colombia Uruguay Italy Swiss Jamaica Serbia";
//
//        String [] countriesArray = countries.split(" ");

//        String positionsDF = "CV LB RB CVM";
//        String[] arrayDF = positionsDF.split(" ");
//        String positionsMF = "CM CAM LM RM";
//        String[] arrayMF = positionsMF.split(" ");
//        String positionsAT = "CAM LM RM LW ST RW";
//        String[] arrayAT = positionsAT.split(" ");

        Random rand = new Random();

        int[] countryProbs = {0,1,1,1,2,2,2,3,4,4,4,5,6,7,8,9,10,10,11,11,12,13,14,15,
                -1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int n = rand.nextInt(countryProbs.length);
        String[] nameAndCountry = playerAndCountry(countryProbs[n]);
        String firstName = nameAndCountry[0];
        String lastName = nameAndCountry[1];
        String playerCountry = nameAndCountry[2];

//        String firstName = namesArray[rand.nextInt(namesArray.length)];
//        String lastName = surnamesArray[rand.nextInt(surnamesArray.length)];
        int height = rand.nextInt(25)+165;
        int day = rand.nextInt(28)+1;
        int month = rand.nextInt(12)+1;
        int year = rand.nextInt(18)+1982;
//        int n = rand.nextInt(11);
//        String playerPos = "";
//        if(n==0){
//            playerPos = "GK";
//            height = rand.nextInt(25)+180;
//            year = rand.nextInt(24)+1976;
//        } else if(n<5){
//            int m = rand.nextInt(4);
//            int k = rand.nextInt(3)+1;
//            playerPos = arrayDF[m];
//        } else if(n<8){
//            int m = rand.nextInt(4);
//            int k = rand.nextInt(3)+1;
//            playerPos = arrayMF[m];
//        } else{
//            int m = rand.nextInt(6);
//            int k = rand.nextInt(3)+1;
//            playerPos = arrayAT[m];
//            height = rand.nextInt(30)+165;
//        }

        if(playerPos.equals("GK")){
            height = rand.nextInt(25)+180;
            year = rand.nextInt(24)+1976;
        } else if (playerPos.equals("CB") || playerPos.equals("LB")||
                playerPos.equals("RB") || playerPos.equals("CDM")){
            height = rand.nextInt(25)+175;
        } else if (playerPos.equals("RW") || playerPos.equals("LW")){
            height = rand.nextInt(25)+160;
        }

//        int playerCountryNum = rand.nextInt(3);
//        if(playerCountryNum == 2) {
//            playerCountryNum = rand.nextInt(countriesArray.length);
//        }
//        String playerCountry = countriesArray[playerCountryNum];
        String dayString = Integer.toString(day);
        String monthString = Integer.toString(month);
        if(day<10){
            dayString = "0"+dayString;
        }
        if(month<10){
            monthString = "0"+monthString;
        }


        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
        String allTeams = prefs.getString("teams", "");
        String[] allTeamsArray = allTeams.split("\\*");
//        String playerTeam = allTeamsArray[rand.nextInt(allTeamsArray.length)].split("\\?")[0];
        String[] singleTeamArray = allTeamsArray[indexTeam].split("\\?");
//        String playerTeam = allTeamsArray[indexTeam].split("\\?")[0];
        String playerTeam = singleTeamArray[0];
        int teamAvg = Integer.valueOf(singleTeamArray[singleTeamArray.length-1]); //Currently final position is 7

//        int overallPlayer = rand.nextInt(22)+65;
        int overallPlayer =rand.nextInt(2)+teamAvg-3;

        overallPlayer = overallPlayer - getGrowth(day, month, year);

//        playerPos = getPositionPlayer(playerTeam);

//        "Toviyah Garou?186?29/09/1996?RM CAM RW?90?Topan?Xav Atlantic*";
        String newPlayer = firstName+" "+lastName+"?"+Integer.toString(height)+"?"+
                dayString+"-"+monthString+"-"+Integer.toString(year)+"?"+
                playerPos+"?"+Integer.toString(overallPlayer)+"?"+playerCountry+"?"+playerTeam+"*";

        String playersForPrefs = prefs.getString("players", "");
        playersForPrefs = playersForPrefs+newPlayer;
        editor.putString("players",playersForPrefs);
        editor.commit();
    }

    public int getGrowth(int day, int month, int year){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String[] dateSplit = date.split("-");
        int endYearAge = Integer.valueOf(dateSplit[2])-year;
        if(Integer.valueOf(dateSplit[1])<month){
            endYearAge = endYearAge-1;
        } else if(Integer.valueOf(dateSplit[1])==month &&
                Integer.valueOf(dateSplit[0])<day){
            endYearAge = endYearAge-1;
        }

//        Random rand = new Random();
        int possibleGrowth = 27 - endYearAge;
        if(possibleGrowth<0 && possibleGrowth>-6){
            possibleGrowth = 0;
        } else if(possibleGrowth<=-6){
            possibleGrowth = possibleGrowth*-1;
        }

        return possibleGrowth;
    }

    public void compensateRating(View v){
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        String allTeams = prefs.getString("teams", "");
        String[] allTeamsArray = allTeams.split("\\*");
        String allPlayers = prefs.getString("players", "");
        String[] allPlayersArray = allPlayers.split("\\*");

        String newDB = "";
        for(int j=0;j<allTeamsArray.length;j++){
            String[] singleTeamArray = allTeamsArray[j].split("\\?");
            String[] players = filterPlayers(allPlayersArray, singleTeamArray[0]);
            int teamRating = Integer.valueOf(singleTeamArray[singleTeamArray.length-1]);
            int avgRating = getAvgRating(allTeamsArray, allPlayersArray, j);
            int plusRating = teamRating-avgRating;
            for(int i= 0;i<players.length;i++){
                String [] singlePlayer = players[i].split("\\?");
                int newRating = Integer.valueOf(singlePlayer[4]);
//                if(teamRating>Integer.valueOf(singlePlayer[4])-plusRating) {
//                    newRating = newRating + plusRating;
//                }
                int potential = Integer.valueOf(singlePlayer[4]);
                int age = Integer.valueOf(getAge(singlePlayer[2]));
                if(age<27) {
                    potential = Integer.valueOf(singlePlayer[4]) + 27 - age;
                }
                if(Integer.valueOf(singlePlayer[4])<teamRating+2 &&
                        avgRating<teamRating && potential < 89){
                    newRating = newRating + 1;
                }
                for(int k= 0;k<singlePlayer.length;k++) {
                    if(k==singlePlayer.length-1){
                        newDB = newDB + singlePlayer[k]+"*";
                    } else if(k!=4) {
                        newDB = newDB + singlePlayer[k]+"?";
                    } else {
                        newDB = newDB + Integer.toString(newRating)+"?";
                    }
                }
//                newDB = newDB+"*";
            }
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("players",newDB);
        editor.commit();


//        return newDB;

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

    public String getNeededPosition(int n) {

        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        String allTeams = prefs.getString("teams", "");
        String[] allTeamsArray = allTeams.split("\\*");

        String playerTeam = allTeamsArray[n].split("\\?")[0];
        String formation = allTeamsArray[n].split("\\?")[6];

        String[] formationArray = formation.split("-");

        return checkFormation(formationArray);
    }

    public String checkFormation(String[] formationArray){
        String pos1="";
        String pos2="";
        String pos3="";
        String pos4="";

        if(formationArray[0].equals("3")) {
            pos1 = "LB CB RB";
        } else if(formationArray[0].equals("4")){
            pos1 = "LB CB CB RB";
        } else if (formationArray[0].equals("5")){
            pos1 = "LB CB CB CB RB";
        }

        if(formationArray[1].equals("1")) {
            pos2 = "CDM";
        } else if(formationArray[1].equals("2")){
            pos2 = "CM CM";
        } else if (formationArray[1].equals("3")){
            pos2 = "CM CM CM";
        } else if (formationArray[1].equals("4")){
            pos2 = "LM CM CM RM";
        }

        if(formationArray.length==3){
            if (formationArray[2].equals("1")) {
                pos3 = "ST";
            } else if (formationArray[2].equals("2")) {
                pos3 = "LW RW";
            } else if (formationArray[2].equals("3")) {
                pos3 = "LW ST RW";
            }
            pos4 = "";
        } else {
            if (formationArray[2].equals("1")) {
                pos3 = "CAM";
            } else if (formationArray[2].equals("2")) {
                pos3 = "LM RM";
            } else if (formationArray[2].equals("3")) {
                pos3 = "LM CAM RM";
            } else if (formationArray[2].equals("4")) {
                pos3 = "LM CM CM RM";
            }
            if (formationArray[3].equals("1")) {
                pos4 = "ST";
            } else if (formationArray[3].equals("2")) {
                pos4 = "ST ST";
            } else if (formationArray[3].equals("3")) {
                pos4 = "LW ST RW";
            }
        }

        String positions = "GK "+pos1+" "+pos2+" "+pos3+" "+pos4;
        String space = " ";
        if(pos4.equals("")){
            space="";
        }
        positions = positions+space+positions;
        return positions;
    }

    public String[] playerAndCountry(int countryIndex){
        String names;
        String surnames;
        String countries;
        switch(countryIndex) {
            case 0:
                names = getString(R.string.namesSlavic);
                surnames = getString(R.string.surnamesSlavic);
                countries = getString(R.string.slavicCountries);
                break;
            case 1:
                names = getString(R.string.namesAfrican);
                surnames = getString(R.string.surnamesAfrican);
                countries = getString(R.string.africanCountries);
                break;
            case 2:
                names = getString(R.string.namesLatin);
                surnames = getString(R.string.surnamesLatin);
                countries = getString(R.string.latinCountries);
                break;
            case 3:
                names = getString(R.string.namesScandinavian);
                surnames = getString(R.string.surnamesScandinavian);
                countries = getString(R.string.scandinavianCountries);
                break;
            case 4:
                names = getString(R.string.namesDutch);
                surnames = getString(R.string.surnamesDutch);
                countries = getString(R.string.dutchCountries);
                break;
            case 5:
                names = getString(R.string.namesGerman);
                surnames = getString(R.string.surnamesGerman);
                countries = getString(R.string.germanCountries);
                break;
            case 6:
                names = getString(R.string.namesFrance);
                surnames = getString(R.string.surnamesFrance);
                countries = getString(R.string.franceCountries);
                break;
            case 7:
                names = getString(R.string.namesEnglish);
                surnames = getString(R.string.surnamesEnglish);
                countries = getString(R.string.englishCountries);
                break;
            case 8:
                names = getString(R.string.namesItaly);
                surnames = getString(R.string.surnamesItaly);
                countries = getString(R.string.italyCountries);
                break;
            case 9:
                names = getString(R.string.namesBelgium);
                surnames = getString(R.string.surnamesBelgium);
                countries = getString(R.string.belgiumCountries);
                break;
            case 10:
                names = getString(R.string.namesBrazil);
                surnames = getString(R.string.surnamesBrazil);
                countries = getString(R.string.brazilCountries);
                break;
            case 11:
                names = getString(R.string.namesArgentina);
                surnames = getString(R.string.surnamesArgentina);
                countries = getString(R.string.argentinaCountries);
                break;
            case 12:
                names = getString(R.string.namesAmerica);
                surnames = getString(R.string.surnamesAmerica);
                countries = getString(R.string.americaCountries);
                break;
            case 13:
                names = getString(R.string.namesRussia);
                surnames = getString(R.string.surnamesRussia);
                countries = getString(R.string.russiaCountries);
                break;
            case 14:
                names = getString(R.string.namesRomania);
                surnames = getString(R.string.surnamesRomania);
                countries = getString(R.string.romaniaCountries);
                break;
            case 15:
                names = getString(R.string.namesHungary);
                surnames = getString(R.string.surnamesHungary);
                countries = getString(R.string.hungaryCountries);
                break;
            default:
//                names = getString(R.string.names);
//                surnames = getString(R.string.surnames);
                names = getString(R.string.namesTopan);
                surnames = getString(R.string.surnamesTopan);
                countries = getString(R.string.countries);
                break;
        }

        String[] namesArray = names.split(" ");
        String[] surnamesArray = surnames.split(" ");
        String[] countriesArray = countries.split(" ");

        Random rand = new Random();

        String firstName = namesArray[rand.nextInt(namesArray.length)];
        String lastName = surnamesArray[rand.nextInt(surnamesArray.length)];
        String country = countriesArray[rand.nextInt(countriesArray.length)];

        String[] returnString = {firstName, lastName, country};
        return returnString;
    }

    public void setDatabaseAfterClear(View view){
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        SharedPreferences.Editor editor = prefs.edit();
        String dbPlayers =
                "Toviyah Garou?186?29-09-1996?RM?90?Topan?Xav Atlantic*" +
                        "Jay Papi Gaio?173?18-04-1990?LW?87?Topan?FC Havtar*" +
                        "Quava Quna?205?12-11-1987?CDM?90?Dragonia?Drakar North?*" +
                        "Xander Puam?172?19-04-1994?ST?87?Topan?FC Wulfbosch*" +
                        "Cruz Cocotúa?172?21-07-2000?LW?77?Topan?Xav Atlantic*" +
                        "Naña Lifyli?180?30-03-1987?RM?88?Topan?Achilles*" +
                        "Seto Sanchez?183?29-04-1992?LB?85?Topan?Achilles*" +
                        "Léon K'ara?172?11-11-1986?CM?85?Topan?FC Wulfbosch*" +
                        "Joël Bjärsmyr?190?03-06-1997?ST?83?Sweden?Rivergod*" +
                        "Victor Gielen?186?16-07-1999?GK?82?België?Achilles*" +
                        "Kovu Kristaldo?178?26-11-1993?CAM?85?Dragonia?Drakar North*" +
                        "Téodor Rosèle?186?21-01-1995?LB?83?Topan?Xav Atlantic*" +
                        "Yago Arnaíz?172?16-08-1999?LB?81?Venezuela?Drakar North?*" +
                        "Cristóbal Castle?190?22-11-1995?CM?84?Maxico?Xav Atlantic*"+
                        "Nicola Ricci?192?03-11-1998?LB?82?Italian?FC Havtar*" +
                        "Bruno Bello?199?11-09-1997?CB?82?Italian?Xav Atlantic?*" +
                        "Lorenzo Visser?190?23-12-1999?RM?80?Netherlands?FC Havtar*"+
                        "Nicolás Contín?177?13-02-2000?RW?77?Argentina?Xav Atlantic*"+
                        "Benito Jurado?172?15-07-1999?LM?77?Venezuela?Achilles*";
        editor.putString("players",dbPlayers);
        editor.putString("playersVIP",dbPlayers);

        String dbTeams =
                "Xav Atlantic?Atlantica?1910?70?Toby Garriz?Navy Red?4-2-1-3?84*" +
                        "FC Havtar?Havtar?1910?90?Peter van der Haven?Blue Navy?4-1-4-1?85*" +
                        "FC Wulfbosch?Wulfbosch?1910?80?Nik Howls?Green?4-2-3-1?85*" +
                        "Achilles?Topato?1910?80?Giorgi Hile?White Black?4-3-3?83*" +
                        "Drakar North?Drakar?1910?80?Eluf Issakson?Black Red?4-2-3-1?84*" +
                        "Rivergod?Riveria?1910?60?Geoffry Maren?Navy Blue?4-1-2-3?83*" +
                        "Elefune?Topato?1910?70?Olaf Släger?Yellow White?5-4-1?80*" +
                        "FC Araia?Araia?1910?60?Kevin De Röver?Green Yellow?4-1-3-2?79*" +
                        "Phoenix Football?Araia?1910?50?Felix Fuegon?Black Red?4-4-2?79*"+
                        "Xaris Plaza?Xaris?1910?40?Olivier Lemaster?Navy Red?4-1-4-1?80*" +
                        "Thorwoed City?Thorwoed?1910?60?William Reed?Orange Red?4-1-3-2?80*" +
                        "Full Crescent Club?Lunarome?1910?30?Josuah Lemus?Black Yellow?4-1-2-3?79*" +
                        "Dragos FC?Drakar?1910?25?Mario Costa?Black Brown?4-2-3-1?77*" +
                        "Fourmation?Cater?1910?25?Ricardo Mireles?Green Blue?4-4-2?76*"+
                        "FC Hague?Hague?1910?60?Daniel D'Orange?Red White?4-4-2?77*" +
                        "Lions Club?Lionarden?1910?20?Leo Raor?Orange Black?4-3-3?77*" +
                        "Club Lux?Lux?1910?25?Philippe Edison?Yellow Lightblue?5-3-2?76*" +
                        "Mota Noa FC?Mota Noa?1910?25?Noah Bionica?Yellow Black?4-1-4-1?76*";
        editor.putString("teams",dbTeams);
        editor.commit();

    }

    public void tempList(View view){
//        Intent intent = new Intent(this, ListActivity.class);
//        intent.putExtra("listName", "players");
//        intent.putExtra("currentTeam", "-NONE-");
//        intent.putExtra("filter", "overall");
//        intent.putExtra("countryFilter", "-NONE-");
//        intent.putExtra("enabledPos", "?");
//        this.startActivity(intent);



        Random rand = new Random();


        Button button = (Button) findViewById(R.id.tempList);

        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        String allTeams = prefs.getString("teams", "");
        String[] allTeamsArray = allTeams.split("\\*");
//        String team1 = allTeamsArray[rand.nextInt(allTeamsArray.length)].split("\\?")[0];
//        String team2 = allTeamsArray[rand.nextInt(allTeamsArray.length)].split("\\?")[0];
//        String[] teamAArray = allTeamsArray[rand.nextInt(allTeamsArray.length)].split("\\?");
//        String[] teamBArray = allTeamsArray[rand.nextInt(allTeamsArray.length)].split("\\?");
//        String teamA = teamAArray[0];
//        String teamB = teamBArray[0];
        String teamA = "Xav Atlantic";
        String teamB = "Xav Atlantic";
        String[] teamAArray = allTeamsArray[0].split("\\?");
        String[] teamBArray = allTeamsArray[0].split("\\?");

        String allPlayers = prefs.getString("players", "");
        String[] allPlayersArray = allPlayers.split("\\*");
//        String[] playersA = filterPlayers(allPlayersArray, "Xav Atlantic");
//        String[] playersB = filterPlayers(allPlayersArray, "Elefune");
        String[] playersA = filterPlayers(allPlayersArray, teamA);
        String[] playersB = filterPlayers(allPlayersArray, teamB);
        String formationA = teamAArray[6];
        String formationB = teamBArray[6];

        String[] positionsA = checkFormation(formationA.split("-")).split(" ");
        String[] positionsB = checkFormation(formationB.split("-")).split(" ");
        String testyS="";
        for(int testy = 0;testy<positionsA.length;testy++){
            testyS = testyS+" "+positionsA[testy];
        }

        String lineUpA =getLineUp(playersA,positionsA);
        String lineUpB =getLineUp(playersB,positionsB);



        String[] filteredPlayersA = lineUpA.split("\\*");
        String[] filteredPlayersB = lineUpB.split("\\*");


        TextView tempText = (TextView) findViewById(R.id.tempText);
        String temp = "";
//        temp = temp+"   "+filteredPlayersA[0].split("\\?")[0];
//        temp = temp+"   "+filteredPlayersA[1].split("\\?")[0];
        for(int t = 0;t<filteredPlayersA.length;t++){
            if(filteredPlayersA[t].split("\\?")[3].equals("GK")){
                temp = temp+"\n";
            }
            temp = temp+filteredPlayersA[t].split("\\?")[0]+" ";
        }
//        temp = temp+"";
        tempText.setText(temp);
        tempText.setTextSize(8);


        MatchClass match = new MatchClass(filteredPlayersA, filteredPlayersB, formationA, formationB);
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        match.midfield();
        String[] scoreScorers = match.getScore().split("\\?");
        String goalScorers = "";
        try {
            goalScorers = scoreScorers[1];
        } catch(Exception e){

        }
        button.setText(teamA + " " + scoreScorers[0] + " " + teamB + "\n" + goalScorers);

    }

    public void goToCompetition(View view){
        Intent intent = new Intent(this, CompetitionActivity.class);
        this.startActivity(intent);
    }

    public String getLineUp(String[] players, String[] positions){
        String lineup = "";
        List<String> list= new ArrayList<String>(Arrays.asList(players));
        for(int i=0; i<positions.length;i++){
            for(int j = 0;j<list.size();j++){
                if(list.get(j).contains("?"+positions[i]+"?")){
                    lineup = lineup+list.get(j)+"*";
//                    testTemp = testTemp+" "+listA.get(j).split("\\?")[3];
                    list.remove(j);
                    break;
                }
            }
        }
        return lineup;
    }


    public void filterTopanNames(View view){
        String[] topaneseNames = getString(R.string.surnames).split(" ");
        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
//        SharedPreferences.Editor editor = prefs.edit();
        String topanSavedNames = prefs.getString("topanNames", "");


        Random rand = new Random();
        int num = rand.nextInt(topaneseNames.length);

        Button rndName = (Button) findViewById(R.id.rndNameTmp);
        rndName.setText(topaneseNames[num]);
    }
    public void saveTopanNames(View view){
////        String[] topaneseNames = getString(R.string.surnames).split(" ");
//        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
//        SharedPreferences.Editor editor = prefs.edit();
//        String topanSavedNames = prefs.getString("topanNames", "");
//
//        Button rndName = (Button) findViewById(R.id.rndNameTmp);
//
//        topanSavedNames = topanSavedNames + " " + rndName.getText().toString();
//
//        editor.putString("topanNames",topanSavedNames);
//        editor.commit();
//
//
//        TextView tempText = (TextView) findViewById(R.id.tempText);
//        tempText.setText(topanSavedNames);
//        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        clipboard.setText(topanSavedNames);


//        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
//        SharedPreferences.Editor editor = prefs.edit();
//        String teams = prefs.getString("teams", "");
//        teams = teams.replace("Xav Atlantic?Atlantica?1910?70?Toby Garriz?Navy Yellow?4-2-3-1",
//                "Xav Atlantic?Atlantica?1910?70?Toby Garriz?Navy Yellow?4-2-1-3");

//        editor.putString("teams",dbTeams);
//        editor.commit();



        Button rndName = (Button) findViewById(R.id.saveNameTmp);
        Intent intent = new Intent(this, FormationActivity.class);
        intent.putExtra("num", rndName.getText().toString());
        rndName.setText(Integer.toString(Integer.valueOf(rndName.getText().toString())+1));
        this.startActivity(intent);
    }


}
