import { server } from "./app";
// 默认监听端口
const Const_Listen_Port = process.env.PORT || 3000;

server.listen(Const_Listen_Port, () => {
  console.log(`Server listening on port: ${Const_Listen_Port}`);
});
