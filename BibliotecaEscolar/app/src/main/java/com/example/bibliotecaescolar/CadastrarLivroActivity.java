package com.example.bibliotecaescolar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bibliotecaescolar.modelos.Livro;
import com.google.firebase.database.*;

public class CadastrarLivroActivity extends AppCompatActivity {

    private EditText edtTitulo, edtAutor, edtGenero, edtClassificacao, edtAnoPublicacao;
    private Button btnSalvarLivro;
    private DatabaseReference livrosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_livro);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtAutor = findViewById(R.id.edtAutor);
        edtGenero = findViewById(R.id.edtGenero);
        edtClassificacao = findViewById(R.id.edtClassificacao);
        edtAnoPublicacao = findViewById(R.id.edtAnoPublicacao);
        btnSalvarLivro = findViewById(R.id.btnSalvarLivro);

        livrosRef = FirebaseDatabase.getInstance().getReference("livros");

        btnSalvarLivro.setOnClickListener(v -> salvarLivro());
    }

    private void salvarLivro() {
        String titulo = edtTitulo.getText().toString().trim();
        String autor = edtAutor.getText().toString().trim();
        String genero = edtGenero.getText().toString().trim();
        String classificacao = edtClassificacao.getText().toString().trim();
        String anoPublicacao = edtAnoPublicacao.getText().toString().trim();

        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty() || classificacao.isEmpty() || anoPublicacao.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buscar último ID para sequencial
        livrosRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long ultimoId = 0;
                for (DataSnapshot child : snapshot.getChildren()) {
                    try {
                        ultimoId = Long.parseLong(child.getKey());
                    } catch (NumberFormatException e) {
                        ultimoId = 0;
                    }
                }
                long novoId = ultimoId + 1;
                String id = String.valueOf(novoId);

                Livro livro = new Livro(id, titulo, autor, genero, classificacao, anoPublicacao);
                livrosRef.child(id).setValue(livro)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(CadastrarLivroActivity.this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                            limparCampos();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(CadastrarLivroActivity.this, "Erro ao cadastrar livro", Toast.LENGTH_SHORT).show()
                        );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CadastrarLivroActivity.this, "Erro ao acessar banco de dados", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limparCampos() {
        edtTitulo.setText("");
        edtAutor.setText("");
        edtGenero.setText("");
        edtClassificacao.setText("");
        edtAnoPublicacao.setText("");
    }
}
