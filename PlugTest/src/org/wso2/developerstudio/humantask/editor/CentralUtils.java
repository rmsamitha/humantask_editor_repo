package org.wso2.developerstudio.humantask.editor;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jface.text.IDocument;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.THumanInteractions;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CentralUtils {
	private static CentralUtils centralUtils;
	private static JAXBContext jaxbContext;
	private IDocument iDocument;

	public static CentralUtils getInstance(XMLEditor textEditor)
			throws JAXBException {
		if (centralUtils == null) {
			centralUtils = new CentralUtils();
			centralUtils.setiDocument(textEditor.getDocumentProvider()
					.getDocument(textEditor.getEditorInput()));
			CentralUtils
					.setJaxbContext(JAXBContext
							.newInstance("org.oasis_open.docs.ns.bpel4people.ws_humantask._200803"));
		}
		return centralUtils;
	}

	public IDocument getiDocument() {
		return iDocument;
	}

	public void setiDocument(IDocument iDocument) {
		this.iDocument = iDocument;
	}

	public static JAXBContext getJaxbContext() {
		return jaxbContext;
	}

	public static void setJaxbContext(JAXBContext jaxbContext) {
		CentralUtils.jaxbContext = jaxbContext;
	}



	public void unmarshalMe(XMLEditor textEditor)
			throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		String textEditorText = iDocument.get();
		Object rootElementObject = unmarshaller.unmarshal(new StringReader(
				textEditorText));
		THumanInteractions tHumanInteractions = ((THumanInteractions) rootElementObject);
		textEditor.setRootElement(tHumanInteractions);
	}

	public void marshalMe(XMLEditor textEditor) throws JAXBException {
		Marshaller marshaller = jaxbContext.createMarshaller();
		StringWriter stringWriter = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(textEditor.getRootElement(), stringWriter);
		iDocument = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());
		iDocument.set(stringWriter.toString());
	}
	
	public void testMarshalMe(XMLEditor textEditor) throws JAXBException {
		Marshaller marshaller = jaxbContext.createMarshaller();
		StringWriter stringWriter = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		Node node=marshaller.getNode(textEditor.getRootElement());
		node.setTextContent("asdasd");
		marshaller.marshal(textEditor.getRootElement(), stringWriter);
		iDocument = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());
		iDocument.set(stringWriter.toString());
	}

	// ////////////////////////////////////DOM UTILS
	// //////////////////////////////////////////////////////
	
	public Document getDocument(XMLEditor textEditor) throws ParserConfigurationException, SAXException, IOException {
		String textEditorText = iDocument.get();
		Document editorDocument = xmlRead(textEditorText);
		return editorDocument;
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

	public Document xmlRead(String textEditorText)
			throws ParserConfigurationException, SAXException, IOException {
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource inputStream = new InputSource(new StringReader(
				textEditorText));
		document = builder.parse(inputStream);
		document.getDocumentElement().normalize();

		return document;
	}

}
