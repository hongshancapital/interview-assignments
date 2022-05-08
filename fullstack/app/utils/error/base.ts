export interface ErrorType {
  code: number;
  message: string;
}

export abstract class BaseCustomError<ET extends ErrorType = ErrorType> extends Error {
  abstract get status(): number;
  get realStatus() {
    return this.status;
  }

  readonly code: number;
  readonly errors: any;

  constructor(errorData: string | ET = '', public logData?: any) {
    super(typeof errorData === 'string' ? errorData : errorData.message);

    const { code, ...other } =
      typeof errorData === 'string'
        ? {
          code: -1,
          message: errorData,
        }
        : errorData;

    this.code = code;
    this.errors = other;

    Object.defineProperty(this, 'logData', {
      configurable: true,
      enumerable: false,
    });
  }
}
