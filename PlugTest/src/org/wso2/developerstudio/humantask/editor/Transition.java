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

import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;
import org.wso2.developerstudio.humantask.models.THumanInteractions;

/**
 * This is the Base UI Composite class on which all the other UI widgets are
 * existing.
 * This holds THumanInteractionsUI section and manages operations related to it.
 */
public class Transition extends Composite {
	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private THumanInteractions humanInteractions;
	private THumanInteractionsUI humanInteractionsUI;
	private XMLEditor textEditor;
	Composite baseContainer;
	private final static Logger LOG = Logger.getLogger(MultiPageEditor.class.getName());

	/**
	 * Set the major UI features of this basic view composite
	 * 
	 * @param textEditor
	 * @param parentComposite
	 * @param styleBit
	 */
	public Transition(final XMLEditor textEditor, final Composite parentComposite, int styleBit) {
		super(parentComposite, SWT.BORDER);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_MAGENTA));
		this.textEditor = textEditor;
		addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new FormLayout());
		setLayoutData(new GridData(GridData.FILL_BOTH));

		final ScrolledComposite parentScrolledComposite =
		                                                  new ScrolledComposite(this, SWT.BORDER |
		                                                                              SWT.V_SCROLL);
		parentScrolledComposite.setAlwaysShowScrollBars(true);
		parentScrolledComposite.setExpandHorizontal(true);
		parentScrolledComposite.setExpandVertical(true);
		FormData parentScrolledCompositeFormLayoutData = new FormData();
		parentScrolledCompositeFormLayoutData.right = new FormAttachment(100);
		parentScrolledCompositeFormLayoutData.bottom = new FormAttachment(100);
		parentScrolledCompositeFormLayoutData.top = new FormAttachment(0, 5);
		parentScrolledCompositeFormLayoutData.left = new FormAttachment(0, 5);
		parentScrolledComposite.setLayoutData(parentScrolledCompositeFormLayoutData);
		this.setData(parentScrolledComposite);

		baseContainer = new Composite(parentScrolledComposite, SWT.NONE);
		baseContainer.setBackground(SWTResourceManager.getColor(204, 153, 255));
		parentScrolledComposite.setContent(baseContainer);

		baseContainer.setLayout(new GridLayout(1, true));
		try {
			CentralUtils centralUtils = CentralUtils.getCentralUtils(textEditor);
			centralUtils.setBasicUI(parentScrolledComposite, baseContainer);
			centralUtils.unmarshal(textEditor);
		} catch (JAXBException e) {
			LOG.info(e.getMessage());
		}

		// create a THumanInteractions object if humanInteractions variable is
		// null
		if (textEditor.getRootElement() == null) {
			humanInteractions = new THumanInteractions();
			textEditor.setRootElement(humanInteractions);
		}
		try {
			// create the biggest xml element- UI section
			humanInteractionsUI =
			                      new THumanInteractionsUI(textEditor, baseContainer, this,
			                                               SWT.NONE, textEditor.getRootElement(),
			                                               0, 0);
		} catch (JAXBException e1) {
			LOG.info(e1.getMessage());
		}

	}

	public void refreshLogic(XMLEditor editor) throws JAXBException {
		if (humanInteractions != null) {
			humanInteractionsUI.dispose();
			humanInteractionsUI =
			                      new THumanInteractionsUI(textEditor, baseContainer, this,
			                                               SWT.NONE, textEditor.getRootElement(),
			                                               0, 0);
			humanInteractionsUI.initialize(editor);
		}
	}

	/**
	 * Load the JAXB model objects into the UI model from the top to bottom of
	 * the tree structure of the model whenever a tab change occurs from text
	 * editor to the UI editor.
	 * 
	 * @param model
	 * @throws JAXBException
	 */
	public void loadModel(Object model) throws JAXBException {
		humanInteractions = (THumanInteractions) model;
		humanInteractionsUI.humanInteractions = (THumanInteractions) model;

		humanInteractionsUI.onPageRefresh(textEditor);
		humanInteractionsUI.loadModel(model);
		humanInteractionsUI.initialize(textEditor);

	}

}