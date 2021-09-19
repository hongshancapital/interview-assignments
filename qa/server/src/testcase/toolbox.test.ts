import axios from 'axios';

const url: string = 'http://127.0.0.1:5000';
const toolboxApi: string = "/toolbox";


test('/toolbox 场景1 ', async () => {
    const response = await axios.get(url + toolboxApi);
    console.log(response["data"]);
    expect(response["data"]['message']).toBe('success');
  });