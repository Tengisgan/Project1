package covid19trackingmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    
    private String titleLine = "state   positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death";
    private String summaryTitle = "";
    
    public void load(String filename) {
        try {
            Scanner input = new Scanner(new File(filename));
            HashMap<String, Node> map = new HashMap<String, Node>();
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
    
    private HashMap<String, String> initials;
    
    public Main() {
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
    }
    

    public static void main(String[] args) {
        Main main = new Main();
        main.load("head_100_random_30.csv");

    }

    
    
    
}
