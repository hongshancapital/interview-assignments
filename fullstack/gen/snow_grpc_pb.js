// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('@grpc/grpc-js');
var snow_pb = require('./snow_pb.js');

function serialize_snow_SnowReply(arg) {
  if (!(arg instanceof snow_pb.SnowReply)) {
    throw new Error('Expected argument of type snow.SnowReply');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_snow_SnowReply(buffer_arg) {
  return snow_pb.SnowReply.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_snow_SnowRequest(arg) {
  if (!(arg instanceof snow_pb.SnowRequest)) {
    throw new Error('Expected argument of type snow.SnowRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_snow_SnowRequest(buffer_arg) {
  return snow_pb.SnowRequest.deserializeBinary(new Uint8Array(buffer_arg));
}


var GreeterService = exports.GreeterService = {
  callSnow: {
    path: '/snow.Greeter/CallSnow',
    requestStream: false,
    responseStream: false,
    requestType: snow_pb.SnowRequest,
    responseType: snow_pb.SnowReply,
    requestSerialize: serialize_snow_SnowRequest,
    requestDeserialize: deserialize_snow_SnowRequest,
    responseSerialize: serialize_snow_SnowReply,
    responseDeserialize: deserialize_snow_SnowReply,
  },
};

exports.GreeterClient = grpc.makeGenericClientConstructor(GreeterService);
