package plugtest.editors;


import java.awt.TextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
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
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.*;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.preferences.PropertyListenerList;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.THumanInteractions;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


/**
 * An example showing how to create a multi-page editor.
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class MultiPageEditor extends MultiPageEditorPart{

	/** The text editor used in page 0. */
	private XMLEditor editor;
	private THumanInteractions tHumanInteractions;
	/** The font chosen in page 1. */
	private Font font;

	/** The text widget used in page 2. */
	private StyledText text;
	/**
	 * Creates a multi-page editor example.
	 */
	public MultiPageEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener);
	}
	/**
	 * Creates page 0 of the multi-page editor,
	 * which contains a text editor.
	 */
	
	
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
			
			System.out.println(indent.concat("Delta " + d.getKind() + " for resource " + resourceName));
							
			for (IResourceDelta childDelta : d.getAffectedChildren()) {
				printDelta(childDelta, indent.concat("\t"));
			}
		}
		public void resourceChanged(final IResourceChangeEvent event){
			
			if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
				Display.getDefault().asyncExec(new Runnable(){
					public void run(){
						IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
						for (int i = 0; i<pages.length; i++){
							if(((FileEditorInput)editor.getEditorInput()).getFile().getProject().equals(event.getResource())){
								IEditorPart editorPart = pages[i].findEditor(editor.getEditorInput());
								pages[i].closeEditor(editorPart,true);
							}
						}
					}            
				});
			}
			
			if (event.getType() == IResourceChangeEvent.POST_CHANGE) {
				/*try {
					System.out.println(getObject());
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				IResourceDelta delta = event.getDelta();
			//	printDelta(delta, "");
*/				
			} else {
				//System.out.println("Event type was " + event.getType());
			}
		}
	};
	
	public THumanInteractions getObject() throws JAXBException{
		IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
		String text=document.get();
		JAXBContext jaxbContext = JAXBContext.newInstance("org.oasis_open.docs.ns.bpel4people.ws_humantask._200803"); //MAKE SURE THE SAME PACAKGE NAME GIVEN IN STEP 4
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		
		return loadXML(new StringReader(text), unmarshaller);
		
		
	}
	
	public THumanInteractions loadXML(Reader istrm,Unmarshaller unmarshaller) {

		try {
			
		Object obj = unmarshaller.unmarshal(istrm);

		if(tHumanInteractions == null) {

		tHumanInteractions = ((JAXBElement<THumanInteractions>)obj).getValue();

		return(tHumanInteractions);

		}

		} catch (JAXBException e) {

		e.printStackTrace();

		}

		return null;

		}

		
	
	
	void createPage0() {
		try {
			editor = new XMLEditor();
			int index = addPage(editor, getEditorInput());
			setPageText(index, editor.getTitle());
		
		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),
				"Error creating nested text editor",
				null,
				e.getStatus());
		}
	}
	/**
	 * Creates page 1 of the multi-page editor,
	 * which allows you to change the font used in page 2.
	 */
	void createPage1() {
		
		Composite3 composite = new Composite3(editor, getContainer(), SWT.NONE);
		/*GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 2;
		final Text element = new Text(composite, SWT.NONE);
		final Text element2 = new Text(composite, SWT.NONE);
		Button fontButton = new Button(composite, SWT.NONE);
		GridData gd = new GridData(GridData.BEGINNING);
		gd.horizontalSpan = 2;
		element.setLayoutData(gd);
		element2.setLayoutData(gd);
		fontButton.setLayoutData(gd);
		fontButton.setText("Write XML...");*/
		///////////////////////////////////////////////////////////////////////////
		/*FormToolkit toolkit = new FormToolkit(Display.getCurrent());
		Section secLPGItem = toolkit.createSection(composite, Section.TWISTIE | Section.TITLE_BAR);
		FormData fd_secLPGItem = new FormData();
		fd_secLPGItem.bottom = new FormAttachment(0, 48);
		fd_secLPGItem.right = new FormAttachment(100, -10);
		fd_secLPGItem.top = new FormAttachment(0, 5);
		fd_secLPGItem.left = new FormAttachment(0, 5);
		secLPGItem.setLayoutData(fd_secLPGItem);
		//toolkit.paintBordersFor(secLPGItem);
		secLPGItem.setText("LogicalPeopleGroup 1");
		secLPGItem.setExpanded(true);
		Text txtNewText = new Text(composite, SWT.NONE);
		txtNewText = toolkit.createText(secLPGItem, "New Text", SWT.NONE);
		secLPGItem.setTextClient(txtNewText);*/
		
		
		
		
		
		
		///////////////////////////////////////////////////////////////////////////////
		
		
		/*fontButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				//writeXML(element,element2);
				ViewHandler view=new ViewHandler(editor);
				view.setTag("PresentationParameter", "huki", 0);
				try {
					CentralHandler.getInstance().marshalMe(editor);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});*/

		int index = addPage(composite);
		setPageText(index, "Properties");
	}
	/**
	 * Creates page 2 of the multi-page editor,
	 * which shows the sorted text.
	 */
	void writeXML(Text element,Text element2){
			try {
			FileEditorInput fi=	(FileEditorInput)editor.getEditorInput();
			IFile file=fi.getFile();
			System.out.print(file.getRawLocation().makeAbsolute().toFile());
    		//FileOutputStream outStream = new FileOutputStream(file.getRawLocation().makeAbsolute().toFile());
    		StringWriter sw=new StringWriter();
			XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(sw);
    		//Field [] field=htmodel.class.getFields();
			IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			
    		writer.writeStartElement(element.getText());
    		writer.writeCharacters(element2.getText());
    		writer.writeEndElement();
    		
    		//System.out.print(sw.getBuffer().toString());
    		
    		writer.flush();
    		writer.close();
    		String theText=document.get();
    		Document editorDocument = xmlRead(theText);
    		NodeList nodeList=editorDocument.getElementsByTagName(element.getText());
    		if(nodeList.getLength() != 0){ //validate for value also
    			nodeList.item(0).setTextContent(element.getText());
    			document.set(docToString(editorDocument));
    		}else{
    			document.set(theText.concat(sw.getBuffer().toString()));
    		}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public String docToString(Document doc){
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			transformer.transform(domSource, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
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
			InputSource is= new InputSource(new StringReader(xmlString));
			doc = builder.parse(is);
			doc.getDocumentElement().normalize();
			System.out.print(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	void createPage2() {
		Composite composite = new Composite(getContainer(), SWT.NONE);
		FillLayout layout = new FillLayout();
		composite.setLayout(layout);
		text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
		text.setEditable(false);

		int index = addPage(composite);
		setPageText(index, "Preview");
	}
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createPage0();
		createPage1();
		createPage2();
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
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart
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
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
	}
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
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
			sortWords();
		}
	}
	/**
	 * Closes all project files on project close.
	 */
	
	/**
	 * Sets the font related data to be applied to the text in page 2.
	 */
	void setFont() {
		FontDialog fontDialog = new FontDialog(getSite().getShell());
		fontDialog.setFontList(text.getFont().getFontData());
		FontData fontData = fontDialog.open();
		if (fontData != null) {
			if (font != null)
				font.dispose();
			font = new Font(text.getDisplay(), fontData);
			text.setFont(font);
		}
	}
	/**
	 * Sorts the words in page 0, and shows them in page 2.
	 */
	void sortWords() {

		String editorText =
			editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();

		StringTokenizer tokenizer =
			new StringTokenizer(editorText, " \t\n\r\f!@#\u0024%^&*()-_=+`~[]{};:'\",.<>/?|\\");
		ArrayList editorWords = new ArrayList();
		while (tokenizer.hasMoreTokens()) {
			editorWords.add(tokenizer.nextToken());
		}

		Collections.sort(editorWords, Collator.getInstance());
		StringWriter displayText = new StringWriter();
		for (int i = 0; i < editorWords.size(); i++) {
			displayText.write(((String) editorWords.get(i)));
			displayText.write(System.getProperty("line.separator"));
		}
		text.setText(displayText.toString());
	}
}
