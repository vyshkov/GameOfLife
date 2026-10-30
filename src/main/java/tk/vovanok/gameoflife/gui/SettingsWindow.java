package tk.vovanok.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.UIManager;

public class SettingsWindow extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private FieldPanel gamePanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            SettingsWindow dialog = new SettingsWindow();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public SettingsWindow() {
        setBounds(100, 100, 262, 234);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(5, 1, 0, 0));
        {
            JPanel panel = new JPanel();
            contentPanel.add(panel);
        }
        {
            JPanel panel = new JPanel();
            panel.setBackground(UIManager.getColor("Button.light"));
            contentPanel.add(panel);
            {
                JLabel lblSize = new JLabel("Size:");
                panel.add(lblSize);
            }
            {
                JSpinner spinner = new JSpinner();
                panel.add(spinner);
                
            }
            {
                JSpinner spinner = new JSpinner();
                panel.add(spinner);
            }
        }

        {
            JPanel panel = new JPanel();
            panel.setBackground(UIManager.getColor("Button.background"));
            contentPanel.add(panel);
            {
                JLabel lblDencity = new JLabel("Dencity:");
                panel.add(lblDencity);
            }
            {
                JSpinner spinner = new JSpinner();
                panel.add(spinner);
            }
        }

        {
            JPanel panel = new JPanel();
            panel.setBackground(UIManager.getColor("Button.light"));
            contentPanel.add(panel);
            {
                JLabel lblColorOfCells = new JLabel("Color of cells:");
                panel.add(lblColorOfCells);
            }
            {
                JComboBox<String> comboBox = new JComboBox<>();
                panel.add(comboBox);
            }
        }
        {
            JPanel panel = new JPanel();
            contentPanel.add(panel);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }

    void setGamePanel(FieldPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

}
