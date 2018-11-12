# Android生命周期(转)

刚开始学习Android的时候，感觉Android生命周期很少用到。但最近发现，对于生命周期的理解是很有必要的。所以对Android的生命周期做简单的总结。

## 1 四种状态

Activity是由Activity栈进管理，当来到一个新的Activity后，此Activity将被加入到Activity栈顶，之前的Activity位于此Activity底部。Acitivity一般意义上有四种状态： 

1. 当Activity位于栈顶时，此时正好处于屏幕最前方，此时处于**运行状态**；
2. 当Activity失去了焦点但仍然对用于可见（如栈顶的Activity是透明的或者栈顶Activity并不是铺满整个手机屏幕），此时处于**暂停状态**；
3. 当Activity被其他Activity完全遮挡，此时此Activity对用户不可见，此时处于**停止状态**；
4. 当Activity由于人为或系统原因（如低内存等）被销毁，此时处于**销毁状态；**

在每个不同的状态阶段，Adnroid系统对Activity内相应的方法进行了回调。因此，我们在程序中写Activity时，一般都是继承Activity类并重写相应的回调方法。 如图所示。图中详细给出了activity真个生命周期的过程，以及不同的状态期间相应的回调方法。

![](image\lifecycle.png)

## 2 执行过程

- Activity实例是由系统自动创建，并在不同的状态期间回调相应的方法。一个最简单的完整的Activity生命周期会按照如下顺序回调：onCreate -> onStart -> onResume -> onPause -> onStop -> onDestroy。称之为**entire lifetime。** 
- 当执行onStart回调方法时，Activity开始被用户所见（也就是说，onCreate时用户是看不到此Activity的，那用户看到的是哪个？当然是此Activity之前的那个Activity），一直到onStop之前，此阶段Activity都是被用户可见，称之为**visible lifetime。** 
- 当执行到onResume回调方法时，Activity可以响应用户交互，一直到onPause方法之前，此阶段Activity称之为**foreground lifetime。** 

在实际应用场景中，假设A Activity位于栈顶，此时用户操作，从A Activity跳转到B Activity。那么对AB来说，具体会回调哪些生命周期中的方法呢？回调方法的具体回调顺序又是怎么样的呢？

开始时，A被实例化，执行的回调有A:onCreate -> A:onStart -> A:onResume。

**当用户点击A中按钮来到B时，假设B全部遮挡住了A，将依次执行A:onPause -> B:onCreate -> B:onStart -> B:onResume -> A:onStop。**

**此时如果点击Back键，将依次执行B:onPause -> A:onRestart -> A:onStart -> A:onResume -> B:onStop -> B:onDestroy。**

至此，Activity栈中只有A。在Android中，有两个按键在影响Activity生命周期这块需要格外区分下，即Back键和Home键。我们先直接看下实验结果：

**此时如果按下Back键，系统返回到桌面，并依次执行A:onPause -> A:onStop -> A:onDestroy。**

**此时如果按下Home键（非长按），系统返回到桌面，并依次执行A:onPause -> A:onStop。由此可见，Back键和Home键主要区别在于是否会执行onDestroy。**



## 转载

- [原文链接](https://www.cnblogs.com/lwbqqyumidi/p/3769113.html)

