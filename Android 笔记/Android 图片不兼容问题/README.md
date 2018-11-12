# Android 图片不兼容问题

## 问题

故事是这样子开始的，把开发完成的APP下载到手机上能运行，这个手机的Android是9.6版本。但是当把这个APP下载到公司的Android开发板或者云屏上就不能运行了，一直报错。我了解决这个问题，找了大半天。原来报错的都是布局文件中的背景图。这个图片的文件名后面是带有v24的，如图所示。图中的有些有些文件名后面是带有v24的。对于这些图片当拿来做背景图片的时候，低版本的Android就不兼容，不能够解析这些图片。

![1536662521913](https://raw.githubusercontent.com/zhi-z/work/master/Android/Android%20%E5%9B%BE%E7%89%87%E4%B8%8D%E5%85%BC%E5%AE%B9%E9%97%AE%E9%A2%98/image/V24.png)

## 解决的方法

如图所示。选择带有v24的文件，然后右键选择，如图所示，最后选择Move.

![1536662737164](https://raw.githubusercontent.com/zhi-z/work/master/Android/Android%20%E5%9B%BE%E7%89%87%E4%B8%8D%E5%85%BC%E5%AE%B9%E9%97%AE%E9%A2%98/image/v24_to_y.png)

当选择Move这回会弹出如下对话框。如图所示。

![1536663042422](https://raw.githubusercontent.com/zhi-z/work/master/Android/Android%20%E5%9B%BE%E7%89%87%E4%B8%8D%E5%85%BC%E5%AE%B9%E9%97%AE%E9%A2%98/image/move.png)

选择drawable，把他移动到drawable文件夹中。到这里就完成了。具体为什么把他移动到drawable就可以兼容低版本了，这个问题我也还没弄懂。后面这个解决方法是**四金童学**教的。

故事到这里结束，问题解决。