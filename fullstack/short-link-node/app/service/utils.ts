import { ServiceErrorInterface, ServiceReturnInterface } from "../interface"

function genError(code: string, msg?: string, e?: string): ServiceErrorInterface {
  return { code, msg, e }
}
function genServiceError(code: string, msg?: string, e?: string): ServiceReturnInterface<any> {
  return {
    error: { code, msg, e } as ServiceErrorInterface
  }
}
function genServiceReturn<T>(data: T): ServiceReturnInterface<T> {
  return { data: data }
}

export { genServiceError, genServiceReturn }
