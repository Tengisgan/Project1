package covid19trackingmanager;

public class Node {
    
    private String[] row;
    
    public Node(String[] row) {
        this.row = row;
    }
    
    private int toInt(String string) {
        if(string.length() > 0) {
            Math.round(Float.parseFloat(string));
        }
        return -1;
    }
   
    public boolean isGradeHigher(String grade) {
        return getGrade(this.getDataQualityGrade()) >= getGrade(grade);
        
    }
    
    private int getGrade(String grade) {
        switch(grade){
            case "A+":
                return 12;
            case "A":
                return 11;
            case "A-":
                return 10;
            case "B+":
                return 9;
            case "B":
                return 8;
            case "B-":
                return 7;
            case "C+":
                return 6;
            case "C":
                return 5;
            case "C-":
                return 4;
            case "D+":
                return 3;
            case "D":
                return 2;
            case "D-":
                return 1;
            default:
                return -1;
        }
    }
    
    public String get(int index) {
        return row[index];
    }
    
    public String[] getRow() {
        return row;
    }
    
    public void setColumn(int index, String value) {
        row[index] = value;
    }
    
    public String getDate() {
        return String.format("%s/%s/%s", row[0].substring(4, 6), row[0].substring(6, 8), row[0].substring(0, 4));
    }

    public String getState() {
        return row[1];
    }

    public int getPositive() {
        return toInt(row[2]);
    }

    public int getNegative() {
        return toInt(row[3]);
    }

    public int getHospitalized() {
        return toInt(row[4]);
    }

    public int getOnVentilatorCurrently() {
        return toInt(row[5]);
    }

    public int getOnVentilatorCumulative() {
        return toInt(row[6]);
    }

    public int getRecovered() {
        return toInt(row[7]);
    }

    public String getDataQualityGrade() {
        return row[8];
    }

    public int getDeath() {
        return toInt(row[9]);
    }
    
}
    

