package lucas.gem;

import com.badlogic.gdx.graphics.Texture;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Created by Chandler on 11/19/2017.
 */

public class Menu {
    static float x,y,width,height;
    boolean physical;


    JFrame frame;
    JPanel jpan;
    JComboBox cBox;
    static JLabel label;
    static String selection="badlogic.jpg";
    Menu(){
        makeFrame();
    }
    void makeFrame(){
        x=0;
        y=0;
        frame=new JFrame("The Titties");
        frame.add(makePanel());
        frame.setSize(300,300);
        frame.setVisible(true);
    }
    JPanel makePanel(){
        jpan=new JPanel(new FlowLayout(FlowLayout.LEFT));
        jpan.add(makeComboBox());
        jpan.add(makeImage());

        return jpan;
    }
    JComboBox makeComboBox(){
        File file=new File(".");
        File[] fileList=file.listFiles();
        cBox=new JComboBox(fileList);
        cBox.setMaximumSize(new Dimension(300,20));
        cBox.addActionListener(new action(cBox));
        return cBox;
    }
    JLabel makeImage(){
        ImageIcon icon=new ImageIcon(selection);
        label=new JLabel(icon);
        label.setVisible(true);
        label.setSize(200,200);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    static void setWidth(float w){
        System.out.println(w+"Spopy");
        width=w;
    }
    static void setHeight(float h){
        height=h;
    }
    float getWidth(){
        return width;
    }
    float getHeight(){
        return height;
    }
    String getTexture(){
        return cBox.getSelectedItem().toString();
    }
}
class action implements ActionListener{
    JComboBox cBox;
    action(JComboBox box){
        cBox=box;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {



        Menu.selection=cBox.getSelectedItem().toString();
        ImageIcon icon=new ImageIcon(Menu.selection);
        Menu.label.setIcon(icon);
        BufferedImage tex=null;
        try {
            tex = ImageIO.read(new File(cBox.getSelectedItem().toString()));
        }catch(IOException e){

        }
        Menu.setWidth(tex.getWidth());
        Menu.setHeight(tex.getHeight());
    }
}
