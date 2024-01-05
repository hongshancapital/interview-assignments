import configs from "../configs";
import ResData from "../domains/ResData";
import { Response } from 'express';

class ResUtil {
  public static success(res: Response, data: any) {
    let successCode = "B00000";
    res.status(200);
    res.send(new ResData("B00000", configs.resCodes[successCode], data));
  }

  public static failed(res: Response, code: string, message?: string) {
    if (!message) {
      message = configs.resCodes[code];
    }
    res.status(500);
    res.send(new ResData(code, message as string, null));
  }
}

export default ResUtil;