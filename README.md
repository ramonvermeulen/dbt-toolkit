<div align="center">
    <img alt="logo" data-is-relative="true" src="./assets/img/logo.png" width="250" height="250"/>
    <h1>dbt-toolkit 🧰</h1>
    <img alt="GitHub Workflow Status" src="https://github.com/ramonvermeulen/dbt-toolkit/actions/workflows/build.yml/badge.svg?branch=main">
    <a href="https://codecov.io/github/ramonvermeulen/dbt-toolkit" ><img src="https://codecov.io/github/ramonvermeulen/dbt-toolkit/graph/badge.svg?token=E0ATR13F60"/></a>
    <a href="https://plugins.jetbrains.com/plugin/24666-dbttoolkit"><img alt="JetBrains Plugin Version" src="https://img.shields.io/jetbrains/plugin/v/24666-dbttoolkit"></a>
    <a href="https://plugins.jetbrains.com/plugin/24666-dbttoolkit"><img alt="JetBrains Plugin Downloads" src="https://img.shields.io/jetbrains/plugin/d/24666-dbttoolkit"></a>
    <a href="https://github.com/ramonvermeulen/dbt-toolkit"><img alt="GitHub Repo stars" src="https://img.shields.io/github/stars/ramonvermeulen/dbt-toolkit"></a>
</div>

<br>

![Lineage Example](./assets/img/sample.gif)

<!-- Plugin description -->
The dbt-toolkit is an early-stage plugin designed to enhance your experience working with [**dbt-core**](https://github.com/dbt-labs/dbt-core)
projects within JetBrains IDEs. It currently supports lineage rendering, dbt documentation, compiled SQL preview, and more!
Providing a more streamlined and integrated approach to managing your dbt projects in Jetbrains IDEs. The dbt-toolkit is inspired by the
well-known VSCode plugin [**vscode-dbt-power-user**](https://github.com/AltimateAI/vscode-dbt-power-user), and aims to become an alternative
for JetBrains IDEs.

> _Note: For larger dbt projects (e.g. hundreds of models) the performance might not be as good, I am looking for ways to improve the performance even
> for larger dbt projects._


## Features
* **Lineage Rendering**: Visualize the dependencies and relationships between your dbt models directly in your IDE. 
This feature helps you understand the impact of changes and the flow of data in your project. Besides that it offers a 
quicker way to navigate through your dbt project.
* **dbt Documentation**: Access your dbt documentation within your IDE, eliminating the need to constantly switch between
   your browser and IDE.
* **Preview compiled SQL**: Preview the compiled SQL of a dbt model quickly in your IDE, and recompile a single model on
  the fly.
* **Preview data**: Preview the output data of a dbt model directly in your IDE.

## Upcoming Features
*These are features I am planning to implement in the future, however this list might change overtime*
* **Full Jinja support in SQL files**: Highlight Jinja syntax in SQL files and navigate through files by clicking
through `{{ ref() }}` and `{{ source() }}` tags.
* **... More**: I am open to suggestions and feedback, so feel free to reach out to me with your ideas!


## Prerequisites
* For IntelliJ users, it is required to have the [**Python**](https://plugins.jetbrains.com/plugin/631-python) plugin installed so that you can configure a venv via:
<br>`File` > `Project Structure` > `SDK` > `Select Python` > `Select New Virtual Environment`
<br>![Set-up venv](./assets/img/settings.jpg)
* I would recommended to install [**dbt-core**](https://pypi.org/project/dbt-core/) and the required adapters within the configured venv.
* Supported/Tested dbt versions are: `>=1.7.0`

If there is a venv configured within the IDE, the plugin will automatically try to use the dbt executable from the venv. 
If there is no venv configured the plugin will use the globally installed dbt version.

## Installation
You can download dbt-toolkit from the JetBrains plugin marketplace. For more information on how to install JetBrains plugins, 
please refer to the [**official documentation**](https://www.jetbrains.com/help/idea/managing-plugins.html).

## Setting environment variables
If your dbt project requires environment variables (e.g. `DBT_SOME_ENV_VAR`), please set them via the plugin settings.
`File` > `Settings` > `dbtToolkit` > `Environment Variables`

There is also support for `.env` files, the plugin automatically checks for a `.env` file in the root of your project.
Via the plugin settings, you can also specify a custom path to the `.env` file.
> _Note: The global plugin environment variables will overwrite any environment variable with the same key in your 
> `.env` files_
<!-- Plugin description end -->

## Releases
The releases can be found under the repository it's [**releases section**](https://github.com/ramonvermeulen/dbt-toolkit/releases), 
the latest release in Github doesn't always reflect the latest release in the Jetbrains Marketplace. The reason for this is that the 
Jetbrains Marketplace needs a couple of days to review a release before publishing. If you really need to be on the latest release 
you can always download the zip file (e.g. `dbt-toolkit-<version>.zip` and in the IDE Plugin window choose for `Install from disk`.

## Feedback and Contributions
I highly appreciate any feedback and contributions as I am developing and improving dbt-toolkit. 
Feel free to report bugs, issues, or suggest features through the GitHub repository.

## Troubleshooting
Experiencing issues after upgrading the plugin? Try to delete your `.idea/dbtToolkitSettings.xml` file. If you're still experiencing 
problems, feel free to open a Github issue and I'll try to investigate and solve the issue in a future release.

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
[docs:plugin-description]: https://plugins.jetbrains.com/docs/intellij/plugin-user-experience.html#plugin-description-and-presentation
