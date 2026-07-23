package main;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    Map<String, TimeSeries> wordMap;
    TimeSeries yearMap;

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {
        wordMap = new TreeMap<>();
        yearMap = new TimeSeries();
        In in = new In(wordHistoryFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split("\t");
            if (!wordMap.containsKey(splitLine[0])) {
                TimeSeries series = new TimeSeries();
                series.put(Integer.valueOf(splitLine[1]), Double.valueOf(splitLine[2]));
                wordMap.put(splitLine[0], series);
            } else {
                wordMap.get(splitLine[0]).put(Integer.valueOf(splitLine[1]), Double.valueOf(splitLine[2]));
            }
        }
        In in1 = new In(yearHistoryFilename);
        while (!in1.isEmpty()) {
            String nextLine = in1.readLine();
            String[] splitLine = nextLine.split(",");
            yearMap.put(Integer.valueOf(splitLine[0]), Double.valueOf(splitLine[1]));
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries ts = wordMap.get(word);
        return new TimeSeries(ts, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        return wordMap.get(word);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return yearMap;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries ts = wordMap.get(word);
        TimeSeries myTs = new TimeSeries(ts, startYear, endYear);
        return myTs.dividedBy(yearMap);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if (!wordMap.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries ts = wordMap.get(word);
        return ts.dividedBy(yearMap);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            if (wordMap.containsKey(word)) {
                TimeSeries ts1 = wordMap.get(word);
                TimeSeries ts2 = new TimeSeries(ts1, startYear, endYear);
                ts = ts.plus(ts2);
            }
        }
        return ts.dividedBy(yearMap);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            if (wordMap.containsKey(word)) {
                TimeSeries ts1 = wordMap.get(word);
                ts = ts.plus(ts1);
            }
        }
        return ts.dividedBy(yearMap);
    }
}
