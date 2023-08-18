package inheritance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ClassA {

    public static void main(String arg[])
    {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);

            Statement statement;
            statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from customers");

            String code;
            String title;

            while (resultSet.next())
            {
                code = resultSet.getString("username");
                title = resultSet.getString("password").trim();
                System.out.println("username : " + code + " username : " + title);
            }

            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (Exception exception)
        {
            System.out.println(exception);
        }

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Centered UI Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for center alignment

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        JLabel nameLabel = new JLabel("Username:");
        JTextField nameField = new JTextField(20);

        JLabel emailLabel = new JLabel("Password:");
        JTextField emailField = new JTextField(20);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                JOptionPane.showMessageDialog(frame, "Username: " + name + "\nPassword: " + email);

                String query = "INSERT INTO customers (username, password) VALUES (?, ?)";
                try (Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/mydb", "root", ""
                );
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, email);

                    int rowsAffected = preparedStatement.executeUpdate();
                    System.out.println(rowsAffected + " rows inserted.");
                } catch (Exception a) {
                    a.printStackTrace();
                }

            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST; // Right-align label
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST; // Left-align input field
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(emailField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center-align button
        panel.add(submitButton, gbc);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
    }

    private void insertData(String username , String password){


    }
}
