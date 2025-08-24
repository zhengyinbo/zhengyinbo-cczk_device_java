# springBoot项目
## 基于shiro的角色权限管理


### 依赖

```xml
 <dependency>
  <groupId>org.apache.shiro</groupId>
   <artifactId>shiro-spring</artifactId>
   <version>1.4.0</version>
 </dependency>
```

### 数据库设计

| 数据表 | 说明 |
| --- | --- |
| user | 用户表 |
| role | 角色表 |
| permission | 权限表 |
| user_role | 用户-角色表 |
| role_permission | 角色-权限表 |

--------------------------------------------------------------------------------

## 项目说明

### 项目启动（首次）
* 自动创建管理员账号及相关权限
* 自动创建表结构
* 搭建mqtt服务器
* 

### Mqtt - 通信技术
* topic 发布/订阅
* mqtt 是逻辑 topic，自动生效（任何客户端只要 订阅/发布 某个topic，它就生效了）
* 每个机器订阅专属的 topic（/topic/deviceNo/cmd）
* 


