import axios from 'axios';
import { baseUrl } from './env';

export const responseHandler = function (response, options) { // 公共响应码集中处理
	options.callback(response);
};

export const fetch = async (url = '', options = {}, type = 'GET', responseType = 'json') => {
	await axios.request({
		url: url,
		baseURL: baseUrl,
		method: type.toLowerCase(),
		params: options.reqParams || {}, // 业务params 请求参数
		data: options.reqBody || {},
		responseType: responseType
	}).then((response) => {
		console.log('====== 返回信息 ======');
		console.log('url：' + url);
		console.log('返回数据：');
		console.log(response.data);
		if (responseType === 'json') {
			responseHandler(response.data, options);
		} else {
			options.callback(response.data);
		}
	}).catch(function (error) {
		errorHandler(url , options,  error);
	});
};

const errorHandler =  (url , options,  error) => {
	console.log('====== 请求出错 ======');
	console.log('url：' + url);
	console.log('错误信息：' + error);
};
