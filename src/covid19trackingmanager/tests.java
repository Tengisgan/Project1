package covid19trackingmanager;

import java.io.ByteArrayInputStream;

import covid19trackingmanager.Covid19TrackingManager;
import junit.framework.TestCase;

/**
 * Test class for project 1. 
 * 
 * @author Tengis Gantulga and Nikolai Long
 * @pid tengisgan and nikolai
 * 
 * @version 2020.09.29
 */
public class tests extends TestCase {
	
	private String[] empty = {};
	private String params;
	
	/**
	 * file with data to be used for everything but the load tests
	 */
	public void setUp() {
    	params = "load head_100_random_30.csv";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
	}
	
	/**
	 * tests loading a file that doesn't reject any records
	 */
    public void testLoad1() {
    	params = "load head_100_random_30.csv";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests loading a file that does not exist
     */
    public void testLoad2() {
    	String params = "load head_100_random.csv";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests loading a file where low quality data is rejected
     */
    public void testLoad3() {
    	String params = "load head_200_random_70.csv";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests loading a file where data is updated
     */
    
    /**
     * tests loading a file where a state does not exist
     */
    
    /**
     * tests loading a file where data is updated for missing data
     */
    
    /**
     * tests searching for a state with an invalid name
     */
    public void testState1() {
    	String params = "search vrg 1";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests searching for a state with no records
     */
    public void testState2() {
    	String params = "search hi 1";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests searching for a valid state with an invalid number or record requests - negative
     */
    public void testState3() {
    	String params = "search ks -1";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests searching for state with correct output
     */
    public void testState4() {
    	String params = "search ks 10";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests searching date without additional inputs - most recent date
     */
    public void testDate1() {
    	String params = "search";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests searching for date that exists
     */
    public void testDate2() {
    	String params = "search 08/18/2020";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests searching for date that has no records
     */
    public void testDate3() {
    	String params = "search 08/18/2000";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests dump data
     */
    public void testDump() {
    	String params = "dumpdata dump.csv";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
    /**
     * tests summary data
     */
    public void testSummary() {
    	String params = "summarydata";
    	System.setIn(new ByteArrayInputStream(params.getBytes()));
    	Covid19TrackingManager.main(empty);
    }
    
}