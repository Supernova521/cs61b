package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap myMap;

    public HistoryTextHandler(NGramMap map) {
        myMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        StringBuilder response = new StringBuilder();
        for (String word : words) {
            response.append(word);
            response.append(": ");
            TimeSeries list = myMap.weightHistory(word, startYear, endYear);
            response.append(list.toString());
            response.append("\n");
        }
        return response.toString();
    }
}
