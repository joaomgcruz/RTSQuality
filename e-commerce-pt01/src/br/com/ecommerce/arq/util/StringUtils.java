package br.com.ecommerce.arq.util;

/**
 * Classe utilitária para se mecher com Strings
 * @author Rodrigo Dutra de Oliveira
 *
 */
public class StringUtils {

	public StringUtils(){
		
	}
	
	/**
	 * Valida endereços de email
	 * @param email
	 * 
	 * @return
	 */
	public static boolean validaEmail(String email) {
		return (email != null && email.length() > 0
				&& email.trim().equals(email) && email.contains(".")
				&& email.contains("@") && !email.contains(" "));
	}
	
	/**
	 * Remove as tags html
	 * @param html
	 * @return
	 */
	public static String stripHtmlTags(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("\\<.*?>","");
	}
	
	/**
	 * Transforma toda ocorrência de uma aspas simples em aspas duplicadas.
	 * Utilizado para construção de queries SQL.
	 *
	 * @param original
	 * @return
	 */
	public static String trataAspasSimples(String original) {
		return original.replaceAll("'", "''");
	}
}
