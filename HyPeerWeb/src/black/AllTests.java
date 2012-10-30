package black;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(BroadcastTest.class);
        suite.addTestSuite(ConnectionsBlackTest.class);
        suite.addTestSuite(HyPeerWebBlack.class);
        suite.addTestSuite(Node2.class);
        suite.addTestSuite(SendTest.class);
        //$JUnit-END$
        return suite;
    }

}
