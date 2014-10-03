package org.wso2.developerstudio.humantask.uimodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TBoolean;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TDocumentation;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TPresentationElements;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TPriorityExpr;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTask;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTaskInterface;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TTasks;
import org.wso2.developerstudio.humantask.editor.AbstractParentTagSection;
import org.wso2.developerstudio.humantask.editor.XMLEditor;

public class TTaskUI extends AbstractParentTagSection {
	int[] childObjectIndexes;
	public TTask task; // Change the type of model object
	int objectIndex; // this is this elements object index
	int compositeIndex; // this is this elements composite index
	int childCompositeIndex;
	Composite container;
	XMLEditor editor;
	ArrayList<Composite> childComposites = new ArrayList<Composite>();
	boolean refreshed = true;

	public TTaskUI(XMLEditor editor, Composite parent, Composite container,
			int style, Object modelParent, int objectIndex, int compositeIndex)
			throws JAXBException {
		super(editor, parent,container, style, new String[] { "Documentation",
				"Interface", "Priority", "People Assignments", "Delegation",
				"Presentation Elements" }, "Task");
		// TTasks tasks=(TTasks)modelParent;
		System.out.println(objectIndex);
		this.task = (TTask) modelParent;
		this.objectIndex = objectIndex;
		this.compositeIndex = compositeIndex;
		this.editor = editor;
		this.container = container;
		childObjectIndexes = new int[6];
		setExpanded(true);
	}

	@Override
	public void btnUpdateHandleLogic(XMLEditor textEditor) throws JAXBException {
		task.setName(((Text) textBoxes.get(0)).getText());
		task.setActualOwnerRequired(TBoolean.fromValue(((Combo) textBoxes
				.get(1)).getText()));
		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void btnRemoveHandleLogic(XMLEditor textEditor) throws JAXBException {
		TTasksUI tTasksUI = (TTasksUI) container;
		tTasksUI.refreshChildren("", compositeIndex, objectIndex);

		try {
			centralUtils.marshalMe(textEditor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		Composite tempCompo = this.getParent();
		this.dispose();
		tempCompo.layout(true, true);

	}

	@Override
	public void newButtonLogic(String selection, ScrolledComposite sc3,
			XMLEditor editor, Composite composite) throws JAXBException {
		if (selection.equalsIgnoreCase("Documentation")) {
			TDocumentation tDocumentation = new TDocumentation();
			tDocumentation.setLang(childCompositeIndex + "");
			tDocumentation.getContent().add(
					new String(childObjectIndexes[0] + ""));
			task.getDocumentation().add(childObjectIndexes[0], tDocumentation);
			TDocumentationUI tNot = new TDocumentationUI(editor, composite,
					childCompositeIndex, childObjectIndexes[0], SWT.NONE, this,
					tDocumentation);
			childComposites.add(childCompositeIndex, tNot);
			// sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT,
			// SWT.DEFAULT));
			System.out.println("hikz value is " + i);
			System.out.println("Number of CC" + childComposites.size());
			// centralUtils.addInstance(tTask);
			childObjectIndexes[0]++;
			childCompositeIndex++;
		} else if (selection.equalsIgnoreCase("Interface")) {
			if (childObjectIndexes[1] < 1) {
				TTaskInterface tTaskInterface = new TTaskInterface();
				tTaskInterface.setPortType(new QName(""));
				tTaskInterface.setOperation("");
				task.setInterface(tTaskInterface);
				TTaskInterfaceUI tNot = new TTaskInterfaceUI(editor, composite,
						childCompositeIndex, childObjectIndexes[1], SWT.NONE,
						this, tTaskInterface);
				childComposites.add(childCompositeIndex, tNot);
				// sc3.setMinSize(innerSection.computeSize(SWT.DEFAULT,
				// SWT.DEFAULT));
				System.out.println("hikz value is " + i);
				System.out.println("Number of CC" + childComposites.size());
				// centralUtils.addInstance(tTask);
				childObjectIndexes[1]++;
				childCompositeIndex++;
			}
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
		}else if (selection.equalsIgnoreCase("Presentation Elements")) {
			if (childObjectIndexes[3] < 1) {
				TPresentationElements tPresentationElements = new TPresentationElements();
				task.setPresentationElements(tPresentationElements);;
				TPresentationElementsUI tNot = new TPresentationElementsUI(editor,composite,this,SWT.NONE,tPresentationElements,childObjectIndexes[0],childCompositeIndex);;
				childComposites.add(childCompositeIndex, tNot);
				childObjectIndexes[3]++;
				childCompositeIndex++;
			}
		}

		// sc3.layout(true,true);
		try {
			centralUtils.marshalMe(editor);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void fillDetailArea(Composite composite) {
		Composite innerZComp = toolkit.createComposite(composite);
		innerZComp.setLayout(new GridLayout(4, true));
		innerZComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		Label lblName = toolkit.createLabel(innerZComp, "Language", SWT.NONE);
		// lblName.setBounds(20, 23, 100, 15);
		Text txtName = toolkit.createText(innerZComp, "", SWT.BORDER);
		// txtName.setBounds(152, 23, 100, 21);
		// txtName.setLayoutData(new GridData();
		textBoxes.add(0, txtName);
		Label lblReference = toolkit.createLabel(innerZComp,
				"Actual Owner Required", SWT.NONE);
		// lblReference.setBounds(252, 23, 100, 15);
		Combo combo = new Combo(innerZComp, SWT.NONE);
		combo.setItems(new String[] { "yes", "no" });
		combo.select(0);
		// combo.setBounds(384, 23, 100, 21);
		textBoxes.add(1, combo);

	}

	@Override
	public void initialize(XMLEditor textEditor) throws JAXBException {
		((Text) textBoxes.get(0)).setText(task.getName());
	}

	@Override
	public void refreshLogic(XMLEditor editor) throws JAXBException { /////////////////////////Start from this/////////////////////
		ArrayList<TDocumentation> documentationGroup = new ArrayList<TDocumentation>();
		documentationGroup = (ArrayList<TDocumentation>) task
				.getDocumentation();
		
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
		/*for (int j = 0; j < childComposites.size(); j++) {
			if (childComposites.get(j) instanceof TDocumentationUI) {
				for (int i = 0; i < documentationGroup.size(); i++) {
					TDocumentationUI tNot;
					if (((childObjectIndexes[0]) == documentationGroup.size())) {
						try {
							tNot = (TDocumentationUI) childComposites.get(j);
							tNot.initialize(editor);
						} catch (JAXBException e) {
							e.printStackTrace();
						}
					} else {
						refreshed = false;

					}
				}
			} else if (childComposites.get(j) instanceof TTaskInterfaceUI) {
				if (task.getInterface() != null) {
					TTaskInterfaceUI tNot;
					if (((childObjectIndexes[1]) == 1)) {
						tNot = (TTaskInterfaceUI) childComposites.get(j);
						tNot.initialize(editor);
					} else {
						refreshed = false;
					}
				}
			} else if (childComposites.get(j) instanceof TPriorityExprUI) {
				if (task.getPriority() != null) {
					TPriorityExprUI tNot;
					if (((childObjectIndexes[2]) == 1)) {
						tNot = (TPriorityExprUI) childComposites.get(j);
						tNot.initialize(editor);
					} else {
						refreshed = false;
					}
				}
			}else if (childComposites.get(j) instanceof TPresentationElementsUI) {
				if (task.getPresentationElements() != null) {
					TPresentationElementsUI tNot;
					if (((childObjectIndexes[3]) == 1)) {
						tNot = (TPresentationElementsUI) childComposites.get(j);
						tNot.initialize(editor);
					} else {
						if (itemName.equalsIgnoreCase("Documentation")) {
							this.childObjectIndexes[0]--;
							System.out.println("Removing object index taskui:"
									+ childObjectIndex);
							task.getDocumentation().remove(childObjectIndex);
							for (Compo		refreshed = false;
					}
				}
			}else {
				System.out.println("Not that thing");
			}
		}*/
		System.out.println("Not that thing");
		if (childComposites.size() == 0) {
			for (int i = 0; i < documentationGroup.size(); i++) {
				TDocumentationUI tNot;

				try {
					tNot = new TDocumentationUI(editor, compositeDetailArea,
							childCompositeIndex, childObjectIndexes[0],
							SWT.NONE, this,
							documentationGroup.get(childObjectIndexes[0]));
					tNot.initialize(editor);
					childComposites.add(childCompositeIndex, tNot);
					childCompositeIndex++;
					childObjectIndexes[0]++;
				} catch (JAXBException e) {
					e.printStackTrace();

				}
			}

			if (task.getInterface() != null) {
				TTaskInterfaceUI tNot;
				TTaskInterface interfaceObject = (TTaskInterface) task
						.getInterface();
				try {
					tNot = new TTaskInterfaceUI(editor, compositeDetailArea,
							childCompositeIndex, childObjectIndexes[1],
							SWT.NONE, this, interfaceObject);
					tNot.initialize(editor);
					childComposites.add(childCompositeIndex, tNot);
					childCompositeIndex++;
					childObjectIndexes[1]++;
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
			
			if (task.getPriority() != null) {
				TPriorityExprUI tNot;
				TPriorityExpr priorityObject = (TPriorityExpr) task
						.getPriority();
				try {
					tNot = new TPriorityExprUI(editor, compositeDetailArea,
							childCompositeIndex, childObjectIndexes[2],
							SWT.NONE, this, priorityObject);
					tNot.initialize(editor);
					childComposites.add(childCompositeIndex, tNot);
					childCompositeIndex++;
					childObjectIndexes[2]++;
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
			if (task.getPresentationElements() != null) {
				TPresentationElementsUI tNot;
				TPresentationElements tPresentationElementsObject = (TPresentationElements) task
						.getPresentationElements();
				try {
					tNot = new TPresentationElementsUI(editor,compositeDetailArea,this,SWT.NONE,tPresentationElementsObject,childObjectIndexes[0],childCompositeIndex);;
					tNot.initialize(editor);
					childComposites.add(childCompositeIndex, tNot);
					childCompositeIndex++;
					childObjectIndexes[3]++;
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void refreshChildren(String itemName, int childCompositeIndex,
			int childObjectIndex) {
		System.out.println("Number of CC" + childComposites.size());
		System.out.println("Removing object index taskui outer:"
				+ childObjectIndex);
		if (itemName.equalsIgnoreCase("Documentation")) {
			this.childObjectIndexes[0]--;
			System.out.println("Removing object index taskui:"
					+ childObjectIndex);
			task.getDocumentation().remove(childObjectIndex);
			for (Composite c : childComposites) {

				if (c instanceof TDocumentationUI) {
					System.out.print("Giya");
					TDocumentationUI d = (TDocumentationUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}
					if (d.objectIndex > childObjectIndex) {
						d.setObjectIndex(d.getObjectIndex() - 1);
					}
				} else if (c instanceof TTaskInterfaceUI) {
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

				} else {
					
				}

			}
		} else if (itemName.equalsIgnoreCase("Interface")) {
			this.childObjectIndexes[1]--;
			task.setInterface(null);
			for (Composite c : childComposites) {
				if (c instanceof TDocumentationUI) {
					TDocumentationUI d = (TDocumentationUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}
				} else if (c instanceof TTaskInterfaceUI) {
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

				} else {
				}

			}
		}else if (itemName.equalsIgnoreCase("Priority")) {
			this.childObjectIndexes[2]--;
			task.setInterface(null);
			for (Composite c : childComposites) {
				if (c instanceof TDocumentationUI) {
					TDocumentationUI d = (TDocumentationUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}
				} else if (c instanceof TTaskInterfaceUI) {
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

				} else {
				}

			}
		}else if (itemName.equalsIgnoreCase("Presentation Elements")) {
			this.childObjectIndexes[3]--;
			task.setInterface(null);
			for (Composite c : childComposites) {
				if (c instanceof TDocumentationUI) {
					TDocumentationUI d = (TDocumentationUI) c;
					if (d.compositeIndex > childCompositeIndex) {
						d.setCompositeIndex(d.getCompositeIndex() - 1);
					}
				} else if (c instanceof TTaskInterfaceUI) {
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

				} else {
				}

			}
		}
		childComposites.remove(childCompositeIndex);
		this.childCompositeIndex--;

	}

	public void loadModel(Object model) throws JAXBException {
		task = (TTask) model;
		for (Composite c : childComposites) {
			if (c instanceof TDocumentationUI) {
				TDocumentationUI d = (TDocumentationUI) c; // children node type
				d.loadModel(task.getDocumentation().get(d.objectIndex));
			} else if (c instanceof TTaskInterfaceUI) {
				TTaskInterfaceUI d = (TTaskInterfaceUI) c; // children node type
				d.loadModel(task.getInterface());
			} else if (c instanceof TPriorityExprUI) {
				TPriorityExprUI d = (TPriorityExprUI) c; // children node type
				d.loadModel(task.getPriority());
			}else if (c instanceof TPresentationElementsUI) {
				TPresentationElementsUI d = (TPresentationElementsUI) c;
				d.presentationElements=task.getPresentationElements();
				d.refreshLogic(editor);
				d.loadModel(task.getPresentationElements());

			}
		}
	}
}
