import HttpResponseError from "./HttpResponseError";

export default class BadRequestError extends HttpResponseError {

  constructor(message?: string) {
    super();
    this.statusCode = 400;
    this.message = message || 'Bad Request';
  }
}