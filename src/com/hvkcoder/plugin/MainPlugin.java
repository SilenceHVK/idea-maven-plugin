package com.hvkcoder.plugin;

import com.hvkcoder.plugin.presistentConfig.PersistentConfig;
import com.hvkcoder.plugin.ui.CommitMessage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.maven.execution.MavenRunner;
import org.jetbrains.idea.maven.execution.MavenRunnerParameters;
import org.jetbrains.idea.maven.execution.MavenRunnerSettings;

import java.util.Collections;

public class MainPlugin extends AnAction {

    private PersistentConfig.State pluginSettings = PersistentConfig.getInstance().getState();

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // 获取操作项目对象
        Project project = e.getProject();

        // 判断是否设置 FTP
        if (pluginSettings.getHost() != null){
            // 调用 Maven 打包
            MavenRunner runner = MavenRunner.getInstance(project);
            MavenRunnerSettings settings = runner.getState().clone();
            settings.getMavenProperties().put("interactiveMode", "false");
            settings.setSkipTests(true);
            MavenRunnerParameters params = new MavenRunnerParameters();
            params.setWorkingDirPath(project.getBasePath());
            params.setGoals(Collections.singletonList("package"));

            runner.run(params, settings, () -> {
                // 打开备注界面
                CommitMessage dialog = new CommitMessage(e);
                dialog.setVisible(true);
            });

        }else{
            Messages.showMessageDialog("请在设置中，设置 FTP 连接","提示",Messages.getWarningIcon());
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        boolean isShow = false;
        //在Action显示之前,根据选中文件扩展名判定是否显示此Action
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
        Module module = DataKeys.MODULE.getData(e.getDataContext());
        if(null != file.getName()&& null != module && file.getName().equals(module.getName())) {
            isShow = true;
        }
        e.getPresentation().setEnabledAndVisible(isShow);
    }
}
