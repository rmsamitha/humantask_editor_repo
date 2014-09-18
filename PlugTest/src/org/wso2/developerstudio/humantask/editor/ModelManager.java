package org.wso2.developerstudio.humantask.editor;

import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.*;


public class ModelManager {
	THumanInteractions rootElement;
	XMLEditor xmlEditor;
	
	public ModelManager(XMLEditor xmlEditor) {
		this.xmlEditor=xmlEditor;
	}
	
	public boolean addToModel(int index,Object obj){
		rootElement=xmlEditor.getRootElement();
		if(obj.getClass() == TExtensions.class){
			rootElement.setExtensions((TExtensions)obj);
		}else if (obj.getClass() == TExtension.class){
			rootElement.getExtensions().getExtension().add(index,(TExtension)obj);
		}else if (obj.getClass() == TBoolean.class){
			rootElement.getExtensions().getExtension().add(index,(TExtension)obj);
		}
		
		return true;
	}
}
