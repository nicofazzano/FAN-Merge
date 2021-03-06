package Funcionalidades;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.TechCare_Ola1;
import Pages.TechnicalCareCSRAutogestionPage;
import Pages.TechnicalCareCSRDiagnosticoPage;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class DiagnosticoInconvenientes extends TestBase {

	private WebDriver driver;
	private CustomerCare cc;
	private TechCare_Ola1 tc;
	private TechnicalCareCSRAutogestionPage tca;
	private TechnicalCareCSRDiagnosticoPage tcd;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		tca =  new TechnicalCareCSRAutogestionPage(driver);
		tcd = new TechnicalCareCSRDiagnosticoPage(driver);
		ges = new GestionDeClientes_Fw(driver);
		tc = new TechCare_Ola1(driver);
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		tca =  new TechnicalCareCSRAutogestionPage(driver);
		tcd = new TechnicalCareCSRDiagnosticoPage(driver);
		tc = new TechCare_Ola1(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilAgente")
		public void initAgente() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		tca =  new TechnicalCareCSRAutogestionPage(driver);
		tcd = new TechnicalCareCSRDiagnosticoPage(driver);
		tc = new TechCare_Ola1(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();
	}
		
	//@BeforeClass (groups = "PerfilAdminFuncional")
		public void initAdminFuncional() {
		driver = setConexion.setupEze();
		cc = new CustomerCare(driver);
		tca =  new TechnicalCareCSRAutogestionPage(driver);
		tcd = new TechnicalCareCSRDiagnosticoPage(driver);
		tc = new TechCare_Ola1(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginAdminFuncional();
		ges.irAConsolaFAN();
	}
	
	//@BeforeMethod (alwaysRun = true)
	public void setup() {
		detalles = null;
		ges.cerrarPestaniaGestion(driver);
		ges.selectMenuIzq("Inicio");
		ges.irGestionClientes();	
	}

	//@AfterMethod (alwaysRun = true)
	public void after() throws IOException {
		guardarListaTxt(sOrders);
		sOrders.clear();
		tomarCaptura(driver,imagen);
		sleep(2000);
	}

	//@AfterClass (alwaysRun = true)
	public void quit() {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119162_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas(String sDNI, String sLinea) {
		imagen = "TS119162";
		detalles = imagen + " -ServicioTecnico: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "equals", "s\u00ed");
		buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(5000);
		tcd.categoriaRed("No son las antenas (Verde)");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS105418_CRM_Movil_Repro_Autogestion_0800_Inconv_con_derivacion_a_representante_Resuelto(String sDNI, String sLinea) {
		imagen = "TS105418";
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(7000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SelfManagementFields")));
		driver.findElement(By.cssSelector("[id=ChannelSelection]")).click();
		List<WebElement> opcionesCanal = driver.findElements(By.cssSelector("[class='slds-list--vertical vlc-slds-list--vertical'] li"));
		buscarYClick(opcionesCanal, "contains", "800");
		List<WebElement> opcionesServicios = driver.findElements(By.cssSelector("[class='slds-list--vertical vlc-slds-list--vertical'] li"));
		buscarYClick(opcionesServicios, "contains", "0800-444-0531");
		List<WebElement> opcionesInconvenientes = driver.findElements(By.cssSelector("[class='slds-list--vertical vlc-slds-list--vertical'] li"));
		buscarYClick(opcionesInconvenientes, "contains", "informa sistema fuera de servicio");
		driver.findElement(By.id("SelfManagementStep_nextBtn")).click();
		sleep(4000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS105428_CRM_Movil_Repro_Autogestion_USSD_No_Interactua_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105428";
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		ges.BuscarCuenta("DNI", cDNI);
		sleep(7000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		tca.listadoDeSeleccion("USSD", "*150#", "No Interact\u00faa");	
		sleep(4000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS105431_CRM_Movil_Repro_Autogestion_WAP_Sitio_Caido_No_carga_informacion_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105431";
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		ges.BuscarCuenta("DNI", cDNI);
		sleep(7000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		tca.listadoDeSeleccion("WAP", "email", "sitio ca\u00eddo/ No carga informaci\u00f3n");
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS105449_CRM_Movil_Repro_Autogestion_0800_Informa_Sistema_Fuera_de_Servicio_No_Resuelto(String sDNI, String sLinea) {
		imagen = "TS105449";
		detalles = imagen + "- Autogestion - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(7000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SelfManagementFields")));
		driver.findElement(By.cssSelector("[id=ChannelSelection]")).click();
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-list--vertical vlc-slds-list--vertical'] li")), "equals", "800");
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-list--vertical vlc-slds-list--vertical'] li")), "contains", "0800-444-0531");
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-list--vertical vlc-slds-list--vertical'] li")), "contains", "informa sistema fuera de servicio");
		driver.findElement(By.id("SelfManagementStep_nextBtn")).click();
		sleep(4000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Informada", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS105453_CRM_Movil_Repro_Autogestion_0800_La_Linea_esta_muda_No_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105453";
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		ges.BuscarCuenta("DNI", cDNI);
		sleep(7000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.id("SelfManagementFields")));
		driver.findElement(By.cssSelector("[id=ChannelSelection]")).click();		
		tca.listadoDeSeleccion("800", "0800-444-4100", "la l\u00ednea esta muda");
		sleep(4000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Informada", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS105466_CRM_CRM_Movil_Repro_Autogestion_WEB_Incon_con_Compra_de_packs_No_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105466";
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(7000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		tca.listadoDeSeleccion("WEB", "Packs", "Incon.con Compra de packs");
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Informada", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS105831_Nros_Emergencia_Informa_Sistema_Fuera_de_Servicio_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105831";
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		ges.BuscarCuenta("DNI", cDNI);
		sleep(7000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		tca.listadoDeSeleccion("Nros. Emergencia", "Otro", "Informa Sistema Fuera de Servicio");
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS111871_CRM_Movil_REPRO_Diagnostico_SVA_Configuracion_Disponible_Presencial_SMS_Saliente_SMS_a_fijo_Geo_No_Ok_Desregistrar_OfCom(String sDNI, String sLinea) {
		imagen = "TS111871";
		detalles = imagen + " -ServicioTecnico - DNI: "+sDNI+" - Linea: "+sLinea;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-info")));
		cc.irAMisServicios();
		tcd.verDetalles();
		tcd.clickDiagnosticarServicio("sms", "SMS Saliente", true);
		tcd.selectionInconvenient("SMS a fijo");
		tcd.continuar();
		tcd.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    tc.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    sleep(5000);
	    tcd.categoriaRed("Desregistrar");
	    cc.obligarclick(driver.findElement(By.id("NetworkCategory_nextBtn")));
	    sleep(5000);
	    driver.findElement(By.id("DeregisterSpeech_nextBtn")).click();
	    sleep(8000);
	    tc.seleccionarPreguntaFinal("S\u00ed");
	    driver.findElement(By.id("Deregister_nextBtn")).click();
	    sleep(7000);
	    tc.seleccionarPreguntaFinal("S\u00ed, funciona");
	    sleep(7000);
	    driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-icon.slds-icon--large.nds-icon.nds-icon_large.ta-Icon-wrapper-content")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119178_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Sin_Locacion_Evento_Masivo(String sDNI, String sLinea) {
		imagen = "TS119178";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		tc.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tcd.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		tc.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		sleep(15000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		tc.seleccionarPreguntaFinal("No, sigue con inconvenientes");
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
//		sleep(8000);
//		tcd.categoriaRed("Encontr\u00e9 un problema (Rojo)");
//		sleep(8000);
//		driver.findElement(By.id("MassiveIncidentLookUp")).click();
//		driver.findElement(By.id("MassiveIncidentLookUp")).sendKeys("Evento Masivo");
//		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		sleep(8000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta Masiva", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119183_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Sin_Locacion_Servicio_con_suspencion(String sDNI, String sLinea) {
		imagen = "TS119183";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		tc.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tcd.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		tc.seleccionarPreguntaFinal("No");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		sleep(15000);
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		tc.seleccionarPreguntaFinal("S\u00ed, funciona correctamente");
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
		sleep(5000);
		tcd.categoriaRed("No son las antenas (Verde)");
		sleep(5000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Realizada exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119186_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Sin_Locacion_NO_recupera_locacion_Geo_rojo(String sDNI, String sLinea) {
		imagen = "TS119186";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");	
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-form-element__label ng-binding ng-scope']")), "contains", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click(); 
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tcd.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-form-element__label ng-binding ng-scope']")), "contains", "no");
		driver.findElement(By.id("DeregisterSpeech_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-form-element__label ng-binding ng-scope']")), "contains", "no");
		driver.findElement(By.id("HlrDeregister_nextBtn")).click();
//		sleep(8000);
//		tcd.categoriaRed("Encontr\u00e9 un problema (Rojo)");
//		sleep(8000);
//		WebElement evento = driver.findElement(By.id("MassiveIncidentLookUp"));
//		evento.click();
//		evento.sendKeys("Evento Masivo");
//		sleep(8000);
//		driver.findElement(By.id("AddressSection_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119201_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_recibir_llamadas_Sin_Locacion_Envia_configuraciones(String sDNI, String sLinea) {
		imagen = "TS119201";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tcd.categoriaRed("Desregistrar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")).getLocation().y+" )");
		tc.seleccionarPreguntaFinal("S\u00ed, funciona correctamente");
		sleep(8000);
		buscarYClick(driver.findElements(By.id("HlrDeregister_nextBtn")), "equals", "continuar");
//		sleep(8000);
//		tcd.categoriaRed("Fuera del Area de Cobertura");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119210_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_ni_recibir_llamadas(String sDNI, String sLinea) {
		imagen = "TS119210";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tcd.categoriaRed("No son las antenas (Verde)");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119221_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_ni_recibir_llamadas_Sin_Locacion_Evento_Masivo(String sDNI, String sLinea) {
		imagen = "TS119221";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select(driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BalanceValidation_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(30000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("AddressSection_nextBtn")));
		tcd.categoriaRed("Encontr\u00e9 un problema (Rojo)");
		driver.findElement(By.id("MassiveIncidentLookUp")).click();
		driver.findElement(By.id("MassiveIncidentLookUp")).sendKeys("Evento Masivo");
		driver.findElement(By.id("AddressSection_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119262_CRM_Movil_REPRO_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Llamar_desde_otro_pais_Sin_Locacion_NO_recupera_locacion_Geo_Fuera_de_area_de_cobertura_OfCom(String sDNI, String sLinea) {
		imagen = "TS119262";
		detalles = imagen + " -Diagnostico: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo llamar desde otro pa\u00eds");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
		tcd.categoriaRed("Desregistrar");
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		sleep(5000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		buscarYClick(driver.findElements(By.id("DeregisterSpeech_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119266_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_sin_rellamado_NO_BAM(String sDNI, String sLinea) {
		imagen = "TS119266";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(15000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
//		tcd.categoriaRed("No son las antenas (Verde)");
//		sleep(10000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("SignalValidation_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
//		driver.findElement(By.id("SignalValidation_nextBtn")).click();
		sleep(7000);		
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119267_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_Sin_conciliar_ni_desregistrar_Envio_de_configuracion(String sDNI, String sLinea) {
		imagen = "TS119267";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("MobileConfigurationSending_nextBtn")));
		cc.obligarclick(driver.findElements(By.className("borderOverlay")).get(2));
		driver.findElement(By.id("MobileConfigurationSending_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119275_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_ANTENA_ROJO_Evento_Masivo_NO_BAM(String sDNI, String sLinea) {
		imagen = "TS119275";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
//		tcd.categoriaRed("Encontr\u00e9 un problema (Rojo)");
//		driver.findElement(By.id("MassiveIncidentLookUp")).sendKeys("Evento Masivo");
//		driver.findElement(By.id("AddressSection_nextBtn")).click();
//		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	//@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119279_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_CONCILIAR_NO_BAM(String sDNI, String sLinea) {
		imagen = "TS119279";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		cc.obligarclick(driver.findElements(By.className("borderOverlay")).get(0));
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		Assert.assertTrue(false);  // Genera error al llegar a este punto.
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "Diagnostico")
	public void TS119286_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_Navega_con_lentitud_Fuera_del_area_de_cobertura_NO_BAM(String sDNI, String sLinea) {
		imagen = "TS119286";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "Navega lento");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
//		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
//		tcd.categoriaRed("Fuera del Area de Cobertura");
//		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Realizada exitosa", "Consulta"));
	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico")
	public void TS105845_CRM_Movil_REPRO_Autogestion_APP_Abre_aplicacion_y_cierra_automaticamente_No_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105845";
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		tca.listadoDeSeleccion("APP", "C", "Abre aplicaci\u00f3n y cierra autom\u00e1ticamente");
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Informada", "Consulta"));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico")
	public void TS111300_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Saliente_SMS_a_fijo_Geo_No_Ok_Desregistrar(String sDNI, String sLinea) {
		imagen = "TS111300";
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		boolean desregistrar = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAMisServicios();
		tcd.verDetalles();
		tcd.clickDiagnosticarServicio("sms", "SMS Saliente", true);
		tcd.selectionInconvenient("SMS a fijo");
		tcd.continuar();
		tcd.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    tcd.categoriaRed("Desregistrar");
	    sleep(5000);
	    if (driver.findElement(By.cssSelector("[class='slds-form-element vlc-flex vlc-slds-radio-control ng-scope ng-valid ng-valid-required ng-dirty ng-valid-parse'] [class='slds-radio ng-scope itemSelected']")).getText().equalsIgnoreCase("Desregistrar"))
	    	desregistrar = true;
	    try {
	    	driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
	    	sleep(2000);
	    	driver.findElement(By.id("alert-ok-button")).click();
	    } catch(Exception e) {}
	    Assert.assertTrue(desregistrar);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico")
	public void TS112441_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Entrante_No_Recibe_De_Un_Numero_En_Particular_Geo_Ok_Rojo(String sDNI, String sLinea) {
		imagen = "TS112441";
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		boolean saldoInsuficiente = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAMisServicios();
		tcd.verDetalles();
		tcd.clickDiagnosticarServicio("sms", "SMS Entrante", true);
		tcd.selectionInconvenient("No recibe de un n\u00famero particular");
		tcd.continuar();
		tcd.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    sleep(5000);
	    for (WebElement x : driver.findElements(By.cssSelector(".slds-page-header__title.vlc-slds-page-header__title.slds-truncate.ng-binding"))) {
	    	if (x.getText().toLowerCase().contains("saldo insuficiente"))
	    		saldoInsuficiente = true;
	    }
	    try {
	    	driver.findElement(By.cssSelector(".vlc-slds-button--tertiary.ng-binding.ng-scope")).click();
	    	sleep(2000);
	    	driver.findElement(By.id("alert-ok-button")).click();
	    } catch(Exception e) {}
	    Assert.assertTrue(saldoInsuficiente);
	}
	
	//@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico")
	public void TS119171_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_llamadas_Conciliacion_Exitosa(String sDNI, String sLinea) {
		imagen = "TS119171";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo realizar llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BalanceValidation_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		cc.obligarclick(driver.findElements(By.className("borderOverlay")).get(0));
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		Assert.assertTrue(false);
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	//@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico") 
	public void TS119198_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_recibir_llamadas_Conciliacion_Exitosa(String sDNI, String sLinea) {
		imagen = "TS119198";
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);		
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		tcd.categoriaRed("Conciliar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(7000);
		Assert.assertTrue(false);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico") 
	public void TS119231_CRM_Movil_PRE_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_realizar_ni_recibir_llamadas_Sin_Locacion_Equipo_sin_senal(String sDNI, String sLinea) {
		imagen = "TS119231";
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);		
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo realizar ni recibir llamadas");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(3000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("BalanceValidation_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tcd.categoriaRed("No son las antenas (Verde)");
		sleep(15000);
		buscarYClick(driver.findElements(By.id("AddressSection_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	//@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico") 
	public void TS119245_CRM_Movil_REPRO_Diagnostico_de_Voz_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Llamar_desde_otro_pais_Conciliacion_Exitosa_Telefonico(String sDNI, String sLinea) {
		imagen = "TS119245";
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);		
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo navegar");
		sleep(5000);
		driver.findElement(By.id("MotiveIncidentSelect_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DataQuotaQuery_nextBtn")));
		tc.seleccionarPreguntaFinal("S\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tcd.categoriaRed("Conciliar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		Assert.assertTrue(false);
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Realizada exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico")
	public void TS119269_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_con_rellamado_NO_BAM(String sDNI, String sLinea) {
		imagen = "TS119269";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;
		sleep(5000);
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select(driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("NetworkCategory_nextBtn")));
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
//		sleep(8000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("AddressSection_nextBtn")));
//		tcd.categoriaRed("No son las antenas (Verde)");
//		sleep(8000);
//		driver.switchTo().frame(cambioFrame(driver, By.id("SignalValidation_nextBtn")));
//		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
//		driver.findElement(By.id("SignalValidation_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico")
	public void TS119271_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_SIN_SEnAL_NO_BAM(String sDNI, String sLinea) {
		imagen = "TS119271";
		detalles = imagen + " -Diagnostico - DNI: " + sDNI;		
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("DataQuotaQuery_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("BlackListValidationOk_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-radio--faux.ng-scope")), "equals", "si");
		driver.findElement(By.id("BlackListValidationOk_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.id("RecentConfiguration_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no");
		driver.findElement(By.id("RecentConfiguration_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tcd.categoriaRed("Fuera del Area de Cobertura");
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico")
	public void TS119272_CRM_Movil_PRE_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_SIN_CUOTA_NO_BAM(String sDNI, String sLinea) {
		imagen = "TS119272";
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		driver.findElement(By.name("loopname")).click();
		selectByText(driver.findElement(By.id("Motive")), "No puedo navegar");
		buscarYClick(driver.findElements(By.id("MotiveIncidentSelect_nextBtn")), "equals", "continuar");
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "no");
		buscarYClick(driver.findElements(By.id("DataQuotaQuery_nextBtn")), "equals", "continuar");
		driver.switchTo().frame(cambioFrame(driver, By.id("UnavailableQuotaMessage")));
		WebElement MediosDispon = driver.findElement(By.cssSelector("[class='slds-form-element__control'] p p span "));
		Assert.assertTrue(MediosDispon.getText().equalsIgnoreCase("Prob\u00e1 realizar una recarga o comprar un pack de datos"));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Realizada exitosa", "Consulta"));
	}
	
	//@Test (groups = "PerfilTelefonico", dataProvider = "Diagnostico") 
	public void TS119281_CRM_Movil_REPRO_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_Navegar_CONCILIACION_EXITOSA_NO_BAM_Telefonico(String sDNI, String sLinea) {
		imagen = "TS119281";
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");
		sleep(10000);
		driver.switchTo().frame(cambioFrame(driver, By.id("Motive")));
		Select motiv = new Select (driver.findElement(By.id("Motive")));
		motiv.selectByVisibleText("No puedo navegar");
		driver.findElement(By.id("MotiveIncidentSelect_nextBtn")).click();
		sleep(8000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "s\u00ed");
		driver.findElement(By.id("DataQuotaQuery_nextBtn")).click();
		sleep(8000);
		driver.switchTo().frame(cambioFrame(driver, By.className("borderOverlay")));
		tcd.categoriaRed("Conciliar");
		driver.findElement(By.id("NetworkCategory_nextBtn")).click();
		Assert.assertTrue(false);
		sleep(7000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Realizada exitosa", "Consulta"));
	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test (groups = "PerfilAgente", dataProvider = "Diagnostico")
	public void TS119283_CRM_Movil_REPRO_Diagnostico_de_Datos_Valida_Red_y_Navegacion_Motivo_de_contacto_No_puedo_navegar_Antena_rojo_NO_BAM_Agente(String sDNI, String sLinea) {
		imagen = "TS119283";
		detalles = imagen + " -ServicioTecnico - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Diagn\u00f3stico");  // Campo Diagnostico no aparece en perfil agente
		Assert.assertTrue(false);
//		tcd.clickDiagnosticarServicio("datos", "Datos", true);
//	    tcd.selectionInconvenient("No puedo navegar");
//	    tcd.continuar();
//	    tcd.seleccionarRespuesta("si");
//	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	}
	
	//----------------------------------------------- ADMIN FUNCIONAL -------------------------------------------------------\\
	
	@Test (groups = "PerfilAdminFuncional", dataProvider = "Diagnostico")
	public void TS105437_CRM_Movil_Repro_Autogestion_WEB_Inconveniente_con_Informe_de_pago_Resuelto(String sDNI, String sLinea) throws InterruptedException {
		imagen = "TS105437";
		detalles = imagen + "- Autogestion - DNI: "+sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(7000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		tca.listadoDeSeleccion("WEB", "Packs", "Incon.con Compra de packs");
		sleep(4000);
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilAdminFuncional", dataProvider = "Diagnostico")
	public void TS105441_CRM_Movil_Repro_Autogestion_WEB_Informacion_Incompleta_Resuelto(String cDNI, String cLinea) throws InterruptedException {
		imagen = "TS105441";
		detalles = imagen + "- Autogestion - DNI: "+cDNI;
		ges.BuscarCuenta("DNI", cDNI);
		sleep(7000);
		cc.irAGestion("diagn\u00f3stico de autogesti\u00f3n");
		sleep(5000);
		tca.listadoDeSeleccion("WEB", "Otro", "Informaci\u00f3n Incompleta");
		sleep(4000);
		tca.verificarNumDeGestion();
		Assert.assertTrue(tca.cerrarCaso("Resuelta exitosa", "Consulta"));
	}
	
	@Test (groups = "PerfilAdminFuncional", dataProvider = "Diagnostico")
	public void TS111042_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Saliente_SMS_a_fijo_Geo_No_Ok_Conciliacion_No_habia_nada_que_conciliar(String sDNI, String sLinea) {
		imagen = "TS111042";
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAMisServicios();
		tcd.verDetalles();
		tcd.clickDiagnosticarServicio("sms", "SMS Saliente", true);
		tcd.selectionInconvenient("SMS a fijo");
		tcd.continuar();
		tcd.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    tc.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    tcd.categoriaRed("Conciliar");
	    buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
	    //pendiente por terminar// error "createFDOAutoReqDate"// preguntar cual es el final del flujo
	}
	
	@Test (groups = "PerfilAdminFuncional", dataProvider = "Diagnostico")
	public void TS111043_CRM_Movil_REPRO_Diagnostico_SVA_Telefonico_SMS_Saliente_SMS_Emision_a_algun_destino_en_particular_Geo_No_Ok_Conciliacion_No_habia_nada_que_conciliar(String sDNI, String sLinea) {
		imagen = "TS111043";
		detalles = imagen + " -Diagnostico Inconveniente - DNI: " + sDNI;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAMisServicios();
		tcd.verDetalles();
		tcd.clickDiagnosticarServicio("sms", "SMS Saliente", true);
		tcd.selectionInconvenient("SMS Emisi\u00f3n a alg\u00fan destino en particular");
		tcd.continuar();
		tcd.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    tc.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    tcd.categoriaRed("Conciliar");
	    buscarYClick(driver.findElements(By.id("NetworkCategory_nextBtn")), "equals", "continuar");
	    //pendiente por terminar// error "createFDOAutoReqDate"// preguntar cual es el final del flujo
	}
	
	@Test (groups = "PerfilAdminFuncional", dataProvider = "Diagnostico")
	public void TS111487_CRM_Movil_REPRO_Diagnostico_SVA_Con_Modificacion_en_el_Equipo_Telefonico_SMS_Entrante_No_Recibe_De_Un_Numero_En_Particular_Geo_No_Ok_Desregistrar(String sDNI, String sLinea) {
		imagen = "TS111487";
		detalles = imagen + " -ServicioTecnico - DNI: "+sDNI+" - Linea: "+sLinea;
		boolean desregistrar = false;
		ges.BuscarCuenta("DNI", sDNI);
		sleep(15000);
		driver.switchTo().frame(cambioFrame(driver, By.className("card-top")));
		cc.irAMisServicios();
		tcd.verDetalles();
		tcd.clickDiagnosticarServicio("sms", "SMS Entrante", true);
		tcd.selectionInconvenient("No recibe de un n\u00famero particular");
		tcd.continuar();
		tcd.seleccionarRespuesta("no");
	    buscarYClick(driver.findElements(By.id("KnowledgeBaseResults_nextBtn")), "equals", "continuar");
	    tc.seleccionarPreguntaFinal("S\u00ed");
	    buscarYClick(driver.findElements(By.id("BalanceValidation_nextBtn")), "equals", "continuar");
	    tcd.categoriaRed("Desregistrar");
	    driver.findElement(By.id("NetworkCategory_nextBtn")).click();
	    sleep(4000);
	    driver.findElement(By.id("DeregisterSpeech_nextBtn")).click();    
	    sleep(6000);
	    driver.findElement(By.id("HLR_IFS_S132_Button")).isDisplayed();
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");
	    sleep(6000);
	    driver.findElement(By.id("Deregister_nextBtn")).click();
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "no, sigue con inconvenientes");
	    sleep(3000);
	    buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "equals", "s\u00ed");	    
	    sleep(8000);
	    driver.switchTo().frame(cambioFrame(driver, By.id("MobileConfigurationSending_nextBtn")));
		List <WebElement> opcion = driver.findElements(By.cssSelector(".imgItemContainer.ng-scope"));
		for(WebElement x : opcion ) {
			if(x.getText().toLowerCase().equals("si, enviar configuraci\u00f3n"))
				x.click();
		}
		driver.findElement(By.id("MobileConfigurationSending_nextBtn")).click();
		sleep(8000);
	    for(WebElement x : driver.findElements(By.className("slds-form-element__control"))) {
	    	if(x.getText().toLowerCase().contains("\u00a1tu configuraci\u00f3n se envi\u00f3 con \u00e9xito\u0021"))
	    		desregistrar = true;	    	
	    }
	    Assert.assertTrue(desregistrar);
	}
}