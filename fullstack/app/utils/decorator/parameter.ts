import { Application, BaseContextClass, Context, EggAppConfig } from 'egg';
import Parameter = require('parameter');
import { ParamValidateError } from '../error';

interface IDecoratorValue {
  ctx: Context,
  app: Application,
  config: EggAppConfig,
}

const parameter = new Parameter({
  // 将原始参数转换为特定类型
  convert: true,
  // 默认值必须是 object
  validateRoot: true,
});

/**
 * 根据类型获取原始数据
 * @param type 类型
 * @param ctx Context
 * @return {unknown} params
 */
function getData(type: string, ctx: Context): unknown {
  switch (type) {
    case 'query':
      return ctx.query;
    case 'body':
      return ctx.request.body;
    case 'params':
      return ctx.params;
    case 'header':
      return ctx.header;
    case 'headers':
      return ctx.header;
    default:
      return ctx[type] || {};
  }
}

/**
 * 根据规则获取数据
 * @param rule 规则
 * @param data 数据
 * @return {any} params 参数
 */
function getParams(rule: Parameter.ParameterRules, data: any) {
  const params: { [key: string]: number } = {};
  const keys = Object.keys(rule);
  keys.forEach(key => {
    params[key] = data[key];
  });
  return params;
}

/**
 * 参数校验装饰器
 * @param type 参数类型
 * @param rule 规则
 * @return {any} any
 */
function validate(type: string, rule: object): any {
  return function decorators(_target: BaseContextClass, _propertyKey: string, descriptor: PropertyDescriptor) {
    const oldValue = descriptor.value;

    function validateParams(data: unknown) {
      const error: Parameter.ValidateError[] | void = parameter.validate(rule as Parameter.ParameterRules, data);
      // 如果报错则直接写 throw 出去
      if (error) {
        const errorKeys = error.map(err => err.field);
        throw new ParamValidateError(`${errorKeys.join(',')} 参数错误`);
      }
      return getParams(rule as Parameter.ParameterRules, data);
    }

    descriptor.value = function httpValue() {
      const { ctx } = this as IDecoratorValue;
      const data: unknown = getData(type, ctx);
      ctx.params = validateParams(data);
      return oldValue.apply(this, [ctx]);
    };
    return descriptor;
  };
}

function addRule(type: string, check: Parameter.ParameterCheckFunction<any> | RegExp, override?: boolean, convertType?: Parameter.ParameterConvertType) {
  parameter.addRule(type, check, override, convertType);
}

function stringToIntsCheck(_rule: never, value: number[]) {
  if (!Array.isArray(value)) {
    return false;
  }
}
const rules = [{
  type: 'stringToInts',
  check: stringToIntsCheck as Parameter.ParameterCheckFunction<any>,
  convertType: (value: string) => {
    const ints = value.split(',');
    return ints.map(item => Number(item));
  },
}];

rules.forEach(rule => {
  addRule(rule.type, rule.check, true, rule.convertType);
});

export {
  validate,
  addRule,
};

