package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFile {
	static void readLineVarFile(String fileName, int lineNumber) throws IOException { 
        BufferedReader reader = new BufferedReader(new InputStreamReader( 
                        new FileInputStream("STAGIAIRES.DON"))); 
        String line = reader.readLine(); 
        if (lineNumber < 0 ) { 
                System.out.println("d'ont in the file"); 
        } 
        int num = 0; 
        while (line != null) { 
                if (lineNumber == ++num) { 
                        System.out.println("line     " + lineNumber + ":     " + line); 
                } 
                line = reader.readLine(); 
        } 
        reader.close(); 
} 

}