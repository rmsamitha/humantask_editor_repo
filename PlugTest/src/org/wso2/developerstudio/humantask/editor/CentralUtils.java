package org.wso2.developerstudio.humantask.editor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.util.StreamReaderDelegate;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.wso2.developerstudio.htconfig.models.TCallback;
import org.wso2.developerstudio.htconfig.models.THTDeploymentConfig;
import org.wso2.developerstudio.htconfig.models.THTDeploymentConfig.Notification;
import org.wso2.developerstudio.htconfig.models.THTDeploymentConfig.Task;
import org.wso2.developerstudio.htconfig.models.TPublish;
import org.wso2.developerstudio.htconfig.models.TPublish.Service;
import org.wso2.developerstudio.humantask.models.THumanInteractions;
import org.wso2.developerstudio.humantask.models.TImport;
import org.wso2.developerstudio.humantask.models.TNotification;
import org.wso2.developerstudio.humantask.models.TTask;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CentralUtils {
	private IDocument iDocument;
	private Composite baseContainer;
	private ScrolledComposite scrolledComposite;

	/**
	 * Returns a single CentralUtils object when it is called
	 * @param XMLEditor textEditor
	 * @return CentralUtils
	 */
	public static CentralUtils getInstance(XMLEditor textEditor)
			throws JAXBException {
		if (textEditor.getCentralUtils() == null) {
			textEditor.setCentralUtils(new CentralUtils());
			textEditor.getCentralUtils().setiDocument(
					textEditor.getDocumentProvider().getDocument(
							textEditor.getEditorInput()));
			textEditor.setJaxbContext(JAXBContext
					.newInstance(HTEditorConstants.HT_JAXB_CONTEXT));
			textEditor.setConfigJaxbContext(JAXBContext
					.newInstance(HTEditorConstants.HT_CONFIG_JAXB_CONTEXT));
		}
		return textEditor.getCentralUtils();
	}

	/**
	 * Unmarshals the text editor text to Object Model
	 * @param XMLEditor textEditor
	 */
	public void unmarshal(XMLEditor textEditor) throws JAXBException {
		Unmarshaller unmarshaller = textEditor.getJaxbContext()
				.createUnmarshaller();
		String textEditorText = iDocument.get();
		Object marshalledObject = null;
       // try {
	        marshalledObject = unmarshaller.unmarshal(new StringReader(
	        		textEditorText));
        
		if (marshalledObject instanceof JAXBElement<?>) {
			JAXBElement<THumanInteractions> rootElementObject = (JAXBElement<THumanInteractions>) unmarshaller
					.unmarshal(new StringReader(textEditorText));
			THumanInteractions tHumanInteractions = (rootElementObject
					.getValue());
			textEditor.setRootElement(tHumanInteractions);
		} else {
			THumanInteractions rootElementObject = (THumanInteractions) unmarshaller
					.unmarshal(new StringReader(textEditorText));
			THumanInteractions tHumanInteractions = (rootElementObject);
			textEditor.setRootElement(tHumanInteractions);
		}
//} catch (javax.xml.bind.UnmarshalException e) {
	//        e.printStackTrace();
        //}

	}

	/**
	 * Marshals the current object model into the text editor
	 * @param XMLEditor textEditor
	 */
	public void marshal(XMLEditor textEditor) throws JAXBException {
		Marshaller marshaller = textEditor.getJaxbContext().createMarshaller();
		StringWriter stringWriter = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(textEditor.getRootElement(), stringWriter);
		iDocument = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());
		iDocument.set(stringWriter.toString().trim());
	}
	
	/**
	 * Sets the basic structure of the UI editor
	 * @param ScrolledComposite scrolledComposite
	 * @param Composite baseConatiner
	 */
	public void setBasicUI(ScrolledComposite scrolledComposite,
			Composite baseConatiner) {
		this.scrolledComposite = scrolledComposite;
		this.baseContainer = baseConatiner;
	}

	public void redraw() {
		scrolledComposite.setMinSize(baseContainer.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
	}
	
	public Document getotherattrs() throws ParserConfigurationException {
/*		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		StringReader InputStream=new StringReader(iDocument.get()); 
		InputStream inp=new 
		Document editorDocument = builder.parse(InputStream);*/
		
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(iDocument.get()));
	//	is.setCharacterStream(new StringReader(iDocument.get()));
		System.out.println();
		
		Document doc=null;
		try {
			 doc = db.parse(is);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(doc!=null)
		{
			System.out.println("dong");
		}
		return doc;
	}

	public Document getWSDL(XMLEditor textEditor)
			throws ParserConfigurationException, SAXException, IOException {
		List<TImport> importList = new ArrayList<TImport>();
		String filePath = null;
		if(textEditor!=null){
			importList=textEditor.getRootElement().getImport();
		}
		for (int importIndex = 0; importIndex < importList.size(); importIndex++) {
			if (importList.get(importIndex).getImportType()
					.equals(HTEditorConstants.HTTP_SCHEMAS_XMLSOAP_ORG_WSDL)
					&& importList.get(importIndex).getLocation() != null) {
				filePath = importList.get(importIndex).getLocation();
			}
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream source = new ByteArrayInputStream(
				HTEditorConstants.DOCUMENT_ROOT.getBytes());
		Document editorDocument = builder.parse(source);
		if (filePath != null) {
			File kfile = new File(filePath);
			editorDocument = xmlRead(kfile.getAbsolutePath().toString());
		}
		return editorDocument;
	}

	public void fillConfigObject(XMLEditor textEditor)
            throws ParserConfigurationException, SAXException, IOException,
            JAXBException, NullPointerException {
        THTDeploymentConfig tHTDeploymentConfig = new THTDeploymentConfig();
        List<TTask> tasksList = textEditor.getRootElement().getTasks()
                .getTask();
        List<TNotification> notificationList = textEditor.getRootElement()
                .getNotifications().getNotification();
        for (int taskIndex = 0; taskIndex < tasksList.size(); taskIndex++) {
            Task configTask = new Task();
            configTask.setName(new QName(tasksList.get(taskIndex).getName()));
            TPublish tPublish = new TPublish();
            Service service = new Service();
            service.setName(new QName(tasksList.get(taskIndex).getInterface()
                    .getOperation()));
            service.setPort(tasksList.get(taskIndex).getInterface()
                    .getPortType().getLocalPart());
            tPublish.setService(service);
            configTask.setPublish(tPublish);
            TCallback tResponse = new TCallback();
            TCallback.Service callbackService = new TCallback.Service();
            callbackService.setName(new QName(tasksList.get(taskIndex)
                    .getInterface().getResponseOperation()));
            callbackService.setPort(tasksList.get(taskIndex).getInterface()
                    .getResponsePortType().getLocalPart());
            tResponse.setService(callbackService);
            configTask.setCallback(tResponse);
            tHTDeploymentConfig.getTask().add(configTask);
        }

        for (int notificationIndex = 0; notificationIndex < notificationList
                .size(); notificationIndex++) {
            Notification configNotification = new Notification();
            configNotification.setName(new QName(notificationList.get(
                    notificationIndex).getName()));
            TPublish tPublish = new TPublish();
            Service service = new Service();
            service.setName(new QName(notificationList.get(notificationIndex)
                    .getInterface().getOperation()));
            service.setPort(notificationList.get(notificationIndex)
                    .getInterface().getPortType().getLocalPart());
            tPublish.setService(service);
            configNotification.setPublish(tPublish);
            tHTDeploymentConfig.getNotification().add(configNotification);
        }
        textEditor.setConfigRootElement(tHTDeploymentConfig);
    }

	public void writeHTConfig(XMLEditor textEditor)
			throws ParserConfigurationException, SAXException, IOException,
			JAXBException, CoreException {
		THTDeploymentConfig rootElement = textEditor.getConfigRootElement();
		Marshaller marshaller = textEditor.getConfigJaxbContext()
				.createMarshaller();
		StringWriter sw = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(rootElement, sw);
		IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(getProjectName());
		IFile file = project.getFile(HTEditorConstants.HTCONFIG_FILE_NAME);
		byte[] bytes = sw.toString().getBytes();
		InputStream source = new ByteArrayInputStream(bytes);

		if (!file.exists()) {
			file.create(source, IResource.NONE, null);
		} else {
			file.setContents(source, 0, null);
		}
	}

	public String getProjectName() {
		IEditorPart editorPart = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		String activeProjectName = null;

		if (editorPart != null) {
			IFileEditorInput input = (IFileEditorInput) editorPart
					.getEditorInput();
			IFile file = input.getFile();
			IProject activeProject = file.getProject();
			activeProjectName = activeProject.getName();
		}
		return activeProjectName;
	}

	public String docToString(Document document) throws TransformerException {
		DOMSource domSource = new DOMSource(document);
		StringWriter stringWriter = new StringWriter();
		StreamResult streamResult = new StreamResult(stringWriter);
		TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		Transformer transformer;
		transformer = transformerFactory.newTransformer();
		transformer.transform(domSource, streamResult);
		return stringWriter.toString();
	}

	public Document xmlRead(String xmlFilePath)
			throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File(xmlFilePath);
		Document document = null;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		try{
		document = documentBuilder.parse(xmlFile);
		}catch(FileNotFoundException e){
			MessageDialog.openError(Display.getDefault().getActiveShell(), HTEditorConstants.INTERNAL_ERROR,HTEditorConstants.FILE_NOT_FOUND);
		}
		document.getDocumentElement().normalize();
		return document;
	}

	public IDocument getiDocument() {
		return iDocument;
	}

	public void setiDocument(IDocument iDocument) {
		this.iDocument = iDocument;
	}

}