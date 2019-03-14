package com.hvkcoder.plugin.ui;

import com.hvkcoder.plugin.presistentConfig.PersistentConfig;
import com.hvkcoder.plugin.utils.FTPUtil;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PluginSettings implements Configurable,Configurable.Composite {
    private JPanel jMain;
    private JButton jbtnConnectionTest;
    private JTextField jtxtHost;
    private JTextField jtxtPort;
    private JTextField jtxtUserName;
    private JPasswordField jtxtPwd;

    private boolean flag = false;

    private PersistentConfig.State settings = PersistentConfig.getInstance().getState();

    private FTPUtil ftpUtil = new FTPUtil();

    public PluginSettings(){
        // 按钮 FTP 连接测试事件
        this.jbtnConnectionTest.addActionListener(e -> {
            flag = ftpUtil.connectServer(jtxtHost.getText(), Integer.parseInt(jtxtPort.getText()), jtxtUserName.getText(), new String(jtxtPwd.getPassword()),"UTF8");
            ftpUtil.closeClient();
            if (flag ){
                Messages.showMessageDialog("FTP 连接成功","提示",Messages.getInformationIcon());
            }else{
                Messages.showMessageDialog("连接 FTP 失败，请检查","提示",Messages.getErrorIcon());
            }
        });
        // 设置端口号类型为数字
        this.jtxtPort.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                jtxtPort.setText(jtxtPort.getText().replaceAll("\\D",""));
            }
        });
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Hvkcoder-Plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return this.jMain;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        if (flag){
            settings.setHost(jtxtHost.getText());
            settings.setPort(jtxtPort.getText());
            settings.setUserName(jtxtUserName.getText());
            settings.setPassword(jtxtPwd.getPassword());
        }
    }

    @NotNull
    @Override
    public Configurable[] getConfigurables() {
        jtxtHost.setText(settings.getHost());
        jtxtUserName.setText(settings.getUserName());
        if (settings.getPort() == null || settings.getPort() == ""){
            jtxtPort.setText("21");
        }else{
            jtxtPort.setText(settings.getPort());
        }
        if (settings.getPassword() != null){
            jtxtPwd.setText(String.valueOf(settings.getPassword()));
        }
        return new Configurable[0];
    }
}
