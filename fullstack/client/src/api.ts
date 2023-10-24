import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://localhost:8080',
})

export type ShortResponse = { 
  success: boolean, 
  message: string, 
  data?: { short: string }
}
export type LongResponse = { 
  success: boolean, 
  message: string, 
  data?: { url: string }
}

export async function long2short(url: string): Promise<ShortResponse> {
  return instance.get('/long2short', { params: { url } })
    .then(resp => {
      return resp.data as ShortResponse
    })
    .catch(error => {
      return {
        success: false,
        message: error.message as string
      }
    })
}

export async function short2long(short: string): Promise<LongResponse> {
  if (short.startsWith('http://st.com/')) {
    short = short.slice('http://st.com/'.length)
  }
  return instance.get('/short2long', { params: { short } })
    .then(resp => {
      return resp.data as LongResponse
    })
    .catch(error => {
      return {
        success: false,
        message: error.message as string
      }
    })
}