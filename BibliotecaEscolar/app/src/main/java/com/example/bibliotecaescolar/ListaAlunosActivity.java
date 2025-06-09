package com.example.bibliotecaescolar;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bibliotecaescolar.modelos.Aluno;
import com.example.bibliotecaescolar.modelos.Emprestimo;
import com.google.firebase.database.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listViewAlunos;
    private DatabaseReference alunosRef, emprestimosRef;
    private List<Aluno> listaAlunos = new ArrayList<>();
    private Map<String, String> emprestimosPorAluno = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listViewAlunos = findViewById(R.id.listViewAlunos);
        alunosRef = FirebaseDatabase.getInstance().getReference("alunos");
        emprestimosRef = FirebaseDatabase.getInstance().getReference("emprestimos");

        carregarEmprestimos();
    }

    private void carregarEmprestimos() {
        emprestimosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    Emprestimo e = d.getValue(Emprestimo.class);
                    if (e != null && e.getIdAluno() != null && e.getIdLivro() != null) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                            Date hoje = Calendar.getInstance().getTime();
                            Date dataDev = sdf.parse(e.getDataDevolucao());

                            if (dataDev != null && dataDev.after(hoje)) {
                                // empréstimo ainda válido
                                emprestimosPorAluno.put(e.getIdAluno(), e.getNomeLivro());
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }


                }
                carregarAlunos();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }


    private void carregarAlunos() {
        alunosRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                listaAlunos.clear();
                for (DataSnapshot d : snapshot.getChildren()) {
                    Aluno a = d.getValue(Aluno.class);
                    if (a != null) {
                        listaAlunos.add(a);
                    }
                }

                listViewAlunos.setAdapter(new ArrayAdapter<Aluno>(ListaAlunosActivity.this, R.layout.item_aluno, listaAlunos) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        if (convertView == null) {
                            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_aluno, parent, false);
                        }

                        Aluno aluno = getItem(position);
                        ((TextView) convertView.findViewById(R.id.txtId)).setText("ID: " + aluno.getId());
                        ((TextView) convertView.findViewById(R.id.txtNome)).setText("Nome: " + aluno.getNome());
                        ((TextView) convertView.findViewById(R.id.txtMatricula)).setText("Matrícula: " + aluno.getMatricula());
                        ((TextView) convertView.findViewById(R.id.txtTurma)).setText("Turma: " + aluno.getTurma());

                        String livroEmprestado = emprestimosPorAluno.get(aluno.getId());
                        String status = (livroEmprestado != null) ? livroEmprestado : "Nenhum livro emprestado";
                        ((TextView) convertView.findViewById(R.id.txtLivroStatus)).setText("Livro emprestado: " + status);

                        return convertView;
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}
