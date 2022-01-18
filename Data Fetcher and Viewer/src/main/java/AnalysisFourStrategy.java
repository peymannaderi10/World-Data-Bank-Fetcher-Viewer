import java.util.HashMap;

/***
 * Author: Nikita Nemtcev and Muhammad Askri
 * Analysis class designed to perform the analysis on forest area dataset.
 */
public class AnalysisFourStrategy extends AnalysisStrategy {
    /***
     * Method that fetches the necessary data and performs the analysis on the data.
     * @param countryCode Takes in a string that represents the country chosen for analysis
     * @param startYear Takes in the year for which data should be acquired in integer form.
     * @param endYear Takes in the year for which data should be acquired in integer form.
     * @return returns a hashmap that contains the desired analysed data for the forest area fetched data.
     */
    public HashMap<Integer, HashMap<String, Float>> performAnalysis(String countryCode, Integer startYear, Integer endYear) {
        // Instantiate reader object to fetch data pertaining to forest area.
        ForestAreaReader forestAreaReader = new ForestAreaReader();

        // Fetch the data from the World Bank data repository.
        HashMap<Integer, Float> forestAreaData = forestAreaReader.fetchData(countryCode, startYear, endYear);

        // Map years to hashmaps which map the String "forestArea" to the average forest area (% of land area) for the respective year.
        HashMap<Integer, HashMap<String, Float>> result = new HashMap();

        float cumulativeForestArea = 0; // Total forest area for every year combined.

        // Compute the cumulative forest area.
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            float currentYearForestArea = forestAreaData.get(currentYear);
            cumulativeForestArea += currentYearForestArea;
        }

        // Populate the hashmap by calculating averages.
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            float currentYearForestArea = forestAreaData.get(currentYear);
            float averageForestArea = currentYearForestArea / cumulativeForestArea;

            HashMap<String, Float> mapDataNameToFetchedData = new HashMap();
            mapDataNameToFetchedData.put("forestArea", averageForestArea);
            result.put(currentYear, mapDataNameToFetchedData);
        }

        return result;
    }
}
