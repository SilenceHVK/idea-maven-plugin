package com.hvkcoder.plugin.ui;

import com.hvkcoder.plugin.presistentConfig.PersistentConfig;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.maven.project.MavenProjectsManager;

import javax.swing.*;
import java.awt.event.*;

public class CommitMessage extends JFrame {
    private JPanel contentPane;
    private JButton jbtnCommit;
    private JTextArea jtxtMessage;

    private PersistentConfig.State settings = PersistentConfig.getInstance().getState();

    public CommitMessage(@NotNull AnActionEvent event) {
        setSize(500,150);
        setResizable(false);
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(jbtnCommit);

        jtxtMessage.setLineWrap(true);
        jtxtMessage.setWrapStyleWord(true);

        jbtnCommit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCommit(event);
                frameHide();
            }
        });

        jtxtMessage.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (jtxtMessage.getText().length() > 100){
                    jtxtMessage.setText(jtxtMessage.getText().substring(0,100));
                }
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    private void onCommit(@NotNull AnActionEvent event) {

        MavenProjectsManager manager = MavenProjectsManager.getInstance(event.getProject());
//        System.out.println(settings.getHost());
    }

    private void frameHide(){
        this.setVisible(false);
    }
}
