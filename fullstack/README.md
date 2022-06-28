## 简历

详见简历.pdf

## 架构设计图

### 技术方案
- TypeScript + TypeORM / Sqlite3 (非工作电脑，方便快速安装调试，在 nedb 和 sqlite 间选择了相对主流的 sqlite)
- Mocha + Chai

### 设计分层
- 参考 Rails （或者 egg.js）做了 路由表、控制层和模型层的划分
- 个人倾向不是复杂业务逻辑不会使用 Service 层，所以这里业务逻辑也在控制层消化掉了
- 模型层的引入是为了方便理解库表设计，因为单表没必要画实体关系图，所以靠 ORM 注释说明了

### API 流程图
见谅，因为用的非开发机，所以没有装专业的绘图软件，这里用 flowchart.js 表示接口流程

#### 读请求
```flow

st=>start: 开始
op=>operation: 获取短链 Code
e=>end: 结束
isExistModel=>condition: 查询是否存在
404=>operation: 返回 404 不存在的请求
build=>operation: 根据 origin 和 path 构造 link
data=>operation: 返回数据

st->op->isExistModel
isExistModel(yes)->build->data->e
isExistModel(no)->404->e
```

#### 写请求
```flow
st=>start: 开始
op=>operation: 响应请求
e=>end: 结束
isValidUri=>condition: 是否为合法 URI
isExistModel=>condition: 是否已经落库
401=>operation: 返回 401 无效请求
save=>operation: 持久化数据
isProduced=>condition: 数据保存成功
501=>operation: 抛出内部服务异常
build=>operation: 根据 origin 和 short code 构造短链
data=>operation: 返回数据

st->op->isValidUri
isValidUri(no)->401->e
isValidUri(yes)->isExistModel
isExistModel(yes)->build->data->e
isExistModel(no)->save->isProduced
isProduced(yes)->build->data->e
isProduced(no)->501->e
```

## 简历补充
- PHP、Ruby、Java、Node 服务端开发、部署经验（低 QPS、内部和初创产品）
 - 所以没有分布式、分库表经验
- 有产品、视觉、交互设计经验，数据统计分析有所了解
- 负责过性能、效率相关工作
- 有项目管理经验
- 有技术敏感度，学习能力强
- mac 环境下开发效率提升有独到见解

