import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/***
 * Author: Peyman Naderi and Muhammad Askri
 * Class to make the bar graph with the desired analysed data.
 */
public class BarGraph extends Viewer {

    /***
     * Constructor for the class to produce a bar graph with the provided data.
     * @param graphTitle Title for the outputted graph passed as String
     * @param hashMap a hashmap containing the data produced from performing analysis
     * @return bar graph populated with analysed data
     */
    public ChartPanel createGraph(String graphTitle, HashMap<Integer, HashMap<String, Float>> hashMap) {
        // Instantiate dataset.
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Populating dataset with every value from hashmap.
        for (Map.Entry mapElement : hashMap.entrySet()) {

            // Get every year that analysis is performed for.
            String year = String.valueOf(mapElement.getKey());

            HashMap<Integer, HashMap<String, Float>> value = (HashMap<Integer, HashMap<String, Float>>) mapElement.getValue();

            for (Map.Entry valueElement : value.entrySet()) {
                String dataName = (String) valueElement.getKey();
                double dataValue = Double.valueOf(String.valueOf(valueElement.getValue()));
                String fullDataName = dataName;

                // Convert shorthand data name to full data name with units.
                switch (dataName) {
                    case "emissions":
                        fullDataName = "CO2 emissions (metric tons per capita)";
                        break;
                    case "energyUse":
                        fullDataName = "Energy use (kg of oil equivalent per capita)";
                        break;
                    case "airPollution":
                        fullDataName = "PM2.5 air pollution (micrograms per cubic meter)";
                        break;
                    case "forestArea":
                        fullDataName = "Forest area (% of land area)";
                        break;
                    case "gdpCapita":
                        fullDataName = "GDP per capita (current US$)";
                        break;
                    case "governmentExpenditure":
                        fullDataName = "Government expenditure on education (% of GDP)";
                        break;
                    case "hospitalBeds":
                        fullDataName = "Hospital beds (per 1,000 people)";
                        break;
                    case "healthExpenditure":
                        fullDataName = "Health expenditure per capita (US$)";
                        break;
                    case "mortalityRate":
                        fullDataName = "Mortality rate, infant (per 1,000 live births)";
                        break;
                    case "healthExpenditureGDP":
                        fullDataName = "Health expenditure (% of GDP)";
                        break;
                }

                dataset.setValue(dataValue, fullDataName, year);
            }
        }

        // Entering all the required variables for bar graph.
        JFreeChart barChart = ChartFactory.createBarChart(graphTitle, "Year", "", dataset, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel BarchartPanel = new ChartPanel(barChart);
        BarchartPanel.setPreferredSize(new Dimension(400, 300));
        BarchartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        BarchartPanel.setBackground(Color.white);

        return BarchartPanel;
    }
}
