package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import app.SaveAnswer;
import app.DragAndDrop;
import appResources.*;
import app.ProdTekAppTools;



public class ProdTekAppController{
	String catalogQuestionString;
	String catalogAnswerString;
	String catalogQuestionNumber;
	String catalogCategory; 
	String catalogUserAnswers; //svar som ligger inne i katalogen fra før
	List<String> catalogKeywords = new ArrayList<String>();
	String catalogPage;
	String catalogLine;
	public int currentLine = -1;
	String currentString; //ligger alltid en linje foran currentLine:
	ArrayList<Integer> answeredLines = new ArrayList<>();
	int numbCorrectUserKeywords;
	int numbCatalogKeywords;
	boolean endOfCatalog = false;
	int catalogLength = 0;

	//statiske varabler alle blir satt før appen launcher


	static String spmPath = "../appResources/savefile.txt"; //"/Users/Bjorn/prodtekProsjekt/prodTekApp/src/appResources/saveFile.txt";
	static boolean pathSet = false; //husk å sette til false ved implementering va dragndrop





	public void initialize() {//kjøres kun når programmet startes
		catalogQuestionString = "Hei, velkommen til produksjonsteknikkAppen. Håper dere finner programmet Nyttig. "
				+ "For å starte trykker dere neste spørsmål. Appen vil lagre svaret deres i tekstfilen 'saveFile.txt' i samme mappe som aplikasjonen er. "
				+ "denne kan dere sende til hverandre og bytte ut med den dere har, da vil de se svarene deres etter spørsmålskatalogen sitt svar."
				+ "Inntil videre versjoner av appen, må path føres inn i appen ved hver oppstart";
		setSpørsmål();

	}


	public static void setspmPath(String path){
		spmPath = path;
		pathSet = true;
	}
	public String getSpmPath() {
		return spmPath;
	}


	//setter skjermen til controllerattributtene.
	public void setScreen() {	
		setCatalogLength(spmPath);
		setSpørsmål();
		setPensumSide();
		setNøkkelord(0, numbCatalogKeywords);
		SetSpmNummer();
		KatalogSvar.setText("");
		setProgMeter();
	}

	public String getCatalogQuestionNumber() {
		return catalogQuestionNumber;
	}


	@FXML
	private ProgressIndicator ProgMeter;

	public void setProgMeter(){
		try {
			Progress p = new Progress();
			this.ProgMeter.setProgress(p.checkProgress(spmPath));


		} catch (Exception e) {
			System.out.println("Prøvde å sette progressometer: "+e);
		}
	}



	public int getCatalogLength(){
		return catalogLength;
	}
	public String getCatalogLine(){
		return catalogLine;
	}

	@FXML
	private TextArea Spørsmål;


	public void setSpørsmål(){
		try {
			Spørsmål.setText(catalogQuestionString);
			Spørsmål.setWrapText(true);
		} catch (Exception e) {
			System.out.println("setSpørsmål har exception: " + e);
		} finally {
		}
	}

	public TextArea getSpørsmål() {
		return Spørsmål;
	}

	@FXML
	private TextField PensumSide;

	public TextField getPensumSide() {
		return PensumSide;
	}

	public void setPensumSide() {
		PensumSide.setText(catalogPage);
	}

	@FXML
	private TextArea BrukerSvar;

	public TextArea getBrukerSvar() {
		return BrukerSvar;
	}

	@FXML
	private TextArea KatalogSvar;

	public TextArea getKatalogSvar() {
		return KatalogSvar;
	}

	public void setKatalogSvar() {
		KatalogSvar.setText(catalogAnswerString);
		KatalogSvar.setWrapText(true);
	}

	@FXML
	private TextField Nøkkelord;

	public TextField getNøkkelord() {
		return Nøkkelord;
	}

	public void setNøkkelord(int teller, int nevner) {
		try {
			String tellerStr = Integer.toString(teller);
			String nevnerStr = Integer.toString(nevner);
			Nøkkelord.setText(tellerStr + "/" + nevnerStr);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		}
	}

	@FXML
	private TextField SpmNummer;

	public TextField getSpmNummer() {
		return SpmNummer;
	}

	public void SetSpmNummer() {
		try {
			SpmNummer.setText(catalogQuestionNumber);
		} catch (Exception e) {
			System.out.println("setSpmNumber exception: " + e);
		} finally {
		}
	}

	@FXML
	private TextField Kategori;

	public String getKategori() {
		return catalogCategory;
	}

	public void setKategori() {
		Kategori.setText(catalogCategory);
	}

	@FXML
	private Button ForrigeKategori;

	@FXML
	private Button ForrigeSpørsmål;

	@FXML
	private Button NesteKategori;

	@FXML
	private Button SjekkNøkkelOrd;

	@FXML
	private Button SeSvar;

	@FXML
	private Button NesteSpørmål;

	@FXML
	private Button TidligereSvar;

	@FXML
	private CheckBox Tilfeldig;


	public void setTilfeldig(boolean bool){
		Tilfeldig.setSelected(bool);
	}
	public boolean getTilfeldig() {
		return Tilfeldig.isSelected();
	}


	@FXML
	private CheckBox Tilbakelegging;

	public void setTilbakeLegging(boolean bool) {
		Tilbakelegging.setSelected(bool);
	}

	public boolean getTilbakelegging() {
		return Tilbakelegging.isSelected();
	}



	// on action metoder
	/*
	public void handleForrigeKategori() {
		lastCategory();
	}
	 */
	public void handleForrigeSpørsmål() {
		this.lastQuestion();
	}

	public void handleNesteKategori() {
		if(this.currentLine == -1) {//gjør at man kan trykke neste kategori med en gang appen er i gang
			handleNesteSpørsmålAction();
		}
		this.nextCategory();
	}

	public void handleSjekkNøkkelordAction() {
		this.checkKeywords();
	}

	public void handleSeSvarAction() {
		this.setKatalogSvar();
	}

	public void handleNesteSpørsmålAction() {
		//  prøver å hente brukersvar og sende det til "SaveUserAnswer-klassen"
		if(!pathSet){
			Stage st = ProdTekApp.stage;
			Scene sc = st.getScene();
			DragAndDrop dad = new DragAndDrop(st,sc);
			dad.getPath();
			if(ProdTekAppTools.isValidPath(spmPath)){
				pathSet = true;
			}
			//try {wait();} catch (InterruptedException e) {e.printStackTrace();}
		}else {
			try { //
				Path path = Paths.get(spmPath);
				new SaveAnswer(currentString,BrukerSvar.getText().toString(),path);

			}catch (Exception e){
				System.out.println("handleNesteSpørsmålAction vil ikke hente/lagre brukersvar: "+e);
			}


			getNextQA();
			setScreen();
			BrukerSvar.setText("");

		}
	}




	/*public void lastCategory() {
		double qNum = Double.parseDouble(catalogQuestionNumber);
		double newQNum = (((qNum-(qNum%10))/10)-9);
		if(newQNum>=10) {
			currentLine = -1;
			while(qNum!=newQNum && !endOfCatalog) {
				qNum = Double.parseDouble(catalogQuestionNumber);
				getNextQA();
				System.out.println("i am working");

			}
			setScreen();
		}
	}
	 */
	public void lastQuestion(){
		double qNum = Double.parseDouble(catalogQuestionNumber);
		if(qNum%10==1) {currentLine-=3;}
		else{currentLine-=2;}
		getNextQA();
		setScreen();
		BrukerSvar.setText("");
	}

	public void nextCategory() {
		double qDouble = Double.parseDouble(catalogQuestionNumber);

		if (qDouble % 10 == 1) { //sjekker at at while løkka funker. hvis vi er på spm. 1 i en kategori, skal den fortsatt hoppe til neste.
			getNextQA();
		}
		int whileBreaker = 0;
		while ((qDouble % 10) != 1 && whileBreaker < 90) {
			whileBreaker ++;
			if (whileBreaker==20) 
			{System.out.println("while løkka i nextCategory nådde maksgrense på spm.");}
			getNextQA();
		}
		setScreen();
		BrukerSvar.setText("");
	}
	public void checkKeywords() {
		for (int i = 0; i < catalogKeywords.size(); i++) {
			String keyWord = catalogKeywords.get(i);
			boolean lastCharLetterTrue = Character.isLetter(keyWord.charAt(keyWord.length()-1));		
			String userInput =BrukerSvar.getText().toLowerCase().replaceAll("\\W","");
			if(!lastCharLetterTrue){
				keyWord = keyWord.substring(0,keyWord.length()-1);
			}
			if (userInput.contains(keyWord.toLowerCase())) {
				this.numbCorrectUserKeywords += 1;
				catalogKeywords.remove(i);

			}
			setNøkkelord(numbCorrectUserKeywords, numbCatalogKeywords);
		}
	}
	public void countCurrentLine() {
		this.currentLine++;
	}



	public String scannerIt(int lineToScan) {
		try{
			Scanner scanner;
			Path path = Paths.get(spmPath);
			scanner = new Scanner(path);
			for (int i = 0; i < lineToScan; i++) {
				scanner.nextLine();
			}

			String line = scanner.nextLine();

			countCurrentLine();
			scanner.close();
			currentString=line;
			return line;
		}
		catch (Exception e) {
			System.out.println("exception i ScannerIt: "+e);
			currentLine = -1;
			getNextQA();
			return("999;;;; ;;;;feil, sjekk ScannerIt");

		}


	}





	public void getNextQA(){ //scanner spørsmålet som currentLine står på, om den er på et kategorilinje eller tom, hopper den til neste.
		try{					// henter inn nytt sprøsmål fra filen og deler det opp i tilegg til å sette oppførsel til spørsmål.
			int lineToScan;

			/*sjekker om tilfeldig er huket av eller ikke, TODO måtte deaktivere funksjonen for å
			 * klare testene.
			 *if(getTilfeldig()) {
			 *	currentLine = randomTextLineNumber(catalogLength);
			 *	lineToScan = currentLine;
			 *}
			 *else{
			 *	lineToScan = currentLine;
			 *}
			 */
			lineToScan = currentLine;

			/*if(!getTilbakelegging()) { // todo: nå lager den en ny liste hver gang. kan gjøre om det.
				Progress p = new Progress();
				this.answeredLines = p.checkAnsweredLines(spmPath);
				if(getTilfeldig()) {
					Random random = new Random();
					this.currentLine = answeredLines.get(random.nextInt(answeredLines.size()));
				}else{
					if (answeredLines.isEmpty()) {
						Spørsmål.setText("Gratulerer!!! du har svart på alle spøsmålene, skru på tilbakellegging for å fortsette");
						Thread.sleep(3000);
					}else {
						int line = answeredLines.get(0);
						this.currentLine = line;	
					}
				}

			}
			 */
			//holder styr på hvilken linje vi er på.
			lineToScan++; //// må skjønne hvordan refere til subklasser fra en superklasse
			String fullPartsString = this.scannerIt(lineToScan);
			this.catalogLine = fullPartsString;
			//System.out.println("catalogline: "+this.catalogLine);
			String[] parts = fullPartsString.split(";;;;",-1);

			try {
				Double questionNumber = Double.parseDouble(parts[0]);

				if((questionNumber % 10)> 0){
					this.catalogQuestionNumber = parts[0];
					this.catalogPage = parts[1];
					this.catalogQuestionString = parts[2];
					this.catalogAnswerString = parts[3];
					this.catalogUserAnswers = parts.toString().replace(this.catalogAnswerString,"").replace(this.catalogPage,"").replace(this.catalogQuestionString,"").replace(this.catalogAnswerString,"").replace(";;","");
					this.catalogKeywords = findKeyWords(parts[3]);
				}
				else if(questionNumber % 10 == 0 && parts[1].equals("") && parts[3].equals("")) {
					this.catalogCategory = parts[2];
					setKategori();
					getNextQA();
				}
				else {
					getNextQA();
				}
				numbCatalogKeywords = catalogKeywords.size();
			}catch (Exception e) {
				getNextQA();
			}
		}catch (Exception e) {
			System.out.print("getNextQA har iofeil: ");
			e.printStackTrace();
		}
	}


	public List<String> findKeyWords(String str) {
		List<String> list = new ArrayList<String>();

		Pattern pattern = Pattern.compile("#(\\S+)");
		Matcher matcher = pattern.matcher(str);

		while (matcher.find()) {
			list.add(matcher.group(1));
		}
		return list;
	}

	public void setCatalogLength(String spmPath) {
		try {
			this.catalogLength = 0;
			Scanner scanner;
			Path path = Paths.get(spmPath);
			scanner = new Scanner(path);

			while(scanner.hasNextLine()) {
				scanner.nextLine();
				catalogLength++;
			}
			scanner.close();
		}
		catch (IOException e) {
			System.out.println("setCatalogLength exception;");
			e.printStackTrace();
		}	
	}

	public int randomTextLineNumber(int limit){
		Random randomGen = new Random();
		int lineNumber = randomGen.nextInt(limit);
		return  lineNumber;
	}


}


