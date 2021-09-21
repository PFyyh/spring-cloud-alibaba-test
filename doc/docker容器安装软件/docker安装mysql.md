---
typora-root-url: images
---

##### 挂载外部配置和数据安装

````shell
mkdir /opt
mkdir /opt/mysql
mkdir /opt/mysql/conf.d
mkdir /opt/mysql/data/
````



##### 创建my.cnf配置文件

````shell
touch /opt/mysql/my.cnf
````

my.cnf添加如下内容：

````shell
[mysqld]
user=mysql
character-set-server=utf8
default_authentication_plugin=mysql_native_password
secure_file_priv=/var/lib/mysql
expire_logs_days=7
sql_mode=STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION
max_connections=1000

[client]
default-character-set=utf8

[mysql]
default-character-set=utf8
````



2、创建容器，并后台启动

````shell
docker run --restart=always --privileged=true -d -v /opt/mysql/data/:/var/lib/mysql -v /opt/mysql/conf.d:/etc/mysql/conf.d -v /opt/mysql/my.cnf:/etc/mysql/my.cnf -p 3306:3306 --name my-mysql -e MYSQL_ROOT_PASSWORD=password mysql
````



参数说明：
--restart=always： 当Docker 重启时，容器会自动启动。
--privileged=true：容器内的root拥有真正root权限，否则容器内root只是外部普通用户权限
-v /opt/mysql/conf.d/my.cnf:/etc/my.cnf：映射配置文件
-v /opt/mysql/data/:/var/lib/mysql：映射数据目录

注意：启动mysql报如下错误，那是因为MYSQL新特性secure_file_priv对读写文件的影响。

````shell
ERROR: mysqld failed while attempting to check config
command was: "mysqld --verbose --help"

mysqld: Error on realpath() on '/var/lib/mysql-files' (Error 2 - No such file or directory)
2019-09-14T09:52:51.015937Z 0 [ERROR] [MY-010095] [Server] Failed to access directory for --secure-file-priv. Please make sure that directory exists and is accessible by MySQL Server. Supplied value : /var/lib/mysql-files
2019-09-14T09:52:51.018328Z 0 [ERROR] [MY-010119] [Server] Aborting
````



解决问题:
windows下：修改my.ini 在[mysqld]内加入secure_file_priv=/var/lib/mysql

linux下：修改my.cnf 在[mysqld]内加入secure_file_priv=/var/lib/mysql