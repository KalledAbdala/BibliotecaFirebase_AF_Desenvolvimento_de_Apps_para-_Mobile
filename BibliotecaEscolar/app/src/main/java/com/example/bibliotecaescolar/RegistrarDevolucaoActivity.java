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
import java.util.Date;
import java.util.Locale;

public class RegistrarDevolucaoActivity extends AppCompatActivity {

    private EditText editIdEmprestimo;
    private Button btnRegistrarDevolucao;
    private DatabaseReference emprestimosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_devolucao);

        editIdEmprestimo = findViewById(R.id.editIdEmprestimo);
        btnRegistrarDevolucao = findViewById(R.id.btnRegistrarDevolucao);
        emprestimosRef = FirebaseDatabase.getInstance().getReference("emprestimos");

        btnRegistrarDevolucao.setOnClickListener(v -> registrarDevolucao());
    }

    private void registrarDevolucao() {
        String id = editIdEmprestimo.getText().toString();

        if (id.isEmpty()) {
            Toast.makeText(this, "Informe o ID do empréstimo", Toast.LENGTH_SHORT).show();
            return;
        }

        emprestimosRef.child(id).get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                String dataHoje = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                snapshot.getRef().child("dataDevolucao").setValue(dataHoje)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Devolução registrada com sucesso!", Toast.LENGTH_SHORT).show();
                            editIdEmprestimo.setText("");
                        })
                        .addOnFailureListener(e -> Toast.makeText(this, "Erro ao registrar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(this, "Empréstimo não encontrado", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Erro ao acessar o banco: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
