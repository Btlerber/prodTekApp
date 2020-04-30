package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import app.ProdTekAppController;
import app.Progress;
import app.SaveAnswer;


class UserSaveTest extends junit.framework.TestCase{
	
	String urlPath = "/Users/Bjorn/Documents/objOri2020/git/tdt4100-v2020-students/prodTekExport/src/tests/saveTextTest";
	Path path = Paths.get(urlPath);
	ProdTekAppController ptac = new ProdTekAppController();
	

	@Test
	void testPath(){
		ptac.setspmPath(urlPath);
		assertEquals(urlPath,ptac.getSpmPath());
	}
	
	@Test
	void checkUserAnswer(){
		//skal ikke lagres, da svaret er for kort 
		ptac.setspmPath(urlPath);
		ptac.getNextQA();
		String linje0 = ptac.getCatalogLine();
		ptac.getNextQA();
		String linje1 = ptac.getCatalogLine();
		
		SaveAnswer sat0 = new SaveAnswer(linje0,"svar",path);
		SaveAnswer sat1 = new SaveAnswer(linje1,"langtsvar, lang nok til å lagres",path);
		//opretter instans og sjekker om et av svarene over er lagret.
		ptac.setspmPath(urlPath);
		ptac.currentLine = -1;
		ptac.getNextQA();
		assertEquals(linje0 , ptac.getCatalogLine());
		ptac.getNextQA();
		assertEquals(linje1+";;;;eldre_brukersvar: langtsvar, lang nok til å lagres", ptac.getCatalogLine());
		
	}
	// kan hende metoden over må kjøre først
	@Test
	void testProgress(){
		Progress p = new Progress();
		assertEquals(0.5,p.checkProgress(urlPath));
	}
	
	@Test
	void testLength() {
		ptac.setCatalogLength(urlPath);
		assertEquals(2,ptac.getCatalogLength());
	}
	
	
	@Test
	void testSomething() {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	
	
}
