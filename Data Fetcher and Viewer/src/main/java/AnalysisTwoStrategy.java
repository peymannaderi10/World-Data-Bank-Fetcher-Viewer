import java.util.HashMap;

/***
 * Analysis class deigned to perform the analysis on Air Pollution and Forest Area dataset.
 */
public class AnalysisTwoStrategy extends AnalysisStrategy {
    /***
     * Method that fetches the necessary data and performs the analysis on the data.
     * @param countryCode Takes in a string that represents the country chosen for analysis
     * @param startYear Takes in the year for which data should be acquired in integer form.
     * @param endYear Takes in the year for which data should be acquired in integer form.
     * @return returns a hashmap that contains the desired analysed data for the Air Pollution and Forest Area fetched data.
     */
    public HashMap<Integer, HashMap<String, Float>> performAnalysis(String countryCode, Integer startYear, Integer endYear) {
        // Instantiate separate reader objects for each type of data to be fetched.
        AirPollutionReader airPollutionReader = new AirPollutionReader();
        ForestAreaReader forestAreaReader = new ForestAreaReader();

        // Fetch the data.
        HashMap<Integer, Float> airPollutionData = airPollutionReader.fetchData(countryCode, startYear, endYear);
        HashMap<Integer, Float> forestAreaData = forestAreaReader.fetchData(countryCode, startYear, endYear);

        // Map years to hashmaps which map the type of data to the data itself.
        HashMap<Integer, HashMap<String, Float>> result = new HashMap();

        // Iterate over every year for which data was fetched.
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            // currentYearData maps the type of data to the data itself.
            // For example, "airPollution" maps to the quantity of air pollution for that respective year.
            HashMap<String, Float> currentYearData = new HashMap();

            float currentYearAirPollution = airPollutionData.get(currentYear);
            float currentYearForestArea = forestAreaData.get(currentYear);

            currentYearData.put("airPollution", currentYearAirPollution);
            currentYearData.put("forestArea", currentYearForestArea);

            result.put(currentYear, currentYearData);
        }

        return result;
    }
}
