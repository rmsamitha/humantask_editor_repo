package org.wso2.developerstudio.humantask.editor;

//import java.awt.Button;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public abstract class AbstractEndTagSection extends Section {
	protected final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	protected CentralUtils centralUtils;
	protected ArrayList<Text> textBoxes;
	protected Composite detailArea,zcontainer;

    public AbstractEndTagSection(final XMLEditor textEditor,
			Composite parent,Composite container, int style,
			String sectionTitle) {
        super(parent, Section.TWISTIE | Section.TITLE_BAR);
       
        setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
        
        setText(sectionTitle);
        setExpanded(true);
        textBoxes = new ArrayList<Text>();
        zcontainer=container;
        setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                toolkit.dispose();
            }
        });

       
        setBackground(SWTResourceManager.getColor(0, 51, 0));
        setTitleBarBackground(new Color(getDisplay(), 228,210,247));
        toolkit.adapt(this);
        toolkit.paintBordersFor(this);
       
        try {
            centralUtils = CentralUtils.getInstance(textEditor);
        } catch (JAXBException e1) {
            e1.printStackTrace();
        }

        // <<Layouting controls
        RowLayout secTaskRL = new RowLayout();
        secTaskRL.type = SWT.VERTICAL;
        this.setLayout(secTaskRL);
        toolkit.paintBordersFor(this);

        final ScrolledComposite scrolledComposite_1 = new ScrolledComposite(
                this, SWT.H_SCROLL | SWT.V_SCROLL);
        scrolledComposite_1.setBackground(SWTResourceManager.getColor(165, 42,
                42));
        toolkit.adapt(scrolledComposite_1);
        toolkit.paintBordersFor(scrolledComposite_1);
        this.setClient(scrolledComposite_1);
        scrolledComposite_1.setExpandHorizontal(true);
        scrolledComposite_1.setExpandVertical(true);

        detailArea = toolkit.createComposite(scrolledComposite_1, SWT.NONE);
        detailArea.setBackground(SWTResourceManager
                .getColor(SWT.COLOR_DARK_RED));
        // composite_SecTASK_SC.set
        toolkit.adapt(detailArea);
        toolkit.paintBordersFor(detailArea);
        scrolledComposite_1.setContent(detailArea);
       // scrolledComposite_1.setMinSize(detailArea.computeSize(
        //        SWT.DEFAULT, SWT.DEFAULT));

        RowLayout rL = new RowLayout();
        GridLayout gl_composite_SecTASK_SC = new GridLayout(6, false);
        // gl_composite_SecTASK_SC.
        gl_composite_SecTASK_SC.marginLeft = 5;
        detailArea.setLayout(gl_composite_SecTASK_SC);

        // layouting controls>>

        try {
            fillDetailArea();
        } catch (NullPointerException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        
        
        ToolBar toolBarTitle = new ToolBar(this, SWT.FLAT | SWT.RIGHT | SWT.SHADOW_OUT);
        setTextClient(toolBarTitle);
        ToolItem btnUpdate = new ToolItem(toolBarTitle, SWT.NONE);
        btnUpdate.setToolTipText("Update XML");
        btnUpdate.setImage(ResourceManager.getPluginImage("PlugTest", "icons/rsz_rsz_software_update.png"));

        ToolItem btnRemove = new ToolItem(toolBarTitle, SWT.CHECK);
        btnRemove.setToolTipText("Remove");
        btnRemove.setImage(ResourceManager.getPluginImage("PlugTest", "icons/rsz_rsz_1rsz_112.png"));

        // scr.setClient(detailArea);
       /* Composite textClientComposite = toolkit.createComposite(this, SWT.NO_BACKGROUND);
        setTextClient(textClientComposite);
        RowLayout rl_compositeTextClient = new RowLayout(SWT.HORIZONTAL);
        rl_compositeTextClient.marginTop = 0;
        rl_compositeTextClient.marginRight = 0;
        rl_compositeTextClient.marginLeft = 0;
        rl_compositeTextClient.marginBottom = 0;
        textClientComposite.setLayout(rl_compositeTextClient);*/

     /*   Button btnUpdate = toolkit.createButton(textClientComposite, "Update",
                SWT.CENTER);
        btnUpdate.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
            }
        });
        btnUpdate.setText("Update");
       
        Button btnRemove = new Button(textClientComposite, SWT.NONE);
        toolkit.adapt(btnRemove, true, true);
        btnRemove.setText("Remove");*/
       
        btnRemove.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                try {
                    btnRemoveHandleLogic(textEditor);
                    centralUtils.redraw();
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }
        });

        btnUpdate.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                try {
                    btnUpdateHandleLogic(textEditor);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            }

        });

    }
    @Override
    public void addDisposeListener(DisposeListener listener) {
        // TODO Auto-generated method stub
       
        super.addDisposeListener(new DisposeListener() {
           
            @Override
            public void widgetDisposed(DisposeEvent e) {
               /* System.out.println("isss disposed");       
                ((ExpandableComposite) zcontainer).setExpanded(true);
               System.out.println("isss yes"); */      
            }
        });
    }

    public abstract void btnUpdateHandleLogic(XMLEditor textEditor)
			throws JAXBException;
	
	public abstract void btnRemoveHandleLogic(XMLEditor textEditor)
			throws JAXBException;

	public abstract void initialize(XMLEditor textEditor)
			throws JAXBException;

	public abstract void fillDetailArea();

	public void drawBorder(final Composite composite) {
		composite.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
				e.gc.drawLine(0, 0, composite.getParent().getBounds().width, 0);
			}

		});
	}
}
