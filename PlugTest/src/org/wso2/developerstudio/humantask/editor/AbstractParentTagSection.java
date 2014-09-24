package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;

public abstract class AbstractParentTagSection extends Section {
	protected final FormToolkit toolkit;
	protected ArrayList<Widget> textBoxes; 
	protected CentralUtils centralUtils;
	protected Section innerSection;
	protected int i = 0;


    public AbstractParentTagSection(final XMLEditor editor, Composite parent,
            int style, String [] dropDownItems)
            throws JAXBException {
        super(parent, Section.TWISTIE | Section.TITLE_BAR);
        centralUtils = CentralUtils.getInstance(editor);
        textBoxes=new ArrayList<Widget>();
        toolkit = new FormToolkit(Display.getCurrent());
        setLayout(new GridLayout(1, true));
        toolkit.paintBordersFor(this);
        setText("AbstractParent");// to check
        setExpanded(true);

        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                toolkit.dispose();
            }
        });
        final ScrolledComposite scDetailarea = new ScrolledComposite(
                this, SWT.H_SCROLL);
        scDetailarea.setMinHeight(-1);
        scDetailarea.setBackground(SWTResourceManager.getColor(165, 42,
                42));
        toolkit.adapt(scDetailarea);
        toolkit.paintBordersFor(scDetailarea);
        this.setClient(scDetailarea);
        scDetailarea.setExpandHorizontal(true);
        scDetailarea.setExpandVertical(true);
        scDetailarea.setLayout(new GridLayout(1, true));

        final Composite compositeDetailArea = new Composite(
                scDetailarea, SWT.NONE);
        compositeDetailArea.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_DARK_RED));
        toolkit.adapt(compositeDetailArea);
        toolkit.paintBordersFor(compositeDetailArea);
        compositeDetailArea.setLayout(new GridLayout(1, true));
        scDetailarea.setContent(compositeDetailArea);

        fillDetailArea(compositeDetailArea);

        Composite compositeTextClient = new Composite(this, SWT.NONE);
        toolkit.adapt(compositeTextClient);
        toolkit.paintBordersFor(compositeTextClient);
        setTextClient(compositeTextClient);
        compositeTextClient.setLayout(new FillLayout(SWT.HORIZONTAL));

        final Combo combo = new Combo(compositeTextClient, SWT.NONE);
        combo.setFont(SWTResourceManager.getFont("Ubuntu", 6, SWT.NORMAL));
        toolkit.adapt(combo);
        toolkit.paintBordersFor(combo);
        combo.setItems(dropDownItems);
        Button btnNewgroup = new Button(compositeTextClient, SWT.NONE);

        toolkit.adapt(btnNewgroup, true, true);
        btnNewgroup.setText("NewGroup");

        Button btnRefresh = new Button(compositeTextClient, SWT.NONE);
        toolkit.adapt(btnRefresh, true, true);
        btnRefresh.setText("Refresh");

        Button btnUpdate = new Button(compositeTextClient, SWT.NONE);
        toolkit.adapt(btnUpdate, true, true);
        btnUpdate.setText("Update");

        Button btnRemove = new Button(compositeTextClient, SWT.NONE);
        toolkit.adapt(btnRemove, true, true);
        btnRemove.setText("Remove");


        btnRefresh.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                try {
                    refreshLogic(editor, compositeDetailArea,
                            scDetailarea);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        });
        btnNewgroup.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                try {
                	String s=combo.getItem(combo.getSelectionIndex());
					newButtonLogic(s, scDetailarea, editor, compositeDetailArea);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }

            }

        });
        btnRemove.addListener(SWT.Selection, new Listener() {
           
            @Override
            public void handleEvent(Event event) {
                try {
                    btnRemoveHandleLogic(editor);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }               
            }

        });
        btnUpdate.addListener(SWT.Selection, new Listener() {
           
            @Override
            public void handleEvent(Event event) {
                try {
                    btnUpdateHandleLogic(editor);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }               
            }
        });

    }

   
	public abstract void btnUpdateHandleLogic(XMLEditor textEditor)
			throws JAXBException;
	
	public abstract void btnRemoveHandleLogic(XMLEditor textEditor)
			throws JAXBException;
	
	public abstract void refreshLogic(XMLEditor editor, Composite composite,ScrolledComposite sc3) throws JAXBException;
	
	public abstract void initialize(XMLEditor textEditor)
			throws JAXBException;
	
	public abstract void newButtonLogic(String selection,
			ScrolledComposite sc3, XMLEditor editor, Composite composite) throws JAXBException;

	public abstract void fillDetailArea(Composite composite) ;
	
	public abstract void refreshChildren(int childCompositeIndex,int childObjectIndex);
}
