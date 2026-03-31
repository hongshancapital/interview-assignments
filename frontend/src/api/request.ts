import axios from "axios";
 
declare module 'axios' {
     export interface AxiosResponse<T = any> extends Promise<T> {}
 }
 
export const Service = axios.create({
  timeout: 3000, //延迟时间
  method: "POST",
  headers: {
    "pc-token": "4a82b23dbbf3b23fd8aa291076e660ec",
    "content-Type": "application/x-www-form-urlencoded",
  },
});
 
//请求拦截
Service.interceptors.request.use((config) => config);
 
//响应拦截
Service.interceptors.response.use(
  (response) => response.data,
  (err) => console.log(err)
);
