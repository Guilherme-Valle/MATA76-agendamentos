package com.timejava.mata76.Enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusAgendamento {

	DISPONIVEL("Disponivel"),
	AGENDADO("Agendado"),
	FINALIZADO("Finalizado");

	StatusAgendamento(String status) {
	}
}
