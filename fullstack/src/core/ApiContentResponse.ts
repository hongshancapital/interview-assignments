import { Response } from 'express';

// Http 内容 response的格式和状态
enum StatusCode {
  SUCCESS = '10000',
  FAILURE = '10001',
  RETRY = '10002',
  INVALID_ACCESS_TOKEN = '10003',
}

enum ResponseStatus {
  SUCCESS = 200,
  REDIRECT = 302,
  BAD_REQUEST = 400,
  NOT_FOUND = 404,
  UNPROCESSABLE_ENTITY = 422,
  INTERNAL_ERROR = 500,
}

abstract class ApiContentResponse {
  constructor(
    protected statusCode: StatusCode,
    protected status: ResponseStatus,
    protected message: string,
  ) {}

  protected prepare<T extends ApiContentResponse>(res: Response, response: T): Response {
    return res.status(this.status).json(ApiContentResponse.sanitize(response));
  }

  public send(res: Response): Response {
    return this.prepare<ApiContentResponse>(res, this);
  }

  private static sanitize<T extends ApiContentResponse>(response: T): T {
    const clone: T = {} as T;
    Object.assign(clone, response);
    for (const i in clone) if (typeof clone[i] === 'undefined') delete clone[i];
    return clone;
  }
}

export class NotFoundResponse extends ApiContentResponse {
  private url: string | undefined;

  constructor(message = 'Not Found') {
    super(StatusCode.FAILURE, ResponseStatus.NOT_FOUND, message);
  }

  send(res: Response): Response {
    this.url = res.req?.originalUrl;
    return super.prepare<NotFoundResponse>(res, this);
  }
}

export class BadRequestResponse extends ApiContentResponse {
  constructor(message = 'Bad Parameters') {
    super(StatusCode.FAILURE, ResponseStatus.BAD_REQUEST, message);
  }
}

export class UnprocessableEntityResponse extends ApiContentResponse {
  constructor(message = 'Unprocessable Entity') {
    super(StatusCode.FAILURE, ResponseStatus.UNPROCESSABLE_ENTITY, message);
  }
}

export class InternalErrorResponse extends ApiContentResponse {
  constructor(message = 'Internal Error') {
    super(StatusCode.FAILURE, ResponseStatus.INTERNAL_ERROR, message);
  }
}
export class SuccessResponse<T> extends ApiContentResponse {
  constructor(message: string, private data: T) {
    super(StatusCode.SUCCESS, ResponseStatus.SUCCESS, message);
  }

  send(res: Response): Response {
    return super.prepare<SuccessResponse<T>>(res, this);
  }
}


