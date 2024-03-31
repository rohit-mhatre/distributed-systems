/**
 * LeaderElection class implements a leader election algorithm using multithreading.
 * It simulates a network of processes where each process communicates with its neighbors
 * to determine the leader in the network.
 *
 * Author: Rohit Mhatre
 * Date: 03/29/2024
 */
public class LeaderElection {

    /**
     * Inner class representing a process in the network.
     * Each process has an ID, status (active or inactive), and a list of neighbor IDs.
     */
    class Process implements Runnable {
        int id;
        boolean active;
        int[] neighbors;

        /**
         * Constructor to initialize a process with its ID and neighbor IDs.
         * @param id The ID of the process.
         * @param neighbors An array containing IDs of neighboring processes.
         */
        Process(int id, int[] neighbors) {
            this.id = id;
            this.active = true;
            this.neighbors = neighbors;
        }

        /**
         * Run method to simulate the execution of the process.
         * It determines the highest ID among its neighbors and identifies itself as the leader if it has the highest ID.
         */
        @Override
        public void run() {
            try {
                Thread.sleep(1000); // Simulate processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Process " + id + " is active");

            int highestId = id;
            for (int neighbor : neighbors) {
                if (neighbor > highestId) {
                    highestId = neighbor;
                }
            }

            if (highestId == id) {
                System.out.println("Process " + id + " is the leader.");
            }
        }
    }

    int totalProcesses;
    Process[] processes;

    /**
     * Constructor to initialize the LeaderElection object.
     */
    public LeaderElection() { }

    /**
     * Method to initialize the processes in the network.
     * Each process is assigned an ID and a list of neighbor IDs.
     */
    public void initializeProcesses() {
        System.out.println("Number of processes: 5");
        totalProcesses = 5;
        processes = new Process[totalProcesses];
        int[][] neighborIds = {{2}, {3}, {4}, {5}, {1}}; // Neighbor IDs for each process
        for (int i = 0; i < totalProcesses; i++) {
            processes[i] = new Process(i + 1, neighborIds[i]); // IDs start from 1
        }
    }

    /**
     * Method to start the leader election algorithm by creating and starting threads for each process.
     */
    public void startElection() {
        Thread[] threads = new Thread[totalProcesses];

        for (int i = 0; i < totalProcesses; i++) {
            threads[i] = new Thread(processes[i]);
            threads[i].start();
        }

        for (int i = 0; i < totalProcesses; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Main method to create LeaderElection object, initialize processes, and start the leader election algorithm.
     * @param args Command-line arguments (not used).
     */
    public static void main(String arg[]) {
        LeaderElection object = new LeaderElection();
        object.initializeProcesses();
        object.startElection();
    }
}
