package com.company;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        try {
            Semaphore semaphore = new Semaphore(4);
            CountDownLatch countDownLatch = new CountDownLatch(100);
            for (int i = 1; i <= 100; i++) {
                new PassengerThread(semaphore, countDownLatch, i).start();
            }

            countDownLatch.await();

            Thread.sleep(1000);
            System.out.println("Все пассажиры заняли места!");
            Thread.sleep(1000);
            System.out.println("Поехали в Ош!");
        } catch (Exception e) {
        }
    }
    public static class PassengerThread extends Thread {
        Semaphore sem;
        CountDownLatch latch;
        int id;

        public PassengerThread(Semaphore sem, CountDownLatch latch, int id) {
            this.sem = sem;
            this.latch = latch;
            this.id = id;
        }

        public synchronized void run() {
            try {
                int cashier = new Random().nextInt(4)+1;
                sem.acquire();
                System.out.println("Пассажир " + id + " купил билет в кассе №"+cashier);
                sleep(1000);

                sem.release();

                sleep(1000);
                latch.countDown();
            } catch (InterruptedException e) {
            }
        }

    }
}
