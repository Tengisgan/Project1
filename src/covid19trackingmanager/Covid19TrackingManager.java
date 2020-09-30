package covid19trackingmanager;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Covid19TrackingManager is a class that loads in
 * COVID-19 data, handles any bad data and updates
 * the data. The class also allows for the user
 * to input commands, like search and summarydata,
 * to get an output they want. 
 * 
 * @author Tengis Gantulga and Nikolai Long
 * @pid tengisgan and nikolai
 * 
 * @version 2020.09.29
 */
public class Covid19TrackingManager {
	
	private static Covid19TrackingManager main = new Covid19TrackingManager();
      
    /**
     * A hashmap to store and manipulate the date. It contains
     * a string key and a value of array of the records.
     */
    private HashMap<String, ArrayList<Record>> records = new HashMap<>();
    
    /**
     * A hashmap to handle the conversion of state names from inputs
     */
    private HashMap<String, String> states = new HashMap<>();
    
    /**
     * load function takes in a csv file and loads the
     * file line by line into the records hashmap. If the file was not found, 
     * it will print a file not found message. The load function will
     * also make comparisons for rows that have the same state
     * and same date and will make changes accordingly. The comparisons
     * are done with the help of a hashmap. 
     * 
     * @param filename : file being loaded
     */
    public void load(String filename) {
        generateStateNicknames();

        try {
            //Scanner input to go through the file
            Scanner input = new Scanner(new File(filename));
            input.next();

            int count = 0;
            while(input.hasNext()) {
                String[] row = input.next().split(",");
                if(states.get(row[1].toLowerCase()) == null) {
                    System.out.println("State of " + row[1] + " does not exist");
                    continue;
                }
                if(row[0].equals("") || row[1].equals("") || row[8].equals("")) {
                    System.out.println("Discard invalid record");
                }else {
                    if(!records.containsKey(row[1])) {
                        records.put(row[1], new ArrayList<>(Arrays.asList(new Record(row))));
                        count ++;
                    }else {
                        Record existing = records.get(row[1]).get(0);
                        // Equality test for dates
                        if(existing.getColumn(0).equalsIgnoreCase(row[0])) {
                            if(existing.isLessQualityThan(row[8])) {
                                System.out.println("Data has been updated for " + row[1] + " " + row[0]);
                                existing.replace(row);
                                count ++;
                            }else {
                                // If the new record has lower quality
                                if(existing.update(row)) {
                                    System.out.println("Data has been updated for the missing data for " + row[1]);
                                    count ++;
                                }else {
                                    System.out.println("Low quality data rejected for " + row[1]);
                                }
                            }
                        }
                        // If the date is different
                        else {
                            records.get(row[1]).add(new Record(row));
                            count ++;
                        }
                    }
                }
            }
            System.out.println("Finished loading " + filename + " file");
            System.out.println(count + " records have been loaded");
        }catch(FileNotFoundException e) {
            System.out.println("File " + filename + " was not found");
        }
    }

    /**
     * This search function takes in a state and
     * number of records of that state. It displays
     * the available the specified amount of records
     * for that state. The state name is converted
     * with the help of convertState function. The
     * ArrayList values of the hashmap are then 
     * traversed through and found for the specified 
     * state, then the records are displayed.
     * 
     * @param state : state being searched for
     * @param count : amount of records needed to be displayed
     */
    public void search(String state, int count) {
        String convertedState = convertState(state);
        if(convertedState == null) {
            System.out.println("State of " + state + " does not exist");
        }else if(count < 0) {
            System.out.println("Invalid command. # of records has to be positive");
        }else {
            if(records.containsKey(convertedState)) {
                ArrayList<Record> existing = records.get(convertedState);
                System.out.println((Math.min(count, existing.size())) + " records are printed for the state of " + state);
                System.out.println("date\tpositive\tnegative\thospitalized\tonVentilatorCurrently\tonVentilatorCumulative\trecovered\tdataQualityGrade\tdeath");
                sort(existing, 0, false);
                for(int i=0; i<(Math.min(count, existing.size())); i++) {
                    System.out.println(existing.get(i).getDateFormatted());
                }
            }else {
                System.out.println("There are no records from " + state);
            }
        }
    }
    
    /**
     * The search function works the same way as
     * search state function but accepts only date
     * instead of state and records. The hashmap is
     * traversed through to find all the records
     * for the given date to display them. 
     * 
     * @param date : date being searched for
     */
    public void search(String date) {
        ArrayList<Record> found = new ArrayList<>();
        for(Map.Entry<String, ArrayList<Record>> entry : records.entrySet()) {
            ArrayList<Record> record = entry.getValue();
            for(int i=0; i<record.size(); i++) {
                if(record.get(i).getDate().equalsIgnoreCase(date)) {
                    found.add(record.get(i));
                }
            }
        }
        if(found.size() > 0) {
            System.out.println("There are " + found.size() + " records on " + date);
            System.out.println("state\tpositive\tnegative\thospitalized\tonVentilatorCurrently\tonVentilatorCumulative\trecovered\tdataQualityGrade\tdeath");
            sort(found, 1, true);
            for(int i=0; i<found.size(); i++) {
                System.out.println(found.get(i).getStateFormatted());
            }
        }else {
            System.out.println("There are no records on " + date);
        }
    }
    
    /**
     * The search function finds the highest
     * date found in the records and calls the
     * search(date) function. 
     * 
     */
    public void search() {
        //lowest date currently
        String date = "00/00/0000";
        for(Map.Entry<String, ArrayList<Record>> entry : records.entrySet()) {
            ArrayList<Record> record = entry.getValue();
            for(int i=0; i<record.size(); i++) {
                if(record.get(i).getDate().compareTo(date) > 0) {
                    //higher date found
                    date = record.get(i).getDate();
                }
            }
        }
        if(date.equalsIgnoreCase("00/00/0000")) {
            System.out.println("No available data");
        }else {
            search(date);
        }
    }

    /**
     * sort function takes in an arraylist of records
     * and sorts them in the order based on the index given. 
     * For example, date is located in index of 0. So if index
     * of 0 is given, the function will sort the arrays based
     * on the dates. Since higher date is supposed to be displayed
     * before the lower dates, boolean reverse is needed to see
     * if we need to reverse it. We will need to reverse it for
     * the states because lower state should be first.
     * 
     * @param records : ArrayList of records to be sorted
     * @param index : the specified index for sorting based
     *                  on date or state. 
     * @param reverse : boolean to reverse the order if needed
     */
    private void sort(ArrayList<Record> records, int index, boolean reverse) {
        Collections.sort(records, new Comparator<Record>() {
            public int compare(Record a, Record b) {
                return b.getColumn(index).compareTo(a.getColumn(index));
            }
        });
        if(reverse) Collections.reverse(records);
    }

    /**
     * convertState converts the state being
     * passed to upper case and tries to find 
     * it from the hashmap with records. If not found, then it tries
     * to go through the other hashmap (states) containing all
     * the conversions of state names and tries to find it there,
     * then converts it, if found, to two letter uppercase abbrevation
     * version. If still not found, then it returns null. 
     * 
     * @param state : given state to be converted
     * @return
     */
    private String convertState(String state) {
        if(states.containsKey(state.toLowerCase())) {
            return state.toUpperCase();
        }else {
            for(Map.Entry<String, String> entry : states.entrySet()) {
                if(state.equalsIgnoreCase(entry.getValue())) {
                    return entry.getKey().toUpperCase();
                }
            }
        }
        return null;
    }
    
    /**
     * Creates a new file and loads all the previous data
     * with the database
     * 
     * @param filename : new file's name
     */
    public void dumpdata(String filename) {
        File file = new File(filename);
        try {
            FileWriter writer = new FileWriter(filename);
            int count = 0;
            writer.write("date,state,positive,negative,hospitalized,onVentilatorCurrently,onVentilatorCumulative,recovered,dataQualityGrade,death\n");
            for(Map.Entry<String, ArrayList<Record>> entry : records.entrySet()) {
                ArrayList<Record> record = entry.getValue();
                for(Record item : record) {
                    for(int i=0; i<10; i++) {
                        String line = item.getColumn(i);
                        writer.write(line + ((i < 9) ? ",": "\n"));
                    }
                    count ++;
                }
            }
            writer.close();
            System.out.println(count + " records have been saved in the " + filename + " file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Reports the current number of total cases, hospitalized,
     * and death in the United States.
     */
    public void summarydata() {
        ArrayList<Record> records = new ArrayList<>();
        for(Map.Entry<String, ArrayList<Record>> entry : this.records.entrySet()) {
            Record current = entry.getValue().get(0);
            if(entry.getValue().size() > 1) {
                for(int i=1; i<entry.getValue().size(); i++) {
                    current.update(2, current.getNumber(2) + entry.getValue().get(i).getNumber(2));
                    current.update(4, current.getNumber(4) + entry.getValue().get(i).getNumber(4));
                    current.update(9, current.getNumber(9) + entry.getValue().get(i).getNumber(9));
                }
            }
            records.add(current);
        }
        sort(records, 1, true);
        System.out.println("Data Summary for " + records.size() + " states:");
        int totalCases = 0;
        int totalDeaths = 0;
        int totalHospitalized = 0;
        for(Record record : records) {
            System.out.println(record.getSummaryFormatted());
            totalCases += getFormattedNumber(record.getColumn(2));
            totalDeaths += getFormattedNumber(record.getColumn(9));
            totalHospitalized += getFormattedNumber(record.getColumn(4));
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        System.out.println("Total Cases: " + decimalFormat.format(totalCases));
        System.out.println("Total Deaths: " + decimalFormat.format(totalDeaths));
        System.out.println("Total Hospitalized: " + decimalFormat.format(totalHospitalized));
    }
    
    
    /**
     * Formats the empty string to return a 0 instead.
     * 
     * @param value : number to be formatted 
     * @return : the formatted number (0) 
     */
    private int getFormattedNumber(String value) {
        return value.equalsIgnoreCase("") ? 0 : Math.round(Float.parseFloat(value));
    }
    
    /**
     * for formatting the state names so that
     * the longer version of each of the state
     * names are associated with their abbreviation.
     */
    private void generateStateNicknames() {
        states.put("al", "alabama");
        states.put("ak", "alaska");
        states.put("az", "arizona");
        states.put("ar", "arkansas");
        states.put("ca", "california");
        states.put("co", "colorado");
        states.put("ct", "connecticut");
        states.put("de", "delaware");
        states.put("fl", "florida");
        states.put("ga", "georgia");
        states.put("hi", "hawaii");
        states.put("id", "idaho");
        states.put("il", "illinois");
        states.put("in", "indiana");
        states.put("ia", "iowa");
        states.put("ks", "kansas");
        states.put("ky", "kentucky");
        states.put("la", "louisiana");
        states.put("me", "maine");
        states.put("md", "maryland");
        states.put("ma", "massachusetts");
        states.put("mi", "michigan");
        states.put("mn", "minnesota");
        states.put("ms", "mississippi");
        states.put("mo", "missouri");
        states.put("mt", "montana");
        states.put("ne", "nebraska");
        states.put("nv", "nevada");
        states.put("nh", "new hampshire");
        states.put("nj", "new jersey");
        states.put("nm", "new mexico");
        states.put("ny", "new york");
        states.put("nc", "north carolina");
        states.put("nd", "north dakota");
        states.put("oh", "ohio");
        states.put("ok", "oklahoma");
        states.put("or", "oregon");
        states.put("pa", "pennsylvania");
        states.put("ri", "rhode island");
        states.put("sc", "south carolina");
        states.put("sd", "south dakota");
        states.put("tn", "tennessee");
        states.put("tx", "texas");
        states.put("ut", "utah");
        states.put("vt", "vermont");
        states.put("va", "virginia");
        states.put("wa", "washington");
        states.put("wv", "west virginia");
        states.put("wi", "wisconsin");
        states.put("wy", "wyoming");
        states.put("as", "american samoa");
        states.put("dc", "district of columbia");
        states.put("fm", "federated states of micronesia");
        states.put("gu", "guam");
        states.put("mh", "marshall islands");
        states.put("mp", "northern mariana islands");
        states.put("pw", "palau");
        states.put("pr", "puerto rico");
        states.put("vi", "virgin islands");
    }
    
    /**
     * Main class for running the program
     * @param args
     */
    public static void main(String[] args) {
        
        Scanner inpt = new Scanner(System.in);
        String[] params;
        params = inpt.nextLine().split(" "); 
        
//        main.load("head_100_random_30.csv");
//        main.search("ks", 10);
//        main.search("08/18/2020");
//        main.search();
//        main.dumpdata("dump.csv");
//        main.summarydata();
        
        if(params.length == 1 && params[0].equals("search")) {
        	main.search();
        }
        else if(params.length == 1 && params[0].equals("summarydata")) {
        	main.summarydata();
        }
        else if(params.length == 2 && params[0].equals("load")) {
        	main.load(params[1]);
        }
        else if(params.length == 2 && params[0].equals("search")) {
        	main.search(params[1]);
        }
        else if(params.length == 2 && params[0].equals("dumpdata")) {
        	main.dumpdata(params[1]);
        }
        else if(params.length == 3 && params[0].equals("search")) {
        	main.search(params[1], Integer.parseInt(params[2]));
        }
        else if(params.length == 4 && params[0].equals("search")) {
        	main.search(params[1]+params[2], Integer.parseInt(params[3]));
        }
        inpt.close();
    }
}
