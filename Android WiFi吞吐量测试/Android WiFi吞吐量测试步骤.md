# Android WiFi吞吐量测试步骤

## 1 测试内容

由于网络的双向通信，通常以上传（Tx）传输速率和下载（Rx）传输速率2个指标来衡量测试设备的吞吐量。

【上传（Tx）传输速率】测试设备向辅助设备发送信令或数据所测得的速率

【下载（Rx）传输速率】测试设备接收辅助设备发送的信令或数据所测得的速率

## 2 测试准备    

- 电脑要安装java环境

- 一台Android手机（测试设备），并安装iPerf for Android 2.06（iperf是一个网络性能测试工具，测试从Client发向Server）
- 一台Windows PC（辅助设备），并安装jperf 2.0（jperf为iperf的图形化程序）
- 手机和PC连接同一个AP（同一个路由器）

## 3 测试上行速率（PC作Server，手机作Client）

**PC端:**

1. 打开jperf

   如图所示，选择2.0.0或者2.0.2**以管理员身份运行jperf.bat**

   ![1542075403577](images\iperf_pc.png)

2. 对软件进行配置

![](images\iperf.png)

-  IPerfMode: Server [命令-s，服务端模式]
-  ListenPort: 5001 [命令-p 5001，服务端监听端口为5001]
- NumConnections: 1 [命令-P 1，保持的连接数为1]
- OutputFormat: MBits [命令-f m，输出格式为Mbps]
- ReportInterval: 1 [命令-i 1，每次报告的时间间隔为1秒]
- Protocol:TCP
- 点击右上角Run Iperf

**手机端:**

- 安装iperf软件，选择图中一个软件进行安装。

![](images\1542074694884.png)

- 在软件编辑框，输入命令-c xx.xx.xx.xx -p 5001 -t 60 -P 1

              -c xx.xx.xx.xx 表示客户端模式，后接服务端ip地址

              -p 5001 表示服务端监听的端口为5001

              -t 60 表示测试时间为60秒

              -P 1 表示客户端与服务端之前的线程数为1，需要与服务端同时使用此参数

      启动测试

例如：

![](images\iperf_p.png)

Interval表示时间间隔。Transfer表示时间间隔里面转输的数据量。Bandwidth是时间间隔里的传输速率。最后一行是本次测试的统计。测试可知带宽平均为 154 Kbits/sec.

## 4 测试下行速率（手机作Server，PC作Client）

手机端：

- 打开iperf
- 在编辑框，输入命令iperf -s
-s 表示服务端模式
- 启动测试

PC端：

- 打开jperf

![](images\client.png)

- IPerfMode: Client, Server address: xx.xx.xx.xx [命令-c xx.xx.xx.xx]

-  Port:5001 [命令-p5001]

-  ParallelStreams: 1 [命令-P 1]

-  Transmit:60 [命令-t60]

- OutputFormat: MBits [命令-f m]

- ReportInterval: 1 [命令-i 1]

- Protocol:TCP

- 点击右上角Run Iperf

## 5 测试数据的导出

在PC端可以直接导出数据，如所示：

![1542078478370](images\out.png)

## 注意事项：

- 电脑的防火墙需要关
- 测试的手机最好不要灭屏
- 要用单独的路由器进行测试

