package br.com.ecommerce.dominio;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.dominio.produto.Produto;

/**
 * Agendamento de Serviços
 * @author Mario
 *
 */
@Entity
@Table
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class IntervaloServico extends CadastroDB{


	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="horainicio")
	private Time horainicio;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name="horafim")
	private Time horafim;
	
	/*@Column(name="dia")
	private int dia;*/
	
	@ManyToOne
	@JoinColumn(name="id_produto")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name="id_dia_semana")
	private DiaSemana diaSemana;
	
	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

	public IntervaloServico(){
		
	}
	
	public Time getHorainicio() {
		return horainicio;
	}

	public void setHorainicio(Time horainicio) {
		this.horainicio = horainicio;
	}

	public Time getHorafim() {
		return horafim;
	}

	public void setHorafim(Time horafim) {
		this.horafim = horafim;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}


}
