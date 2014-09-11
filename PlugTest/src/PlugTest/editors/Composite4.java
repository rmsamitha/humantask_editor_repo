package PlugTest.editors;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.THumanInteractions;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroup;
import org.oasis_open.docs.ns.bpel4people.ws_humantask._200803.TLogicalPeopleGroups;

public class Composite4 extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtNewText;
	private int l=0;
	protected Section secLogicalPeopleGroups;
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Composite4(final XMLEditor editor,Composite parent, int style) {
		super(parent, style);
		/*addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		// set layout type of this parent composite
		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = 10;
		setLayout(formLayout);

		// set the minimum width and height of the scrolled content - method 2
		// create the scrolledComposite
		final ScrolledComposite sc2 = new ScrolledComposite(this, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		//sc2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
		// fd_sc2.right = new FormAttachment(0, 10);
		// fd_sc2.bottom = new FormAttachment(0, 457);
		// fd_sc2.top = new FormAttachment(0);
		// fd_sc2.left = new FormAttachment(0, -10);
		FormData fd_sc2 = new FormData();
		fd_sc2.left = new FormAttachment(0);
		fd_sc2.right = new FormAttachment(100, -10);
		fd_sc2.top = new FormAttachment(0, 10);
		fd_sc2.bottom = new FormAttachment(100);
		sc2.setLayoutData(fd_sc2);
		sc2.setExpandHorizontal(true);
		sc2.setExpandVertical(true);

		final Composite c2 = new Composite(sc2, SWT.NONE);
		sc2.setContent(c2);
		//c2.setBackground(SWTResourceManager.getColor(255, 228, 181));
		FormLayout layout2 = new FormLayout();
		c2.setLayout(layout2);
		

		 secLogicalPeopleGroups = toolkit.createSection(c2,
				Section.TWISTIE | Section.TITLE_BAR);
		final FormData fd_secLogicalPeopleGroups = new FormData();
		fd_secLogicalPeopleGroups.bottom = new FormAttachment(100, 0);
		fd_secLogicalPeopleGroups.right = new FormAttachment(100, -10);
		fd_secLogicalPeopleGroups.top = new FormAttachment(0, 10);
		fd_secLogicalPeopleGroups.left = new FormAttachment(0, 10);
		secLogicalPeopleGroups.setLayout(new GridLayout());
		//secLogicalPeopleGroups.setBackground(SWTResourceManager.getColor(205,
		//		133, 63));
		toolkit.paintBordersFor(secLogicalPeopleGroups);
		secLogicalPeopleGroups.setText("Logical People Groups");
		secLogicalPeopleGroups.setExpanded(true);
		final ScrolledComposite sc3 = new ScrolledComposite(secLogicalPeopleGroups, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		toolkit.adapt(sc3);
		//toolkit.paintBordersFor(sc3);
		secLogicalPeopleGroups.setClient(sc3);
		FormData fd_sc3 = new FormData();
		fd_sc3.left = new FormAttachment(0);
		fd_sc3.right = new FormAttachment(100, 0);
		fd_sc3.top = new FormAttachment(0, 0);
		fd_sc3.bottom = new FormAttachment(100);
		sc3.setLayoutData(fd_sc3);
		sc3.setExpandHorizontal(true);
		sc3.setExpandVertical(true);
	
		final Composite composite = toolkit.createComposite(sc3,
				SWT.NONE);
		toolkit.paintBordersFor(composite);
		//composite.setLayoutData(fd_sc3);
		composite.setLayout(new GridLayout(1,true));
		final TNotificationComposite composite2=new TNotificationComposite(secLogicalPeopleGroups, SWT.BORDER,10,100);
		//toolkit.paintBordersFor(composite2);
		secLogicalPeopleGroups.setClient(composite2);
		//composite2.setVisible(false);
		Label lblNewLabel = toolkit.createLabel(composite, "New Label",
				SWT.NONE);
		lblNewLabel.setBounds(20, 23, 55, 15);
		txtNewText = toolkit.createText(composite, "New Text", SWT.NONE);
		txtNewText.setBounds(102, 23, 61, 21);
		Composite textCompo=toolkit.createComposite(secLogicalPeopleGroups,SWT.None);
		textCompo.setLayout(new GridLayout(2,true));
		Button btnNewButton = toolkit.createButton(textCompo,
				"New Group", SWT.NONE);
		
		Button btnRefresh = toolkit.createButton(textCompo,
				"Refresh", SWT.NONE);
		final ArrayList<TNotificationComposite> composites=new ArrayList<TNotificationComposite>();
		sc3.setContent(composite);
		for(int i=0;i<4;i++){
			TNotificationComposite tNot= new TNotificationComposite(composite, SWT.NONE,(i+1)*20,(i+2)*20);
			FormData fd_sc4 = new FormData();
			fd_sc4.left = new FormAttachment(0);
			fd_sc4.right = new FormAttachment(100, -10);
			fd_sc4.top = new FormAttachment((i+1)*20, 10);
			fd_sc4.bottom = new FormAttachment((i+2)*20);
			tNot.setLayoutData(fd_sc4);
			composites.add(i,tNot);
			sc3.setContent(composite);
		//composites.get(3).dispose();
		System.out.println(i);
		}
		sc3.setContent(composite);
		sc3.layout(true, true);
		sc3.setSize(sc3.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		//sc3.setSize(sc3.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		//toolkit.adapt(composites.get(i));
		
		
		
		
	//	System.out.println("ai  value"+groups.size());

		
		
		CentralUtils ch2;
		try {
			ch2 = CentralUtils.getInstance(editor);
		} catch (JAXBException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		btnRefresh.addListener(SWT.Selection, new Listener() {
				
			
			@Override
			public void handleEvent(Event event) {
				int j=0;
				try {
					ch2.docToTHumanInteraction(editor);
				} catch (JAXBException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ArrayList<TLogicalPeopleGroup> groups=new ArrayList<TLogicalPeopleGroup>();
				if(CentralUtils.gettHumanInteractions().getLogicalPeopleGroups()!= null){
					groups=(ArrayList<TLogicalPeopleGroup>) CentralUtils.gettHumanInteractions().getLogicalPeopleGroups().getLogicalPeopleGroup();
				}
				for(TLogicalPeopleGroup logic:groups){
					TNotificationComposite tNot;
					if((composites.size()==groups.size())){
						tNot=composites.get(j);
						tNot.initialize(j,ch2);
						System.out.println("Equals");
					}else{
						tNot= new TNotificationComposite(editor,composite,j, SWT.NONE);
						composites.add(j,tNot);
						//int index=ch.tHumanInteractions.getLogicalPeopleGroups().getLogicalPeopleGroup().indexOf(tl);
						System.out.println("Not Equals");
					}
					
					
					composites.get(j).setBounds(20,(j+1)*110,sc3.getSize().x,100);
					sc3.setMinSize(secLogicalPeopleGroups.computeSize(SWT.DEFAULT, SWT.DEFAULT));
					sc3.layout(true,true);
					secLogicalPeopleGroups.layout(true,true);
					j++;
					System.out.println("j  value"+j);
				}
				l=j;
				System.out.println("refresh l value is "+l);
			}
			});
		
		btnNewButton.addListener(SWT.Selection, new Listener() {
			int i=0;	
			
			@Override
			public void handleEvent(Event event) {
				if(i==0){
					i=l;
				}
				System.out.println("new button l value is "+i);
				TNotificationComposite tNot= new TNotificationComposite(editor,composite,i, SWT.NONE);
				composites.add(i,tNot);
				     ///////// Check This
				sc3.setMinSize(secLogicalPeopleGroups.computeSize(SWT.DEFAULT, SWT.DEFAULT));
				sc3.layout(true,true);
				secLogicalPeopleGroups.layout(true,true);
				
				System.out.println("hikz value is "+i);
				try {
					CentralUtils ch=CentralUtils.getInstance(editor);
					if(ch.gettHumanInteractions().getLogicalPeopleGroups() == null){
						TLogicalPeopleGroups tLogicalPeopleGroups=new TLogicalPeopleGroups();
						ch.gettHumanInteractions().setLogicalPeopleGroups(tLogicalPeopleGroups);
					}
				} catch (JAXBException e) {
					e.printStackTrace();
				}
				
				i++;
			}
			
		});
		
		
		//secLogicalPeopleGroups.setTextClient(btnNewButton);
		
		
		secLogicalPeopleGroups.setTextClient(textCompo);*/

	}
	
	
}