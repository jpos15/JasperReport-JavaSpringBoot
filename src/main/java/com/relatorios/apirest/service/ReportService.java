package com.relatorios.apirest.service;

import java.io.*;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.relatorios.apirest.models.Cliente;
import com.relatorios.apirest.repository.ClienteRepository;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {

    @Autowired
    ClienteRepository clienteRepository;

    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "C:\\\\Users\\\\João Paulo\\\\Desktop\\\\Nova pasta";

        List<Cliente> cliente = clienteRepository.findAll();

        ////load file and compile it
        File file = ResourceUtils.getFile("classpath:report_cliente_bd.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cliente);
        Map<String, Object> parameters = new HashedMap();
        parameters.put("createdBy", "Java Techie");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\report_cliente_bd.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\report_cliente_bd.pdf");
        }
        return "Report generated in path" + path;
    }

    public void recebeJson(List<Object> json) throws FileNotFoundException, JRException {
        String path = "C:\\\\Users\\\\João Paulo\\\\Desktop\\\\Nova pasta";

        ////load file and compile it
        File file = ResourceUtils.getFile("classpath:report_cliente_json.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(json);
        Map<String, Object> parameters = new HashedMap();
        parameters.put("createdBy", "Java Techie");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\report_cliente_bd.pdf");
    }

}
