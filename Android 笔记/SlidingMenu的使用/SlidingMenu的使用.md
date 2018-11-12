# SlidingMenu的使用

SlidingMenu主要是为了让菜单显示得更加的好看。比如设置菜单的显示等，使用这个方式会更加的人性化。、

## 1 简介

SilidingMenu是一个开源的库。是在GitHub上的，开源直接下载。

## 2 配置

首先，把SlidingMenu下载下来。我们要使用的是SilidingMenu中的library还要使用actionBarSherlock中的文件，所以要把这两个文件导入。如图所示，导入的时候选择复制进来.

![1536149921687](image\import.png)

把两个库导入进来以后，再创建一个工程，用来使用者两个库的。**主要这个工程创建完毕后，要记得添加这两个库到项目中。**

- 把这两个库添加到项目中以后，配置中的版本要一样，所以要进行修改，使他们的配置一样。到这里就可以进行开发了。

## 3 开发

首先创建一个SilidingMenu的布局文件，实现的代码如下：

![1536150586126](image\SilidingMenu_xml.png)

接着在主函数中要写的代码如下：

![1536150664955](image\SilidingMenu_main.png)

到这里已经能够实现了，但是他是铺满整个屏幕的。接着在进行配置，实现的代码如下：添加了如下一行代码，就是设置显示的偏移的尺寸。

![1536150946701](image\SilidingMenu_CustomWidth.png)

实现的效果如图所示：

![1536151255120](image\ResultsShow.png)

完成到这一步之后就可以往SlidingMenu中添加一些控件和按钮了。

- 以上是通过滑动屏幕吧SlidingMenu把他拉出来的，现在我们要进一步通过物理按键的方式把SlidingMenu效果显示出来。实现的代码如下：重写onKeyDown事件。

![1536151706215](image\keyDown.png)

到这里就完成了。

## 参考文献

https://www.jianshu.com/p/5ae5eec97541