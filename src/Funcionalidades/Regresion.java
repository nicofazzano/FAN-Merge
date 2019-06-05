package Funcionalidades;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import Pages.BasePage;
import Pages.CBS;
import Pages.ContactSearch;
import Pages.CustomerCare;
import Pages.Marketing;
import Pages.PagePerfilTelefonico;
import Pages.SalesBase;
import Pages.setConexion;
import PagesPOM.GestionDeClientes_Fw;
import PagesPOM.LoginFw;
import Tests.CBS_Mattu;
import Tests.TestBase;

public class Regresion extends TestBase {

	private WebDriver driver;
	private LoginFw log;
	private GestionDeClientes_Fw ges;
	private CustomerCare cc;
	private CBS cbs;
	private CBS_Mattu cbsm;
	private ContactSearch contact;
	private List<String> sOrders = new ArrayList<String>();
	private String imagen;
	String detalles;
	
	
	//@BeforeClass (alwaysRun = true)
	public void Sit02() throws IOException, AWTException {
		driver = setConexion.setupEze();
		sleep(5000);
		cc = new CustomerCare(driver);
		log = new LoginFw(driver);
		ges = new GestionDeClientes_Fw(driver);
		contact = new ContactSearch(driver);
		log.LoginSit02();
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		//cc.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilOficina")
	public void initOOCC() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		contact = new ContactSearch(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		log.loginOOCC();
		ges.irAConsolaFAN();
	}
		
	@BeforeClass (groups = "PerfilTelefonico")
	public void initTelefonico() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		contact = new ContactSearch(driver);
		log.loginTelefonico();
		ges.irAConsolaFAN();
	}
	
	//@BeforeClass (groups = "PerfilAgente")
		public void initAgente() throws IOException, AWTException {
		driver = setConexion.setupEze();
		ges = new GestionDeClientes_Fw(driver);
		cc = new CustomerCare(driver);
		cbs = new CBS();
		cbsm = new CBS_Mattu();
		log = new LoginFw(driver);
		contact = new ContactSearch(driver);
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

	//@AfterMethod(alwaysRun=true)
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
	
	@Test (groups = "PerfilTelefonico", dataProvider="rNuevaNomina") 
	public void TS_001_Nominacion_Cliente_Nuevo_Telefonico(String sLinea, String sDni, String sNombre, String sApellido, String sGenero, String sFnac, String sEmail, String sProvincia, String sLocalidad,String sZona, String sCalle, String sNumCa, String sCP, String tDomic) { 
		imagen = "TS_001";
		detalles = null;
		detalles = imagen + "- Nominacion - DNI:" + sDni;		
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("PhoneNumber")));
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")));
		driver.findElement(By.cssSelector(".slds-button.slds-button--icon.slds-m-right--x-small.ng-scope")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".slds-hint-parent.ng-scope"), 0));
		WebElement botonNominar = null;
		for (WebElement x : driver.findElements(By.cssSelector(".slds-hint-parent.ng-scope"))) {
			if (x.getText().toLowerCase().contains("plan con tarjeta"))
				botonNominar = x;
		}
		for (WebElement x : botonNominar.findElements(By.tagName("td"))) {
			if (x.getAttribute("data-label").equals("actions"))
				botonNominar = x;
		}
		botonNominar.findElement(By.tagName("a")).click();
		cambioDeFrame(driver, By.id("DocumentTypeSearch"),0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("DocumentInputSearch")));
		contact.crearClienteNominacion("DNI", sDni, sGenero, sNombre, sApellido, sFnac, sEmail);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".slds-input.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required")));
		contact.completarDomicilio(sProvincia, sLocalidad, sZona, sCalle, sNumCa, sCP, tDomic);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("FinishProcess_nextBtn")));
		List <WebElement> element = driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"));
		boolean a = false;
		for (WebElement x : element) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!")) {
				a = true;
				System.out.println(x.getText());
			}
		}
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
		Assert.assertTrue(a);
	}
	
	@Test (groups = "PerfilOficina", dataProvider = "rExistenteNomina") 
	public void TS85097_CRM_Movil_REPRO_Nominatividad_Cliente_Existente_Presencial_DOC_OfCom(String sLinea, String sDni, String sNombre, String sApellido) {
		imagen = "TS85097";
		detalles = null;
		detalles = imagen + " -Nominacion: " + sDni+"- Linea: "+sLinea;
		boolean nominacion = false;
		cambioDeFrame(driver,By.id("SearchClientDocumentType"),0);
		driver.findElement(By.id("PhoneNumber")).sendKeys(sLinea);
		driver.findElement(By.id("SearchClientsDummy")).click();
		sleep(10000);
		WebElement cli = driver.findElement(By.id("tab-scoped-2")); 	
		cli.findElement(By.tagName("tbody")).findElement(By.tagName("tr")).click();
		sleep(3000);
		List<WebElement> Lineas = driver.findElement(By.id("tab-scoped-2")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement UnaL: Lineas) {
			if(UnaL.getText().toLowerCase().contains("plan con tarjeta repro")) {
				UnaL.findElements(By.tagName("td")).get(6).findElement(By.tagName("svg")).click();
				System.out.println("Linea Encontrada");
				break;
			}
		}
		sleep(3000);
		contact.searchContact2("DNI", sDni, "Masculino");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("Contact_nextBtn")));
		driver.findElement(By.id("Contact_nextBtn")).click();	
		sleep(10000);
		contact.tipoValidacion("documento");
		File directory = new File("Dni.jpg");
		contact.subirArchivo(new File(directory.getAbsolutePath()).toString(), "si");
		sleep(18000);
		for (WebElement x : driver.findElement(By.id("NominacionExitosa")).findElements(By.tagName("p"))) {
			if (x.getText().toLowerCase().contains("nominaci\u00f3n exitosa!"))
				nominacion = true;
		}
		Assert.assertTrue(nominacion);
		driver.findElement(By.id("FinishProcess_nextBtn")).click();
		sleep(3000);
		cbsm.ValidarInfoCuenta(sLinea, sNombre,sApellido, "Plan con Tarjeta Repro");
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="rRecargasTC")
	public void TS134332_CRM_Movil_REPRO_Recargas_Telefonico_TC(String sDNI, String sMonto, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular) {
		imagen = "TS134332";
		detalles = null;
		detalles = imagen + "-Recarga-DNI:" + sDNI;
		String datoViejo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosInicial = Integer.parseInt(datoViejo.substring(0, (datoViejo.length()) - 4));
		ges.BuscarCuenta("DNI", sDNI);
		ges.irAGestionEnCard("Recarga de cr\u00e9dito");
		cambioDeFrame(driver, By.id("RefillAmount"), 0);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("RefillAmount")));
		driver.findElement(By.id("RefillAmount")).sendKeys(sMonto);
		driver.findElement(By.id("AmountSelectionStep_nextBtn")).click();
		cambioDeFrame(driver, By.id("InvoicePreview_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")));
		String caso = driver.findElement(By.cssSelector(".slds-grid.slds-wrap.ng-pristine.ng-valid")).findElement(By.tagName("child")).findElements(By.tagName("p")).get(1).getText();
		caso = caso.substring(caso.lastIndexOf(" ")+1, caso.length());
		driver.findElement(By.id("InvoicePreview_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing")));
		Assert.assertTrue(driver.findElement(By.id("GeneralMessageDesing")).getText().toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		cbsm.Servicio_NotificarPago(caso);
		String datoVNuevo = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer datosFinal = Integer.parseInt(datoVNuevo.substring(0, (datoVNuevo.length()) - 4));
		Assert.assertTrue(datosInicial + 1000 == datosFinal);
		cc.buscarOrdenDiag(caso+"*");
		cc.verificarPedido(caso, "activada");
	}

	@Test (groups = "PerfilOficina", dataProvider="rSmsDescuento")
	public void TS_002_Compra_De_Pack_SMS_Descuento_De_Saldo_OOCC(String sDni, String sPack, String sLinea){
		imagen ="TS_002";
		
	}
	
	@Test (groups = "PerfilAgente", dataProvider="rDatosEfectivo")
	public void TS135801_CRM_Movil_PREVenta_de_pack_Paquete_M2M_10_MB_Factura_de_Venta_Efectivo_Presencial_PuntMa_Alta_Agente(String sDni, String sPack, String sLinea){
		imagen = "TS135801";
		detalles = null;
		detalles = "Venta de Pack "+imagen+"-DNI:"+sDni;
		SalesBase sale = new SalesBase(driver);
		BasePage cambioFrameByID=new BasePage();
		CustomerCare cCC = new CustomerCare(driver);
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		driver.switchTo().frame(cambioFrameByID.getFrameForElement(driver, By.id("SearchClientDocumentType")));	
		sleep(8000);
		sale.BuscarCuenta("DNI", sDni);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		pagePTelefo.buscarAssert();
		cCC.seleccionarCardPornumeroLinea(sLinea, driver);
		pagePTelefo.comprarPack();
		sleep(5000);
		//pagePTelefo.PacksRoaming(sVentaPack);
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="rMinutosTC")
	public void TS_003_Compra_De_Pack_Minutos_TC_Telefonico(String sDni, String sPack, String sLinea, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular){
		imagen = "TS_003";
		detalles = null;
		detalles = "Compra de pack "+imagen+"-DNI:"+sDni;
		PagePerfilTelefonico pagePTelefo = new PagePerfilTelefonico(driver);
		Marketing mk = new Marketing(driver);
		ges.BuscarCuenta("DNI", sDni);
		String accid = driver.findElement(By.cssSelector(".searchClient-body.slds-hint-parent.ng-scope")).findElements(By.tagName("td")).get(5).getText();
		System.out.println("id "+accid);
		detalles +="-Cuenta:"+accid;
		pagePTelefo.buscarAssert();
		cc.seleccionarCardPornumeroLinea(sLinea, driver);
		pagePTelefo.comprarPack("comprar sms");
		sleep(5000);
		cc.closeleftpanel();
		//String chargeCode = null;
		try {
			//chargeCode = 
			pagePTelefo.PackLDI(sPack);
		}
		catch (Exception eE) {
			driver.navigate().refresh();
			sleep(10000);
			mk.closeTabByName(driver, "Comprar SMS");
			cc.seleccionarCardPornumeroLinea(sLinea, driver);
			pagePTelefo.comprarPack("comprar sms");
			//chargeCode = 
			pagePTelefo.PackLDI(sPack);
		}
		pagePTelefo.tipoDePago("en factura de venta");
		buscarYClick(driver.findElements(By.id("SetPaymentType_nextBtn")), "equals", "continuar");
		sleep(10000);
		pagePTelefo.getTipodepago().click();
		sleep(8000);
		pagePTelefo.getSimulaciondeFactura().click();
		sleep(12000);
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		sleep(8000);
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("CardNumber-0")).sendKeys(sNumTarjeta);
		selectByText(driver.findElement(By.id("expirationMonth-0")), sVenceMes);
		selectByText(driver.findElement(By.id("expirationYear-0")), sVenceAno);
		driver.findElement(By.id("securityCode-0")).sendKeys(sCodSeg);
		selectByText(driver.findElement(By.id("documentType-0")), "DNI");
		driver.findElement(By.id("documentNumber-0")).sendKeys(sDni);
		driver.findElement(By.id("cardHolder-0")).sendKeys(sTitular);
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden:"+sOrden;
		pagePTelefo.getMediodePago().click();
		sleep(45000);
		pagePTelefo.getOrdenSeRealizoConExito().click();
		driver.navigate().refresh();
		sleep(5000);
		cc.buscarCaso(sOrden);
		sleep(3000);
		CBS cCBS = new CBS();
		CBS_Mattu cCBSM = new CBS_Mattu();
		Assert.assertTrue(cCBS.validarActivacionPack(cCBSM.Servicio_QueryFreeUnit(sLinea), sPack));
		driver.switchTo().frame(cambioFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr")));
		WebElement tabla = driver.findElement(By.id("ep")).findElements(By.tagName("table")).get(1);
		String datos = tabla.findElements(By.tagName("tr")).get(4).findElements(By.tagName("td")).get(1).getText();
		Assert.assertTrue(datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated"));	
		System.out.println("Operacion: Compra de Pack "+ "Order: " + sOrden + "Cuenta: "+ accid + "Fin");
		detalles = imagen + "-Venta de pack-DNI: "+ sDni + "-" + "Charge Code: "; 
	}
	
	@Test (groups = "PerfilTelefonico", dataProvider="rDescuentoTelef")
	public void TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito(String sDni, String sLinea, String accid){
		imagen = "TS_CRM_Movil_REPRO_Renovacion_De_Cuota_Telefonico_Descuento_De_Saldo_Con_Credito";
		detalles = null;
		detalles = "Renovacion de cuota "+imagen+"-DNI:"+sDni;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		String sMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer iMainBalance = Integer.parseInt(sMainBalance.substring(0, (sMainBalance.length()) - 1));
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.BuscarCuenta("DNI", sDni);
		detalles +="-Cuenta:"+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		sleep(5000);
		List<WebElement> elementos = driver.findElement(By.cssSelector(".table.slds-table.slds-table--bordered.slds-table--cell-buffer")).findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
		for(WebElement elemento : elementos) {
			if(elemento.getText().contains("50 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));	
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Saldo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		sleep(10000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		String uMainBalance = cbs.ObtenerValorResponse(cbsm.Servicio_queryLiteBySubscriber(sLinea), "bcs:MainBalance");
		Integer uiMainBalance = Integer.parseInt(uMainBalance.substring(0, (uMainBalance.length()) - 1));
		Assert.assertTrue(iMainBalance > uiMainBalance);
		sleep(12000);
		String sOrder = cc.obtenerOrden(driver, "Reseteo de Cuota");
		System.out.println("Orden"+sOrder);
		detalles += "-Orden:"+sOrder+"- Linea"+sLinea;
		System.out.println("Order: " + sOrder + " Fin");
	}
	
	@Test (groups = "PerfilOficina", dataProvider="rEfectivoOOCC")
	public void TS130056_CRM_Movil_REPRO_Renovacion_de_cuota_Presencial_Reseteo_200_MB_por_Dia_Efectivo_con_Credito(String sDni, String sLinea, String accid){
		imagen = "TS130056";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDni+" - Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		ges.BuscarCuenta("DNI", sDni);
		detalles+="-Cuenta: "+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("combosMegas"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement elemento : elementos) {
			if(elemento.getText().contains("200 MB")) {
				elemento.findElement(By.className("slds-checkbox")).click();
				break;
			}
		}
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("CombosDeMegas_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("CombosDeMegas_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-radio ng-scope']")));
		buscarYClick(driver.findElements(By.cssSelector("[class='slds-radio ng-scope']")), "contains", "Factura de Venta");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SetPaymentType_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		sleep(12000);
		String sOrden = cc.obtenerOrden2(driver);
		detalles+="-Orden: "+sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "efectivo");
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SelectPaymentMethodsStep_nextBtn")));
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("SaleOrderMessages_nextBtn")));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles +="-Monto: "+orden.split("-")[2]+"-Prefactura: "+orden.split("-")[1];
		sOrders.add("Recargas" + orden + ", cuenta:"+accid+", DNI: " + sDni +", Monto:"+orden.split("-")[2]);
		cbsm.Servicio_NotificarPago(orden.split("-")[0]);
		driver.navigate().refresh();
		sleep(5000);
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres Adicionales");
		System.out.println("Datos Inicial "+datosInicial);
		System.out.println("Datos final "+datosFinal);
		Assert.assertTrue((Integer.parseInt(datosInicial)+204800)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);
	}
	
	@Test (groups = "PerfilAgente", dataProvider="rTCAgente")
	public void TS_009_Renovacion_De_Cuota_TC_Agente(String sDni, String sLinea, String accid, String sBanco, String sTarjeta, String sPromo, String sCuota, String sNumTarjeta, String sVenceMes, String sVenceAno, String sCodSeg, String sTitular){
		imagen = "TS_009";
		detalles = null;
		detalles = "Renocavion de cuota: "+imagen+" - DNI: "+sDni+" - Linea: "+sLinea;
		String datosInicial = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		ges.BuscarCuenta("DNI", sDni);
		System.out.println("id "+accid);
		detalles += "-Cuenta: "+accid;
		ges.irAGestionEnCard("Renovacion de Datos");
		cambioDeFrame(driver, By.id("CombosDeMegas_nextBtn"), 0);
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"), 2));
		List<WebElement> elementos = driver.findElements(By.cssSelector("[class='slds-grid slds-wrap ng-invalid ng-invalid-vlc-val-error ng-dirty'] tbody tr"));
		for(WebElement UnE:elementos) {
			if(UnE.findElement(By.tagName("td")).getText().contains("50 MB")) {
				UnE.findElement(By.className("slds-checkbox")).click();
			}
		}
		driver.findElement(By.id("CombosDeMegas_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SetPaymentType_nextBtn")));
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding.ng-scope")), "contains", "en factura de venta");
		cc.obligarclick(driver.findElement(By.id("SetPaymentType_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("InvoicePreview_nextBtn")));
		cc.obligarclick(driver.findElement(By.id("InvoicePreview_nextBtn")));
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("SelectPaymentMethodsStep_nextBtn")));
		String sOrden = cc.obtenerOrden2(driver);
		detalles += "-Orden:" + sOrden;
		buscarYClick(driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding")), "equals", "tarjeta de credito");
		cambioDeFrame(driver, By.id("BankingEntity-0"), 0);
		selectByText(driver.findElement(By.id("BankingEntity-0")), sBanco);
		selectByText(driver.findElement(By.id("CardBankingEntity-0")), sTarjeta);
		selectByText(driver.findElement(By.id("promotionsByCardsBank-0")), sPromo);
		selectByText(driver.findElement(By.id("Installment-0")), sCuota);
		driver.findElement(By.id("SelectPaymentMethodsStep_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("GeneralMessageDesing"))); 
		String check = driver.findElement(By.id("GeneralMessageDesing")).getText();
		Assert.assertTrue(check.toLowerCase().contains("la orden se realiz\u00f3 con \u00e9xito"));
		driver.findElement(By.id("SaleOrderMessages_nextBtn")).click();
		String orden = cc.obtenerTNyMonto2(driver, sOrden);
		System.out.println("orden = "+orden);
		detalles+=", Monto:"+orden.split("-")[2]+"Prefactura: "+orden.split("-")[1];
		cbsm.Servicio_NotificarPago(sOrden);
		sleep(5000);
		if(activarFalsos == true) {
			cbsm.Servicio_NotificarPago(sOrden);
			sleep(20000);
		}
		driver.navigate().refresh();
		String datosFinal = cbs.ObtenerUnidadLibre(cbsm.Servicio_QueryFreeUnit(sLinea), "Datos Libres");
		Assert.assertTrue((Integer.parseInt(datosInicial)+51200)==Integer.parseInt(datosFinal));
		cambioDeFrame(driver, By.cssSelector(".hasMotif.orderTab.detailPage.ext-webkit.ext-chrome.sfdcBody.brandQuaternaryBgr"), 0);
		boolean condition = false;
		List<WebElement> tabla = driver.findElements(By.cssSelector("[class='detailList'] tr"));
		for (WebElement fila : tabla) {
			if (fila.getText().contains("Estado")) {
				String datos = fila.findElement(By.cssSelector("[class='dataCol col02']")).getText();
				System.out.println(datos);
				condition = datos.equalsIgnoreCase("activada")||datos.equalsIgnoreCase("activated");
				break;
			}
		}
		Assert.assertTrue(condition);	
	}
	
}