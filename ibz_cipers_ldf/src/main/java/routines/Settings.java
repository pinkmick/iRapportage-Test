package routines;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.json.*;

//Class Settings: Used to retrieve most of the info from the settings.ini file
//Always try to use the existing features in this calss whenever possible, create custom features in the CustomSettings class
public class Settings {
	//Variables
	private static String error = "";
	private static org.json.JSONObject config;

	//Constructor which reads the settings file and decrypts it
	public Settings() throws Exception {
		String file = readFile("../../../../CirCooNodejs/config/settings.ini");
		String decrypted = decrypt(PasswordReader.read(), file);
		config = new JSONObject(decrypted);
	}
	
	//Function to decrypt the given input
	private static String decrypt(String pass, String encrypted) throws Exception {
	    byte[] passBytes = pass.getBytes("UTF-8");
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] thedigest = md.digest(passBytes);
	    SecretKeySpec skey = new SecretKeySpec(thedigest, "AES");
	    Cipher dcipher = Cipher.getInstance("AES");
	    dcipher.init(Cipher.DECRYPT_MODE, skey);

	    byte[] clearbyte = dcipher.doFinal(toByte(encrypted));
	    return new String(clearbyte);
	}

	//Function to transform the hex string to byte[] so it can be used to decrypt
	private static byte[] toByte(String hexString) {
	    int len = hexString.length()/2;
	    byte[] result = new byte[len];
	    for (int i = 0; i < len; i++) {
	        result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
	    }
	    return result;
	}
	
	//Simple function to read a file and return the contents of the file
	public static String readFile(String filename) {
		String file = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while(line != null) {
				file += line + "\n";
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			error = "Couldn't find settings.ini file!";
		} catch (IOException e) {
			error = "Error when reading settings.ini file, check if we have permissions to read the file!";
		}
		return file;
	}
	
	public static String getError() {
		return error;
	}
	
	//Set of functions to return the info from the settings file
	public static String get(String setting) {
		return config.optString(setting);
	}
	public static String get(String object, String setting) {
		return config.getJSONObject(object).optString(setting);
	}
	public static String get(String object, String subObject, String setting) {
		return config.getJSONObject(object).getJSONObject(subObject).optString(setting);
	}
	
	/* Not working, decrypt function for AES-256-CDC (delete after 1-6-2017)
	private static String decrypt(String pass, String encrypted) throws Exception {
		String md5 = new String(MessageDigest.getInstance("md5").digest(pass.getBytes()), StandardCharsets.UTF_8);
		byte[] ivBytes = MessageDigest.getInstance("md5").digest((md5 + pass).getBytes());
		
		System.out.println(md5);
		
		SecretKey aesKey = new SecretKeySpec(md5.getBytes(), "AES");
		//System.out.println(new String(new IvParameterSpec(aesKey.getEncoded()).getIV(), StandardCharsets.UTF_8));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, aesKey, new IvParameterSpec(ivBytes));
		return "" + cipher.doFinal(encrypted.getBytes());
		//return "";
	}*/
}
