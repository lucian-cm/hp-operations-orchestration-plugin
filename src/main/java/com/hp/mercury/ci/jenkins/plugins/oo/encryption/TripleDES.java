// (c) Copyright 2013 Hewlett-Packard Development Company, L.P. 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
package com.hp.mercury.ci.jenkins.plugins.oo.encryption;

import jenkins.model.Jenkins;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class TripleDES {

	private static String DEFAULT_CODEPAGE = "windows-1252";
	private static String ENCRYPTION_MODE = "DESede/ECB/PKCS5Padding";
	private static String ENCRYPTION_KEYSPECTYPE = "DESede";

    /**
     *
     * @param encPass encrypted data to decrypt
     * @return the decrypted data
     */
	public static String decryptPassword(String encPass) {
		try {
			return decryptString(DatatypeConverter.parseBase64Binary(encPass));
		}catch (Exception e) {
			return "";
		}
	}

    /**
     *
     * @param toHash data to md5-hash
     * @return the md5 function value when applied to the input string
     */
	private static byte[] md5Hash(String toHash) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] digest = md5.digest(toHash.getBytes());
		byte[] key = new byte[24];
		System.arraycopy(digest, 0, key, 0, 16);
		System.arraycopy(digest, 0, key, 16, 8);

		return key;
	}

    //hardcoded password - bad...
	private static SecretKeySpec secretKeySpec() throws Exception {
        String jenkinsSecretKey = Jenkins.getInstance().getSecretKey();
		return new SecretKeySpec(TripleDES.md5Hash(jenkinsSecretKey), ENCRYPTION_KEYSPECTYPE);
	}
	

    //internal function that decrypts the string after it has been modified to
    //base64 encoding, making it a byte sequence
	private static String decryptString(byte[] text) throws Exception{
		SecretKey key = secretKeySpec();

		try {
			Cipher cipher = Cipher.getInstance(ENCRYPTION_MODE);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] data = cipher.doFinal(text);

			return new String(data, 0, data.length);
		}catch(Exception e) {
			return null;
		}
	}

    //internal function that encrypts the string after it has been modified to
    //base64 encoding, making it a byte sequence
	static byte[] encryptString(byte[] text) throws Exception{
		SecretKey key = secretKeySpec();

		try {
			Cipher cipher = Cipher.getInstance(ENCRYPTION_MODE);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] data = cipher.doFinal(text);

			return data;
		}catch(Exception e) {
			return null;
		}
	}

    /**
     *
     * @param password string to encrypt
     * @return encrypted password
     */
    public static String encryptPassword(String password) {

        byte [] encString = new byte[0];
        try {
            encString = TripleDES.encryptString(password.getBytes(DEFAULT_CODEPAGE));
        } catch (Exception e) {
            //never happens
        }
        return DatatypeConverter.printBase64Binary(encString);
    }

}