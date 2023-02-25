
export default class  HttpResponseError extends Error {
  public statusCode: number;

  public message: string;
}