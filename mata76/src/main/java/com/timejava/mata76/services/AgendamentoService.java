package com.timejava.mata76.services;

import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.timejava.mata76.entidades.Agendamento;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

	public Agendamento buscarAgendamento() throws InterruptedException, ExecutionException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = dbFirestore.collection("Agendamento").document("Agendar");
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

	public String criarAgenda(Agendamento agendamento) throws ExecutionException, InterruptedException {
		Firestore dbFirestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection("Agendamento").document("Agendar_2").set(agendamento);
		return collectionsApiFuture.get().getUpdateTime().toString();
	};
}
