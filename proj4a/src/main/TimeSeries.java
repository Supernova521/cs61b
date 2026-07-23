package main;

import java.util.*;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        List<Integer> yearList = ts.years();
        List<Double> dataList = ts.data();
        for (int i = 0; i < ts.size(); i++) {
            if (yearList.get(i) >= startYear && yearList.get(i) <= endYear) {
                this.put(yearList.get(i), dataList.get(i));
            }
        }
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        Set<Integer> keys = this.keySet();
        return new ArrayList<>(keys);
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        Collection<Double> values = this.values();
        return new ArrayList<>(values);
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries thingToReturn = new TimeSeries(this, MIN_YEAR, MAX_YEAR);
        List<Integer> keys = ts.years();
        List<Double> values = ts.data();
        for (int i = 0; i < ts.size(); i++) {
            if (thingToReturn.containsKey(keys.get(i))) {
                thingToReturn.put(keys.get(i), this.get(keys.get(i)) + values.get(i));
            } else {
                thingToReturn.put(keys.get(i), values.get(i));
            }
        }
        return thingToReturn;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries thingToReturn = new TimeSeries(ts, MIN_YEAR, MAX_YEAR);
        List<Integer> keys = this.years();
        List<Integer> tsKeys = ts.years();
        for (Integer key : keys) {
            if (thingToReturn.containsKey(key)) {
                thingToReturn.put(key, this.get(key) / thingToReturn.get(key));
            } else {
                throw new IllegalArgumentException();
            }
        }
        for (Integer key : tsKeys) {
            if (!keys.contains(key)) {
                thingToReturn.remove(key);
            }
        }
        return thingToReturn;
    }
}
