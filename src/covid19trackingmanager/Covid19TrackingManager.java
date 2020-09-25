package covid19trackingmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Covid19TrackingManager extends BST {

    private BST binaryTree = new BST();
    private String titleLine = "state   positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death";
    
    private enum Commands{
        LOAD("load"), SEARCH("search"), SUMMARYDATA("summarydata"), DUMPDATA("dumpdata")
    }
    
    public static void main(String[] args){
        switch(args[0]){
            case LOAD:
                this.loadFile(args[1]);
                break;
            case SEARCH:
                if(length(args) == 2){
                    this.searchDate(args[1]);
                }
                else{
                    this.searchState(args);
                }
                break;
            case SUMMARYDATA:
                this.summaryData();
                break;
            case DUMPDATA:
                this.dumpData(args[1]);
                break;
            default:
                System.out.println("error, invalid input... exiting");
        }
    }

    public void loadFile(String filename) throws FileNotFoundException{
        File dataFile = new File(filename);
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

    public void searchDate(String date){
        Node root = binaryTree.root;
        String print = "";
        while(root.right != null){
            if(root.date == date){
                print += root.print + "\n";
            }
            root = root.right;
        }
        if(records == 0){
            num = "no";
        }
        else{
            String num = Integer.toString(records);
        }
        System.out.println("There are " + num + " records on " + date);
        System.out.println(print);
    }

    public void searchState(String[] args){

        if(records == 0){
            num = "no";
        }
        else{
            String num = Integer.toString(records);
        }
        String state = args[2];
        System.out.println("There are " + num + " records from " + state);
    }

    public void summaryData(){
        System.out.println("Data Summary for " + num + " states");
    }

    public void dumpData(String filename){
        System.out.println(num + "records have been saved in the " + filename + " file");
    }
}