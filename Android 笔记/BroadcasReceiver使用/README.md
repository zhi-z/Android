# BroadcastReceiver使用

中文是指广播接收器的使用。
## 1 主要内容
- 使用BroadcastReceiver
- 动态注册和注销BroadcastReceiver
- BroadcastReceiver的优先级

## 2 实现方式
其实它也是一种通信的方式。实现的步骤如下：
- 首先，创建一个Receiver类

- 接着在mainActivity中使用sendBroadcast(Intent intent);发送数据

通过以上两步就可以实现通过

## 3 优先级

- 首先，创建两个Receiver,一个发送。如图所示，在AndroidManifest.xml走进行修改，优先级为10的先接收到消息。

![1537005959707](image\priority.png)



- 定义好以后，如果执行完高的优先级以后，不想让后面低的优先级继续执行了，改如何做？我们可以在高的优先级中的onReceive中执行abortBroadcast()函数来结束广播机制。结束的是后面优先级低的接收器。但是在发送消息的时候不能 使用如上的sendBroadcast函数来发送数据，如果使用这个函数来发送数据的话会发生异常。这时候需要使用sendOrdredBroadcast()函数来发送消息。

