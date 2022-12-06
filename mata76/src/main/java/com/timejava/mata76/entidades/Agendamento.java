package com.timejava.mata76.entidades;


import javax.annotation.Nullable;

import com.timejava.mata76.Enums.StatusAgendamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Agendamento {

	private Servico servico;
	@Nullable
	private Usuario usuario;
	private String dataAgendamento;
	private StatusAgendamento status;

}
