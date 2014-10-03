package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TDocumentation;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TPresentationElements;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TPresentationParameters;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TPriorityExpr;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTask;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTaskInterface;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTasks;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TText;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.XMLEditor;

public class TPresentationElementsUI extends AbstractParentTagSection {
	int [] childObjectIndexes;
	public TPresentationElements presentationElements;   // Change the type of model object
	private int objectIndex;
	private int compositeIndex;
	public int getObjectIndex() {
		return objectIndex;
	}

	public void setObjectIndex(int objectIndex) {
		this.objectIndex = objectIndex;
	}

	public int getCompositeIndex() {
		return compositeIndex;
	}

	public void setCompositeIndex(int compositeIndex) {
		this.compositeIndex = compositeIndex;
	}

	int childCompositeIndex;
	protected Composite container;
	XMLEditor editor;
	ArrayList<Composite> childComposites = new ArrayList<Composite>();
	
	public TPresentationElementsUI(XMLEditor editor,Composite parent,Composite container,
			int style,Object modelParent,int objectIndex,int compositeIndex) throws JAXBException {
		super(editor, parent,container,style,new String[] {"Name","Presentation Parameters","Subject","Description"},"Presentation Elements"); //change the list of items in drop down list
		this.presentationElements=(TPresentationElements)modelParent; // change the model object
		this.objectIndex=objectIndex;
		this.compositeIndex=compositeIndex;
		this.container=container;
		this.editor=editor;
		childObjectIndexes = new int[4];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TTaskUI taskUI=(TTaskUI)container;
		taskUI.refreshChildren("PresentationElements", compositeIndex, objectIndex);
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
			
			ArrayList<TText> tNameGroup = new ArrayList<TText>();
			tNameGroup = (ArrayList<TText>) presentationElements.getName();
			for (int i = 0; i < tNameGroup.size(); i++) {
				TNameUI tNot;
				
					try {
						 tNot = new TNameUI(editor, compositeDetailArea,
								childCompositeIndex, childObjectIndexes[0], SWT.NONE, this,
								tNameGroup.get(childObjectIndexes[0]));
						tNot.initialize(editor);
						childComposites.add(childCompositeIndex, tNot);
						childCompositeIndex++;
						childObjectIndexes[0]++;
					} catch (JAXBException e) {
						
						e.printStackTrace();
					}
					
				
				
				//tNot.updated = true;
				//sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				//sc3.layout(true, true);
				//innerSection.layout(true, true);
				
			}
			
			if (presentationElements.getPresentationParameters() != null) {
				TPresentationParametersUI tNot;
				TPresentationParameters presentationParametersObject = (TPresentationParameters) presentationElements.getPresentationParameters();
						
				try {
					tNot = new TPresentationParametersUI(editor,compositeDetailArea,this,SWT.NONE,presentationParametersObject,childObjectIndexes[1],childCompositeIndex);
					tNot.initialize(editor);
					childComposites.add(childCompositeIndex, tNot);
					childCompositeIndex++;
					childObjectIndexes[1]++;
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		// TODO Auto-generated method stub

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase("Name")) {
			TText tName = new TText();
			tName.setLang("");
			tName.getContent().add(new String(""));
			presentationElements.getName().add(childObjectIndexes[0], tName);
			TNameUI tNot = new TNameUI(editor, composite,
					childCompositeIndex, childObjectIndexes[0], SWT.NONE, this,
					tName);
			childComposites.add(childCompositeIndex, tNot);
			// sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT,
			// SWT.DEFAULT));
			System.out.println("hikz value is " + i);
			System.out.println("Number of CC" + childComposites.size());
			// centralUtils.addInstance(tTask);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase("Presentation Parameters")) {
			if (childObjectIndexes[1] < 1) {
				TPresentationParameters tPresentationParameters = new TPresentationParameters();
				tPresentationParameters.setExpressionLanguage("");
				presentationElements.setPresentationParameters(tPresentationParameters);;
				TPresentationParametersUI tNot = new TPresentationParametersUI(editor,compositeDetailArea,this,SWT.NONE,tPresentationParameters,childObjectIndexes[1],childCompositeIndex);
				childComposites.add(childCompositeIndex, tNot);
				// sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT,
				// SWT.DEFAULT));
				System.out.println("hikz value is " + i);
				System.out.println("Number of CC" + childComposites.size());
				// centralUtils.addInstance(tTask);
				childObjectIndexes[1]++;
				childCompositeIndex++;
			}
		}/*
		} else if (selection.equalsIgnoreCase("Priority")) {
			if (childObjectIndexes[2] < 1) {
				TPriorityExpr tPriorityExpr = new TPriorityExpr();
				tPriorityExpr.setExpressionLanguage("");
				tPriorityExpr.getContent().add(0,"");
				task.setPriority(tPriorityExpr);
				TPriorityExprUI tNot = new TPriorityExprUI(editor, composite,
						childCompositeIndex, childObjectIndexes[2], SWT.NONE,
						this, tPriorityExpr);
				childComposites.add(childCompositeIndex, tNot);
				childObjectIndexes[2]++;
				childCompositeIndex++;
			}
		}*/

		// sc3.layout(true,true);
		try {
			centralUtils.marshalMe(editor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void fillDetailArea(Composite composite) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		if (itemName.equalsIgnoreCase("Name")) {
			this.childObjectIndexes[0]--;
			System.out.println("Removing object index taskui:"
					+ childObjectIndex);
			presentationElements.getName().remove(childObjectIndex);
			for (Composite c : childComposites) {

				if (c instanceof TNameUI) {
					
					TNameUI d = (TNameUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}
					if (d.objectIndex > childObjectIndex) {
						d.setObjectIndex(d.getObjectIndex() - 1);
					}
				}/* else if (c instanceof TTaskInterfaceUI) {
					TTaskInterfaceUI d = (TTaskInterfaceUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}

				} else if (c instanceof TPriorityExprUI) {
					TPriorityExprUI d = (TPriorityExprUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}

				}else if (c instanceof TPresentationElementsUI) {
					TPresentationElementsUI d = (TPresentationElementsUI) c;
					if (d.getCompositeIndex() > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}

				}*/ else {
					
				}

			}
		}

	}
	
	public void loadModel(Object model) {
		presentationElements = (TPresentationElements) model;
		for (Composite c : childComposites) {
			if (c instanceof TNameUI) {
				TNameUI d = (TNameUI) c; // children node type
				d.loadModel(presentationElements.getName().get(d.objectIndex));
			}/* else if (c instanceof TTaskInterfaceUI) {
				TTaskInterfaceUI d = (TTaskInterfaceUI) c; // children node type
				d.loadModel(task.getInterface());
			} else if (c instanceof TPriorityExprUI) {
				TPriorityExprUI d = (TPriorityExprUI) c; // children node type
				d.loadModel(task.getPriority());
			}*/
		}
	}

}
