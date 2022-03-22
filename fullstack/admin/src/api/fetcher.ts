
import useSWR from 'swr'
import { AnyMap } from '@/types/common'
import { apiKey, baseApi, isFetcherLock } from './base'

export interface ResponseData<T = any> {
  statusCode: number;
  data: T;
  message: string;
}

export enum EHttpMethods {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  PATCH = 'PATCH',
  DELETE = 'DELETE',
}

export interface RequestOptions {
  headers?: HeadersInit;
  signal?: AbortSignal;
  method?: 'GET' | 'POST' | 'PATCH' | 'DELETE' | 'PUT';// EHttpMethods;
  query?: AnyMap;
  data?: AnyMap;
  body?: string;
  timeout?: number;
  credentials?: 'include' | 'same-origin';
  mode?: 'cors' | 'same-origin';
  cache?: 'no-cache' | 'default' | 'force-cache';

  fallbackData?: any // IPageConfigData[]
}

export default async function fetcher(url: string, options?: RequestOptions): Promise<ResponseData>{
  url = /^http/.test(url) ? url : `${baseApi}${url}`
  console.log('baseApi + url', url);
  const res = await fetch(url, {
    ...options,
    headers: {
      // "Authorization": `Bearer ${localStorage.getItem('accessToken')}`,
      ...(options?.headers || {}),
      "X-Api-Key": `APIKey ${apiKey}`,
      'Content-Type': 'application/json;charset=UTF-8'
    }
  })
  const { statusCode, message, data = {} }: {
    statusCode: number
    message: string
    data: any
  } = await res.json()
  return { statusCode, message, data }
}

export function useFetcher(url: string, options?: RequestOptions) {
  url = /^http/.test(url) ? url : `${baseApi}${url}`
  const { fallbackData } = options || {}
  // 服务器端渲染
  if (isFetcherLock) {
    return {
      data: fallbackData,
      error: null
    }
  }
  const { data: resuldData = {}, error } = useSWR(url, fetcher);
  const { statusCode, message, data } = resuldData as any
  return {
    data,
    code: statusCode,
    message,
    isLoading: !error && !data,
    isError: error,
  }
}