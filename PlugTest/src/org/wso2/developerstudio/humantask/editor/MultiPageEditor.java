package org.wso2.developerstudio.humantask.editor;

import java.util.logging.Logger;

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

public class MultiPageEditor extends MultiPageEditorPart {

	private XMLEditor textEditor;
	private Transition baseUI;

	private final static Logger LOG = Logger.getLogger(MultiPageEditor.class
			.getName());

	public MultiPageEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener);

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
		// composite = new BaseView(textEditor, getContainer(), SWT.NONE);
		baseUI = new Transition(textEditor, getContainer(), SWT.NONE);
		int pageIndex = addPage(baseUI);
		setPageText(pageIndex, HTEditorConstants.UI_EDITOR_TITLE);
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
		super.dispose();
	}

	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException(
					"Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
	}

	public boolean isSaveAsAllowed() {
		return true;
	}

	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if (newPageIndex == 1) {
			try {
				CentralUtils centralUtils = CentralUtils
						.getInstance(textEditor);
				centralUtils.unmarshalMe(textEditor);
				baseUI.loadModel(textEditor.getRootElement().getTasks());
				centralUtils.redraw();
			} catch (JAXBException e) {
				LOG.info(e.getMessage());
			}
		}
	}

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
}
