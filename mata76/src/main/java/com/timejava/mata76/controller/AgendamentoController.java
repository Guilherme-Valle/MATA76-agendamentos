package com.timejava.mata76.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

import com.timejava.mata76.entidades.Agendamento;
import com.timejava.mata76.entidades.Servico;
import com.timejava.mata76.entidades.Usuario;
import com.timejava.mata76.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {

	@Autowired
	AgendamentoService agendamentoService;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	@GetMapping("/test")
	public ResponseEntity<String> testEndpointo() {
		return ResponseEntity.ok("Tuturu!");
	}

	@GetMapping("/buscar")
	public ResponseEntity<Agendamento> buscarAgendamentos() throws ExecutionException, InterruptedException {
		return ResponseEntity.ok(agendamentoService.buscarAgendamento());
	}

	@PostMapping("/criar-agendamento")
	public ResponseEntity<String> criarAgendamento() throws ExecutionException, InterruptedException {
		Servico servico = new Servico(98L, "Oftamologia");
		Usuario usuario = new Usuario("99999999998", "João");
		LocalDateTime date = LocalDateTime.of(2022, Month.NOVEMBER, 22, 14,30);
		Agendamento agendamento = new Agendamento(servico, usuario, date.format(formatter));
		return ResponseEntity.ok(agendamentoService.criarAgenda(agendamento));
	}
}
