/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Marijo
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField tfROLL_DELAY;
    @FXML
    private MenuItem miDocumentation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleGenerateDocumentation(ActionEvent event) {
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<!DOCTYPE html>\n");
        htmlBuilder.append("<html>\n");
        htmlBuilder.append("<head>\n");
        htmlBuilder.append("<title>Popis paketa</title>\n");
        htmlBuilder.append("</head>\n");
        htmlBuilder.append("<body>\n");
        htmlBuilder.append("<h1>Paket: </h1>\n");

        String sourceLocation = ".\\src";

        String[] srcContent = new File(sourceLocation).list();

        for (String fileOrFolder : srcContent) {
            //System.out.println(fileOrFolder);

            File fileItem = new File(sourceLocation + "\\" + fileOrFolder);
            String packageName = "";

            if (fileItem.isDirectory()) {
                packageName = packageName + fileOrFolder;
                htmlBuilder.append("<h2>Paket");
                htmlBuilder.append(fileOrFolder);
                htmlBuilder.append("</h2>\n");
                String[] dirContent = fileItem.list();
                for (String file : dirContent) {

                    File nestedPackage = new File(fileItem.getPath() + "\\"
                            + file);

                    if (nestedPackage.isDirectory()) {
                        String childPackageName = packageName + "." + file;
                        htmlBuilder.append("<h2>Paket:");
                        htmlBuilder.append(childPackageName);
                        htmlBuilder.append("</h2>\n");

                        String[] nestedPackageContent = nestedPackage.list();

                        for (String nestedClass : nestedPackageContent) {
                            documentClass(nestedClass, htmlBuilder,
                                    childPackageName);
                        }

                    } else {
                        documentClass(file, htmlBuilder, packageName);
                    }
                }
            }
        }

        htmlBuilder.append("<p></p>\n");
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>\n");

        try (FileWriter htmlWriter = new FileWriter("dokumentacija.html")) {
            htmlWriter.write(htmlBuilder.toString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno spremanje dokumentacije");
            alert.setContentText("Datoteka \"dokumentacija.html\""
                    + " je uspješno generirana!");
            alert.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(SettingsController.class.getName()).log(
                    Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neuspješno spremanje dokumentacije");
            alert.setContentText("Datoteka \"dokumentacija.html\""
                    + " nije generirana!");
            alert.showAndWait();
        }
    }
    private void documentClass(String file, StringBuilder htmlBuilder, String packageName) {
        if (file.endsWith(".java")) {
            htmlBuilder.append("<h2>Klasa:");
            htmlBuilder.append(file);
            htmlBuilder.append("</h2>\n");

            try {
                Class c = Class.forName(
                        packageName + "."
                        + file.substring(0, file.indexOf(".")));

                Field[] fields = c.getDeclaredFields();

                htmlBuilder.append("Varijable:<br />");

                for (Field field : fields) {
                    int modifiers = field.getModifiers();

                    extractModifiers(modifiers, htmlBuilder);

                    htmlBuilder.append(field.getType().getName());
                    htmlBuilder.append(" ");
                    htmlBuilder.append(field.getName());
                    htmlBuilder.append("<br />");
                }

                Constructor[] constructors = c.getConstructors();

                htmlBuilder.append("Konstruktori:<br />");

                for (Constructor constructor : constructors) {
                    int modifiers = constructor.getModifiers();

                    extractModifiers(modifiers, htmlBuilder);

                    htmlBuilder.append(constructor.getName());
                    htmlBuilder.append("(");

                    Parameter[] params = constructor.getParameters();

                    for (Parameter param : params) {
                        int paramModifiers = param.getModifiers();

                        extractModifiers(paramModifiers, htmlBuilder);

                        htmlBuilder.append(param.getType().getName());
                        htmlBuilder.append(" ");
                        htmlBuilder.append(param.getName());
                        if (params[params.length - 1].equals(param) == false) {
                            htmlBuilder.append(", ");
                        }
                    }

                    htmlBuilder.append(")");

                    htmlBuilder.append("<br />");

                    Method[] methods = c.getMethods();

                    htmlBuilder.append("Metode:<br />");

                    for (Method method : methods) {
                        int methodModifiers = method.getModifiers();

                        extractModifiers(methodModifiers, htmlBuilder);

                        htmlBuilder.append(method.getReturnType().getName());
                        htmlBuilder.append(" ");
                        
                        htmlBuilder.append(method.getName());
                        htmlBuilder.append(" ");

                        Parameter[] methodParams = method.getParameters();
                        
                        htmlBuilder.append("(");

                        for (Parameter param : methodParams) {
                            int paramModifiers = param.getModifiers();

                            extractModifiers(paramModifiers, htmlBuilder);

                            htmlBuilder.append(param.getType().getName());
                            htmlBuilder.append(" ");
                            htmlBuilder.append(param.getName());
                            if (methodParams[methodParams.length - 1]
                                    .equals(param) == false) {
                                htmlBuilder.append(", ");
                            }
                        }

                        htmlBuilder.append(")");
                        htmlBuilder.append("<br />");
                    }
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void extractModifiers(int modifiers, StringBuilder htmlBuilder) {
        if (Modifier.isPublic(modifiers)) {
            htmlBuilder.append("public ");
        }
        if (Modifier.isPrivate(modifiers)) {
            htmlBuilder.append("private ");
        }
        if (Modifier.isProtected(modifiers)) {
            htmlBuilder.append("protected ");
        }
        if (Modifier.isStatic(modifiers)) {
            htmlBuilder.append("static ");
        }
        if (Modifier.isFinal(modifiers)) {
            htmlBuilder.append("final ");
        }
        if (Modifier.isSynchronized(modifiers)) {
            htmlBuilder.append("synchronized ");
        }
    }

    
}
