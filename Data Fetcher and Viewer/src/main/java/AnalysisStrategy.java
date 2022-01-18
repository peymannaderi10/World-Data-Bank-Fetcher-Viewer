import java.util.HashMap;

/***
 * An abstract class that outlays the model for other analysis classes.
 */
public abstract class AnalysisStrategy {
    public abstract HashMap performAnalysis(String countryCode, Integer startYear, Integer endYear);
}
