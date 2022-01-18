import java.util.HashMap;

/**
 * The abstract class for a reader.
 */

public abstract class Reader {
    /**
     * An abstract method that other reader classes override.
     * This method fetches data from the World Bank data repository.
     * @param countryName is the country code, for example the country code of "Canada" is "can".
     * @param startYear is the year where data fetching begins.
     * @param endYear is the year where data fetching ends.
     * @return a hash map containing data.
     */
    public abstract HashMap fetchData(String countryName, Integer startYear, Integer endYear);
}
