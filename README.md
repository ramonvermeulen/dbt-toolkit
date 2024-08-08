<div align="center">
    <img alt="logo" data-is-relative="true" src="./assets/img/logo.png" width="250" height="250"/>
    <h1>dbtToolkit 🧰</h1>
    <img alt="GitHub Workflow Status" src="https://github.com/ramonvermeulen/dbt-toolkit/actions/workflows/build.yml/badge.svg?branch=main">
    <a href="https://plugins.jetbrains.com/plugin/24666-dbttoolkit"><img alt="JetBrains Plugin Version" src="https://img.shields.io/jetbrains/plugin/v/24666-dbttoolkit"></a>
    <a href="https://plugins.jetbrains.com/plugin/24666-dbttoolkit"><img alt="JetBrains Plugin Downloads" src="https://img.shields.io/jetbrains/plugin/d/24666-dbttoolkit"></a>
    <a href="https://github.com/ramonvermeulen/dbt-toolkit"><img alt="GitHub Repo stars" src="https://img.shields.io/github/stars/ramonvermeulen/dbt-toolkit"></a>
</div>

<br>

![Lineage Example](./assets/img/sample.gif)

<!-- Plugin description -->
The dbtToolkit is an early-stage plugin designed to enhance your experience working with [**dbt-core**](https://github.com/dbt-labs/dbt-core)
projects within JetBrains IDEs. It currently supports lineage rendering and dbt documentation, 
providing a more streamlined and integrated approach to managing your dbt projects. The dbtToolkit is inspired by the
well-known VSCode plugin [**vscode-dbt-power-user**](https://github.com/AltimateAI/vscode-dbt-power-user), and aims to become an alternative
for JetBrains IDEs.


## Features
* **Lineage Rendering**: Visualize the dependencies and relationships between your dbt models directly in your IDE. 
This feature helps you understand the impact of changes and the flow of data in your project. Besides that it offers a 
quicker way to navigate through your dbt project.
* **dbt Documentation**: Access your dbt documentation within your IDE, eliminating the need to switch between different 
tools and platforms. This feature supports a more efficient workflow and keeps essential information at your fingertips.
* **Preview compiled SQL**: Preview the compiled SQL of a dbt model quickly in your IDE, and recompile a single model on
  the fly.

## Upcoming Features
*These are features I am planning to implement in the future, however this list might change overtime*
* **Full Jinja support in SQL files**: Highlight Jinja syntax in SQL files and navigate through files by clicking
through `{{ ref() }}` and `{{ source() }}` tags.
* **Preview data**: Preview the output data of a dbt model directly in your IDE.
* **... More**: I am open to suggestions and feedback, so feel free to reach out to me with your ideas!

<!-- Plugin description end -->
## Prerequisites
* For IntelliJ users, I recommend to have the [**Python**](https://plugins.jetbrains.com/plugin/631-python) plugin installed so that you can configure a venv.
* I also recommend to have a venv configured and [**dbt-core**](https://pypi.org/project/dbt-core/) installed within the venv: <br>
`File` > `Project Structure` > `SDK` > `Select Python` > `Select New Virtual Environment`

If there is a venv configured within the IDE, the plugin will automatically try to use the dbt executable from the venv. 
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
