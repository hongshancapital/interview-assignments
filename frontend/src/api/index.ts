import {Service} from './request';



// 配置请求
export function getCarList(config: { page: string; }){
    const params = new URLSearchParams()
    params.append('page',config.page);
 
    return Service({
        url:'./api/***',
        data:params
    })
}