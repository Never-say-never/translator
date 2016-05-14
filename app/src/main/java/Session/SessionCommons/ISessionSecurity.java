package Session.SessionCommons;

/*
 * SessionSecurity -> take care of application recourses,
 * stay them save and untouched
 */
public interface ISessionSecurity {

	/*
	 * key for encode application dictionary tabs security part to not lose my
	 * English dictionary because it's hard to get god formated CSV one.
	 */
	public final byte[] SALT = new byte[] {
			(byte) 0x47, (byte) 0x73,
			(byte) 0x21, (byte) 0x8c,
			(byte) 0xae, (byte) 0xa8,
			(byte) 0xe1, (byte) 0x99
	};
	  
	/*
	 * specific type of Blowfish encrypt method
	 */
	public final String ALGORITHM = "DES/CBC/PKCS5Padding";
	
	
	/*
	 * type of encrypting data - Blowfish
	 */
	public final String ALGORITHM_KEY = "DES";

	/*
	 * decode method -> for decode tab letter in "search process" get text from
	 * file for interpreted into understandable format
	 */
	public abstract String decript(String toDeCript);

	/*
	 * code method -> for code tab letter in first start App get text from file
	 * for interpreted into encrypt format
	 */
	public abstract String encript(String toCript);

}
