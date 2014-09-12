package PlugTest.editors;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroup;

public class TNotificationComposite extends TMultiBaseComposite {

	public TNotificationComposite(XMLEditor editor, Composite parent,
			int index, int style) {
		super(editor, parent, index, style,"notification group");
		HTCompositeUtil htcu=new HTCompositeUtil();
		this.setBounds(htcu.getRectangle(index, 20,110, parent.getParent().getSize().x, 100, 10));
	}
	
	@Override
	public void initialize(int i,CentralUtils ch2){
		
		ArrayList<TLogicalPeopleGroup> groups=new ArrayList<TLogicalPeopleGroup>();
		if(CentralUtils.gettHumanInteractions().getLogicalPeopleGroups()!= null){
			groups=(ArrayList<TLogicalPeopleGroup>) CentralUtils.gettHumanInteractions().getLogicalPeopleGroups().getLogicalPeopleGroup();
			
			if(groups.size() > i && !groups.get(i).getName().isEmpty()){
				System.out.println("i value is "+groups.get(i).getName());
				textBoxes.get(0).setText(groups.get(i).getName());
				textBoxes.get(1).setText(groups.get(i).getReference());
			}
		}
		
	}

	@Override
	public void newButtonHandleLogic(int i,XMLEditor editor) {
		TLogicalPeopleGroup tl ;
			
		if((CentralUtils.gettHumanInteractions().getLogicalPeopleGroups().getLogicalPeopleGroup().size()<(i+1))){
			tl=new TLogicalPeopleGroup();
			tl.setName(textBoxes.get(1).getText());
			tl.setReference(textBoxes.get(2).getText());	
			CentralUtils.gettHumanInteractions().getLogicalPeopleGroups().getLogicalPeopleGroup().add(i,tl);
		}else{
			tl=CentralUtils.gettHumanInteractions().getLogicalPeopleGroups().getLogicalPeopleGroup().get(i);
			tl.setName(textBoxes.get(0).getText());
			tl.setReference(textBoxes.get(1).getText());
			//int index=ch.tHumanInteractions.getLogicalPeopleGroups().getLogicalPeopleGroup().indexOf(tl);
		}
		try {
			centralHandler.marshalMe(editor);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void fillDetailArea() {
		Label lblNewLabel = toolkit.createLabel(detailArea, "Name",
				SWT.NONE);
		lblNewLabel.setBounds(20, 23, 100, 15);
		Text txtNewText = toolkit.createText(detailArea, "New Text", SWT.NONE);
		txtNewText.setBounds(152, 23, 100, 21);
		textBoxes.add(0,txtNewText);
		Label lblReference = toolkit.createLabel(detailArea, "Reference",
				SWT.NONE);
		lblReference.setBounds(252, 23, 100, 15);
		Text txtReference = toolkit.createText(detailArea, "New Text", SWT.NONE);
		txtReference.setBounds(384, 23, 100, 21);
		textBoxes.add(1,txtReference);
	}
}
