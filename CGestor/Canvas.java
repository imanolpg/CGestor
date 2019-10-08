import javax.swing.JFrame;

public class Canvas extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Canvas(String title) {
		this.setTitle(title);
		this.setSize(1000, 600);
		this.setLocation(100, 50);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}
	
}
