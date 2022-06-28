import axios from 'axios';

type Response = {data:string, status:number};

export async function get(url: string):Promise<Response>{
    return await axios.get(url);
}

export async function post(url: string, requestData: any):Promise<Response> {
    return await axios.post(url, requestData);
}




