import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/***
 * Class to make the piechart with the desired analysed data.
 */
public class PieGraph extends Viewer {
    /***
     * A method that produces a piechart populated with analysed data.
     * @param graphTitle Tittle for graph passed as String
     * @param data a hashmap containing the data produced from performing analysis
     * @return piechart populated with analysed data
     */
    public ChartPanel createGraph(String graphTitle, HashMap<Integer, HashMap<String, Float>> data) throws Exception {
        // Compute the number of series (i.e. one series, two series, or three series).
        Integer numSeries = data.values().iterator().next().size();

        // Pie charts can only render data for one series.
        // If there are multiple series in the data hashmap, return an empty pie graph (nothing).
        if (numSeries != 1) {
            throw new Exception("Pie graphs cannot render data with more than one series.");
        }

        DefaultPieDataset dataset = new DefaultPieDataset();

        // Iterate over the data hashmap and extract the year as well as the corresponding data.
        // Then, add the data to the dataset.
        for (Map.Entry mapElement : data.entrySet()) {
            Integer year = Integer.parseInt(String.valueOf(mapElement.getKey()));

            HashMap<Integer, HashMap<String, Float>> value = (HashMap<Integer, HashMap<String, Float>>) mapElement.getValue();

            for (Map.Entry valueElement : value.entrySet()) {
                double dataValue = Double.valueOf(String.valueOf(valueElement.getValue()));
                dataset.setValue(year, dataValue);
            }
        }

        JFreeChart pieChart = ChartFactory.createPieChart(graphTitle, dataset, true, true, false);
        ChartPanel piechartPanel = new ChartPanel(pieChart);

        piechartPanel.setPreferredSize(new Dimension(400, 300));
        piechartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        piechartPanel.setBackground(Color.white);
        piechartPanel.setVisible(true);

        return piechartPanel;
    }
}
