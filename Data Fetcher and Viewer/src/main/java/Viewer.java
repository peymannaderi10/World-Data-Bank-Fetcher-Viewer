import java.util.HashMap;
import org.jfree.chart.ChartPanel;

/***
 * Abstract class that provides a layout for all the graph classes.
 */
public abstract class Viewer {
    /**
     * Method that creates a graph that renders fetched data.
     * @param selectedAnalysisType is the String that represents the analysis type.
     * @param data is the data that was fetched from the World Bank data repository API.
     * @return a ChartPanel object that represents the graph.
     * @throws Exception if the graph is unable to represent the data (for example, a pie graph cannot represent data with more than one series).
     */
    public abstract ChartPanel createGraph(String selectedAnalysisType, HashMap<Integer, HashMap<String, Float>> data) throws Exception;
}
