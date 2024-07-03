// server.ts
import * as grpc from "@grpc/grpc-js";
import { IGreeterServer, GreeterService } from "../gen/snow_grpc_pb";
import messages from "../gen/snow_pb"; 
import SnowFlake from './util/snowflake';

// 回调接口
const greeterServiceImpl: IGreeterServer = {   
    callSnow: (call, callback) => {
        const { request } = call;
        const name = request.getName();            // 获取请求源
        const response = new messages.SnowReply();
        let snowflake = new SnowFlake(1n, 1n)
        response.setMessage(snowflake.nextId().toString());
        callback(null, response);
    }
} 
 
// 提供编码服务
function main() {
  const server = new grpc.Server();
  server.addService(GreeterService, greeterServiceImpl);
  server.bindAsync(
    "0.0.0.0:50551",
    grpc.ServerCredentials.createInsecure(),
    () => {
      server.start();
      console.log("grpc server started");
    }
  );
}

main();
