package covid19trackingmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Covid19TrackingManager extends BST {

    private BST binaryTree = new BST();
    private String titleLine = "state   positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death";
    private String summaryTitle = "";
    
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
        while(scan.hasNextLine()){
            if(records == 0){
                continue;
            }
            binaryTree.insert(scan.nextLine())
            records++;
        }
        if(records == 0){
            num = "no";
        }
        else{
            String num = Integer.toString(records);
        }
        System.out.println(records + "records have been loaded");
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