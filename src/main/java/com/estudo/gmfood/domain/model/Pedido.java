package com.estudo.gmfood.domain.model;

import com.estudo.gmfood.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigo;
	private BigDecimal subtotal; 
	private BigDecimal valorTotal;
	private BigDecimal taxaFrete;

	@Embedded
	private Endereco enderecoEntrega;

	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;

	@CreationTimestamp
	private LocalDateTime dataCriacao;
	private LocalDateTime dataConfirmacao;
	private LocalDateTime dataCancelamento;
	private LocalDateTime dataEntrega;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();

	public void definirFrete() {
		setTaxaFrete(getRestaurante().getTaxaFrete());
	}

	public void atribuirPedidoAosItens() {
		getItens().forEach(item -> item.setPedido(this));
	}

	public void calcularValorTotal() {
		getItens().forEach(ItemPedido::calcularPrecoTotal);

		this.subtotal = getItens()
				.stream()
				.map(item -> item.getPrecoTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		this.valorTotal = this.subtotal.add(this.taxaFrete);
	}

	public void confimar() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(LocalDateTime.now());
	}

	public void entregar() {
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(LocalDateTime.now());
	}

	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(LocalDateTime.now());
	}

	private void setStatus(StatusPedido novoStatus) {
		if (getStatus().naoPodeAlterarPara(novoStatus)) {
			throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s",
					getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));
		}
		this.status = novoStatus;
	}

	@PrePersist
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}

}






