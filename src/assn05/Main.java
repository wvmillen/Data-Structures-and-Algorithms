package assn05;

public class Main {

    public static void main(String[] args) {
        testP1();
        testP2();
//        testP3();
        testP4();

        double[] runtimes = compareRuntimes();
        System.out.println("Total time for SimpleEmergencyRoom dequeue: " + runtimes[0] );
        System.out.println("Average time for SimpleEmergencyRoom dequeue: " + runtimes[1] );
        System.out.println("Total time for MaxBinHeapER dequeue: " + runtimes[2] );
        System.out.println("Average time for MaxBinHeapER dequeue: " + runtimes[3] );
    }

    // test Part 1
    public static void testP1(){
        /*
        Part 1 - Write some tests to convince yourself that your code for Part 1 is working
         */
    }

    // test Part 2
    public static void testP2(){
       /*
        Part 2 - Write some tests to convince yourself that your code for Part 2 is working
         */
    }

    /*
    Part 3
     */
    public static void testP3(){
        MaxBinHeapER transfer = new MaxBinHeapER(makePatients());
        Prioritized[] arr = transfer.getAsArray();
        for(int i = 0; i < transfer.size(); i++) {
            System.out.println("Value: " + arr[i].getValue()
                    + ", Priority: " + arr[i].getPriority());
        }
    }

    /*
    Part 4
     */
    public static void testP4() {
               /*
        Part 4 - Write some tests to convince yourself that your code for Part 4 is working
         */

    }

    public static void fillER(MaxBinHeapER complexER) {
        for(int i = 0; i < 100000; i++) {
            complexER.enqueue(i);
        }
    }
    public static void fillER(SimpleEmergencyRoom simpleER) {
        for(int i = 0; i < 100000; i++) {
            simpleER.addPatient(i);
        }
    }

    public static Patient[] makePatients() {
        Patient[] patients = new Patient[10];
        for(int i = 0; i < 10; i++) {
            patients[i] = new Patient(i);
        }
        return patients;
    }

    public static double[] compareRuntimes() {
        // Array which you will populate as part of Part 4
        double[] results = new double[4];

        SimpleEmergencyRoom simpleER = new SimpleEmergencyRoom();
        fillER(simpleER);
        long startSimpleDequeue = System.nanoTime();
        while (simpleER.size() > 0) {
            simpleER.dequeue();
        }
        long endSimpleDequeue = System.nanoTime();
        results[0] = (endSimpleDequeue - startSimpleDequeue);
        results[1] = results[0] / 100000.0; // Average time per dequeue


        MaxBinHeapER complexER = new MaxBinHeapER();
        fillER(complexER);
        long startComplexDequeue = System.nanoTime();
        while (complexER.size() > 0) {
            complexER.dequeue();
        }
        long endComplexDequeue = System.nanoTime();
        results[2] = (endComplexDequeue - startComplexDequeue);
        results[3] = results[2] / 100000.0; // Average time per dequeue


        return results;
    }

}
