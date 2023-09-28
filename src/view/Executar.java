package view;

import controller.ThreadPessoa;

import java.util.concurrent.Semaphore;

public class Executar {
    public static void main(String[] args) {
        int limitePessoasPassandoPorta = 1;
        Semaphore semaforo = new Semaphore(limitePessoasPassandoPorta);
        ThreadPessoa[] pessoas = new ThreadPessoa[4];

        for (int i = 0; i < 4; i++) {
            pessoas[i] = new ThreadPessoa(i, semaforo);
            pessoas[i].start();
        }
    }
}
