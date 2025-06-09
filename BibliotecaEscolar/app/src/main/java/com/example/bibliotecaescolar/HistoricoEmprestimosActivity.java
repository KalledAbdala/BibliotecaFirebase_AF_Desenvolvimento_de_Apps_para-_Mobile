package com.example.bibliotecaescolar;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bibliotecaescolar.adapter.EmprestimoAdapter;
import com.example.bibliotecaescolar.modelos.Emprestimo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoricoEmprestimosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EmprestimoAdapter adapter;
    private List<Emprestimo> listaEmprestimos;
    private DatabaseReference emprestimosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_emprestimos);

        recyclerView = findViewById(R.id.recyclerEmprestimos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaEmprestimos = new ArrayList<>();
        adapter = new EmprestimoAdapter(listaEmprestimos);
        recyclerView.setAdapter(adapter);

        emprestimosRef = FirebaseDatabase.getInstance().getReference("emprestimos");

        carregarEmprestimos();
    }

    private void carregarEmprestimos() {
        emprestimosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaEmprestimos.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    Emprestimo emp = snap.getValue(Emprestimo.class);
                    if (emp != null) {
                        String id = emp.getId();
                        String idAluno = emp.getIdAluno();
                        String idLivro = emp.getIdLivro();
                        String dataEmprestimo = emp.getDataEmprestimo();
                        String dataDevolucao = emp.getDataDevolucao();

                        buscarAlunoELivro(id, idAluno, idLivro, dataEmprestimo, dataDevolucao);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistoricoEmprestimosActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buscarAlunoELivro(String id, String idAluno, String idLivro, String dataEmprestimo, String dataDevolucao) {
        DatabaseReference alunosRef = FirebaseDatabase.getInstance().getReference("alunos").child(idAluno);
        DatabaseReference livrosRef = FirebaseDatabase.getInstance().getReference("livros").child(idLivro);

        alunosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot alunoSnap) {
                String nomeAluno = alunoSnap.child("nome").getValue(String.class);

                livrosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot livroSnap) {
                        String nomeLivro = livroSnap.child("titulo").getValue(String.class);

                        Emprestimo emprestimo = new Emprestimo(
                                id,
                                idAluno,
                                nomeAluno, // recupere o nome com base no ID
                                idLivro,
                                nomeLivro, // idem
                                dataEmprestimo,
                                dataDevolucao
                        );


                        listaEmprestimos.add(emprestimo);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }


}
