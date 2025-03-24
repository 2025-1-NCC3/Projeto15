package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextConfirmEmail, editTextPassword, editTextConfirmPassword;
    private Button btnRegister;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicializar os componentes de UI
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextConfirmEmail = findViewById(R.id.editTextConfirmEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Inicializar o DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Definir o evento de clique no botão de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String confirmEmail = editTextConfirmEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                // Validação simples
                if (email.isEmpty() || confirmEmail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else if (!email.equals(confirmEmail)) {
                    Toast.makeText(CadastroActivity.this, "Os e-mails não coincidem!", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(CadastroActivity.this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
                } else if (!ValidationUtils.isValidEmail(email)) {
                    Toast.makeText(CadastroActivity.this, "Formato de email inválido!", Toast.LENGTH_SHORT).show();
                } else {
                    // Salvar os dados no banco de dados
                    long result = dbHelper.addUser(email, password);
                    if (result != -1) {
                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                        finish(); // Fecha a tela de cadastro e volta para a tela de login
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}