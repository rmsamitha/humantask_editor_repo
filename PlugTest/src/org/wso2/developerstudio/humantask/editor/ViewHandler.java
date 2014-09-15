package org.wso2.developerstudio.humantask.editor;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.text.IDocument;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ViewHandler {
	Document document;
	XMLEditor xmlEditor;
	
	
	public ViewHandler(XMLEditor xmlEditor){
		try {
			document=CentralUtils.getInstance(xmlEditor).getDocument(xmlEditor);
		} catch (JAXBException e) {
			
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			
			e.printStackTrace();
		} catch (SAXException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		this.xmlEditor=xmlEditor;
	}
	
	
	public void setTag(String tagName,String value,int index) throws JAXBException{
		
		NodeList nodeList=document.getElementsByTagName("htd:logicalPeopleGroup");
		System.out.print("aawwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" + nodeList.getLength());
		if(nodeList.getLength() != 0){ //validate for value also
			nodeList.item(index).setTextContent(value);
			/*CentralUtils.getInstance(xmlEditor).testMarshalMe(xmlEditor,nodeList.item(index),"TLogicalPeopleGroup");
			System.out.print("aawwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" + nodeList.item(index).getTextContent());
			*/
			//id.set(CentralHandler.getInstance().docToString(document));
			/*try {
				//CentralHandler.getInstance().docToTHumanInteraction(xmlEditor);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}else{
			//CentralHandler.getInstance().iDocument.set(theText.concat(sw.getBuffer().toString()));
		}
		
		
	}
	
	
}
