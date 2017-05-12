package passwordgenerator;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PasswordGenerator extends JFrame
{
    Panel panel = new Panel();
    
    public PasswordGenerator()
    {       
        panel.setVisible(true);
        addComponents();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        add(panel);

        pack();
        
    }
    public static void main(String[] args)
    {
        PasswordGenerator frame = new PasswordGenerator();
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

    }
    public void addComponents()
    {
        JTextField entry = new JTextField();
        entry.setColumns(20);
        entry.setText("Password length: ");
        entry.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                entry.setText("");
            }
        });
        
        JTextField entry2 = new JTextField();
        entry2.setColumns(22);
        entry2.setText("Password name: ");
        entry2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                entry2.setText("");
            }
        });

        JTextArea textArea = new JTextArea();
        textArea.setColumns(40);
        textArea.setLineWrap(true);
        textArea.setRows(3);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        
        JButton save = new JButton("save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  
            {
                savePsswd(textArea.getText(), entry2.getText());
            }
        });
        
        JButton generate = new JButton("generate");
        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)  
            {
                textArea.setText(generatePsswd(entry.getText()));
            }
        });
        
        
        panel.add(entry);
        panel.add(generate);
        panel.add(textArea);
        panel.add(entry2);
        panel.add(save);

    }
    
    public String generatePsswd(String size)
    {
        int ssize;
        
        try 
        {
            ssize = Integer.valueOf(size);

        } catch (NumberFormatException nfe) 
            {
               ssize =0;
            }

        
        if(ssize >= 8)
        {
            boolean hasLarge = false;
            boolean hasNumber = false;
            boolean hasSpecial = false;
            boolean hasSmall = false;
            boolean allCharacters = false;

            String psswd = "";
            String[][] characters = 
            {
                {"a","b","c","d","e","f","g","i","j","k","m","n","o","p","q","r","s","t","w","x","y","z"},
                {"A","B","C","D","E","F","G","H","J","K","L","M","N","P","Q","R","S","T","W","X","Y","Z"},
                {"0","1","2","3","4","5","6","7","8","9"},
                {"*","$","-","+","?","_","&","=","!","%","{","}","/"}};


            int group,character;
            Random rand = new Random();

            while(!allCharacters)
            {
                psswd = "";
                hasSmall = hasLarge = hasSpecial = hasNumber = false;

                for(int i = 0; i < ssize; i++)
                {
                    group = rand.nextInt(4);
                    if(group == 0){hasSmall = true;}
                    if(group == 1){hasLarge = true;}
                    if(group == 2){hasNumber = true;}
                    if(group == 3){hasSpecial = true;}

                    character = rand.nextInt(characters[group].length);
                    psswd += characters[group][character];

                }

                if(hasSmall && hasLarge && hasNumber && hasSpecial)
                {
                    allCharacters = true;
                }
            }            

            //System.out.println(psswd);
            return psswd;
        }
        else
            return "Password should be at least 8 characters long and it's length should be an integer.";
    }
    
 
    
    public void savePsswd(String psswd, String name)
    {
        try {
            FileWriter writer = new FileWriter("passwords.txt", true);
            writer.append(name + ": " + psswd + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            System.out.println("asd");
        }
    }
}
