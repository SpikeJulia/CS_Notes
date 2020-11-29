# CURL 用法指南

### 简介

curl 是常用的命令行工具，用来请求 Web 服务器，其名字就是客户端（client）的 URL 工具的意思。

它的功能非常强大，命令行参数多达几十种。如果熟练的话，完全可以取代 Postman 这一类的图形界面工具。

本文介绍它的主要命令行参数，作为日常的参考，方便查阅。内容翻译自 [《curl cookbook》](https://catonmat.net/cookbooks/curl)。

<img src="E:\cmder\A_CS_NOTES\CS_Notes\Linux\abort_command\curl.jpg" style="zoom: 67%;" />

***

### 不带参数

不带任何参数时，curl 就是发出 GET 请求：

```shell
$ curl https://www.example.com
```

***

### -A

-A 参数指定客户端的用户代理标头 —— User-Agent，curl的默认用户代理字符串是 **curl/[version]**:

*User-Agent：UA，它是一个特殊字符串头，使得服务器能够识别客户使用的操作系统及版本、CPU 类型、浏览器及版本、浏览器渲染引擎、浏览器语言、浏览器插件等。*

```shell
## 将User-Agent 改成 Safari 浏览器
$ curl -A '76.0.3809.100 Safari' https://www.example.com
```

```shell
## 移除 User-Agent 标头
$ curl -A '' https://www.example.com
```

```shell
## -H 参数也可以直接指定标头，更改UA
$ curl -H 'User-Agent: Mozilla/5.0' https://www.example.com
```

***

### -b

-b 用做向服务器发送Cookie：

```shell
## 生成一个Cookie: name=spikejulia，向服务器发送此 Cookie
$ curl -b 'name=spikejulia' https://www.example.com
```

```shell
## 发送多个Cookie，分号隔开
$ curl -b 'name=spikejulia;age=23' https://www.example.com
```

```shell
## 读取本地的Cookie文件，文件里是Cookie信息，发送到服务器
$ curl -b Cookies.txt https://www.example.com
```

***

### -c

-c 将服务器设置的Cookie写入一个文件中：

```shell
## 将服务器 http 请求回应所设置的Cookie写入本地文件 Cookies.txt
$ curl -c Cookies.txt https://www.example.com
```

***

### -d

-d 用于发送 POST 请求，设置请求体：

```shell
## HTTP 请求会自动加上标头Content-Type : application/x-www-form-urlencoded。并且会自动将请求转为 POST 方法，因此可以省略-X POST
$ curl -d 'name=spikejulia&age=23' https://www.example.com
## 或者分开写
$ curl -d 'name=spikejulia' -d 'age=23' https://www.example.com
```

-d 还可以读取本地文件的数据，并发送至服务器

```shell
## 读取data.txt的内容作为数据体发送
$ curl -d '@data.txt' https://www.example.com
```

***

### -data-urlencode

-data-urlencode 等同于 -d，发送POST请求的数据体，区别在于自动进行URL 编码：

```shell
## 请求体中带了一个空格，所以需要URL编码
$ curl -data-urlencode 'name=spike julia' https://www.example.com
```

***

### -e

-e 用以设置 HTTP 的标头 Referer，表示请求的来源：

```shell
$ curl -e 'https://github.com' https://www.example.com
```

-H 参数通过直接添加Referer能实现相同效果

```shell
$ curl -H 'Referer:https://github.com' https://www.example.com
```

***

### -F

-F 用来向服务器上传二进制文件

```shell
## 给HTTP请求加上标头Content-Type: multipart/form-data，然后将文件julia.png作为file字段上传
$ curl -F 'file=@juali.png' https://www.example.com/picture
```

-F 可以指定MIME类型

```shell
## 指定 MIME 类型为image/png，否则 curl 会把 MIME 类型设为application/octet-stream
$ curl -F 'file=@juali.png;type=image/png' https://www.example.com/picture
```

-F 可以指定文件名

```shell
## 原文件名为 julia.png, 服务器收到为 spike.png
$ curl -F 'file=@juali.png;filename=spike.png' https://www.example.com/picture
```

***

### -G

-G 用来构造 URL 查询字符串

```shell
## 发出一个GET请求，实际请求为 https://www.example.com/search/name=spikejulia&age=23, 如果省略-G则会发出一个POST请求
## 如果需要URL编码，可使用 -data-urlencode
$ curl -G -d 'name=spikejulia' -d 'age=23' https://www.example.com/search
```

***

### -H

用以添加HTTP请求标头：

```shell
## 设置标头语言
$ curl -H 'Accept-Language: en-US' https://www.example.com
```

设置多个标头

```shell
## 设置多个标头
$ curl -H 'Accept-Language: en-US' -H 'Secret-Message:mkl' https://www.example.com
```

设置Content-Type

```shell
$ curl -d '{"name": "spikejulia", "pass": "root"}' -H 'Content-Type: application/json' https://www.example.com/login
```

***

### -i

打印服务器回应的 HTTP标头：

```shell
## 收到服务器回应后，先输出扶额u其回应的标头然后空一行，再输出网页源码
$ curl -i https://www.example.com
```

***

### -I

用以向服务器发出 HEAD请求，然后将服务器返回的 HTTP标头打印出来：

```shell
$curl -I https://www.example.com
```

--head 参数等同于 -I

```shell
$curl --head https://www.example.com
```

***

### -k

跳过 SSL 检测

```shell
## 不会检查服务器的SSL证书是否正确
$ curl -k https://www.example.com
```

***

### -L

使 HTTP 请求跟随服务器的重定向，curl默认不跟随：

```shell
$ curl -L -d 'name=julia' https://www.example.com
```

***

### --limit-rate

用以限制 HTTP请求和回应的贷款，可以模拟慢网速的环境：

```shell
## 限制带宽为 400k/s
$ curl -limit-rate 400k https://www.example.com
```

***

### -o

将服务器的回应保存为文件：

```shell
## 将www.example.com保存为 example.html
$ curl -o example.html https://www.example.com
```

等同于 wget命令:

```shell
## 将www.example.com保存为 example.html
$ curl -wget example.html https://www.example.com
```



***

### -O

同 -o，但是-O自动默认文件名为 URL最后的部分

```shell
## 保存的文件名为 path.html
$ curl -O https://www.example.com/path.html
```

***

### -s

将不输出错误信息和进度信息

```shell
## 一旦发生错误，不会显示错误信息
$ curl -s https://www.example.com
```

不输出任何信息

```shell
## 不产生任何输出的命令,判断它是否成功的唯一方法是检查curl程序的返回代码。如果它是零，那么curl成功，否则它失败。
$ curl -s -o /dev/null https://www.example.com
```



***

### -S

指定只输出错误信息，通常和 -s 一起使用

希望curl不输出额外信息，但是又想知道错误原因的时候适用此组合命令

```shell
##没有任何输出，除非发生错误
curl -S -s -o /dev/null https://www.example.com
```

***

### -u

设置服务器其认证的用户名和密码，

```shell
##设置用户名 spike 密码 root，curl对该用户名和密码进行base64编码，并在授权HTTP头中将其发送到https://www.example.com/login的服务器
$ curl -u 'spike:root' https://www.example.com/login
```

设置用户名和密码在URL中, 此时不需要使用 -u 参数

```shell
## curl能自动识别用户名和密码
$ curl https://spike:root@example.com/login
```

只输入用户名，curl会提示输入密码

```shell
$ curl -u 'spike' https://www.example.com/login
```

***

### -v

输出通信的整个过程，用于调试

```shell
$ curl -v https://www.example.com
```

--trace 参数也可以用于调试，还能输入原始的二进制数据

```shell
$ curl -trace https://www.example.com
```

***

### -x

指定HTTP请求的代理, 如果没有指定代理协议，默认为 HTTP

```shell
## 指定HTTP请求通过 myproxy.com:8080 的socks0 代理发出
$ curl -x socks0://spike:root@mypoxy.com:8080 https://www.example.com
```

***

### -X

指定HTTP 请求的方法

```shell
## POST 方法
$ curl -X POST https://www.example.com
```

