# Background Remover

This is an android library for removing background from the image.You have to give the bitmap of the image to this library and the library will return the bitmap with the removed background.

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
	 implementation 'com.github.GhayasAhmad:auto-background-remover:1.0.3'
}
```

## Code:
### Default
Default method for simply removing the bacground from the subject from bitmap without trimming the bitmap.

```kotlin
BackgroundRemover.bitmapForProcessing(
	bitmap,
	object: OnBackgroundChangeListener{
		override fun onSuccess(bitmap: Bitmap) {
			//do what ever you want to do with this bitmap
		}

		override fun onFailed(exception: Exception) {
			//exception
		}
	}
)

```
<img src="https://user-images.githubusercontent.com/65961727/189538271-6e4658f5-cc08-45c9-a876-e13a54c2140f.gif" width="250px"/>
</br>

### Trim Image
Remove the empty part of the image from bitmap. `true` for removing empty part and `false`for not and by default it is `false`.

```kotlin
BackgroundRemover.bitmapForProcessing(
	bitmap,
	true,
	object: OnBackgroundChangeListener{
		override fun onSuccess(bitmap: Bitmap) {
			//do what ever you want to do with this bitmap
		}

		override fun onFailed(exception: Exception) {
			//exception
		}
	}
)

```

<img src="https://user-images.githubusercontent.com/65961727/189539901-fd0270df-a63f-41df-a810-598805301661.gif" width="250px"/>

# 👨 Made By

`Ghayas Ahmad`

**Connect with me on**
</br>

[![Github](https://img.shields.io/badge/-Github-000?style=flat&logo=Github&logoColor=white)](https://github.com/GhayasAhmad)
[![Linkedin](https://img.shields.io/badge/-LinkedIn-blue?style=flat&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/ghayasahmad47/)
[![Gmail](https://img.shields.io/badge/-Gmail-c14438?style=flat&logo=Gmail&logoColor=white)](mailto:sheikhghayas47@gmail.com)
[![Instagram](https://img.shields.io/badge/Instagram-%23E4405F.svg?style=flat&logo=Instagram&logoColor=white)](https://www.instagram.com/gcodes._/)

</br>
<a href="https://www.buymeacoffee.com/ghayas" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>



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
