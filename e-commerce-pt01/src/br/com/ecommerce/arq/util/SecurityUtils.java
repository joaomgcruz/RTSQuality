package br.com.ecommerce.arq.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtils {

	public static String toMD5(String string) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(string.getBytes());
		byte[] hashMd5 = md.digest();

		StringBuilder novaSenha = new StringBuilder();
		
		for (int i = 0; i < hashMd5.length; i++) {
			int parteAlta = ((hashMd5[i] >> 4) & 0xf) << 4;
			int parteBaixa = hashMd5[i] & 0xf;
			if (parteAlta == 0) novaSenha.append('0');
			novaSenha.append(Integer.toHexString(parteAlta | parteBaixa));
		}
		
		return novaSenha.toString();
	}
}
