package white;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(ConnectionsTest.class);
        suite.addTestSuite(HyPeerWebDatabaseTest.class);
        suite.addTestSuite(HyPeerWebTest.class);
        suite.addTestSuite(NodeTest.class);
        suite.addTestSuite(NodeTest2.class);
        //$JUnit-END$
        return suite;
    }

}
