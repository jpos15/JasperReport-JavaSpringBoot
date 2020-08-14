package com.relatorios.apirest.resources;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import net.sf.jasperreports.engine.JRException;

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
	public void json(@RequestBody List<Object> lista) throws FileNotFoundException, JRException {
		reportService.recebeJson(lista);
	}
}
