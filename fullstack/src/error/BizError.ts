class BizError extends Error {

  public code: number;

  constructor(message: string, code: number = 400) {
    super(message);
    this.name = 'BizError';
    this.code = code;
  }

}

export default BizError;
