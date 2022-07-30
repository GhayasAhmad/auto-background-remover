This is an android library for removing background from the image.

You have to give the bitmap of the image to this library and the library will return the bitmap with the removed background.

## Gradle
Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency

```
dependencies {
	        implementation 'com.github.GhayasAhmad:auto-background-remover:1.0.1'
	}
```

## Code:
```
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
