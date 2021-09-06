package ui.dialogs;

import context.ApplicationContext;
import model.Car;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

public class CreateCarDialog extends JDialog {

    public CreateCarDialog() {
        setSize(400, 400);
        setTitle("Create car");
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.add(Box.createVerticalStrut(10));

        JTextField carModel = new JTextField("Model");
        carModel.setMaximumSize(new Dimension(100, 50));
        rootPanel.add(carModel);

        rootPanel.add(Box.createVerticalGlue());

        JButton confirm = new JButton("Confirm");
        confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        rootPanel.add(confirm);
        confirm.addActionListener(e -> {
            ApplicationContext.insertCar(new Car(carModel.getText()));
            dispose();
            new CreateUserDialog();
        });

        rootPanel.add(Box.createVerticalStrut(10));

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(rootPanel);
        setModal(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();

        setLocation(dimension.width / 2 - 200, dimension.height / 2 - 300);
        setVisible(true);
    }
}
