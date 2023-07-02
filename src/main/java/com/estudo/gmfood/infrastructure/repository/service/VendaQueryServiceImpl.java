package com.estudo.gmfood.infrastructure.repository.service;

import com.estudo.gmfood.domain.filter.VendaDiariaFilter;
import com.estudo.gmfood.domain.model.Pedido;
import com.estudo.gmfood.domain.model.dto.VendaDiaria;
import com.estudo.gmfood.domain.service.VendaQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(VendaDiaria.class);
        var root = query.from(Pedido.class);

        var functionDateDataCriacao = builder.function(
                "date", Date.class, root.get("dataCriacao"));

        var selection = builder.construct(
                VendaDiaria.class,
                functionDateDataCriacao,
                builder.count(root.get("id")),
                builder.sum(root.get("valorTotal")));

        query.select(selection).groupBy(functionDateDataCriacao);

        return manager.createQuery(query).getResultList();
    }
}
