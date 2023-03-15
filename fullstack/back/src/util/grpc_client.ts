import { GreeterClient } from "../../../gen/snow_grpc_pb";
import messages, { SnowReply } from "../../../gen/snow_pb"; 
import * as grpc from "@grpc/grpc-js";

// 初始化 Client
const client = new GreeterClient(
  "localhost:50551",
  grpc.credentials.createInsecure()
);


// 发送请求 
export const callSnow = (url: string)=> {
  return new Promise<SnowReply>((resolve, reject) => {
    // 使用 message 初始化参数
    const request = new messages.SnowRequest();
    request.setName(url);

    client.callSnow(request, (err, response) => {
      if (err) {
        reject(err);
      } else {
        resolve(response as SnowReply);
      }
    });
  });
}

// async function main() {
//   // 传入长链接
//   const res = await callSnow("")
//   console.log(res)
// }

// main();
