import ajv from 'ajv';
import ajvk from 'ajv-keywords';
import { SchemaFor } from '../types/schema';

const AJV = ajvk(new ajv({ $data: true }), 'select');

export const enumVals = <E extends {}>(ee: E) => {
  let keys = Object.keys(ee);
  return keys
    .map(k => (ee as any)[k] as E[keyof E])
    .filter(v => typeof v === 'number');
};

const convertToJsonSchema7 = (schemaDef: any): object => {
  let { check, ...schemaForAJV } = schemaDef;
  if (schemaForAJV.items) {
    return convertItemToJsonSchema7({ t: 'array', ...schemaForAJV });
  }
  if (schemaForAJV.t === 'union') {
    return convertItemToJsonSchema7(schemaForAJV);
  }
  return convertItemToJsonSchema7({ t: 'object', props: schemaForAJV });
};

const convertItemToJsonSchema7 = (itemArg: any): object => {
  let item = (typeof itemArg === 'string') ? { t: itemArg } : itemArg;
  let { t, vals, discriminant, discriminantInUpLevel, schemas, ...itemRest } = item;
  let newItem;
  if (t === 'object') {
    newItem = convertObjectToJsonSchema7(item);
  } else if (t === 'union') {
    newItem = convertUnionToJsonSchema7(vals, discriminant, discriminantInUpLevel || false, schemas, item.printLabel);
  } else if (t === 'array') {
    newItem = convertArrayToJsonSchema7(item);
  } else if (t === 'enum') {
    newItem = { ...itemRest, type: 'integer', 'enum': vals };
  } else {
    if (t === 'email') {
      t = 'string';
      // itemRest.pattern = '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$';
      itemRest.format = 'email';
    }
    newItem = { ...itemRest, type: t };
  }

  return newItem;
};

const convertObjectToJsonSchema7 = (schemaDef: any): object => {
  if (schemaDef.props && schemaDef.props.t === 'union') {
    const { vals, discriminant, discriminantInUpLevel, schemas } = schemaDef.props;
    return convertUnionToJsonSchema7(vals, discriminant, discriminantInUpLevel || false, schemas, schemaDef.props.printLabel);
  }
  let res = {
    'type': 'object',
    properties: {},
    required: [] as string[],
    additionalProperties: false,
  };
  for (const key of Object.keys(schemaDef.props)) {
    let item = (schemaDef.props)[key];
    if (!item.optional) {
      res.required.push(key);
    }
    (res.properties as any)[key] = convertItemToJsonSchema7(item);
  }

  return res;
};

const convertUnionToJsonSchema7 = (vals: number[], discriminant: string, discriminantInUpLevel: boolean, schemas: any[], printLabel?: string): object => {
  let res = {
    type: 'object',
    required: discriminantInUpLevel ? [] : [discriminant],
    properties: {},
    select: { $data: discriminantInUpLevel ? `1/${discriminant}` : `0/${discriminant}` },
    selectCases: {},
    selectDefault: false,
  };
  if (!discriminantInUpLevel) {
    (res.properties as any)[discriminant] = { type: 'integer', enum: vals };
  }
  if (vals.length !== schemas.length) {
    throw new Error(`Not matching discriminant and schemas lists, label: ${printLabel}`);
  }
  for (let i = 0; i < vals.length; i++) {
    const enumVal = vals[i];
    const schema = schemas[i];
    (res.selectCases as any)[enumVal] = convertToJsonSchema7(schema);
  }
  return res;
};

const convertArrayToJsonSchema7 = (schemaDef: any): object => {
  let { t, items, ...limits } = schemaDef;
  return {
    'type': 'array',
    items: convertItemToJsonSchema7(schemaDef.items),
    ...limits,
  };
};

export const compileValidation = <T>(schema: SchemaFor<T>): (obj: any) => T => {
  if (schema.check && Object.keys(schema).length === 1) {
    return (obj: any): T => {
      schema.check!(obj);
      return obj as T;
    };
  }
  let convertSchema = convertToJsonSchema7(schema);
  let val = AJV.compile(convertSchema);
  return (obj: any): T => {
    let valRes = val(obj);
    if (!valRes) {
      throw new Error('Type validation failed');
    }
    let retVal = obj as T;
    if (schema.check) {
      schema.check(retVal);
    }

    return retVal;
  };
};

export const ensureEmpty = (x: any): {} => {
  if (x === undefined) {
    return {};
  }
  if (typeof x === 'object' && Object.getOwnPropertyNames(x).length === 0) {
    return {};
  }
  throw new Error('Bad data');
};
