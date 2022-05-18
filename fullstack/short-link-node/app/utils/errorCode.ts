// 模块
const CodeModulesEnum = {
  service: "10",
  router: "20",
  mysql: "30"
}
type CodeModules = keyof typeof CodeModulesEnum

// 功能
const CodeFunctionEnum = {
  addShortLine: "10",
  updateShortLine: "11",
  queryShortLine: "12",
  redirectUrl: "20"
}

// 过程错误
const CodeStepEnum = {
  ValidityParams: "10"
}

type CodeFunction = keyof typeof CodeFunctionEnum

export { CodeModulesEnum, CodeFunctionEnum, CodeStepEnum }

export default function genErrorCode(module: CodeModules) {
  const moduleCode = CodeModulesEnum[module]
  return function (f: CodeFunction) {
    const fCode = CodeFunctionEnum[f]
    return moduleCode + fCode
  }
}
