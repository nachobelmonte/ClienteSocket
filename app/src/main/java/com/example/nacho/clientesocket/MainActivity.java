package com.example.nacho.clientesocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    public static EditText ip, puerto, name;
    int port = 0;
    private Socket cliente;
    private Button iniciar, iniciarChat;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ip = (EditText) findViewById(R.id.ip);
        name = (EditText) findViewById(R.id.name);
        puerto = (EditText) findViewById(R.id.puerto);
        iniciar = (Button) findViewById(R.id.iniciar);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = name.getText().toString();

                if (!nombre.isEmpty()) {
                    String ipAdd = ip.getText().toString();

                    try {
                        port = Integer.parseInt(puerto.getText().toString());
                    } catch (Exception e) {

                    }

                    new Thread(new Runnable() {

                        @Override
                        public void run() {

                            try {

                                cliente = new Socket(ip.getText().toString(), port);

                                DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
                                salida.writeUTF(nombre + " se ha conectado");
                                Intent intent = new Intent(MainActivity.this, chat.class);
                                startActivity(intent);
                                salida.flush();
                                salida.close();
                                cliente.close();

                            } catch (UnknownHostException e) {


                            } catch (IOException e) {

                            }

                        }
                    }).start();
                }

            }
        });
    }
}

