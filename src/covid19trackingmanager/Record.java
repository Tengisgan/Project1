package covid19trackingmanager;

import java.text.DecimalFormat;


/**
 * The node class for containing all the records into an array.
 * It has functions to help pinpoint the different
 * records in each index of the array.
 * 
 * @author Tengis Gantulga and Nikolai Long
 * @pid tengisgan and nikolai
 * 
 * @version 2020.09.29
 */
public class Record {
    
    /**
     * Array of strings for storing each of the columns of 
     * the data.
     * For example, row[0] would be the date and row[1]
     * would be the state.
     */
    private String[] row;

    /**
     * Constructor class for record (node) that 
     * takes in a row.
     * 
     * @param row : row of data
     */
    public Record(String[] row) {
        this.row = row;
    }

    /**
     * replaces the existing row with another row
     * given by the parameter 
     * 
     * @param row : row replacing the old row
     */
    public void replace(String[] row) {
        this.row = row;
    }

    /**
     * Goes through the row and if there is
     * any empty or missing data, and if there
     * is a data that can replace the empty
     * space from the parameter row, replaces
     * the empty space from data from the parameter row.
     * 
     * @param row : row being compared to, to see
     *              if there is data that can be
     *              taken and updated in the missing spaces
     *              of the original row.
     * @return : the updated row
     */
    public boolean update(String[] row) {
        boolean updated = false;
        for(int i=0; i<this.row.length; i++) {
            if(this.row[i].equals("")) {
                this.row[i] = row[i];
                updated = true;
            }
        }
        return updated;
    }

    /**
     * instead of updating the specific row,
     * only updates the index that needs
     * to be updated
     * 
     * @param index : index being updated
     * @param update : index to replace
     *                  the original index. 
     */
    public void update(int index, int update) {
        this.row[index] = Integer.toString(update);
    }

    /**
     * Get the String in a given index of row. Or in 
     * other words, get the column.
     * @param index : given index of row
     * @return : The String in the given index of row. 
     */
    public String getColumn(int index) {
        return row[index];
    }
    
    /**
     * Comparing the quality grade, with the
     * help of getNumericalGrade function
     * of this to
     * param and returns true if this is less than 
     * the parameter
     * @param qualityGrade : qualityGrade to compare to
     * @return : true if this is less than qualityGrade
     *           /false otherwise. 
     */
    public boolean isLessQualityThan(String qualityGrade) {
        return getNumericalGrade(row[8]) < getNumericalGrade(qualityGrade);
    }
    
    /**
     * Converts all the String qualityGrades to integer 
     * values so that they can be compared to properly
     * @param qualityGrade : String qualityGrade to be converted to int
     * @return : int version of the quality grade
     */
    private int getNumericalGrade(String qualityGrade) {
        int grade = (700 - ((int) qualityGrade.charAt(0) * 10));
        return qualityGrade.length() == 1 ? grade : grade + (44 - ((int) qualityGrade.charAt(1)));
    }

    /**
     * get the date formatted in xx/xx/xxxx 
     * with the help of substrings
     * @return the correct formatted verison of date
     */
    public String getDate() {
        return String.format("%s/%s/%s", row[0].substring(4, 6), row[0].substring(6, 8), row[0].substring(0, 4));
    }

    /**
     * Formatted output to a function call
     * of search(state, counter), which
     * returns all of the columns except
     * for the state.
     * @return : the correct formatted output for search(state, counter)
     */
    public String getDateFormatted() {
        return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", getDate(), row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9]);
    }

    /**
     * Formatted output to a function call 
     * of search() and search(date), which
     * returns all of the columns except
     * for the date. 
     * @return : the correct formatted output for search() and search(date)
     */
    public String getStateFormatted() {
        return String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s", row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9]);
    }

    /**
     * Formatted output for summarydata()
     * function call, which displays
     * state, positive, hospitalized, and death count.
     * decimalFormat is also called to format the
     * numbers to have commas in them.
     * @return : the correct formatted output for summarydata()
     */
    public String getSummaryFormatted() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return String.format("%s\t%s\t%s\t%s", row[1], getFormattedNumber(row[2]), getFormattedNumber(row[9]), getFormattedNumber(row[4]));
    }

    /**
     * Converts a String value to have commas in them for
     * outputting
     * @param value : The value to be formatted
     * @return : The value with commas in them
     */
    private String getFormattedNumber(String value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return value.equalsIgnoreCase("") ? "0" : decimalFormat.format(Float.parseFloat(value));
    }

    /**
     * same as getColumn(int index), but
     * instead of returning it as a String,
     * we return it as an integer
     * 
     * @param index : the specific column index in the row
     * @return : the integer version of given index in row
     */
    public int getNumber(int index) {
        return getColumn(index).equalsIgnoreCase("") ? 0 : Math.round(Float.parseFloat(getColumn(index)));
    }

}
