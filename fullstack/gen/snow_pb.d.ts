// package: snow
// file: snow.proto

/* tslint:disable */
/* eslint-disable */

import * as jspb from "google-protobuf";

export class SnowRequest extends jspb.Message { 
    getName(): string;
    setName(value: string): SnowRequest;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): SnowRequest.AsObject;
    static toObject(includeInstance: boolean, msg: SnowRequest): SnowRequest.AsObject;
    static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
    static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
    static serializeBinaryToWriter(message: SnowRequest, writer: jspb.BinaryWriter): void;
    static deserializeBinary(bytes: Uint8Array): SnowRequest;
    static deserializeBinaryFromReader(message: SnowRequest, reader: jspb.BinaryReader): SnowRequest;
}

export namespace SnowRequest {
    export type AsObject = {
        name: string,
    }
}

export class SnowReply extends jspb.Message { 
    getMessage(): string;
    setMessage(value: string): SnowReply;

    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): SnowReply.AsObject;
    static toObject(includeInstance: boolean, msg: SnowReply): SnowReply.AsObject;
    static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
    static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
    static serializeBinaryToWriter(message: SnowReply, writer: jspb.BinaryWriter): void;
    static deserializeBinary(bytes: Uint8Array): SnowReply;
    static deserializeBinaryFromReader(message: SnowReply, reader: jspb.BinaryReader): SnowReply;
}

export namespace SnowReply {
    export type AsObject = {
        message: string,
    }
}
