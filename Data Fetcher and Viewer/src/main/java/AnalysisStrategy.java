import java.util.HashMap;

/***
 * Author: Nikita Nemtcev
 * An abstract class that outlays the model for other analysis classes.
 */
public abstract class AnalysisStrategy {
    public abstract HashMap performAnalysis(String countryCode, Integer startYear, Integer endYear);
}
