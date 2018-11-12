# Android笔记

## Intent

- 显式intent：可以直接启动一个activity，在同一个应用中；

- 隐式intent：可以启动另一个应用程序的activity

  但在隐式intent中，当另外一个程序的activity访问设置为false的时候，就不能访问；配置需要在AndroidManifest.xml中进行配置
- intent过滤器：在AndroidManifest.xml中进行配置，使用<intent-filter>中进行配置，可以在启动的时候访问另外程序的activity，这是对应多个activity的访问。

## 通过网页链接本地activity

1. 首先AndroidManifest.xml进行配置

   ```
   <intent-filter>
   <category 					    										android:name="android.intent.category.BROWSABLE"/>
   <category android:name="android.intent.category.DEFAULT"/>
   
   <action android:name="android.intent.action.VIEW"/>
   <data android:scheme="app"/>
   </intent-filter>
   ```

2. 然后在HTML5中是要加你那个href标签，设定启动哪个APP；

3. 接收浏览器传过来的参数，通过getIntent().getIntent()函数获取 启动这个activity的对象的数据，他是一个URI对象；

## Context作用

它是用来访问全局信息的接口，比如说应用程序的资源，字符串资源等。对于一些常用的资源都继承Context，就是为了方便数据的管理和获取。也可以获取图片的数据。例如通过如下获取字符串。

```
System.out.println(getResources().getText(R.string.hello_world));
```

所以可以使用contex作为全局共享信息的桥梁。

## Application用途

主要是用于多个主键之间数据的共享。

- 首先，创建一个App.java

- 接着在AndroidManifest.xml进行配置

- 完成以上之后，我们可以在我们创建的砸死两个Java文件中添加共享我们要共享的数据，实现的代码如下：

  ```
  ((App)(getApplicationContext())).setTextData(editText.getText().toString());
  ```

  完成以上以后，就可以实现在另一个APP保存的数据，在另个一也能收到。

## Application生命周期

一般执行某个activity的时候先执行onCreate函数，所以可以在这个函数中进行初始化。

## Service使用

### 1 使用Service

一些不需要显示的界面，比如后台运行的程序，与 服务器保持推送等，这就使用到Service。

```
 startService(intent); //开启Service
 stopService(intent);  //关闭Service
```

### 2 绑定Service

也是启动Service的一种方式

```
bindService(intent,this, Context.BIND_AUTO_CREATE);//链接服务
unbindService(this);//关闭服务
```

### 3 Service 的生命周期

只要有两个函数

- onCreate函数：
- onDestroy函数：可以在这里设置服务停止标志位。

如果同时启动服务和绑定服务的话，那么必须要同时停止和解除绑定服务，这个服务才会停止。

### 4 Service接收activity数据

通过intent传递。简单的实现代码如下：

- 传递

```
intent.putExtra("data",editTextData.getText().toString()); //在activity把数据传递出去
```

- 接收

在service中有一个onStartCommand(Intent intent, int flags, int startId)函数，这个函数接收到的intent可以获取到传递过来的参数。

## Json数据

读取json文件

![1534942598678](C:\Users\RD007\AppData\Local\Temp\1534942598678.png)

数据

![1534942625916](C:\Users\RD007\AppData\Local\Temp\1534942625916.png)

 

## 线程

### 1 直接创建线程

使用主线程的话会造成线程阻塞，体验效果不好。对于这类问题，需要异步来操作，对于大量的等待、计算或者阻塞的方法，这时候就需要多线程。而对于主线程是用于与用户交互的线程，而主线程以为，可以通过创建更多的线程来完成更加复杂的工作。对于创建线程的代码如下：

```
                new Thread(){//匿名的类

                    public void run() {
                        super.run();
                        try {
                            while (true){
                                sleep(1000);
                                System.out.println(">>>>>>>>>>>>>>>>>>Tick");
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
```

创建了线程之后，就能够并发的执行，而不影响其他操作。

### 2 AsyncTask使用

在这个类中可以直接实现异步操作。

- 首先创建AsyncTask对象，然后调用execute方法；
- 完成以上以后，AsyncTack会调用其中的回调方法，完成后续的工作；回调函数主要有三个方法，代表着AsyncTask执行之前、执行中和执行后调用的函数。



## 通过http get获取网络数据

实现的代码如下：

```
                // 读取数据，中间的过程比较耗时，所以使用异步的方式获取
                new AsyncTask<String,Void,Void>(){

                    @Override
                    protected Void doInBackground(String... strings) {
                        try {
                            URL url = new URL(strings[0]);

                            //获取互联网连接
                            URLConnection urlConnection = url.openConnection();
                            //获取当前网络的输入流
                            InputStream inputStream =urlConnection.getInputStream();

                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
                            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                            //用于 保持一行数据
                            String line;
                            while ((line = bufferedReader.readLine())!=null){
                                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>:"+line);
                            }

                            bufferedReader.close();
                            inputStreamReader.close();
                            inputStream.close();

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute("http://eiiman.raindi.net/api/pumper/?tdsourcetag=s_pcqq_aiomsg");
            }
```

