package GUI;


import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.Scene;

import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.GraphicsConfiguration;
import java.net.MalformedURLException;
import java.net.URL;
import com.sun.j3d.utils.behaviors.vp.*;
import java.io.FileNotFoundException;

/**
 * Simple Java 3D example program to display an .obj object.
 */
public class DisplayPanel extends javax.swing.JPanel {

	private boolean spin = false;
	private boolean noTriangulate = false;
	private boolean noStripify = false;
	private double creaseAngle = 60.0;
	private URL filename = null;

	private SimpleUniverse univ = null;
	private BranchGroup scene = null;

	public BranchGroup createSceneGraph() {
		// Create the root of the branch graph
		BranchGroup objRoot = new BranchGroup();

		// Create a Transformgroup to scale all objects so they
		// appear in the scene.
		TransformGroup objScale = new TransformGroup();
		Transform3D t3d = new Transform3D();
		t3d.setScale(0.7);
		objScale.setTransform(t3d);
		objRoot.addChild(objScale);

		// Create the transform group node and initialize it to the
		// identity. Enable the TRANSFORM_WRITE capability so that
		// our behavior code can modify it at runtime. Add it to the
		// root of the subgraph.
		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objScale.addChild(objTrans);

		int flags = ObjectFile.RESIZE;
		if (!noTriangulate)
			flags |= ObjectFile.TRIANGULATE;
		if (!noStripify)
			flags |= ObjectFile.STRIPIFY;
		ObjectFile f = new ObjectFile(flags,
				(float) (creaseAngle * Math.PI / 180.0));
		Scene s = null;
		try {
			s = f.load(filename);
		} catch (FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		} catch (ParsingErrorException e) {
			System.err.println(e);
			System.exit(1);
		} catch (IncorrectFormatException e) {
			System.err.println(e);
			System.exit(1);
		}

		objTrans.addChild(s.getSceneGroup());

		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		if (spin) {
			Transform3D yAxis = new Transform3D();
			Alpha rotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0,
					4000, 0, 0, 0, 0, 0);

			RotationInterpolator rotator = new RotationInterpolator(
					rotationAlpha, objTrans, yAxis, 0.0f,
					(float) Math.PI * 2.0f);
			rotator.setSchedulingBounds(bounds);
			objTrans.addChild(rotator);
		}

		// Set up the background
		Color3f bgColor = new Color3f(0.05f, 0.05f, 0.5f);
		Background bgNode = new Background(bgColor);
		bgNode.setApplicationBounds(bounds);
		objRoot.addChild(bgNode);

		return objRoot;
	}

	private Canvas3D createUniverse() {
		// Get the preferred graphics configuration for the default screen
		GraphicsConfiguration config = SimpleUniverse
				.getPreferredConfiguration();

		// Create a Canvas3D using the preferred configuration
		Canvas3D canvas3d = new Canvas3D(config);

		// Create simple universe with view branch
		univ = new SimpleUniverse(canvas3d);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				100.0);

		// add mouse behaviors to the ViewingPlatform
		ViewingPlatform viewingPlatform = univ.getViewingPlatform();

		PlatformGeometry pg = new PlatformGeometry();

		// Set up the ambient light
		Color3f ambientColor = new Color3f(0.1f, 0.1f, 0.1f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		pg.addChild(ambientLightNode);

		// Set up the directional lights
		Color3f light1Color = new Color3f(1.0f, 1.0f, 0.9f);
		Vector3f light1Direction = new Vector3f(1.0f, 1.0f, 1.0f);
		Color3f light2Color = new Color3f(1.0f, 1.0f, 1.0f);
		Vector3f light2Direction = new Vector3f(-1.0f, -1.0f, -1.0f);

		DirectionalLight light1 = new DirectionalLight(light1Color,
				light1Direction);
		light1.setInfluencingBounds(bounds);
		pg.addChild(light1);

		DirectionalLight light2 = new DirectionalLight(light2Color,
				light2Direction);
		light2.setInfluencingBounds(bounds);
		pg.addChild(light2);

		viewingPlatform.setPlatformGeometry(pg);

		// This will move the ViewPlatform back a bit so the
		// objects in the scene can be viewed.
		viewingPlatform.setNominalViewingTransform();

		if (!spin) {
			OrbitBehavior orbit = new OrbitBehavior(canvas3d,
					OrbitBehavior.REVERSE_ALL);
			orbit.setSchedulingBounds(bounds);
			viewingPlatform.setViewPlatformBehavior(orbit);
		}

		// Ensure at least 5 msec per frame (i.e., < 200Hz)
		univ.getViewer().getView().setMinimumFrameCycleTime(5);

		return canvas3d;
	}

	/**
	 * Creates new form ObjLoad
	 */
	public DisplayPanel() {
		
	}

	private void createScene()
	{
		// Initialize the GUI components
		initComponents();

		// Create Canvas3D and SimpleUniverse; add canvas to drawing panel
		myCanvas3D = createUniverse();
		this.add(myCanvas3D, java.awt.BorderLayout.CENTER);

		// Create the content branch and add it to the universe
		scene = createSceneGraph();
		univ.addBranchGraph(scene);
	}
	// ----------------------------------------------------------------

	public void setObjectFile(String fileName) {
		sFileName = fileName;

		if ( myCanvas3D != null )
		{
			this.remove(myCanvas3D);
		}
		
		try
		{
			filename = new URL("file:" + fileName );
		} catch ( MalformedURLException e )
		{
			System.out.println(e);
		}
		
		createScene();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents() {
		// drawingPanel = new javax.swing.JPanel();

		// setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		// setTitle("ObjLoad");
		this.setLayout(new java.awt.BorderLayout());

		this.setPreferredSize(new java.awt.Dimension(700, 700));
		// getContentPane().add(drawingPanel, java.awt.BorderLayout.CENTER);

	}// </editor-fold>//GEN-END:initComponents

	private String sFileName;
	private Canvas3D myCanvas3D;
}
