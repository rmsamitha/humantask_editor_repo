package org.wso2.developerstudio.humantask.editor;

import org.eclipse.ui.editors.text.TextEditor;
import org.wso2.developerstudio.humantask.models.THumanInteractions;

public class XMLEditor extends TextEditor {

	private ColorManager colorManager;
	private THumanInteractions rootElement;
	
	public XMLEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
		rootElement =  new THumanInteractions();
	}
	
	public THumanInteractions getRootElement() {
		return rootElement;
	}
	public void setRootElement(THumanInteractions rootElement) {
		this.rootElement = rootElement;
	}
	
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
