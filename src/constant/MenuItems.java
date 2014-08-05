package constant;

public class MenuItems {
	public static final String[] items = new String[] { "Home",
			"MyServiceContainer" // 1
			, "MyBPMNEngine" // 2
			, "MyWebServer" // 3
			, "BPIDELite" // 4
			, "MashRoom"  // 5
			, "MyLoadTester" //6
			, "J2WS Online" //7
			, "WS Sim" //8
			, "SubscribedServices" // 9
			, "SubscribedBPMN" // 10
			, "HBase" // 11
			, "Cassandra" // 12
			, "MySQL" // 13

	};

	public static final String[] addrs = new String[] { "index.jsp",
			"MyServiceContainer.jsp" // 1
			, "MyBpmnEngine.jsp" // 2
			, "MyWebServer.jsp" // 3
			, "process/BpmnEditor.jsp" // 4
			, "MashRoom.jsp" //5
			, "MyLoadTester.jsp" // 6
			, "J2WS.jsp" // 7
			, "simulation/BpmnEditor_3.jsp" // 8
			, "SubscribedServices.jsp" // 9
			, "SubscribedBPMN.jsp" // 10
			, "ApplyHbaseDataService" // 11
			, "ApplyCassandraDataService" // 12
			, "MySQL.jsp" // 13
	};

	public static final Boolean[] editable = new Boolean[] { false, 
			 false // 1
			, true // 2
			, true // 3
			, false // 4
			, true //5
			, true // 6
			, true // 7
			, true //8
			, true //9
			, true //10
			, true //11
			, true //12
			, true //13
	};

	public static int confInt = 0;

	static {
		for (int i = 0; i < editable.length; i++) {
			if (!editable[i])
				confInt |= (1 << i);
		}
	}
}