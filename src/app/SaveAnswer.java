package app;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SaveAnswer{

	public SaveAnswer(String line,String answer,Path path){   //tar in linje, og hva som skal legges til på linjen, og path seff. hvis answer er tomt, gjør den ingenting
		if(answer.length() < 5) {
			System.out.println("for kort svar til å lagre");
			return;
		}
		String replacement = line+";;;;eldre_brukersvar: "+answer;
		System.out.println("replacment: "+replacement);
		Charset charset = StandardCharsets.UTF_8;
		String content;
		boolean wasWritten = false;
		try {		//prøver å bytte ut strengen ved å lese inn hele filen, bytte ut substrengen og skrive filen på nytt.
			content = new String(Files.readAllBytes(path), charset);
			//String content1 = content.replaceAll(line,replacement);
			String content1 = content.replace(line,replacement);
			try{
				Thread.sleep(200);
			}catch (Exception e) {System.out.println(e);}
			Files.write(path, content1.getBytes(charset));
			wasWritten = true;
		} catch (IOException e) {
			System.out.println("SaveAnswer_replaceLine sier: " + e);
			wasWritten = false;
		}
		if(wasWritten) {
			System.out.println("svar lagret");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		String urlPath = "/Users/Bjorn/Documents/objOri2020/git/tdt4100-v2020-students/prodTekExport/src/tests/saveTextTest";
		Path path = Paths.get(urlPath);
		String linje0 ="11.0;;;;;;;;Forklar kort stegvis hvordan prosessen ved pulverlakkering foregår. Nevn noen fordeler ved bruk av pulverlakkering.;;;;Pulverlakkering er en 4 stegs prosess: #forbehandling, påføring av #pulver, #smelting og #kvalitetskontroll. Forbehandling: Det er avgjørende for resultatet at forbehandling er skikkelig utført, hensikten med forbehandlingen er å fjerne overflaterester og ujevnheter. Det finnes flere forskjellige forbehandlingsmetoder: Sandblåsing, syrebad eller ultrasonisk rensning m.m Forbehandling er avgjørende for levetiden til overflaten, det finnes derfor korrosjonsklasser hvor de forskjellige forbehandlingsmetodene inngår. Påføring av pulver: Etter forbehandling, begynner selve påføringen av pulver. Selve pulveret blir ladet elektrostatisk ved hjelp av sprøyte pistolen mot objektet som er jordet, pulveret fester seg til objektet på grunn av at spenningsforskjellen suger til seg til seg pulverlakken, spenning er lik over hele objekt flaten, det gjør at pulveret fordeles likt. Smelting/herding: Etter at pulveret er påført flaten til objektet, blir den ført inn i en ovn i 10 til 15 minutter på 200 grader for først å smelte sammen pulverpartikler til et sammenhengende og beskyttende lakksjikt og så for å herde den. Kvalitetskontroll: Etter at herdeprosessen er ferdig, vil det bli foretatt en visuell kontroll og deretter vil man kontrollere at filmtykkelsen er i henhold til spesifikasjonene og at den overflaten har den ønskelige glansen. Noen av fordelene med pulverlakkering er blant annet, at overflater som er pulverlakkert er robuste mot slag, støtt og riper, som kommer av det tykke lakksjikte. Overflaten tåler også godt vær og vind. Alt i alt så er pulverlakkering gode egenskaper på de fleste områder. Påføring er noe enklere vet at man kan påføre tykkere lag med lakk uten at det renner eller drypper. Pulverlakkering er en relativt billig prosess, blant annet så kan pulveret som ikke treffer flaten brukes om igjen. Dette gjør at prosessen blir mer miljøvennlig og bærekraftig enn flytende lakk.;;;;;;;;;;;;;;;;;;;;;;;;forbehandling, smelting;;;;";
		String linje1 ="21.0;;;;;;;;Hva skiller klipping fra stansing?;;;;Klipping er bearbeiding og utføres med to #egger mot hverandre. Materialet utsettes for store deformasjon og #skjærspenning rundt eggene, så det oppstår brudd. Dersom #klippekontur er sluttet, kaller vi det for #stansing.;;;;;;;;;;;;;;;;;;;;;;;;;;;;eldre_brukersvar: da utfører vi noe....";
		SaveAnswer sat0 = new SaveAnswer(linje0,"svar",path);
		SaveAnswer sat1 = new SaveAnswer(linje1,"langtsvar, langt nok til å blit lagret",path);
	}
}




























