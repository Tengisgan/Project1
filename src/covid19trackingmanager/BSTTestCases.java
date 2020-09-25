package covid19trackingmanager;

import junit.framework.TestCase;

public class BSTTestCases extends TestCase {
    
    /*
     * Declaring a binary search tree for testing
     */
    private BST bst;
    
    /*
     * Initialization
     */
    public void setUp() {
        bst = new BST();
    }
    
    /**
     * Testing insert function. If this function
     * works, then insertHelper also works.
     */
    public void testInsert() {
        bst.insert("1");
        assertFalse(bst.isEmpty());
        bst.insert("2");
        assertEquals(bst.findMaxValue(), "2");
        assertEquals(bst.findMinValue(), "1");
    }
    
    /*
     * Testing removing function
     */
    public void testDelete() {
        bst.insert("1");
        assertFalse(bst.isEmpty());
        bst.delete("1");
        assertTrue(bst.isEmpty());
        bst.insert("1");
        bst.insert("6");
        bst.insert("3");
        bst.insert("4");
        bst.insert("2");
        bst.delete("3");
        assertFalse(bst.find("3"));
        bst.makeEmpty();
        assertTrue(bst.isEmpty());
    }
}
