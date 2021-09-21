# git入门

## 简单使用

##### 初始化

```shell
git init	
```

##### 检测文件状态

红色表示新增的文件，修改了原老文件。(工作区)

绿色表示管理中，但是没有生成版本。（提供后悔，思考是否提交版本的地方）

![image-20210918111505958](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210918111505958.png)

```shell
git status
```

##### 加入版本

###### 添加文件

```shell
git add <file>
```

###### 添加所有文件

```
git add .
```

##### 个人配置

配置内容包括用户名和邮箱。

```
git config --global user.email ""
git config --global user.name ""
```

##### 生成新版本

```
git commit -m "messages"
```

##### 查看提交日志

```shell
git log
```

### 回滚

##### 查看版本

```shell
git log
```

##### 图形展示

保留哈希和信息

```shell
git log --graph --pretty=format:="%h %s"
```

##### 回滚到指定版本

```shell
git reset --hard <version>
```

##### 撤销回滚的版本

通过下面命令查看版本

```shell
git reflog
```

### 总结

![image-20210918165055902](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210918165055902.png)

## 删除总结

当文件没有加入版本，那随便删除。

##### 已经提交

回滚到暂存区

```shell
git reset --soft <version>
```

然后执行回滚工作区

```
git reset Head
```

如果想要删除

```shell
git checkout --<file>
```

或者直接rm

```shell
rm <file>
```

## 分支

开发新功能的时候，需要创建分支。

解决线上bug的时候，也需要创建分支。

##### 查看分支

```shell
git branch
```

##### 创建分支

```shell
git branch <branchName>
```

或者使用,创建分支同时切换分支。

```
git checkout -b <branch>
```

##### 切换分支

```shell
git checkout <branchName>
```

然后就可以正常操作。

##### 合并分支

如果B需要合并到A，那么需要切换到A然后执行下面的命令。合并以后会产生新的版本且如果产生过冲突的话，这样会导致分支还是老版本。所以如果需要继续开发分支，需要在分支再次merge。

```
git merge <branchName>
```

如果出现冲突的话，只有自己手动解决。后续有软件解决冲突。

##### 删除分支

```shell
git branch -d <branchName>
```

## 快速解决冲突

修改config文件

```
[diff]
    tool = bc4
[difftool]
	prompt = true
[difftool "bc4"]
	cmd = \"C:/Program Files/Beyond Compare 4/BComp.exe\" "$(cygpath -w $LOCAL)" "$REMOTE"
[merge]
	tool = bc4
[mergetool]
	prompt = true
[mergetool "bc4"]
	#trustExitCode = true
	cmd = \"C:/Program Files/Beyond Compare 4/BComp.exe\" "$LOCAL" "$REMOTE" "$BASE" "$MERGED"
```



## 远程仓库

因为https不好用，所以推荐使用ssh方式设置远程仓库

##### 没有文件推送方式

```
echo "# spring-cloud-alibaba-test" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin git@github.com:PFyyh/spring-cloud-alibaba-test.git
git push -u origin main
```

##### 已经存在文件

```
git remote add origin git@github.com:PFyyh/spring-cloud-alibaba-test.git
git branch -M main
git push -u origin main
```

##### 设置免密登录

###### 生成公钥私钥

```shell
ssh-keygen -t rsa -C <邮箱名字>
```

然后会提示文件位置，然后一路回车。

![image-20210920182801862](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210920182801862.png)

###### 拷贝公钥

找到文件所在文件夹，打开*.pub文件。

复制出来然后粘贴到github的创建key位置。

![image-20210920182932966](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210920182932966.png)

##### 推送

```shell
git push origin <branch>
```

![image-20210920183022066](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210920183022066.png)

##### 下载代码

这一步就会默认实现git remote add origin

```shell
git clone <gitURL>
```

##### 更新代码

```shell
git pull origin <branch>
```

等同于

```shell
git fetch orgin <branch>
git merge orgin/<branch>
```

## 保持代码整洁

##### 单分支变基

```shell
git rebase -i head~<版本数量>
```

几个版本就会涉及到信息提交内容合并上面。

这种情况就需要将后面两个pick改为s,表示使用提交但是版本用前面的版本。v4->v3->v2 最后就只会保留v2版本。

![image-20210921163540968](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921163540968.png)

保存以后，就需要修改提交内容。

![](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921163714972.png)

##### 分支之间变基

这种情况需要先切换到dev上面（次级）

```shell
git checkout dev
```

然后执行

```shell
git rebase master
```

这时候就会把master的提交内容拉过来，如果有冲突就需要自己解决。

然后输入

```shell
git rebase --continue
```

这样就可以了，然后在输入合并信息，生成新版本。

这时候在切换到master分支

```shell
git checkout master
```

再把分支合并到master里面来

```shell
git merge dev
```

##### 远程和本地之间变基

如果知道远程仓库有代码，这种时候需要合并就需要注意不能使用git pull，这样的话两个版本就会直接合并，这就不是我们想要的效果。

我们知道git pull分为两个语句，一是git fetch拉到版本库，二是git merge合并版本。所以我们需要执行两步操作

###### 拉到本地版本库

```shell
git fetch origin <branch>
```

###### 版本变基

```shell
git rebase origin/<branch>
```

## 创建版本

##### 打标签

```shell
git tag -a <version> -m <message>
```

推送

```shell
git push origin --tags
```

## 代码复查

创建规则

![image-20210921203706708](C:\Users\YYH\AppData\Roaming\Typora\typora-user-images\image-20210921203706708.png)

勾选合并前需要review

![选择名字](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921203947916.png)

选择需要review且只需要一个人

![image-20210921204015117](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921204015117.png)

## 邀请

##### 搜索邀请人

![image-20210921205229481](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921205229481.png)

##### 查看邮箱

![image-20210921205314121](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921205314121.png)

##### 创建合并请求

![image-20210921210715759](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921210715759.png)

##### 发出申请

![image-20210921210433415](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921210433415.png)

##### 管理员合并

![image-20210921210503537](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921210503537.png)

![image-20210921210520157](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921210520157.png)

##### 最后合并结果

![image-20210921210551831](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921210551831.png)

## 贡献代码

1. fork别人的代码到自己的仓库
2. clone下来
3. 修改代码
4. 发起pull request请求

## 配置

##### 项目配置文件

作用范围：当前项目

```shell
git config --local user.name=""
git config --local user.email=""
```

##### 全局配置文件

作用范围：当前用户

所在位置：当前用户所在目录，如：C:\Users\\.gitconfig

```
git config --system user.name=""
git config --system user.email=""
```

##### 系统配置文件

软件安装位置，所在位置：C:\Program Files\Git

作用范围：整个电脑。

## 附录

忽略文件.gitignore，推荐idea安装插件gitignore。

[点击这里访问更多模板](https://github.com/github/gitignore)

下面是我使用的模板

```
### Java template
# Compiled class file
*.class

# Log file
*.log

# BlueJ files
*.ctxt

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files #
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# virtual machine crash logs, see http://www.java.com/en/download/help/error_hotspot.xml
hs_err_pid*

### Example user template template
### Example user template

# IntelliJ project files
.idea
*.iml
out
gen
target
```

更加完整的图

分支不太好画，偷懒一波。

![image-20210921223744326](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921223744326.png)

如果出现了中文乱码的情况
```
git config --global core.quotepath false
```

![image-20210921224446682](https://boot-generate.oss-cn-chengdu.aliyuncs.com/img/image-20210921224446682.png)

