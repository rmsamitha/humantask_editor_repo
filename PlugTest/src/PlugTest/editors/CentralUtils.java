package plugtest.editors;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jface.text.IDocument;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.THumanInteractions;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class CentralUtils {
	private static CentralUtils centralUtils;
	private static THumanInteractions tHumanInteractions;
	private static IDocument iDocument;
	private static JAXBContext jaxbContext;
	

	public static CentralUtils getInstance(XMLEditor editor) throws JAXBException {
		if (centralUtils == null) {
			centralUtils = new CentralUtils();
			CentralUtils.settHumanInteractions(new THumanInteractions());
			CentralUtils.setiDocument(editor.getDocumentProvider().getDocument(
					editor.getEditorInput()));
			CentralUtils.setJaxbContext(JAXBContext
					.newInstance("org.oasis_open.docs.ns.bpel4people.ws_humantask._200803"));
		}
		return centralUtils;
	}

	public static THumanInteractions gettHumanInteractions() {
		return tHumanInteractions;
	}

	public static void settHumanInteractions(
			THumanInteractions tHumanInteractions) {
		CentralUtils.tHumanInteractions = tHumanInteractions;
	}
	
	public static IDocument getiDocument() {
		return iDocument;
	}

	public static void setiDocument(IDocument iDocument) {
		CentralUtils.iDocument = iDocument;
	}

	public static JAXBContext getJaxbContext() {
		return jaxbContext;
	}

	public static void setJaxbContext(JAXBContext jaxbContext) {
		CentralUtils.jaxbContext = jaxbContext;
	}
	public Document getDocument(XMLEditor editor) {
		String theText = iDocument.get();
		Document editorDocument = xmlRead(theText);
		return editorDocument;
	}

	public THumanInteractions docToTHumanInteraction(XMLEditor editor)
			throws JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		String theText = iDocument.get();
		Object obj = unmarshaller.unmarshal(new StringReader(theText));
		tHumanInteractions = ((THumanInteractions) obj);
		return tHumanInteractions;
	}

	public void marshalMe(XMLEditor editor) throws JAXBException {
		Marshaller marshaller = jaxbContext.createMarshaller();
		StringWriter sw = new StringWriter();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(tHumanInteractions, sw);
		iDocument = editor.getDocumentProvider().getDocument(
				editor.getEditorInput());
		iDocument.set(sw.toString());
	}

	// //////////////////////////////////// UTILS
	// //////////////////////////////////////////////////////

	public String docToString(Document doc) {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.transform(domSource, result);
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return writer.toString();

	}

	public Document xmlRead(String xmlString) {
		Document doc = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			doc = builder.parse(is);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

}
