package soares.pedro.lista.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import soares.pedro.lista.R;
import soares.pedro.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        NewItemActivityViewModel vm = new ViewModelProvider( this ).get(NewItemActivityViewModel.class );

        Uri selectPhotoLocation = vm.getSelectPhotoLocation();
        if(selectPhotoLocation != null) {
            ImageView imvfotoPreview = findViewById(R.id.imvPhotopreview);
            imvfotoPreview.setImageURI(selectPhotoLocation);
        }

        Button btnAdditem = findViewById(R.id.btnAddItem);//recebo o o botao de acrescentar item
        btnAdditem.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { //crio uma acao pro botao

                Uri photoSelected = vm.getSelectPhotoLocation();

                if(photoSelected == null){ //verifico se a imagem foi selecionada
                    Toast.makeText(NewItemActivity.this, "É necssário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;//caso esteja vazia, dou um aviso
                }
                EditText etTitle = findViewById(R.id.etTitle); //seleciono a caixa de texto
                String title = etTitle.getText().toString(); //pego o texto dentro da caixa
                if(title.isEmpty()){ //verifico se o titulo foi preenchido
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;//caso esteja vazia, dou um aviso
                }

                EditText etDesc = findViewById(R.id.etDesc);//seleciono a caixa de texto
                String description = etDesc.getText().toString();//pego o texto dentro da caixa
                if(description.isEmpty()){ //verifico se a descricao foi preenchida
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;//caso esteja vazia, dou um aviso
                }
                Intent i = new Intent(); //crio uma intent para retornar os dados
                i.setData(photoSelected); //passo a imagem para o intent
                i.putExtra("title", title);//passo o titulo
                i.putExtra("description", description);//passo a descricao
                setResult(Activity.RESULT_OK, i);//mando o resultado caso a tela não de erro
                finish();//finalizo a activity
            }
        });

        ImageButton imgCI = findViewById(R.id.imBCI);//pego o botao de acrescentar imagem
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //crio a acao ao clicar no botao
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*"); //crio um itent que abre a galeria
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST); //executo o intent com o metodo de retornar da tela destino um dado(foto)
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_PICKER_REQUEST){ //verificamos se a chamada de startactivityForresult é a do photo_picker
            if(resultCode == Activity.RESULT_OK){ //verificamos se a tela retornou corretamente

                Uri photoSelected = data.getData();
                ImageView imvfotoPreview = findViewById( R.id.imvPhotopreview);
                imvfotoPreview.setImageURI(photoSelected);

                NewItemActivityViewModel vm = new ViewModelProvider( this).get( NewItemActivityViewModel.class );
                vm.setSelectPhotoLocation(photoSelected);
            }
        }
    }
}
