package com.example.tobias.footballapp;

import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FormationActivity extends AppCompatActivity {

    ImageView gk1;
    TextView textGk1;

    ImageView def51;
    ImageView def52;
    ImageView def53;
    ImageView def54;
    ImageView def55;
    ImageView def41;
    ImageView def42;
    ImageView def43;
    ImageView def44;
    ImageView def31;
    ImageView def32;
    ImageView def33;

    TextView textDef51;
    TextView textDef52;
    TextView textDef53;
    TextView textDef54;
    TextView textDef55;
    TextView textDef41;
    TextView textDef42;
    TextView textDef43;
    TextView textDef44;
    TextView textDef31;
    TextView textDef32;
    TextView textDef33;

    ImageView mid151;
    ImageView mid152;
    ImageView mid153;
    ImageView mid154;
    ImageView mid155;
    ImageView mid141;
    ImageView mid142;
    ImageView mid143;
    ImageView mid144;
    ImageView mid131;
    ImageView mid132;
    ImageView mid133;

    TextView textMid151;
    TextView textMid152;
    TextView textMid153;
    TextView textMid154;
    TextView textMid155;
    TextView textMid141;
    TextView textMid142;
    TextView textMid143;
    TextView textMid144;
    TextView textMid131;
    TextView textMid132;
    TextView textMid133;

    ImageView mid251;
    ImageView mid252;
    ImageView mid253;
    ImageView mid254;
    ImageView mid255;
    ImageView mid241;
    ImageView mid242;
    ImageView mid243;
    ImageView mid244;
    ImageView mid231;
    ImageView mid232;
    ImageView mid233;

    TextView textMid251;
    TextView textMid252;
    TextView textMid253;
    TextView textMid254;
    TextView textMid255;
    TextView textMid241;
    TextView textMid242;
    TextView textMid243;
    TextView textMid244;
    TextView textMid231;
    TextView textMid232;
    TextView textMid233;

    ImageView mid121;
    ImageView mid122;
    ImageView mid221;
    ImageView mid222;

    TextView textMid121;
    TextView textMid122;
    TextView textMid221;
    TextView textMid222;


    ImageView atk41;
    ImageView atk42;
    ImageView atk43;
    ImageView atk44;
    ImageView atk31;
    ImageView atk32;
    ImageView atk33;
    ImageView atk21;
    ImageView atk22;

    TextView textAtk41;
    TextView textAtk42;
    TextView textAtk43;
    TextView textAtk44;
    TextView textAtk31;
    TextView textAtk32;
    TextView textAtk33;
    TextView textAtk21;
    TextView textAtk22;

    String[] formations = {"4-2-1-3","4-1-4-1","4-2-3-1","4-1-2-3","4-1-3-2","4-3-3","4-4-2","5-3-2","5-4-1"};
//,"4-3-3","4-4-2","5-3-2","5-4-1"

    FormationClass teamFormation;

    List<String> formGK = new ArrayList<>();
    List<String> formDef = new ArrayList<>();
    List<String> formMid1 = new ArrayList<>();
    List<String> formMid2 = new ArrayList<>();
    List<String> formAtk = new ArrayList<>();

    int shirtID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);


        Bundle extras = getIntent().getExtras();
        String ws = extras.getString("num");
        int rnd = Integer.valueOf(extras.getString("num"));


        setImageViews();


        SharedPreferences prefs = this.getSharedPreferences("settings", this.MODE_APPEND);
        String allTeams = prefs.getString("teams", "");
        String[] allTeamsArray = allTeams.split("\\*");
        String allPlayers = prefs.getString("players", "");
        String[] allPlayersArray = allPlayers.split("\\*");

//        Random rand = new Random();
//        int rnd = rand.nextInt(allTeamsArray.length);
//        String team = allTeamsArray[rnd].split("\\?")[0];
        String team = allTeamsArray[rnd].split("\\?")[0];
//        String team = "FC Havtar";
//        String team = "Xav Atlantic";
        String currentTeam ="";

        for(int i = 0; i<allTeamsArray.length;i++){
            if(allTeamsArray[i].contains(team)){
                currentTeam = allTeamsArray[i];//.split("\\?")[0];
            }
        }

        getShirts(currentTeam.split("\\?")[0]);

        String[] teamPlayers = filterPlayers(allPlayersArray, currentTeam.split("\\?")[0]);


        teamFormation = new FormationClass(currentTeam.split("\\?")[6], teamPlayers);//formations[4]);

        setTeamFormation(currentTeam.split("\\?")[6]);

        setRightShirt();
    }

    public void setTeamFormation(String formation){
        formGK = teamFormation.getGK();
        formDef = teamFormation.getDef();
        formMid1 = teamFormation.getMid1();
        formMid2 = teamFormation.getMid2();
        formAtk = teamFormation.getAtk();

        textGk1.setText(formGK.get(0).split("\\?")[1]);

        String[] posNum = formation.split("-");
        visibleDef(Integer.valueOf(posNum[0]));
        visibleMid1(Integer.valueOf(posNum[1]));
        visibleMid2(Integer.valueOf(posNum[2]));
        if(posNum.length>3) {
            visibleAtk(Integer.valueOf(posNum[3]));
        } else {
            visibleAtk(Integer.valueOf(0));
        }
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

    public void randomFormation(View view){
        Button button = (Button) findViewById(R.id.buttonFormation);
        Random rand = new Random();

        int rnd = rand.nextInt(formations.length);
        String[] posNum = formations[rnd].split("-");
        visibleDef(Integer.valueOf(posNum[0]));
        visibleMid1(Integer.valueOf(posNum[1]));
        visibleMid2(Integer.valueOf(posNum[2]));
        if(posNum.length>3) {
            visibleAtk(Integer.valueOf(posNum[3]));
        } else {
            visibleAtk(Integer.valueOf(0));
        }
        button.setText(formations[rnd]);
    }

    public void setImageViews(){
        gk1 = (ImageView) findViewById(R.id.shirt01);
        textGk1 = (TextView) findViewById(R.id.text01);
        def51 = (ImageView) findViewById(R.id.shirtDef51);
        def52 = (ImageView) findViewById(R.id.shirtDef52);
        def53 = (ImageView) findViewById(R.id.shirtDef53);
        def54 = (ImageView) findViewById(R.id.shirtDef54);
        def55 = (ImageView) findViewById(R.id.shirtDef55);
        def41 = (ImageView) findViewById(R.id.shirtDef41);
        def42 = (ImageView) findViewById(R.id.shirtDef42);
        def43 = (ImageView) findViewById(R.id.shirtDef43);
        def44 = (ImageView) findViewById(R.id.shirtDef44);
        def31 = (ImageView) findViewById(R.id.shirtDef31);
        def32 = (ImageView) findViewById(R.id.shirtDef32);
        def33 = (ImageView) findViewById(R.id.shirtDef33);
        textDef51 = (TextView) findViewById(R.id.textDef51);
        textDef52 = (TextView) findViewById(R.id.textDef52);
        textDef53 = (TextView) findViewById(R.id.textDef53);
        textDef54 = (TextView) findViewById(R.id.textDef54);
        textDef55 = (TextView) findViewById(R.id.textDef55);
        textDef41 = (TextView) findViewById(R.id.textDef41);
        textDef42 = (TextView) findViewById(R.id.textDef42);
        textDef43 = (TextView) findViewById(R.id.textDef43);
        textDef44 = (TextView) findViewById(R.id.textDef44);
        textDef31 = (TextView) findViewById(R.id.textDef31);
        textDef32 = (TextView) findViewById(R.id.textDef32);
        textDef33 = (TextView) findViewById(R.id.textDef33);

        mid151 = (ImageView) findViewById(R.id.shirtMid151);
        mid152 = (ImageView) findViewById(R.id.shirtMid152);
        mid153 = (ImageView) findViewById(R.id.shirtMid153);
        mid154 = (ImageView) findViewById(R.id.shirtMid154);
        mid155 = (ImageView) findViewById(R.id.shirtMid155);
        mid141 = (ImageView) findViewById(R.id.shirtMid141);
        mid142 = (ImageView) findViewById(R.id.shirtMid142);
        mid143 = (ImageView) findViewById(R.id.shirtMid143);
        mid144 = (ImageView) findViewById(R.id.shirtMid144);
        mid131 = (ImageView) findViewById(R.id.shirtMid131);
        mid132 = (ImageView) findViewById(R.id.shirtMid132);
        mid133 = (ImageView) findViewById(R.id.shirtMid133);
        textMid151 = (TextView) findViewById(R.id.textMid151);
        textMid152 = (TextView) findViewById(R.id.textMid152);
        textMid153 = (TextView) findViewById(R.id.textMid153);
        textMid154 = (TextView) findViewById(R.id.textMid154);
        textMid155 = (TextView) findViewById(R.id.textMid155);
        textMid141 = (TextView) findViewById(R.id.textMid141);
        textMid142 = (TextView) findViewById(R.id.textMid142);
        textMid143 = (TextView) findViewById(R.id.textMid143);
        textMid144 = (TextView) findViewById(R.id.textMid144);
        textMid131 = (TextView) findViewById(R.id.textMid131);
        textMid132 = (TextView) findViewById(R.id.textMid132);
        textMid133 = (TextView) findViewById(R.id.textMid133);

        mid251 = (ImageView) findViewById(R.id.shirtMid251);
        mid252 = (ImageView) findViewById(R.id.shirtMid252);
        mid253 = (ImageView) findViewById(R.id.shirtMid253);
        mid254 = (ImageView) findViewById(R.id.shirtMid254);
        mid255 = (ImageView) findViewById(R.id.shirtMid255);
        mid241 = (ImageView) findViewById(R.id.shirtMid241);
        mid242 = (ImageView) findViewById(R.id.shirtMid242);
        mid243 = (ImageView) findViewById(R.id.shirtMid243);
        mid244 = (ImageView) findViewById(R.id.shirtMid244);
        mid231 = (ImageView) findViewById(R.id.shirtMid231);
        mid232 = (ImageView) findViewById(R.id.shirtMid232);
        mid233 = (ImageView) findViewById(R.id.shirtMid233);
        textMid251 = (TextView) findViewById(R.id.textMid251);
        textMid252 = (TextView) findViewById(R.id.textMid252);
        textMid253 = (TextView) findViewById(R.id.textMid253);
        textMid254 = (TextView) findViewById(R.id.textMid254);
        textMid255 = (TextView) findViewById(R.id.textMid255);
        textMid241 = (TextView) findViewById(R.id.textMid241);
        textMid242 = (TextView) findViewById(R.id.textMid242);
        textMid243 = (TextView) findViewById(R.id.textMid243);
        textMid244 = (TextView) findViewById(R.id.textMid244);
        textMid231 = (TextView) findViewById(R.id.textMid231);
        textMid232 = (TextView) findViewById(R.id.textMid232);
        textMid233 = (TextView) findViewById(R.id.textMid233);

        mid121 = (ImageView) findViewById(R.id.shirtMid121);
        mid122 = (ImageView) findViewById(R.id.shirtMid122);
        mid221 = (ImageView) findViewById(R.id.shirtMid221);
        mid222 = (ImageView) findViewById(R.id.shirtMid222);
        textMid121 = (TextView) findViewById(R.id.textMid121);
        textMid122 = (TextView) findViewById(R.id.textMid122);
        textMid221 = (TextView) findViewById(R.id.textMid221);
        textMid222 = (TextView) findViewById(R.id.textMid222);

        atk21 = (ImageView) findViewById(R.id.shirtAtk21);
        atk22 = (ImageView) findViewById(R.id.shirtAtk22);
        atk31 = (ImageView) findViewById(R.id.shirtAtk31);
        atk32 = (ImageView) findViewById(R.id.shirtAtk32);
        atk33 = (ImageView) findViewById(R.id.shirtAtk33);
        atk41 = (ImageView) findViewById(R.id.shirtAtk41);
        atk42 = (ImageView) findViewById(R.id.shirtAtk42);
        atk43 = (ImageView) findViewById(R.id.shirtAtk43);
        atk44 = (ImageView) findViewById(R.id.shirtAtk44);
        textAtk21 = (TextView) findViewById(R.id.textAtk21);
        textAtk22 = (TextView) findViewById(R.id.textAtk22);
        textAtk31 = (TextView) findViewById(R.id.textAtk31);
        textAtk32 = (TextView) findViewById(R.id.textAtk32);
        textAtk33 = (TextView) findViewById(R.id.textAtk33);
        textAtk41 = (TextView) findViewById(R.id.textAtk41);
        textAtk42 = (TextView) findViewById(R.id.textAtk42);
        textAtk43 = (TextView) findViewById(R.id.textAtk43);
        textAtk44 = (TextView) findViewById(R.id.textAtk44);
    }


    public void setRightShirt(){
        gk1.setImageResource(shirtID);
        def51.setImageResource(shirtID);
        def52.setImageResource(shirtID);
        def53.setImageResource(shirtID);
        def54.setImageResource(shirtID);
        def55.setImageResource(shirtID);
        def41.setImageResource(shirtID);
        def42.setImageResource(shirtID);
        def43.setImageResource(shirtID);
        def44.setImageResource(shirtID);
        def31.setImageResource(shirtID);
        def32.setImageResource(shirtID);
        def33.setImageResource(shirtID);

        mid151.setImageResource(shirtID);
        mid152.setImageResource(shirtID);
        mid153.setImageResource(shirtID);
        mid154.setImageResource(shirtID);
        mid155.setImageResource(shirtID);
        mid141.setImageResource(shirtID);
        mid142.setImageResource(shirtID);
        mid143.setImageResource(shirtID);
        mid144.setImageResource(shirtID);
        mid131.setImageResource(shirtID);
        mid132.setImageResource(shirtID);
        mid133.setImageResource(shirtID);

        mid251.setImageResource(shirtID);
        mid252.setImageResource(shirtID);
        mid253.setImageResource(shirtID);
        mid254.setImageResource(shirtID);
        mid255.setImageResource(shirtID);
        mid241.setImageResource(shirtID);
        mid242.setImageResource(shirtID);
        mid243.setImageResource(shirtID);
        mid244.setImageResource(shirtID);
        mid231.setImageResource(shirtID);
        mid232.setImageResource(shirtID);
        mid233.setImageResource(shirtID);

        mid121.setImageResource(shirtID);
        mid122.setImageResource(shirtID);
        mid221.setImageResource(shirtID);
        mid222.setImageResource(shirtID);

        atk21.setImageResource(shirtID);
        atk22.setImageResource(shirtID);
        atk31.setImageResource(shirtID);
        atk32.setImageResource(shirtID);
        atk33.setImageResource(shirtID);
        atk41.setImageResource(shirtID);
        atk42.setImageResource(shirtID);
        atk43.setImageResource(shirtID);
        atk44.setImageResource(shirtID);
    }

    public void visibleDef(int num){
        switch (num){
            case 5:
                def51.setVisibility(View.VISIBLE);
                def52.setVisibility(View.VISIBLE);
                def53.setVisibility(View.VISIBLE);
                def54.setVisibility(View.VISIBLE);
                def55.setVisibility(View.VISIBLE);
                def41.setVisibility(View.INVISIBLE);
                def42.setVisibility(View.INVISIBLE);
                def43.setVisibility(View.INVISIBLE);
                def44.setVisibility(View.INVISIBLE);
                def31.setVisibility(View.INVISIBLE);
                def32.setVisibility(View.INVISIBLE);
                def33.setVisibility(View.INVISIBLE);
                textDef51.setVisibility(View.VISIBLE);
                textDef52.setVisibility(View.VISIBLE);
                textDef53.setVisibility(View.VISIBLE);
                textDef54.setVisibility(View.VISIBLE);
                textDef55.setVisibility(View.VISIBLE);
                textDef41.setVisibility(View.INVISIBLE);
                textDef42.setVisibility(View.INVISIBLE);
                textDef43.setVisibility(View.INVISIBLE);
                textDef44.setVisibility(View.INVISIBLE);
                textDef31.setVisibility(View.INVISIBLE);
                textDef32.setVisibility(View.INVISIBLE);
                textDef33.setVisibility(View.INVISIBLE);

                textDef55.setText(formDef.get(0).split("\\?")[1]);
                textDef54.setText(formDef.get(1).split("\\?")[1]);
                textDef53.setText(formDef.get(2).split("\\?")[1]);
                textDef52.setText(formDef.get(3).split("\\?")[1]);
                textDef51.setText(formDef.get(4).split("\\?")[1]);
                break;
            case 4:
                def51.setVisibility(View.INVISIBLE);
                def52.setVisibility(View.INVISIBLE);
                def53.setVisibility(View.INVISIBLE);
                def54.setVisibility(View.INVISIBLE);
                def55.setVisibility(View.INVISIBLE);
                def41.setVisibility(View.VISIBLE);
                def42.setVisibility(View.VISIBLE);
                def43.setVisibility(View.VISIBLE);
                def44.setVisibility(View.VISIBLE);
                def31.setVisibility(View.INVISIBLE);
                def32.setVisibility(View.INVISIBLE);
                def33.setVisibility(View.INVISIBLE);
                textDef51.setVisibility(View.INVISIBLE);
                textDef52.setVisibility(View.INVISIBLE);
                textDef53.setVisibility(View.INVISIBLE);
                textDef54.setVisibility(View.INVISIBLE);
                textDef55.setVisibility(View.INVISIBLE);
                textDef41.setVisibility(View.VISIBLE);
                textDef42.setVisibility(View.VISIBLE);
                textDef43.setVisibility(View.VISIBLE);
                textDef44.setVisibility(View.VISIBLE);
                textDef31.setVisibility(View.INVISIBLE);
                textDef32.setVisibility(View.INVISIBLE);
                textDef33.setVisibility(View.INVISIBLE);

                textDef44.setText(formDef.get(0).split("\\?")[1]);
                textDef43.setText(formDef.get(1).split("\\?")[1]);
                textDef42.setText(formDef.get(2).split("\\?")[1]);
                textDef41.setText(formDef.get(3).split("\\?")[1]);
                break;
            case 3:
                def51.setVisibility(View.INVISIBLE);
                def52.setVisibility(View.INVISIBLE);
                def53.setVisibility(View.INVISIBLE);
                def54.setVisibility(View.INVISIBLE);
                def55.setVisibility(View.INVISIBLE);
                def41.setVisibility(View.INVISIBLE);
                def42.setVisibility(View.INVISIBLE);
                def43.setVisibility(View.INVISIBLE);
                def44.setVisibility(View.INVISIBLE);
                def31.setVisibility(View.VISIBLE);
                def32.setVisibility(View.VISIBLE);
                def33.setVisibility(View.VISIBLE);
                textDef51.setVisibility(View.INVISIBLE);
                textDef52.setVisibility(View.INVISIBLE);
                textDef53.setVisibility(View.INVISIBLE);
                textDef54.setVisibility(View.INVISIBLE);
                textDef55.setVisibility(View.INVISIBLE);
                textDef41.setVisibility(View.INVISIBLE);
                textDef42.setVisibility(View.INVISIBLE);
                textDef43.setVisibility(View.INVISIBLE);
                textDef44.setVisibility(View.INVISIBLE);
                textDef31.setVisibility(View.VISIBLE);
                textDef32.setVisibility(View.VISIBLE);
                textDef33.setVisibility(View.VISIBLE);

                textDef33.setText(formDef.get(0).split("\\?")[1]);
                textDef32.setText(formDef.get(1).split("\\?")[1]);
                textDef31.setText(formDef.get(2).split("\\?")[1]);
                break;
        }
    }

    public void visibleMid1(int num){
        switch (num){
            case 5:
                mid151.setVisibility(View.VISIBLE);
                mid152.setVisibility(View.VISIBLE);
                mid153.setVisibility(View.VISIBLE);
                mid154.setVisibility(View.VISIBLE);
                mid155.setVisibility(View.VISIBLE);
                mid141.setVisibility(View.INVISIBLE);
                mid142.setVisibility(View.INVISIBLE);
                mid143.setVisibility(View.INVISIBLE);
                mid144.setVisibility(View.INVISIBLE);
                mid131.setVisibility(View.INVISIBLE);
                mid132.setVisibility(View.INVISIBLE);
                mid133.setVisibility(View.INVISIBLE);
                mid121.setVisibility(View.INVISIBLE);
                mid122.setVisibility(View.INVISIBLE);
                textMid151.setVisibility(View.VISIBLE);
                textMid152.setVisibility(View.VISIBLE);
                textMid153.setVisibility(View.VISIBLE);
                textMid154.setVisibility(View.VISIBLE);
                textMid155.setVisibility(View.VISIBLE);
                textMid141.setVisibility(View.INVISIBLE);
                textMid142.setVisibility(View.INVISIBLE);
                textMid143.setVisibility(View.INVISIBLE);
                textMid144.setVisibility(View.INVISIBLE);
                textMid131.setVisibility(View.INVISIBLE);
                textMid132.setVisibility(View.INVISIBLE);
                textMid133.setVisibility(View.INVISIBLE);
                textMid121.setVisibility(View.INVISIBLE);
                textMid122.setVisibility(View.INVISIBLE);

                textMid155.setText(formMid1.get(1).split("\\?")[1]);
                textMid154.setText(formMid1.get(2).split("\\?")[1]);
                textMid153.setText(formMid1.get(3).split("\\?")[1]);
                textMid152.setText(formMid1.get(4).split("\\?")[1]);
                textMid151.setText(formMid1.get(5).split("\\?")[1]);
                break;
            case 4:
                mid151.setVisibility(View.INVISIBLE);
                mid152.setVisibility(View.INVISIBLE);
                mid153.setVisibility(View.INVISIBLE);
                mid154.setVisibility(View.INVISIBLE);
                mid155.setVisibility(View.INVISIBLE);
                mid141.setVisibility(View.VISIBLE);
                mid142.setVisibility(View.VISIBLE);
                mid143.setVisibility(View.VISIBLE);
                mid144.setVisibility(View.VISIBLE);
                mid131.setVisibility(View.INVISIBLE);
                mid132.setVisibility(View.INVISIBLE);
                mid133.setVisibility(View.INVISIBLE);
                mid121.setVisibility(View.INVISIBLE);
                mid122.setVisibility(View.INVISIBLE);
                textMid151.setVisibility(View.INVISIBLE);
                textMid152.setVisibility(View.INVISIBLE);
                textMid153.setVisibility(View.INVISIBLE);
                textMid154.setVisibility(View.INVISIBLE);
                textMid155.setVisibility(View.INVISIBLE);
                textMid141.setVisibility(View.VISIBLE);
                textMid142.setVisibility(View.VISIBLE);
                textMid143.setVisibility(View.VISIBLE);
                textMid144.setVisibility(View.VISIBLE);
                textMid131.setVisibility(View.INVISIBLE);
                textMid132.setVisibility(View.INVISIBLE);
                textMid133.setVisibility(View.INVISIBLE);
                textMid121.setVisibility(View.INVISIBLE);
                textMid122.setVisibility(View.INVISIBLE);

                textMid144.setText(formMid1.get(0).split("\\?")[1]);
                textMid143.setText(formMid1.get(1).split("\\?")[1]);
                textMid142.setText(formMid1.get(2).split("\\?")[1]);
                textMid141.setText(formMid1.get(3).split("\\?")[1]);
                break;
            case 3:
                mid151.setVisibility(View.INVISIBLE);
                mid152.setVisibility(View.INVISIBLE);
                mid153.setVisibility(View.INVISIBLE);
                mid154.setVisibility(View.INVISIBLE);
                mid155.setVisibility(View.INVISIBLE);
                mid141.setVisibility(View.INVISIBLE);
                mid142.setVisibility(View.INVISIBLE);
                mid143.setVisibility(View.INVISIBLE);
                mid144.setVisibility(View.INVISIBLE);
                mid131.setVisibility(View.VISIBLE);
                mid132.setVisibility(View.VISIBLE);
                mid133.setVisibility(View.VISIBLE);
                mid121.setVisibility(View.INVISIBLE);
                mid122.setVisibility(View.INVISIBLE);
                textMid151.setVisibility(View.INVISIBLE);
                textMid152.setVisibility(View.INVISIBLE);
                textMid153.setVisibility(View.INVISIBLE);
                textMid154.setVisibility(View.INVISIBLE);
                textMid155.setVisibility(View.INVISIBLE);
                textMid141.setVisibility(View.INVISIBLE);
                textMid142.setVisibility(View.INVISIBLE);
                textMid143.setVisibility(View.INVISIBLE);
                textMid144.setVisibility(View.INVISIBLE);
                textMid131.setVisibility(View.VISIBLE);
                textMid132.setVisibility(View.VISIBLE);
                textMid133.setVisibility(View.VISIBLE);
                textMid121.setVisibility(View.INVISIBLE);
                textMid122.setVisibility(View.INVISIBLE);

                textMid133.setText(formMid1.get(0).split("\\?")[1]);
                textMid132.setText(formMid1.get(1).split("\\?")[1]);
                textMid131.setText(formMid1.get(2).split("\\?")[1]);
                break;
            case 2:
                mid151.setVisibility(View.INVISIBLE);
                mid152.setVisibility(View.INVISIBLE);
                mid153.setVisibility(View.INVISIBLE);
                mid154.setVisibility(View.INVISIBLE);
                mid155.setVisibility(View.INVISIBLE);
                mid141.setVisibility(View.INVISIBLE);
                mid142.setVisibility(View.INVISIBLE);
                mid143.setVisibility(View.INVISIBLE);
                mid144.setVisibility(View.INVISIBLE);
                mid131.setVisibility(View.INVISIBLE);
                mid132.setVisibility(View.INVISIBLE);
                mid133.setVisibility(View.INVISIBLE);
                mid121.setVisibility(View.VISIBLE);
                mid122.setVisibility(View.VISIBLE);
                textMid151.setVisibility(View.INVISIBLE);
                textMid152.setVisibility(View.INVISIBLE);
                textMid153.setVisibility(View.INVISIBLE);
                textMid154.setVisibility(View.INVISIBLE);
                textMid155.setVisibility(View.INVISIBLE);
                textMid141.setVisibility(View.INVISIBLE);
                textMid142.setVisibility(View.INVISIBLE);
                textMid143.setVisibility(View.INVISIBLE);
                textMid144.setVisibility(View.INVISIBLE);
                textMid131.setVisibility(View.INVISIBLE);
                textMid132.setVisibility(View.INVISIBLE);
                textMid133.setVisibility(View.INVISIBLE);
                textMid121.setVisibility(View.VISIBLE);
                textMid122.setVisibility(View.VISIBLE);

                textMid122.setText(formMid1.get(0).split("\\?")[1]);
                textMid121.setText(formMid1.get(1).split("\\?")[1]);
                break;
            case 1:
                mid151.setVisibility(View.INVISIBLE);
                mid152.setVisibility(View.INVISIBLE);
                mid153.setVisibility(View.INVISIBLE);
                mid154.setVisibility(View.INVISIBLE);
                mid155.setVisibility(View.INVISIBLE);
                mid141.setVisibility(View.INVISIBLE);
                mid142.setVisibility(View.INVISIBLE);
                mid143.setVisibility(View.INVISIBLE);
                mid144.setVisibility(View.INVISIBLE);
                mid131.setVisibility(View.INVISIBLE);
                mid132.setVisibility(View.VISIBLE);
                mid133.setVisibility(View.INVISIBLE);
                mid121.setVisibility(View.INVISIBLE);
                mid122.setVisibility(View.INVISIBLE);
                textMid151.setVisibility(View.INVISIBLE);
                textMid152.setVisibility(View.INVISIBLE);
                textMid153.setVisibility(View.INVISIBLE);
                textMid154.setVisibility(View.INVISIBLE);
                textMid155.setVisibility(View.INVISIBLE);
                textMid141.setVisibility(View.INVISIBLE);
                textMid142.setVisibility(View.INVISIBLE);
                textMid143.setVisibility(View.INVISIBLE);
                textMid144.setVisibility(View.INVISIBLE);
                textMid131.setVisibility(View.INVISIBLE);
                textMid132.setVisibility(View.VISIBLE);
                textMid133.setVisibility(View.INVISIBLE);
                textMid121.setVisibility(View.INVISIBLE);
                textMid122.setVisibility(View.INVISIBLE);

                textMid132.setText(formMid1.get(0).split("\\?")[1]);
                break;
        }
    }

    public void visibleMid2(int num){
        switch (num){
            case 5:
                mid251.setVisibility(View.VISIBLE);
                mid252.setVisibility(View.VISIBLE);
                mid253.setVisibility(View.VISIBLE);
                mid254.setVisibility(View.VISIBLE);
                mid255.setVisibility(View.VISIBLE);
                mid241.setVisibility(View.INVISIBLE);
                mid242.setVisibility(View.INVISIBLE);
                mid243.setVisibility(View.INVISIBLE);
                mid244.setVisibility(View.INVISIBLE);
                mid231.setVisibility(View.INVISIBLE);
                mid232.setVisibility(View.INVISIBLE);
                mid233.setVisibility(View.INVISIBLE);
                mid221.setVisibility(View.INVISIBLE);
                mid222.setVisibility(View.INVISIBLE);
                textMid251.setVisibility(View.VISIBLE);
                textMid252.setVisibility(View.VISIBLE);
                textMid253.setVisibility(View.VISIBLE);
                textMid254.setVisibility(View.VISIBLE);
                textMid255.setVisibility(View.VISIBLE);
                textMid241.setVisibility(View.INVISIBLE);
                textMid242.setVisibility(View.INVISIBLE);
                textMid243.setVisibility(View.INVISIBLE);
                textMid244.setVisibility(View.INVISIBLE);
                textMid231.setVisibility(View.INVISIBLE);
                textMid232.setVisibility(View.INVISIBLE);
                textMid233.setVisibility(View.INVISIBLE);
                textMid221.setVisibility(View.INVISIBLE);
                textMid222.setVisibility(View.INVISIBLE);

                textMid254.setText(formMid2.get(0).split("\\?")[1]);
                textMid254.setText(formMid2.get(1).split("\\?")[1]);
                textMid253.setText(formMid2.get(2).split("\\?")[1]);
                textMid252.setText(formMid2.get(3).split("\\?")[1]);
                textMid251.setText(formMid2.get(4).split("\\?")[1]);
                break;
            case 4:
                mid251.setVisibility(View.INVISIBLE);
                mid252.setVisibility(View.INVISIBLE);
                mid253.setVisibility(View.INVISIBLE);
                mid254.setVisibility(View.INVISIBLE);
                mid255.setVisibility(View.INVISIBLE);
                mid241.setVisibility(View.VISIBLE);
                mid242.setVisibility(View.VISIBLE);
                mid243.setVisibility(View.VISIBLE);
                mid244.setVisibility(View.VISIBLE);
                mid231.setVisibility(View.INVISIBLE);
                mid232.setVisibility(View.INVISIBLE);
                mid233.setVisibility(View.INVISIBLE);
                mid221.setVisibility(View.INVISIBLE);
                mid222.setVisibility(View.INVISIBLE);
                textMid251.setVisibility(View.INVISIBLE);
                textMid252.setVisibility(View.INVISIBLE);
                textMid253.setVisibility(View.INVISIBLE);
                textMid254.setVisibility(View.INVISIBLE);
                textMid255.setVisibility(View.INVISIBLE);
                textMid241.setVisibility(View.VISIBLE);
                textMid242.setVisibility(View.VISIBLE);
                textMid243.setVisibility(View.VISIBLE);
                textMid244.setVisibility(View.VISIBLE);
                textMid231.setVisibility(View.INVISIBLE);
                textMid232.setVisibility(View.INVISIBLE);
                textMid233.setVisibility(View.INVISIBLE);
                textMid221.setVisibility(View.INVISIBLE);
                textMid222.setVisibility(View.INVISIBLE);

                textMid244.setText(formMid2.get(0).split("\\?")[1]);
                textMid243.setText(formMid2.get(1).split("\\?")[1]);
                textMid242.setText(formMid2.get(2).split("\\?")[1]);
                textMid241.setText(formMid2.get(3).split("\\?")[1]);
                break;
            case 3:
                mid251.setVisibility(View.INVISIBLE);
                mid252.setVisibility(View.INVISIBLE);
                mid253.setVisibility(View.INVISIBLE);
                mid254.setVisibility(View.INVISIBLE);
                mid255.setVisibility(View.INVISIBLE);
                mid241.setVisibility(View.INVISIBLE);
                mid242.setVisibility(View.INVISIBLE);
                mid243.setVisibility(View.INVISIBLE);
                mid244.setVisibility(View.INVISIBLE);
                mid231.setVisibility(View.VISIBLE);
                mid232.setVisibility(View.VISIBLE);
                mid233.setVisibility(View.VISIBLE);
                mid221.setVisibility(View.INVISIBLE);
                mid222.setVisibility(View.INVISIBLE);
                textMid251.setVisibility(View.INVISIBLE);
                textMid252.setVisibility(View.INVISIBLE);
                textMid253.setVisibility(View.INVISIBLE);
                textMid254.setVisibility(View.INVISIBLE);
                textMid255.setVisibility(View.INVISIBLE);
                textMid241.setVisibility(View.INVISIBLE);
                textMid242.setVisibility(View.INVISIBLE);
                textMid243.setVisibility(View.INVISIBLE);
                textMid244.setVisibility(View.INVISIBLE);
                textMid231.setVisibility(View.VISIBLE);
                textMid232.setVisibility(View.VISIBLE);
                textMid233.setVisibility(View.VISIBLE);
                textMid221.setVisibility(View.INVISIBLE);
                textMid222.setVisibility(View.INVISIBLE);

                textMid233.setText(formMid2.get(0).split("\\?")[1]);
                textMid232.setText(formMid2.get(1).split("\\?")[1]);
                textMid231.setText(formMid2.get(2).split("\\?")[1]);
                break;
            case 2:
                mid251.setVisibility(View.INVISIBLE);
                mid252.setVisibility(View.INVISIBLE);
                mid253.setVisibility(View.INVISIBLE);
                mid254.setVisibility(View.INVISIBLE);
                mid255.setVisibility(View.INVISIBLE);
                mid241.setVisibility(View.INVISIBLE);
                mid242.setVisibility(View.INVISIBLE);
                mid243.setVisibility(View.INVISIBLE);
                mid244.setVisibility(View.INVISIBLE);
                mid231.setVisibility(View.INVISIBLE);
                mid232.setVisibility(View.INVISIBLE);
                mid233.setVisibility(View.INVISIBLE);
                mid221.setVisibility(View.VISIBLE);
                mid222.setVisibility(View.VISIBLE);
                textMid251.setVisibility(View.INVISIBLE);
                textMid252.setVisibility(View.INVISIBLE);
                textMid253.setVisibility(View.INVISIBLE);
                textMid254.setVisibility(View.INVISIBLE);
                textMid255.setVisibility(View.INVISIBLE);
                textMid241.setVisibility(View.INVISIBLE);
                textMid242.setVisibility(View.INVISIBLE);
                textMid243.setVisibility(View.INVISIBLE);
                textMid244.setVisibility(View.INVISIBLE);
                textMid231.setVisibility(View.INVISIBLE);
                textMid232.setVisibility(View.INVISIBLE);
                textMid233.setVisibility(View.INVISIBLE);
                textMid221.setVisibility(View.VISIBLE);
                textMid222.setVisibility(View.VISIBLE);

                textMid222.setText(formMid2.get(0).split("\\?")[1]);
                textMid221.setText(formMid2.get(1).split("\\?")[1]);
                break;
            case 1:
                mid251.setVisibility(View.INVISIBLE);
                mid252.setVisibility(View.INVISIBLE);
                mid253.setVisibility(View.INVISIBLE);
                mid254.setVisibility(View.INVISIBLE);
                mid255.setVisibility(View.INVISIBLE);
                mid241.setVisibility(View.INVISIBLE);
                mid242.setVisibility(View.INVISIBLE);
                mid243.setVisibility(View.INVISIBLE);
                mid244.setVisibility(View.INVISIBLE);
                mid231.setVisibility(View.INVISIBLE);
                mid232.setVisibility(View.VISIBLE);
                mid233.setVisibility(View.INVISIBLE);
                mid231.setVisibility(View.INVISIBLE);
                mid232.setVisibility(View.VISIBLE);
                mid233.setVisibility(View.INVISIBLE);
                mid221.setVisibility(View.INVISIBLE);
                mid222.setVisibility(View.INVISIBLE);
                textMid251.setVisibility(View.INVISIBLE);
                textMid252.setVisibility(View.INVISIBLE);
                textMid253.setVisibility(View.INVISIBLE);
                textMid254.setVisibility(View.INVISIBLE);
                textMid255.setVisibility(View.INVISIBLE);
                textMid241.setVisibility(View.INVISIBLE);
                textMid242.setVisibility(View.INVISIBLE);
                textMid243.setVisibility(View.INVISIBLE);
                textMid244.setVisibility(View.INVISIBLE);
                textMid231.setVisibility(View.INVISIBLE);
                textMid232.setVisibility(View.VISIBLE);
                textMid233.setVisibility(View.INVISIBLE);
                textMid221.setVisibility(View.INVISIBLE);
                textMid222.setVisibility(View.INVISIBLE);

                textMid232.setText(formMid2.get(0).split("\\?")[1]);
                break;
        }
    }

    public void visibleAtk(int num){
        switch (num){
            case 4:
                atk21.setVisibility(View.INVISIBLE);
                atk22.setVisibility(View.INVISIBLE);
                atk31.setVisibility(View.INVISIBLE);
                atk32.setVisibility(View.INVISIBLE);
                atk33.setVisibility(View.INVISIBLE);
                atk41.setVisibility(View.VISIBLE);
                atk42.setVisibility(View.VISIBLE);
                atk43.setVisibility(View.VISIBLE);
                atk44.setVisibility(View.VISIBLE);
                textAtk21.setVisibility(View.INVISIBLE);
                textAtk22.setVisibility(View.INVISIBLE);
                textAtk31.setVisibility(View.INVISIBLE);
                textAtk32.setVisibility(View.INVISIBLE);
                textAtk33.setVisibility(View.INVISIBLE);
                textAtk41.setVisibility(View.VISIBLE);
                textAtk42.setVisibility(View.VISIBLE);
                textAtk43.setVisibility(View.VISIBLE);
                textAtk44.setVisibility(View.VISIBLE);

                textAtk44.setText(formAtk.get(0).split("\\?")[1]);
                textAtk43.setText(formAtk.get(1).split("\\?")[1]);
                textAtk42.setText(formAtk.get(2).split("\\?")[1]);
                textAtk41.setText(formAtk.get(3).split("\\?")[1]);
                break;
            case 3:
                atk21.setVisibility(View.INVISIBLE);
                atk22.setVisibility(View.INVISIBLE);
                atk31.setVisibility(View.VISIBLE);
                atk32.setVisibility(View.VISIBLE);
                atk33.setVisibility(View.VISIBLE);
                atk41.setVisibility(View.INVISIBLE);
                atk42.setVisibility(View.INVISIBLE);
                atk43.setVisibility(View.INVISIBLE);
                atk44.setVisibility(View.INVISIBLE);
                textAtk21.setVisibility(View.INVISIBLE);
                textAtk22.setVisibility(View.INVISIBLE);
                textAtk31.setVisibility(View.VISIBLE);
                textAtk32.setVisibility(View.VISIBLE);
                textAtk33.setVisibility(View.VISIBLE);
                textAtk41.setVisibility(View.INVISIBLE);
                textAtk42.setVisibility(View.INVISIBLE);
                textAtk43.setVisibility(View.INVISIBLE);
                textAtk44.setVisibility(View.INVISIBLE);

                textAtk33.setText(formAtk.get(0).split("\\?")[1]);
                textAtk32.setText(formAtk.get(1).split("\\?")[1]);
                textAtk31.setText(formAtk.get(2).split("\\?")[1]);
                break;
            case 2:
                atk21.setVisibility(View.VISIBLE);
                atk22.setVisibility(View.VISIBLE);
                atk31.setVisibility(View.INVISIBLE);
                atk32.setVisibility(View.INVISIBLE);
                atk33.setVisibility(View.INVISIBLE);
                atk41.setVisibility(View.INVISIBLE);
                atk42.setVisibility(View.INVISIBLE);
                atk43.setVisibility(View.INVISIBLE);
                atk44.setVisibility(View.INVISIBLE);
                textAtk21.setVisibility(View.VISIBLE);
                textAtk22.setVisibility(View.VISIBLE);
                textAtk31.setVisibility(View.INVISIBLE);
                textAtk32.setVisibility(View.INVISIBLE);
                textAtk33.setVisibility(View.INVISIBLE);
                textAtk41.setVisibility(View.INVISIBLE);
                textAtk42.setVisibility(View.INVISIBLE);
                textAtk43.setVisibility(View.INVISIBLE);
                textAtk44.setVisibility(View.INVISIBLE);


                textAtk22.setText(formAtk.get(0).split("\\?")[1]);
                textAtk21.setText(formAtk.get(1).split("\\?")[1]);
                break;
            case 1:
                atk21.setVisibility(View.INVISIBLE);
                atk22.setVisibility(View.INVISIBLE);
                atk31.setVisibility(View.INVISIBLE);
                atk32.setVisibility(View.VISIBLE);
                atk33.setVisibility(View.INVISIBLE);
                atk41.setVisibility(View.INVISIBLE);
                atk42.setVisibility(View.INVISIBLE);
                atk43.setVisibility(View.INVISIBLE);
                atk44.setVisibility(View.INVISIBLE);
                textAtk21.setVisibility(View.INVISIBLE);
                textAtk22.setVisibility(View.INVISIBLE);
                textAtk31.setVisibility(View.INVISIBLE);
                textAtk32.setVisibility(View.VISIBLE);
                textAtk33.setVisibility(View.INVISIBLE);
                textAtk41.setVisibility(View.INVISIBLE);
                textAtk42.setVisibility(View.INVISIBLE);
                textAtk43.setVisibility(View.INVISIBLE);
                textAtk44.setVisibility(View.INVISIBLE);

                textAtk32.setText(formAtk.get(0).split("\\?")[1]);
                break;
            case 0:
                atk21.setVisibility(View.INVISIBLE);
                atk22.setVisibility(View.INVISIBLE);
                atk31.setVisibility(View.INVISIBLE);
                atk32.setVisibility(View.INVISIBLE);
                atk33.setVisibility(View.INVISIBLE);
                atk41.setVisibility(View.INVISIBLE);
                atk42.setVisibility(View.INVISIBLE);
                atk43.setVisibility(View.INVISIBLE);
                atk44.setVisibility(View.INVISIBLE);
                textAtk21.setVisibility(View.INVISIBLE);
                textAtk22.setVisibility(View.INVISIBLE);
                textAtk31.setVisibility(View.INVISIBLE);
                textAtk32.setVisibility(View.INVISIBLE);
                textAtk33.setVisibility(View.INVISIBLE);
                textAtk41.setVisibility(View.INVISIBLE);
                textAtk42.setVisibility(View.INVISIBLE);
                textAtk43.setVisibility(View.INVISIBLE);
                textAtk44.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void getShirts(String teamShirt){
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
    }

}
