package inc.troll.mnemescan.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

	public static String toSha256(String toHash) {
		String result = "";

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(toHash.getBytes(StandardCharsets.UTF_8));
			result =  bytesToHex(hash); // java ... WHYYY not in place?!
		} catch(NoSuchAlgorithmException e) {
			// TODO rething this handling :-/
			e.printStackTrace();
		}

		return result;
	}

	public static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder();

		for(int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static float roundCoordinates(double value, int decimalDigits) {
		if (decimalDigits < 0) {
			throw new IllegalArgumentException();
		}

		long factor = (long) Math.pow(10, decimalDigits);
		value = value * factor;
		long tmp = Math.round(value);
		return (float) tmp / factor;
	}

	public static String dateToString(Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formater.format(date);
	}
}
