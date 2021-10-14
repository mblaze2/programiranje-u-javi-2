/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.lecture03;

import hr.algebra.lecture03.model.Index;
import hr.algebra.lecture03.model.Student;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lcabraja
 */
public class SerializationUsage {

    private static final String FILENAME = "student.ser";

    public static void main(String[] args) {
        Student student = new Student("Milica", "Lulek", 3, new Index(123, "Algebrinjo"));
        System.out.println(student);
        try {
            //write(student, FILENAME);
            student = (Student) read(FILENAME);
            System.out.println(student);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SerializationUsage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void write(Student student, String FILENAME) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(student);
        }
    }
    
    private static Object read(String fileName) throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return ois.readObject();
        }
    }
}
