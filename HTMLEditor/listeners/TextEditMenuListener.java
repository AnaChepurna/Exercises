package com.javarush.task.task32.task3209.listeners;

import com.javarush.task.task32.task3209.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

/**
 * Created by Ana on 08.07.2017.
 */
public class TextEditMenuListener implements MenuListener {
    private View view;

    public TextEditMenuListener(View view){
        this.view = view;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        JMenu jMenu = (JMenu) e.getSource();
        for (Component d : jMenu.getMenuComponents()){
            d.setEnabled(view.isHtmlTabSelected());
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}