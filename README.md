# dbtIdea

![Build](https://github.com/ramonvermeulen/dbt-idea/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID.svg)](https://plugins.jetbrains.com/plugin/PLUGIN_ID)

<!-- Plugin description -->
With dbtIdea it is easier to work with dbt projects in JetBrains IDEs. 
The plugin provides extra functionality to work with dbt projects in the IDE.
<!-- Plugin description end -->

### Known issues
* Plugin requires to have the dbt cli installed globally, I am looking for a way to support venv as well. 
Currently, the plugin uses background processes to execute dbt commands, as of now these processes are executed without a venv activated.

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
