package org.wso2.developerstudio.humantask.editor;

import javax.xml.bind.JAXBContext;

import org.eclipse.ui.editors.text.TextEditor;
import org.wso2.developerstudio.htconfig.models.THTDeploymentConfig;
import org.wso2.developerstudio.humantask.models.THumanInteractions;

public class XMLEditor extends TextEditor {

    private ColorManager colorManager;
    private THumanInteractions rootElement;
    private THTDeploymentConfig configRootElement;
    private  CentralUtils centralUtils;
    private  JAXBContext jaxbContext;
    private  JAXBContext configJaxbContext;
    
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