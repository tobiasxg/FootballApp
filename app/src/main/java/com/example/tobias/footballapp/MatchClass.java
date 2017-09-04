package com.example.tobias.footballapp;

import android.widget.Toast;

import java.util.Random;

/**
 * Created by Tobias on 3-9-2017.
 */

public class MatchClass {

    String[] playersA;
    String[] playersB;
    String formationA;
    String formationB;
    int defA;
    int midA;
    int atkA;
    int defB;
    int midB;
    int atkB;

    int goalA;
    int goalB;

    String goalScorers;


    public MatchClass(String[] playersA, String[] playersB, String formationA, String formationB){
        this.playersA = playersA;
        this.playersB = playersB;
        this.formationA = formationA;
        this.formationB = formationB;

        String[] formationArrayA = formationA.split("-");
        String[] formationArrayB = formationB.split("-");
        if(formationArrayA.length==3){
            defA = Integer.valueOf(formationArrayA[0]);
            midA = Integer.valueOf(formationArrayA[1]);
            atkA = Integer.valueOf(formationArrayA[2]);
        }else if(formationArrayA.length==4){
            defA = Integer.valueOf(formationArrayA[0]);
            midA = Integer.valueOf(formationArrayA[1])+Integer.valueOf(formationArrayA[2]);
            atkA = Integer.valueOf(formationArrayA[3]);
        }
        if(formationArrayB.length==3){
            defB = Integer.valueOf(formationArrayB[0]);
            midB = Integer.valueOf(formationArrayB[1]);
            atkB = Integer.valueOf(formationArrayB[2]);
        }else if(formationArrayB.length==4){
            defB = Integer.valueOf(formationArrayB[0]);
            midB = Integer.valueOf(formationArrayB[1])+Integer.valueOf(formationArrayB[2]);
            atkB = Integer.valueOf(formationArrayB[3]);
        }

        goalA = 0;
        goalB = 0;

        goalScorers = "";
    }

    public void midfield(){
        int avgMidA = getValue(playersA, 1+defA, 1+defA+midA);
        int avgMidB = getValue(playersB, 1+defB, 1+defA+midA);
        Random rand = new Random();
        int rndA = rand.nextInt(avgMidA);
        int rndB = rand.nextInt(avgMidB);
        if(rndA+5>=rndB){// +5 for homeplaying team
            goalA = goalA + attack(playersA, playersB, atkA, midA, defA, defB);
        } else {
            goalB = goalB + attack(playersB, playersA, atkB, midB, defB, defA);
        }
    }

    public String getScore(){
        return Integer.toString(goalA) + " - " + Integer.toString(goalB)+"?"+goalScorers;
    }

    public int attack(String[] playersAttack, String[] playersDefense,
                      int atkVal, int midVal, int defVal, int defense){

        int defendingValue = getValue(playersDefense, 1, 1+defense);
        int attackingValue = getValue(playersAttack, 1+defVal+midVal, 1+atkVal+defVal+midVal);
        int midfieldValue = getValue(playersAttack, 1+defVal, 1+defVal+midVal);

        Random rand = new Random();
        int rndAtk = rand.nextInt(attackingValue);
        int rndMid = rand.nextInt(midfieldValue);
        int rndDef = rand.nextInt(defendingValue);

        if (rndAtk>rndDef){
            int midOrAtk = rand.nextInt(midVal+atkVal+atkVal);
            int shooter = 0;
            if(midOrAtk>midVal) {
                shooter = rand.nextInt(midVal + atkVal) + defVal + 1;
            } else {
                shooter = rand.nextInt(midVal) + defVal + 1;
            }

            String a0 = playersAttack[0];
            String a1 = playersAttack[1];
            String a2 = playersAttack[2];
            String a3 = playersAttack[3];
            String a4 = playersAttack[4];
            String a5 = playersAttack[5];
            String a6 = playersAttack[6];
            String a7 = playersAttack[7];
            String a8 = playersAttack[8];
            String a9 = playersAttack[9];
            String a10 = playersAttack[10];

            String shooterName = playersAttack[shooter].split("\\?")[3];
            String shooterPos = playersAttack[shooter].split("\\?")[3];
//            String nimma = playersAttack[shooter].split("\\?")[0];
//            if(shooterName.equals("GK")){
//                String x = "";
//            }
//            String shooterName = playersAttack[shooter].split("\\?")[0];
            int shooterRating = Integer.valueOf(playersAttack[shooter].split("\\?")[4]);
            int keeperRating = Integer.valueOf(playersDefense[0].split("\\?")[4]);
            int rndShoot = rand.nextInt(shooterRating);
            int rndKeeper = rand.nextInt(keeperRating);
            if (shooterPos.equals("RW") || shooterPos.equals("LW") ||
                    shooterPos.equals("RM") || shooterPos.equals("LM")) {
                rndShoot=rndShoot+6;
                if(shooterPos.equals("CAM")||shooterPos.equals("ST")) {
                    rndShoot=rndShoot+10;
                }
            }
            if(rndShoot>rndKeeper+10){
                goalScorers=goalScorers+"*"+shooterName;
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public int getValue(String[] players, int start, int end){
        try {
            int value = 0;
            for (int i = start; i < end + 1; i++) {
                value = value + Integer.valueOf(players[i].split("\\?")[4]);
            }
//            int swek = value;
            value = value / (end - start);
            return value;
        } catch (Exception e){
            goalA = goalA+1000;
        }
        return 1;
    }
}
