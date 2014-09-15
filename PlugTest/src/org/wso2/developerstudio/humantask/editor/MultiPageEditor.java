package org.wso2.developerstudio.humantask.editor;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.*;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.ide.IDE;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.THumanInteractions;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class MultiPageEditor extends MultiPageEditorPart {

	private XMLEditor textEditor;
	private THumanInteractions rootElement;
	private final static Logger LOG = Logger.getLogger(MultiPageEditor.class
			.getName());

	public MultiPageEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener);
		setRootElement(new THumanInteractions());
	}

	public THumanInteractions getRootElement() {
		return rootElement;
	}

	public void setRootElement(THumanInteractions rootElement) {
		this.rootElement = rootElement;
	}

	protected void createPages() {
		createPage0();
		createPage1();
	}

	void createPage0() {
		try {
			textEditor = new XMLEditor();
			int pageIndex = addPage(textEditor, getEditorInput());
			setPageText(pageIndex, textEditor.getTitle());
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(),
					"Error creating nested text editor", null, e.getStatus());
		}
	}

	void createPage1() {
		BaseView composite = new BaseView(textEditor, getContainer(), SWT.NONE);
		int pageIndex = addPage(composite);
		setPageText(pageIndex, HTEditorConstants.UI_EDITOR_TITLE);
	}

	public THumanInteractions getRootObject() throws JAXBException {
		IDocument textEditorDocument = textEditor.getDocumentProvider()
				.getDocument(textEditor.getEditorInput());
		String textEditorText = textEditorDocument.get();
		JAXBContext jaxbContext = JAXBContext
				.newInstance(HTEditorConstants.HT_DEFAULT_NAMESPACE);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return loadXML(new StringReader(textEditorText), unmarshaller);
	}

	public THumanInteractions loadXML(Reader editorTextReader,
			Unmarshaller unmarshaller) throws JAXBException {
		Object modelObject = unmarshaller.unmarshal(editorTextReader);
		if (rootElement == null) {
			rootElement = ((JAXBElement<THumanInteractions>) modelObject)
					.getValue();
		}
		return rootElement;
	}

	public String docToString(Document doc) throws TransformerException {
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		transformer = tf.newTransformer();
		transformer.transform(domSource, result);

		return writer.toString();
	}

	public Document xmlRead(String xmlText)
			throws ParserConfigurationException, SAXException, IOException {
		Document document = null;
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		InputSource inputSource = new InputSource(new StringReader(xmlText));
		document = documentBuilder.parse(inputSource);
		document.getDocumentElement().normalize();
		System.out.print(document);

		return document;
	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
		super.dispose();
	}

	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	/**
	 * Saves the multi-page editor's document as another file. Also updates the
	 * text for page 0's tab, and updates this multi-page editor's input to
	 * correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException(
					"Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
	}

	/*
	 * (non-Javadoc) Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if (newPageIndex == 2) {
			// Write Page Change Logic
		}
	}
	
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////
	IResourceChangeListener listener = new IResourceChangeListener() {
		public void printDelta(IResourceDelta d, String indent) {

			IResource resource = d.getResource();
			IJavaElement javaElement = JavaCore.create(resource);
			String resourceName = null;
			if (javaElement instanceof IJavaProject) {
				resourceName = "Java project " + resource.getName();
			} else if (javaElement instanceof IPackageFragmentRoot) {
				resourceName = "PFR " + resource.getName();
			} else if (javaElement instanceof IPackageFragment) {
				resourceName = "PF " + resource.getName();

			} else if (javaElement instanceof ICompilationUnit) {
				resourceName = "CU " + resource.getName();
			} else {
				resourceName = resource.getName();
			}

			System.out.println(indent.concat("Delta " + d.getKind()
					+ " for resource " + resourceName));

			for (IResourceDelta childDelta : d.getAffectedChildren()) {
				printDelta(childDelta, indent.concat("\t"));
			}
		}

		public void resourceChanged(final IResourceChangeEvent event) {

			if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						IWorkbenchPage[] pages = getSite().getWorkbenchWindow()
								.getPages();
						for (int i = 0; i < pages.length; i++) {
							if (((FileEditorInput) textEditor.getEditorInput())
									.getFile().getProject()
									.equals(event.getResource())) {
								IEditorPart editorPart = pages[i]
										.findEditor(textEditor.getEditorInput());
								pages[i].closeEditor(editorPart, true);
							}
						}
					}
				});
			}

			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				/*
				 * try { System.out.println(getObject()); } catch (JAXBException
				 * e) { }
				 * 
				 * 
				 * IResourceDelta delta = event.getDelta(); // printDelta(delta,
				 * "");
				 */
			} else {
				// System.out.println("Event type was " + event.getType());
			}
		}
	};

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////
}
