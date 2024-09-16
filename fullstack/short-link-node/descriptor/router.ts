import Joi, { Schema, ValidationResult } from "joi"
import RouteError from "../routes/common/RouteError"
import { CodeStepEnum } from "../utils/errorCode"
function ReqValidate(joiScheme: any) {
  return function (target: any, key: any, descriptor: PropertyDescriptor) {
    const func = descriptor.value
    descriptor.value = (...args: any[]) => {
      const vr: ValidationResult = Joi.object(joiScheme).validate(args[0] || {})
      if (vr.error) {
        throw new RouteError(CodeStepEnum.ValidityParams, vr.error.message, vr.error)
      } else {
        const result = func.apply(target, args)
        return result
      }
    }
    return descriptor
  }
}

function Auth() {
  return function (target: any, key: any, descriptor: PropertyDescriptor) {
    const func = descriptor.value
    descriptor.value = (...args: any[]) => {
      const result = func.apply(target, args)
      return result
      // TODO 登录校验
    }
    return descriptor
  }
}

function Log() {
  return function (target: any, key: any, descriptor: PropertyDescriptor) {
    const func = descriptor.value
    descriptor.value = (...args: any[]) => {
      console.log("xxxxxxLog", args)
      const result = func.apply(target, args)
      return result
      // TODO 登录校验
    }
    return descriptor
  }
}
export { ReqValidate, Auth, Log }
