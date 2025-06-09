package com.example.bibliotecaescolar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bibliotecaescolar.modelos.Emprestimo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RealizarEmprestimoActivity extends AppCompatActivity {

    private EditText editIdLivro, editIdAluno;
    private Button btnSalvarEmprestimo;

    private DatabaseReference emprestimosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizar_emprestimo);

        editIdLivro = findViewById(R.id.editIdLivro);
        editIdAluno = findViewById(R.id.editIdAluno);
        btnSalvarEmprestimo = findViewById(R.id.btnSalvarEmprestimo);

        emprestimosRef = FirebaseDatabase.getInstance().getReference("emprestimos");

        btnSalvarEmprestimo.setOnClickListener(view -> salvarEmprestimo());
    }

    private void salvarEmprestimo() {
        String idLivro = editIdLivro.getText().toString();
        String idAluno = editIdAluno.getText().toString();

        if (idLivro.isEmpty() || idAluno.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference alunosRef = FirebaseDatabase.getInstance().getReference("alunos").child(idAluno);
        DatabaseReference livrosRef = FirebaseDatabase.getInstance().getReference("livros").child(idLivro);

        alunosRef.get().addOnSuccessListener(alunoSnap -> {
            livrosRef.get().addOnSuccessListener(livroSnap -> {
                if (!alunoSnap.exists() || !livroSnap.exists()) {
                    Toast.makeText(this, "Aluno ou livro não encontrado", Toast.LENGTH_SHORT).show();
                    return;
                }

                String nomeAluno = alunoSnap.child("nome").getValue(String.class);
                String nomeLivro = livroSnap.child("titulo").getValue(String.class);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String dataEmprestimo = sdf.format(Calendar.getInstance().getTime());

                Calendar dataDevolucaoCal = Calendar.getInstance();
                dataDevolucaoCal.add(Calendar.DAY_OF_MONTH, 7);
                String dataDevolucao = sdf.format(dataDevolucaoCal.getTime());

                emprestimosRef.get().addOnSuccessListener(snapshot -> {
                    long proximoId = 1;
                    if (snapshot.exists()) {
                        proximoId = snapshot.getChildrenCount() + 1;
                    }

                    String id = String.valueOf(proximoId);

                    Emprestimo emprestimo = new Emprestimo(
                            id,
                            idAluno,
                            nomeAluno,
                            idLivro,
                            nomeLivro,
                            dataEmprestimo,
                            dataDevolucao
                    );

                    emprestimosRef.child(id).setValue(emprestimo)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(this, "Empréstimo #" + id + " registrado com sucesso!", Toast.LENGTH_SHORT).show();
                                editIdLivro.setText("");
                                editIdAluno.setText("");
                            })
                            .addOnFailureListener(e ->
                                    Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                            );

                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao buscar empréstimos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Erro ao buscar livro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });

        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Erro ao buscar aluno: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }


}
