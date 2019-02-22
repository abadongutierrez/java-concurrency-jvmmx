package com.jabaddon.jvmmx.concurrency.basics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
A little simulator where clients arrive to the Bank line and are attended when the Cashier calls the next person in line
using wait and notify.
 */
public class ClientsWaitingInBankLineApp {
    public static void main(String[] args) throws InterruptedException {
        BankLine bankLine = new BankLine();
        initClients(bankLine);

        Thread cashierThread = new Thread(new Cashier(bankLine));
        cashierThread.start();
        cashierThread.join();

        System.out.println("That's it!");
    }

    private static void initClients(BankLine bankLine) {
        for (int i = 0; i < 5; i++) {
            String name = "Client-" + i;
            Thread thread = new Thread(new Client(name, bankLine), name);
            thread.start();
        }
    }

    static class Client implements Runnable {
        private final BankLine bankLine;
        private boolean attended;
        private String name;

        public Client(String name, BankLine bankLine) {
            this.name = name;
            this.bankLine = bankLine;
        }

        @Override
        public void run() {
            bankLine.add(this);
        }

        public void attended() {
            attended = true;
        }

        public boolean isAttended() {
            return attended;
        }

        public String getName() {
            return name;
        }
    }

    static class BankLine {
        private List<Client> clients = new ArrayList<>();
        private boolean cashierCalled;

        public synchronized void add(Client client) {
            clients.add(client);
            System.out.printf("Client %s is in the line\n", client.getName());
            // why a loop? Javadoc says: interrupts and spurious wakeups are possible, and this method should always be used in a loop.
            while (!cashierCalled) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    System.out.println("Line was interrupted");
                }
            }
            System.out.printf("Client %s was attended\n", client.getName());
            client.attended();
            cashierCalled = false;
        }

        public synchronized void nextClient() {
            cashierCalled = true;
            notify();
        }

        public boolean areAllClientsAttended() {
            return clients.stream().allMatch(Client::isAttended);
        }
    }

    static class Cashier implements Runnable {
        private final BankLine bankLine;

        public Cashier(BankLine bankLine) {
            this.bankLine = bankLine;
        }

        @Override
        public void run() {
            System.out.println("Starting cashier...");
            while (!bankLine.areAllClientsAttended()) {
                System.out.println("Cashier calling next client in line.");
                bankLine.nextClient();

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ex) {
                    System.out.println("Cashier interrupted.");
                }
            }
            System.out.println("Cashier attended all clients!");
        }
    }
}