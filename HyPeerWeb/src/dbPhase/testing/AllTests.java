package dbPhase.testing;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(ConnectionsTest.class);
        //$JUnit-END$
        return suite;
    }

}
