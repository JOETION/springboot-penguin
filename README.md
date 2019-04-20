# 在线考试系统(低仿牛客网)

## 系统概要
   在线考试系统旨在为学生提供一个在线考试的平台,在这里,学生不仅可以网上在线考试,而且可以浏览题库中心下的各个课程的题库,也可以在讨论区下发表自己的想法,当然也在帖子下留言评论.

## 功能实现
#### 1. 在线考试模块
- [x] 考试倒计时、考试安排表
- [x] 答题卡、作答区
- [x] 批改完试卷后查看成绩情况以及参考答案

#### 2. 题库系统模块
- [x] 课程分类
- [x] 题目列表、题目难度
- [x] 题目描述、参考答案等
- [ ] 题目标签
- [ ] 在线编程(Online Judge)

#### 3. 论坛系统模块
- [x] 发布帖子、回帖、评论
- [x] 浏览帖子
- [x] 传送门窗口
- [ ] 点赞、浏览统计
- [x] 帖子分类
- [x] 帖子编辑、删除

#### 4. 个人中心模块
- [x] 更新个人信息、上传头像等
- [x] 考试记录
- [x] 发帖记录
- [x] 考试统计分析

#### 5. 后台管理模块
- [x] 用户管理
- [x] 考试管理
- [x] 题目管理
- [x] 课程管理
- [x] 成绩管理
- [x] 帖子管理
- [x] 评论管理
- [x] 投诉管理
- [x] 公告管理

## 技术选型
### 后台技术选型
* SpringBoot(Spring、SpringMVC)
* MyBatis
* Thymeleaf

### 前端技术选型
* Semantic UI
* Bootstrap
* jQuery

### 开发环境
* 操作系统：Windows 10
* 编程语言: Java 8
* 开发工具: IDEA、Navicat、Git
* 项目构建: Maven 3.3.9
* 服务器：Tomcat 8.5
* 数据库: MySQL、Redis
* 代码托管平台: GitHub

### 部署环境
* 操作系统: CentOS 7.4
* 编程语言: Java 8
* 服务器: Tomcat 8.5、Nginx 1.11.6
* 数据库: MySQL、Redis

## 预览效果
![首页](https://github.com/JOETION/springboot-penguin/blob/master/preview/首页.jpg)

![首页](https://github.com/JOETION/springboot-penguin/blob/master/preview/首页1.png)

![考试页](https://github.com/JOETION/springboot-penguin/blob/master/preview/考试页.png)

![考试详情页](https://github.com/JOETION/springboot-penguin/blob/master/preview/考试详情页.png)

![问题集页](https://github.com/JOETION/springboot-penguin/blob/master/preview/问题集页.jpg)

![讨论区首页](https://github.com/JOETION/springboot-penguin/blob/master/preview/讨论区首页.png)

![讨论区详情页](https://github.com/JOETION/springboot-penguin/blob/master/preview/讨论区详情页.png)

![消息页](https://github.com/JOETION/springboot-penguin/blob/master/preview/消息页.png)

![个人详情页](https://github.com/JOETION/springboot-penguin/blob/master/preview/个人详情页.png)

![个人讨论区页](https://github.com/JOETION/springboot-penguin/blob/master/preview/个人讨论区页.png)

![考试结果详情页](https://github.com/JOETION/springboot-penguin/blob/master/preview/考试结果详情页.png)

![后台管理页](https://github.com/JOETION/springboot-penguin/blob/master/preview/后台管理页.png)



## 讨论
有问题请在[issue](https://github.com/JOETION/springboot-penguin/issues)讨论

## 备注
Nginx的配置:
```Nginx

       server {
               listen       80;
               server_name  localhost;
       
               #去掉项目端口
               location / {
                    proxy_pass         http://localhost:8080;
                    proxy_set_header   Host             $host;
                    proxy_set_header   X-Real-IP        $remote_addr;
                    proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
               }
       		
       		   #用于后台投诉管理云检测
       		   location /complaint/cloudCheck {
       		   proxy_pass http://www.jinyongci.com/search.php;
       		   }
       		
       		   #root属性就是访问路径的前缀,autoindex自动将资源生成索引
       		   location /upload/images/ {
       		    root E:/Company/springboot-penguin/;
       			autoindex on;
       		   }
       		   client_max_body_size 20m; #允许最大上传文件大小,默认情况下，该指令值为1m。
       		}
       		
```