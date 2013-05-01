package br.com.ecommerce.dominio.produto;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import br.com.ecommerce.arq.dominio.CadastroDB;
import br.com.ecommerce.dominio.TipoArmazenamento;

@Entity
@Table
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Produto extends CadastroDB {

	/**
	 * Nome do produto
	 */
	@Column(length = 1024)
	private String nome;

	/**
	 * Preço do produto 
	 */

	private double preco;

	/**
	 * Observações importantes que devem ser mostradas ao consumidor.
	 */
	@Column(length = 2048)
	private String observacao;

	@OneToMany(mappedBy = "produto")
	@OrderBy("posicao ASC")
	private Collection<CampoExtraProduto> camposExtras;

	/**
	 * Tipo do Produto.
	 */
	@ManyToOne
	@JoinColumn(name = "id_tipo")
	@Deprecated
	private TipoProduto tipo;

	/**
	 * SubTipo do produto.
	 */
	@ManyToOne
	@JoinColumn(name = "id_subtipo")
	private SubTipoProduto subTipo;

	/**
	 * URL da imagem do produto.
	 */
	@Column(length = 4096, name = "url_imagem")
	private String urlImagem;

	@ManyToOne
	@JoinColumn(name = "id_tipo_armazenamento")
	private TipoArmazenamento tipoArmazenamento;

	@Column(length = 4096)
	private String caracteristicas;

	@Column(length = 128)
	private String warranty;

	@Column(length = 128)
	private String size;

	private double Weight;

	public Produto() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public void setCamposExtras(Collection<CampoExtraProduto> camposExtras) {
		this.camposExtras = camposExtras;
	}

	public Collection<CampoExtraProduto> getCamposExtras() {
		return camposExtras;
	}

	@Deprecated
	public TipoProduto getTipo() {
		return tipo;
	}

	@Deprecated
	public void setTipo(TipoProduto tipo) {
		this.tipo = tipo;
	}

	public SubTipoProduto getSubTipo() {
		return subTipo;
	}

	public void setSubTipo(SubTipoProduto subTipo) {
		this.subTipo = subTipo;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getPreco() {
		return preco;
	}

	public TipoArmazenamento getTipoArmazenamento() {
		return tipoArmazenamento;
	}

	public void setTipoArmazenamento(TipoArmazenamento tipoArmazenamento) {
		this.tipoArmazenamento = tipoArmazenamento;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public double getWeight() {
		return Weight;
	}

	public void setWeight(double weight) {
		Weight = weight;
	}

}
