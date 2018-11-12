# Android LinearLayout

## 1 常用属性

![1533642381730](assets\1533642381730.png)

## 2 运行过程

当启动程序后会调用MainActivity.java文件，为什么会调用这个文件？因为在mainfest文件中对这个文件进行注册了。对于mainfest的作用如下：

- 每一个activity都要在mainfest文件中进行声明，例如：注册一个activity，名字叫作MainActivity，并把这个activity设置为启动的activity。

  ```
  <activity android:name=".MainActivity">
      <intent-filter>   
          <action android:name="android.intent.action.MAIN" />
  
          <category android:name="android.intent.category.LAUNCHER" />
      </intent-filter>
  </activity>
  ```

## 3 线性布局 

对于线性布局，在创建完项目后需要在activity_main.xml中进行修改，如图所示，选中如下文件。

![1533643679007](assets\1533643679007.png)

在文件中设置成线性布局，实现的代码如下：实现了两层LinearLayout嵌套

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.datah.linearlayout.MainActivity">

    <LinearLayout
        android:id="@+id/l1_1"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:background="@color/colorPrimaryDark">
    </LinearLayout>
</LinearLayout>
```

- 参数说明：match_parent表示匹配父控件，意思是占满父控件

  