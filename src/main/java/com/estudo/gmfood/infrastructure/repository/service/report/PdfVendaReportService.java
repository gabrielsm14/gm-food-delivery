package com.estudo.gmfood.infrastructure.repository.service.report;

import com.estudo.gmfood.domain.filter.VendaDiariaFilter;
import com.estudo.gmfood.domain.service.VendaQueryService;
import com.estudo.gmfood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfVendaReportService implements VendaReportService {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet) {
        try {
            var inputStream = this.getClass().getResourceAsStream("/report/vendas-diarias.jasper");

            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var dataSource = new JRBeanCollectionDataSource(vendaQueryService.consultarVendasDiarias(filtro, timeOffSet));

            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Não foi possivel emitir relatorio de vendas diarias", e.getCause());
        }
    }
}
