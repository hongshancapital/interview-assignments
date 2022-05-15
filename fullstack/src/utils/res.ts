import { BaseRes } from '../types/type'

export const ResSuccess = <T>(data: T): BaseRes<T> => {
  return {
    code: 200,
    msg: "",
    data: data
  }
}

export const ResError = (msg: string, code = 0): {
  code: number,
  msg: string,
  data: any
} => {
  return {
    code: code,
    msg: msg,
    data: {}
  }
}