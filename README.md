# idea-maven-plugin

> 该项目为 `IDEA` 插件开发练手项目，主要包含：

- [x] 调用 `IDEA` 集成插件 `Maven` 打包方法
- [x] `IDEA Settings` 界面
- [x] `Settings` 界面持久化存储
- [x] `FTP` 连接测试
- [x] `swing` 组件开发


调用 IDEA 提供的 Maven 插件方法，需将 `lib/maven.jar` 加入 `IDEA SDK` 集成

<img src="https://github.com/SilenceHVK/idea-maven-plugin/raw/master/images/idea-sdk.png" alt="IDEA-SDK">

```java
MavenRunner runner = MavenRunner.getInstance(project);
MavenRunnerSettings settings = runner.getState().clone();
settings.getMavenProperties().put("interactiveMode", "false");
settings.setSkipTests(true);
MavenRunnerParameters params = new MavenRunnerParameters();
params.setWorkingDirPath(project.getBasePath());
params.setGoals(Collections.singletonList("package"));
```


