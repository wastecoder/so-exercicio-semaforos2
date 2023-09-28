package controller;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPessoa extends Thread {
    private final int TAMANHO_CORREDOR = 200;
    private static AtomicInteger colocacao = new AtomicInteger(1);
    private int id;
    private Semaphore semaforo;

    public ThreadPessoa(int id, Semaphore semaforo) {
        this.id = id;
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        correrAtePorta();

        try {
            //Inicio area critica
            semaforo.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaforo.release();
            //Fim area critica

            passarPelaPorta();
        }
    }

    public int gerarNumeroAleatorio(int minimo, int maximo) {
        return ThreadLocalRandom.current().nextInt(minimo, maximo);
    }

    public void correrAtePorta() {
        int velocidadeIndividual = gerarNumeroAleatorio(4, 7);

        int distanciaPercorrida = 0;
        while (distanciaPercorrida < TAMANHO_CORREDOR) {
            distanciaPercorrida += velocidadeIndividual;
            System.out.println("#" + id + " >>> percorreu " + distanciaPercorrida + "m");
        }
        System.out.println("#" + id + " >>> chegou na porta na posicao " + colocacao.getAndIncrement());
    }

    public void passarPelaPorta() {
        int tempoAbrirEFecharPorta = gerarNumeroAleatorio(1000, 2001);

        try {
            sleep(tempoAbrirEFecharPorta);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("#" + id + " >>>> levou " + tempoAbrirEFecharPorta + "ms para passar pela porta");
    }
}
