export enum ID_Service_Result_Code {
  SUCCESS = 0,
  FAILURE = 1,
  EXCEPTION = 2,
}

export class ID_Service_Response {
  private res_code: ID_Service_Result_Code;

  private res_value: number;

  private res_message: string;

  constructor(code:ID_Service_Result_Code, value:number, message: string) {
    this.res_code = code;
    this.res_value = value;
    this.res_message = message;
  }

  code(): ID_Service_Result_Code { return this.res_code; }

  value(): number { return this.res_value; }

  message(): string { return this.res_message; }
}

export interface ID_Service_Intf {
  request: (preferred_range? :number) => Promise<ID_Service_Response>
}
