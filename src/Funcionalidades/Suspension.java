package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Pages.Accounts;
import Pages.CustomerCare;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Suspension extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private CBS_Mattu cbsm;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log ;
	String detalles;
	
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}

	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}

	//@BeforeClass(groups = "PerfilAgente")
	public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();

	}

	@BeforeMethod(alwaysRun = true)
	public void setup() throws Exception {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();
	}

	@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver, imagen);
		sleep(2000);
	}

	// @AfterClass (alwaysRun = true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaSuspension" )
	public void TS_98438_CRM_Movil_REPRO_Suspension_por_Siniestro_Hurto_Linea_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS_98438";
		detalles = imagen + "- Suspension - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cc.irAGestion("suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"), 0);
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Suspensi\u00f3n')]"), 0);
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class ='slds-radio ng-scope']//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text() , 'Linea')]")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+sLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AccountData_nextBtn")));
		selectByText(driver.findElement(By.cssSelector("[id = 'Radio3-ReasonSuspension']")), "Hurto");
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Argentina')]"), 0);
		selectByText(driver.findElement(By.cssSelector("[id = 'State']")), sProvincia);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sLocalidad);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]")));
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]"), 0);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(3000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		clickBy(driver, By.id("Step6-Summary_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		sleep(15000);
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean gestion = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
			String estado = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]")).getText();
			if (estado.equalsIgnoreCase("realizada exitosa")) {
				gestion = true;
			} else {
				sleep(8000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(gestion);
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("Contact_body"), 0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click(); 
		sleep(5000);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String verificacion = driver.findElement(By.xpath("//section[@class = 'console-card active expired']//div[@class = 'card-info']//ul[@class = 'uLdetails']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));
	
		
		
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "CuentaSuspension" )
	public void TS_98442_CRM_Movil_REPRO_Suspension_por_Siniestro_Extravio_Linea_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS_98442";
		detalles = imagen + "- Suspension - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cc.irAGestion("suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"), 0);
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Suspensi\u00f3n')]"), 0);
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class ='slds-radio ng-scope']//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text() , 'Linea')]")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+sLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AccountData_nextBtn")));
		selectByText(driver.findElement(By.cssSelector("[id = 'Radio3-ReasonSuspension']")), "Extravio");
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Argentina')]"), 0);
		selectByText(driver.findElement(By.cssSelector("[id = 'State']")), sProvincia);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sLocalidad);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]")));
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]"), 0);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(2000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		clickBy(driver, By.id("Step6-Summary_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		sleep(20000);
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean gestion = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
			String estado = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]")).getText();
			if (estado.equalsIgnoreCase("realizada exitosa")) {
				gestion = true;
			} else {
				sleep(8000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(gestion);
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("Contact_body"), 0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click(); 
		sleep(5000);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String verificacion = driver.findElement(By.xpath("//section[@class = 'console-card active expired']//div[@class = 'card-info']//ul[@class = 'uLdetails']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));
	}
	
	@Test (groups = {"PerfilOficina","R1"}, dataProvider = "CuentaSuspensionApro" )
	public void TS148711_CRM_Movil_Mix_Suspension_por_Siniestro_Hurto_Linea_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS_148711";
		detalles = imagen + "- Suspension - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cc.irAGestion("suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"), 0);
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Suspensi\u00f3n')]"), 0);
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class ='slds-radio ng-scope']//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text() , 'Linea')]")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+sLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AccountData_nextBtn")));
		selectByText(driver.findElement(By.cssSelector("[id = 'Radio3-ReasonSuspension']")), "Hurto");
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Argentina')]"), 0);
		selectByText(driver.findElement(By.cssSelector("[id = 'State']")), sProvincia);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sLocalidad);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]")));
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]"), 0);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(5000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		clickBy(driver, By.id("Step6-Summary_nextBtn"), 0);
		esperarElemento(driver,By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']"), -50);
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		sleep(20000);
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean gestion = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
			String estado = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]")).getText();
			if (estado.equalsIgnoreCase("realizada exitosa")) {
				gestion = true;
			} else {
				sleep(8000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(gestion);
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("Contact_body"), 0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click(); 
		sleep(5000);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String verificacion = driver.findElement(By.xpath("//div[@class = 'card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));
	}
	
//	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
//	public void TS98434_CRM_Movil_REPRO_Suspension_por_Siniestro_Robo_Linea_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
//		imagen = "TS98434";
//		boolean gestion = false;
//		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
//		sb.BuscarCuenta("DNI", sDNI);
//		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
//		sleep(15000);
//		cc.irAGestion("suspensiones");
//		sleep(5000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
//		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
//		sleep(7000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
//		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
//		sleep(5000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
//		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
//		sleep(5000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
//		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
//		sleep(5000);
//		driver.findElement(By.id("State")).sendKeys(sProvincia);
//		driver.findElement(By.id("Partido")).sendKeys(sPartido);
//		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
//		driver.findElement(By.id("AccountData_nextBtn")).click();
//		sleep(5000);
//		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
//		sleep(15000);
//		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
//		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
//			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
//				gestion = true;
//		}
//		Assert.assertTrue(gestion);
//	}
	
//	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
//	public void TS98436_CRM_Movil_REPRO_Suspension_por_Siniestro_Robo_Linea_No_Titular_Presencial(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
//		imagen = "TS98436";
//		boolean gestion = false;
//		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
//		sb.BuscarCuenta("DNI", sDNI);
//		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
//		sleep(15000);
//		cc.irAGestion("suspensiones");
//		sleep(5000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
//		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
//		sleep(7000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
//		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
//		sleep(5000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
//		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
//		sleep(5000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
//		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
//		sleep(5000);
//		driver.findElement(By.id("State")).sendKeys(sProvincia);
//		driver.findElement(By.id("Partido")).sendKeys(sPartido);
//		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
//		driver.findElement(By.id("DNI")).sendKeys(sDNI);
//		driver.findElement(By.id("FirstName")).sendKeys("Cinco");
//		driver.findElement(By.id("LastName")).sendKeys("Newton");
//		driver.findElement(By.id("Phone")).sendKeys("2944675270");
//		driver.findElement(By.id("AccountData_nextBtn")).click();
//		sleep(5000);
//		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
//		sleep(15000);
//		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
//		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
//			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
//				gestion = true;
//		}
//		Assert.assertTrue(gestion);
//	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = {"PerfilTelefonico","R1"}, dataProvider = "CuentaSuspensionApro" )
	public void TS148622_CRM_Movil_Mix_Suspension_por_Siniestro_Robo_Linea_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS147604";
		ges.BuscarCuenta("DNI", sDNI);
		cc.irAGestion("suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"), 0);
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Suspensi\u00f3n')]"), 0);
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class ='slds-radio ng-scope']//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text() , 'Linea')]")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+sLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AccountData_nextBtn")));
		selectByText(driver.findElement(By.cssSelector("[id = 'Radio3-ReasonSuspension']")), "Robo");
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Argentina')]"), 0);
		selectByText(driver.findElement(By.cssSelector("[id = 'State']")), sProvincia);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sLocalidad);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]")));
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]"), 0);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(2000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		clickBy(driver, By.id("Step6-Summary_nextBtn"), 0);
		esperarElemento(driver,By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']"), -50);
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		sleep(20000);
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean gestion = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
			String estado = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]")).getText();
			if (estado.equalsIgnoreCase("realizada exitosa")) {
				gestion = true;
			} else {
				sleep(8000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(gestion);
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("Contact_body"), 0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click(); 
		sleep(5000);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String verificacion = driver.findElement(By.xpath("//div[@class = 'card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));
	}
	
	@Test (groups = {"PerfilTelefonico","R1"}, dataProvider = "CuentaSuspensionApro" )
	public void TS148626_CRM_Movil_Mix_Suspension_por_Siniestro_Hurto_Linea_No_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sLocalidad, String sPartido) {
		imagen = "TS148626";
		ges.BuscarCuenta("DNI", sDNI);
		cc.irAGestion("suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"), 0);
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Suspensi\u00f3n')]"), 0);
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@class ='slds-radio ng-scope']//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text() , 'Linea')]")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")),"contains","l\u00ednea: "+sLinea);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("AccountData_nextBtn")));
		selectByText(driver.findElement(By.cssSelector("[id = 'Radio3-ReasonSuspension']")), "Hurto");
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Argentina')]"), 0);
		selectByText(driver.findElement(By.cssSelector("[id = 'State']")), sProvincia);
		driver.findElement(By.id("CityTypeAhead")).sendKeys(sLocalidad);
		driver.findElement(By.id("Partido")).sendKeys(sPartido);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]")));
		clickBy(driver, By.xpath("//span[@class = 'slds-form-element__label ng-binding ng-scope'] [contains (text(), 'Si')]"), 0);
		driver.findElement(By.id("AccountData_nextBtn")).click();
		sleep(2000);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		clickBy(driver, By.id("Step6-Summary_nextBtn"), 0);
		esperarElemento(driver,By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']"), -50);
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		sleep(20000);
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean gestion = false;
		for (int i = 0; i < 10; i++) {
			cambioDeFrame(driver, By.id("searchPageHolderDiv"), 0);
			String estado = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]")).getText();
			if (estado.equalsIgnoreCase("realizada exitosa")) {
				gestion = true;
			} else {
				sleep(8000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(gestion);
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		cambioDeFrame(driver, By.id("Contact_body"), 0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click(); 
		sleep(5000);
		cambioDeFrame(driver, By.className("card-top"), 0);
		String verificacion = driver.findElement(By.xpath("//div[@class = 'card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Suspendido"));
	}
//	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
//	public void TS98435_CRM_Movil_REPRO_Suspension_por_Siniestro_Robo_Linea_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
//		imagen = "TS98435";
//		boolean gestion = false;
//		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
//		sb.BuscarCuenta("DNI", sDNI);
//		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
//		sleep(15000);
//		cc.irAGestion("suspensiones");
//		sleep(5000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
//		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
//		sleep(7000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
//		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
//		sleep(5000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
//		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
//		sleep(5000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
//		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
//		sleep(5000);
//		driver.findElement(By.id("State")).sendKeys(sProvincia);
//		driver.findElement(By.id("Partido")).sendKeys(sPartido);
//		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
//		driver.findElement(By.id("AccountData_nextBtn")).click();
//		sleep(5000);
//		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
//		sleep(15000);
//		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
//		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
//			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
//				gestion = true;
//		}
//		Assert.assertTrue(gestion);
//	}
//	
//	@Test (groups = {"Suspension", "GestionesPerfilOficina", "E2E", "Ciclo3"}, dataProvider = "CuentaSuspension")
//	public void TS98437_CRM_Movil_Prepago_Suspension_por_Siniestro_Robo_Linea_No_Titular_Telefonico(String sDNI, String sLinea, String sProvincia, String sCiudad, String sPartido) {
//		imagen = "TS98437";
//		boolean gestion = false;
//		driver.switchTo().frame(cambioFrame(driver, By.id("SearchClientDocumentType")));
//		sb.BuscarCuenta("DNI", sDNI);
//		driver.findElement(By.cssSelector(".slds-tree__item.ng-scope")).click();
//		sleep(15000);
//		cc.irAGestion("suspensiones");
//		sleep(5000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
//		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
//		sleep(7000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
//		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
//		sleep(5000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "contains", "l\u00ednea: ");
//		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
//		sleep(5000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "robo");
//		driver.findElement(By.id("Step4-SuspensionReason_nextBtn")).click();
//		sleep(5000);
//		driver.findElement(By.id("State")).sendKeys(sProvincia);
//		driver.findElement(By.id("Partido")).sendKeys(sPartido);
//		driver.findElement(By.id("CityTypeAhead")).sendKeys(sCiudad);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
//		driver.findElement(By.id("DNI")).sendKeys(sDNI);
//		driver.findElement(By.id("FirstName")).sendKeys("Cinco");
//		driver.findElement(By.id("LastName")).sendKeys("Newton");
//		driver.findElement(By.id("Phone")).sendKeys("2944675270");
//		driver.findElement(By.id("AccountData_nextBtn")).click();
//		sleep(5000);
//		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
//		sleep(15000);
//		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.ta-care-omniscript-pending-icon")));
//		for (WebElement x : driver.findElements(By.className("ta-care-omniscript-done"))) {
//			if (!x.getText().contains("Tu solicitud est\u00e1 siendo procesada"))
//				gestion = true;
//		}
//		Assert.assertTrue(gestion);
//	}
	
	//----------------------------------------------- FRAUDE -------------------------------------------------------\\
	
//	@Test (groups = {"Suspension", "GestionesPerfilFraude","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
//	public void TS98477_CRM_Movil_REPRO_Suspension_por_Fraude_Linea_Comercial_Desconocimiento_Administrativo(String sDNI, String sLinea, String cProvincia, String cCiudad, String cPartido) {
//		imagen = "TS98477";
//		detalles = null;
//		detalles = imagen + " -Suspension - DNI: " + sDNI;
//		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
//		busqueda.sendKeys(sLinea);
//		busqueda.submit();
//		sleep(4000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
//		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
//		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
//		sleep(8000);
//		cc.irAGestion("suspensiones y reconexion backoffice");
//		sleep(40000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
//		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
//		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
//		driver.findElement(By.id("Step3_nextBtn")).click();
//		sleep(8000);
//		selectByText(driver.findElement(By.id("SelectFraud")),"Comercial");
//		selectByText(driver.findElement(By.id("SelectSubFraud")),"Desconocimiento");
//		driver.findElement(By.id("Step4_nextBtn")).click();
//		sleep(15000);
//		driver.findElement(By.id("StepSummary_nextBtn")).click();
//		sleep(15000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
//		sleep(40000);
//		boolean b = false;
//		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
//		for(WebElement x : prov) {
//			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
//				b = true;
//			}			
//		}
//		Assert.assertTrue(b);
//		sleep(10000);
//		CBS_Mattu cCBSM = new CBS_Mattu();
//		Assert.assertTrue(cCBSM.obtenerStatusLinea(sLinea).equals("suspension fraude"));
//		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
//		detalles += " - Orden: "+orden;
//		sOrders.add("Suspencion, orden numero: " + orden + " con numero de DNI: " + sDNI);
//		System.out.println(sOrders);
//	}
//	
//	@Test (groups = {"Suspension", "GestionesPerfilFraude","E2E","Ciclo3"}, dataProvider="CuentaSuspension") 
//	public void TS98484_CRM_Movil_REPRO_Suspension_por_Fraude_DNI_CUIT_Comercial_Fraude_por_suscripcion_Administrativo(String sDNI, String sLinea, String cProvincia, String cCiudad, String cPartido) {
//		imagen = "TS98484";
//		detalles = null;
//		detalles = imagen + "-Suspension - DNI:" + sDNI;
//		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
//		busqueda.sendKeys(sLinea);
//		busqueda.submit();
//		sleep(4000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
//		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
//		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
//		sleep(8000);
//		cc.irAGestion("suspensiones y reconexion back");
//		sleep(15000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
//		sleep(8000);
//		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
//		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
//		driver.findElement(By.id("Step3_nextBtn")).click();
//		sleep(8000);
//		selectByText(driver.findElement(By.id("SelectFraud")),"Comercial");
//		selectByText(driver.findElement(By.id("SelectSubFraud")),"Administrativo por suscripci\u00f3n");
//		driver.findElement(By.id("TxtComment")).sendKeys("Fraude");
//		driver.findElement(By.id("Step4_nextBtn")).click();
//		sleep(8000);
//		driver.findElement(By.id("StepSummary_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
//		boolean a = false;
//		List <WebElement> realiz = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
//		for(WebElement x : realiz) {
//			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
//				a = true;
//			}
//		}
//		Assert.assertTrue(a);
//		sleep(8000);
//		CBS_Mattu cCBSM = new CBS_Mattu();
//		Assert.assertTrue(cCBSM.obtenerStatusLinea(sLinea).equals("suspension fraude"));
//		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
//		detalles+=" - Orden: "+orden;
//	}
//	
//	@Test (groups = {"Suspension", "GestionesPerfilFraude","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
//	public void TS98487_CRM_Movil_REPRO_Suspension_por_Fraude_DNI_CUIT_Comercial_Irregular_Administrativo(String sDNI, String sLinea, String cProvincia, String cCiudad, String cPartido) {
//		imagen = "TS98487";
//		detalles = null;
//		detalles = imagen + " - Suspension - DNI: " + sDNI;
//		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
//		busqueda.sendKeys(sLinea);
//		busqueda.submit();
//		sleep(4000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
//		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
//		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
//		sleep(8000);
//		cc.irAGestion("suspensiones y reconexion backoffice");
//		sleep(40000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
//		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
//		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
//		driver.findElement(By.id("Step3_nextBtn")).click();
//		sleep(8000);
//		selectByText(driver.findElement(By.id("SelectFraud")),"Comercial");
//		selectByText(driver.findElement(By.id("SelectSubFraud")),"Irregular");
//		driver.findElement(By.id("Step4_nextBtn")).click();
//		sleep(8000);
//		driver.findElement(By.id("StepSummary_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
//		sleep(40000);
//		boolean b = false;
//		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
//		for(WebElement x : prov) {
//			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
//				b = true;
//			}			
//		}
//		Assert.assertTrue(b);
//		sleep(10000);
//		CBS_Mattu cCBSM = new CBS_Mattu();
//		Assert.assertTrue(cCBSM.obtenerStatusLinea(sLinea).equals("suspendida fraude"));
//		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
//		detalles+= " - Orden: "+orden;
//		sOrders.add("Suspencion, orden numero: " + orden + " con numero de DNI: " + sDNI);
//		System.out.println(sOrders);
//	}
//	
//	@Test (groups = {"Suspension", "GestionesPerfilFraude","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
//	public void TS98491_CRM_Movil_REPRO_Suspension_por_Fraude_Linea_Comercial_Desconocimiento_Administrativo(String cDNI, String cLinea, String cProvincia, String cCiudad, String cPartido) {
//		imagen = "TS98491";
//		detalles = null;
//		detalles = imagen + "- Suspension -DNI:" + cDNI;
//		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
//		busqueda.sendKeys(cLinea);
//		busqueda.submit();
//		sleep(4000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
//		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
//		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
//		sleep(8000);
//		cc.irAGestion("suspensiones y reconexion back");
//		sleep(15000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
//		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
//		sleep(10000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
//		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
//		driver.findElement(By.id("Step3_nextBtn")).click();
//		sleep(8000);
//		selectByText(driver.findElement(By.id("SelectFraud")),"Comercial");
//		selectByText(driver.findElement(By.id("SelectSubFraud")),"Desconocimiento");
//		driver.findElement(By.id("TxtComment")).sendKeys("Fraude");
//		driver.findElement(By.id("Step4_nextBtn")).click();
//		sleep(8000);
//		driver.findElement(By.id("StepSummary_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")),"contains", "continue");
//		sleep(15000);
//		boolean a = false;
//		List <WebElement> realiz = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
//		for(WebElement x : realiz) {
//			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
//				a = true;
//			}
//		}
//		Assert.assertTrue(a);
//		sleep(8000);
//		CBS_Mattu cCBSM = new CBS_Mattu();
//		Assert.assertTrue(cCBSM.obtenerStatusLinea(cLinea).equals("suspension fraude"));
//		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
//		detalles+=" - Orden: "+orden;	
//	}
//	
//	@Test (groups = {"Suspension", "GestionesPerfilFraude","E2E","Ciclo3"}, dataProvider="CuentaSuspension")
//	public void TS98498_CRM_Movil_REPRO_Suspension_por_Fraude_Cuenta_de_facturacion_Comercial_Desconocimiento_Administrativo(String sDNI, String sLinea, String cProvincia, String cCiudad, String cPartido) {
//		imagen = "TS98498";
//		detalles = null;
//		detalles = imagen + " -Suspension - DNI: " + sDNI;
//		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
//		busqueda.sendKeys(sLinea);
//		busqueda.submit();
//		sleep(4000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
//		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
//		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
//		sleep(8000);
//		cc.irAGestion("suspensiones y reconexion back");
//		sleep(15000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "suspensi\u00f3n");
//		sleep(8000);
//		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
//		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
//		driver.findElement(By.id("Step3_nextBtn")).click();
//		sleep(8000);
//		selectByText(driver.findElement(By.id("SelectFraud")), "Comercial");
//		selectByText(driver.findElement(By.id("SelectSubFraud")), "Desconocimiento");
//		driver.findElement(By.id("Step4_nextBtn")).click();
//		sleep(8000);
//		driver.findElement(By.id("StepSummary_nextBtn")).click();
//		sleep(8000);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")), "contains", "continue");
//		sleep(15000);
//		boolean b = false;
//		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
//		for(WebElement x : prov) {
//			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
//				b = true;
//			}			
//		}
//		Assert.assertTrue(b);
//		sleep(8000);
//		CBS_Mattu cCBSM = new CBS_Mattu();
//		Assert.assertTrue(cCBSM.obtenerStatusLinea(sLinea).equals("suspension fraude"));
//		String orden = cc.obtenerOrden(driver, "Suspension administrativa");
//		detalles+=" - orden: "+orden;
//		System.out.println(sOrders);	
//	}
}