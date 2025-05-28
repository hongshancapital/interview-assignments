export interface IReqConfig {
  body?: any
  method?: string
  headers?: {}
}

export interface ICreateBody {
  url: string
}

export interface IResult<T> {
  code: number
  message: string
  data: T
}

export interface IShortRow {
  long_url: string
  short_key: string
}

export type ShortListType = IResult<[IShortRow]>

export type ShortRowType = IResult<IShortRow>

export async function request<T> (url: string, config: IReqConfig = {}): Promise<T> {
  const baseurl = 'http://localhost:3002/short'
  const defaultHeaders = {
    'Content-Type': 'application/json'
  }
  config.headers = {
    ...config.headers,
    ...defaultHeaders
  }
  try {
    const res = await fetch(`${baseurl}/${url}`, config)
    const json = await res.json()
    if (res.ok) return json
    throw new Error('Network response was not ok')
  } catch (err) {
    throw err
  }
}

export function requestAll() {
  return request<ShortListType>('all')
}

export function requestSearch(url: string) {
  return request<ShortRowType>(url)
}

export function requestCreate (url: string, body: ICreateBody) {
 return request<ShortRowType>(url, {
    method: 'POST',
    body: JSON.stringify(body)
  })  
}

