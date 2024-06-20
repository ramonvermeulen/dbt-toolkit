<div align="center">
    <img alt="logo" data-is-relative="true" src="./assets/img/logo.png" width="250" height="250"/>
    <h1>dbtToolkit</h1>
    <img alt="GitHub Workflow Status" src="https://github.com/ramonvermeulen/dbt-idea/workflows/Build/badge.svg">
    <a href="https://plugins.jetbrains.com/plugin/PLUGIN_ID"><img alt="JetBrains Plugin Version" src="https://img.shields.io/jetbrains/plugin/v/PLUGIN_ID"></a>
    <a href="https://plugins.jetbrains.com/plugin/PLUGIN_ID"><img alt="JetBrains Plugin Downloads" src="https://img.shields.io/jetbrains/plugin/d/PLUGIN_ID"></a>
    <img alt="GitHub Repo stars" src="https://img.shields.io/github/stars/ramonvermeulen/dbt-toolkit">
</div>

<br>
<!-- Plugin description -->
The dbtToolkit is an early-stage plugin designed to enhance your experience working with dbt 
projects within JetBrains IDEs. It currently supports lineage rendering and dbt documentation, 
providing a more streamlined and integrated approach to managing your dbt projects.
<!-- Plugin description end -->
<br>

<div align="center">
    <img alt="sample" src="./assets/img/sample.gif"/>
</div>

## Features
* **Lineage Rendering**: Visualize the dependencies and relationships between your dbt models directly in your IDE. 
This feature helps you understand the impact of changes and the flow of data in your project.  
* **dbt Documentation**: Access your dbt documentation within your IDE, eliminating the need to switch between different 
tools and platforms. This feature supports a more efficient workflow and keeps essential information at your fingertips.

As an early version, I am actively working on expanding the functionalities of dbtToolkit. Stay tuned for more features 
that will further enhance your dbt project development experience in JetBrains IDEs.

## Prerequisites
* For IntelliJ users, I recommend to have the [**Python**](https://plugins.jetbrains.com/plugin/631-python) plugin installed
* I also recommend to have a venv configured and [**dbt-core**](https://pypi.org/project/dbt-core/) installed within the venv <br> 
*(File > Project Structure > SDK > Select Python > Select New Virtual Environment)*

If there is a venv configured, the plugin will automatically try to use the dbt executable from the venv. 
If there is no venv configured the plugin will use the globally installed dbt version.

## Installation
You can download dbtToolkit from the JetBrains plugin marketplace. For more information on how to install JetBrains plugins, 
please refer to the [**official documentation**](https://www.jetbrains.com/help/idea/managing-plugins.html).

## Feedback and Contributions
I highly appreciate any feedback and contributions as I am developing and improving dbtToolkit. 
Feel free to report bugs, issues, or suggest features through the GitHub repository.


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
