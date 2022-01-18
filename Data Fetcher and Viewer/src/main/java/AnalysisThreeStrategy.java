import java.util.HashMap;

/***
 * Author: Nikita Nemtcev
 * Analysis class designed to perform the analysis on CO2 emissions and GDP per capita dataset.
 */
public class AnalysisThreeStrategy extends AnalysisStrategy {
    /***
     * Method that fetches the necessary data and performs the analysis on the data.
     * @param countryCode Takes in a string that represents the coutry chosen for analysis
     * @param startYear Takes in the year for which data should be acquired in integer form.
     * @param endYear Takes in the year for which data should be acquired in integer form.
     * @return returns a hashmap that contains the desired abnalysed data for the CO2 emissions and GDP per capita fetched data.
     */
    public HashMap<Integer, HashMap<String, Float>> performAnalysis(String countryCode, Integer startYear, Integer endYear) {
        // Instantiate separate reader objects for each type of data to be fetched.
        CO2EmissionsReader emissionsReader = new CO2EmissionsReader();
        GDPCapitaReader gdpCapitaReader = new GDPCapitaReader();

        // Fetch the data.
        HashMap<Integer, Float> emissionsData = emissionsReader.fetchData(countryCode, startYear, endYear);
        HashMap<Integer, Float> gdpCapitaData = gdpCapitaReader.fetchData(countryCode, startYear, endYear);

        // Map years to a hashmap which maps the type of data to the data itself.
        HashMap<Integer, HashMap<String, Float>> result = new HashMap();

        // Iterate over every year for which data was fetched.
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            HashMap<String, Float> currentYearData = new HashMap();
            float currentYearEmissions = emissionsData.get(currentYear);
            float currentYearGDP = gdpCapitaData.get(currentYear);

            currentYearData.put("emissions", currentYearEmissions);
            currentYearData.put("gdpCapita", currentYearGDP);

            result.put(currentYear, currentYearData);
        }

        return result;
    }
}
