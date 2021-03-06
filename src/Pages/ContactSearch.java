package Pages;

import java.io.File;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.ClickAction;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import PagesPOM.GestionDeClientes_Fw;
import Tests.TestBase;

public class ContactSearch extends BasePage {

	final WebDriver driver;

	@FindBy(how = How.ID, using = "SearchClientDocumentType")
	private WebElement documentType;
	
	@FindBy(how = How.ID, using = "DocumentTypeSearch")
	private WebElement documentType2;

	@FindBy(how = How.ID, using = "SearchClientDocumentNumber")
	private WebElement document;
	
	@FindBy(how = How.ID, using = "DocumentInputSearch")
	private WebElement document2;
	
	@FindBy(how = How.CSS, using = ".OSradioButton.ng-scope.only-buttom")
	private WebElement BotonCrearNuevoCliente;
	
	@FindBy(how = How.CSS, using = ".slds-radio--faux.ng-scope")
	private List<WebElement> gender;
	
	@FindBy(how = How.ID, using = "FirstName")
	private WebElement nombre;
	
	@FindBy(how = How.ID, using = "LastName")
	private WebElement apellido;
	
	@FindBy(how = How.ID, using = "Birthdate")
	private WebElement fNac;

	@FindBy(how = How.ID, using = "EmailSelectableItems")
	private WebElement Email;
	
	@FindBy(how = How.ID, using = "PermanencyDueDate")
	private WebElement FechaDePermanencia;
	
	@FindBy(how = How.ID, using = "Contact_nextBtn")
	private WebElement next; 
	
	@FindBy(how = How.ID, using = "myBtn")
	private WebElement botonTipoDeEntrega;
	
	@FindBy(how = How.ID, using = "DeliveryMethod")
	private WebElement tipoDeEntrega;
	
	@FindBy(how = How.ID, using = "SalesChannelConfiguration_nextBtn")
	private WebElement confirmaEntrega;
	
	@FindBy(how = How.CSS, using = ".vlc-slds-button--tertiary.ng-binding.ng-scope")
	private WebElement cancel;
	
	@FindBy(how = How.ID, using = "SearchClientsDummy")
	private WebElement buscar;
	
	@FindBy(how = How.ID, using = "ImpositiveCondition")
	private WebElement CondicionImpositiva;
	
	@FindBy(how = How.ID, using = "state-LegalAddress")
	private WebElement Provincia;

	@FindBy(how = How.CSS, using = ".slds-input.ng-pristine.ng-untouched.ng-empty.ng-invalid.ng-invalid-required")
	private WebElement Localidad;
	
	@FindBy(how = How.ID, using = "zoneType-LegalAddress")
	private WebElement Zona;
	
	@FindBy(how = How.CSS, using = ".slds-input.ng-pristine.ng-empty.ng-invalid.ng-invalid-required.ng-touched")
	private WebElement Calle;
	
	@FindBy(how = How.ID, using = "streetNumber-LegalAddress")
	private WebElement NumeroCalle;
	
	@FindBy(how = How.ID, using = "postalCode-LegalAddress")
	private WebElement CodigoPostal;
	
	@FindBy(how = How.ID, using = "addresssType-LegalAddress")
	private WebElement TipoDomicilio;
	
	@FindBy(how = How.ID, using = "neighborhood-LegalAddress")
	private WebElement Barrio;
	
	@FindBy(how = How.ID, using = "floor-LegalAddress")
	private WebElement Piso;
	
	@FindBy(how = How.ID, using = "department-LegalAddress")
	private WebElement Departamento;
	
	@FindBy(how = How.ID, using = "buildingType-LegalAddress")
	private WebElement TipoEdificio;
	
	@FindBy(how = How.ID, using = "buildingNumber-LegalAddress")
	private WebElement NumeroEdificio;
	
	@FindBy(how = How.ID, using = "btnSameAsLegalAddress")
	private WebElement BotonDireccionLegal;
	
	@FindBy(how = How.ID, using = "AccountData_nextBtn")
	private WebElement BotonCrearCliente;
	
	@FindBy(how = How.ID, using = "PDFForm_nextBtn")
	private WebElement botonSiguienteFormulario;
	
	public ContactSearch(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void searchContact(String docType, String docValue, String genero) {
		try {Thread.sleep(10000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		setSimpleDropdown(documentType, docType);
		document.click();
		document.sendKeys(docValue);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (genero) {
		case "femenino":
			gender.get(0).click();
			break;
		case "masculino":
			gender.get(1).click();
			break;
		}
		if(!genero.equals(""))
		driver.findElement(By.cssSelector(".OSradioButton.ng-scope.only-buttom")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}

	public void searchContact2(String docType, String docValue, String genero) {
		TestBase TB = new TestBase();
		TB.cambioDeFrame(driver, By.id("DocumentTypeSearch"), 0);
		setSimpleDropdown(documentType2, docType);
		driver.findElement(By.id("DocumentInputSearch")).click();
		driver.findElement(By.id("DocumentInputSearch")).sendKeys(docValue);
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (genero.toLowerCase()) {
		case "femenino":
			gender.get(0).click();
			break;
		case "masculino":
			gender.get(1).click();
			break;
		}
		if(!genero.equals(""))
			BotonCrearNuevoCliente.click();
		try {Thread.sleep(4000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	
	public void DNI(String DNI) {
		document.sendKeys(DNI);
	}

	public void sex(String genero) {
		switch (genero.toLowerCase()) {
		case "femenino":
			gender.get(0).click();
			break;
		case "masculino":
			gender.get(1).click();
			break;
		}
	}

	public void ingresarMail(String mail, String continuar) {
		driver.findElement(By.cssSelector(".slds-input.form-control.ng-pristine.ng-untouched.ng-valid.ng-empty")).click();
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		driver.findElement(By.xpath("//*[@id=\"EmailSelectableItems\"]/div/ng-include/div/ng-form/div[1]/div[1]/input")).sendKeys(mail);
		switch (continuar) {
		case "si":
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.id("Contact_nextBtn")).click();
			break; 
		case "no":
			//Nada
		}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void tipoValidacion(String tipoValidacion) {
		TestBase TB = new TestBase();
		TB.waitForClickeable(driver, By.id("MethodSelection_prevBtn"));
		switch (tipoValidacion) {
		case "documento":
			List<WebElement> valdni = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
			for (WebElement x : valdni) {
				if (x.getText().toLowerCase().equals("validaci\u00f3n por documento de identidad")) {
					x.click();
					break;
				}
			}
			break;
		case "preguntas y respuestas":
			List<WebElement> valqa = driver.findElements(By.cssSelector(".slds-form-element__label.ng-binding"));
			for (WebElement x : valqa) {
				if (x.getText().toLowerCase().equals("validaci\u00f3n por preguntas y respuestas")) {
					x.click();
					break;
				}
			}
			break;
		}
		driver.findElement(By.id("MethodSelection_nextBtn")).click();
		try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void tipoValidacion2(String tipoValidacion) {
		switch (tipoValidacion) {
		case "documento":
			List<WebElement> valdni = driver.findElements(By.cssSelector(".ta-radioBtnContainer.taBorderOverlay.slds-grid.slds-grid--align-center.slds-grid--vertical-align-center.ng-scope"));
			for (WebElement x : valdni) {
				if (x.getText().toLowerCase().equals("validaci\u00f3n por documento de identidad")) {
					x.click();
					break;
				}
			}
			break;
		case "preguntas y respuestas":
			List<WebElement> valqa = driver.findElements(By.cssSelector(".ta-radioBtnContainer.taBorderOverlay.slds-grid.slds-grid--align-center.slds-grid--vertical-align-center.ng-scope"));
			for (WebElement x : valqa) {
				if (x.getText().toLowerCase().equals("validaci\u00f3n por preguntas y respuestas")) {
					x.click();
					break;
				}
			}
			break;
		}
		driver.findElement(By.id("ValidationMethod_nextBtn")).click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void subirArchivo(String uploadPath, String continuar) {
		driver.findElement(By.id("FileDocumentImage")).sendKeys(uploadPath);
		try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (continuar) {
		case "si":
			try {Thread.sleep(2000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.id("DocumentMethod_nextBtn")).click();
			break;
		case "no":
			//Nada
		}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void validacionPorDNI() {
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.id("MethodSelection_prevBtn")));
		driver.findElement(By.xpath("//*[@class='slds-size--1-of-1 slds-radio slds-grid slds-box vlc-slds-selectableItem ng-scope']//h2[text()='Validaci\u00f3n por DNI']")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='ta-imageContainer btnImage']")));
		File directory = new File("Dni.jpg");
		driver.findElement(By.id("FileDocumentImage")).sendKeys(new File(directory.getAbsolutePath()).toString());
		sleep(3000);
		driver.findElement(By.id("DocumentMethod_nextBtn")).click();
		ges.getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[class='slds-button slds-button_brand']")));
		driver.findElement(By.cssSelector("[class='slds-button slds-button_brand']")).click();		
	}
	
	public void subirformulario(String uploadPath) {
		TestBase tb = new TestBase();
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		tb.cambioDeFrame(driver,(By.id("UploadSignedForm")),0);
//		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("UploadSignedForm")));
		driver.findElement(By.id("UploadSignedForm")).sendKeys(uploadPath);
		ges.getWait().until(ExpectedConditions.elementToBeClickable(botonSiguienteFormulario));
//		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		/*switch (Siguiente) {
		case "si":
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.id("PDFForm_nextBtn")).click();
			break;
		case "no":
			//Nada
		}*/
		botonSiguienteFormulario.click();
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void subir_cupos(String uploadPath, String continuar){
		driver.findElement(By.id("fileinput")).sendKeys(uploadPath);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (continuar) {
		case "si":
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.name("BTN-Importar")).click();
			break;
		case "no":
			//Nada
		}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
	public void subir_regiones(String uploadPath, String continuar){
		driver.findElement(By.id("fileinput")).sendKeys(uploadPath);
		try {Thread.sleep(3000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
		switch (continuar) {
		case "si":
			try {Thread.sleep(5000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
			driver.findElement(By.cssSelector(".btn.btn-primary.btn-sm.btn-block.btn-continuar")).click();
			break;
		case "no":
			//Nada
		}
		try {Thread.sleep(7000);} catch (InterruptedException ex) {Thread.currentThread().interrupt();}
	}
	
////////////////////////////////////////////////////////++++  METODOS NUEVOS ALTA DE LINEA ++++++//////////////////////////////////////////
		
	public void Llenar_Contacto(String sNom, String sAp, String sFN, String genero, String mail ) {
		TestBase TB = new TestBase();
		TB.waitFor(driver,By.id("FirstName"));
		nombre.clear();
		apellido.clear();
		fNac.clear();
		//Email.clear();
		nombre.sendKeys(sNom);
		apellido.sendKeys(sAp);
		fNac.sendKeys(sFN);
//		switch (genero.toLowerCase()) {
//		case "femenino":
//			gender.get(0).click();
//			break;
//		case "masculino":
//			gender.get(1).click();
//			break;
//		}
		Email.findElement(By.tagName("input")).sendKeys(mail);
		next.click();
	}
	
	public void buscarCuenta(String tipoDoc, String dni){ 
		TestBase tb = new TestBase();
		tb.cambioDeFrame(driver, By.id("SearchClientDocumentType"), 0);
		selectByText(documentType,tipoDoc);
		document.click();
		document.sendKeys(dni);
		tb.waitForClickeable(driver, By.id("SearchClientsDummy"));
		buscar.click();
	}
	
	public void seleccionarCatalogo(){
		TestBase tb = new TestBase();
		tb.waitFor(driver, By.id("tab-scoped-1"));
		List<WebElement> btns = driver.findElements(By.cssSelector(".slds-button.slds-button.slds-button--icon"));
		for(WebElement e: btns){
			if(e.getText().toLowerCase().equals("catalogo")){ 
				e.click();
				break;
			}
		}
	}
	
	public String crearCliente(String tipoDoc,String genero){
		TestBase tb = new TestBase();
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		tb.cambioDeFrame(driver,By.id("SearchClientDocumentType"),0);
		Random aleatorio = new Random(System.currentTimeMillis());
		aleatorio.setSeed(System.currentTimeMillis());
		int intAleatorio = aleatorio.nextInt(8999999)+1000000;
		selectByText(documentType,tipoDoc);
		document.click();
		document.sendKeys(Integer.toString(intAleatorio));
		switch (genero.toLowerCase()) {
		case "femenino":
			gender.get(0).click();
			break;
		case "masculino":
			gender.get(1).click();
			break;
		}
		buscar.click();
		ges.getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".OSradioButton.ng-scope.only-buttom")));
		BotonCrearNuevoCliente.click();
		return document.getAttribute("value");
	}
	
	
	public void completarDomicilio(String cProvincia, String cLocalidad, String cZona, String cCalle, String cNumCalle, String cCodPostal, String cTipoDomicilio){
		TestBase tb = new TestBase();
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		tb.cambioDeFrame(driver,(By.id("state-LegalAddress")),0);
		selectByText(CondicionImpositiva, "IVA Consumidor final");
		sleep(5000);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("state-LegalAddress")));
		selectByText(Provincia, cProvincia);
		sleep(750);
		Localidad.sendKeys(cLocalidad);
		Localidad.sendKeys(Keys.ENTER);
		sleep(1750);
		Zona.sendKeys(cZona);
		Calle.sendKeys(cCalle);
		NumeroCalle.sendKeys(cNumCalle);
		CodigoPostal.sendKeys(cCodPostal);
		selectByText(TipoDomicilio, cTipoDomicilio);
		BotonDireccionLegal.click();
		sleep(3000);
		BotonCrearCliente.click();
	}
	
	public void completarDomicilioSimple(String sProvincia, String sLocalidad, String sZona, String sCalle, String sNumCalle, String sCodPostal, String sTipoDomicilio, String sPiso, String sDepartamento){
	
		TestBase tb = new TestBase();
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		tb.cambioDeFrame(driver,(By.id("state-LegalAddress")),0);
		//selectByText(CondicionImpositiva, "IVA Consumidor final");
		selectByText(Provincia, sProvincia);
		sleep(750);
		Localidad.sendKeys(sLocalidad);
		Localidad.sendKeys(Keys.ENTER);
		sleep(1750);
		Zona.sendKeys(sZona);
		Calle.sendKeys(sCalle);
		NumeroCalle.sendKeys(sNumCalle);
		CodigoPostal.sendKeys(sCodPostal);
		selectByText(TipoDomicilio, sTipoDomicilio);
		Piso.sendKeys(sPiso);
		Departamento.sendKeys(sDepartamento);
		BotonDireccionLegal.click();
		sleep(3000);
		BotonCrearCliente.click();
	}
	
	public void completarDomicilioDetallado(String cProvincia, String cLocalidad, String cZona, String cCalle, String cNumCalle, String cCodPostal, String cTipoDomicilio, String cBarrio, String cPiso, String cDepartamento, String cTipoEdificio, String cNumEdificio){
		TestBase tb = new TestBase();
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		tb.cambioDeFrame(driver,(By.id("state-LegalAddress")),0);
		//selectByText(CondicionImpositiva, "IVA Consumidor final");
		selectByText(Provincia, cProvincia);
		sleep(750);
		Localidad.sendKeys(cLocalidad);
		Localidad.sendKeys(Keys.ENTER);
		sleep(1750);
		Zona.sendKeys(cZona);
		Calle.sendKeys(cCalle);
		NumeroCalle.sendKeys(cNumCalle);
		CodigoPostal.sendKeys(cCodPostal);
		selectByText(TipoDomicilio, cTipoDomicilio);
		Barrio.sendKeys(cBarrio);
		Piso.sendKeys(cPiso);
		Departamento.sendKeys(cDepartamento);
		selectByText(TipoEdificio, cTipoEdificio);
		NumeroEdificio.sendKeys(cNumEdificio);
		BotonDireccionLegal.click();
		sleep(3000);
		BotonCrearCliente.click();
	}
	
	public void elegirPlan(String plan){
		TestBase tb = new TestBase();
		GestionDeClientes_Fw ges = new GestionDeClientes_Fw(driver);
		tb.cambioDeFrame(driver, By.cssSelector(".slds-button.slds-button_neutral.icon-add_shopping_cart.icon-only.cpq-add-button"),0);
		tb.waitForClickeable(driver, By.xpath("//*[@class='slds-radio_button__label']//span[text()='Products']"));
		driver.findElement(By.xpath("//*[@class='slds-radio_button__label']//span[text()='Products']")).click();
		ges.getWait().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("[class='slds-media cpq-product-item-container']"), 0));
		driver.findElement(By.cssSelector(".slds-input.input-icon.ng-pristine.ng-untouched.ng-valid.ng-empty")).sendKeys(plan);
		sleep(2500);
		tb.waitForClickeable(driver, By.cssSelector(".slds-media.cpq-product-item-container"));
		driver.findElement(By.cssSelector("[class='slds-media cpq-product-item-container'] button")).click();
//		List<WebElement> productos = driver.findElements(By.cssSelector(".slds-media.cpq-product-item-container"));
//		List<WebElement> botones = driver.findElements(By.cssSelector(".slds-button.slds-button_neutral.icon-add_shopping_cart.icon-only.cpq-add-button"));
//		for (int i = 0; i <= productos.size(); i++) {
//			if (productos.get(i).getText().substring(0, productos.get(i).getText().indexOf("\n")).equalsIgnoreCase(plan)) {
//				botones.get(i).click();
//				break;
//			}
//		}
	}
	
	public void continuar(){
		TestBase tb = new TestBase();
		tb.waitForClickeable(driver, By.cssSelector(".slds-button.slds-button--brand.ta-button-brand"));
		driver.findElement(By.cssSelector(".slds-button.slds-button--brand.ta-button-brand")).click();
	}
	
	// +++++++++++++++++ falta terminar +++++++++++++++++++++++++++++
	public void tipoEntrega(String tipo){
		TestBase tb = new TestBase();
		botonTipoDeEntrega.click();
		sleep(5000);
		tb.cambioDeFrame(driver, By.id("DeliveryMethod"),0);
		//tb.waitForClickeable(driver, By.id("DeliveryMethod"));
		selectByText(tipoDeEntrega, tipo);
		confirmaEntrega.click();
		tb.waitForClickeable(driver, By.cssSelector(".slds-button.slds-button--brand.ta-button-brand"));
	}	
	
	public void crearClienteNominacion(String tipoDoc, String dni, String genero, String sNom, String sAp, String sFN, String mail){
		TestBase tb = new TestBase();
		selectByText(documentType2,tipoDoc);
		document2.click();
		document2.sendKeys(dni);
		switch (genero.toLowerCase()) {
		case "femenino":
			gender.get(0).click();
			break;
		case "masculino":
			gender.get(1).click();
			break;
		}
		try {
			BotonCrearNuevoCliente.click();
		} catch(Exception e) {
			driver.findElement(By.cssSelector("[class='OSradioButton radiobox ng-scope']")).click();
		}
		tb.waitFor(driver,By.id("FirstName"));
		// ESTO HAY QUE SACARLO, NO DEBERIA IR
		driver.findElement(By.xpath("//*[@id=\"Gende|0\"]/div/div[1]/label[1]/span[2]")).click();
		nombre.clear();
		nombre.sendKeys(sNom);
		apellido.clear();
		apellido.sendKeys(sAp);
		fNac.clear();
		fNac.sendKeys(sFN);
		Email.findElement(By.tagName("input")).clear();
		Email.findElement(By.tagName("input")).sendKeys(mail);
		next.click();
	}
	
	public void crearClienteNominacionPasaporte(String tipoDoc, String dni, String genero, String sNom, String sAp, String sFN, String mail, String fPemanencia){
		TestBase tb = new TestBase();
		tb.cambioDeFrame(driver, By.id("DocumentTypeSearch"), 0);
		selectByText(documentType2,tipoDoc);
		document2.click();
		document2.sendKeys(dni);
		switch (genero.toLowerCase()) {
		case "femenino":
			gender.get(0).click();
			break;
		case "masculino":
			gender.get(1).click();
			break;
		}
		BotonCrearNuevoCliente.click();
		tb.waitFor(driver,By.id("FirstName"));
		nombre.clear();
		nombre.sendKeys(sNom);
		apellido.clear();
		apellido.sendKeys(sAp);
		fNac.clear();
		fNac.sendKeys(sFN);
		Email.findElement(By.tagName("input")).clear();
		Email.findElement(By.tagName("input")).sendKeys(mail);
		FechaDePermanencia.clear();
		FechaDePermanencia.sendKeys(fPemanencia);
		next.click();
	}
}
