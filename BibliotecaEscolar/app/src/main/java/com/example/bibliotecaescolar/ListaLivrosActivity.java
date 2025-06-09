package com.example.bibliotecaescolar;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bibliotecaescolar.modelos.Livro;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class ListaLivrosActivity extends AppCompatActivity {

    private ListView listViewLivros;
    private List<Livro> livros = new ArrayList<>();
    private DatabaseReference livrosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_livros);

        listViewLivros = findViewById(R.id.listViewLivros);
        livrosRef = FirebaseDatabase.getInstance().getReference("livros");

        livrosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                livros.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Livro livro = snap.getValue(Livro.class);
                    livros.add(livro);
                }
                listViewLivros.setAdapter(new LivroAdapter());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Lidar com erro
            }
        });
    }

    class LivroAdapter extends ArrayAdapter<Livro> {
        LivroAdapter() {
            super(ListaLivrosActivity.this, R.layout.item_livro, livros);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Livro livro = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_livro, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.txtTitulo)).setText("Título: " + livro.getTitulo());
            ((TextView) convertView.findViewById(R.id.txtAutor)).setText("Autor: " + livro.getAutor());
            ((TextView) convertView.findViewById(R.id.txtGenero)).setText("Gênero: " + livro.getGenero());
            ((TextView) convertView.findViewById(R.id.txtClassificacao)).setText("Classificação: " + livro.getClassificacao());

            return convertView;
        }
    }
}
