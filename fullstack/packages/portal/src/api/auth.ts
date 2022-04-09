import Router from 'next/router';

import fetcher, { ResponseData } from './fetcher';

export const loginApi = `/login`;

export const checkBooleanStatus = (statusCode: number) => {
  if (statusCode >= 200 && statusCode < 300) {
    return true
  }
  
  if (statusCode < 200 && statusCode >= 300) {
    return false
  }

  return false
}

const setLoginData = (data: {
  username: string
  accessToken: string
}) => {
  if (typeof window !== 'undefined' && !!data.accessToken) {
    window.localStorage.setItem('username', data.username);
    window.localStorage.setItem('accessToken', data.accessToken);
  }
}

export const login = async (datas: {
  phone: string
  code?: string
  password?: string
}) => {
  const res: ResponseData = await fetcher(loginApi, {
    method: "POST",
    body: JSON.stringify(datas)
  });
  setLoginData(res.data)
  
  if (checkBooleanStatus(res.statusCode)) {
    window.location.href = String(Router.query.callback || '/')
  }
  return res
}
