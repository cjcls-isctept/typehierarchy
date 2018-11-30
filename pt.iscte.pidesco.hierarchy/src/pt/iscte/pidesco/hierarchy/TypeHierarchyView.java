package pt.iscte.pidesco.hierarchy;

import java.io.File;
import java.util.Map;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.PackageElement;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TypeHierarchyView implements PidescoView {

	private Label label_1;



	/**
	 * creates type hierarchy main component
	 * @param viewArea is the area in which all the components are inserted
	 * @param imageMap
	 */
	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		viewArea.setLayout(new RowLayout(SWT.VERTICAL));
		BundleContext context = Activator.getContext();
		
		ServiceReference<ProjectBrowserServices> serviceReference = context
				.getServiceReference(ProjectBrowserServices.class);
		ProjectBrowserServices projServ = context.getService(serviceReference);

		ServiceReference<JavaEditorServices> serviceReference2 = context.getServiceReference(JavaEditorServices.class);
		JavaEditorServices javaServ = context.getService(serviceReference2);

		final Tree tree = new Tree(viewArea, SWT.V_SCROLL | SWT.H_SCROLL);
		//tree.setSize(290, 260);
		if (javaServ.getOpenedFile() != (null)) {
			TreeItem treeItem0 = new TreeItem(tree, 0);
			treeItem0.setText(javaServ.getOpenedFile().getAbsolutePath());
			TreeItem treeItem1 = new TreeItem(treeItem0, 0);
	        treeItem1.setText("Level 1 Item ");
			viewArea.layout();
		}

		javaServ.addListener(new JavaEditorListener.Adapter() {
			@Override
			public void fileOpened(File file) {

			}

		});

		projServ.addListener(new ProjectBrowserListener.Adapter() {
			@Override
			public void doubleClick(SourceElement element) {

			}

		});


	}

}
