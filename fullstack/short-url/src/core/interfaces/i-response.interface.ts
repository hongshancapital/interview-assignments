import { StatusCode } from '../enums/status-code.enum';

export interface IResponse<Data> {
  statusCode: StatusCode;
  data?: Data;
  error?: string;
}
