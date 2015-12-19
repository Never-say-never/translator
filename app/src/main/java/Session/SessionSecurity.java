package Session;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import Session.SessionCommons.ISessionSecurity;
import android.annotation.SuppressLint;

public class SessionSecurity implements ISessionSecurity {

	private final static String HEX = "0123456789ABCDEF";

	private static byte[] appSecurKey = generateKey();

	public SessionSecurity() { 

	}

	@Override
	public String decript(String toDeCript) {
		byte[] result = null;
		try {
			result = decrypt(appSecurKey, toByte(toDeCript));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new String(result);
	}

	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);

		return decrypted;
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++) {
			String tmp = hexString.substring(2 * i, 2 * i + 2);
			result[i] = Integer.valueOf(tmp, 16).byteValue();
		}

		return result;
	}

	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String toHex(byte[] buf) {
		if (buf == null) {
			return "";
		}

		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}

		return result.toString();
	}

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f));
		sb.append(HEX.charAt(b & 0x0f));
	}

	@Override
	public String encript(String toCript) {
		byte[] result = null;
		try {
			result = encrypt(appSecurKey, toCript.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toHex(result);
	}

	@SuppressLint("TrulyRandom")
	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);

		return encrypted;
	}

	@SuppressLint("TrulyRandom")
	private static byte[] generateKey() {
		KeyGenerator keyGen = null;
		SecureRandom secure = null;
		try {
			keyGen = KeyGenerator.getInstance("AES");
			secure = SecureRandom.getInstance("SHA1PRNG");
			secure.setSeed(SALT);
			keyGen.init(128, secure);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return keyGen.generateKey().getEncoded();
	}

}