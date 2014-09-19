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

public abstract class AbstractParentSection extends Section {
	protected FormToolkit toolkit;
	protected ArrayList<Text> textBoxes;
	protected int compositeIndex = 0;

	public AbstractParentSection(final XMLEditor editor, Composite parent,
			int style) {
		super(parent, Section.TWISTIE | Section.TITLE_BAR);
		toolkit = new FormToolkit(Display.getCurrent());
		setLayout(new GridLayout(1,true));
		toolkit.paintBordersFor(this);
		setText("AbstractParent");
		setExpanded(true);

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
		
		Composite composite_SecTASK_SC = new Composite(scrolledComposite_1, SWT.NONE);
		composite_SecTASK_SC.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		toolkit.adapt(composite_SecTASK_SC);
		toolkit.paintBordersFor(composite_SecTASK_SC);
		composite_SecTASK_SC.setLayout(new GridLayout(1, true));
		scrolledComposite_1.setContent(composite_SecTASK_SC);
		
		fillDetailArea(composite_SecTASK_SC);
		
	}

	public abstract void refreshLogic(XMLEditor editor,
			ArrayList<Composite> composites, Composite composite,
			ScrolledComposite sc3) throws JAXBException;

	public abstract void newButtonLogic(String selection,int i, ArrayList<Composite> composites,
			ScrolledComposite sc3, XMLEditor editor, Composite composite) throws JAXBException;

	public abstract void fillDetailArea(Composite composite) ;
}
