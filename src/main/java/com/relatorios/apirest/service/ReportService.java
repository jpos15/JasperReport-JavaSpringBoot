package com.relatorios.apirest.service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.relatorios.apirest.models.Cliente;
import com.relatorios.apirest.repository.ClienteRepository;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ResourceLoader resourceLoader;

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

    public byte[] recebeJson(List<Object> json) throws JRException, IOException {
        Map parametros = new HashMap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        try {
            InputStream jrxmlInput = this.getClass().getClassLoader().getResource("report_cliente_json.jrxml").openStream();
            //this.getClass().getClassLoader().getResource("data.jrxml").openStream();
            JasperDesign design = JRXmlLoader.load(jrxmlInput);
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            //System.out.println("Report compiled");
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(json);
            //JasperReport jasperReport = JasperCompileManager.compileReport(reportLocation);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource); // datasource Service

            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
            exporter.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in generate Report..."+e);
        } finally {
        }
        return outputStream.toByteArray();
    }
}
