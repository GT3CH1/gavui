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
1. Ensure `mavenCentral` is added as a repository.
2. In your project's gradle.properties file, add the following:

```gradle
gavui_version = <your-version>
```

3. In your project's build.gradle file, add the following:

```groovy
dependencies {
    // the rest of your dependencies...
    modImplementation "com.peasenet:gavui:${gavui_version}"
}
```

4. Sync your gradle project
5. Add the following line to your mod's initializer:

```java
@Override
public void onInitialize() {
    // Note that its best to put this before any other intialization that requires the UI
    GavUI.initialize();
    // your mod's initialization code...
}  
```

6. Enjoy!

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