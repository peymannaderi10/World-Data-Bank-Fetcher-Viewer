import java.net.URL;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * Author: Nikita Nemtcev
 * The reader class that fetches data from the World Bank data repository pertaining to PM2.5 air pollution.
 */
public class AirPollutionReader extends Reader {
    /**
     * fetchData is the method that fetches data from the World Bank API using an HTTP GET request.
     * @param countryCode is the String that represents the country. For example, "can" is for Canada.
     * @param startYear is the beginning year for data fetching.
     * @param endYear is the end year for data fetching.
     * @return a hash map that maps years to PM2.5 air pollution, mean annual exposure (micrograms per cubic meter).
     */
    public HashMap<Integer, Float> fetchData(String countryCode, Integer startYear, Integer endYear) {
        // Format string containing indicator, country name, and the year range for fetching data.
        String urlString = String.format(
                "http://api.worldbank.org/v2/country/%s/indicator/EN.ATM.PM25.MC.M3?date=%d:%d&format=json",
                countryCode,
                startYear,
                endYear
        );

        float exposureForYear = 0;
        HashMap<Integer, Float> data = new HashMap<>();

        // Attempt to fetch data from the World Bank data repository.
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();

            // If HTTP response code is 200, then the data was successfully fetched.
            if (responseCode == 200) {
                String inline = "";
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }

                sc.close();
                JsonArray jsonArray = new JsonParser().parse(inline).getAsJsonArray();

                int sizeOfResults = jsonArray.get(1).getAsJsonArray().size();
                int year = 0;

                for (int i = 0; i < sizeOfResults; i++) {
                    year = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("date").getAsInt();
                    if (jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").isJsonNull()) {
                        data.put(year, (float) 0.0);
                    } else {
                        exposureForYear = jsonArray.get(1).getAsJsonArray().get(i).getAsJsonObject().get("value").getAsFloat();
                        data.put(year, exposureForYear);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("There was an error fetching data from the repository.");
        }

        return data;
    }
}
