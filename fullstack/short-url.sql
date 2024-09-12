/*
 Navicat Premium Data Transfer

 Source Server         : 1.116.229.2
 Source Server Type    : MongoDB
 Source Server Version : 50011
 Source Host           : 1.116.229.2:27017
 Source Schema         : short-url

 Target Server Type    : MongoDB
 Target Server Version : 50011
 File Encoding         : 65001

 Date: 26/08/2022 09:19:40
*/


// ----------------------------
// Collection structure for urls
// ----------------------------
db.getCollection("urls").drop();
db.createCollection("urls");
db.getCollection("urls").createIndex({
    "short_url": NumberInt("1")
}, {
    name: "short_url_1",
    background: true
});
db.getCollection("urls").createIndex({
    "long_url": NumberInt("1")
}, {
    name: "long_url_1",
    background: true
});
db.getCollection("urls").createIndex({
    "hash_url": NumberInt("1")
}, {
    name: "hash_url_1",
    background: true
});

// ----------------------------
// Documents of urls
// ----------------------------
db.getCollection("urls").insert([ {
    _id: ObjectId("63071ccfeb1dfe6c5a34ec0c"),
    "short_url": "fbGTtQf6",
    "long_url": "a",
    "hash_url": "",
    "create_time": ISODate("2022-08-25T06:55:11.127Z"),
    "update_time": ISODate("2022-08-25T06:55:11.127Z"),
    __v: NumberInt("0")
} ]);
db.getCollection("urls").insert([ {
    _id: ObjectId("63071d14220204dd32506818"),
    "short_url": "fgNV9lgA",
    "long_url": "aa",
    "hash_url": "",
    "create_time": ISODate("2022-08-25T06:56:20.374Z"),
    "update_time": ISODate("2022-08-25T06:56:20.374Z"),
    __v: NumberInt("0")
} ]);
db.getCollection("urls").insert([ {
    _id: ObjectId("63072105894796c6543016c8"),
    "short_url": "gtk4Tz8Y",
    "long_url": "11a",
    "hash_url": "",
    "create_time": ISODate("2022-08-25T07:13:09.522Z"),
    "update_time": ISODate("2022-08-25T07:13:09.522Z"),
    __v: NumberInt("0")
} ]);
db.getCollection("urls").insert([ {
    _id: ObjectId("630721620b35c83b882060db"),
    "short_url": "gAdOposw",
    "long_url": "https://mp.weixin.qq.com/s__biz=MzA5NDI5NDI3Nw==&mid=2649497809&idx=2&sn=381d02312b0c49e9cc406436f963efc5&chksm=884858bbbf3fd1ad6f431e94ca9dd0543d817a3856bf857807cb088dda9b9aa855720d4177eb&mpshare=1&scene=23&srcid=1012SKaGEy75jnX0omALmfx1#rd ",
    "hash_url": "ba824add54b038305b0b36418d7f84f8",
    "create_time": ISODate("2022-08-25T07:14:42.95Z"),
    "update_time": ISODate("2022-08-25T07:14:42.95Z"),
    __v: NumberInt("0")
} ]);
db.getCollection("urls").insert([ {
    _id: ObjectId("6307285890e5e43a826e54ca"),
    "short_url": "iHK6Cbni",
    "long_url": "https://mp.weixin.qq.com/s__biz=MzA5NDI5NDI3Nw==&mid=2649497809&idx=2&sn=381d02312b0c49e9cc406436f963efc5&chksm=884858bbbf3fd1ad6f431e94ca9dd0543d817a3856bf857807cb088dda9b9aa855720d4177eb&mpshare=1&scene=23&srcid=1012SKaGEy75jnX0omALmfx1#rd",
    "hash_url": "2998576f98b27be02099522ec1c490f4",
    "create_time": ISODate("2022-08-25T07:44:24.035Z"),
    "update_time": ISODate("2022-08-25T07:44:24.035Z"),
    __v: NumberInt("0")
} ]);
db.getCollection("urls").insert([ {
    _id: ObjectId("630733aae8eabd096a7b6e17"),
    "short_url": "m9Og2zGE",
    "long_url": "https://mp.weixin.qq.com/s__biz=MzA5NDI5NDI3Nw==&mid=2649497809&idx=2&sn=381d02312b0c49e9cc406436f963efc5&chksm=884858bbbf3fd1ad6f431e94ca9dd0543d817a3856bf857807cb088dda9b9aa855720d4177eb&mpshare=1&scene=23&",
    "hash_url": "234a5fd78d19333bd475a35d225df670",
    "create_time": ISODate("2022-08-25T08:32:42.991Z"),
    "update_time": ISODate("2022-08-25T08:32:42.991Z"),
    __v: NumberInt("0")
} ]);
db.getCollection("urls").insert([ {
    _id: ObjectId("63075c6916f3176569dc6b4b"),
    "short_url": "yA1h8e4w",
    "long_url": "http::/www.huang/iHK6CbniHK6CbniHK6CbniHK6CbniHK6Cbn",
    "hash_url": "d56ac261e301391849e482ddc8b2341f",
    "create_time": ISODate("2022-08-25T11:26:33.341Z"),
    "update_time": ISODate("2022-08-25T11:26:33.342Z"),
    __v: NumberInt("0")
} ]);
