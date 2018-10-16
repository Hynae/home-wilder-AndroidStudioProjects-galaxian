package fr.wcs.galaxian;

import java.util.Date;

public class UserScoreModel {
    private String pseudo;
    private int score;


    public UserScoreModel(String pseudo, int score) {
        this.pseudo = pseudo;
        this.score = score;
    }

    public String getpseudo() {
        return pseudo;
    }

    public int getScore() {
        return score;
    }


    public UserScoreModel() {
    }
}
