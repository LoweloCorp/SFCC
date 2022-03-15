package com.lowelostudents.caloriecounter.services;

import android.util.Log;

import com.lowelostudents.caloriecounter.models.entities.Nutrients;

import java.util.List;
import java.util.logging.Filter;

import info.debatty.java.stringsimilarity.Levenshtein;

// TODO sort lists
public class FilterService {
    private static FilterService instance;

    public static synchronized FilterService getInstance() {
        if(instance == null) instance = new FilterService();

        return instance;
    }

    public <T extends Nutrients> List<T> filterListByLevenshtein (List<T> list, String string) {
        Levenshtein levenshtein = new Levenshtein();
        double[] scores = new double[list.size()];
        double tempScore;
        T tempItem;
        for (int i = 0; i < list.size(); i++) {
            scores[i] = levenshtein.distance(list.get(i).getName(), string);
        }

        for (int i = 0; i < scores.length; i++) {
            for (int j = 0; j < scores.length; j++) {
                if (scores[i] < scores[j]) {
                    tempScore = scores[i];
                    tempItem = list.get(i);
                    scores[i] = scores[j];
                    list.set(i, list.get(j));
                    scores[j] = tempScore;
                    list.set(j, tempItem);
                }
            }
        }

        return list;
    }
}
