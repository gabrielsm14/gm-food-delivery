package com.estudo.gmfood.infrastructure.repository.spec;

import com.estudo.gmfood.domain.model.Pedido;
import com.estudo.gmfood.domain.repository.filter.PedidoFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
		return (root, quey, builder) -> {
			root.fetch("restaurante");

			var predicates = new ArrayList<Predicate>();

			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), filtro.getClienteId()));
			}

			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("resturante"), filtro.getClienteId()));
			}

			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			}

			if (filtro.getDataCriacaoFim() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFim()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
