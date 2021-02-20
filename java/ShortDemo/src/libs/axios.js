// import axios from 'axios'
// axios.defaults.baseURL = 'http://localhost:80';
// axios.defaults.timeout = 5000;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';        //配置请求头
// //POST传参序列化(添加请求拦截器)
// let base = '/api';
// //返回状态判断(添加响应拦截器)
// axios.interceptors.request.use((config) => {
//   if (config.method === 'get') {
//     // config.data = qs.stringify(config.data);
//     config.data = JSON.stringify(config.data);
//   }
//   return config;
// },(error) =>{
//   return Promise.reject(error);
// });
//
// //返回状态判断
// axios.interceptors.response.use((res) =>{
//   if(!res.data.success){	//这里根据接口返回的实际情况来写
//     //这里是接口异常，可以统一弹窗提示错误信息
//     //ElementUI.Message.error(res.data.message);
//     return Promise.reject(res);
//   }
//   return res;
// }, (error) => {
//   //404等问题可以在这里处理
//   return Promise.reject(error);
// });
//
// export function post(api, params) {
//   return new Promise((resolve, reject) => {
//     params = JSON.stringify(params);
//     axios.post(api, params)
//       .then((response) => {
//         resolve(response.data);
//       }, (err) => {
//         reject(err.data);
//       })
//       .catch((error) => {
//         reject(error);
//       });
//   });
// }
//
// export function get(api, data) {
//   return new Promise((resolve, reject) => {
//     axios.get(api, { params: data })
//       .then((response) => {
//         resolve(response.data);
//       }, (err) => {
//         reject(err);
//       })
//       .catch((error) => {
//         reject(error);
//       });
//   });
// }
