package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/*
  Atividade principal exibida após o login do usuário.
  Responsável por mostrar a interface principal do aplicativo.
 */
public class HomeActivity extends AppCompatActivity {

    /*
      Método de ciclo de vida chamado na criação da Activity.
      Configura a interface inicial e componentes.
      @param savedInstanceState Estado anterior da instância (se houver).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Carrega o layout XML da tela inicial

        // Aqui você normalmente inicializaria componentes da interface
        // Exemplo:
        // TextView welcomeText = findViewById(R.id.welcome_text);
        // welcomeText.setText("Bem-vindo!");

        // E configuraria listeners para botões ou outros elementos
    }

    // Outros métodos de ciclo de vida podem ser adicionados conforme necessário
    // (onStart(), onResume(), onPause(), etc.)

    // Exemplo de método para futuras funcionalidades:
    // private void setupRecyclerView() { ... }
}