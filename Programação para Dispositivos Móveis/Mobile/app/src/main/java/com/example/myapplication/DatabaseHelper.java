package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Constantes para configuração do banco de dados
    private static final String DATABASE_NAME = "UserDB";  // Nome do banco de dados
    private static final int DATABASE_VERSION = 1;         // Versão do banco de dados
    private static final String TABLE_USERS = "users";     // Nome da tabela de usuários

    // Colunas da tabela
    private static final String COLUMN_ID = "id";           // Coluna ID (chave primária)
    private static final String COLUMN_EMAIL = "email";     // Coluna para armazenar email
    private static final String COLUMN_PASSWORD = "password"; // Coluna para senha criptografada

    // Comando SQL para criar a tabela de usuários
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"  // ID autoincrementável
            + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE,"           // Email único e obrigatório
            + COLUMN_PASSWORD + " TEXT NOT NULL"                 // Senha obrigatória
            + ")";

    // Construtor da classe DatabaseHelper
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método chamado ao criar o banco de dados pela primeira vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS); // Executa o SQL de criação da tabela
    }

    // Método chamado ao atualizar o banco de dados
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS); // Remove a tabela antiga se existir
        onCreate(db); // Recria o banco de dados
    }

    // Método para adicionar um novo usuário ao banco de dados
    public long addUser(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email); // Insere o email
        values.put(COLUMN_PASSWORD, PasswordUtils.hashPassword(password)); // Insere a senha criptografada
        long result = db.insert(TABLE_USERS, null, values); // Executa a inserção
        db.close(); // Fecha a conexão com o banco
        return result; // Retorna o ID do novo registro ou -1 em caso de erro
    }

    // Método para verificar se um usuário existe com as credenciais fornecidas
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID}; // Colunas a serem retornadas
        // Cláusula WHERE para verificar email e senha correspondentes
        String selection = COLUMN_EMAIL + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        // Valores para substituir os placeholders '?'
        String[] selectionArgs = {email, PasswordUtils.hashPassword(password)}; // Compara com senha criptografada
        // Executa a consulta
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount(); // Obtém o número de registros encontrados
        cursor.close(); // Fecha o cursor
        db.close(); // Fecha a conexão com o banco
        return count > 0; // Retorna true se encontrou pelo menos um registro
    }
}