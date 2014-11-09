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

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * This is the abstract class extended by all the UI classes representing each
 * xml element which do not have child elements in the .ht file. The basic and
 * common UI related declarations and button listners are included here. All the
 * UI classes which are representing xml elements which do not have child
 * elements (end tags) extend this abstract class. This
 * AbstractEndTagSection.java class itself is extended by
 * org.eclipse.ui.forms.widgets.Section and so this is actually a SWT section.
 * There are number of sub classes of this AbstractEndTagSection class and
 * the logics and method implementations are almost same in those all sub classes.
 * One such sub class TDocumentationUI is comprehensively  
 * commented for better understanding of a reader.
 */
public abstract class AbstractEndTagSection extends Section {
	protected final FormToolkit formToolkit;
	protected CentralUtils centralUtils;
	protected ArrayList<Widget> textBoxesList;// List to keep Text
	// boxes(org.eclipse.swt.widgets.Text)
	// and combo boxes representing
	// xml attributes
	protected Composite detailArea; // Area to contain attribute table
	protected Composite parentTagContainer; // Keeps reference of the section's
	// parent tag section
	protected XMLEditor xmlEditor;
	protected ToolItem btnUpdate;

	/**
	 * The basic and common UI related declarations and Update and Remove button
	 * listners are included here.
	 * @param textEditor
	 * @param parentComposite
	 * @param parentTagContainer
	 * @param styleBit
	 * @param sectionTitle
	 * @throws JAXBException
	 */
	public AbstractEndTagSection(final XMLEditor textEditor, Composite parentComposite,
	                             Composite parentTagContainer, int styleBit, String sectionTitle)
	                                                                                             throws JAXBException {
		super(parentComposite, ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
		this.xmlEditor = textEditor;
		formToolkit = new FormToolkit(Display.getCurrent());
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		setText(sectionTitle);
		setExpanded(false);
		textBoxesList = new ArrayList<Widget>();
		this.parentTagContainer = parentTagContainer;
		setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});

		setTitleBarBackground(new Color(getDisplay(), 228, 210, 247));
		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		centralUtils = CentralUtils.getInstance(textEditor);
		RowLayout sectionRowLayout = new RowLayout();
		sectionRowLayout.type = SWT.VERTICAL;
		this.setLayout(sectionRowLayout);
		formToolkit.paintBordersFor(this);

		// All the components of the section are in this ScrolledComposite
		final ScrolledComposite sectionParentScrolledComposite =
		                                                         new ScrolledComposite(
		                                                                               this,
		                                                                               SWT.H_SCROLL |
		                                                                                       SWT.V_SCROLL);
		sectionParentScrolledComposite.setBackground(SWTResourceManager.getColor(165, 42, 42));
		formToolkit.adapt(sectionParentScrolledComposite);
		formToolkit.paintBordersFor(sectionParentScrolledComposite);
		this.setClient(sectionParentScrolledComposite);
		sectionParentScrolledComposite.setExpandHorizontal(true);
		sectionParentScrolledComposite.setExpandVertical(true);

		detailArea = formToolkit.createComposite(sectionParentScrolledComposite, SWT.NONE);
		formToolkit.adapt(detailArea);
		formToolkit.paintBordersFor(detailArea);
		sectionParentScrolledComposite.setContent(detailArea);
		GridLayout detailAreaGridLayout = new GridLayout(1, false);
		detailAreaGridLayout.marginLeft = 5;
		detailArea.setLayout(detailAreaGridLayout);
		
		
		//Tool bar which contains update and remove buttons of the section, on the right of the title bar. 
		ToolBar toolBarTitle = new ToolBar(this, SWT.FLAT | SWT.RIGHT | SWT.SHADOW_OUT);
		setTextClient(toolBarTitle);

		// Update button of the section
		btnUpdate = new ToolItem(toolBarTitle, SWT.NONE);
		btnUpdate.setToolTipText(HTEditorConstants.UPDATE_BUTTON_TOOLTIP);
		btnUpdate.setImage(ResourceManager.getPluginImage(HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,
		                                                  HTEditorConstants.UPDATE_BUTTON_IMAGE));
		fillDetailArea();
		
		// Remove button of the section
		ToolItem btnRemove = new ToolItem(toolBarTitle, SWT.CHECK);
		btnRemove.setToolTipText(HTEditorConstants.REMOVE_BUTTON_TOOLTIP);
		btnRemove.setImage(ResourceManager.getPluginImage(HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,
		                                                  HTEditorConstants.REMOVE_BUTTON_IMAGE));

		btnRemove.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					btnRemoveHandleLogic(textEditor);
					//Fill the emptied space created due to removing the section and re layout the sections
					centralUtils.redraw();
				} catch (JAXBException e) {
					MessageDialog.openError(Display.getCurrent().getActiveShell(),
					                        HTEditorConstants.INTERNAL_ERROR,
					                        HTEditorConstants.XML_PARSE_ERROR_MESSAGE);
				}
			}
		});

		btnUpdate.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					btnUpdateHandleLogic(textEditor);
				} catch (JAXBException e) {
					MessageDialog.openError(Display.getCurrent().getActiveShell(),
					                        HTEditorConstants.INTERNAL_ERROR,
					                        HTEditorConstants.XML_PARSE_ERROR_MESSAGE);
				}
			}

		});

	}

	/**
	 * Update the values of attributes of the section and marshal into the
	 * TextEditor when the update button of that section is clicked
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	public abstract void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException;

	/**
	 * Dispose the section when the remove button of a section is clicked
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	public abstract void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException;

	/**
	 * Initialize or set the values of attributes whenever a tab change occur
	 * from text editor to the UI editor
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	public abstract void initialize(XMLEditor textEditor) throws JAXBException;

	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes in the section
	 */
	public abstract void fillDetailArea();

	/**
	 * Draw the outline borders of sections
	 * @param composite
	 */
	public void drawBorder(final Composite composite) {
		composite.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				e.gc.drawLine(0, 0, composite.getParent().getBounds().width, 0);
			}

		});
	}
}
