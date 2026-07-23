import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries myPopulation = new TimeSeries();
        myPopulation.put(1991, 100.0);
        myPopulation.put(1992, 2.0);
        myPopulation.put(1994, 10.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }

        TimeSeries dividePopulation = catPopulation.dividedBy(myPopulation);

        List<Double> expectedDivision = new ArrayList<>();
        expectedDivision.add(0.0 / 100.0);
        expectedDivision.add(100.0 / 2.0);
        expectedDivision.add(200.0 / 10.0);

        for (int i = 0; i < expectedDivision.size(); i += 1) {
            assertThat(dividePopulation.data().get(i)).isWithin(1E-10).of(expectedDivision.get(i));
        }

        TimeSeries myYears = new TimeSeries(catPopulation, 1992, 1994);

        List<Integer> myExpectedYears = new ArrayList<>();
        myExpectedYears.add(1992);
        myExpectedYears.add(1994);

        assertThat(myYears.years()).isEqualTo(myExpectedYears);
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }
} 