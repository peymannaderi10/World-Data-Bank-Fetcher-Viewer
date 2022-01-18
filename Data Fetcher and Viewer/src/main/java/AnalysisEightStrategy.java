import java.util.HashMap;

/***
 * Analysis class deigned to perform the analysis on Government Expenditure and Health Expenditure dataset.
 */
public class AnalysisEightStrategy extends AnalysisStrategy {
    /***
     * Method that fetches the necessary data and performs the analysis on the data.
     * @param countryCode Takes in a string that represents the country chosen for analysis
     * @param startYear Takes in the year for which data should be acquired in integer form.
     * @param endYear Takes in the year for which data should be acquired in integer form.
     * @return returns a hashmap that contains the desired analysed data for the Government Expenditure and Health Expenditure
     */
    public HashMap<Integer, HashMap<String, Float>> performAnalysis(String countryCode, Integer startYear, Integer endYear) {
        // Instantiate separate reader objects for each type of data to be fetched.
        GovernmentExpenditureReader governmentExpenditureReader = new GovernmentExpenditureReader();
        HealthExpenditureGDPReader healthExpenditureGDPReader = new HealthExpenditureGDPReader();

        // Fetch the data.
        HashMap<Integer, Float> governmentExpenditureData = governmentExpenditureReader.fetchData(countryCode, startYear, endYear);
        HashMap<Integer, Float> healthExpenditureGDPData = healthExpenditureGDPReader.fetchData(countryCode, startYear, endYear);

        // Map years to hashmaps which map the type of data to the data itself.
        HashMap<Integer, HashMap<String, Float>> result = new HashMap();

        // Populate the hashmap with data.
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            HashMap<String, Float> currentYearData = new HashMap();
            float currentYearGovernmentExpenditure = governmentExpenditureData.get(currentYear);
            float currentYearHealthExpenditureGDP = healthExpenditureGDPData.get(currentYear);

            currentYearData.put("governmentExpenditure", currentYearGovernmentExpenditure);
            currentYearData.put("healthExpenditureGDP", currentYearHealthExpenditureGDP);

            result.put(currentYear, currentYearData);
        }

        return result;
    }
}
