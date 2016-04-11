package br.ufrn.dimap.ttracker.data;

import java.io.Serializable;

public enum TaskType implements Serializable {
	VERIFICACAO("VERIFICAÇÃO"),ERROEXECUCAO("ERRO DE EXECUÇÃO"),APRIMORAMENTO("APRIMORAMENTO"),ERRONEGOCIOVALIDACAO("ERRO DE NEGÓCIO/VALIDAÇÃO"),ERROPADRONVISUALIZACAO("ERRO DE PADRON. DE VISUALIZAÇÃO"),OTHER("OTHER");
	
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
		case "ERRO DE EXECUÇÃO":
			return ERROEXECUCAO;
		case "APRIMORAMENTO":
			return APRIMORAMENTO;
		case "ERRO DE NEGÓCIO/VALIDAÇÃO":
			return ERRONEGOCIOVALIDACAO;
		case "ERRO DE PADRON. DE VISUALIZAÇÃO":
			return ERROPADRONVISUALIZACAO;
		default:
			return OTHER;
		}
	}
	
}
