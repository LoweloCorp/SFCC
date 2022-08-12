package com.lowelostudents.caloriecounter.services;

import com.lowelostudents.caloriecounter.enums.AggregationType;
import com.lowelostudents.caloriecounter.models.entities.Food;

import java.util.List;
import java.util.stream.Collectors;

import info.debatty.java.stringsimilarity.Levenshtein;

// TODO sort lists
public class FilterService {
    private static FilterService instance;

    public static synchronized FilterService getInstance() {
        if (instance == null) instance = new FilterService();

        return instance;
    }

    public List<Food> filterListByLevenshtein(List<Food> list, String string) {
        Levenshtein levenshtein = new Levenshtein();

        double[] scores = new double[list.size()];
        double tempScore;
        Food tempItem;

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

    public List<Food> filterListByToggle(List<Food> list, boolean initial, boolean subsequent, AggregationType initialType) {
        AggregationType subsequentType = initialType == AggregationType.FOOD ? AggregationType.MEAL : AggregationType.FOOD;
        List<Food> result;

        if (!initial) {
            if (subsequent) {
                result = list.stream().filter(item -> item.getAggregationType() != initialType).collect(Collectors.toList());
            } else {
                result = list.stream().filter(item -> item.getAggregationType() != initialType && item.getAggregationType() != subsequentType).collect(Collectors.toList());
            }
        } else {
            if (subsequent) {
                result = list;
            } else {
                result = list.stream().filter(item -> item.getAggregationType() != subsequentType).collect(Collectors.toList());
            }
        }

        return result;
    }
}
