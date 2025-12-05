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
}

class PriorityQueue {
    private int[] heap;
    private int size;
    private int capacity;
    private Dictionary<Integer, Patient> ids;

    public PriorityQueue(int capacity, Dictionary<Integer, Patient> dict) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
        this.ids = dict;
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

    // Insert a new value into the heap
    public void insert(int id) {
        if (size == capacity) {
            System.out.println("Heap is full!");
            return;
        }

        heap[size] = ids.get(id).getSeverity();
        int current = size;
        size++;

        // Heapify up
        while (ids.get(current).getSeverity() != 0 && ids.get(
                heap[current]).getSeverity() < ids.get(heap[parent(current)]).getSeverity()) {
            swap(current, parent(current));
            current = parent(current);
        }

        System.out.println("Inserted " + id);
    }

    // Get the minimum value (root)
    public int peek() {
        if (size == 0)
            throw new IllegalStateException("Heap is empty");
        return heap[0];
    }

    // Remove and return the minimum value
    public int pop() {
        if (size == 0)
            throw new IllegalStateException("Heap is empty");

        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;

        heapifyDown(0);

        return min;
    }

    // Restore heap property downward
    private void heapifyDown(int i) {
        int smallest = i;
        int left = leftChild(i);
        int right = rightChild(i);

        if (left < size && ids.get(heap[left]).getSeverity() < ids.get(heap[smallest]).getSeverity())
            smallest = left;
        if (right < size && ids.get(heap[right]).getSeverity() < ids.get(heap[smallest]).getSeverity())
            smallest = right;

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

    TriageNurse(Queue<Integer> q, Hashtable<Integer, Patient>patientsById, Scanner in) {
        incoming = q;
        this.patientsById = patientsById;
        priority = new PriorityQueue(incoming.size(), patientsById);
        input = in;
    }

    void assessPatient(int id, int severity) {
        // sets patient severity and adds to priority queue
        Patient p = patientsById.get(id);
        if (p==null)
            System.out.println( "No Patient with ID" + id);
        return;
    }
    p.setSeverity(severity);
    priority.add(p);
    system.out.println("Triage:Patient " + id + " (")

    PriorityQueue getPriorityQueue() {
        return priority;
    }
}

class Rooms {
    ArrayList<LinkedList<Patient>> doctors;
    Hashtable<Integer, Integer> billing;
    int capacityPerDoctor = 5;

    Rooms(PriorityQueue pq, Hashtable<Integer, Integer> b) {
        doctors = new ArrayList<LinkedList<Patient>>(10);
        ; // each index represents a doctor
        billing = b;
    }

    void assignPatients() {
        // increment through rooms, assign patients from priority,
        // treat upon incrementation, remove if treated
    }

    void addToOutputFile() {
        // use this method when removing a patient from rooms
    }
}
