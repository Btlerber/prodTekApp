package app;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Progress {
	double answerdLines = 0;
	double validLines = 0;
	double factor;
	
	public Progress() {
		// TODO Auto-generated constructor stub
		
	}
	
	public double checkProgress(String spmPath){    ///sjekk at det ikke er kategorilinje, at linjen ikke er tom gi deretter et antall gyldige linjer.
		
		try {
			answerdLines = 0;
			validLines = 0;
			Scanner scanner;
			Path path = Paths.get(spmPath);
			scanner = new Scanner(path);
			while(scanner.hasNextLine()){
				String scnLine = scanner.nextLine();
				try {
					String[] parts = scnLine.split(";;;;",-1);
					String spm = parts[2];
					if(scnLine.contains(";;;;eldre_brukersvar:")){
						answerdLines += 1.0;
					}//sjekker at det er en linje med symboler i, sjekker at det er et spørsmål der, sjekker at lengden på svaret er lenger enn svar.
					if(scnLine.length()>20 && !(scnLine.split(";;;;",-1)[2].isEmpty() || scnLine.split(";;;;",-1)[3].length() < 16 )){
						validLines += 1.0;
					}
				}catch (Exception e) {
					if(scnLine.length() > 20) {
						System.out.println("checkProgress splittet ikke linjen: "+e);
					}
			}
			}
			scanner.close();
			factor = answerdLines/validLines;
			//System.out.println("ans/val: "+answerdLines+" "+validLines+" "+factor);
			System.out.println(factor);
			return factor;
			
		}catch (IOException e) {
			System.out.println("checkProgress kjører feil: "+e);
			}
		
		return -1;
	}
	
	
	
	
	public ArrayList<Integer> checkAnsweredLines(String spmPath){
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			//Scanner scanner;
			//Path path = Paths.get(spmPath);
			//scanner = new Scanner(path);
			@SuppressWarnings("resource")
			LineNumberReader r = new LineNumberReader(new FileReader(spmPath));
			String line = null;
			while((line = r.readLine()) != null){
				
				//System.out.println(line);
				if(!line.contains(";;;;eldre_brukersvar:") && line.length()>20 && !(line.split(";;;;",-1)[2].isEmpty() || line.split(";;;;",-1)[3].length() < 16 )){ 
				
					int linjenummer = r.getLineNumber();
					list.add(linjenummer); //legger til de linjene som ikke er besvart.
					System.out.println(linjenummer);
				}
				
			}
			r.close();
		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("checkanswreLines: "+e);
		}	
		System.out.println(list.toString());
		return list;
	}
	
	
	public static void main(String[] args) {
		Progress p = new Progress();
		System.out.println(p.checkAnsweredLines("../ovinger/src/appResources/saveFile.txt"));
	}
	
	
	
	
	
	
	
}


