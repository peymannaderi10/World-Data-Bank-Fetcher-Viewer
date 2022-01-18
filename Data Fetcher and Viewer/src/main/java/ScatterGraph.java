import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.*;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

/***
 * Author: Peyman Naderi
 * Class to produce scatter plot for analysed data.
 */
public class ScatterGraph extends Viewer {

    /***
     * A method that produces a scatter plot with the provided data.
     * @param graphTitle title for the outputted graph passed as String
     * @param data a hashmap containing the data produced from performing analysis
     * @return scatter plot populated with analysed data
     */
    public ChartPanel createGraph(String graphTitle, HashMap<Integer, HashMap<String, Float>> data) {
        // Compute the number of series (i.e. one series, two series, or three series).
        Integer numSeries = data.values().iterator().next().size();

        // Create an array of fixed length that stores the series names.
        // For example, ["emissions", "energyUse", "airPollution"].
        String[] seriesNames = data.values().iterator().next().keySet().toArray(new String[numSeries]);
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        // Map the series name to a corresponding TimeSeries object.
        HashMap<String, TimeSeries> series = new HashMap();

        // Populate the series hashmap.
        for (String seriesName : seriesNames) {
            if (seriesName.equals("emissions")) {
                series.put(seriesName, new TimeSeries("CO2 emissions (metric tons per capita)"));
            } else if (seriesName.equals("energyUse")) {
                series.put(seriesName, new TimeSeries("Energy use (kg of oil equivalent per capita"));
            } else if (seriesName.equals("airPollution")) {
                series.put(seriesName, new TimeSeries("PM2.5 air pollution (micrograms per cubic meter)"));
            } else if (seriesName.equals("forestArea")) {
                series.put(seriesName, new TimeSeries("Forest area (% of land area)"));
            } else if (seriesName.equals("gdpCapita")) {
                series.put(seriesName, new TimeSeries("GDP per capita (current US$)"));
            } else if (seriesName.equals("governmentExpenditure")) {
                series.put(seriesName, new TimeSeries("Government expenditure on education (% of GDP)"));
            } else if (seriesName.equals("hospitalBeds")) {
                series.put(seriesName, new TimeSeries("Hospital beds (per 1,000 people)"));
            } else if (seriesName.equals("healthExpenditure")) {
                series.put(seriesName, new TimeSeries("Health expenditure per capita (US$)"));
            } else if (seriesName.equals("mortalityRate")) {
                series.put(seriesName, new TimeSeries("Mortality rate, infant (per 1,000 live births)"));
            } else if (seriesName.equals("healthExpenditureGDP")) {
                series.put(seriesName, new TimeSeries("Health expenditure (% of GDP)"));
            }
        }

        // Iterate over the data in the data hashmap that was fetched from the World Bank data repository.
        for (Map.Entry mapElement : data.entrySet()) {
            // The current year for the data.
            Integer year = Integer.parseInt(String.valueOf(mapElement.getKey()));

            // The hashmap containing data corresponding to that year.
            HashMap<Integer, HashMap<String, Float>> value = (HashMap<Integer, HashMap<String, Float>>) mapElement.getValue();

            // Iterate over the data in the above hashmap.
            for (Map.Entry valueElement : value.entrySet()) {
                String dataName = (String) valueElement.getKey();
                double dataValue = Double.valueOf(String.valueOf(valueElement.getValue()));

                TimeSeries currentSeries = series.get(dataName);
                currentSeries.add(new Year(year), dataValue);
            }
        }

        // Add each series to the dataset.
        for (int i = 0; i < numSeries; i++) {
            String currentSeries = seriesNames[i];
            TimeSeries timeSeries = series.get(currentSeries);
            dataset.addSeries(timeSeries);
        }

        XYPlot plot = new XYPlot();

        plot.setDataset(0, dataset);

        XYItemRenderer itemRenderer = new XYLineAndShapeRenderer(false, true);

        plot.setRenderer(0, itemRenderer);
        DateAxis domainAxis = new DateAxis("Year");
        plot.setDomainAxis(domainAxis);

        plot.setRangeAxis(new NumberAxis(""));

        plot.mapDatasetToRangeAxis(0, 0);

        JFreeChart scatterChart = new JFreeChart(graphTitle,
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        ChartPanel scatterchartPanel = new ChartPanel(scatterChart);
        scatterchartPanel.setPreferredSize(new Dimension(400, 500));
        scatterchartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scatterchartPanel.setBackground(Color.white);

        return scatterchartPanel;
    }
}
