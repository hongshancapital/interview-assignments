// url生成相关错误码
import { BizError } from "./bizerror";

export const ErrorUrlShouldProvide = new BizError("30000", "short url should be provided");
export const ErrorUrlOriginNotFound = new BizError("30001", "origin url not found");

export const ErrorUrlRequiredCreator = new BizError("30012", "creator should be provided");
export const ErrorUrlRequiredOriginUrl = new BizError("30013", "originUrl should be provided");
export const ErrorUrlRequiredDescription = new BizError("30014", "description should be provided");

export const ErrorUrlCreatorTooLong = new BizError("30015", "creator too long");
export const ErrorUrlOriginUrlTooLong = new BizError("30016", "originUrl too long");
export const ErrorUrlDescriptionTooLong = new BizError("30017", "description too long");