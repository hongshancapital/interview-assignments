export interface IResData {
  code: string,
  message: string,
  data: any,
  serviceTime: Date,
}

class ResData implements IResData {
  public code !: string;
  public message !: string;
  public data !: any;
  public serviceTime !: Date;

  constructor(code: string, message: string, data: any) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.serviceTime = new Date();
  }
}

export default ResData;