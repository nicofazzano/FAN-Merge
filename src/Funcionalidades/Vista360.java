package Funcionalidades;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.CustomerCare;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.TestBase;

public class Vista360 extends TestBase {

	private WebDriver driver;
	private SalesBase sb;
	private CustomerCare cc;
	private PagePerfilTelefonico ppt;
	private Marketing mk;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	private GestionDeClientes_Fw ges;
	private LoginFw log ;
	String detalles;
	
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		mk = new Marketing(driver);
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
	
	@BeforeClass (groups = "PerfilAgente")
	public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sb = new SalesBase(driver);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		log.loginAgente();
		ges.irAConsolaFAN();	
	
	}
	
	@BeforeMethod (alwaysRun = true)
	public void setup() throws Exception {
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
	public void quit() throws IOException {
		driver.quit();
		sleep(5000);
	}
	
	
	//----------------------------------------------- OOCC -------------------------------------------------------\\
	
	@Test
	public void TS145701_CRM_Movil_Mix_Card_de_Facturacion_Datos_informativos_Crm_OC() {
		imagen = "TS145701";
		ges.BuscarCuenta("DNI", "37478859");
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='console-card active expired']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active expired']")));
		Assert.assertTrue(!driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-top'] div h2")).getText().isEmpty());
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='slds-text-body_regular account-number']")).getText().contains("L\u00edmite de Cr\u00e9dito"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='header-right']")).getText().contains("Saldo"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Cuenta"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Modalidad de Recepci\u00f3n de Factura"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("\u00daltimo Vencimiento"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Adherido a D\u00e9bito"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Ciclo de Facturaci\u00f3n"));
	}
	
	@Test
	public void TS148788_CRM_Movil_Mix_Vista_360_Distribucion_de_paneles_Perfil_Crm_OC() {
		imagen = "TS148788";
		ges.BuscarCuenta("DNI", "37478859");
		cambioDeFrame(driver, By.cssSelector("[class='profile-box']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='left-sidebar-section-header'] [class='icon-v-right-caret']")));		
		Assert.assertTrue(false);  //No estan los segmentos ni los botones de "Anadir mas"
	}
	
	@Test
	public void TS148726_CRM_Movil_Mix_Vista_360_Consulta_por_gestiones_Gestiones_Cerradas_Informacion_brindada_Crm_OC() {
		imagen = "TS148726";
		ges.BuscarCuenta("DNI", "37478859");
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		driver.findElement(By.xpath("//*[@class='console-card active']//*[@class='card-info-hybrid']//*[@class='actions']//span[text()='Gestiones']")).click();
		cambioDeFrame(driver, By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")));		
		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small  slds-m-around--medium'] tbody tr"), 0));
		List<WebElement> datosTabla = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small  slds-m-around--medium'] tbody tr"));
		Assert.assertTrue(datosTabla.size() >= 1);
	}
	
	
	
//	@Test (groups = "PerfilOficina", dataProvider = "documentacionVista360")//ok
//	public void TS134379_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_FAN_Front_OOCC(String sDNI) throws AWTException {
//		imagen = "TS134379";
//		detalles = imagen + "-Vista 360 - DNI: "+sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		try { ges.cerrarPanelDerecho(); } catch (Exception e) {}
//		ges.irAGestionEnCard("Mis servicios");
//		
//		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"), 0);
//		WebElement tabla = null;
//		for(WebElement x : driver.findElements(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"))){
//				if(x.getText().toLowerCase().contains("servicios incluidos")) {
//					tabla = x;
//					break;
//				}
//		}
//		List<WebElement> elementosDeLaTabla = tabla.findElement(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium'] [class='slds-p-bottom--small'] ")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
//		//ArrayList<String> tablaComparar = new ArrayList<String>(Arrays.asList("Barrings Configurables por el Usuario","Caller Id","Contestador Personal", "DDI con Roaming Internacional","Llamada en espera","Transferencia de Llamadas","Datos","MMS", "SMS Saliente", "SMS Entrante", "Voz"));
//		ArrayList<String> tablaComparar = new ArrayList<String>(Arrays.asList("Contestador Personal", "Datos", "DDI con Roaming Internacional", "MMS", "Plan con Tarjeta Repro", "SMS Entrante", "SMS Saliente" ,"Voz"));
//		for (int i = 0; i < tablaComparar.size(); i++) {
//			String nombre = elementosDeLaTabla.get(i).findElements(By.tagName("td")).get(0).getText();
//			String nombreComparar = tablaComparar.get(i);
//			String estado = elementosDeLaTabla.get(i).findElements(By.tagName("td")).get(2).getText();
//			Assert.assertTrue(nombre.equalsIgnoreCase(nombreComparar));
//			Assert.assertTrue(estado.equalsIgnoreCase("Activo"));
//		}
//	}
//	
//	@Test (groups = "PerfilOficina", dataProvider = "CuentaVista360")
//	public void TS134349_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_abiertas_Plazo_vencido_Asistencia_registrada_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
//		imagen = "TS134349";
//		detalles = imagen + " - Vista 360 - DNI: "+sDNI+" - Nombre: "+sNombre;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont"), 0);
//		driver.findElement(By.id("text-input-03")).click();
//		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
//		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")).click();
//		sleep(2000);
//		ges.getWait().until(ExpectedConditions.visibilityOf(driver.findElements(By.cssSelector("[class='slds-m-around--medium'] tbody tr a")).get(0)));
//		
//		WebElement nroCaso = driver.findElements(By.cssSelector("[class='slds-m-around--medium'] tbody tr a")).get(0);
//		nroCaso.click(); 
//		WebElement fechaYHora = null;
//		cambioDeFrame(driver, By.name("close"),0);
//		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
//			if (x.getText().toLowerCase().contains("owned by me"))
//				fechaYHora = x;
//		}
//		fechaYHora = fechaYHora.findElement(By.tagName("tbody"));
//		for (WebElement x : fechaYHora.findElements(By.tagName("tr"))) {
//			if (x.getText().toLowerCase().contains("fecha/hora de cierre"))
//				fechaYHora = x;
//		}
//		Assert.assertTrue(fechaYHora.getText().contains("Fecha/Hora de cierre"));
//		Assert.assertTrue(fechaYHora.findElements(By.tagName("td")).get(3).getText().matches("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$"));
//	}
//	
//	@Test (groups = "PerfilOficina", dataProvider = "CuentaVista360") 
//	public void TS134368_OFCOM_CRM_Movil_Prepago_Vista_360_Detalle_de_consumo_Consulta_visualizacion_y_busqueda_de_los_distintos_consumos_realizados_por_el_cliente_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
//		imagen = "TS134368";
//		detalles = imagen + "-Vista 360 - DNI: "+sDNI+ " - Nombre: "+sNombre;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Detalles de Consumo"); 
//		cambioDeFrame(driver, By.id("text-input-02"), 0);
//		driver.findElement(By.id("text-input-02")).click();
//		List<WebElement> pres = driver.findElement(By.id("option-list-01")).findElements(By.tagName("li"));
//		for(WebElement p : pres){
//			if(p.getText().toLowerCase().equals("los \u00faltimos 15 d\u00edas"))
//			cc.obligarclick(p);
//		}
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand")).click();
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-text-heading--small"),0));
//		clickBy(driver, By.xpath("//label[contains (text(), 'Filtros avanzados')]"), 0);
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-grid slds-wrap slds-card slds-p-around--medium']")));
//		WebElement desplegable = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap slds-card slds-p-around--medium'] [class='slds-p-horizontal--small slds-size--1-of-1 slds-medium-size--4-of-8 slds-large-size--2-of-8'] [class = 'slds-form-element']")).get(2);
//		desplegable.click();
//		driver.findElement(By.xpath("//div[@class= 'slds-dropdown slds-dropdown--left']//li//*[contains(text(),'Pack 1 GB x 1 dia + whatsapp gratis')]")).click();
//		Select pagina = new Select (driver.findElement(By.cssSelector(".slds-select.ng-pristine.ng-untouched.ng-valid.ng-not-empty")));
//		pagina.selectByVisibleText("30");
//		sleep(7000);
//		boolean b = false;
//		WebElement lista = driver.findElement(By.cssSelector("[class='slds-table slds-table_striped slds-table--bordered slds-table--resizable-cols slds-table--fixed-layout via-slds-table-pinned-header'] tbody"));
//		List <WebElement> consumos = lista.findElements(By.tagName("tr"));
//		System.out.println(lista.getText());
//		for(WebElement x : consumos) {
//			if(x.getText().contains("Pack 1 GB")) {
//				b = true;
//				break;
//			}
//		}
//		Assert.assertTrue(b);
//	}
//	
//	@Test (groups = "PerfilOficina", dataProvider = "CuentaVista360")
//	public void TS134370_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_no_registradas_FAN_Front_OOCC(String sDNI , String sLinea, String sNombre) {
//		//Para seleccionar las fechas= //div[@class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']//table//tbody//tr//td//span
//		imagen = "TS134370";
//		detalles = imagen+"- Vista 360 - DNI: "+sDNI;
//		String dia = fechaDeHoy().substring(3,5);
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"), 0);
//		driver.findElement(By.id("text-input-id-1")).click();
//		WebElement table = driver.findElement(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']"));
//		sleep(3000);
//		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows) {
//			try {
//				if (cell.getText().equals(dia)) {
//					cell.click();
//				}
//			} catch (Exception e) {}
//		}
//		driver.findElement(By.id("text-input-id-2")).click();
//		WebElement table_2 = driver.findElement(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']"));
//		sleep(3000);
//		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows_2) {
//			try {
//				if (cell.getText().equals(dia)) {
//					cell.click();
//				}
//			} catch (Exception e) {}
//		}
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
//		cambioDeFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"), 0);
//		sleep(3000);
//		boolean a = false;
//		for(WebElement x : driver.findElements(By.cssSelector(".ng-pristine.ng-untouched.ng-valid.ng-empty"))) {
//			if(x.getText().toLowerCase().equals("order")) {
//				a = true;
//				break;
//			}		
//		}
//		Assert.assertFalse(a);
//	}
//	
//	@Test (groups = "PerfilOficina", dataProvider = "CuentaVista360")
//	public void TS134371_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_abiertas_Plazo_No_vencido_Consulta_registrada_FAN_Front_OOCC(String sDNI, String sLinea, String sNombre){
//		imagen = "TS134371";
//		detalles = imagen+"- Vista 360 - DNI: "+sDNI;
//		String dia = fechaDeHoy().substring(3,5);
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"), 0);
//		driver.findElement(By.id("text-input-id-1")).click();
//		WebElement table = driver.findElement(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']"));
//		sleep(3000);
//		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows) {
//			try {
//				if (cell.getText().equals(dia)) {
//					cell.click();
//					break;
//				}
//			} catch (Exception e) {}
//		}
//		driver.findElement(By.id("text-input-id-2")).click();
//		WebElement fecha = driver.findElement(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']"));
//		sleep(3000);
//		List<WebElement> tableRows2 = fecha.findElements(By.xpath("//tr//td"));
//		for (WebElement x: tableRows2) {
//			try {
//				if (x.getText().equals(dia)) {
//					x.click();
//					break;
//				}
//			} catch (Exception e) {}
//		}
//		cambioDeFrame(driver, By.id("text-input-03"), 0);
//		driver.findElement(By.id("text-input-03")).click();
//		driver.findElement(By.xpath("//*[text() = 'Ordenes']")).click();
//		esperarElemento(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont"), 0);
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
//		esperarElemento(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"), 0);
//		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
//		String estado = nroCaso.findElements(By.tagName("td")).get(4).getText();
//		Assert.assertTrue(estado.toLowerCase().contains("activada") || (estado.toLowerCase().contains("iniciada")));
//	}
//	
//	@Test (groups = "PerfilOficina", dataProvider = "CuentaVista360")
//	public void TS134380_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_FAN_Front_OOCC(String sDNI,String sLinea, String sNombre){
//		imagen = "TS134380";
//		detalles = imagen + " - Vista 360 - DNI: "+sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Mis Servicios");
//		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"), 0);
//		WebElement productosActivos = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
//		List<WebElement> productos = productosActivos.findElements(By.tagName("tr"));
//		for (WebElement producto : productos) {
//			Assert.assertTrue(producto.getText().matches("(.*\\n*)*(.*)[\\$]{1}(\\d+)[,](\\d+)(.*\\n*)*"));
//		}
//	}
//	
//	@Test (groups = "PerfilOficina", dataProvider = "CuentaVista360")
//	public void TS134495_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
//		imagen = "TS134495";
//		detalles = imagen + "-Vista 360 - DNI: "+sDNI+ " - Nombre: "+sNombre;
//		ges.BuscarCuenta("DNI", sDNI);
//		try {
//			cc.openleftpanel();
//		} catch (Exception eE) {}
//		esperarElemento(driver, By.className("profile-box-details"), 0);
//		WebElement wProfileBoxDetails = driver.findElement(By.className("profile-box-details"));
//		Assert.assertTrue(wProfileBoxDetails.findElement(By.tagName("h1")).getText().toLowerCase().contains(sNombre.toLowerCase()));
//		String sWebDNI = wProfileBoxDetails.findElement(By.className("detail-card")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(1).getText();
//		Assert.assertTrue(sWebDNI.equals(sDNI));
//		Assert.assertTrue(sWebDNI.matches("[0-9]+"));
//		String npsText = wProfileBoxDetails.findElement(By.className("detail-card")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(0).getText();
//		Assert.assertTrue(npsText.equals("NPS:"));
//		List<WebElement> wWebList = wProfileBoxDetails.findElements(By.className("profile-edit"));
//		Assert.assertTrue(wWebList.get(0).getText().toLowerCase().equals("actualizar datos"));
//		Assert.assertTrue(wWebList.get(1).getText().toLowerCase().equals("reseteo clave"));
//		driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Datos Personales')] /.. /.. /.. /.. /..")).click();
//		String headerDatosPersonales = driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Datos Personales')]")).getText();
//		Assert.assertTrue(headerDatosPersonales.equals("Datos Personales"));
//		driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Segmentaci')] /.. /.. /.. /.. /..")).click();
//		String headerSegmentacion = driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Segmentaci')]")).getText();
//		Assert.assertTrue(headerSegmentacion.equalsIgnoreCase("Segmentaci\u00f3n"));
//		List<WebElement> panelesIzquierdos = driver.findElements(By.cssSelector(".left-sidebar-section-container"));
//		List<WebElement> datosPersonales = panelesIzquierdos.get(0).findElements(By.cssSelector(".client-data-label"));
//		List<WebElement> segmentacion = panelesIzquierdos.get(1).findElements(By.cssSelector(".client-data-label"));
//		ArrayList<String> datosPersonalesComparar = new ArrayList<String>(Arrays.asList("Correo Electr\u00f3nico:","M\u00f3vil:", "Tel\u00e9fono alternativo:", "Club Personal:", "Categor\u00eda:"));
//		ArrayList<String> segmentacionComparar = new ArrayList<String>(Arrays.asList("Segmento:", "Atributos:"));
//		for (int i = 0; i < datosPersonales.size(); i++) {
//			Assert.assertTrue(datosPersonales.get(i).getText().equals(datosPersonalesComparar.get(i)));
//		}
//		for (int i = 0; i < segmentacion.size(); i++) {
//			WebElement campo = segmentacion.get(i);
//			String textoDelCampo = campo.getText();
//			Assert.assertTrue(textoDelCampo.equals(segmentacionComparar.get(i)));
//			if (textoDelCampo.equals("Segmento:")) {
//				String valorDelCampo = driver.findElement(By.xpath("/html/body/div/div[1]/ng-include/div[4]/ng-include/div/div[2]/table[1]/tbody/tr/td/div[2]")).getText();
//				Assert.assertTrue(valorDelCampo.equalsIgnoreCase("altas recientes - masivo"));
//			}
//		}
//	}
//	
//	@Test (groups = "PerfilOficina", dataProvider = "CuentaVista360")
//	public void TS134496_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Perfil_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
//		imagen = "TS134496";
//		detalles = imagen + " - Vista 360 - DNI: "+sDNI+" - Nombre: "+sNombre;
//		ges.BuscarCuenta("DNI", sDNI);
//		cambioDeFrame(driver, By.className("card-top"), 0);
//		WebElement linea = driver.findElements(By.xpath("//div[@class = 'card-top']//h2[@class = 'slds-text-heading_medium']")).get(1);
//		Assert.assertTrue(linea.getText().equals(sLinea));		
//	}
//	
//	@Test (groups = "PerfilOficina", dataProvider = "CuentaVista360")
//	public void TS134745_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Datos_FAN_Front_OOCC(String sDNI, String sLinea,String sNombre) {
//		imagen = "TS134745";
//		detalles = imagen + " - Vista 360 - DNI: "+sDNI+" - Nombre: "+sNombre;
//		boolean creditoRecarga = false, creditoPromocional = false, estado = false, internetDisponible = false;
//		boolean detalles = false, historiales = false, misServicios = false, gestiones = false;
//		ges.BuscarCuenta("DNI", sDNI);
//		cambioDeFrame(driver, By.className("card-top"), 0);
//		WebElement plan = driver.findElements(By.xpath("//div[@class = 'card-top']//h2[@class = 'slds-text-heading_medium']")).get(0);
//		WebElement FechaActivacion = driver.findElement(By.cssSelector("[class = 'slds-text-body_regular expired-title']"));
//		WebElement linea = driver.findElements(By.xpath("//div[@class = 'card-top']//h2[@class = 'slds-text-heading_medium']")).get(1);
//		Assert.assertTrue(plan.isDisplayed() && FechaActivacion.isDisplayed() && linea.getText().contains(sLinea));
//		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.detail-label"))) {
//			if (x.getText().toLowerCase().contains("cr\u00e9dito recarga"))
//				creditoRecarga = true;
//			if (x.getText().toLowerCase().contains("cr\u00e9dito promocional"))
//				creditoPromocional = true;
//			if (x.getText().toLowerCase().contains("estado"))
//				estado = true;
//			if (x.getText().toLowerCase().contains("internet disponible"))
//				internetDisponible = true;
//		}
//		Assert.assertTrue(creditoRecarga && creditoPromocional && estado && internetDisponible);
//		for(WebElement y : driver.findElements(By.className("slds-text-body_regular"))) {
//			if(y.getText().toLowerCase().contains("detalles de consumo"))
//				detalles = true;
//			if(y.getText().toLowerCase().contains("historiales"))
//				historiales = true;
//			if(y.getText().toLowerCase().contains("mis servicios"))
//				misServicios = true;
//			if(y.getText().toLowerCase().contains("gestiones"))
//				gestiones = true;
//		}
//		Assert.assertTrue(detalles && historiales && misServicios && gestiones);
//	}
//	
//	@Test (groups = "PerfilOficina", dataProvider = "CuentaVista360")
//	public void TS134746_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Desplegable_FAN_Front_OOCC(String sDNI,String sLinea, String sNombre) {
//		imagen = "TS134746";
//		detalles = imagen+" - Vista360 - DNI: "+sDNI+" - Linea: "+sLinea;
//		ges.BuscarCuenta("DNI",sDNI);
//		cambioDeFrame(driver, By.className("card-top"), 0);
//		driver.findElement(By.className("card-top")).click();
//		ArrayList<String> gestiones = new ArrayList<String>(Arrays.asList("Historial de Suspensiones", "Recarga de cr\u00e9dito", "Renovacion de Datos", "Alta/Baja de Servicios", "Suscripciones", "Inconvenientes con Recargas" , "Diagn\u00f3stico","N\u00fameros Gratis", "Cambio SimCard" ,"Cambio de Plan"));
//		esperarElemento(driver, By.cssSelector("[class='console-flyout active flyout'] [class='card-info'] [class = 'slds-grid community-flyout-content'] [class = 'community-flyout-actions-card'] ul"), 0);
//		WebElement wFlyoutCard = driver.findElement(By.cssSelector("[class='console-flyout active flyout'] [class='card-info'] [class = 'slds-grid community-flyout-content'] [class = 'community-flyout-actions-card'] ul"));
//		List<WebElement> wWebList = wFlyoutCard.findElements(By.tagName("li"));
//		for (WebElement gest : wWebList) {
//			String canal = gest.findElement(By.tagName("span")).getText();
//			Assert.assertTrue(gestiones.contains(canal));
//		}
//		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'Mensajes')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
//		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'MB')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
//		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'KB')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
//		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'Minutos')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
//		Assert.assertTrue(!wFlyoutCard.findElement(By.xpath("//div[@class='items-card ng-not-empty ng-valid'] //div[contains(text(),'Segundos')] /following-sibling::label")).getText().equalsIgnoreCase("Informaci�n no disponible"));
//		ArrayList<String> gestiones_2 = new ArrayList<String>(Arrays.asList("Comprar SMS", "Comprar Internet" , "Comprar Minutos"));
//		WebElement wRightSideCard = driver.findElement(By.xpath("//*[@class='items-card ng-not-empty ng-valid']"));
//		wWebList = wRightSideCard.findElements(By.xpath("//button[@class='slds-button slds-button--neutral slds-truncate']"));
//		for(WebElement gest_2 : wWebList) {
//			String canal_2 = gest_2.findElement(By.tagName("span")).getText();
//			Assert.assertTrue(gestiones_2.contains(canal_2));
//		}
//	}
	
	//----------------------------------------------- TELEFONICO -------------------------------------------------------\\
	
	@Test
	public void TS145700_CRM_Movil_Mix_Card_de_Facturacion_Datos_informativos_Crm_Telefonico() {
		imagen = "TS145700";
		ges.BuscarCuenta("DNI", "37478859");
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		mk.closeActiveTab();
		cc.irAFacturacion();
		cambioDeFrame(driver, By.cssSelector("[class='console-card active expired']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active expired']")));
		Assert.assertTrue(!driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-top'] div h2")).getText().isEmpty());
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='slds-text-body_regular account-number']")).getText().contains("L\u00edmite de Cr\u00e9dito"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='header-right']")).getText().contains("Saldo"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Cuenta"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Modalidad de Recepci\u00f3n de Factura"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("\u00daltimo Vencimiento"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Adherido a D\u00e9bito"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active expired'] [class='card-info'] [class='details']")).getText().contains("Ciclo de Facturaci\u00f3n"));
	}
	
	@Test
	public void TS148798_CRM_Movil_Mix_Vista_360_Card_de_servicios_Desplegable_Crm_Telefonico() {
		imagen = "TS148798";
		boolean histSusp = false, suscrip = false, renovDatos = false, detPlan = false, numGratis = false, diag = false, inconvRecarg = false, abmServ = false, internet = false, minutos = false, sms = false, credDisp = false, credProm = false;
		ges.BuscarCuenta("DNI", "37478859");
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-top']")).getText().contains("Conexi\u00f3n Control Abono"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-info-hybrid'] [class='details'] [class='imagre']")).getText().contains("Activo"));
		driver.findElement(By.cssSelector("[class='console-card active'] [id='flecha'] i")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='community-flyout-actions-card']")));
		for (WebElement x : driver.findElements(By.cssSelector("[class='community-flyout-actions-card'] ul li"))) {
			if (x.getText().equalsIgnoreCase("Historial de Suspensiones"))
				histSusp = true;
			if (x.getText().equalsIgnoreCase("Suscripciones"))
				suscrip = true;
			if (x.getText().equalsIgnoreCase("Renovacion de Datos"))
				renovDatos = true;
			if (x.getText().equalsIgnoreCase("Detalle del Plan"))
				detPlan = true;
			if (x.getText().equalsIgnoreCase("N\u00fameros Gratis"))
				numGratis = true;
			if (x.getText().equalsIgnoreCase("Diagn\u00f3stico"))
				diag = true;
			if (x.getText().equalsIgnoreCase("Inconvenientes con Recargas"))
				inconvRecarg = true;
			if (x.getText().equalsIgnoreCase("Alta/Baja de Servicios"))
				abmServ = true;
		}
		Assert.assertTrue(histSusp && suscrip && renovDatos && detPlan && numGratis && diag && inconvRecarg && abmServ);
		for (WebElement x : driver.findElements(By.cssSelector("[class='community-flyout-grid-items-card'] [class='slds-grid slds-gutters']"))) {
			if (x.getText().contains("Internet (Mb)"))
				internet = true;
			if (x.getText().contains("Minutos (minutos:segundos)"))
				minutos = true;
			if (x.getText().contains("SMS (cantidad)"))
				sms = true;
			if (x.getText().contains("Cr\u00e9dito Disponible"))
				credDisp = true;
			if (x.getText().contains("Cr\u00e9dito Promocional"))
				credProm = true;
		}
		Assert.assertTrue(internet && minutos && sms && credDisp && credProm);
	}
	
//	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")
//	public void TS134798_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Datos_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre){
//		imagen = "TS134798";
//		detalles = imagen + " -ServicioTecnico: " + sDNI;
//		boolean creditoRecarga = false, creditoPromocional = false, estado = false, internetDisponible = false;
//		boolean detalles = false, historiales = false, misServicios = false, gestiones = false;
//		ges.BuscarCuenta("DNI", sDNI);
//		cambioDeFrame(driver, By.className("card-top"),0);
//		WebElement plan = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(0);
//		WebElement FechaActivacion = driver.findElement(By.cssSelector(".slds-text-body_regular.expired-title"));
//		WebElement linea = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(2);
//		Assert.assertTrue(plan.isDisplayed() && FechaActivacion.isDisplayed() && linea.getText().contains(sLinea));
//		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.detail-label"))) {
//			if (x.getText().toLowerCase().contains("cr\u00e9dito recarga"))
//				creditoRecarga = true;
//			if (x.getText().toLowerCase().contains("cr\u00e9dito promocional"))
//				creditoPromocional = true;
//			if (x.getText().toLowerCase().contains("estado"))
//				estado = true;
//			if (x.getText().toLowerCase().contains("internet disponible"))
//				internetDisponible = true;
//		}
//		Assert.assertTrue(creditoRecarga && creditoPromocional && estado && internetDisponible);
//		for(WebElement y : driver.findElements(By.className("slds-text-body_regular"))) {
//			if(y.getText().toLowerCase().contains("detalles de consumo"))
//				detalles = true;
//			if(y.getText().toLowerCase().contains("historiales"))
//				historiales = true;
//			if(y.getText().toLowerCase().contains("mis servicios"))
//				misServicios = true;
//			if(y.getText().toLowerCase().contains("gestiones"))
//				gestiones = true;
//		}
//		Assert.assertTrue(detalles && historiales && misServicios && gestiones);
//	}
//	
//	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")
//	public void TS134800_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
//		imagen = "TS134800";
//		detalles = imagen + "-Vista 360-DNI:" + sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Mis servicios");
//		cambioDeFrame(driver,By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"),0);
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='via-slds slds-m-around--small ng-scope'] [class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium'] [class='title'] "),0));
//		List<WebElement> verif = driver.findElements(By.cssSelector("[class='via-slds slds-m-around--small ng-scope'] [class='slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium'] [class='title'] "));
//		Assert.assertTrue(ges.matchText(verif, "Servicios Incluidos"));
//		cambioDeFrame(driver,By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"),0);
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"),0));
//		WebElement tabla = driver.findElement(By.cssSelector("[class = 'slds-grid slds-wrap slds-card slds-m-bottom--small slds-p-around--medium']"));
//		List<WebElement> elementosDeLaTabla = tabla.findElements(By.cssSelector(" [class='slds-p-bottom--small'] tr td"));
//		ArrayList<String> tablaComparar = new ArrayList<String>(Arrays.asList("Barrings Configurables por el Usuario","Caller Id","Contestador Personal", "DDI con Roaming Internacional","Llamada en espera","Transferencia de Llamadas","Datos","MMS", "SMS Saliente", "SMS Entrante", "Voz"));
//		for (int x=0; x< elementosDeLaTabla.size();x=x+3) {
//			for(String elemento : tablaComparar) {
//				String estado = elementosDeLaTabla.get(x+2).getText();
//				if(elementosDeLaTabla.get(x).getText().equals(elemento)) {
//					Assert.assertTrue(elementosDeLaTabla.get(x).getText().equals(elemento));
//					Assert.assertTrue(estado.equals("Activo"));					
//				}
//			}
//		}		
//	}
//	
//	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")//ok
//	public void TS134801_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_FAN_Front_Telefonico(String sDNI, String sNombre, String sLinea){
//		imagen = "TS134801";
//		detalles = imagen + " -ServicioTecnico: " + sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Mis servicios");
//		cambioDeFrame(driver, By.cssSelector(".slds-card.slds-m-around--small.ta-fan-slds"),0);
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-card.slds-m-around--small.ta-fan-slds"),0));
//		List <WebElement> serv= driver.findElements(By.cssSelector(".slds-p-bottom--small"));
//		boolean a = false;
//		for(WebElement s : serv){
//			if(s.getText().toLowerCase().contains("mms"))
//				a = true;
//		}
//		Assert.assertTrue(a);
//	}
//	
//	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")//ok
//	public void TS134809_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_no_registradas_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre){
//		imagen = "TS134809";
//		detalles = imagen + " -ServicioTecnico: " + sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		String day = fechaDeHoy();
//		String dia = day.substring(3,5);
//		cambioDeFrame(driver, By.id("text-input-id-1"),0);
//		driver.findElement(By.id("text-input-id-1")).click();
//		driver.findElement(By.cssSelector(".slds-button.slds-button--icon-container.nds-button.nds-button_icon-container")).click();
//		driver.findElement(By.xpath("//*[text() = '01']")).click();
//		driver.findElement(By.id("text-input-id-2")).click();
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']")));
//		WebElement table_2 = driver.findElement(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']"));
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//tr//td"),0));
//		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows_2) {
//			try {
//				if (cell.getText().equals(dia)) {
//					cell.click();
//					sleep(5000);
//				}
//			} catch (Exception e) {}
//		}
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns"),0));
//		driver.findElement(By.id("text-input-03")).click();
//		driver.findElement(By.xpath("//*[@class = 'slds-dropdown slds-dropdown--left resize-dropdowns']//li//span[contains(text(), 'Todos')]")).click();
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")));
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
//		Boolean asd = true;
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-dropdown.slds-dropdown--left.resize-dropdowns"),0));
//		sleep(5000);
//		List <WebElement> cuadro = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
//		for(WebElement c : cuadro){
//			if(c.getText().toLowerCase().contains("Order")){
//				asd = false;
//			}
//		}
//		Assert.assertTrue(asd);	
//	}
//	
//	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")//ok
//	public void TS134794_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
//		imagen = "TS134794";
//		detalles = imagen + " - Vista360 - DNI: " + sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		try {
//			cc.openleftpanel();
//		} catch (Exception eE) {}
//		WebElement wProfileBoxDetails = driver.findElement(By.className("profile-box-details"));
//		Assert.assertTrue(wProfileBoxDetails.findElement(By.tagName("h1")).getText().toLowerCase().contains(sNombre.toLowerCase()));
//		String sWebDNI = wProfileBoxDetails.findElement(By.className("detail-card")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(1).getText();
//		Assert.assertTrue(sWebDNI.equals(sDNI));
//		Assert.assertTrue(sWebDNI.matches("[0-9]+"));
//		String npsText = wProfileBoxDetails.findElement(By.className("detail-card")).findElement(By.tagName("tbody")).findElements(By.tagName("tr")).get(2).findElements(By.tagName("td")).get(0).getText();
//		Assert.assertTrue(npsText.equals("NPS:"));
//		List<WebElement> wWebList = wProfileBoxDetails.findElements(By.className("profile-edit"));
//		Assert.assertTrue(wWebList.get(0).getText().toLowerCase().equals("actualizar datos"));
//		Assert.assertTrue(wWebList.get(1).getText().toLowerCase().equals("reseteo clave"));
//		driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Datos Personales')] /.. /.. /.. /.. /..")).click();
//		String headerDatosPersonales = driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Datos Personales')]")).getText();
//		Assert.assertTrue(headerDatosPersonales.equals("Datos Personales"));
//		driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Segmentaci')] /.. /.. /.. /.. /..")).click();
//		String headerSegmentacion = driver.findElement(By.xpath("//div[@class='left-sidebar-section-header'] //span[contains(text(),'Segmentaci')]")).getText();
//		Assert.assertTrue(headerSegmentacion.equalsIgnoreCase("Segmentaci\u00f3n"));
//		List<WebElement> panelesIzquierdos = driver.findElements(By.cssSelector(".left-sidebar-section-container"));
//		List<WebElement> datosPersonales = panelesIzquierdos.get(0).findElements(By.cssSelector(".client-data-label"));
//		List<WebElement> segmentacion = panelesIzquierdos.get(1).findElements(By.cssSelector(".client-data-label"));
//		ArrayList<String> datosPersonalesComparar = new ArrayList<String>(Arrays.asList("Correo Electr\u00f3nico:","M\u00f3vil:", "Tel\u00e9fono alternativo:", "Club Personal:", "Categor\u00eda:"));
//		ArrayList<String> segmentacionComparar = new ArrayList<String>(Arrays.asList("Segmento:", "Atributos:"));
//		for (int i = 0; i < datosPersonales.size(); i++) {
//			Assert.assertTrue(datosPersonales.get(i).getText().equals(datosPersonalesComparar.get(i)));
//		}
//		for (int i = 0; i < segmentacion.size(); i++) {
//			WebElement campo = segmentacion.get(i);
//			String textoDelCampo = campo.getText();
//			Assert.assertTrue(textoDelCampo.equals(segmentacionComparar.get(i)));
//			if (textoDelCampo.equals("Segmento:")) {
//				String valorDelCampo = driver.findElement(By.xpath("/html/body/div/div[1]/ng-include/div[4]/ng-include/div/div[2]/table[1]/tbody/tr/td/div[2]")).getText();
//				Assert.assertTrue(valorDelCampo.equalsIgnoreCase("altas recientes - masivo"));
//			}
//		}
//	}
//	
//	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")//ok
//	public void TS134796_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Visualizacion_e_ingreso_a_las_ultimas_gestiones_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre) {
//		imagen = "TS134796";
//		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		cambioDeFrame(driver, By.cssSelector(".slds-p-around--small.slds-col"),0);
//		WebElement gestiones = driver.findElement(By.cssSelector(".slds-p-around--small.slds-col"));
//		Assert.assertTrue(gestiones.getText().toLowerCase().contains("t\u00edtulo") && gestiones.getText().contains("Fecha de creacion") && gestiones.getText().toLowerCase().contains("estado") && gestiones.getText().toLowerCase().contains("numero de orden"));
//	}
//	
//	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")//ok
//	public void TS134797_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Panel_Derecho_Busqueda_de_gestiones_promociones_y_gestiones_abandonadas_FAN_Front_Telefonico(String sDNI, String sLinea,String sNombre) {
//		imagen = "TS134797";
//		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		try{
//			cc.openrightpanel();
//		}catch(Exception e){
//			
//		}
//		cambioDeFrame(driver, By.cssSelector(".abandoned-content.scrollmenu"),0);
//		WebElement abandoned = driver.findElement(By.className("abandoned-section"));
//		WebElement promocion = driver.findElement(By.className("promotions-section"));
//		WebElement busquedgestion = driver.findElement(By.className("sidebar-actions"));
//		Assert.assertTrue(abandoned.getText().contains("Gestiones Abandonadas") && driver.findElement(By.className("abandoned-section")).isDisplayed());
//		Assert.assertTrue(promocion.getText().contains("Promociones") && driver.findElement(By.className("promotions-section")).isDisplayed());
//		Assert.assertTrue(busquedgestion.getText().contains("Iniciar Gestiones") && driver.findElement(By.className("sidebar-actions")).isDisplayed());
//	}
//	
//	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")//ok
//	public void TS134799_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Desplegable_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
//		imagen = "134799";
//		detalles = imagen + "-Vista 360-DNI:" + sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		cambioDeFrame(driver, By.className("card-top"),0);
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.className("card-top")));
//		driver.findElement(By.className("card-top")).click();
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".console-flyout.active.flyout"), 0));
//		WebElement desplegable = driver.findElement(By.cssSelector(".console-flyout.active.flyout"));
//		Assert.assertTrue(desplegable.isDisplayed());
//		ArrayList<String> elementosDesplegableIzquierdoComparar = new ArrayList<String>(Arrays.asList(
//				"Historial de Suspensiones", "Recarga de cr\u00e9dito", "Renovacion de Datos",
//				"Alta/Baja de Servicios", "Suscripciones", "Inconvenientes con Recargas", "Diagn\u00f3stico",
//				"N\u00fameros Gratis", "Cambio de Plan"));
//		List<WebElement> elementosDesplegableIzquierdo = desplegable.findElements(By.cssSelector("[class='console-flyout active flyout'] [class='community-flyout-actions-card'] ul li"));
//		int i = 0;
//		for (WebElement fila : elementosDesplegableIzquierdo) {
//			Assert.assertTrue(fila.getText().equals(elementosDesplegableIzquierdoComparar.get(i)));
//			i++;
//		}
//		Assert.assertTrue(true);
//	}
//	
//	@Test (groups = "PerfilTelefonico", dataProvider = "CuentaVista360")//ok
//	public void TS134808_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_Cerradas_Informacion_brindada_FAN_Front_Telefonico(String sDNI, String sLinea, String sNombre) {
//		imagen = "TS134808";
//		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
//		String dia = fechaDeHoy().substring(3,5);
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"),0);
//		driver.findElement(By.id("text-input-id-1")).click();
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"), 0));
//		WebElement table = driver.findElement(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']"));
//		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows) {
//			try {
//				if (cell.getText().equals(dia))
//					cell.click();
//			} catch (Exception e) {}
//		}
//		driver.findElement(By.id("text-input-id-2")).click();
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-datepicker.slds-dropdown.slds-dropdown--left"), 0));
//		WebElement table_2 = driver.findElement(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']"));
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//tr//td"), 0));
//		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows_2) {
//			try {
//				if (cell.getText().equals(dia))
//					cell.click();
//			} catch (Exception e) {}
//		}
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont"))));
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
//		cambioDeFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"),0);
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"), 0));
//		WebElement tabla = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"));
//		ges.getWait().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")));
//		Assert.assertTrue(tabla.isDisplayed());
//	}
//	
//	@Test (groups = "PerfilTelefonico",  dataProvider = "CuentaVista360")//listad de detalles no 
//	public void TS135351_CRM_Movil_Prepago_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_Plazo_No_vencido_Consulta_registrada_CASOS_FAN_Telefonico(String sDNI, String sLinea,String sNombre) {
//		imagen = "TS135351";
//		boolean gestion = false;
//		detalles = imagen +" -Vista 360-DNI: " + sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		super.cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont"), 0);
//		driver.findElement(By.id("text-input-03")).click();
//		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector(".slds-p-bottom--small"))));
//		String nroCaso = driver.findElement(By.cssSelector(".slds-p-bottom--small")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr")).findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).getText();
//		driver.switchTo().defaultContent();
//		sb.cerrarPestaniaGestion(driver);
//		sleep(5000);
//		cc.buscarCaso(nroCaso);
//		WebElement estado = null;
//		super.cambioDeFrame(driver, By.className("detailList"),0);
//		for (WebElement x : driver.findElements(By.className("detailList"))) {
//			if (x.getText().toLowerCase().contains("propietario del caso"))
//				estado = x;
//		}
//
//		for (WebElement x : estado.findElements(By.tagName("tr"))) {
//			if (x.getText().toLowerCase().contains("estado"))
//				estado = x;			
//		}
//		if (estado.getText().toLowerCase().contains("en espera de ejecuci\u00f3n") || estado.getText().toLowerCase().contains("informada") || estado.getText().toLowerCase().contains("resuelta exitosa") ||  estado.getText().toLowerCase().contains("pendiente evento masivo"));
//			gestion = true;
//		Assert.assertTrue(gestion);
//	}
//	
//	@Test (groups = "PerfilTelefonico",  dataProvider = "CuentaVista360")//ok
//	public void TS135356_CRM_Movil_Prepago_Vista_360_Consulta_de_Gestiones_Gestiones_abiertas_Plazo_No_vencido_Consulta_registrada_ORDENES_FAN_Telefonico(String sDNI, String sLinea,String sNombre) {
//		imagen = "TS135356";
//		detalles = imagen+"-Vista 360 - DNI:"+sDNI;
//		boolean gestion = false;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont"),0);
//		driver.findElement(By.id("text-input-03")).click();
//		driver.findElement(By.xpath("//*[text() = 'Ordenes']")).click();
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
//		cambioDeFrame(driver, By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"),0);
//		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
//		nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).click();
//		WebElement estado = null;
//		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"),0);
//		for (WebElement x : driver.findElements(By.className("pbSubsection"))) {
//			if (x.getText().toLowerCase().contains("n\u00famero de pedido"))
//				estado = x;
//		}
//		for (WebElement x : estado.findElement(By.className("detailList")).findElements(By.tagName("tr"))) {
//			if (x.getText().toLowerCase().contains("estado"))
//				estado = x;
//		}
//		if (estado.getText().toLowerCase().contains("activada") || (estado.getText().toLowerCase().contains("iniciada")))
//			gestion = true;
//		Assert.assertTrue(gestion);
//	}
	
	//----------------------------------------------- AGENTE -------------------------------------------------------\\
	
	@Test
	public void TS148803_CRM_Movil_Mix_Vista_360_Producto_Activo_del_cliente_Datos_Crm_Agente() {
		imagen = "TS148798";
		boolean gest = false, prodServ = false, cuenta = false, estado = false, credDisp = false, credProm = false;
		ges.BuscarCuenta("DNI", "37478859");
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-top']")).getText().contains("Conexi\u00f3n Control Abono"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-top'] [class='slds-text-body_regular activation-date slds-p-bottom--xx-small']")).getText().matches("Act: [0-9]{2}/[0-9]{2}/[0-9]{4}"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='console-card active'] [class='card-top'] [class='slds-text-body_regular line-number slds-p-bottom--xx-small']")).getText().matches("Linea: [0-9]{10}"));
		for (WebElement x : driver.findElements(By.cssSelector("[class='console-card active'] [class='card-info-hybrid'] [class='details']"))) {
			if (x.getText().contains("Cuenta:"))
				cuenta = true;
			if (x.getText().contains("Estado:"))
				estado = true;
			if (x.getText().contains("Cr\u00e9dito Disponible:"))
				credDisp = true;
			if (x.getText().contains("Cr\u00e9dito Promocional:"))
				credProm = true;
		}
		Assert.assertTrue(cuenta && estado && credDisp && credProm);
		for (WebElement x : driver.findElements(By.cssSelector("[class='console-card active'] [class='card-info-hybrid'] [class='actions'] li"))) {
			if (x.getText().equalsIgnoreCase("Gestiones"))
				gest = true;
			if (x.getText().equalsIgnoreCase("Productos y Servicios"))
				prodServ = true;
		}
		Assert.assertTrue(gest && prodServ);
	}
	
	@Test
	public void TS148747_CRM_Movil_Mix_Vista_360_Productos_y_Servicios_Visualizacion_del_estado_de_los_servicios_activos_Crm_Agente() {
		imagen = "TS148726";
		ges.BuscarCuenta("DNI", "37478859");
		cambioDeFrame(driver, By.cssSelector("[class='console-card active']"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class='console-card active']")));
		driver.findElement(By.xpath("//*[@class='console-card active']//*[@class='card-info-hybrid']//*[@class='actions']//span[text()='Productos y Servicios']")).click();
		cambioDeFrame(driver, By.cssSelector("[class='slds-button slds-button--brand']"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-button slds-button--brand']")));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--x-large'] thead")).getText().contains("NOMBRE"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--x-large'] thead")).getText().contains("FECHA DE ESTADO"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-size--1-of-1 slds-medium-size--1-of-1 slds-large-size--1-of-1 slds-m-top--x-large'] thead")).getText().contains("ESTADO"));
		Assert.assertTrue(driver.findElement(By.cssSelector("[class='slds-button slds-button--brand']")).getText().contains("Ver detalle"));
	}
	
//	@Test (groups = "PerfilAgente", dataProvider = "CuentaVista360")//ok
//	public void TS134823_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Datos_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
//		imagen = "TS134823";
//		detalles = imagen + " -ServicioTecnico: " + sDNI;
//		boolean creditoRecarga = false, creditoPromocional = false, estado = false, internetDisponible = false;
//		boolean  historiales = false, misServicios = false, gestiones = false, historialDeSuspensiones = false;
//		ges.BuscarCuenta("DNI", sDNI);
//		cambioDeFrame(driver, By.className("card-top"),0);
//		WebElement plan = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(0);
//		WebElement FechaActivacion = driver.findElement(By.cssSelector(".slds-text-body_regular.expired-title"));
//		WebElement linea = driver.findElement(By.className("card-top")).findElements(By.className("slds-text-heading_medium")).get(2);
//		Assert.assertTrue(plan.isDisplayed() && FechaActivacion.isDisplayed() && linea.getText().contains(sLinea));
//		for (WebElement x : driver.findElements(By.cssSelector(".slds-text-body_regular.detail-label"))) {
//			if (x.getText().toLowerCase().contains("cr\u00e9dito recarga"))
//				creditoRecarga = true;
//			if (x.getText().toLowerCase().contains("cr\u00e9dito promocional"))
//				creditoPromocional = true;
//			if (x.getText().toLowerCase().contains("estado"))
//				estado = true;
//			if (x.getText().toLowerCase().contains("internet disponible"))
//				internetDisponible = true;
//		}
//		Assert.assertTrue(creditoRecarga && creditoPromocional && estado && internetDisponible);
//		for(WebElement y : driver.findElements(By.className("slds-text-body_regular"))) {
//			if(y.getText().toLowerCase().contains("historiales"))
//				historiales = true;
//			if(y.getText().toLowerCase().contains("mis servicios"))
//				misServicios = true;
//			if(y.getText().toLowerCase().contains("gestiones"))
//				gestiones = true;
//			if(y.getText().toLowerCase().contains("historial de suspensiones"))
//				historialDeSuspensiones = true;
//		}
//		Assert.assertTrue(historiales && misServicios && gestiones && historialDeSuspensiones);
//	}
//	
//	@Test (groups = "PerfilAgente", dataProvider = "CuentaVista360")//ok
//	public void TS134824_CRM_Movil_Prepago_Vista_360_Producto_Activo_del_cliente_Desplegable_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
//		imagen = "TS134824";
//		detalles = imagen + " -ServicioTecnico: " + sDNI;
//		boolean recarga = false, renovacion = false, servicios = false, numerosGratis = false ,cambioSim = false, cambioPlan = false;
//		ges.BuscarCuenta("DNI", sDNI);
//		cambioDeFrame(driver, By.className("card-top"),0);
//		driver.findElement(By.className("card-top")).click();
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class = 'community-flyout-actions-card'] li"), 2));
//		WebElement wFlyOutActionCard = driver.findElement(By.className("community-flyout-actions-card"));
//		List<WebElement> wGestiones = wFlyOutActionCard.findElements(By.tagName("li"));
//		for (WebElement wAux : wGestiones) {
//			if(wAux.getText().toLowerCase().contains("recarga de cr\u00e9dito"))
//				recarga = true;
//			if(wAux.getText().toLowerCase().contains("renovacion de datos"))
//				renovacion = true;	
//			if(wAux.getText().toLowerCase().contains("alta/baja de servicios"))
//				servicios = true;
//			if (wAux.getText().toLowerCase().contains("n\u00fameros gratis"))
//				numerosGratis = true;
//			if(wAux.getText().toLowerCase().contains("cambio simcard"))
//				cambioSim = true;
//			if(wAux.getText().toLowerCase().contains("cambio de plan"))
//				cambioPlan = true;
//			
//		}
//		Assert.assertTrue(recarga && renovacion && servicios && numerosGratis && cambioSim && cambioPlan);
//	}
//	
//	@Test (groups = "PerfilAgente", dataProvider = "CuentaVista360")//ok
//	public void TS134832_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_no_registradas_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
//		imagen = "TS13832";
//		detalles = imagen + " -ServicioTecnico: " + sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		cambioDeFrame(driver, By.id("text-input-id-1"),0);
//		String dia = fechaDeHoy().substring(3, 5);
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("text-input-id-1"))));
//		driver.findElement(By.id("text-input-id-1")).click();
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("[class='slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left'] tr td"))));
//		List<WebElement> tableRows = driver.findElements(By.cssSelector("[class='slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left'] tr td"));
//		ges.clickElementoPorTextExacto(tableRows, "02");
//		driver.findElement(By.id("text-input-id-2")).click();
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left'] tr td"),0));
//		List<WebElement> tableRows_2 = driver.findElements(By.cssSelector("[class='slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left'] tr td"));
//		ges.clickElementoPorTextExacto(tableRows_2, dia);
//		List<WebElement> tipo = driver.findElements(By.cssSelector("[class='slds-dropdown slds-dropdown--left resize-dropdowns'] li"));
//		ges.clickElementoPorTextExacto(tipo, "todos");
//		ges.getWait().until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']"))));
//		driver.findElement(By.cssSelector("[class='slds-button slds-button--brand filterNegotiations slds-p-horizontal--x-large slds-p-vertical--x-small secondaryFont']")).click();
//		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class = 'slds-grid slds-wrap slds-card slds-m-bottom--small  slds-m-around--medium'] tbody tr td"),3));
//		List <WebElement> cuadro = driver.findElements(By.cssSelector("[class = 'slds-grid slds-wrap slds-card slds-m-bottom--small  slds-m-around--medium'] tbody tr td"));
//		boolean verif = ges.containsText(cuadro, "order");
//		Assert.assertTrue(verif);
//	}
//	
//	@Test (groups = "PerfilAgente", dataProvider = "CuentaVista360")
//	public void TS134817_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_servicios_activos_FAN_Front_OOCC_Agentes(String sDNI, String sLinea, String sNombre){
//		imagen = "TS134817";
//		detalles = imagen + "-Vista 360 - DNI: "+sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Mis Servicios");
//		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium"), 0);
//		buscarYClick(driver.findElements(By.cssSelector(".slds-button.slds-button--brand")), "equals", "ver detalle");
//		cambioDeFrame(driver, By.cssSelector(".via-slds-card__header.slds-card__header.headerList"), 0);
//		boolean b = false;
//		for(WebElement x : driver.findElements(By.cssSelector(".slds-card.slds-m-bottom--small"))) {
//			if(x.getText().toLowerCase().contains("servicio") && x.getText().toLowerCase().contains("fecha") && x.getText().toLowerCase().contains("estado")) {
//				b = true;
//			}
//		}
//		Assert.assertTrue(b);
//	}
//	
//	@Test (groups = "PerfilAgente", dataProvider = "CuentaVista360")
//	public void TS134818_CRM_Movil_Prepago_Vista_360_Mis_Servicios_Visualizacion_del_estado_de_los_Productos_activos_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
//		imagen = "TS134818";
//		detalles = imagen + "Vista 360-DNI:" + sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Mis Servicios");
//		boolean a = false;
//		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")));
//		WebElement wTabla = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-p-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
//		List <WebElement> lTabla = wTabla.findElements(By.tagName("tr"));
// 		for(WebElement x : lTabla) {
// 			if(x.getText().toLowerCase().contains("activo"))
// 				a = true;
// 		}
//		Assert.assertTrue(a);
//	}
//	
//	@Test (groups = "PerfilAgente", dataProvider = "CuentaVista360")
//	public void TS134819_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Informacion_del_cliente_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
//		imagen = "TS134819";
//		detalles = imagen + "-Vista 360 - DNI: "+ sDNI+ "- Nombre: " + sNombre;
//		ges.BuscarCuenta("DNI", sDNI);
//		cambioDeFrame(driver, By.className("profile-box"), 0);
//		String nombre = driver.findElement(By.cssSelector("[class='slds-text-heading_large title-card']")).getText();
//		boolean dni = false;
//		for (WebElement x : driver.findElements(By.cssSelector("[class='detail-card'] tbody tr"))) {
//			String texto = x.getText();
//			if (texto.contains(sDNI) && texto.contains("DNI")) {
//				dni = true;
//				break;
//			}
//		}
//		Assert.assertTrue(nombre.equalsIgnoreCase(sNombre) && dni);
//		WebElement tabla = driver.findElement(By.className("detail-card"));
//		Assert.assertTrue(tabla.getText().toLowerCase().contains("atributo") && tabla.getText().toLowerCase().contains("nps"));
//		WebElement tabla2 = driver.findElement(By.className("profile-box"));
//		Assert.assertTrue(tabla2.getText().toLowerCase().contains("actualizar datos"));
//		WebElement tabla3 = driver.findElement(By.className("left-sidebar-section-container"));
//		Assert.assertTrue(tabla3.getText().contains("Correo Electr\u00f3nico") && tabla3.getText().contains("M\u00f3vil"));
//		Assert.assertTrue(tabla3.getText().toLowerCase().contains("tel\u00e9fono alternativo") && tabla3.getText().toLowerCase().contains("club personal") && tabla3.getText().toLowerCase().contains("categor\u00eda"));
//	}
//	
//	@Test (groups = "PerfilAgente", dataProvider = "CuentaVista360")
//	public void TS134821_CRM_Movil_Prepago_Vista_360_Distribucion_de_paneles_Visualizacion_e_ingreso_a_las_ultimas_gestiones_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre){
//		imagen = "TS134821";
//		detalles = imagen + "Vista 360 -DNI:" + sDNI;
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		cambioDeFrame(driver, By.id("text-input-03"), 0);
//		driver.findElement(By.id("text-input-03")).click();
//		driver.findElement(By.xpath("//*[text() = 'Ordenes']")).click();
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")));
//		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).getLocation().y+" )");
//		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
//		boolean ingresar = false;
//		if(nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).isDisplayed()) {
//			nroCaso.findElements(By.tagName("td")).get(2).findElement(By.tagName("div")).findElement(By.tagName("a")).click();
//			ingresar = true;
//		}
//		Assert.assertTrue(ingresar);
//	}
//	
//	@Test (groups = "PerfilAgente", dataProvider = "CuentaVista360")
//	public void TS134831_CRM_Movil_Prepago_Vista_360_Consulta_por_gestiones_Gestiones_Cerrada_Informacion_brindada_FAN_Front_Agentes(String sDNI, String sLinea, String sNombre) {
//		imagen = "TS134831";
//		detalles = imagen+"-Vista 360 -DNI:"+sDNI;
//		String dia = fechaDeHoy().substring(3,5);
//		ges.BuscarCuenta("DNI", sDNI);
//		ges.irAGestionEnCard("Gestiones");
//		cambioDeFrame(driver, By.cssSelector(".slds-grid.slds-wrap.slds-grid--pull-padded.slds-m-around--medium.slds-p-around--medium.negotationsfilter"), 0);
//		driver.findElement(By.id("text-input-id-1")).click();
//		WebElement table = driver.findElement(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']"));
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']")));
//		List<WebElement> tableRows = table.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows) {
//			try {
//				if (cell.getText().equals(dia))
//					cell.click();
//			} catch (Exception e) {}
//		}
//		driver.findElement(By.id("text-input-id-2")).click();
//		WebElement table_2 = driver.findElement(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']"));
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class = 'slds-datepicker slds-dropdown slds-dropdown--left nds-datepicker nds-dropdown nds-dropdown--left']")));
//		List<WebElement> tableRows_2 = table_2.findElements(By.xpath("//tr//td"));
//		for (WebElement cell : tableRows_2) {
//			try {
//				if (cell.getText().equals(dia))
//					cell.click();
//			} catch (Exception e) {}
//		}
//		driver.findElement(By.id("text-input-03")).click();
//		driver.findElement(By.xpath("//*[text() = 'Casos']")).click();
//		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.filterNegotiations.slds-p-horizontal--x-large.slds-p-vertical--x-small.secondaryFont")).click();
//		cambioDeFrame(driver,  By.cssSelector(".slds-table.slds-table--bordered.slds-table--resizable-cols.slds-table--fixed-layout.via-slds-table-pinned-header"), 0);
//		boolean verificacion = false;
//		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,"+driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).getLocation().y+" )");
//		WebElement nroCaso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.slds-card.slds-m-bottom--small.slds-m-around--medium")).findElement(By.tagName("table")).findElement(By.tagName("tbody"));
//		List <WebElement> lista = nroCaso.findElements(By.tagName("tr"));
//		for(WebElement x : lista) {
//			if(x.getText().toLowerCase().contains("resuelta exitosa") || x.getText().toLowerCase().contains("realizada exitosa"))
//				verificacion = true;
//		}
//		Assert.assertTrue(verificacion);
//	}
}