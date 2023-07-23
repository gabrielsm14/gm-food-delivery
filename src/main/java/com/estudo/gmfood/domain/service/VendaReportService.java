package com.estudo.gmfood.domain.service;

import com.estudo.gmfood.domain.filter.VendaDiariaFilter;
import com.estudo.gmfood.domain.model.dto.VendaDiaria;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);
}
