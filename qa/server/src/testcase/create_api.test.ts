import axios from "axios";

const url: string = 'http://127.0.0.1:5000';
const createApi: string = "/toolbox/create"



//健壮性校验

//入参非空校验
test('/toolbox/create 场景1: name字段为空 ', async () => {
  const response = await axios.post(url + createApi, {
      "name": "", 
      "tools": "Headless Chrome"
  });
  console.log(response);
  expect(response.status).toBe(400);
});


test('/toolbox/create 场景2: tools字段为空 ', async () => {
  const response = await axios.post(url + createApi, {
      "name": "test", 
      "tools": ""
  });
  expect(response.status).toBe(400);
});

//入参参数值为NULL
test('/toolbox/create 场景3: name字段为null ', async () => {
  const response = await axios.post(url + createApi, {
      "name": null, 
      "tools": "Headless Chrome"
  });
  expect(response.status).toBe(400);
});

test('/toolbox/create 场景4: tools字段为null ', async () => {
  const response = await axios.post(url + createApi, {
      "name": "test", 
      "tools": null
  });
  expect(response.status).toBe(400);
});

//入参缺失
test('/toolbox/create 场景5: name字段缺失 ', async () => {
  const response = await axios.post(url + createApi, {
      "tools": "Headless Chrome"
  });
  expect(response.status).toBe(400);
});

test('/toolbox/create 场景6: tools字段缺失 ', async () => {
  const response = await axios.post(url + createApi, {
      "name": "test"
  });
  expect(response.status).toBe(400);
});

test('/toolbox/create 场景7: name、tools字段同时缺失 ', async () => {
  const response = await axios.post(url + createApi, {
      "name": "test"
  });
  expect(response.status).toBe(400);
});

//因sqlite表中name与tools两个字段均为text型，且最大长度支持到2^31-1(2,147,483,647)个字符，只用等价类法对一个字段进行字符类型娇艳，不进行最大长度验证
test('/toolbox/create 场景8: name字段为int型 ', async () => {
  const response = await axios.post(url + createApi, {
      "name": Math.round(Math.random() * 100),
      "tools":"Headless Chrome"
  });
  expect(response.status).toBe(200);
});


//tools字段传入非枚举范围内参数
test('/toolbox/create 场景9:tools字段传入非枚举范围内参数 ', async () => {
  const response = await axios.post(url + createApi, {
    "name": "test"+Math.round(Math.random() * 100),  
    "tools": "Chrome"
  });
  expect(response.status).toBe(400);
});

//特殊字符
//常用分隔符
test('/toolbox/create 场景10:name字段传入分隔符 ', async () => {
  const response = await axios.post(url + createApi, {
    "name": "test｜test2｜test3"+Math.round(Math.random() * 100),  
    "tools": "Chrome"
  });
  expect(response.status).toBe(200);
});

//数据库相关特殊字符
test('/toolbox/create 场景11:name字段传入常用数据库字符', async () => {
  const response = await axios.post(url + createApi, {
    "name": +Math.round(Math.random() * 100)+"test%_",  
    "tools": "Chrome"
  });
  expect(response.status).toBe(200);
});

//空格
test('/toolbox/create 场景12:name字段传入带空格的字符串', async () => {
  const response = await axios.post(url + createApi, {
    "name": "test test2"+Math.round(Math.random() * 100),  
    "tools": "Chrome"
  });
  expect(response.status).toBe(200);
});

//正向流程，添加全新数据
test('/toolbox/create 场景13: 添加全新数据 ', async () => {
    const response = await axios.post(url + createApi, {
        "name": "test"+Math.round(Math.random() * 100), 
        "tools": "Headless Chrome"
    });
    expect(response.status).toBe(200);
  });

//异常流程
  test('/toolbox/create 场景14: 添加已存在的数据', async () => {
    const response = await axios.post(url + createApi, {
        "name": "吴先生", 
        "tools": "Headless Chrome"
    });
    expect(response.status).toBe(400);
  });

  //sql注入