package org.wso2.developerstudio.humantask.editor;

import org.eclipse.ui.editors.text.TextEditor;

public class MyEditor extends TextEditor {

	private ColorManager colorManager;

	public MyEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
