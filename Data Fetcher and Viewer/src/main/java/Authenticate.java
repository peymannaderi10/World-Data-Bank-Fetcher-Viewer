import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Hashtable;

/***
 * Class that provides the the login user interface and authenticates the user credentianls for system access.
 * The user credentials (username and passwords) are stored and read from the Users file
 */
public class Authenticate implements ActionListener {
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel usernameLabel;
    private static JTextField usernameTextField;
    private static JLabel passwordLabel;
    private static JPasswordField passwordTextField;
    private static JButton login;
    private static JButton signup;
    private static JLabel success;
    private static Hashtable<String, String> credentials;

    /***
     *UI is built in this main method.
     */
    public static void main(String[] args) {
        // creating a HashTable Dictionary to keep track of all the passwords and usernames in the Users file.
        credentials = new Hashtable<>();
        try {
            // Reader block that reads the file and splits each line into the usernames and password. The string after the last whitespace on the line is the password.
            BufferedReader reader = new BufferedReader(new FileReader("Users"));
            String line = reader.readLine();
            while (line != null) {
                String Username, Password;
                int index = line.lastIndexOf(" ");
                Username = line.substring(0, index);
                Password = line.substring(index + 1, line.length());
                credentials.put(Username, Password);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Creating the Frame
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);

        //Creating the panel for adding components on the frame
        panel = new JPanel(); // the panel is not visible in output
        frame.add(panel);

        //Setting the layout as null to use default layout
        panel.setLayout(null);

        //Making a label for username and passwords with thier own textboxes
        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(10, 20, 80, 25);
        panel.add(usernameLabel);

        usernameTextField = new JTextField(20); // accepts upto 20 characters
        usernameTextField.setBounds(100, 20, 200, 25);
        panel.add(usernameTextField);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordTextField = new JPasswordField(20); // accepts upto 20 characters
        passwordTextField.setBounds(100, 50, 200, 25);
        panel.add(passwordTextField);

        //Adding the login and signup button
        login = new JButton("Login");
        login.setBounds(10, 80, 80, 25);
        login.addActionListener(new Authenticate());
        panel.add(login);

        signup = new JButton("Signup");
        signup.setBounds(100, 80, 80, 25);
        signup.addActionListener(new Authenticate());
        panel.add(signup);

        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        frame.setVisible(true);
    }

    /***
     * This method is designed to provide functionality to the Signup and Login buttons.
     * @param e Everytime login or signup is hit an action event takes place.
     * returns nothing. But the desired function (action) for the pressed button (action event) is preformed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Get the entered username and password from the user
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        //If login is pressed than validate user credentials.
        if (e.getSource() == login){
            String matcher = credentials.get(username);
            if (password.equals(matcher)) {
                MainUI.main(null);
                frame.setVisible(false);
                frame.dispose();
            }
            //If wrong credentials are entered than a message pops up and the program terminates
            else{
                JOptionPane.showMessageDialog(frame, "The entered credentials are wrong.");
                frame.setVisible(false);
                frame.dispose();
            }
        }

        //If signup is chosen than store the user entered credentials on file
        if(e.getSource() == signup){
            //Checks if user entered characters
            if (username.isBlank() || password.isBlank()){
                JOptionPane.showMessageDialog(frame, "Please enter a username and password.");
            }
            //If the user name exists a message pops up
            if (credentials.get(username) != null){
                JOptionPane.showMessageDialog(frame, "The entered Username already exists in the system.");
            }
            //If a new username is entered than add it to our existing Users
            if(credentials.get(username) == null && (!username.isBlank()) && (!password.isBlank())){
                credentials.put(username,password);
                BufferedWriter writer;
                try {
                    writer = new BufferedWriter(new FileWriter("Users", true));
                    writer.append("\n" + username + " " + password);
                    writer.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JOptionPane.showMessageDialog(frame, "New user added in to the system.");
            }
        }
    }
}
