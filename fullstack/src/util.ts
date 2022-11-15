export type HTTPSuccessResult = {
  isSuccess: boolean;
  msg?: string;
  data: any;
}
export type HTTPFailureResult = {
  isSuccess: boolean;
  msg?: string;
  data?: any;
}
export function success (result: any, msg?: string): HTTPSuccessResult {
  const rs: HTTPSuccessResult = {
    isSuccess: true,
    data: result
  };
  if (msg !== undefined) {
    rs.msg = msg;
  }
  return rs;
}
export function failure (msg?: string, data?: any): HTTPFailureResult {
  const rs: HTTPFailureResult = {
    isSuccess: false
  };
  if (msg !== undefined) {
    rs.msg = msg;
  }
  if (data !== undefined) {
    rs.data = data;
  }
  return rs;
}
