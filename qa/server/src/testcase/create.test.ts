import axios from "axios";

const url: string = 'http://127.0.0.1:5000';
const createApi: string = "/toolbox/create"


test('/toolbox/create 场景1: 添加不存在的数据 ', async () => {
    const response = await axios.post(url + createApi, {
        "name": "string1", 
        "tools": "string1"
    });
    //console.log(response);
    expect(response.status).toBe(200);
  });


  test('/toolbox/create 场景2: 添加已存在的数据', async () => {
    const response = await axios.post(url + createApi, {
        "name": "string1", 
        "tools": "string1"
    });
    // console.log(response);
    expect(response.status).toBe(400);
  });

  test('/toolbox/create 场景3: 添加空值', async () => {
    const response = await axios.post(url + createApi, {
        "name": "", 
        "tools": ""
    });
    // console.log(response);
    expect(response.status).toBe(400);
  });

  test('/toolbox/create 场景4: 添加非string类型数值', async () => {
    const response = await axios.post(url + createApi, {
        "name": 222, 
        "tools": 34.55
    });
    // console.log(response);
    expect(response.status).toBe(200);
  });

  test('/toolbox/create 场景5: 添加异常值，如null， none等', async () => {
    const response = await axios.post(url + createApi, {
        "name": null, 
        "tools": null
    });
    // console.log(response);
    expect(response.status).toBe(200);
  });

  test('/toolbox/create 场景6: 添加string型的异常值，如\"null\"， \"none\"等', async () => {
    const response = await axios.post(url + createApi, {
        "name": "null", 
        "tools": "null"
    });
    // console.log(response);
    expect(response.status).toBe(200);
  });

  test('/toolbox/create 场景7: 某个参数丢失，如tools', async () => {
    const response = await axios.post(url + createApi, {
        "name": null
    });
    // console.log(response);
    expect(response.status).toBe(400);
  });

  test('/toolbox/create 场景8: SQL注入攻击，删除数据', async () => {
    const response = await axios.post(url + createApi, {
        "name": "string ",
        "tools": "string' DELETE FROM toolbox_prefs"
    });
    // console.log(response);
    expect(response.status).toBe(200);
  });

  test('/toolbox/create 场景9: SQL注入攻击，添加注释', async () => {
    const response = await axios.post(url + createApi, {
        "name": "'/* string  ",
        "tools": "string */ "
    });
    // console.log(response);
    expect(response.status).toBe(200);
  });

  test('/toolbox/create 场景10: 超长值', async () => {
    const response = await axios.post(url + createApi, {
        "name": "string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string  ",
        "tools": "string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string string  "
    });
    // console.log(response);
    expect(response.status).toBe(200);
  });