import axios from "axios";

interface shrinkBody {
  fullUrl: string;
}

interface IStatusMessage {
  [key: number]: string;
}

axios.interceptors.response.use(
  (res) => res,
  (error: any) => {
    if (error.response) {
      let statusMessage: IStatusMessage = {
        400: "请求参数错误",
        500: "服务器端出错",
      };
      error.message =
        statusMessage[error.response.status] ||
        `连接错误${error.response.status}`;
    }
    return Promise.reject(error);
  }
);

export const shrinkUrl = (body: shrinkBody) => {
  return axios.post("http://localhost:5000/w/shrink", body)
  .then(res => res.data)
};

export const searchShrinkUrl = (shortUrl: string) => {
  return axios.get("http://localhost:5000/r/search", {
    params: {
      shortUrl,
    },
  })
  .then(res => res.data);
};
