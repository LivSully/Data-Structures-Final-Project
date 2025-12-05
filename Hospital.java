import java.util.Queue;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Dictionary;

public class Hospital {
    public static void main(String[] args) {

        // reads input file
        try{
            File file = new File("All ER Patients"); // adjust extension if needed
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue; // skip blank lines
                }
            String[] parts = line.split(",",7);
            if (parts.length <7){
                System.out.println("line skip");
                continue;
            }
        }
        // turns each line into a Patient object
        // stores Patient objects in a queue, dictionary, and hash table
        // dictionary: key = id, value = Patient object
        // hash table: key = ssn, value = billing info(starts at 0)
        // triage nurse object and used to create priority queue
        // rooms object created with priority queue
        // use rooms meth
        
        }
    }
}

class Patient {
    String name;
    int age;
    String weight;
    String height;
    int ssn;
    String symptoms;
    int seenCount; // number of times patient has been seen
    int id; // unique identifier for each patient
    int severity; // user determined

    Patient(String n, int a, String w, String h, int s, String sym, int idNum) {
        name = n;
        age = a;
        weight = w;
        height = h;
        ssn = s;
        symptoms = sym;
        seenCount = 0;
        id = idNum;
        severity = 0; // will be set by User
    }

    String getSymptoms() {
        return symptoms;
    }

    void setSeverity(int sev) {
        severity = sev;
    }

    int getSeverity() {
        return severity;
    }

    int getId() {
        return id;
    }

    int getSSN() {
        return ssn;
    }

    void incSeenCount() {
        seenCount++;
    }

    int getSeenCount() {
        return seenCount;
    }
    void decreaseServerity(){
        if (severity >0){
            severity --;
        }
    }
}

class PriorityQueue {
    private int[] heap;
    private int size;
    private int capacity;
    private Dictionary<Integer, Patient> patientsDict;

     public PriorityQueue(int capacity, Dictionary<Integer, Patient> dict) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
        this.patientsDict = dict;
    }


    // Get index helpers
    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }
    private int getSeverityOfIndex(int index) {
        int patientId = heap[index];
        Patient p = patientsDict.get(patientId);
        if (p == null) return Integer.MAX_VALUE; // treat missing as very low priority
        return p.getSeverity();
    }

    private int getSeverityOfId(int patientId) {
        Patient p = patientsDict.get(patientId);
        if (p == null) return Integer.MAX_VALUE;
        return p.getSeverity();
    }
    public boolean isEmpty(){
        return size ==0;
    }
    public int size(){
        return size;
    }
    // Insert a new value into the heap
    public void insert(int id) {
        if (size == capacity) {
            System.out.println("PriorityQueue is full!");
            return;
        }

        heap[size] = id;
        int current=size;
        size++;

        // Heapify up
         while (current > 0 &&
            getSeverityOfIndex(current) < getSeverityOfIndex(parent(current))) {
            swap(current, parent(current));
            current = parent(current);
        }
        System.out.println("Inserted " + id);
    }
    // Get the minimum value (root)
    public int peek() {
        if (size == 0)
            throw new IllegalStateException("PriorityQueue is empty");
        {
        return heap[0];
        }

    // Remove and return the minimum value
    public int pop() {
        if (size == 0)
            throw new IllegalStateException("PriorityQueue is empty");
        }

        int minId = heap[0];
        heap[0] = heap[size - 1];
        size--;

        heapifyDown(0);
        return minId;
    }
    // Restore heap property downward
    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && getSeverityOfIndex(left) < getSeverityOfIndex(smallest)) {
            smallest = left;
        }
        if (right < size && getSeverityOfIndex(right) < getSeverityOfIndex(smallest)) {
            smallest = right;
        }

        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }


    // Swap elements at two positions
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}

class TriageNurse {
    Queue<Integer> incoming; // contains patient ids
    PriorityQueue priority; // contains patient ids
    Dictionary<Integer, Patient> patientsById;
    Scanner input;

    TriageNurse(Queue<Integer> q,
                Dictionary<Integer, Patient> patientsDict,
                Scanner in,
                int maxPatients) {
        incoming = q;
        this.patientsDict = patientsDict;
        priority = new PriorityQueue(maxPatients, patientsDict);
        input = in;
    }

    // Ask user to assign severity for this patient and insert into PQ
    void assessPatientInteractive(int id) {
        Patient p = patientsDict.get(id);
        if (p == null) {
            System.out.println("Triage error: No patient with ID " + id);
            return;
        }

    System.out.println("\nTriage next patient:");
        System.out.println("ID: " + p.getId() +
                    ", Name: " + p.name +
                   ", Age: " + p.age +
                   ", Symptoms: " + p.getSymptoms());
    
        int severity = -1;
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter severity (1 = see now, 2 = medium, 3 = low): ");
            String line = input.nextLine().trim();
            try {
                severity = Integer.parseInt(line);
                if (severity >= 1 && severity <= 3) {
                    valid = true;
                } else {
                    System.out.println("Please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please enter 1, 2, or 3.");
            }
        }
        // update patient + insert id into priority queue
        p.setSeverity(severity);
        priority.insert(id);
        System.out.println("Patient " + id + " triaged with severity " + severity);
    }
    void triageAllPatientsInteractive() {
        while (!incoming.isEmpty()) {
            int nextId = incoming.poll();
            assessPatientInteractive(nextId);
        }
    }



class Rooms {
    ArrayList<LinkedList<Patient>> doctors;   // each index = one doctor
    Hashtable<Integer, Integer> billing;      // HASH TABLE: ssn -> total bill
    Dictionary<Integer, Patient> patientsDict; // DICTIONARY: id -> Patient
    PriorityQueue triage;                     // PRIORITY QUEUE: patient IDs

    int capacityPerDoctor = 10;
    static final int NUM_DOCTORS = 10;
    static final int VISIT_COST = 500;        // $500 per visit

    Rooms(PriorityQueue pq,
          Hashtable<Integer, Integer> billingTable,
          Dictionary<Integer, Patient> patientsDict) {
        triage = pq;
        billing = billingTable;
        this.patientsDict = patientsDict;

        doctors = new ArrayList<LinkedList<Patient>>(NUM_DOCTORS);
        for (int i = 0; i < NUM_DOCTORS; i++) {
            doctors.add(new LinkedList<Patient>());
        }
    }

    // One "treatment round":
    // 1) Each doctor visits each of their patients once:
    //      - severity--
    //      - seenCount++
    //      - billing[ssn] += 500
    //      - if severity == 0 → discharge
    // 2) Then fill open slots (up to 10 per doctor) from triage PQ.
    void assignPatients() {
        // Step 1: treat current patients
        for (int docIndex = 0; docIndex < doctors.size(); docIndex++) {
            LinkedList<Patient> docList = doctors.get(docIndex);
            if (docList.isEmpty()) continue;

            System.out.println("Doctor " + docIndex + " is treating " + docList.size() + " patients.");

            java.util.Iterator<Patient> it = docList.iterator();
            while (it.hasNext()) {
                Patient p = it.next();

                // One visit
                p.decreaseSeverity();
                p.incSeenCount();

                // HASH TABLE: ssn -> bill
                int ssn = p.getSSN();
                Integer current = billing.get(ssn);
                if (current == null) current = 0;
                billing.put(ssn, current + VISIT_COST);

                System.out.println("  Doctor " + docIndex +
                        " visited patient " + p.getId() +
                        " (new severity " + p.getSeverity() +
                        ", total visits " + p.getSeenCount() +
                        ", current bill $" + billing.get(ssn) + ")");

                // discharge if severity reaches 0
                if (p.getSeverity() <= 0) {
                    System.out.println("  -> Patient " + p.getId() + " is discharged.");
                    it.remove();
                    // Allie can later call addToOutputFile(p) here.
                }
            }
        }

        // Step 2: fill open slots from triage PQ
        for (int docIndex = 0; docIndex < doctors.size(); docIndex++) {
            LinkedList<Patient> docList = doctors.get(docIndex);

            while (docList.size() < capacityPerDoctor && !triage.isEmpty()) {
                int nextId = triage.pop();          // get next patient ID by priority
                Patient p = patientsDict.get(nextId);
                if (p == null) continue;
                docList.add(p);
                System.out.println("Doctor " + docIndex +
                        " admits patient " + p.getId() +
                        " with severity " + p.getSeverity());
            }
        }
    }
    boolean hasActivePatients() {
        for (LinkedList<Patient> docList : doctors) {
            if (!docList.isEmpty()) {
                return true;
            }
        }
        return false;
    }


    void addToOutputFile(Patient p) {
        // Person 3 (Allie) can implement file writing here
        // using:
        //   p.getId(), p.getSSN(), p.getSeenCount(), billing.get(p.getSSN())
    }

    void printDoctorStatus() {
        System.out.println("\n--- Doctors & Patients ---");
        for (int i = 0; i < doctors.size(); i++) {
            LinkedList<Patient> docList = doctors.get(i);
            System.out.print("Doctor " + i + " has " + docList.size() + " patients: ");
            for (Patient p : docList) {
                System.out.print("#" + p.getId() +
                        "(sev " + p.getSeverity() +
                        ", visits " + p.getSeenCount() + ")  ");
            }
            System.out.println();
        }
    }
}
}

