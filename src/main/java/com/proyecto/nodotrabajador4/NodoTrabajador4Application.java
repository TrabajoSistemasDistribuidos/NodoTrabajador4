package com.proyecto.nodotrabajador4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class NodoTrabajador4Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NodoTrabajador4Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (ZContext context = new ZContext()) {
            // Creamos un socket PULL para recibir números del nodo central
            ZMQ.Socket receiver = context.createSocket(ZMQ.PULL);
            // Nos conectamos al puerto del nodo central que envía los números
            receiver.connect("tcp://localhost:5557");

            // Creamos un socket PUSH para enviar resultados al nodo central
            ZMQ.Socket sender = context.createSocket(ZMQ.PUSH);
            // Nos conectamos al puerto del nodo central que recibe resultados
            sender.connect("tcp://localhost:5558");

            while (true) {
                // Recibimos un número del nodo central
                String mensaje = receiver.recvStr();
                int numero = Integer.parseInt(mensaje);

                // Comprobamos si el número es primo
                boolean esPrimo = esNumeroPrimo(numero);

                // Enviamos el resultado al nodo central
                sender.send(String.valueOf(esPrimo));
            }
        }
    }

    // Método para verificar si un número es primo
    private boolean esNumeroPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }
}
