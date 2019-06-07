package routines;

//Class CustomSettings: use these to retrieve the makelaarsuite passwords + gemeentecodes
//Always use the existing features in Settings when possible
public class CustomSettings {
	//Functions to return info about the makelaarsuite database
	public static String getConnectionstring() {
		String connectionString = "";
		if(Settings.get("PROXY").equals("false")) {
			//Postgres database
			connectionString += Settings.get("overzichtenDbSettings", "con", "postgresql");
			connectionString += Settings.get("CONNECTION_DEFINITION", "host") + ":";
			connectionString += Settings.get("CONNECTION_DEFINITION", "port") + "/";
			connectionString += Settings.get("CONNECTION_DEFINITION", "database");
		} else if (Settings.get("ORACLE_RAC").equals("true")) {
			//Oracle RAC configuration
			connectionString += Settings.get("overzichtenDbSettings", "con", "oracle");
			connectionString += "(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)";
			connectionString += "(HOST=" + Settings.get("PROXY_CONNECTION_DEFINITION", "host") + ")";
			connectionString += "(PORT=" + Settings.get("PROXY_CONNECTION_DEFINITION", "port") + ")";
			connectionString += ")(LOAD_BALANCE=yes))(CONNECT_DATA=(SERVER=DEDICATED)";
			connectionString += "(SERVICE_NAME=" + Settings.get("PROXY_CONNECTION_DEFINITION", "service") + ")))";
		} else {
			//Oracle database
			connectionString += Settings.get("overzichtenDbSettings", "con", "oracle");
			connectionString += Settings.get("PROXY_CONNECTION_DEFINITION", "host") + ":";
			connectionString += Settings.get("PROXY_CONNECTION_DEFINITION", "port") + ":";
			connectionString += Settings.get("PROXY_CONNECTION_DEFINITION", "database");
		}
		return connectionString;
	}
	public static String getDriverClass() {
		if(Settings.get("PROXY").equals("false")) {
			//Postgres
			return Settings.get("overzichtenDbSettings", "driver", "postgresql");
		} else {
			//Oracle
			return Settings.get("overzichtenDbSettings", "driver", "oracle");
		}
	}
	public static String getMKSUser() {
		if(Settings.get("PROXY").equals("false")) {
			//Postgres
			return Settings.get("CONNECTION_DEFINITION", "user");
		} else {
			//Oracle
			return Settings.get("PROXY_CONNECTION_DEFINITION", "user");
		}
	}
	public static String getMKSPass() {
		if(Settings.get("PROXY").equals("false")) {
			//Postgres
			return Settings.get("CONNECTION_DEFINITION", "password");
		} else {
			//Oracle
			return Settings.get("PROXY_CONNECTION_DEFINITION", "password");
		}
	}
	
	public static String getSLZConnectionstring() {
		String connectionString = "";
		if(Settings.get("SLZ_AVAILABLE").equals("true") && Settings.get("SLZ_ORACLE_RAC_AVAILABLE").equals("false")) {
			//SLZ
			connectionString += Settings.get("overzichtenDbSettings", "con", "oracle");
			connectionString += Settings.get("SLZ", "host") + ":";
			connectionString += Settings.get("SLZ", "portnr") + ":";
			connectionString += Settings.get("SLZ", "database");
		} else if (Settings.get("SLZ_AVAILABLE").equals("true") && Settings.get("SLZ_ORACLE_RAC_AVAILABLE").equals("true")) {
			//Oracle RAC configuration
			connectionString += Settings.get("overzichtenDbSettings", "con", "oracle");
			connectionString += "(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)";
			connectionString += "(HOST=" + Settings.get("SLZ", "host") + ")";
			connectionString += "(PORT=" + Settings.get("SLZ", "portnr") + ")";
			connectionString += ")(CONNECT_DATA=";
			connectionString += "(SERVICE_NAME=" + Settings.get("SLZ", "service") + ")))";
		}
		return connectionString;
	}
	
	//Function for the gemeentecodes
	public static String getGemeentecodes() {
		String file = Settings.readFile(Settings.get("MAKELAAR_PATH") + "MKS/conf/cmg/CiVMK.properties");
		String[] parts = file.split("\n");
		for(int i = 0; i < parts.length; i++) {
			if(parts[i].startsWith("gemeentecodes")) {
				String[] temp = parts[i].split("=");
				return temp[1].replace(';', ',');
			}
		}
		return "Error reading CiVMK.properties for gemeentecode!!";
	}
}
