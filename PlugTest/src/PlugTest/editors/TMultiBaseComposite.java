package plugtest.editors;

import javax.xml.bind.JAXBException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public abstract class TMultiBaseComposite extends Composite {
	protected final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	protected CentralUtils centralHandler;
	protected Text txtNewText,txtReference;
	protected Composite detailArea;
	
	public TMultiBaseComposite(final XMLEditor editor,Composite parent,final int index, int style,String text) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		try {
			centralHandler=CentralUtils.getInstance(editor);
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		toolkit.adapt(this);
		FormLayout formLayout = new FormLayout();
		setLayout(formLayout);
		final ScrolledComposite sc2 = new ScrolledComposite(this, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.BORDER);
		sc2.setLayout(new GridLayout());
		sc2.setExpandHorizontal(true);
		sc2.setExpandVertical(true);
		
		final Composite c2 = new Composite(sc2, SWT.NONE);
		sc2.setContent(c2);
		c2.setLayout(new FormLayout());

		final Section innerSection = toolkit.createSection(c2,
				Section.TWISTIE | Section.TITLE_BAR);
		innerSection.setLayout(new GridLayout());
		innerSection.setText(text);
		innerSection.setExpanded(true);
		detailArea = toolkit.createComposite(innerSection,
				SWT.NONE);
		fillDetailArea();
		innerSection.setClient(detailArea);
		Button btnNewButton = toolkit.createButton(innerSection,
				"Update", SWT.NONE);
		innerSection.setTextClient(btnNewButton);
		try {
			initialize(index,centralHandler);
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btnNewButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				try {
					newButtonHandleLogic(index,editor);
				} catch (JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public abstract void newButtonHandleLogic(int i,XMLEditor editor) throws JAXBException;
	
	public abstract void initialize(int i,CentralUtils ch2) throws JAXBException;
	
	public abstract void fillDetailArea();

}
