"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
var __rest = (this && this.__rest) || function (s, e) {
    var t = {};
    for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p) && e.indexOf(p) < 0)
        t[p] = s[p];
    if (s != null && typeof Object.getOwnPropertySymbols === "function")
        for (var i = 0, p = Object.getOwnPropertySymbols(s); i < p.length; i++) {
            if (e.indexOf(p[i]) < 0 && Object.prototype.propertyIsEnumerable.call(s, p[i]))
                t[p[i]] = s[p[i]];
        }
    return t;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ensureEmpty = exports.compileValidation = exports.enumVals = void 0;
var ajv_1 = __importDefault(require("ajv"));
var ajv_keywords_1 = __importDefault(require("ajv-keywords"));
var AJV = (0, ajv_keywords_1.default)(new ajv_1.default({ $data: true }), 'select');
var enumVals = function (ee) {
    var keys = Object.keys(ee);
    return keys
        .map(function (k) { return ee[k]; })
        .filter(function (v) { return typeof v === 'number'; });
};
exports.enumVals = enumVals;
var convertToJsonSchema7 = function (schemaDef) {
    var check = schemaDef.check, schemaForAJV = __rest(schemaDef, ["check"]);
    if (schemaForAJV.items) {
        return convertItemToJsonSchema7(__assign({ t: 'array' }, schemaForAJV));
    }
    if (schemaForAJV.t === 'union') {
        return convertItemToJsonSchema7(schemaForAJV);
    }
    return convertItemToJsonSchema7({ t: 'object', props: schemaForAJV });
};
var convertItemToJsonSchema7 = function (itemArg) {
    var item = (typeof itemArg === 'string') ? { t: itemArg } : itemArg;
    var t = item.t, vals = item.vals, discriminant = item.discriminant, discriminantInUpLevel = item.discriminantInUpLevel, schemas = item.schemas, itemRest = __rest(item, ["t", "vals", "discriminant", "discriminantInUpLevel", "schemas"]);
    var newItem;
    if (t === 'object') {
        newItem = convertObjectToJsonSchema7(item);
    }
    else if (t === 'union') {
        newItem = convertUnionToJsonSchema7(vals, discriminant, discriminantInUpLevel || false, schemas, item.printLabel);
    }
    else if (t === 'array') {
        newItem = convertArrayToJsonSchema7(item);
    }
    else if (t === 'enum') {
        newItem = __assign(__assign({}, itemRest), { type: 'integer', 'enum': vals });
    }
    else {
        if (t === 'email') {
            t = 'string';
            // itemRest.pattern = '^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$';
            itemRest.format = 'email';
        }
        newItem = __assign(__assign({}, itemRest), { type: t });
    }
    return newItem;
};
var convertObjectToJsonSchema7 = function (schemaDef) {
    if (schemaDef.props && schemaDef.props.t === 'union') {
        var _a = schemaDef.props, vals = _a.vals, discriminant = _a.discriminant, discriminantInUpLevel = _a.discriminantInUpLevel, schemas = _a.schemas;
        return convertUnionToJsonSchema7(vals, discriminant, discriminantInUpLevel || false, schemas, schemaDef.props.printLabel);
    }
    var res = {
        'type': 'object',
        properties: {},
        required: [],
        additionalProperties: false,
    };
    for (var _i = 0, _b = Object.keys(schemaDef.props); _i < _b.length; _i++) {
        var key = _b[_i];
        var item = (schemaDef.props)[key];
        if (!item.optional) {
            res.required.push(key);
        }
        res.properties[key] = convertItemToJsonSchema7(item);
    }
    return res;
};
var convertUnionToJsonSchema7 = function (vals, discriminant, discriminantInUpLevel, schemas, printLabel) {
    var res = {
        type: 'object',
        required: discriminantInUpLevel ? [] : [discriminant],
        properties: {},
        select: { $data: discriminantInUpLevel ? "1/".concat(discriminant) : "0/".concat(discriminant) },
        selectCases: {},
        selectDefault: false,
    };
    if (!discriminantInUpLevel) {
        res.properties[discriminant] = { type: 'integer', enum: vals };
    }
    if (vals.length !== schemas.length) {
        throw new Error("Not matching discriminant and schemas lists, label: ".concat(printLabel));
    }
    for (var i = 0; i < vals.length; i++) {
        var enumVal = vals[i];
        var schema = schemas[i];
        res.selectCases[enumVal] = convertToJsonSchema7(schema);
    }
    return res;
};
var convertArrayToJsonSchema7 = function (schemaDef) {
    var t = schemaDef.t, items = schemaDef.items, limits = __rest(schemaDef, ["t", "items"]);
    return __assign({ 'type': 'array', items: convertItemToJsonSchema7(schemaDef.items) }, limits);
};
var compileValidation = function (schema) {
    if (schema.check && Object.keys(schema).length === 1) {
        return function (obj) {
            schema.check(obj);
            return obj;
        };
    }
    var convertSchema = convertToJsonSchema7(schema);
    var val = AJV.compile(convertSchema);
    return function (obj) {
        var valRes = val(obj);
        if (!valRes) {
            throw new Error('Type validation failed');
        }
        var retVal = obj;
        if (schema.check) {
            schema.check(retVal);
        }
        return retVal;
    };
};
exports.compileValidation = compileValidation;
var ensureEmpty = function (x) {
    if (x === undefined) {
        return {};
    }
    if (typeof x === 'object' && Object.getOwnPropertyNames(x).length === 0) {
        return {};
    }
    throw new Error('Bad data');
};
exports.ensureEmpty = ensureEmpty;
