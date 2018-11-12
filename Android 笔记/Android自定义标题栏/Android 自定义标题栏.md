# Android 自定义标题栏

## 1 创建标题布局文件

首先创建一个标题文件，设置标题显示的样式等，在这里创建了一个title_layout.xml的布局文件，实现的代码如下：

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/colorPrimary"
    android:orientation="horizontal">

    <Button
        android:id="@+id/title_back"
        android:layout_width="65dp"
        android:layout_height="50dp"
        android:layout_gravity="center"
        android:layout_margin="5dp"
        android:text="返回"
        android:textSize="18dp" />

    <TextView
        android:id="@+id/title_text"
        android:layout_width="147dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_weight="1"
        android:gravity="center"
        android:text="Title"
        android:textColor="#fff"
        android:textSize="24dp" />

    <Button
        android:id="@+id/title_edit"
        android:layout_width="62dp"
        android:layout_height="50dp"
        android:layout_gravity="center"
        android:layout_margin="5dp"
        android:text="设置" />
</LinearLayout>
```

## 2 创建标题操作类

创建这个类主要是为了实现重复编写布局代码问题，在这个文件中也可以添加一些响应事件。在这里新建了一个TiitleLayou继承自LinearLayout，让他车呢各位我们自定义的标题栏，实现的代码如下：

```
public class TitleLayout  extends LinearLayout {

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_layout, this);
    }

}
```

 首先我们重写了LinearLayout中的带有两个参数的构造函数，在布局中引入TitleLayout控件就会调用这个构造函数。然后在构造函数中需要对标题栏布局进行动态加载，这就需要借助LayoutInflater实现。通过LayoutInflater的from()方法可以构建出一个LayoutInflater对象，然后调用inflate()方法就可以动态加载一个布局文件。inflate()方法接受两个参数，第一个参数是要加载的布局文件的id，第二个参数是给加载好的布局再添加一个父布局。

## 3 添加控件到activity_main

完成以上的操作后需要把布局文件添加到activity_main.xml中，在activity_main.xml中实现的代码如下：

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.example.raindi.test.TitleLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </com.example.raindi.test.TitleLayout>
</LinearLayout>
```

- 注意这里只对activity_main.xml中添加了：

    <com.example.raindi.test.TitleLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </com.example.raindi.test.TitleLayout>
到这里就完成了标题栏的自定义实现。但是只是显示效果，并没有能够监听事件的处理。实现的效果如下图所示：

![1536046622018](image\1536046622018.png)

## 4 添加按钮点击监听 

上面的步骤只是实现了标题的自定义显示，接着在标题栏的两个按钮添加监听事件，实现的代码如下：

```
public class TitleLayout  extends LinearLayout {

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_layout, this);
        //添加监听事件
        Button titleBack = (Button) findViewById(R.id.title_back);
        Button titleEdit = (Button) findViewById(R.id.title_edit);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //结束当前Activity
                ((Activity) getContext()).finish();
            }
        });

        titleEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "设置成功。", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
```

到这里就实现了全部的过程。运行后点击设置按钮进行测试，实现的效果如下：会弹出一个“设置成功的提示”

![1536048088942](image\end.png)

## 参考文献

- [Android自定义标题栏](https://www.cnblogs.com/woider/p/5119088.html)
- [Android 自定义控件 自定义标题栏](https://blog.csdn.net/plain_maple/article/details/52651171)