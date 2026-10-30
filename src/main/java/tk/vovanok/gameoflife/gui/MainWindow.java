package tk.vovanok.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import tk.vovanok.gameoflife.core.FieldCalculator;

public class MainWindow extends JFrame {

    private FieldPanel gamePanel;
    private Timer timer;
    private SettingsWindow settingsGui;

    private final MouseAdapter fieldMouseHandler = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            gamePanel.reverseGameArrayValue(e.getX() / 10, e.getY() / 10);
            gamePanel.fCalc.calcCols();
            gamePanel.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            gamePanel.setEraser(false);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            gamePanel.updateCursorPosition(x, y);

            if (SwingUtilities.isLeftMouseButton(e)) {
                if (gamePanel.fCalc.isAlive(x / 10, y / 10)) {
                    return;
                }
                gamePanel.reverseGameArrayValue(x / 10, y / 10);
                gamePanel.fCalc.calcCols();
            } else {
                gamePanel.setEraser(true);
                gamePanel.fCalc.erase(x / 10, y / 10, 9);
            }
            gamePanel.repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            gamePanel.updateCursorPosition(e.getX(), e.getY());
            gamePanel.repaint();
        }
    };

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Can not load look and feel!");
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> new MainWindow().setVisible(true));
    }

    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        var mnFile = new JMenu("File");
        menuBar.add(mnFile);

        var mntmSettings = new JMenuItem("Settings");
        mntmSettings.addActionListener(e -> {
            settingsGui.setGamePanel(gamePanel);
            settingsGui.setVisible(true);
        });
        mnFile.add(mntmSettings);
        mnFile.add(new JMenuItem("Save"));
        mnFile.add(new JMenuItem("Restore"));

        var contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setResizable(false);

        gamePanel = new FieldPanel(100, 80);
        gamePanel.addMouseListener(fieldMouseHandler);
        gamePanel.addMouseMotionListener(fieldMouseHandler);
        contentPane.add(gamePanel, BorderLayout.CENTER);

        var controls = new JPanel();
        contentPane.add(controls, BorderLayout.NORTH);

        var btnNext = new JButton("Next");
        btnNext.addActionListener(e -> step());

        var btnRandom = new JButton("Random");
        btnRandom.addActionListener(e -> gamePanel.randomize());

        var btnClear = new JButton("Clear");
        btnClear.addActionListener(e -> gamePanel.clear());
        controls.add(btnClear);
        controls.add(btnRandom);
        controls.add(btnNext);

        var btnAuto = new JButton("Start!");
        btnAuto.addActionListener(e -> {
            if (timer.isRunning()) {
                timer.stop();
                btnAuto.setText("Start!");
            } else {
                timer.start();
                btnAuto.setText("Stop!");
            }
        });
        controls.add(btnAuto);

        var delaySpin = new JSpinner();
        delaySpin.addChangeListener(e -> timer.setDelay(((int) delaySpin.getValue()) * 25));
        delaySpin.setModel(new SpinnerNumberModel(12, 1, 50, 1));
        controls.add(delaySpin);

        var ruleSelector = new JComboBox<>(new DefaultComboBoxModel<>(
                new String[] {"Classic life", "Gnarl", "Maze", "Coagulations"}));
        ruleSelector.addActionListener(e -> {
            switch (ruleSelector.getSelectedIndex()) {
                case 0 -> gamePanel.setRule(FieldCalculator.RULE_CLASSIC);
                case 1 -> gamePanel.setRule(FieldCalculator.RULE_GNARL);
                case 2 -> gamePanel.setRule(FieldCalculator.RULE_MAZE);
                case 3 -> gamePanel.setRule(FieldCalculator.RULE_COAGULATIONS);
            }
        });
        ruleSelector.setToolTipText("Choose rule");
        controls.add(ruleSelector);
        pack();

        timer = new Timer(300, e -> step());

        settingsGui = new SettingsWindow();
        settingsGui.setModal(true);
    }

    private void step() {
        gamePanel.calculate();
        gamePanel.repaint();
    }
}
