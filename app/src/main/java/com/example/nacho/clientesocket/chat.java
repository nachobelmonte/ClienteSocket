package com.example.nacho.clientesocket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class chat extends AppCompatActivity {

    //   Bundle bundle = getIntent().getExtras();

    ImageView enviar;
    TextView titulo, conversacion;
    EditText msg;
    private String mensaje;
    private String ip, nombre;
    private int puerto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        enviar = (ImageView) findViewById(R.id.enviar);

        titulo = (TextView) findViewById(R.id.titulo);
        conversacion = (TextView) findViewById(R.id.conversacion);
        msg = (EditText) findViewById(R.id.msg);
        titulo.setText(MainActivity.ip.getText().toString());

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = MainActivity.name.getText().toString();

                if (!nombre.isEmpty()) {
                    ip = MainActivity.ip.getText().toString();

                    mensaje = msg.getText().toString();
                    puerto = Integer.valueOf(MainActivity.puerto.getText().toString());
                    msg.setText("");

                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {

                                Socket miSocket = new Socket(ip, puerto);

                                DataOutputStream salida = new DataOutputStream(miSocket.getOutputStream());


                                salida.writeUTF(MainActivity.name.getText().toString() + " :\t\t" + mensaje);
                                conversacion.append("\n\t" + MainActivity.name.getText().toString() + " :\t\t" + mensaje);

                                salida.flush();
                                salida.close();
                                miSocket.close();

                            } catch (UnknownHostException e) {
                                conversacion.append("\n\n SERVIDOR DESCONECTADO");
                            } catch (IOException e) {
                                conversacion.append("\n\n SERVIDOR DESCONECTADO");
                            }

                        }
                    }).start();
                }
            }
        });
    }
}
