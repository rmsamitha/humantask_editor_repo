package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TPresentationParameters;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTasks;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.BaseView;
import org.wso2.developerstudio.humantask.editor.XMLEditor;

public class TPresentationParametersUI extends AbstractParentTagSection {
	int [] childObjectIndexes;
	public TPresentationParameters presentationParameters;   // Change the type of model object
	private int objectIndex;
	private int compositeIndex;
	int childCompositeIndex;
	protected Composite container;
	XMLEditor editor;
	ArrayList<Composite> childComposites = new ArrayList<Composite>();
	
	public TPresentationParametersUI(XMLEditor editor,Composite parent,Composite container,
			int style,Object modelParent,int objectIndex,int compositeIndex) throws JAXBException {
		super(editor, parent,container,style,new String[] {"Task"},"Tasks"); //change the list of items in drop down list
		this.presentationParameters=(TPresentationParameters)modelParent; // change the model object
		this.objectIndex=objectIndex;
		this.compositeIndex=compositeIndex;
		this.container=container;
		this.editor=editor;
		childObjectIndexes = new int[1];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TPresentationElementsUI presentationElementsUI=(TPresentationElementsUI)container;
		presentationElementsUI.refreshChildren("Presentation ",compositeIndex, objectIndex);
		
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Composite tempCompo=this.getParent();
		this.dispose();
		tempCompo.layout(true,true);

	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException {
		for(Composite c:childComposites){
			c.dispose();
			System.out.println("Disposed");
			}
			childComposites.clear();
			System.out.println("XC Size is :"+childComposites.size());
			
			
			childCompositeIndex=0;
			for (int j = 0; j < childObjectIndexes.length; j++) {
				childObjectIndexes[j]=0;
		}
			
			
			

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void fillDetailArea(Composite composite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		// TODO Auto-generated method stub

	}

}
