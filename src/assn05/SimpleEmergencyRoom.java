package assn05;

import java.util.ArrayList;
import java.util.List;

public class SimpleEmergencyRoom {
    private List<Patient> patients;

    public SimpleEmergencyRoom() {
        patients = new ArrayList    <>();
    }

    // TODO (Task 1): dequeue
    public Patient dequeue() {
        if (patients.isEmpty()) {  //If the list is empty, there is nothing to dequeue
            return null;
        }
        int index = 0;   //keeps track of current patients index
        for (int i = 0; i < patients.size(); i++) {   //loops over entire array
            if ((int) patients.get(i).getPriority() < (int) patients.get(index).getPriority()) { //If current patient's priority == the priority we are looking for
                index = i;  //index == current index we are looking at
            }
        }
        Patient patientBeingRemoved = new Patient(patients.get(index).getValue(),patients.get(index).getPriority());  //new patient object with value and priority of the patient we are dequeing
        patients.remove(index);  //remove the patient
        return patientBeingRemoved;  //return the patient we just removed
    }


    public <V, P> void addPatient(V value, P priority) {
        Patient patient = new Patient(value, (Integer) priority);
        patients.add(patient);
    }

    public <V> void addPatient(V value) {
        Patient patient = new Patient(value);
        patients.add(patient);
    }

    public List getPatients() {
        return patients;
    }

    public int size() {
        return patients.size();
    }




}
