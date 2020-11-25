# Ubuntu 安装MySQL

## 1. 安装MySQL

```
#更新软件包
sudo apt-get update
#安装 mysql-server
sudo apt-get install mysql-server
```

## 2. 配置MySQL

### 2.1 初始化配置

```
sudo mysql_secure_installation
```

* 其他配置：

```
#1
VALIDATE PASSWORD PLUGIN can be used to test passwords...
Press y|Y for Yes, any other key for No: N (我的选项)

#2
Please set the password for root here...
New password: (root)
Re-enter new password: (root)

#3
By default, a MySQL installation has an anonymous user,
allowing anyone to log into MySQL without having to have
a user account created for them...
Remove anonymous users? (Press y|Y for Yes, any other key for No) : N (我的选项)

#4
Normally, root should only be allowed to connect from
'localhost'. This ensures that someone cannot guess at
the root password from the network...
Disallow root login remotely? (Press y|Y for Yes, any other key for No) : Y (我的选项)

#5
By default, MySQL comes with a database named 'test' that
anyone can access...
Remove test database and access to it? (Press y|Y for Yes, any other key for No) : N (我的选项)

#6
Reloading the privilege tables will ensure that all changes
made so far will take effect immediately.
Reload privilege tables now? (Press y|Y for Yes, any other key for No) : Y (我的选项)

```

### 2.2 检查mysql服务状态

* 命令

```
sysytemctl status mysql.service
```

* 正常状态

```
root@iZbp15uz5fg3jofejnlb37Z:~# systemctl status mysql.service
● mysql.service - MySQL Community Server
     Loaded: loaded (/lib/systemd/system/mysql.service; enabled; vendor preset:>
     Active: active (running) since Wed 2020-11-25 21:29:21 CST; 5min ago
   Main PID: 1850 (mysqld)
     Status: "Server is operational"
      Tasks: 38 (limit: 2319)
     Memory: 332.3M
     CGroup: /system.slice/mysql.service
             └─1850 /usr/sbin/mysqld

Nov 25 21:29:20 iZbp15uz5fg3jofejnlb37Z systemd[1]: Starting MySQL Community Se>
Nov 25 21:29:21 iZbp15uz5fg3jofejnlb37Z systemd[1]: Started MySQL Community Ser>

```

***

## 3. 配置远程访问

在Ubuntu下MySQL缺省是只允许本地访问的，使用workbench连接工具是连不上的；如果你要其他机器也能够访问的话，需要进行配置。

### 3.1 首先根用户进入

```
sudo mysql -uroot -p
```

登入root进行权限设置：

* MySql-8前:

  ```
  mysql>GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'password'WITH GRANT OPTION;
  mysql> FLUSH PRIVILEGES;
  ```

* MySql-8授权:

  ```
  mysql> CREATE USER 'root'@'%' IDENTIFIED BY 'password';
  mysql> GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;
  mysql> FLUSH PRIVILEGES;
  ```

### 3.3 配置config文件

```
## 进入mysql配置文件
cd /etc/mysql/mysql.conf.d/
## 查看
ls
mysql.cnf  mysqld.cnf
## 修改  mysqld.cnf 中的 bind-address 为 0.0.0.0
vi mysqld.cnf
```

### 3.3 新建数据库和用户测试

```
## 创建数据库
CREATE DATABASE test;

## 创建用户tangxuan(123456)，并允许远程连接
mysql> create user 'tangxuan'@'%' identified by '123456';
Query OK, 0 rows affected (0.00 sec)

mysql> grant all privileges on *.* to 'tangxuan'@'%' with grant option;
Query OK, 0 rows affected (0.00 sec)

mysql> flush privileges;
Query OK, 0 rows affected (0.01 sec)

```

### 3.4 ！！！！设置阿里云防火墙开放端口3306

