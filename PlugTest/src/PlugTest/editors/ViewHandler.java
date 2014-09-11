package plugtest.editors;

import javax.xml.bind.JAXBException;

import org.eclipse.jface.text.IDocument;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ViewHandler {
	Document document;
	XMLEditor xmlEditor;
	
	
	public ViewHandler(XMLEditor xmlEditor){
		try {
			document=CentralUtils.getInstance(xmlEditor).getDocument(xmlEditor);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.xmlEditor=xmlEditor;
	}
	
	
	public void setTag(String tagName,String value,int index){
		
		NodeList nodeList=document.getElementsByTagName(tagName);
		//System.out.print("aawwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" + nodeList.getLength());
		if(nodeList.getLength() != 0){ //validate for value also
			nodeList.item(index).setTextContent(value);
			//System.out.print("aawwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww" + nodeList.item(index).getTextContent());
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
