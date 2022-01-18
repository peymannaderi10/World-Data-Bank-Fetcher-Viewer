import java.util.HashMap;

/***
 * Analysis class deigned to perform the analysis on government expenditure.
 * Author: Nikita Nemtcev
 */
public class AnalysisFiveStrategy extends AnalysisStrategy {
    /***
     * Method that fetches the necessary data and performs the analysis on the data.
     * @param countryCode Takes in a string that represents the country chosen for analysis
     * @param startYear Takes in the year for which data should be acquired in integer form.
     * @param endYear Takes in the year for which data should be acquired in integer form.
     * @return returns a hashmap that contains the desired analysed data for the government expenditure.
     */
    public HashMap<Integer, HashMap<String, Float>> performAnalysis(String countryCode, Integer startYear, Integer endYear) {
        // Instantiate reader object to fetch data pertaining to government expenditure on education.
        GovernmentExpenditureReader governmentExpenditureReader = new GovernmentExpenditureReader();

        // Fetch the data from the World Bank data repository.
        HashMap<Integer, Float> governmentExpenditureData = governmentExpenditureReader.fetchData(countryCode, startYear, endYear);

        // Map years to hashmaps which map the String "governmentExpenditure" to the average government expenditure on education for the respective year.
        HashMap<Integer, HashMap<String, Float>> result = new HashMap();

        float cumulativeGovernmentExpenditure = 0; // Total government expenditure for every year combined.

        // Compute the cumulative government expenditure on education.
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            float currentYearGovernmentExpenditure = governmentExpenditureData.get(currentYear);
            cumulativeGovernmentExpenditure += currentYearGovernmentExpenditure;
        }

        // Populate the hashmap by calculating averages.
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            float currentYearGovernmentExpenditure = governmentExpenditureData.get(currentYear);
            float averageGovernmentExpenditure = currentYearGovernmentExpenditure / cumulativeGovernmentExpenditure;

            HashMap<String, Float> mapDataNameToFetchedData = new HashMap();
            mapDataNameToFetchedData.put("governmentExpenditure", averageGovernmentExpenditure);
            result.put(currentYear, mapDataNameToFetchedData);

            result.put(currentYear, mapDataNameToFetchedData);
        }

        return result;
    }
}
