import java.util.HashMap;

/***
 * Author: Nikita Nemtcev and Muhammad Askri
 * Analysis class designed to perform the analysis on infant mortality rate and Health Expenditure per Capita dataset.
 */
public class AnalysisSevenStrategy extends AnalysisStrategy {
    /***
     * Method that fetches the necessary data and performs the analysis on the data.
     * @param countryCode Takes in a string that represents the country chosen for analysis
     * @param startYear Takes in the year for which data should be acquired in integer form.
     * @param endYear Takes in the year for which data should be acquired in integer form.
     * @return returns a hashmap that contains the desired analysed data for the infant mortality rate and Health Expenditure per Capita fetched data.
     */
    public HashMap<Integer, HashMap<String, Float>> performAnalysis(String countryCode, Integer startYear, Integer endYear) {
        // Instantiate separate reader objects for each type of data to be fetched.
        HealthExpenditureCapitaReader healthExpenditureReader = new HealthExpenditureCapitaReader();
        MortalityRateInfantReader mortalityRateInfantReader = new MortalityRateInfantReader();

        // Fetch the data.
        HashMap<Integer, Float> healthExpenditureData = healthExpenditureReader.fetchData(countryCode, startYear, endYear);
        HashMap<Integer, Float> mortalityRateInfantData = mortalityRateInfantReader.fetchData(countryCode, startYear, endYear);

        // Map years to hashmaps which map the type of data to the data itself.
        HashMap<Integer, HashMap<String, Float>> result = new HashMap();

        // Populate the hashmap with data.
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            HashMap<String, Float> currentYearData = new HashMap();
            float currentYearHealthExpenditure = healthExpenditureData.get(currentYear);
            float currentYearMortalityRateInfant = mortalityRateInfantData.get(currentYear);

            currentYearData.put("healthExpenditure", currentYearHealthExpenditure);
            currentYearData.put("mortalityRate", currentYearMortalityRateInfant);

            result.put(currentYear, currentYearData);
        }

        return result;
    }
}
