package org.launchcode.models.data;

import org.launchcode.models.Job;
import org.launchcode.models.JobField;

import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
public class JobFieldData<T extends JobField> {

    private ArrayList<T> allFields = new ArrayList<>(); //an AL of JobField objects. Each has value(String) and ID (int)

    public ArrayList<T> findAll() {
        return allFields;
    }

    public T findById(int id) { //fields like employers, locations, etc (field(id, value))
        for (T item : allFields) { //that field in fields list
            if (item.getId() == id) //get by that id assoc. with it
                return item;
        }

        return null;
    }

    public void add(T item) {
        allFields.add(item);
    }

    T findByValue(String value) {
        for (T item : allFields) {
            if (item.contains(value))
                return item;
        }

        return null;
    }

}
