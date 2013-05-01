package br.com.ecommerce.arq.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe utilitária para se tratar com datas
 * 
 * @author Rodrigo Dutra de Oliveira
 * 
 */
public class CalendarUtils {

	/**
	 * Ano inicio padrão.
	 * 
	 * @TODO passar para o banco como um parâmetro.
	 */
	public static final int ANO_INICIO_PADRAO = 2002;

	/**
	 * Método usado para se obter o ano corrente
	 * 
	 * @return o ano corrente
	 */
	public static int getAnoAtual() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * Método usado para converter uma String para Hora
	 * 
	 * @return hora
	 */
	public static Time convertStringToTime(String hora) throws ParseException {
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
		Date data = formatador.parse(hora);
		Time time = new Time(data.getTime());
		return time;
	}
	
	@SuppressWarnings("deprecation")
	public static int calcularIdade(Date date){
		Date data = new Date();
		int idadeAux = data.getYear() - date.getYear();
		if (data.getMonth() - date.getMonth() < 0){
			idadeAux = idadeAux - 1;
		} else if (data.getMonth() - date.getMonth() == 0){
			if (data.getDate() - date.getDate() < 0){
				idadeAux = idadeAux - 1;
			}
		}
		return idadeAux;
	}
}
