## 搭建Nacos集群暴露metrics数据

按照[部署文档](https://nacos.io/zh-cn/docs/deployment.html)搭建好Nacos集群

配置application.properties文件，暴露metrics数据。我开启了三个nacos，每一台都要开启下面的配置。

```
management.endpoints.web.exposure.include=*
```

重启后访问http://ip:8848/nacos/actuator/prometheus

![image-20210915210608789](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210915210608789.png)

## 搭建prometheus采集Nacos metrics数据

下载你想安装的prometheus版本，地址为[download prometheus](https://prometheus.io/download/)

我把Prometheus安装在我的nginx服务器上面。

### linux & mac

解压prometheus压缩包

```
tar xvfz prometheus-*.tar.gz
cd prometheus-*
```

启动prometheus服务

```
./prometheus --config.file="prometheus.yml"
```

访问[Prometheus Time Series Collection and Processing Server](http://192.168.50.56:9090/targets)

如果状态是未知状态，可以点一次链接就好了。

![image-20210915210324347](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210915210324347.png)

## 搭建grafana图形化展示metrics数据

### linux

```
sudo yum install https://s3-us-west-2.amazonaws.com/grafana-releases/release/grafana-5.2.4-1.x86_64.rpm
sudo service grafana-server start
```

访问页面http://192.168.50.56:3000/。下图是我已经配置好了数据源。

![image-20210915210834016](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210915210834016.png)

添加数据源，注意选择类型。

![image-20210915210754024](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210915210754024.png)

导入Nacos grafana监控[模版](https://github.com/nacos-group/nacos-template/blob/master/nacos-grafana.json)

![image-20210915211011765](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210915211011765.png)

将下载的模板上传

![image-20210915211026255](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210915211026255.png)

通过这里就能看到nacos情况。

![image-20210915211056498](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210915211056498.png)