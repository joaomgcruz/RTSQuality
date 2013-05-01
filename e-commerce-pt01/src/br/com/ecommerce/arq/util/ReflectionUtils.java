package br.com.ecommerce.arq.util;

import java.util.StringTokenizer;

/**
 * Classe utilitária para se tratar em acesso direto a objetos e classes.
 * @author Rodrigo Dutra de Oliveira
 *
 */
public class ReflectionUtils {
	
	public ReflectionUtils(){
		
	}
	
	/**
	 * Verifica se uma classe tem um determinado atributo. Muito util, por
	 * exemplo, quando se quer verificar o atributo "ativo" no DAO, onde
	 * temos a classe e no o objeto.
	 */
	public static String evalProperty(Object obj, String property) {
		try {
			property = property.trim();
			StringTokenizer st = new StringTokenizer(property, ".");
			String propertyInsideToken;

			Object atual = obj;
			Class<?> c = obj.getClass();

			while (st.hasMoreTokens()) {
				propertyInsideToken = st.nextToken();
				atual = c.getMethod(formatMethod(propertyInsideToken),
						(Class[]) null).invoke(atual, (Object[]) null);
				if (atual == null) {
					return "";
				}

				c = atual.getClass();

			}

			return atual.toString();

		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * Formata um método de acesso a um determinado fild obedecendo as convenções java beans
	 * @param field
	 * 
	 * @return
	 */
	private static String formatMethod(String field) {
		return "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
	}
}
