package org.wso2.developerstudio.humantask.editor;

import java.util.ArrayList;

import javax.xml.bind.JAXBException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public abstract class AbstractParentTagSection extends Section {
	protected final FormToolkit toolkit;
	protected ArrayList<Widget> textBoxes;
	protected CentralUtils centralUtils;
	protected Section innerSection;
	protected int i = 0;
	protected Composite compositeDetailArea;

	public AbstractParentTagSection(final XMLEditor editor, Composite parent,
			int style, final String[] dropDownItems, String name)
			throws JAXBException {
		super(parent, Section.TWISTIE | Section.TITLE_BAR);
		centralUtils = CentralUtils.getInstance(editor);
		textBoxes = new ArrayList<Widget>();
		toolkit = new FormToolkit(Display.getCurrent());
		setLayout(new GridLayout(1, false));
		toolkit.adapt(this);
		/*setLayoutData(new GridData(GridData.FILL_BOTH));*/
		setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
                1, 1));
		toolkit.paintBordersFor(this);
		setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TREE_BORDER);
		this.setTitleBarBackground(SWTResourceManager
				.getColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
		setText(name);// to check
		setExpanded(true);

		// S this.setBackground(SWTResourceManager.getColor(212, 222, 246));

		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});

		final ScrolledComposite scDetailarea = new ScrolledComposite(this,
				SWT.H_SCROLL);
		scDetailarea.setMinHeight(-1);
		/*
		 * scDetailarea.setBackground(SWTResourceManager.getColor(165, 42, 42));
		 */
		toolkit.adapt(scDetailarea);
		toolkit.paintBordersFor(scDetailarea);
		this.setClient(scDetailarea);
		scDetailarea.setExpandHorizontal(true);
		scDetailarea.setExpandVertical(true);
		scDetailarea.setLayout(new GridLayout(1, false));

		compositeDetailArea = new Composite(scDetailarea, SWT.NONE);
		/*
		 * compositeDetailArea.setBackground(SWTResourceManager
		 * .getColor(SWT.COLOR_DARK_RED));
		 */
		toolkit.adapt(compositeDetailArea);
		toolkit.paintBordersFor(compositeDetailArea);
		// compositeDetailArea.setLayout(new GridLayout(6, true));

		fillDetailArea(compositeDetailArea);
		scDetailarea.setContent(compositeDetailArea);
		compositeDetailArea.setLayout(new GridLayout(1, true));
		// mghprlnkNewImagehyperlink.setText("New ImageHyperlink");
		// mghprlnkNewImagehyperlink.setImage(new Image(getDisplay(),
		// "icons/sample.gif"));

		// Shell compositeTextClient= new Shell();

		/*Composite compositeTextClient = new Composite(this, SWT.NONE);
		compositeTextClient.setBackgroundMode(SWT.INHERIT_FORCE);
		toolkit.adapt(compositeTextClient);
		toolkit.paintBordersFor(compositeTextClient);
		setTextClient(compositeTextClient);
		RowLayout rl_compositeTextClient = new RowLayout(SWT.HORIZONTAL);
		rl_compositeTextClient.marginTop = 0;
		rl_compositeTextClient.marginRight = 0;
		rl_compositeTextClient.marginLeft = 0;
		rl_compositeTextClient.marginBottom = 0;
		compositeTextClient.setLayout(rl_compositeTextClient);*/
		
		
		//////////
		   ToolBar toolBarTitle = new ToolBar(this, SWT.FLAT | SWT.RIGHT);

	        setTextClient(toolBarTitle);

	        ToolItem btnNewgroup = new ToolItem(toolBarTitle, SWT.NONE);
	        btnNewgroup.setToolTipText("Add Child Element");
	        btnNewgroup.setWidth(27);
	        btnNewgroup.setImage(ResourceManager.getPluginImage("PlugTest", "icons/rsz_rsz_1412072977_plus_add_blueedited.png"));

	        ToolItem btnUpdate = new ToolItem(toolBarTitle, SWT.NONE);
	        btnUpdate.setToolTipText("Update XML");
	        btnUpdate.setImage(ResourceManager.getPluginImage("PlugTest", "icons/rsz_rsz_software_update.png"));

	        ToolItem btnRemove = new ToolItem(toolBarTitle, SWT.CHECK);
	        btnRemove.setToolTipText("Remove");
	        btnRemove.setImage(ResourceManager.getPluginImage("PlugTest", "icons/rsz_rsz_1rsz_112.png"));


	        final Shell shell = new Shell(this.getDisplay());

	        final Menu menu = new Menu(shell, SWT.POP_UP);

	        ArrayList<MenuItem> menuItemArrayList = new ArrayList<MenuItem>();
	        for (int x = 0; x < dropDownItems.length; x++) {
	            menuItemArrayList.add(new MenuItem(menu, SWT.NONE));
	            menuItemArrayList.get(x).setText(dropDownItems[x]);
	            final String v = dropDownItems[x];

	            menuItemArrayList.get(x).addListener(SWT.Selection, new Listener() {
	                public void handleEvent(Event event) {
	                    System.out.println("item selected: RADIO item");
	                    try {
	                        newButtonLogic(v, scDetailarea, editor,
	                                compositeDetailArea);
	                    } catch (JAXBException e) {
	                        e.printStackTrace();
	                    }
	                }
	            });

	        }

	        btnNewgroup.addListener(SWT.Selection, new Listener() {

	            @Override
	            public void handleEvent(Event event) {
	                menu.setVisible(true);
	            }
	        });
		
		///////////////////

		/*
		 * final Combo combo = new Combo(compositeTextClient, SWT.NONE);
		 * combo.setLayoutData(new RowData(100, 25));
		 * combo.setFont(SWTResourceManager.getFont("Ubuntu", 9, SWT.NORMAL));
		 * toolkit.adapt(combo); toolkit.paintBordersFor(combo);
		 * combo.setItems(dropDownItems); combo.select(0);
		 */
		/*final Button btnNewgroup = new Button(compositeTextClient, SWT.NONE);

		btnNewgroup.setToolTipText("Add Child Element");
		btnNewgroup.setLayoutData(new RowData(24, 24));
		btnNewgroup.setImage(ResourceManager.getPluginImage("PlugTest",
				"icons/rsz_add.png"));
		// btnNewgroup.setImage(ResourceManager.getPluginImage("PlugTest",
		// "icons/rsz_software_update.jpg"));

		toolkit.adapt(btnNewgroup, true, true);*/

		/*final Menu menu = new Menu(btnNewgroup);
		btnNewgroup.setMenu(menu);
		
		 * MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		 * mntmNewItem.setText("New Item"); MenuItem mntmNewItem2 = new
		 * MenuItem(menu, SWT.NONE); mntmNewItem2.setText("New Item2");
		 * 
		 * menu.setVisible(true);
		 */

		/*ArrayList<MenuItem> menuItemArrayList = new ArrayList<MenuItem>();
		for (int x = 0; x < dropDownItems.length; x++) {
			menuItemArrayList.add(new MenuItem(menu, SWT.NONE));
			menuItemArrayList.get(x).setText(dropDownItems[x]);
			final String v = dropDownItems[x];

			menuItemArrayList.get(x).addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					System.out.println("item selected: RADIO item");
					try {
						newButtonLogic(v, scDetailarea, editor,
								compositeDetailArea);
					} catch (JAXBException e) {
						e.printStackTrace();
					}
				}
			});

		}*/

		/*btnNewgroup.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				menu.setVisible(true);
			}
		});*/

		// dropDownItems
		// menu.add

		/*Button btnRefresh = new Button(compositeTextClient, SWT.NONE);
		toolkit.adapt(btnRefresh, true, true);
		btnRefresh.setText("Refresh");*/

		/*Button btnUpdate = new Button(compositeTextClient, SWT.NONE);
		btnUpdate.setLayoutData(new RowData(24, 24));
		// btnUpdate.s
		btnUpdate.setToolTipText("Update");
		btnUpdate.setImage(ResourceManager.getPluginImage("PlugTest",
				"icons/rsz_software_update.png"));
		toolkit.adapt(btnUpdate, true, true);

		Button btnRemove = new Button(compositeTextClient, SWT.NONE);
		btnRemove.setLayoutData(new RowData(24, 24));
		btnRemove.setToolTipText("Remove");
		btnRemove.setImage(ResourceManager.getPluginImage("PlugTest",
				"icons/rsz_1rsz_112.png"));
		toolkit.adapt(btnRemove, true, true);*/

		// Label lblNewLabel = toolkit.createLabel(compositeTextClient, "",
		// SWT.NONE);
		// lblNewLabel.setImage(ResourceManager.getPluginImage("PlugTest",
		// "icons/rsz_1rsz_112.png"));
		// // lblNewLabel.setImage(image);

		// ImageHyperlink ih=new ImageHyperlink(compositeDetailArea, SWT.NONE);
		// ih.setImage(image);

		/*
		 * ImageHyperlink ih = new ImageHyperlink(compositeTextClient,
		 * SWT.NONE); ih.setLayoutData(new RowData(27, 30));
		 * ih.setToolTipText("Remove");
		 * ih.setImage(ResourceManager.getPluginImage("PlugTest",
		 * "icons/rsz_1rsz_112.png"));
		 */
		// new Label(compositeDetailArea, SWT.NONE);

		/*
		 * ih.addListener(SWT.Selection, new Listener() {
		 * 
		 * @Override public void handleEvent(Event event) { try {
		 * btnRemoveHandleLogic(editor); } catch (JAXBException e) {
		 * e.printStackTrace(); } }
		 * 
		 * });
		 */
		/*
		 * ih.addHyperlinkListener(new HyperlinkAdapter() { public void
		 * linkActivated(HyperlinkEvent e) {
		 * System.out.println("Image hyperlink activated."); try {
		 * btnRemoveHandleLogic(editor); } catch (JAXBException e2) {
		 * e2.printStackTrace(); } } });
		 */

		/*btnRefresh.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				try {
					refreshLogic(editor);
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		});*/

		/*
		 * btnNewgroup.addListener(SWT.Selection, new Listener() {
		 * 
		 * @Override public void handleEvent(Event event) { try { String s =
		 * combo.getItem(combo.getSelectionIndex()); newButtonLogic(s,
		 * scDetailarea, editor, compositeDetailArea); } catch (JAXBException e)
		 * { e.printStackTrace(); }
		 * 
		 * }
		 * 
		 * });
		 */

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

	public abstract void refreshLogic(XMLEditor editor) throws JAXBException;

	public abstract void initialize(XMLEditor textEditor) throws JAXBException;

	public abstract void newButtonLogic(String selection,
			ScrolledComposite sc3, XMLEditor editor, Composite composite)
			throws JAXBException;

	public abstract void fillDetailArea(Composite composite);

	public abstract void refreshChildren(String itemName,
			int childCompositeIndex, int childObjectIndex);

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