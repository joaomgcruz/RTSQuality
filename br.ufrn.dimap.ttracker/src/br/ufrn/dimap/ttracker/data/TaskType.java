package br.ufrn.dimap.ttracker.data;

import java.io.Serializable;

public enum TaskType implements Serializable {
	VERIFICACAO("VERIFICA��O"),ERROEXECUCAO("ERRO DE EXECU��O"),APRIMORAMENTO("APRIMORAMENTO"),ERRONEGOCIOVALIDACAO("ERRO DE NEG�CIO/VALIDA��O"),ERROPADRONVISUALIZACAO("ERRO DE PADRON. DE VISUALIZA��O"),OTHER("OTHER");
	
	private String name;
	
	TaskType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static TaskType getTaskTypeByName(String name) {
		switch(name) {
		case "VERIFICACAO":
			return VERIFICACAO;
		case "ERRO DE EXECU��O":
			return ERROEXECUCAO;
		case "APRIMORAMENTO":
			return APRIMORAMENTO;
		case "ERRO DE NEG�CIO/VALIDA��O":
			return ERRONEGOCIOVALIDACAO;
		case "ERRO DE PADRON. DE VISUALIZA��O":
			return ERROPADRONVISUALIZACAO;
		default:
			return OTHER;
		}
	}
	
}
