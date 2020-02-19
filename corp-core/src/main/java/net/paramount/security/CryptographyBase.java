/**
 * 
 */
package net.paramount.security;

import javax.crypto.spec.SecretKeySpec;

import net.paramount.exceptions.CryptographyException;
import net.paramount.framework.component.CompCore;

/**
 * @author ducbq
 *
 */
public abstract class CryptographyBase extends CompCore {
	private static final long serialVersionUID = -8623665991128374797L;

	protected static byte[] privateSecretKeyRaw = { 
			(byte) 0xA5, (byte) 0x01, (byte) 0x7B, (byte) 0xE5,
			(byte) 0x23, (byte) 0xCA, (byte) 0xD4, (byte) 0xD2,
			(byte) 0xC6, (byte) 0x5F, (byte) 0x7D, (byte) 0x8B,
			(byte) 0x0B, (byte) 0x9A, (byte) 0x3C, (byte) 0xF1 };
	
	protected final static String SECRET_KEY_AES = "stackjava.com.if";


	protected final static String SALT_PREFIX = "{{";
	protected final static String SALT_POSFIX = "}}";

	protected static final SecretKeySpec skeySpecDES = new SecretKeySpec(SECRET_KEY_AES.getBytes(), SecretAlgorithm.DES.name());
	
	protected String addSalts(String buffer) {
		return new StringBuilder(SALT_PREFIX).append(buffer).append(SALT_POSFIX).toString();
	}

	protected String eliminateSalts(String buffer) {
		if (buffer.startsWith(SALT_PREFIX) && buffer.endsWith(SALT_POSFIX))
			return buffer.substring(SALT_PREFIX.length(), buffer.length() - SALT_POSFIX.length());

		return buffer;
	}

	public byte[] encode(String plainText) throws CryptographyException {
		return null;//performEncode(plainText);
	}

	public byte[] decode(byte[] encodedBytes) throws CryptographyException {
		return null;//performDecode(encodedBytes);
	}

	public String stringEncode(String plainText) throws CryptographyException {
		return performStringEncode(plainText);
	}

	public String stringDecode(String encodedBuffer) throws CryptographyException {
		return performStringDecode(encodedBuffer);
	}

	protected String performEncode(String plainText) throws CryptographyException {
		throw new CryptographyException("Not implemented!");
	}

	protected byte[] performDecode(byte[] encodedBytes) throws CryptographyException {
		throw new CryptographyException("Not implemented!");
	}

	protected String performStringEncode(String plainText) throws CryptographyException {
		throw new CryptographyException("Not implemented!");
	}

	protected String performStringDecode(String encodedBuffer) throws CryptographyException {
		throw new CryptographyException("Not implemented!");
	}
}
