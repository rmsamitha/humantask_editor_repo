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

import javax.xml.bind.JAXBContext;

import org.eclipse.ui.editors.text.TextEditor;
import org.wso2.developerstudio.htconfig.models.THTDeploymentConfig;
import org.wso2.developerstudio.humantask.models.THumanInteractions;

/**
 * This is the class which handles the text editor component of the
 * application.This class holds the centralUtils and RootElement objects which
 * are as the JAXB Object model(Root Element) and the util class(Central Utils)
 * for the application
 * 
 */
public class XMLEditor extends TextEditor {

	private ColorManager colorManager;
	private THumanInteractions rootElement;
	private THTDeploymentConfig configRootElement;
	private CentralUtils centralUtils;
	private JAXBContext jaxbContext;
	private JAXBContext configJaxbContext;

	public XMLEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
		rootElement = new THumanInteractions();
	}

	public THumanInteractions getRootElement() {
		return rootElement;
	}

	public void setRootElement(THumanInteractions rootElement) {
		this.rootElement = rootElement;
	}

	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

	public CentralUtils getCentralUtils() {
		return centralUtils;
	}

	public void setCentralUtils(CentralUtils centralUtils) {
		this.centralUtils = centralUtils;
	}

	public JAXBContext getJaxbContext() {
		return jaxbContext;
	}

	public void setJaxbContext(JAXBContext jaxbContext) {
		this.jaxbContext = jaxbContext;
	}

	public THTDeploymentConfig getConfigRootElement() {
		return configRootElement;
	}

	public void setConfigRootElement(THTDeploymentConfig configRootElement) {
		this.configRootElement = configRootElement;
	}

	public JAXBContext getConfigJaxbContext() {
		return configJaxbContext;
	}

	public void setConfigJaxbContext(JAXBContext configJaxbContext) {
		this.configJaxbContext = configJaxbContext;
	}

}