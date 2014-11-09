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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * This is the abstract class extended by all the UI classes representing each
 * xml element which have child elements in the .ht file. The basic and
 * common UI related declarations and button listeners are included here. All
 * the UI classes which are representing xml elements which have child
 * elements (parent tags) extend this abstract class. So all such those UI
 * classes representing each xml element which have child elements in the .ht
 * file are a AbstractParentTagSection. This AbstractParentTagSection.java class
 * itself is extended by org.eclipse.ui.forms.widgets.Section and so this is
 * actually a SWT section. There are number of sub classes of this
 * AbstractParentTagSection class and the logics and method implementations
 * are almost same in those all sub classes. One such sub class,
 * TLogicalPeopleGroup is comprehensively commented for better understanding of
 * a reader.
 */
public abstract class AbstractParentTagSection extends Section {
	protected final FormToolkit formToolkit;
	protected ArrayList<Widget> textBoxesList;/*
											   * List to keep Text
											   * boxes(org.eclipse.swt.widgets.Text
											   * )
											   * and combo boxes representing
											   * xml attributes
											   */
	protected CentralUtils centralUtils;
	// protected Section innerSection;
	protected Composite parentTagContainer;// Keeps reference of the section's
	// parent tag section
	protected Composite detailArea;// Area to contain attributes table
	protected ToolItem btnNewgroup;
	protected ToolItem btnUpdate;// Update button of the section
	protected ToolItem btnRemove;// Remove button of the section
	public Composite parentComposite;
	public XMLEditor xmlEditor;
	protected ToolBar titleToolBar;

	/**
	 * The basic and common UI related declarations and button listeners are
	 * included here
	 * 
	 * @param textEditor
	 * @param parentComposite
	 * @param parentTagContainer
	 * @param styleBit
	 * @param dropDownItems
	 * @param sectionTitle
	 * @throws JAXBException
	 */
	public AbstractParentTagSection(final XMLEditor textEditor, Composite parentComposite,
	                                Composite parentTagContainer, int styleBit,
	                                final String[] dropDownItems, String sectionTitle)
	                                                                                  throws JAXBException {
		super(parentComposite, Section.TWISTIE | Section.TITLE_BAR);
		formToolkit = new FormToolkit(Display.getCurrent());
		setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		setText(sectionTitle);
		setExpanded(true);
		setTitleBarBackground(new Color(getDisplay(), 224, 227, 253));
		centralUtils = CentralUtils.getInstance(textEditor);
		textBoxesList = new ArrayList<Widget>();
		this.parentTagContainer = parentTagContainer;
		this.parentComposite = parentComposite;
		setLayout(new GridLayout(1, false));
		formToolkit.adapt(this);
		formToolkit.paintBordersFor(this);
		setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		this.xmlEditor = textEditor;

		// dispose the formToolkit when this Section is disposed
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				formToolkit.dispose();
			}
		});

		// All the components of the section are in this ScrolledComposite
		final ScrolledComposite sectionParentScrolledComposite =
		                                                         new ScrolledComposite(this,
		                                                                               SWT.H_SCROLL);
		sectionParentScrolledComposite.setMinHeight(-1);
		formToolkit.adapt(sectionParentScrolledComposite);
		formToolkit.paintBordersFor(sectionParentScrolledComposite);
		this.setClient(sectionParentScrolledComposite);
		sectionParentScrolledComposite.setExpandHorizontal(true);
		sectionParentScrolledComposite.setExpandVertical(true);
		sectionParentScrolledComposite.setLayout(new GridLayout(1, false));

		detailArea = new Composite(sectionParentScrolledComposite, SWT.NONE);
		formToolkit.adapt(detailArea);
		formToolkit.paintBordersFor(detailArea);

		sectionParentScrolledComposite.setContent(detailArea);
		detailArea.setLayout(new GridLayout(1, true));
		titleToolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		setTextClient(titleToolBar);

		btnNewgroup = new ToolItem(titleToolBar, SWT.NONE);
		btnNewgroup.setToolTipText(HTEditorConstants.ADD_CHILD_BUTTON_TOOLTIP);
		btnNewgroup.setWidth(27);
		btnNewgroup.setImage(ResourceManager.getPluginImage(HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,
		                                                    HTEditorConstants.EDIT_BUTTON_IMAGE));

		btnUpdate = new ToolItem(titleToolBar, SWT.NONE);
		btnUpdate.setToolTipText(HTEditorConstants.UPDATE_BUTTON_TOOLTIP);
		btnUpdate.setImage(ResourceManager.getPluginImage(HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,
		                                                  HTEditorConstants.UPDATE_BUTTON_IMAGE));

		btnRemove = new ToolItem(titleToolBar, SWT.CHECK);
		btnRemove.setToolTipText(HTEditorConstants.REMOVE_BUTTON_TOOLTIP);
		btnRemove.setImage(ResourceManager.getPluginImage(HTEditorConstants.PLUGIN_IMAGE_SYMBOLIC_NAME,
		                                                  HTEditorConstants.REMOVE_BUTTON_IMAGE));

		final Shell shell = new Shell(this.getDisplay());

		final Menu menu = new Menu(shell, SWT.POP_UP);

		/*
		 * Item list(possible child elements list) to add, when
		 * "add child elements"(btnNewgroup) tool item is selected
		 */
		ArrayList<MenuItem> menuItemArrayList = new ArrayList<MenuItem>();

		/*
		 * set listener to each menu item to create new section, when that menu
		 * item is selected
		 */
		for (int dropDownItemIndex = 0; dropDownItemIndex < dropDownItems.length; dropDownItemIndex++) {
			menuItemArrayList.add(new MenuItem(menu, SWT.NONE));
			menuItemArrayList.get(dropDownItemIndex).setText(dropDownItems[dropDownItemIndex]);

			final String selection = dropDownItems[dropDownItemIndex];
			menuItemArrayList.get(dropDownItemIndex).addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {

					try {
						newButtonLogic(selection, sectionParentScrolledComposite, textEditor,
						               detailArea);
						centralUtils.redraw();/*
											   * Fill the emptied space created
											   * due to removing the section and
											   * re-layout the sections
											   */
					} catch (JAXBException e) {
						MessageDialog.openError(shell, HTEditorConstants.INTERNAL_ERROR,
						                        HTEditorConstants.XML_PARSE_ERROR_MESSAGE);
					}
				}
			});

		}

		// set the listner to popup the drop down menu when the btnNewgroup is
		// clicked
		btnNewgroup.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				menu.setVisible(true);
			}
		});

		// set the listener to btnRemove to remove the section
		btnRemove.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				try {
					btnRemoveHandleLogic(textEditor);
					centralUtils.redraw();
				} catch (JAXBException e) {
					MessageDialog.openError(shell, HTEditorConstants.INTERNAL_ERROR,
					                        HTEditorConstants.XML_PARSE_ERROR_MESSAGE);
				}
			}

		});

		/*
		 * set listener to btnUpdate to update the (xml)attributes of the (xml
		 * element)section
		 */
		btnUpdate.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				try {
					btnUpdateHandleLogic(textEditor);
				} catch (JAXBException e) {
					MessageDialog.openError(shell, HTEditorConstants.INTERNAL_ERROR,
					                        HTEditorConstants.XML_PARSE_ERROR_MESSAGE);
				}
			}
		});

		// Create table to enter attributes for each xml element
		fillDetailArea(detailArea);

	}

	/**
	 * Draw the outline borders of sections
	 * 
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

	/**
	 * Update the values of attributes of the section and marshal into the
	 * TextEditor when the update button of that section is clicked
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	public abstract void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException;

	/**
	 * Dispose the section when the remove button of section is clicked.
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	public abstract void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException;

	/**
	 * Whenever a tab change occur from text editor to UI editor, this method is
	 * invoked. It disposes all the child Sections in this section and recreate
	 * them and call initialize() to reinitialize their attribute values,
	 * according to the single model maintained by both the UI editor and text
	 * editor
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	public abstract void refreshLogic(XMLEditor textEditor) throws JAXBException;

	/**
	 * Initialize or set the values of attributes whenever a tab change occur
	 * from text editor to the UI editor
	 * 
	 * @param textEditor
	 * @throws JAXBException
	 */
	public abstract void initialize(XMLEditor textEditor) throws JAXBException;

	/**
	 * Create a new child Section for the selected xml child element
	 * 
	 * @param selection
	 * @param scrolledComposite
	 * @param editor
	 * @param composite
	 * @throws JAXBException
	 */
	public abstract void newButtonLogic(String selection, ScrolledComposite scrolledComposite,
	                                    XMLEditor editor, Composite composite) throws JAXBException;

	/**
	 * Fill the detail area of the composite. This creates the table UI widget
	 * to keep xml element attributes in the section
	 */
	public abstract void fillDetailArea(Composite composite);

	/**
	 * Whenever a child Section of this section is removed by the user, this
	 * method is invoked to
	 * reorganize the order and indexes of the child Sections of this section
	 * 
	 * @param itemName
	 * @param childCompositeIndex
	 * @param childObjectIndex
	 */
	public abstract void refreshChildren(String itemName, int childCompositeIndex,
	                                     int childObjectIndex);

}