package com.estudo.gmfood.domain.service;

import com.estudo.gmfood.domain.filter.VendaDiariaFilter;
import com.estudo.gmfood.domain.model.dto.VendaDiaria;

import java.util.List;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);
}
