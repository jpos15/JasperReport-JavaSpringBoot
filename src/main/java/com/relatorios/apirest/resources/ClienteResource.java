package com.relatorios.apirest.resources;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.relatorios.apirest.models.Cliente;
import com.relatorios.apirest.repository.ClienteRepository;
import com.relatorios.apirest.service.ReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api")
@Api(value="API REST Clientes")
@CrossOrigin(origins = "*")
public class ClienteResource {
	
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/clientes")
	@ApiOperation(value = "Retorna uma lista de clientes")
	public List<Cliente> listaClientes(){
		return clienteRepository.findAll();
	}
	
	@GetMapping("/cliente/{id}")
	@ApiOperation(value = "Retorna um unico cliente")
	public Cliente clienteId(@PathVariable(value = "id")long id){
		return clienteRepository.findById(id);
	}
	
	@PostMapping("cliente")
	@ApiOperation(value = "Adiciona um cliente")
	public Cliente adicionarCliente(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@DeleteMapping("/cliente")
	@ApiOperation(value = "Atualiza um cliente")
	public void excluirCliente(@RequestBody Cliente cliente) {
		clienteRepository.delete(cliente);
	}
	
	@PutMapping("/cliente")
	@ApiOperation(value = "Atualiza um cliente")
	public Cliente atualizarCliente(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	@GetMapping("/report/{format}")
	public String gerenateReport(@PathVariable String format) throws FileNotFoundException, JRException {
		return reportService.exportReport(format);
	}

	@PostMapping("/json")
	public Object json(@RequestBody List<Object> lista, HttpServletResponse response) throws IOException, JRException {
		byte[] bytes = reportService.recebeJson(lista);
		System.out.println(bytes);
		String encodedString = Base64.getEncoder().encodeToString(bytes);
		System.out.println(encodedString);

		Object retorno = encodedString;
		return retorno;
	}
}
