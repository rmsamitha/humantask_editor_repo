package org.wso2.developerstudio.humantask.editor;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.wso2.developerstudio.humantask.models.THumanInteractions;

public class CentralUtils {
	private static CentralUtils centralUtils;
	private static JAXBContext jaxbContext;
	private IDocument iDocument;
	private Composite baseContainer;
	private ScrolledComposite scrolledComposite;

	public static CentralUtils getInstance(XMLEditor textEditor)
			throws JAXBException {
		if (centralUtils == null) {
			centralUtils = new CentralUtils();
			centralUtils.setiDocument(textEditor.getDocumentProvider()
					.getDocument(textEditor.getEditorInput()));
			CentralUtils
					.setJaxbContext(JAXBContext
							.newInstance(HTEditorConstants.HT_JAXB_CONTEXT));
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

	public void unmarshalMe(XMLEditor textEditor) throws JAXBException {
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
	// //////////////////////UI Utils////////////////////////////////
	public void setBasicUI(ScrolledComposite scrolledComposite,
			Composite baseConatiner) {
		this.scrolledComposite = scrolledComposite;
		this.baseContainer = baseConatiner;
	}

	public void redraw() {
		scrolledComposite.setMinSize(baseContainer.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
	};

}
