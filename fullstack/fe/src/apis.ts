import axios from "axios";

axios.interceptors.response.use((res) => {
  if (res.data.code) {
    window.alert(res.data.msg);
    throw new Error(res.data.msg);
  }
  return res;
});

type Res<T> = {
  code: number;
  data: T;
  msg: string;
};

export function long_to_short(data: { long: string }) {
  return axios.post<Res<{ short: string; long: string }>>(
    "/api/long_to_short",
    data
  );
}
