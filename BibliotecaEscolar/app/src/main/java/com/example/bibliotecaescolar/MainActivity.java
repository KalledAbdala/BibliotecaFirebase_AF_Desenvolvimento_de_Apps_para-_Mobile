package com.example.bibliotecaescolar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Button btnCadastrarLivro = findViewById(R.id.btnCadastrarLivro);
        Button btnCadastrarAluno = findViewById(R.id.btnCadastrarAluno);
        Button btnEmprestimo = findViewById(R.id.btnEmprestimo);
        Button btnDevolucao = findViewById(R.id.btnDevolucao);
        Button btnHistorico = findViewById(R.id.btnHistorico);
        Button btnListaLivros = findViewById(R.id.btnListaLivros);
        Button btnListaAlunos = findViewById(R.id.btnListaAlunos);



        btnCadastrarLivro.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastrarLivroActivity.class);
            startActivity(intent);
        });

        btnCadastrarAluno.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CadastrarAlunoActivity.class);
            startActivity(intent);
        });

        btnEmprestimo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RealizarEmprestimoActivity.class);
            startActivity(intent);
        });

        btnDevolucao.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegistrarDevolucaoActivity.class);
            startActivity(intent);
        });

        btnHistorico.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoricoEmprestimosActivity.class);
            startActivity(intent);
        });

        btnListaLivros.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaLivrosActivity.class);
            startActivity(intent);
        });

        btnListaAlunos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListaAlunosActivity.class);
            startActivity(intent);
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
