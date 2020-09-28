package covid19trackingmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Covid19TrackingManager {
    
    private String titleLine = "state   positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death";
    private String summaryTitle = "";
    
    private HashMap<String, Node> map = new HashMap<String, Node>();
    
    public void load(String filename) {
        try {
            Scanner input = new Scanner(new File(filename));

            int counter = 0;
            input.next();
            while(input.hasNext()) {
                String[] row = input.next().split(",");
                if (!initials.containsKey(row[1].toLowerCase())) {
                    System.out.println("State of " + row[1] + " does not exist!");
                    continue;
                }

                if(map.containsKey(row[1])) {
                    if(map.get(row[1]).get(0).equals(row[0])) {
                        String[] array = map.get(row[1]).getRow();
                        for(int i = 0; i < array.length; i++) {
                            if(array[i].equals("")) {
                                map.get(row[1]).setColumn(i, row[i]);
                            }
                        }
                        System.out.println("Data has been updated for the missing data in " + row[1]);

                    }
                    if(map.get(row[1]).isGradeHigher(row[8])) {
                        System.out.println("Low quality data rejected for " + row[1]);

                    } else {
                        map.put(row[1], new Node(row));
                        System.out.println("Data has been updated for " + row[1] + row[0]);
                    }

                }
                else {
                    map.put(row[1], new Node(row));
                    counter++;
                }
            }
            System.out.println("Finished loading " + filename);
            System.out.println(counter + " records have been loaded");
        }
        catch (FileNotFoundException e) {
            System.out.println("File " + filename + " was not found");
        }

    }
    
    
    
    public void search() {
    	int date = 0;
    	for(Map.Entry<String, Node> ent : map.entrySet()) {
    		int newDate = Integer.parseInt(ent.getValue().get(0));
    		System.out.println(newDate);
    		if (newDate > date) {
    			date = newDate;
    		}
    	}
    	String strDate = Integer.toString(date);
    	System.out.println(strDate);
    	strDate = String.format("%s/%s/%s", strDate.substring(4, 6), strDate.substring(6, 8), strDate.substring(0, 4));
    	this.searchDate(strDate);
    }
    
    public void searchDate(String date) {
    	String print = "";
    	int counter = 0;
    	System.out.println(map.isEmpty());
    	for(Map.Entry<String, Node> ent: map.entrySet()) {
    		String newDate = ent.getValue().getDate();
    		if (newDate.equals(date)) {
    			for(int index = 1; index < 10; index++) {
    				print += ent.getValue().get(index) + "    ";
    			}
    			print += "\n";
    			counter++;
    		}
    	}
    	System.out.println("There are " + counter + " records on " + date);
    	System.out.println(titleLine);
    	System.out.println(print);
    }
    
    public void searchState(String state, int records) {
        if(records <= 0) {
            System.out.println("Invalid command. # of records has to be positive");
        }
        else {
            String print = "";
            String conState = getState(state);
            int counter = 0;
            while(counter < records) {
                   for(Map.Entry<String, Node> ent : map.entrySet()) {
                        if (ent.getValue().get(1).equals(conState)) {
                            for(int i = 0; i < 10; i++) {
                                if(i == 1) {
                                    continue;
                                }
                                print += ent.getValue().get(i) + "    ";
                            }
                            counter++;
                        }
                    }
            }
            if (print == "") {
                System.out.println("There are no records from " + state);
            }
            else {
                System.out.println(counter + " records are printed out for the state of " + state);
            }
        }

    }
    
    private HashMap<String, String> initials;
    
    public Covid19TrackingManager() {
        initials = new HashMap<String, String>();
        initials.put("al", "alabama");
        initials.put("ak", "alaska");
        initials.put("az", "arizona");
        initials.put("ar", "arkansas");
        initials.put("ca", "california");
        initials.put("co", "colorado");
        initials.put("ct", "connecticut");
        initials.put("de", "delaware");
        initials.put("fl", "florida");
        initials.put("ga", "georgia");
        initials.put("hi", "hawaii");
        initials.put("id", "idaho");
        initials.put("il", "illinois");
        initials.put("in", "indiana");
        initials.put("ia", "iowa");
        initials.put("ks", "kansas");
        initials.put("ky", "kentucky");
        initials.put("la", "louisiana");
        initials.put("me", "maine");
        initials.put("md", "maryland");
        initials.put("ma", "massachusetts");
        initials.put("mi", "michigan");
        initials.put("mn", "minnesota");
        initials.put("ms", "mississippi");
        initials.put("mo", "missouri");
        initials.put("mt", "montana");
        initials.put("ne", "nebraska");
        initials.put("nv", "nevada");
        initials.put("nh", "new hampshire");
        initials.put("nj", "new jersey");
        initials.put("nm", "new mexico");
        initials.put("ny", "new york");
        initials.put("nc", "north carolina");
        initials.put("nd", "north dakota");
        initials.put("oh", "ohio");
        initials.put("ok", "oklahoma");
        initials.put("or", "oregon");
        initials.put("pa", "pennsylvania");
        initials.put("ri", "rhode island");
        initials.put("sc", "south carolina");
        initials.put("sd", "south dakota");
        initials.put("tn", "tennessee");
        initials.put("tx", "texas");
        initials.put("ut", "utah");
        initials.put("vt", "vermont");
        initials.put("va", "virginia");
        initials.put("wa", "washington");
        initials.put("wv", "west virginia");
        initials.put("wi", "wisconsin");
        initials.put("wy", "wyoming");
        initials.put("as", "american samoa");
        initials.put("dc", "district of columbia");
        initials.put("fm", "federated states of micronesia");
        initials.put("gu", "guam");
        initials.put("mh", "marshall islands");
        initials.put("mp", "northern mariana islands");
        initials.put("pw", "palau");
        initials.put("pr", "puerto rico");
        initials.put("vi", "virgin islands");
        initials.put("alabama", "al");
        initials.put("alaska", "ak");
        initials.put("arizona", "az");
        initials.put("arkansas", "ar");
        initials.put("california", "ca");
        initials.put("colorado", "co");
        initials.put("connecticut", "ct");
        initials.put("delaware", "de");
        initials.put("florida", "fl");
        initials.put("georgia", "ga");
        initials.put("hawaii", "hi");
        initials.put("idaho", "id");
        initials.put("illinois", "il");
        initials.put("indiana", "in");
        initials.put("iowa", "ia");
        initials.put("kansas", "ks");
        initials.put("kentucky", "ky");
        initials.put("louisiana", "la");
        initials.put("maine", "me");
        initials.put("maryland", "md");
        initials.put("massachusetts", "ma");
        initials.put("michigan", "mi");
        initials.put("minnesota", "mn");
        initials.put("mississippi", "ms");
        initials.put("missouri", "mo");
        initials.put("montana", "mt");
        initials.put("nebraska", "ne");
        initials.put("nevada", "nv");
        initials.put("new hampshire", "nh");
        initials.put("new jersey", "nj");
        initials.put("new mexico", "nm");
        initials.put("new york", "ny");
        initials.put("north carolina", "nc");
        initials.put("north dakota", "nd");
        initials.put("ohio", "oh");
        initials.put("oklahoma", "ok");
        initials.put("oregon", "or");
        initials.put("pennsylvania", "pa");
        initials.put("rhode island", "ri");
        initials.put("south carolina", "sc");
        initials.put("south dakota", "sd");
        initials.put("tennessee", "tn");
        initials.put("texas", "tx");
        initials.put("utah", "ut");
        initials.put("vermont", "vt");
        initials.put("virginia", "va");
        initials.put("washington", "wa");
        initials.put("west virginia", "wv");
        initials.put("wisconsin", "wi");
        initials.put("wyoming", "wy");
        initials.put("american samoa", "as");
        initials.put("district of columbia", "dc");
        initials.put("federated states of micronesia", "fm");
        initials.put("guam", "gu");
        initials.put("marshall islands", "mh");
        initials.put("northern mariana islands", "mp");
        initials.put("palau", "pw");
        initials.put("puerto rico", "pr");
        initials.put("virgin islands", "vi");
    }
    
    public String getState(String state) {
		/*
		 * if(state.length() == 2) { state.toLowerCase(); }
		 */
        for(Map.Entry<String, String> entry: initials.entrySet()) {
            if(state.equalsIgnoreCase(entry.getValue())) {
                return entry.getKey();
            }
        }
        System.out.println("State of " + state + " does not exist!");
        return null;
    }

    public static void main(String[] args) {
    	Covid19TrackingManager main = new Covid19TrackingManager();
        main.load("head_100_random_30.csv");
        main.searchDate("08/");
        main.search();
        main.searchState("pH", 1);
        main.searchState("virginia", 1);
        main.searchDate("08/17/2020");
    }

    
    
    
}
