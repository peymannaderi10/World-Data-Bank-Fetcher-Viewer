import java.util.HashMap;

/***
 * Analysis class deigned to perform the analysis on CO2 emissions, energy use and air pollution dataset.
 */
public class AnalysisOneStrategy extends AnalysisStrategy {
    /***
     * Method that fetches the necessary data and performs the analysis on the data.
     * @param countryCode Takes in a string that represents the country chosen for analysis
     * @param startYear Takes in the year for which data should be acquired in integer form.
     * @param endYear Takes in the year for which data should be acquired in integer form.
     * @return returns a hashmap that contains the desired analysed data for CO2 emissions, energy use and air pollution fetched data.
     */
    public HashMap<Integer, HashMap<String, Float>> performAnalysis(String countryCode, Integer startYear, Integer endYear) {
        // Instantiate separate reader objects for each type of data to be fetched.
        CO2EmissionsReader emissionsReader = new CO2EmissionsReader();
        EnergyUseReader energyUseReader = new EnergyUseReader();
        AirPollutionReader airPollutionReader = new AirPollutionReader();

        // Fetch the data.
        HashMap<Integer, Float> emissionsData = emissionsReader.fetchData(countryCode, startYear, endYear);
        HashMap<Integer, Float> energyUseData = energyUseReader.fetchData(countryCode, startYear, endYear);
        HashMap<Integer, Float> airPollutionData = airPollutionReader.fetchData(countryCode, startYear, endYear);

        // Map years to hashmaps which map the type of data to the data itself.
        HashMap<Integer, HashMap<String, Float>> result = new HashMap();

        // Iterate over every year for which data was fetched.
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            // currentYearData maps the type of data to the data itself.
            // For example, "emissions" maps to the quantity of CO2 emissions for that respective year.
            HashMap<String, Float> currentYearData = new HashMap();

            float currentYearEmissions = emissionsData.get(currentYear);
            float currentYearEnergyUse = energyUseData.get(currentYear);
            float currentYearAirPollution = airPollutionData.get(currentYear);

            currentYearData.put("emissions", currentYearEmissions);
            currentYearData.put("energyUse", currentYearEnergyUse);
            currentYearData.put("airPollution", currentYearAirPollution);

            result.put(currentYear, currentYearData);
        }

        return result;
    }
}
