enum Code {
  SUCCESS = 1000,
  FAILED = 2000,
}

class ResCode {

  public static Code = Code;

  public static getCode(code: Code) {
    return [code, `${Code[code].split('_').join(' ')}`];
  }
}

export default ResCode;