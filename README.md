This is an android library for removing background from the image.

You have to give the bitmap of the image to this library and the library will return the bitmap with the removed background.

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
	 implementation 'com.github.GhayasAhmad:auto-background-remover:1.0.1'
}
```

## Code:
```kotlin
BackgroundRemover.bitmapForProcessing(bitmap, object: OnBackgroundChangeListener{
	override fun onSuccess(bitmap: Bitmap) {
		//do what ever you want to do with this bitmap
	}

	override fun onFailed(exception: Exception) {
		//exception
	}
})

```

# 👨 Made By

`Ghayas Ahmad`

**Connect with me on**
</br>

[![Github](https://img.shields.io/badge/-Github-000?style=flat&logo=Github&logoColor=white)](https://github.com/GhayasAhmad)
[![Linkedin](https://img.shields.io/badge/-LinkedIn-blue?style=flat&logo=Linkedin&logoColor=white)](https://www.linkedin.com/in/ghayasahmad47/)
[![Gmail](https://img.shields.io/badge/-Gmail-c14438?style=flat&logo=Gmail&logoColor=white)](mailto:sheikhghayas47@gmail.com)


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
