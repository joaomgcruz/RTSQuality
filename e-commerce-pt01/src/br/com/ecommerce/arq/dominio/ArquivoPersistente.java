package br.com.ecommerce.arq.dominio;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Classe usada para armazenar arquivos.
 * @author Rodrigo Dutra de Oliveira
 *
 */
@Entity
@Table(name="arquivo_persistente")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class ArquivoPersistente extends SimpleDB{

	/**
	 * Arquivo armazenado.
	 */
	@Lob
	private byte[] arquivo;
	
	private String nome;
	
	private String descricao;
	
	public ArquivoPersistente(){
		
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
