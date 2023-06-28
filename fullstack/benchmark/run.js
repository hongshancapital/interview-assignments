/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-env node */

const AutoCannon = require("autocannon");
const fetch = require("node-fetch");

const common = {
  method: "POST",
  headers: { "content-type": "application/json" },
  // connections: 10,
  // pipelining: 1,
  // duration: 10,
};

// async/await
async function encode() {
  const result = await AutoCannon({
    ...common,
    url: "http://localhost:3000/encode",
    body: JSON.stringify({ url: "http://example.com" }),
  });
  console.log(AutoCannon.printResult(result));
}

async function decode() {
  const res = await fetch("http://localhost:3000/encode", {
    method: "POST",
    headers: { "content-type": "application/json" },
    body: JSON.stringify({ url: "http://www.example.com" }),
  });

  const json = await res.json();

  const result = await AutoCannon({
    ...common,
    url: "http://localhost:3000/decode",
    body: JSON.stringify({ alias: json.alias }),
  });
  console.log(AutoCannon.printResult(result));
}

switch (process.argv[2]) {
  case "encode":
    encode();
    break;
  case "decode":
    decode();
    break;
  default:
    console.log("请选择接口");
}
