// 枚举状态码 根据自己需要定义
enum Code {
  success = 3000,
  error,
}

enum CodeMessage {
  success = '请求成功',
  error = '系统异常',
}

type codeType = keyof typeof Code

export { Code, codeType, CodeMessage }
