package com.timejava.mata76.entidades;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Agendamento {

	private Servico servico;
	private Usuario usuario;
	private String dataAgendamento;

}
