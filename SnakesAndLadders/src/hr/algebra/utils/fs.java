/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marijo
 */
public class fs {
    public static void main(String[] args) throws IOException {
        
        StringBuilder htmlBuilder = new StringBuilder();
        
        htmlBuilder.append("<!DOCTYPE html>\n");
        htmlBuilder.append("<html>\n");
        htmlBuilder.append("<head>\n");
        htmlBuilder.append("<title>Project documentation</title>\n");
        htmlBuilder.append("</head>\n");
        htmlBuilder.append("<body>\n");
        
        Files.walk(Paths.get(".\\src"))
                .filter(f -> !f.getFileName().toString().endsWith("fxml"))
                .forEach((it) -> {
                    File fileItem = it.toFile();
                    if(!fileItem.isDirectory() && !it.toString().contains("resources")){
                        try {
                            String filename = it.toString().replace("\\", ".");
                            filename = filename.substring(1, filename.length() - 1);
                            filename = filename.substring(0, filename.lastIndexOf("."));
                            filename = filename.substring(5, filename.length());
                            System.out.println(filename);
                            Class c = Class.forName(filename);
                            htmlBuilder.append("<h1>File: ");
                            htmlBuilder.append(it.getFileName());
                            htmlBuilder.append("</h1>");
                            htmlBuilder.append(System.lineSeparator());
                            Reflection.readClassAndMembersInfo( c, htmlBuilder);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(fs.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
        htmlBuilder.append("</body>\n");
        htmlBuilder.append("</html>\n");
          
        System.out.println(htmlBuilder);
        
        Writer.write(htmlBuilder.toString());

    }
    
}
