package br.com.ecommerce.arq.mensagem;

import br.com.ecommerce.arq.dominio.TipoMensagem;

/**
 * Apresenta mensagens que são constatemente reusadas pelo sistema.
 * 
 * @author Rodrigo Dutra de Oliveira
 *
 */
public final class MensagensArquitetura {

	/**
	 * Mensagem informando que um determinado campo obrigatório não foi informado.
	 */
	public static final MensagemArq CAMPO_OBRIGATORIO_NAO_INFORMADO = new MensagemArq("%s: Campo obrigatório não informado.", 
			TipoMensagem.ERRO);
	
	/**
	 * Mensagem informando que um determinado solicitação já havia sido processada.
	 */
	public static final MensagemArq SOLICITACAO_JA_PROCESSADA = new MensagemArq("%s: Operação já havia sido processada.", 
			TipoMensagem.ERRO);
	
	/**
	 * Mensagem informando que um determinado solicitação já havia sido processada.
	 */
	public static final MensagemArq OPERACAO_REALIZADA_COM_SUCESSO = new MensagemArq("%s: Operação realizada com sucesso.", 
			TipoMensagem.INFORMATION);
	
	/**
	 * Mensagem informando que o elemento não se encontra mais no banco de dados.
	 */
	public static final MensagemArq ELEMENTO_NAO_DISPONIVEL_NO_BANCO = new MensagemArq("%s: Não se encontra mais no banco.", 
			TipoMensagem.INFORMATION);
	
	public static final MensagemArq BUSCA_SEM_RESULTADOS = new MensagemArq("%s: Não foram encontrados resultados com estes parâmetros.", 
			TipoMensagem.ERRO);
	
}
