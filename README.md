# Nodo Trabajador
El proyecto del Nodo Trabajador es responsable de recibir números del nodo central, procesarlos para determinar si son primos, y enviar los resultados de vuelta al nodo central. 
Utiliza ZeroMQ para la comunicación con el nodo central. El flujo de trabajo es el siguiente:
 - Se conecta al nodo central utilizando un socket PULL para recibir números.
 - Procesa cada número recibido para determinar si es primo.
 - Envía el resultado (verdadero o falso) de vuelta al nodo central utilizando un socket PUSH.
