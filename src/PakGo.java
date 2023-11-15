import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PakGo extends JFrame {

    private JTextField usernameField, passwordField, nameField, addressField, phoneNumberField, itemTypeField,
            weightField, costField, sourceCityField, destinationCityField;
    private JButton loginButton, submitButton;
    private JLabel nameLabel, addressLabel, phoneNumberLabel, itemTypeLabel, weightLabel, costLabel, sourceCityLabel,
            destinationCityLabel;
    private JPanel adminPanel, senderPanel;
    private JButton showTableButton;

    public PakGo() {

        // JFrame Configuration
        setTitle("Admin PakGo");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Admin Panel
        adminPanel = new JPanel();
        adminPanel.setLayout(new GridLayout(3, 2));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        adminPanel.add(new JLabel("Username:"));
        adminPanel.add(usernameField);
        adminPanel.add(new JLabel("Password:"));
        adminPanel.add(passwordField);
        adminPanel.add(new JLabel(""));
        adminPanel.add(loginButton);

        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("src/img/bg-01.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);

        // database
        class Koneksi {
            public static Connection connect() {
                String url = "jdbc:mysql://localhost:3306/pakgo"; // Ganti sesuai dengan URL MySQL Anda
                String user = "root"; // Ganti sesuai dengan nama pengguna MySQL Anda
                String password = ""; // Ganti sesuai dengan kata sandi MySQL Anda

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver"); // Perhatikan perubahan nama driver
                    Connection con = DriverManager.getConnection(url, user, password);
                    return con;
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        }

        // Sender Panel
        senderPanel = new JPanel();
        senderPanel.setLayout(new GridLayout(9, 2));
        senderPanel.setPreferredSize(new Dimension(600, 250));
        nameField = new JTextField(20);
        addressField = new JTextField(20);
        phoneNumberField = new JTextField(20);
        itemTypeField = new JTextField(20);
        weightField = new JTextField(20);
        costField = new JTextField(20);
        sourceCityField = new JTextField(20);
        destinationCityField = new JTextField(20);

        submitButton = new JButton("Submit");
        showTableButton = new JButton("Show Table");
        nameLabel = new JLabel("Nama:");
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        addressLabel = new JLabel("Alamat:");
        addressLabel.setForeground(Color.BLACK);
        addressLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        phoneNumberLabel = new JLabel("No_Hp:");
        phoneNumberLabel.setForeground(Color.BLACK);
        phoneNumberLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        itemTypeLabel = new JLabel("Jenis_barang:");
        itemTypeLabel.setForeground(Color.BLACK);
        itemTypeLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        weightLabel = new JLabel("Berat (kg):");
        weightLabel.setForeground(Color.BLACK);
        weightLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        costLabel = new JLabel("Biaya:");
        costLabel.setForeground(Color.BLACK);
        costLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        sourceCityLabel = new JLabel("Asal:");
        sourceCityLabel.setForeground(Color.BLACK);
        sourceCityLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        destinationCityLabel = new JLabel("Tujuan:");
        destinationCityLabel.setForeground(Color.BLACK);
        destinationCityLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
        showTableButton = new JButton("Show Table");

        // Empty label for spacing
        senderPanel.add(nameLabel);
        senderPanel.add(nameField);
        senderPanel.add(addressLabel);
        senderPanel.add(addressField);
        senderPanel.add(phoneNumberLabel);
        senderPanel.add(phoneNumberField);
        senderPanel.add(itemTypeLabel);
        senderPanel.add(itemTypeField);
        senderPanel.add(weightLabel);
        senderPanel.add(weightField);
        senderPanel.add(costLabel);
        senderPanel.add(costField);
        senderPanel.add(sourceCityLabel);
        senderPanel.add(sourceCityField);
        senderPanel.add(destinationCityLabel);
        senderPanel.add(destinationCityField);
        senderPanel.add(showTableButton);
        senderPanel.add(submitButton);

        senderPanel.setVisible(false);

        showTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    Connection con = Koneksi.connect();
                    if (con != null) {
                        String selectQuery = "SELECT name, address, phone_number, item_type, weight, cost, source_city, destination_city FROM pakgo_shipments";
                        PreparedStatement ps = con.prepareStatement(selectQuery);
                        ResultSet rs = ps.executeQuery();
                        DefaultTableModel tableModel = new DefaultTableModel(0, 8);
                        String[] columnNames = { "Name", "Address", "Phone Number", "Item Type", "Weight (kg)", "Cost",
                                "Source City", "Destination City" };
                        tableModel.setColumnIdentifiers(columnNames);

                        while (rs.next()) {
                            String name = rs.getString("name");
                            String address = rs.getString("address");
                            String phone = rs.getString("phone_number");
                            String itemType = rs.getString("item_type");
                            double weight = rs.getDouble("weight");
                            double cost = rs.getDouble("cost");
                            String sourceCity = rs.getString("source_city");
                            String destCity = rs.getString("destination_city");

                            tableModel.addRow(new Object[] { name, address, phone, itemType, weight, cost, sourceCity,
                                    destCity });
                        }

                        JTable dataTable = new JTable(tableModel);
                        JScrollPane scrollPane = new JScrollPane(dataTable);

                        // Create a new JFrame to display the table
                        JFrame tableFrame = new JFrame("PakGo Shipments");
                        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        tableFrame.setLayout(new BorderLayout());
                        tableFrame.add(scrollPane, BorderLayout.CENTER);
                        tableFrame.pack();
                        tableFrame.setVisible(true);

                        rs.close();
                        ps.close();
                        con.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Action Listener for Login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Perform admin login verification here
                if (username.equals("adminpakgo") && password.equals("1234")) {
                    adminPanel.setVisible(false);
                    senderPanel.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Try again.");
                }
            }
        });

        // Action Listener for Submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = nameField.getText();
                String alamat = addressField.getText();
                String no_Hp = phoneNumberField.getText();
                String jenis_barang = itemTypeField.getText();
                double berat = Double.parseDouble(weightField.getText());
                double biaya = Double.parseDouble(costField.getText());
                String asal = sourceCityField.getText();
                String tujuan = destinationCityField.getText();

                // Set up the backgroundLabel layout
                backgroundLabel.setLayout(new BorderLayout());
                backgroundLabel.add(adminPanel, BorderLayout.NORTH);
                backgroundLabel.add(senderPanel, BorderLayout.CENTER);

                // Do something with sender data here (e.g., store it)
                String result = "Shipping Details:\n";
                result += "Nama: " + nama + "\n";
                result += "Alamat: " + alamat + "\n";
                result += "No hp: " + no_Hp + "\n";
                result += "jenis barang: " + jenis_barang + "\n";
                result += "Berat: " + berat + " kg\n";
                result += "Biaya: " + biaya + "\n";
                result += "Asal: " + asal + "\n";
                result += "Tujuan: " + tujuan + "\n";

                JOptionPane.showMessageDialog(null, result);

                try {
                    Connection con = Koneksi.connect();
                    if (con != null) {
                        String insertQuery = "INSERT INTO pakgo_shipments (name, address, phone_number, item_type, weight, cost, source_city, destination_city) "
                                +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement ps = con.prepareStatement(insertQuery);
                        ps.setString(1, nama);
                        ps.setString(2, alamat);
                        ps.setString(3, no_Hp);
                        ps.setString(4, jenis_barang);
                        ps.setDouble(5, berat);
                        ps.setDouble(6, biaya);
                        ps.setString(7, asal);
                        ps.setString(8, tujuan);

                        int updateResult = ps.executeUpdate();
                        if (updateResult > 0) {
                            JOptionPane.showMessageDialog(null, "Shipment data saved successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to save shipment data.");
                        }

                        // Reset input fields
                        nameField.setText("");
                        addressField.setText("");
                        phoneNumberField.setText("");
                        itemTypeField.setText("");
                        weightField.setText("");
                        costField.setText("");
                        sourceCityField.setText("");
                        destinationCityField.setText("");

                        con.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Add panels to the JFrame
        getContentPane().setLayout(new CardLayout());
        getContentPane().add(adminPanel, "adminPanel");
        getContentPane().add(senderPanel, "senderPanel");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                PakGo mainGUI = new PakGo();
                mainGUI.setVisible(true);
            }
        });
    }
}
