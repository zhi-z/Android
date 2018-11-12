# Android Fragment使用

为什么要使用Fragment：因为做一个APP的时候都会使用到页面切换，在之前一般会使用activity方式进行切换，如果同一个程序的内部做切换的话，还使用activity很不合适。为什么呢？因为activity是一个比较重量级的主键。所以使用Fragment来代替。跳转的速度比activity快。

- 创建：创建的时候要选择Fragment框架，如图所示。

![1536147188443](image\setUp.png)

- 创建完毕后会有一个palceholderGragment函数，这时候我们需要进行重构，把他放到一个单独的类中。如图所示:

![1536147624200](image\reconsitution.png)

- 我们要做的是设置一个按键，这个按钮是在main_fragment中的，然后点击按键就会切换到另一个界面，也就是另一个Fragment。所以我们重新创建一个布局文件。如图所示：

  ![1536147895657](image\fragment_xml.png)

- 接着创建一个类，这个类是对应上面的另一个布局文件，也就是要跳转过去的Fragment，这个类要继承Fragment，如图所示。

![1536148376782](image\fragment_class.png)

这里主要是对布局文件进行初始化，然后返回。接着如何操作可以仿照MainActivity中的方式进行。

- 然后在palceholderGragment函数中进行连接，也就是进行提交。

![1536148707582](image\fragment_commit.png)

- 后退键的使用，当我们冲main_Fragment中跳转到另一个Fragment的时候我们需要按后退键返回到main_Frament中，实现的代码如下：使用的是popBasckStack函数

![1536149033316](image\back.png)

这样就完成了。