package vzap.phoenix.testing;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TestingEmployeeSkills extends JPanel
{
	private JTabbedPane tabbedPane;
	private JPanel profilePanel;
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public TestingEmployeeSkills()
	{
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		profilePanel = new JPanel();
		tabbedPane.addTab("New tab", null, profilePanel, null);
		
		panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);

	}

}
