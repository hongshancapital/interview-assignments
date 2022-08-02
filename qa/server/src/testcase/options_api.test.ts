import axios from "axios";

const url: string = 'http://127.0.0.1:5000';
const optionsApi: string = "/toolbox/options"

test('/toolbox/options 场景1  ', async () => {
    const response = await axios.get(url + optionsApi);
    expect(response["data"]).toBeTruthy();
  });