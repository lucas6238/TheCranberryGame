package lucas.gem;

import com.badlogic.gdx.graphics.Texture;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Created by Chandler on 11/19/2017.
 */

public class Menu {
    static float x, y, width, height;
    boolean physical;
    Border bloackLine = BorderFactory.createLineBorder(Color.black);
    JFrame frame;
    JPanel jpan;
    JComboBox cBox;
    Editor editor;

    static JSpinner xSpin, ySpin, widthSpin, heightSpin;

    static JLabel label;
    static String selection = "badlogic.jpg";

    Menu(Editor ed) {
        makeFrame();
        editor = ed;
    }

    void makeFrame() {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        frame = new JFrame("The JFrame");
        frame.add(makePanel());
        frame.setSize(600, 900);
        frame.setVisible(true);
    }

    JPanel makePanel() {
        jpan = new JPanel(new BorderLayout());
        makeComboBox();
        makeImage();

        cBox.setPreferredSize(new Dimension(400, 20));
        label.setPreferredSize(new Dimension(400, 400));

        JPanel temp = new JPanel(new BorderLayout());
        temp.add(label, BorderLayout.SOUTH);
        temp.add(cBox, BorderLayout.NORTH);
        temp.setPreferredSize(new Dimension(400, 600));

        jpan.add(temp, BorderLayout.NORTH);
        jpan.add(spinners(), BorderLayout.SOUTH);
        jpan.setBorder(bloackLine);

        return jpan;
    }
    JMenuBar makeMenuBar(){
        JMenuBar menuBar=new JMenuBar();
        JMenu menu=new JMenu();
        JMenuItem menuItem=new JMenuItem();
        menuItem.addActionListener(new SaveAction(this));
        return menuBar;
    }

    JPanel spinners() {
        JPanel temp, north, south;

        xSpin = new JSpinner(new SpinnerNumberModel());
        widthSpin = new JSpinner(new SpinnerNumberModel());
        ySpin = new JSpinner(new SpinnerNumberModel());
        heightSpin = new JSpinner(new SpinnerNumberModel());

        xSpin.setPreferredSize(new Dimension(200, 20));
        ySpin.setPreferredSize(new Dimension(200, 20));
        widthSpin.setPreferredSize(new Dimension(200, 20));
        heightSpin.setPreferredSize(new Dimension(200, 20));

        xSpin.addChangeListener(new updateValues());
        ySpin.addChangeListener(new updateValues());
        widthSpin.addChangeListener(new updateValues());
        heightSpin.addChangeListener(new updateValues());

        north = new JPanel(new FlowLayout());
        north.add(new JLabel("X"));
        north.add(xSpin);
        north.add(new JLabel("Width"));
        north.add(widthSpin);

        north.setBorder(bloackLine);

        south = new JPanel(new FlowLayout());
        south.add(new JLabel("Y"));
        south.add(ySpin);
        south.add(new JLabel("Height"));
        south.add(heightSpin);

        south.setBorder(bloackLine);

        north.setPreferredSize(new Dimension(400, 40));
        south.setPreferredSize(new Dimension(400, 40));

        temp = new JPanel(new BorderLayout());
        temp.add(north, BorderLayout.NORTH);
        temp.add(south, BorderLayout.SOUTH);
        return temp;
    }

    JComboBox makeComboBox() {
        File file = new File(".");
        File[] fileList = file.listFiles();
        Stack<Integer> ints = new Stack<Integer>();
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].toString().endsWith(".png")) {
                ints.push(i);
            }
            System.out.println();
        }
        File[] pngList = new File[ints.size()];
        int n = ints.size();
        for (int i = 0; i < n; i++) {
            pngList[i] = fileList[ints.pop()];
        }
        cBox = new JComboBox(pngList);
        cBox.setMaximumSize(new Dimension(300, 20));
        cBox.addActionListener(new action(cBox));
        cBox.setBorder(bloackLine);
        return cBox;
    }
    JLabel makeImage() {
        ImageIcon icon = new ImageIcon(selection);
        label = new JLabel(icon);
        label.setVisible(true);
        label.setPreferredSize(new Dimension(200, 200));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(bloackLine);
        return label;
    }
    void resetValues(){
        BufferedImage tex=null;
        try {
            tex = ImageIO.read(new File(cBox.getSelectedItem().toString()));
            Menu.setWidth(tex.getWidth()*3);
            Menu.setHeight(tex.getHeight()*3);
            Menu.updateValues();
        }catch(IOException e){
            System.out.println("Texture not found");
        }
    }
    static void updateValues() {
        //Converting jspinner's value to strings, and strings to floats was the only way I could get numbers out of them
        width = Float.valueOf(widthSpin.getValue().toString());
        height = Float.valueOf(heightSpin.getValue().toString());
        x=Float.valueOf(xSpin.getValue().toString());
        y=Float.valueOf(ySpin.getValue().toString());
    }
    void save(){
        editor.save();
    }
    static void setWidth(float w) {
        widthSpin.setValue(w);
    }
    static void setHeight(float h) {
        heightSpin.setValue(h);
    }
    static void setX(float value) {
        xSpin.setValue(value);
    }
    static void setY(float value) {
        ySpin.setValue(value);
    }
    float getWidth(){
        return width;
    }
    float getHeight(){
        return height;
    }
    float getX(){
        return x;
    }
    float getY(){
        return y;
    }
    String getTexture(){
        return Menu.selection;
    }
    void printValues(){
        System.out.println(x+" "+y+" "+height+" "+width);
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
        System.out.println(Menu.selection);
        ImageIcon icon=new ImageIcon(Menu.selection);
        Menu.label.setSize(600,600);
        Menu.label.setIcon(icon);
        BufferedImage tex=null;
    }
}
class updateValues implements ChangeListener{
    public updateValues(){
    }
    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        Menu.updateValues();
    }
}
class SaveAction implements ActionListener{
    Menu menu;
    SaveAction(Menu m){
        menu=m;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        menu.save();
    }
}