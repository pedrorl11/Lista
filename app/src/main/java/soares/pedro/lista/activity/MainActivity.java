package soares.pedro.lista.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import soares.pedro.lista.adapter.MyAdapter;
import soares.pedro.lista.model.MyItem;
import soares.pedro.lista.R;

public class MainActivity extends AppCompatActivity {

    MyAdapter myAdapter;
    static int NEW_ITEM_REQUEST = 1;
    List<MyItem> itens = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if(requestCode == NEW_ITEM_REQUEST) {//verifico se o resultado corresponde a new item request
             if(resultCode == Activity.RESULT_OK) {//verifico se a tela de destino retornou sem erros

                 MyItem myItem = new MyItem();//crio um item
                 myItem.title = data.getStringExtra("title");//atribuo o titulo retornado pelo usuario para o item
                 myItem.description = data.getStringExtra("description");//atribuo a descricao retornada pelo usuario para o item
                 myItem.photo = data.getData();//atribuo a foto retornada pelo usuario para o item
                 itens.add(myItem); // adiciono o item na array de itens
                 myAdapter.notifyItemInserted(itens.size()-1);//aviso o adapter que um item foi criado para atualizar o recyclerview
                 }
             }
         }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvItens = findViewById(R.id.rvItens);// pego o recyclervview
        myAdapter = new MyAdapter(this,itens); // crio o myadapter
        rvItens.setAdapter(myAdapter); //seto o adapter no recyclerview

        rvItens.setHasFixedSize(true);//indico que nao ha variacao de tamanho entre itens

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);//mostra a lista de forma linear e vertical
        rvItens.setLayoutManager(layoutManager); // passo esse layout para o recyclerview

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL);//crio uma linha divisoria entre itens
        rvItens.addItemDecoration(dividerItemDecoration);//seto a configuraçao para o recyclerview

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);//pego o botao
        fabAddItem.setOnClickListener(new View.OnClickListener() {//crio uma acao para o botao ao ser clicado
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewItemActivity.class);//crio um intent explicito
                startActivityForResult(i, NEW_ITEM_REQUEST);//uso o metodo da intent dizendo que a tela new_item ira retornar valores
            }
        });
    }
}