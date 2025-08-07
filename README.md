<h1 align="center">Android Background Remover Library</h1>

# ✨ Android Background Remover Library – Fast, Offline AI Background Removal

**Android Background Remover** is a high-performance library for automatically removing backgrounds from images in Android apps.  
Powered by advanced **AI segmentation models**, it delivers accurate, fast, and offline background removal with just a few lines of code.

---

## 🚀 Features

- ✅ **One-Line Background Removal** – Just pass a `Bitmap`, and get a clean, background-free image.
- 🎯 **High Accuracy & Sharp Edges** – AI-powered mask generation preserves object detail with minimal artifacts.
- ⚡ **Fast & Lightweight** – Optimized for real-time performance on Android devices.
- 🔌 **No External API Required** – Works 100% offline — no server or cloud dependency.
- 📦 **Easy to Integrate** – Plug-and-play with a clean, developer-friendly API.
- 🖼️ **Supports Multiple Image Formats** – Compatible with PNG, JPEG, WebP, and more.
- 🪄 **Optional Transparent Cropping** – Trim empty areas after background removal for cleaner results.
- 📱 **Ideal for E-Commerce, Social Media & Photo Editing Apps**

---

## 💡 Use Cases

- 🛍️ Product image cleanup for **e-commerce apps**
- 📸 Profile picture editors for **social and dating apps**
- 🎨 Background removal in **photo editing and design tools**
- 🤖 Preprocessing for **machine learning or computer vision**

---

## Gradle

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency

```groovy
dependencies {
    implementation 'com.github.GhayasAhmad:auto-background-remover:1.0.7'
}
```

## Code:

### Default

Default method for simply removing the bacground from the subject from bitmap without trimming the
bitmap.

```kotlin
lifecycleScope.launch {
    val resultBitmap = bitmap.removeBackground(
        context = applicationContext
    )
}

```


</br>

### Trim Image

Remove the empty part of the image from bitmap. `true` for removing empty part and `false`for not
and by default it is `false`.

```kotlin
lifecycleScope.launch {
    val resultBitmap = bitmap.removeBackground(
        context = applicationContext,
        trimEmptyPart = true,
    )
}

```
</br>

| With Trim Image | Without Trim Image |
| :---: | :---: |
| ![With Trim Image](https://user-images.githubusercontent.com/65961727/189539901-fd0270df-a63f-41df-a810-598805301661.gif) | ![Without Trim Image](https://user-images.githubusercontent.com/65961727/189538271-6e4658f5-cc08-45c9-a876-e13a54c2140f.gif) |

</br>

> [!important]
> This repository is primarily focused on ensuring the app size remains minimal and does not require the integration of additional Python libraries. For more accurate results or if you prefer solutions involving Python libraries, feel free to contact me.


## 🚀 Let’s Work Together

I’m actively open to freelance opportunities, collaborations, and contract-based Android or Compose
Multiplatform app development projects.

- 📩 **Hire Me for Your Next Project**: Whether you’re a startup, business, or individual looking to
  build a mobile app with modern technologies like Jetpack Compose, Kotlin Multiplatform, or Android
  native, I’m available for consulting and development work.
- 🤝 **Business Inquiries & Partnerships**: If you’re interested in technical collaboration,
  white-label development, or building innovative digital products together, feel free to reach out.
- 💼 **Available for Remote Work, Short-Term Contracts, or Long-Term Engagements**

---


**Connect with me on**
</br>

[![Github](https://img.shields.io/badge/-Github-000?style=flat&logo=Github&logoColor=white)](https://github.com/GhayasAhmad)
[![Linkedin](https://img.shields.io/badge/-LinkedIn-blue?style=flat&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/ghayasahmad47/)
[![Gmail](https://img.shields.io/badge/-Gmail-c14438?style=flat&logo=Gmail&logoColor=white)](mailto:sheikhghayas47@gmail.com)
[![Instagram](https://img.shields.io/badge/Instagram-%23E4405F.svg?style=flat&logo=Instagram&logoColor=white)](https://www.instagram.com/gcodes._/)

</br>

# 📜 License

```
MIT License

Copyright (c) 2022 Ghayas Ahmad

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
