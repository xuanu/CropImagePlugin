# CropImagePlugin
裁剪图片

> 基于[Android 高仿微信头像截取 打造不一样的自定义控件](http://blog.csdn.net/lmj623565791/article/details/39761281 )  的修改  

增加方法，可以设置裁剪框比例

使用方法：
1. 项目中添加   
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2. 模块中添加 
```
dependencies {
	        compile 'com.github.xuanu:CropImagePlugin:0.0.3'
	}
```  
3. 使用  
```
 startActivityForResult(new Intent(this, CropImageActivity.class).putExtra(CropImageActivity.Key_Key, filePath2).putExtra(CropImageActivity.SCALE_KEY, 1f), xxx);//一定要加f
//Key_Key,本地路径，必填
//scale比例，选填
```
