// package: snow
// file: snow.proto

/* tslint:disable */
/* eslint-disable */

import * as grpc from "@grpc/grpc-js";
import * as snow_pb from "./snow_pb";

interface IGreeterService extends grpc.ServiceDefinition<grpc.UntypedServiceImplementation> {
    callSnow: IGreeterService_ICallSnow;
}

interface IGreeterService_ICallSnow extends grpc.MethodDefinition<snow_pb.SnowRequest, snow_pb.SnowReply> {
    path: "/snow.Greeter/CallSnow";
    requestStream: false;
    responseStream: false;
    requestSerialize: grpc.serialize<snow_pb.SnowRequest>;
    requestDeserialize: grpc.deserialize<snow_pb.SnowRequest>;
    responseSerialize: grpc.serialize<snow_pb.SnowReply>;
    responseDeserialize: grpc.deserialize<snow_pb.SnowReply>;
}

export const GreeterService: IGreeterService;

export interface IGreeterServer extends grpc.UntypedServiceImplementation {
    callSnow: grpc.handleUnaryCall<snow_pb.SnowRequest, snow_pb.SnowReply>;
}

export interface IGreeterClient {
    callSnow(request: snow_pb.SnowRequest, callback: (error: grpc.ServiceError | null, response: snow_pb.SnowReply) => void): grpc.ClientUnaryCall;
    callSnow(request: snow_pb.SnowRequest, metadata: grpc.Metadata, callback: (error: grpc.ServiceError | null, response: snow_pb.SnowReply) => void): grpc.ClientUnaryCall;
    callSnow(request: snow_pb.SnowRequest, metadata: grpc.Metadata, options: Partial<grpc.CallOptions>, callback: (error: grpc.ServiceError | null, response: snow_pb.SnowReply) => void): grpc.ClientUnaryCall;
}

export class GreeterClient extends grpc.Client implements IGreeterClient {
    constructor(address: string, credentials: grpc.ChannelCredentials, options?: Partial<grpc.ClientOptions>);
    public callSnow(request: snow_pb.SnowRequest, callback: (error: grpc.ServiceError | null, response: snow_pb.SnowReply) => void): grpc.ClientUnaryCall;
    public callSnow(request: snow_pb.SnowRequest, metadata: grpc.Metadata, callback: (error: grpc.ServiceError | null, response: snow_pb.SnowReply) => void): grpc.ClientUnaryCall;
    public callSnow(request: snow_pb.SnowRequest, metadata: grpc.Metadata, options: Partial<grpc.CallOptions>, callback: (error: grpc.ServiceError | null, response: snow_pb.SnowReply) => void): grpc.ClientUnaryCall;
}
