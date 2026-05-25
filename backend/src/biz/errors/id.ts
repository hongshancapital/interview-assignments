// id生成相关错误码
import { BizError } from "./bizerror";

export const ErrorIdSystemNotCreated = new BizError("20000", "id system config is not created yet");
export const ErrorIdMaxExceed = new BizError("20001", "max id exceed");