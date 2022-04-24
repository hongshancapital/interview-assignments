### 服务端：server/

aws-dynamicdb redis(可选) node

默认启动端口：3003

启动方式：

.env 配置 redis 地址

npm run dev

schame
{id[主键],code,longUrl,createdAt}

其中 code 与 id 相同，由 nanoid 生成，nanoid 重复问题未考虑

第一次访问短链接会触发缓存，默认时间 60 分

接口:

/api/generate [post]

/api/:code [get]

/:code [redirect]

### 客户端: client/

由 create-react-app 生成

npm start

输入 URL，点击生成，点击生成的短链接
