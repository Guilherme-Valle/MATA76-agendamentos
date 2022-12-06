package com.timejava.mata76.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.timejava.mata76.Enums.StatusAgendamento;
import com.timejava.mata76.entidades.Agendamento;
import com.timejava.mata76.entidades.Servico;
import com.timejava.mata76.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

	private final static String COLECAO = "Agendamento";

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	@Autowired
	ServicoService servicoService;

	public Agendamento buscar(String documentId) throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection(COLECAO).document(documentId);
		ApiFuture<DocumentSnapshot> future = documentReference.get();

		DocumentSnapshot document = future.get();

		Agendamento agendamento = null;

		if(document.exists()) {
			agendamento = document.toObject(Agendamento.class);
			return agendamento;
		} else {
			return null;
		}
	}

	public List<Agendamento> buscar() throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query documentReference = dbFirestore.collection(COLECAO).orderBy("dataAgendamento");
		ApiFuture<QuerySnapshot> query = documentReference.get();
		List<Agendamento> agendamentos = new ArrayList();
		for (DocumentSnapshot document : query.get().getDocuments()) {
			agendamentos.add(document.toObject(Agendamento.class));
		}

		return agendamentos;
	}

	public List<Agendamento> buscarPorData(LocalDate data) throws ExecutionException, InterruptedException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		String dataFormatada = formatter.format(data.atStartOfDay());
		String dataFormataa = formatter.format(data.atTime(23, 59));
		Query documentReference = dbFirestore.collection(COLECAO).whereGreaterThanOrEqualTo("dataAgendamento", dataFormatada).whereLessThanOrEqualTo("dataAgendamento", dataFormataa);
		ApiFuture<QuerySnapshot> query = documentReference.get();
		List<Agendamento> agendamentos = new ArrayList();
		for (DocumentSnapshot document : query.get().getDocuments()) {
			agendamentos.add(document.toObject(Agendamento.class));
		}

		return agendamentos;
	}

	public List<Agendamento> buscarPorServico(Long servicoId) throws ExecutionException, InterruptedException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query documentReference = dbFirestore.collection(COLECAO).whereEqualTo("servico.id", servicoId);
		ApiFuture<QuerySnapshot> query = documentReference.get();
		List<Agendamento> agendamentos = new ArrayList();
		for (DocumentSnapshot document : query.get().getDocuments()) {
			agendamentos.add(document.toObject(Agendamento.class));
		}

		return agendamentos;
	}

	public List<Agendamento> buscarPorCpf(String cpf) throws ExecutionException, InterruptedException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query documentReference = dbFirestore.collection(COLECAO).whereEqualTo("usuario.cpf", cpf);
		ApiFuture<QuerySnapshot> query = documentReference.get();
		List<Agendamento> agendamentos = new ArrayList();
		for (DocumentSnapshot document : query.get().getDocuments()) {
			agendamentos.add(document.toObject(Agendamento.class));
		}

		return agendamentos;
	}

	private List<Agendamento> buscarAgendamentosDisponiveis(Long servicoId) throws ExecutionException, InterruptedException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		Query documentReference = dbFirestore.collection(COLECAO).whereEqualTo("servico.id", servicoId).whereEqualTo("usuario", null);
		ApiFuture<QuerySnapshot> query = documentReference.get();
		List<Agendamento> agendamentos = new ArrayList();
		for (DocumentSnapshot document : query.get().getDocuments()) {
			agendamentos.add(document.toObject(Agendamento.class));
		}

		return agendamentos;
	}

	public String salvar(Agendamento agendamento) {
		String documentId = gerarDocumentId(agendamento);
		Firestore dbFirestore = FirestoreClient.getFirestore();
		dbFirestore.collection(COLECAO).document(documentId).set(agendamento);
		return documentId;
	}

	public String atualizar(String documentId, Agendamento agendamento) throws ExecutionException, InterruptedException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COLECAO).document(documentId).set(agendamento);
		return collectionsApiFuture.get().getUpdateTime().toString();
	}

	public void remover(String documentId) {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		dbFirestore.collection(COLECAO).document(documentId).delete();
	}

	private String gerarDocumentId(Agendamento agendamento) {
		String documentId = agendamento.getDataAgendamento().replaceAll("[^0-9]", "") + "d" + agendamento.getServico().getId() + "s";
		return documentId;
	}

	public void finalizar(String documentId, Agendamento agendamento) throws ExecutionException, InterruptedException {
		agendamento.setStatus(StatusAgendamento.FINALIZADO);
		atualizar(documentId, agendamento);
	}

	public String marcarNovoAgendamento(Usuario usuario, Servico servico) throws ExecutionException, InterruptedException {
		List<Agendamento> agendamentos = buscarAgendamentosDisponiveis(servico.getId());
		Agendamento agendamentoDisponivel = agendamentos.get(0);
		agendamentoDisponivel.setUsuario(usuario);
		atualizar(gerarDocumentId(agendamentoDisponivel), agendamentoDisponivel);
		return agendamentoDisponivel.getDataAgendamento();
	}
}
