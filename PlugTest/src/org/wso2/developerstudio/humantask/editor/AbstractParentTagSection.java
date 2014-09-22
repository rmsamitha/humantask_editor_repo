package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Combo;

public abstract class AbstractParentTagSection extends Section {
	protected FormToolkit toolkit;
	protected ArrayList<Text> textBoxes;
	protected int compositeIndex = 0;
	protected CentralUtils centralUtils;
	protected Section innerSection;
	protected Button btnNewgroup;
	protected int i = 0;

	public AbstractParentTagSection(final XMLEditor editor, Composite parent,
			int style, final ScrolledComposite rootScrolledComposite) throws JAXBException {
		super(parent, Section.TWISTIE | Section.TITLE_BAR);
		centralUtils=CentralUtils.getInstance(editor);
		textBoxes=new ArrayList<Text>();
		toolkit = new FormToolkit(Display.getCurrent());
		setLayout(new GridLayout(1,true));
		toolkit.paintBordersFor(this);
		setText("AbstractParent");
		setExpanded(true);

		System.out.println("created a new Task section.");
		final ScrolledComposite scrolledComposite_1 = new ScrolledComposite(
				this,  SWT.H_SCROLL);
		scrolledComposite_1.setMinHeight(-1);
		scrolledComposite_1.setBackground(SWTResourceManager.getColor(165, 42,
				42));
		toolkit.adapt(scrolledComposite_1);
		toolkit.paintBordersFor(scrolledComposite_1);
		this.setClient(scrolledComposite_1);
		scrolledComposite_1.setExpandHorizontal(true);
		scrolledComposite_1.setExpandVertical(true);
		scrolledComposite_1.setLayout(new GridLayout(1,true));
		
		final Composite composite_SecTASK_SC = new Composite(scrolledComposite_1, SWT.NONE);
		composite_SecTASK_SC.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		toolkit.adapt(composite_SecTASK_SC);
		toolkit.paintBordersFor(composite_SecTASK_SC);
		composite_SecTASK_SC.setLayout(new GridLayout(1, true));
		scrolledComposite_1.setContent(composite_SecTASK_SC);
		
		fillDetailArea(composite_SecTASK_SC);
		
		Composite composite = new Composite(this, SWT.NONE);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		setTextClient(composite);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final Combo combo = new Combo(composite, SWT.NONE);
		combo.setFont(SWTResourceManager.getFont("Ubuntu", 6, SWT.NORMAL));
		toolkit.adapt(combo);
		toolkit.paintBordersFor(combo);
		combo.setItems(new String[] {"a", "b", "c", "d", "e"});
		
		btnNewgroup = new Button(composite, SWT.NONE);
		
		toolkit.adapt(btnNewgroup, true, true);
		btnNewgroup.setText("NewGroup");
		
		Button btnRefresh = new Button(composite, SWT.NONE);
		toolkit.adapt(btnRefresh, true, true);
		btnRefresh.setText("Refresh");
		
		final ArrayList<Section> sectionsAL = new ArrayList<Section>();
		
		btnRefresh.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				try {
					refreshLogic(editor, sectionsAL, composite_SecTASK_SC, rootScrolledComposite);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewgroup.addListener(SWT.Selection, new Listener() {
			

			@Override
			public void handleEvent(Event event) {
				try {
					System.out.println("in the listner of abstract class-1" );
					//String s=combo.getItem(combo.getSelectionIndex());
					newButtonLogic("s",compositeIndex, sectionsAL, rootScrolledComposite, editor, composite_SecTASK_SC);
					System.out.println("in the listner of abstract class-2" );
					compositeIndex++;
				} catch (JAXBException e) {
					e.printStackTrace();
				}

			}

		});
		
	}

	public abstract void refreshLogic(XMLEditor editor,
			ArrayList<Section> composites, Composite composite,
			ScrolledComposite sc3) throws JAXBException;

	public abstract void newButtonLogic(String selection,int i, ArrayList<Section> composites,
			ScrolledComposite sc3, XMLEditor editor, Composite parent) throws JAXBException;

	public abstract void fillDetailArea(Composite composite) ;
}
