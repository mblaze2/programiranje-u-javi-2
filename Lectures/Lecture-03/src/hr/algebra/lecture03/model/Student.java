/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.lecture03.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author lcabraja
 */
public class Student extends Person implements Serializable {

    private static final long serialVersionUID = 2L;

    private final transient int yearOfStudy;
    private final Index index;

    public Student(String firstName, String lastName, int yearOfStudy, Index index) {
        super(firstName, lastName);
        this.yearOfStudy = yearOfStudy;
        this.index = index;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeUTF(getFirstName());
        oos.writeUTF(getLastName());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        Objects.requireNonNull(index.getIssuer());
        setFirstName(ois.readUTF());
        setLastName(ois.readUTF());
    }

    @Override
    public String toString() {
        return "Student{" + "yearOfStudy=" + yearOfStudy + ", index=" + index + '}' + super.toString();
    }

}
