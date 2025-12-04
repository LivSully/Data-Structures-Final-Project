import java.util.Queue;
import java.util.PriorityQueue;

public class Hospital {
    public static void main(String[] args) {
        // reads input file
        // turns each line into a Patient object
        // stores Patient objects in a queue, dictionary, and hash table
        // dictionary: key = id, value = Patient object
        // hash table: key = ssn, value = billing info(starts at 0)
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
    }

    String getSymptoms() {
        return symptoms;
    }

    void setSeverity(int sev) {
        severity = sev;
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

class TriageNurse {
    Queue<Integer> incoming; // contains patient ids
    PriorityQueue<Patient> priority = new PriorityQueue<Patient>(); // contains patient ids

    TriageNurse(Queue<Integer> q) {
        incoming = q;
    }

    void assessPatient(int id, int severity) {
        // sets patient severity and adds to priority queue
    }

    PriorityQueue<Patient> getPriorityQueue() {
        return priority;
    }
}