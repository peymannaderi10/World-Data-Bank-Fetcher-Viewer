/**
 * A class that contains information about countries, for example, what countries can be analyzed in this program.
 * Author: Usman Khan
 */
public class Country {
    // Country model class for storing name, from date and to date in array list.
    private String name;
    private String fromDate;
    private String toDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * This function validates if the country is there in the list of
     * valid countries.
     * @param countryToBeAnalyzed is the country to be validated
     * @return boolean indicating if the country is valid
     */

    public static boolean validateCountry (String countryToBeAnalyzed)
    {
        String[] countriesAllowedforAnalysis = new String[]{"Canada","United Kingdom","USA and Puerto Rico","France","Germany"};
        for(String countryAllowed : countriesAllowedforAnalysis) { //loops through the list of countries
            if(countryAllowed.equals(countryToBeAnalyzed)) //checks if selected country is in list of countries
                return true;
        }
        return false;
    }
}