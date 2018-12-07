package pt.iscte.pidesco.hierarchy.internal;

import java.io.File;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import pt.iscte.pidesco.hierarchy.visitor.ClassInfoVisitor;
import pt.iscte.pidesco.extensibility.PidescoView;
import pt.iscte.pidesco.javaeditor.service.JavaEditorListener;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;
import pt.iscte.pidesco.projectbrowser.model.SourceElement;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserListener;
import pt.iscte.pidesco.projectbrowser.service.ProjectBrowserServices;

public class TypeHierarchyView implements PidescoView {

	private Label label_1;
	private ClassInfoVisitor classInfoVisitor;
	private CheckClassInfo checkClassInfo;

	/**
	 * creates type hierarchy main component
	 * 
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

		final Tree tree = new Tree(viewArea, SWT.VIRTUAL);
		tree.setSize(500, 500);

		getOpennedClassInfo(javaServ.getOpenedFile().getPath(), javaServ);

		if (javaServ.getOpenedFile() != (null)) {
			TreeItem treeItem0 = new TreeItem(tree, 0);

			treeItem0.setText(javaServ.getOpenedFile().getPath());

			TreeItem treeItem1 = new TreeItem(treeItem0, 0);
			// treeItem1.setText(javaServ.getOpenedFile().get);
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

	private static class CheckClassInfo extends ASTVisitor {

		// visits class/interface declaration
		// Names of classes/interfaces must start with an
		// uppercase letter and cannot have underscores;
		@Override
		public boolean visit(PackageDeclaration node) {
			String name = node.getName().toString();
			System.out.println("Package " + name);

			
			return super.visit(node);
		}

		@Override
		public boolean visit(TypeDeclaration node) {
			String name = node.getName().toString();
			System.out.println("Class " + name);

			
			return true;
		}

		
		
		@Override
		public boolean visit(ClassInstanceCreation node) {
			return true;
		}

	}

	private void getOpennedClassInfo(String path, JavaEditorServices javaServ) {
		System.out.println("getClassInfo.............. " + path);
		checkClassInfo = new CheckClassInfo();

		javaServ.parseFile(javaServ.getOpenedFile(), checkClassInfo);

		// JavaParser.parse(path, checkClassInfo);

		// JavaParser.parse(path, classInfoVisitor);

		// javaClassVisitor=new JavaClassVisitor("E:\\MEI\\Ano
		// 2\\PA\\WS\\BrowserTest\\src\\");
		// javaClassVisitor.accept(new PrintVisitor());
	}

}
