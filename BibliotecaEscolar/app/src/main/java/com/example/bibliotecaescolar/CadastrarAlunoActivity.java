package com.example.bibliotecaescolar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bibliotecaescolar.modelos.Aluno;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastrarAlunoActivity extends AppCompatActivity {

    private EditText editNomeAluno, editMatricula, editTurma;
    private Button btnSalvarAluno;

    private DatabaseReference alunosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_aluno);

        editNomeAluno = findViewById(R.id.editNomeAluno);
        editMatricula = findViewById(R.id.editMatricula);
        editTurma = findViewById(R.id.editTurma);
        btnSalvarAluno = findViewById(R.id.btnSalvarAluno);

        alunosRef = FirebaseDatabase.getInstance().getReference("alunos");

        btnSalvarAluno.setOnClickListener(view -> salvarAluno());
    }

    private void salvarAluno() {
        String nome = editNomeAluno.getText().toString();
        String matricula = editMatricula.getText().toString();
        String turma = editTurma.getText().toString();

        if (nome.isEmpty() || matricula.isEmpty() || turma.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference alunosRef = FirebaseDatabase.getInstance().getReference("alunos");

        alunosRef.get().addOnSuccessListener(snapshot -> {
            long proximoId = 1;
            if (snapshot.exists()) {
                proximoId = snapshot.getChildrenCount() + 1;
            }

            String id = String.valueOf(proximoId);

            Aluno aluno = new Aluno(id, nome, matricula, turma);
            alunosRef.child(id).setValue(aluno)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Aluno salvo com ID #" + id, Toast.LENGTH_SHORT).show();
                        editNomeAluno.setText("");
                        editMatricula.setText("");
                        editTurma.setText("");
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        }).addOnFailureListener(e ->
                Toast.makeText(this, "Erro ao acessar o banco: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

}
