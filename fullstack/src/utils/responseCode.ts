const codeMsg = {
  'success': {
    code: 'A00000'
  },
  'arg_error': {
    code: 'A00001',
    msg: '参数错误'
  },
  'internal_error': {
    code: 'A00002',
    msg: '服务内部错误'
  },
};

export const responseCode = (type, data = {}, errMsg = '') => {
  const { code, msg: message } = codeMsg[type] || codeMsg['internal_error'];
  const msg = errMsg || message;
  return { code, data, ...(msg ? { msg } : {}) };
};