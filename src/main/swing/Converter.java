package swing;

import java.awt.Container;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Converter extends JFrame {
	final private Container container = getContentPane();
	
	final private JTextField textFieldMiles =  new JTextField();
	final private JTextField textFieldKilometers =  new JTextField();
	
	final private JPanel panel1 = new JPanel();
	final private JPanel panel2 = new JPanel();
	final private JButton btnConvertToKilometers = new JButton("-> Kms");
	final private JButton btnConvertToMiles = new JButton("-> Millas");

	final private JLabel lblMiles = new JLabel("Millas:");
	final private JLabel lblKilometers = new JLabel("Kilómetros:");

	/**
	 * Create the frame.
	 */
	public Converter() {
		createGUI();
		attachEvents();
	}
	private void createGUI() {
		super.setTitle("Conversor Millas <=> Kilómetros");

		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		panel1.setBorder(BorderFactory.createTitledBorder("Millas a Kilómetros"));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
		panel1.add(lblKilometers);
		panel1.add(textFieldMiles);
		panel1.add(btnConvertToKilometers);

		panel2.setBorder(BorderFactory.createTitledBorder("Kilómetros a Millas"));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.add(lblMiles);
		panel2.add(textFieldKilometers);
		panel2.add(btnConvertToMiles);

		container.add(panel1);
		container.add(panel2);
		setSize(350, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	private void attachEvents() {
		btnConvertToKilometers.addActionListener(e -> {
			double kms = 0;
			try {
				kms = Float.valueOf(textFieldMiles.getText()) * 1.609;
				textFieldKilometers.setText("" + kms);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Solo números");
			}
		});

		btnConvertToMiles.addActionListener(e -> {
			double kms = 0;
			try {
				kms = Float.valueOf(textFieldKilometers.getText()) / 1.609;
				textFieldMiles.setText("" + kms);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Solo números");
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Converter().setVisible(true));
	}
}
