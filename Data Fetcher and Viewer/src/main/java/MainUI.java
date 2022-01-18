import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import java.io.*;


public class MainUI extends JFrame {
    JComboBox<String> methodsList;
    JComboBox<String> countriesList;
    JComboBox<String> fromList;
    JComboBox<String> toList;
    JComboBox<String> viewsList;
    List<Country> countryModelList = new ArrayList<Country>();
    String currentlySelectedMethod = "";

    private static final long serialVersionUID = 1L;

    private static MainUI instance;

    public static MainUI getInstance() {
        if (instance == null)
            instance = new MainUI();

        return instance;
    }

    private ArrayList<Viewer> currentListOfViewers = new ArrayList();

    /***
     * Removes punctuation from string
     * @param str String from which punctuation is to be removed
     * @return non punctuated string
     */
    private String removePunctuationFromString(String str) {
        String strNoPunctuation = "";
        for (Character c : str.toCharArray()) {
            if (Character.isLetterOrDigit(c) || Character.isWhitespace(c) || c == ',' || c == '-' || c == '(' || c == ')') {
                strNoPunctuation += c;
            }
        }

        return strNoPunctuation;
    }

    /***
     * Method to Removes the viewer from currentListOfViewers.
     * @param viewerType type of viewer or graph passed as an instance of Object class
     * @return None
     */
    private void removeViewerFromCurrentListOfViewers(Object viewerType) {
        for (int i = 0; i < currentListOfViewers.size(); i++) {
            Viewer viewer = currentListOfViewers.get(i);

            if (viewerType instanceof PieGraph && viewer instanceof PieGraph) {
                currentListOfViewers.remove(viewer);
            } else if (viewerType instanceof LineGraph && viewer instanceof LineGraph) {
                currentListOfViewers.remove(viewer);
            } else if (viewerType instanceof BarGraph && viewer instanceof BarGraph) {
                currentListOfViewers.remove(viewer);
            } else if (viewerType instanceof ScatterGraph && viewer instanceof ScatterGraph) {
                currentListOfViewers.remove(viewer);
            }
        }
    }

    /***
     * Method to Check if currentListOfViewers contains the given viewer.
     * @param viewerType type of viewer or graph passed as an instance of Object class
     * @return true if viewers are present are in current list of viewers, else return false
     */
    private boolean checkIfCurrentListOfViewersContainsViewer(Viewer viewerType) {
        if (viewerType instanceof PieGraph) {
            for (Viewer viewer : currentListOfViewers) {
                if (viewer instanceof PieGraph) {
                    return true;
                }
            }

            return false;
        }

        if (viewerType instanceof LineGraph) {
            for (Viewer viewer : currentListOfViewers) {
                if (viewer instanceof LineGraph) {
                    return true;
                }
            }

            return false;
        }

        if (viewerType instanceof BarGraph) {
            for (Viewer viewer : currentListOfViewers) {
                if (viewer instanceof BarGraph) {
                    return true;
                }
            }

            return false;
        }

        if (viewerType instanceof ScatterGraph) {
            for (Viewer viewer : currentListOfViewers) {
                if (viewer instanceof ScatterGraph) {
                    return true;
                }
            }

            return false;
        }

        return false;
    }

    /***
     * Reads countries from the CSV file and stores them in array.
     * @return An arraylist that contains String. It holds the information about every country.
     */
    private ArrayList<String> readCountriesFromCSVFile() {
        ArrayList<String> countryNames = new ArrayList();

        try {
            String line;
            BufferedReader br = new BufferedReader(new FileReader("/Users/Owner-PC/OneDrive/Desktop/PROJECTS/Data Fetcher and Viewer/src/country_list.csv"));
            br.readLine(); // Skip the first line which contains the column name (Country Name).

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                //read country name, from year and to year from csv file and create coutry model
                String countryName = fields[1];
                String from = fields[6];
                String to = fields[7];

                //in csv file , we have to date Now so we will replace that Now with current year.
                if(to.equalsIgnoreCase("Now")){
                    to="2021";
                }

                //create country model and add it to country list
                Country country = new Country();
                country.setName(countryName);
                country.setFromDate(from);
                country.setToDate(to);

                countryModelList.add(country);

                countryNames.add(removePunctuationFromString(fields[1]));
            }
        } catch (Exception e) {
            System.out.println("Could not read the file containing the list of countries.");
        }

        return countryNames;
    }

    /***
     * Class that produces the user interface for the program.
     */
    private MainUI() {
        // Set window title.
        super("Country Statistics");

        // Set top bar.
        JLabel chooseCountryLabel = new JLabel("Choose a country: ");

        // Countries that will appear in dropdown menu.
        Vector<String> countriesInDropdown = new Vector<String>();

        try {
            // Read countries from the country_list.csv file.
            ArrayList<String> countriesFromCSVFile = this.readCountriesFromCSVFile();

            // Add each country to the dropdown menu.
            for (int i = 0; i < countriesFromCSVFile.size(); i++) {
                countriesInDropdown.add(countriesFromCSVFile.get(i));
            }

        } catch (Exception e) {
            System.out.println("Could not read the file containing the list of countries.");
        }

        countriesList = new JComboBox<String>(countriesInDropdown);

        // If a country for which analysis is not allowed is chosen a message pops up
        countriesList.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                if(!Country.validateCountry(getCountry())){

                    JOptionPane.showMessageDialog(null, "Country is not valid");
                }

            }
        });

        //Generating the data range selection mechanism
        JLabel from = new JLabel("From");
        JLabel to = new JLabel("To");
        Vector<String> years = new Vector<String>();

        for (int i = 2020; i >= 1962; i--) {
            years.add("" + i);
        }

        fromList = new JComboBox<String>(years);
        toList = new JComboBox<String>(years);
        fromList.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get selected country name from combo box
                String selectedCountry = countriesList.getSelectedItem().toString();
                Country selectedCountryModel = null;

                //iterate over the country model list and pick up complete country model
                for(Country country: countryModelList){

                    if(country.getName().equals(selectedCountry)){
                        //if country find then initialize it
                        selectedCountryModel = country;
                    }

                }

                //get to year value from combo box
                String toYear = toList.getSelectedItem().toString();

                //get from year value from combo box
                String fromYear = fromList.getSelectedItem().toString();

                //from and to date of combo boxes should be in range of from and to dates of csv file
                if (!validDate(fromYear, toYear)) {
                    JOptionPane.showMessageDialog(null, "Please enter valid date range");
                }

            }
        });

        toList.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //get selected country name from combo box
                String selectedCountry = countriesList.getSelectedItem().toString();
                Country selectedCountryModel = null;

                //iterate over the country model list and pick up complete country model
                for(Country country: countryModelList){

                    if(country.getName().equals(selectedCountry)){
                        //if country find then initilize it
                        selectedCountryModel = country;
                    }

                }

                //get to year value from combo box
                String toYear = toList.getSelectedItem().toString();

                //get from year value from combo box
                String fromYear = fromList.getSelectedItem().toString();

                //from and to date of combo boxes should be in range of from and to dates of csv file
                if (!validDate(fromYear, toYear)) {
                    JOptionPane.showMessageDialog(null, "Please enter valid date range");
                }
            }
        });

        // Set charts region
        JPanel west = new JPanel();
        west.setLayout(new GridLayout(2, 0));

        JPanel north = new JPanel();
        north.add(chooseCountryLabel);
        north.add(countriesList);
        north.add(from);
        north.add(fromList);
        north.add(to);
        north.add(toList);

        // Set bottom bar
        JButton recalculate = new JButton("Recalculate");
        recalculate.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                west.removeAll();
                MainUI.getInstance().pack();
                displayResults(west);
            }
        });

        JLabel viewsLabel = new JLabel("Available Views: ");

        Vector<String> viewsNames = new Vector<String>();
        viewsNames.add("Pie Chart");
        viewsNames.add("Line Chart");
        viewsNames.add("Bar Chart");
        viewsNames.add("Scatter Chart");
        viewsList = new JComboBox<String>(viewsNames);

        JButton addView = new JButton("+");
        addView.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                // Currently selected viewer in the dropdown menu.
                String selectedViewer = String.valueOf(viewsList.getSelectedItem());

                // Currently selected analysis type in the dropdown menu.
                String selectedAnalysisType = String.valueOf(methodsList.getSelectedItem());

                // Check if selected analysis type is one series.
                Boolean analysisTypeIsOneSeries = selectedAnalysisType.equals("Average forest area") || selectedAnalysisType.equals("Average of Gov. expenditure on education");

                if (selectedViewer.equals("Pie Chart")) {
                    if (!analysisTypeIsOneSeries) {
                        JOptionPane.showMessageDialog(null, "The selected analysis type is not one series, so the pie chart cannot render this data.");
                        return;
                    }

                    if (checkIfCurrentListOfViewersContainsViewer(new PieGraph())) {
                        JOptionPane.showMessageDialog(null, "Error: The pie chart viewer has already been added.");
                        return;
                    }

                    currentListOfViewers.add(new PieGraph());
                } else if (selectedViewer.equals("Line Chart")) {
                    if (checkIfCurrentListOfViewersContainsViewer(new LineGraph())) {
                        JOptionPane.showMessageDialog(null, "Error: The line chart viewer has already been added.");
                        return;
                    }

                    currentListOfViewers.add(new LineGraph());
                } else if (selectedViewer.equals("Bar Chart")) {
                    if (checkIfCurrentListOfViewersContainsViewer(new BarGraph())) {
                        JOptionPane.showMessageDialog(null, "Error: The bar chart viewer has already been added.");
                        return;
                    }

                    currentListOfViewers.add(new BarGraph());
                } else if (selectedViewer.equals("Scatter Chart")) {
                    if (checkIfCurrentListOfViewersContainsViewer(new ScatterGraph())) {
                        JOptionPane.showMessageDialog(null, "Error: The scatter chart viewer has already been added.");
                        return;
                    }

                    currentListOfViewers.add(new ScatterGraph());
                }
            }
        });
      
      	JButton exit = new JButton("Exit");
        exit.addActionListener(e -> System.exit(0));
        north.add(exit);
      

        // Button to remove graphs from the current viewers list
        JButton removeView = new JButton("-");

        // Adding functionality to the viewer remove button
        removeView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedViewer = String.valueOf(viewsList.getSelectedItem());

                if (selectedViewer.equals("Pie Chart")) {
                    if (checkIfCurrentListOfViewersContainsViewer(new PieGraph())) {
                        removeViewerFromCurrentListOfViewers(new PieGraph());
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: the pie chart viewer has not yet been added.");
                    }
                } else if (selectedViewer.equals("Line Chart")) {
                    if (checkIfCurrentListOfViewersContainsViewer(new LineGraph())) {
                        removeViewerFromCurrentListOfViewers(new LineGraph());
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: the line chart viewer has not yet been added.");
                    }
                } else if (selectedViewer.equals("Bar Chart")) {
                    if (checkIfCurrentListOfViewersContainsViewer(new BarGraph())) {
                        removeViewerFromCurrentListOfViewers(new BarGraph());
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: the bar chart viewer has not yet been added.");
                    }
                } else if (selectedViewer.equals("Scatter Chart")) {
                    if (checkIfCurrentListOfViewersContainsViewer(new ScatterGraph())) {
                        removeViewerFromCurrentListOfViewers(new ScatterGraph());
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: the scatter chart viewer has not yet been added.");
                    }
                }
            }
        });

        JLabel methodLabel = new JLabel("Choose analysis method: ");

        // Add the eight different analysis types to the dropdown menu.
        Vector<String> methodsNames = new Vector<String>();
        methodsNames.add("CO2 emissions vs. Energy use vs. PM2.5 air pollution");
        methodsNames.add("PM2.5 air pollution vs. Forest area");
        methodsNames.add("Ratio of CO2 emissions and GDP per capita");
        methodsNames.add("Average forest area");
        methodsNames.add("Average of Gov. expenditure on education");
        methodsNames.add("Ratio of Hospital beds and Current health expenditure");
        methodsNames.add("Current health expenditure per capita vs. Mortality rate");
        methodsNames.add("Ratio of Gov. expenditure on education vs. Current health expenditure");
        methodsList = new JComboBox<String>(methodsNames);

        // Keep track of the currently selected analysis type.
        // This is used for checking if the user has selected a new analysis type.
        currentlySelectedMethod = methodsList.getItemAt(0);

        // Clear the list of viewers that were added if the user selects a new analysis type.
        // However, if the analysis type stays the same, the list of viewers also stays the same.
        methodsList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newlySelectedMethod = String.valueOf(methodsList.getSelectedItem());
                if (!newlySelectedMethod.equals(currentlySelectedMethod)) {
                    currentListOfViewers.clear();
                    west.removeAll();
                    MainUI.getInstance().pack();
                    JOptionPane.showMessageDialog(null, "New analysis type selected, all added viewers have been removed.");
                }

                currentlySelectedMethod = newlySelectedMethod;
            }
        });

        JPanel south = new JPanel();
        south.add(viewsLabel);
        south.add(viewsList);
        south.add(addView);
        south.add(removeView);

        south.add(methodLabel);
        south.add(methodsList);
        south.add(recalculate);

        JPanel east = new JPanel();

        getContentPane().add(north, BorderLayout.NORTH);
        getContentPane().add(east, BorderLayout.EAST);
        getContentPane().add(south, BorderLayout.SOUTH);
        getContentPane().add(west, BorderLayout.WEST);
    }

    /***
     * Returns the name of country selected by user
     * @return String containing the name of se lected country
     */
    private String getCountry() {
        return (String) countriesList.getSelectedItem();
    }

    /***
     * Returns start year selected by user
     * @return Integer that represent the start year selected by user
     */
    private int getStartYear() {
        return Integer.parseInt((String) fromList.getSelectedItem());
    }

    /***
     * Returns end year selected by user
     * @return Integer that represent the end year selected by user
     */
    private int getEndYear() {
        return Integer.parseInt((String) toList.getSelectedItem());
    }

    /***
     * Returns the type of analysis selected by the user
     * @return String containing the type of selected analysis
     */
    private String getAnalysis() {
        return (String) methodsList.getSelectedItem();
    }

    /***
     * Checks that the entered date range is valid
     * @param start String representing the starting year selected
     * @param end String representing the starting year selected
     * @return true if date range is valid else return false
     */
    private boolean validDate(String start, String end) {
        if (Integer.parseInt(start) <= Integer.parseInt(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * displayResults gets the country, start and end years, and analysis type that the user has selected.
     * Then, it initiates the algorithm (strategy) and supplies the information that the user has selected.
     * The data is then fetched from the World Bank data repository and is passed to the strategy as an argument.
     * This data is then rendered via the appropriate viewers that have been added to currentListOfViewers.
     * @param west is a reference to the panel where charts are displayed.
     */
    public void displayResults(JPanel west) {
        // Get country, start and end years, and analysis type that have been selected by the user.
        String selectedCountryName = getCountry();
        Integer selectedStartYear = getStartYear();
        Integer selectedEndYear = getEndYear();
        String selectedAnalysisType = getAnalysis();

        // Initialize the strategy object that will perform the analysis.
        AnalysisStrategy strategy = null;

        // Instantiate the correct analysis strategy depending on selectedAnalysisType.
        switch (selectedAnalysisType) {
            case "CO2 emissions vs. Energy use vs. PM2.5 air pollution":
                strategy = new AnalysisOneStrategy();
                break;
            case "PM2.5 air pollution vs. Forest area":
                strategy = new AnalysisTwoStrategy();
                break;
            case "Ratio of CO2 emissions and GDP per capita":
                strategy = new AnalysisThreeStrategy();
                break;
            case "Average forest area":
                strategy = new AnalysisFourStrategy();
                break;
            case "Average of Gov. expenditure on education":
                strategy = new AnalysisFiveStrategy();
                break;
            case "Ratio of Hospital beds and Current health expenditure":
                strategy = new AnalysisSixStrategy();
                break;
            case "Current health expenditure per capita vs. Mortality rate":
                strategy = new AnalysisSevenStrategy();
                break;
            case "Ratio of Gov. expenditure on education vs. Current health expenditure":
                strategy = new AnalysisEightStrategy();
                break;
        }

        // The country code for the country.
        // For example, the country code for "Canada" is "can".
        String countryCode = "";

        // Generate the country code for the selected country name.
        switch (selectedCountryName) {
            case "Canada":
                countryCode = "can";
                break;
            case "United Kingdom":
                countryCode = "gbr";
                break;
            case "USA and Puerto Rico":
                countryCode = "usa";
                break;
            case "France":
                countryCode = "fra";
                break;
            case "Germany":
                countryCode = "deu";
                break;
            default:
                // If user selected any other country, show an error popup.
                JOptionPane.showMessageDialog(null, String.format("Error: %s is not available for analysis.", selectedCountryName));
                return;
        }

        // Fetch the data and perform the analysis.
        HashMap<Integer, HashMap<String, Float>> data = strategy.performAnalysis(countryCode, selectedStartYear, selectedEndYear);

        // Handle case where all of the data is missing.

        boolean allDataIsMissing = true;

        for (HashMap<String, Float> currentYearData : data.values()) {
            for (float dataValue : currentYearData.values()) {
                if (Math.signum(dataValue) != 0) {
                    allDataIsMissing = false;
                }
            }
        }

        // All the data required for this analysis is missing.
        if (allDataIsMissing) {
            JOptionPane.showMessageDialog(null, "Error: cannot perform analysis, all data is missing.");
            return;
        }

        // Handle case where all of the data for some of the year(s) is missing.
        for (Iterator<Map.Entry<Integer, HashMap<String, Float>>> it = data.entrySet().iterator(); it.hasNext();) {
            Map.Entry<Integer, HashMap<String, Float>> currentYearData = it.next();
            Float[] fetchedDataValues = currentYearData.getValue().values().toArray(new Float[currentYearData.getValue().size()]);

            Boolean allDataIsMissingForYear = true;

            for (Float currentYearDataValue : fetchedDataValues) {
                if (Math.signum(currentYearDataValue) != 0) {
                    allDataIsMissingForYear = false;
                }
            }

            // If all the data is missing for the year, remove the data.
            if (allDataIsMissingForYear) {
                it.remove();
            }
        }

        // Iterate through the list of added viewers and render the data.
        for (Viewer viewer : currentListOfViewers) {
            try {
                ChartPanel graph = viewer.createGraph(selectedAnalysisType, data);
                west.add(graph);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: pie graphs cannot render data with more than one series.");
            }
        }

        // Resize the frame so that the graphs can be displayed properly.
        MainUI.getInstance().pack();
    }

    /***
     * Main function that calls an instance of the MainUI
     * @param args null
     */
    public static void main(String[] args) {
        JFrame frame = MainUI.getInstance();
        frame.setSize(900, 600);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
