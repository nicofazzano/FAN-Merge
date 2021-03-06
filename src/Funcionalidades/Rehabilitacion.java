package Funcionalidades;

import java.awt.AWTException;
import java.io.File;
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
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Rehabilitacion extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private SalesBase sb;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private ContactSearch contact;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private TestBase tb;
	String detalles;
	
	
	
	@BeforeClass (alwaysRun = true)
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		tb = new TestBase();
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
	
	@BeforeMethod(alwaysRun=true)
	public void setup() throws Exception {
		detalles = null;
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		ges.selectMenuIzq("Inicio");
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
	}

	//@AfterMethod(alwaysRun=true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass(alwaysRun=true)
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = "PerfilOficina", dataProvider="CuentaHabilitacion")
	public void TS98590_CRM_Movil_REPRO_Rehabilitacion_por_Siniestro_Presencial_Robo_Linea(String sDNI, String sLinea) {
		imagen = "TS98590";
		detalles = imagen + " - Rehabilitacion - DNI: " + sDNI;
		cambioDeFrame(driver,By.id("phSearchInput"),0);
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		sleep(7500);
		cambioDeFrame(driver,By.id("Contact_body"),0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click();
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='card-top']")));
		cc.irAGestion("Suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step2-AssetTypeSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		driver.findElement(By.xpath("//*[@class='slds-radio--faux']")).click();
		sleep(2500);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		sleep(2500);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(5000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean z = false;
			for(int i = 0; i<10; i++ ){
				cambioDeFrame(driver, By.id("Case_body"),0);
				WebElement status = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]"));
				if(status.getText().contains("Realizada exitosa")){
					z = true;	
				}
			else{
				sleep(5000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(z);
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
		ges.BuscarCuenta("DNI", sDNI);
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='card-top']")));
		WebElement postestado = driver.findElement(By.xpath("//div[@class = 'card-info']//ul[@class = 'uLdetails']//span[@class = 'imagre']"));
		Assert.assertTrue(postestado.getText().contains("Activo"));
		
	}
	
	@Test (groups = {"PerfilOficina","R1"}, dataProvider="CuentaHabilitacion")
	public void TS148676_CRM_Movil_Mix_Rehabilitacion_por_Siniestro_Presencial_Hurto_Linea(String sDNI, String sLinea) {
		imagen = "TS148676";
		detalles = imagen + " - Rehabilitacion - DNI: " + sDNI;
		cambioDeFrame(driver,By.id("phSearchInput"),0);
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		sleep(7500);
		cambioDeFrame(driver,By.id("Contact_body"),0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click();
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='card-top']")));
		cc.irAGestion("Suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step2-AssetTypeSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		driver.findElement(By.xpath("//*[@class='slds-radio--faux']")).click();
		sleep(2500);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		sleep(2500);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(5000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean z = false;
			for(int i = 0; i<10; i++ ){
				cambioDeFrame(driver, By.id("Case_body"),0);
				WebElement status = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]"));
				if(status.getText().contains("Realizada exitosa")){
					z = true;	
				}
			else{
				sleep(5000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(z);
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
		ges.BuscarCuenta("DNI", sDNI);
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String verificacion = driver.findElement(By.xpath("//div[@class ='card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Activo"));
	}
	
	@Test (groups = {"PerfilOficina","R1"}, dataProvider="CuentaHabilitacion")
	public void TS148675_CRM_Movil_Mix_Rehabilitacion_por_Siniestro_Presencial_Robo_Linea(String sDNI, String sLinea) {
		imagen = "TS148675";
		detalles = imagen + " - Rehabilitacion - DNI: " + sDNI;
		cambioDeFrame(driver,By.id("phSearchInput"),0);
		driver.findElement(By.id("phSearchInput")).sendKeys(sDNI);
		driver.findElement(By.id("phSearchInput")).submit();
		sleep(7500);
		cambioDeFrame(driver,By.id("Contact_body"),0);
		WebElement nombreCuenta = driver.findElement(By.xpath("//*[@id='Contact_body']//tbody/tr[2]//td[2]//a"));
		nombreCuenta.click();
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='card-top']")));
		cc.irAGestion("Suspensiones");
		cambioDeFrame(driver, By.id("Step1-SuspensionOrReconnection_nextBtn"),0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step1-SuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1-SuspensionOrReconnection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step2-AssetTypeSelection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-AssetTypeSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step3-AvailableAssetsSelection_nextBtn")));
		driver.findElement(By.xpath("//*[@class='slds-radio--faux']")).click();
		sleep(2500);
		driver.findElement(By.id("Step3-AvailableAssetsSelection_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Step6-Summary_nextBtn")));
		sleep(2500);
		driver.findElement(By.id("Step6-Summary_nextBtn")).click();
		sleep(5000);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header h1")).getText().equalsIgnoreCase("tu solicitud est\u00e1 siendo procesada."));
		String ncaso = driver.findElement(By.cssSelector("[class = 'slds-box ng-scope'] [class = 'ta-care-omniscript-done'] header")).getText();
		String numeroCaso = cc.getNumeros(ncaso);
		System.out.println(cc.getNumeros(ncaso));
		ges.cerrarPestaniaGestion(driver);
		driver.findElement(By.id("phSearchInput")).clear();
		driver.findElement(By.id("phSearchInput")).sendKeys(numeroCaso);
		driver.findElement(By.id("phSearchInput")).submit();
		boolean z = false;
			for(int i = 0; i<10; i++ ){
				cambioDeFrame(driver, By.id("Case_body"),0);
				WebElement status = driver.findElement(By.xpath("//*[@id='Case_body']//tr[2]//td[3]"));
				if(status.getText().contains("Realizada exitosa")){
					z = true;	
				}
			else{
				sleep(5000);
				driver.navigate().refresh();
			}
		}
		Assert.assertTrue(z);
		ges.cerrarPestaniaGestion(driver);
		ges.irGestionClientes();
		ges.BuscarCuenta("DNI", sDNI);
		tb.cambioDeFrame(driver, By.cssSelector("[class='card-top']"), 0);
		String verificacion = driver.findElement(By.xpath("//div[@class ='card-info-hybrid']//ul[@class = 'details']//span[@class = 'imagre']")).getText();
		Assert.assertTrue(verificacion.equals("Activo"));
	}
	
	
	//----------------------------------------------- FRAUDE -------------------------------------------------------\\
	
	@Test (groups = {"Habilitacion", "GestionesPerfilFraude","E2E", "Ciclo3"}, dataProvider="CuentaHabilitacion")
	public void TS98599_CRM_Movil_REPRO_Rehabilitacion_Administrativo_Fraude_DNI(String sDNI, String sLinea) {
		imagen = "TS98599";
		detalles = null;
		detalles = imagen + " - Rehabilitacion - DNI: " + sDNI;
		WebElement busqueda = driver.findElement(By.id("phSearchInput"));
		busqueda.sendKeys(sLinea);
		busqueda.submit();
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Asset")));
		WebElement body = driver.findElement(By.id("Asset")).findElement(By.id("Asset_body")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
		body.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(2).click();
		sleep(8000);
		cc.irAGestion("suspensiones y reconexion backoffice");
		sleep(40000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step1SelectSuspensionOrReconnection_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "habilitaci\u00f3n");
		driver.findElement(By.id("Step1SelectSuspensionOrReconnection_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step2-SelectAssetOrDocument_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "linea");
		driver.findElement(By.id("Step2-SelectAssetOrDocument_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Step3_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")),"contains", "nombre: plan con tarjeta repro");
		driver.findElement(By.id("Step3_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("TxtComment")).sendKeys("Fraude");
		driver.findElement(By.id("Step4_nextBtn")).click();
		sleep(8000);
		driver.findElement(By.id("StepSummary_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--neutral.ng-binding.ng-scope")), "contains", "continue");
		sleep(15000);
		boolean b = false;
		List <WebElement> prov = driver.findElements(By.cssSelector(".slds-box.ng-scope"));
		for(WebElement x : prov) {
			if(x.getText().toLowerCase().contains("tu gesti\u00f3n se realiz\u00f3 con \u00e9xito")) {
				b = true;
			}			
		}
		Assert.assertTrue(b);
		sleep(8000);
		String orden = cc.obtenerOrden(driver, "Habilitaci\u00f3n administrativa");
		detalles+=" - Orden: "+orden;
		System.out.println(sOrders);
	}
}