import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

/***
 * Class to make the line graph with the desired analysed data.
 */
public class LineGraph extends Viewer {

    /***
     * Method that creates a line graph with data that was fetched from the World Bank data repository.
     * @param graphTitle Tittle for the outputted graph passed as String
     * @param data Haspmap containing the data produced from performing analysis
     * @return line graph populated with analysed data
     */
    public ChartPanel createGraph(String graphTitle, HashMap<Integer, HashMap<String, Float>> data) {
        // Compute the number of series (i.e. one series, two series, or three series).
        Integer numSeries = data.values().iterator().next().size();

        // Create an array of fixed length that stores the series names.
        // For example, ["emissions", "energyUse", "airPollution"].
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Iterate over the data in the data hashmap that was fetched from the World Bank data repository.
        for (Map.Entry mapElement : data.entrySet()) {
            // The current year for the data.
            Integer year = Integer.parseInt(String.valueOf(mapElement.getKey()));

            // The hashmap containing data corresponding to that year.
            HashMap<Integer, HashMap<String, Float>> value = (HashMap<Integer, HashMap<String, Float>>) mapElement.getValue();

            // Iterate over the data in the above hashmap.
            for (Map.Entry valueElement : value.entrySet()) {
                String shortenedDataName = (String) valueElement.getKey();
                String fullDataName = "";

                switch (shortenedDataName) {
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

                double dataValue = Double.valueOf(String.valueOf(valueElement.getValue()));
                dataset.addValue(dataValue, fullDataName, year);
            }
        }

        JFreeChart chart = ChartFactory.createLineChart(graphTitle, "Year", "", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        chart.getLegend().setFrame(BlockBorder.NONE);
        chart.setTitle(new TextTitle(graphTitle, new Font("Serif", java.awt.Font.BOLD, 18)));

        ChartPanel linechartPanel = new ChartPanel(chart);
        linechartPanel.setPreferredSize(new Dimension(400, 300));
        linechartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        linechartPanel.setBackground(Color.white);

        return linechartPanel;
    }
}