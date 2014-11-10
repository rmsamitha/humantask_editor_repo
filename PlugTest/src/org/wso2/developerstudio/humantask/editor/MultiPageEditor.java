/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.developerstudio.humantask.editor;

import javax.xml.bind.JAXBException;

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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;

/**
 * This class is the main entry point for the whole editor. This class
 * initialized the two editors (Text editor and the UI editor) and implements
 * logics which effects the both editors
 * 
 */
public class MultiPageEditor extends MultiPageEditorPart {
	private XMLEditor textEditor;
	private Transition baseUI;

	/**
	 * Constructs new Multi Page Editor
	 */
	public MultiPageEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createPages()
	 */
	@Override
	protected void createPages() {
		createPage0();
		createPage1();
	}

	/**
	 * Creates the page 0 which is the text editor
	 */
	void createPage0() {
		try {
			textEditor = new XMLEditor();
			int pageIndex = addPage(textEditor, getEditorInput());
			setPageText(pageIndex, textEditor.getTitle());
		} catch (PartInitException e) {
			ErrorDialog.openError(getSite().getShell(), "Error creating nested text editor", null,
			                      e.getStatus());
		}
	}

	/**
	 * Creates the page 1 which is the UI editor
	 */
	void createPage1() {
		baseUI = new Transition(textEditor, getContainer(), SWT.NONE);
		int pageIndex = addPage(baseUI);
		setPageText(pageIndex, HTEditorConstants.UI_EDITOR_TITLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#dispose()
	 */
	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	/**
	 * @param marker
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.MultiPageEditorPart#init(org.eclipse.ui.IEditorSite,
	 * org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput)) {
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		}
		super.init(site, editorInput);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.MultiPageEditorPart#pageChange(int)
	 */
	@Override
	protected void pageChange(int newPageIndex) {

		if (newPageIndex == 1) {
			try {

				CentralUtils centralUtils = CentralUtils.getCentralUtils(textEditor);
				if (!centralUtils.getiDocument().get().trim().equals("") ||
				    centralUtils.getiDocument().get() != null) {
					centralUtils.unmarshal(textEditor);
					baseUI.baseContainer.setVisible(true);
					baseUI.loadModel(textEditor.getRootElement());
					centralUtils.redraw();
					super.pageChange(newPageIndex);
				}
			} catch (JAXBException e) {

				if (textEditor.getRootElement() == null) {
					MessageDialog.openError(Display.getDefault().getActiveShell(),
					                        HTEditorConstants.XML_PARSE_ERROR,
					                        HTEditorConstants.XML_PARSE_ERROR_UI_DESIGN_CANNOT_BE_VIEWED);
				}
				if (textEditor.getRootElement() != null) {
					
					MessageDialog.openError(Display.getDefault().getActiveShell(),
					                        HTEditorConstants.XML_PARSE_ERROR,
					                        HTEditorConstants.XML_PARSE_ERROR_UI_DESIGN_CANNOT_BE_VIEWED);
					textEditor.setRootElement(null);
					try {
						baseUI.loadModel(textEditor.getRootElement());//
						baseUI.baseContainer.setVisible(false);

					} catch (JAXBException e1) {

						MessageDialog.openError(Display.getDefault().getActiveShell(),
						                        HTEditorConstants.XML_PARSE_ERROR,
						                        HTEditorConstants.XML_PARSE_ERROR_UI_DESIGN_CANNOT_BE_VIEWED);

					}
				}
				MessageDialog.openError(Display.getDefault().getActiveShell(),
				                        HTEditorConstants.XML_PARSE_ERROR,
				                        HTEditorConstants.XML_PARSE_ERROR_UI_DESIGN_CANNOT_BE_VIEWED);

			}
		}
	}

	IResourceChangeListener listener = new IResourceChangeListener() {
		@SuppressWarnings("unused")
		public void printDelta(final IResourceDelta d, final String indent) {

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

			for (IResourceDelta childDelta : d.getAffectedChildren()) {
				printDelta(childDelta, indent.concat("\t"));
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.core.resources.IResourceChangeListener#resourceChanged
		 * (org.eclipse.core.resources.IResourceChangeEvent)
		 */
		@Override
		public void resourceChanged(final IResourceChangeEvent event) {

			if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
						for (IWorkbenchPage page : pages) {
							if (((FileEditorInput) textEditor.getEditorInput()).getFile()
							                                                   .getProject()
							                                                   .equals(event.getResource())) {
								IEditorPart editorPart =
								                         page.findEditor(textEditor.getEditorInput());
								page.closeEditor(editorPart, true);
							}
						}
					}
				});
			}

		}
	};
}
