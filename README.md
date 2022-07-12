# GavUI

A Minecraft UI library that aims to bring ease of use to the modding community.

---

## Features

* Clickable elements
* Dragging elements
* Scrolling elements
* Toggle elements
* Cycling elements
* An extensive color palette

---

## How to use

1. Generate a GitHub token
2. Add the library repository to your build.gradle file:

```groovy
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/gt3ch1/gavui")
        credentials {
            username = project.findProperty("gpr.user") ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.key") ?: System.getenv("TOKEN")
        }
    }
}
```

3. Edit ~/.gradle/gradle.properties and add the following:

```
gpr.user = <your-username>
gpr.key = <your-token>
```

4. In your project's gradle.properties file, add the following:

```gradle
gavui_version = <your-version>
```

5. In your project's build.gradle file, add the following:

```groovy
dependencies {
    // the rest of your dependencies...
    modImplementation "com.peasenet:gavui:${gavui_version}"
}
```

6. Sync your gradle project
7. Add the following line to your mod's initializer:

```java
@Override
public void onInitialize() {
    // Note that its best to put this before any other intialization that requires the UI
    GavUI.initialize();
    // your mod's initialization code...
}  
```

8. Enjoy!

---

# Examples

If you want to see examples, please check out
the [Gavin's Mod GitHub repository](https://github.com/gt3ch1/minecraft-mod) for
some usage, more specifically
the [Gui Element](https://github.com/GT3CH1/minecraft-mod/blob/1.19-dev/src/main/java/com/peasenet/gui/GuiElement.java)
class for the project.

---

# License

This project is licensed under the [MIT license](LICENSE).

---

# Contributing

All contributions are welcome, but are under the discretion of the maintainers.