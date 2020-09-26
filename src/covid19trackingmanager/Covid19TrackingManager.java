package covid19trackingmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Covid19TrackingManager{

    private String titleLine = "state   positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death";
    private String summaryTitle = "";
    private ArrayList<Node> array;

    public class Node(){
        private int date;
        private String state;
        private int positive;
        private int negative;
        private int hospitalized;
        private int onVentilatorCurrently;
        private int onVentilatorCumulative;
        private int recovered;
        private String dataQualityGrade;
        private int death;
 
        public Node(int date, String state, int positive, int negative, int hospitalized, 
            int onVentilatorCurrently, int onVentilatorCumulative, int recovered, String dataQualityGrade, int death;){
            this.date = date;
            this.state = state;
            this.positive = positive;
            this.negative = negative;
            this.hospitalized = hospitalized;
            this.onVentilatorCurrently = onVentilatorCurrently;
            this.onVentilatorCumulative = onVentilatorCumulative;
            this.recovered = recovered;
            this.dataQualityGrade = dataQualityGrade;
            this.death = death;
            }
    }
    
    public static void main(String[] args){
        if(length(args) == 0 || length(args) > 4){
            System.out.println("error, invalid number of inputs");
            return;
        }
        switch(args[0]){
            case "load":
                this.loadFile(args[1]);
                break;
            case "search":
                if(length(args) == 1){
                    this.search();
                }
                else if(length(args) == 2){
                    this.searchDate(args[1]);
                }
                else{
                    this.searchState(args);
                }
                break;
            case "summarydata":
                this.summaryData();
                break;
            case "dumpdata":
                this.dumpData(args[1]);
                break;
            default:
                System.out.println("error, invalid input");
                break;
        }
        return;
    }

    public void loadFile(String filename){
        array = new ArrayList()<Node>;

        File dataFile;
        try{
            dataFile = new File(filename);
        }
        catch(Exception e){
            System.out.println("File " + filename + " was not found");
            return;
        }
        
        Scanner scan = new Scanner(dataFile);
        System.out.println("Finished loading" + filename);
        int records = 0;
        String[] params;

        while(scan.hasNextLine()){
            if(records == 0){
                continue;
            }
            params = scan.nextLine().split(",");
            records++;
            Node newNode = new Node(Integer.parseInt(params[0]), params[1], Integer.parseInt(params[2]), Integer.parseInt(params[3]), 
                Integer.parseInt(params[4]),Integer.parseInt(params[5]), Integer.parseInt(params[6]), Integer.parseInt(params[7]),
                params[8], Integer.parseInt(params[9]));
            if(validState(newNode.state)){
                System.out.println("State of " + newNode.state + " does not exist!");
            }
            this.addNode(newNode);
        }
        System.out.println(Integer.toString(records) + "records have been loaded");
    }

    public Boolean validState(String state){
        String stateConv = state.toUpperCase();
        States[] stateAr = States.values();
        Boolean match = false;
        for(States st : stateAr){
            if(stateConv.equals(st.getFullName())){
                match = true;
            }
        }
        return !match;
    }

    public void addNode(Node newNode){
        if(array.size() == 0){
            array.add(newNode);
        }
        Boolean state = false;
        int stateInd;
        Boolean date = false;
        int dateInd;
        for(int index = 0; index < array.size(); index++){
            if(newNode.state == array.get(index).state){
                state = true;
                stateInd = index;
            }
            if(newNode.date == array.get(index).date){
                date = true;
                dateInd = index;
            }
        }
        if(state){
            if(!date){
                array.add(newNode);
            }
            int nnq;
            int ssq;
            String[]
            switch(newNode.dataQualityGrade){
                case "A+":
                    nnq = 1;
                    break;
                case "A":
                    nnq = 2;
                    break;
                case "A-":
                    nnq = 3;
                    break;
                case "B+":
                    nnq = 4;
                    break;
                case "B":
                    nnq = 5;
                    break;
                case "B-":
                    nnq = 6;
                    break;
                case "C+":
                    nnq = 7;
                    break;
                case "C":
                    nnq = 8;
                    break;
                case "C-":
                    nnq = 9;
                    break;
                case "D+":
                    nnq = 10;
                    break;
                case "D":
                    nnq = 11;
                    break;
                case "D-":
                    nnq = 12;
                    break;
                default:
                    System.out.println("error handling grades");
                    System.exit();
            }
            switch(array.get(stateInd).dataQualityGrade){
                case "A+":
                    ssq = 1;
                    break;
                case "A":
                    ssq = 2;
                    break;
                case "A-":
                    ssq = 3;
                    break;
                case "B+":
                    ssq = 4;
                    break;
                case "B":
                    ssq = 5;
                    break;
                case "B-":
                    ssq = 6;
                    break;
                case "C+":
                    ssq = 7;
                    break;
                case "C":
                    ssq = 8;
                    break;
                case "C-":
                    ssq = 9;
                    break;
                case "D+":
                    ssq = 10;
                    break;
                case "D":
                    ssq = 11;
                    break;
                case "D-":
                    ssq = 12;
                    break;
                default:
                    System.out.println("error handling grades");
                    System.exit();
            }
            if(nnq > ssq){
                this.update(newNode, array.get(stateInd));
                array.remove(stateInd);
                array.add(stateInd, newNode);
                System.out.println("Data has been updated for" + newNode.state + " " + Integer.toString(newNode.date));
            }
            else{
                this.update(array.get(stateInd), newNode);
                System.out.println("Low quality data rejected for " + newNode.state);
            }
        }
        else{
            array.add(newNode);
        }
    }

    public Node update(Node highNode, Node lowNode){
        Boolean updated = false;
        if(highNode.positive == 0 && lowNode.positive != 0){
            highNode.positive = lowNode.positive;
            updated = true;
        }
        if(highNode.negative == 0 && lowNode.negative != 0){
            highNode.negative = lowNode.negative;
            updated = true;
        }
        if(highNode.hospitalized == 0 && lowNode.hospitalized != 0){
            highNode.hospitalized = lowNode.hospitalized;
            updated = true;
        }
        if(highNode.onVentilatorCurrently == 0 && lowNode.onVentilatorCurrently != 0){
            highNode.onVentilatorCurrently = lowNode.onVentilatorCurrently;
            updated = true;
        }
        if(highNode.onVentilatorCumulative == 0 && lowNode.onVentilatorCumulative != 0){
            highNode.onVentilatorCumulative = lowNode.onVentilatorCumulative;
            updated = true;
        }
        if(highNode.recovered == 0 && lowNode.recovered != 0){
            highNode.recovered = lowNode.recovered;
            updated = true;
        }
        if(highNode.death == 0 && lowNode.death != 0){
            highNode.death = lowNode.death;
            updated = true;
        }
        if(updated){
            System.out.println("Data has been updated for the missing data in " + highNode.state);
        }
    }

    public void search(){
        if(binaryTree.getSize() == 0){
            System.out.println("No available data");
            return;
        }
        this.searchDate((binaryTree.findMaxValue()));
    }

    public void searchDate(String date){
        Node root = binaryTree.root;
        String print = titleLine + "\n";
        while(root != null){
            if(Integer.toString(root.date).equals(date)){
                print += root.print + "\n";
            }
            root = root.right;
        }
        if(records == 0){
            System.out.println("There are no records on " + date);
        }
        else{
            String num = Integer.toString(records);
            System.out.println("There are " + num + " records on " + date);
            System.out.println(print);
        }
    }

    public void searchState(String[] args){
        String printState = "";
        String state = "";
        int recordsToDo = 0;
        int recordsRec = 0;
        if(length(args) == 4){
            state = this.convertState(args[1] + args[2]);
            printState = args[1] + args[2];
            recordsToDo = args[3];
        }
        else if(length(args[1] != 2)){
            state = this.convertState(args[1]);
            printState = args[1];
            recordsToDo = args[2];
        }
        else{
            state = args[1].toUpperCase();
            printState = args[1];
            recordsToDo = args[2];
        }
        if(state.equals("NONE")){
            return;
        }
        if(recordsToDo < 1){
            System.out.println("Invalid command. # of records has to be positive");
            return;
        }
        Node root = binaryTree.root;
        String print = titleLine + "\n";
        while(root != null && recordsRec < recordsToDo){
            if(root.state.equals(state)){
                print += root.print + "\n";
            }
            root = root.right;
        }
        if(recordsRec == 0){
            System.out.println("There are no records from " + printState);
        }
        else{
            String num = Integer.toString(recordsRec);
            System.out.println(num + "records are printed out for the state of " + printState);
            System.out.println(print);
        }
        
    }

    private String convertState(String state){
        String stateConv = state.toUpperCase();
        States[] stateAr = States.values();
        for(States st : stateAr){
            if(stateConv.equals(st.getFullName())){
                return st.getAbbreviation();
            }
        }
        System.out.println("State of " + state + " does not exist!");
        return "NONE";
    }

    public void summaryData(){
        if(binaryTree.getSize() == 0){
            System.out.println("No available data");
            return;
        }
        String print = "";
        Node root = binaryTree.root;
        String lastSt = "";
        int totCas = 0;
        int totDth = 0;
        int totHos = 0;
        while(root != null){
            if(root.state.equals(lastSt)){
                root = root.right;
                continue;
            }
            lastSt = root.state;
            print += root.printSum + "\n";
            totCase += root.cases;
            totDth += root.deaths;
            totHos += root.hospitalized;
            root = root.right;
        }
        System.out.println("Data Summary for " + binaryTree.nodes + " states");
        System.out.println("State   Total Case  Total Death   Total Hospitalized");
        System.out.println(print);
        System.out.println("Total Cases: " + Integer.toString(totCas));
        System.out.println("Total Deaths: " + Integer.toString(totDth));
        System.out.println("Total Hospitalized: " + Integer.toString(totHos));
    }

    public void dumpData(String filename){
        int records = binaryTree.dumpData(filename);
        System.out.println(records + "records have been saved in the " + filename + " file");
    }
}