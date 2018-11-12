# SharedPreferences数据存储(转)

## 1 简介

在Android开发中，经常需要将少量简单类型数据保存在本地，如：用户设置。这些需要保存的数据可能一两个字符串，像这样的数据一般选择使用SharedPreferences来保存。 

**SharedPreferences**：一个轻量级的存储类，特别适合用于保存软件配置参数。（是用xml文件存放数据，文件存放在/data/data/<package name>/shared_prefs目录下） 

SharedPreferences可以保存的数据类型有：int、boolean、float、long、String、StringSet。 

## 2 使用步骤

### 2.1 数据存储

保存数据一般分为四个步骤：

1. 使用Activity类的getSharedPreferences方法获得SharedPreferences对象；
2. 使用SharedPreferences接口的edit获得SharedPreferences.Editor对象；
3. 通过SharedPreferences.Editor接口的putXXX方法保存key-value对；
4. 通过过SharedPreferences.Editor接口的commit方法保存key-value对。

 ### 2.2 读取数据

读取数据一般分为两个步骤：

1. 使用Activity类的getSharedPreferences方法获得SharedPreferences对象；
2. 通过SharedPreferences对象的getXXX方法获取数据；

 ## 3 方法

- 获取SharedPreferences对象：根据name查找SharedPreferences，若已经存在则获取，若不存在则创建一个新的

```
public abstract SharedPreferences getSharedPreferences (String name, int mode)
```

参数
name：命名 
mode：模式，包括 
MODE_PRIVATE（只能被自己的应用程序访问） 
MODE_WORLD_READABLE（除了自己访问外还可以被其它应该程序读取） MODE_WORLD_WRITEABLE（除了自己访问外还可以被其它应该程序读取和写入） 

若该Activity只需要创建一个SharedPreferences对象的时候，可以使用getPreferences方法，不需要为SharedPreferences对象命名，只要传入参数mode即可 。

```
public SharedPreferences getPreferences (int mode)
```

- 获取Editor对象:由SharedPreferences对象调用

```
abstract SharedPreferences.Editor edit()
```

- 写入数据:由Editor对象调用

```
//写入boolean类型的数据
abstract SharedPreferences.Editor   putBoolean(String key, boolean value)
//写入float类型的数据
abstract SharedPreferences.Editor   putFloat(String key, float value)
//写入int类型的数据
abstract SharedPreferences.Editor   putInt(String key, int value)
//写入long类型的数据
abstract SharedPreferences.Editor   putLong(String key, long value)
//写入String类型的数据
abstract SharedPreferences.Editor   putString(String key, String value)
//写入Set<String>类型的数据
abstract SharedPreferences.Editor   putStringSet(String key, Set<String> values)
```

参数 key：指定数据对应的
key value：指定的值 

- 清空数据:由Editor对象调用

```
abstract SharedPreferences.Editor  clear()
```

- 提交数据：由Editor对象调用

```
abstract boolean  commit()
```

- 读取数据：由SharedPreferences对象调用

```
//读取所有数据
abstract Map<String, ?> getAll()
//读取的数据为boolean类型
abstract boolean    getBoolean(String key, boolean defValue)
//读取的数据为float类型
abstract float  getFloat(String key, float defValue)
//读取的数据为int类型
abstract int    getInt(String key, int defValue)
//读取的数据为long类型
abstract long   getLong(String key, long defValue)
//读取的数据为String类型
abstract String getString(String key, String defValue)
//读取的数据为Set<String>类型
abstract Set<String>    getStringSet(String key, Set<String> defValues)
```

参数 

key：指定数据的

key defValue：当读取不到指定的数据时，使用的默认值defValue 

## 4 性能

- ShredPreferences是单例对象，第一次打开后，之后获取都无需创建，速度很快。
- 当第一次获取数据后，数据会被加载到一个缓存的Map中，之后的读取都会非常快。
- 当由于是XML<->Map的存储方式，所以，数据越大，操作越慢，get、commit、apply、remove、clear都会受影响，所以尽量把数据按功能拆分成若干份。



## 转载

[原文（SharedPreferences详解）](https://www.jianshu.com/p/59b266c644f3)



 

 