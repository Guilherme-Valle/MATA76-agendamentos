package com.timejava.mata76.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.timejava.mata76.Enums.StatusAgendamento;
import com.timejava.mata76.entidades.Agendamento;
import com.timejava.mata76.entidades.Servico;
import com.timejava.mata76.entidades.Usuario;
import com.timejava.mata76.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

	@Autowired
	AgendamentoService agendamentoService;

	@GetMapping("/agendamento/{documentId}")
	public ResponseEntity<Agendamento> buscarAgendamento(@PathVariable String documentId) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(agendamentoService.buscar(documentId));
	}

	@GetMapping
	public ResponseEntity<List<Agendamento>> buscarAgendamentos() throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(agendamentoService.buscar());
	}

	@GetMapping(params = "data")
	public ResponseEntity<List<Agendamento>> buscarAgendamento(@RequestParam LocalDate data) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(agendamentoService.buscarPorData(data));
	}

	@GetMapping(params = "servico")
	public ResponseEntity<List<Agendamento>> buscarAgendamentos(@RequestParam Long servico) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(agendamentoService.buscarPorServico(servico));
	}

	@GetMapping(params = "cpf")
	public ResponseEntity<List<Agendamento>> buscarAgendamentos(@RequestParam String cpf) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(agendamentoService.buscarPorCpf(cpf));
	}

	@PostMapping("/criar-agendamento")
	public ResponseEntity<String> criarAgendamento(@RequestBody Agendamento agendamento) throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(agendamentoService.salvar(agendamento));
	}

	@PostMapping("/remover-agendamento/{documentId}")
	public ResponseEntity<String> removerAgendamento(@PathVariable String documentId){
		agendamentoService.remover(documentId);
		return ResponseEntity.status(200).body("Agendamento removido!");
	}

	@PostMapping("/finalizar-agendamento/{documentId}")
	public ResponseEntity<String> finalizarAgendamento(@PathVariable String documentId) throws ExecutionException, InterruptedException {
		Agendamento agendamento = agendamentoService.buscar(documentId);
		agendamentoService.finalizar(documentId, agendamento);
		return ResponseEntity.ok("Finalizado! Novo agendamento em: " + agendamentoService.marcarNovoAgendamento(agendamento.getUsuario(), agendamento.getServico()));
	}
}
