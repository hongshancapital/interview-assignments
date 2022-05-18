import {
  AddShortLinkParams,
  AddShortLinkReturn,
  UpdateShortLinkParams,
  UpdateShortLinkReturn,
  GetShortLinkParams,
  GetShortLinkReturn
} from "./interface"
import { ServiceReturnInterface, ServiceErrorInterface } from "../../interface"
import genErrorCode, { CodeModulesEnum } from "../../utils/errorCode"
import { genServiceError, genServiceReturn } from "../utils"
import { add, query, update } from "../../dataCore/slink"
const genServiceCode = genErrorCode("service")
async function addShortLinkService(params: AddShortLinkParams): Promise<ServiceReturnInterface<AddShortLinkReturn>> {
  try {
    const slink = await add(params.appid, params.oriUrl)
    console.info("addShortLinkService[OK]", JSON.stringify(params), JSON.stringify(slink))
    return genServiceReturn({ slink })
  } catch (err: any) {
    console.info("addShortLinkService[Error]", JSON.stringify(params), JSON.stringify(err))
    const ErrorCode = genServiceCode("addShortLine") + (err?.code || "99")
    return genServiceError(ErrorCode, err)
  }
}

async function updateShortLinkService(
  params: UpdateShortLinkParams
): Promise<ServiceReturnInterface<UpdateShortLinkReturn>> {
  try {
    const slink = await update(params.appid, params.oriUrl, params.slink)
    console.info("updateShortLinkService[OK]", JSON.stringify(params), JSON.stringify(slink))
    return genServiceReturn({ slink, oriUrl: params.oriUrl })
  } catch (err: any) {
    console.info("updateShortLinkService[Error]", JSON.stringify(params), JSON.stringify(err))
    const ErrorCode = genServiceCode("updateShortLine") + (err?.code || "99")
    return genServiceError(ErrorCode, err)
  }
}

async function getShortLinkService(params: GetShortLinkParams): Promise<ServiceReturnInterface<GetShortLinkReturn>> {
  try {
    const oriUrl = await query(params.slink)
    console.info("getShortLinkService[OK]", JSON.stringify(params), JSON.stringify(oriUrl))
    return genServiceReturn({ slink: params.slink, oriUrl })
  } catch (err: any) {
    console.info("getShortLinkService[Error]", JSON.stringify(params), JSON.stringify(err))
    const ErrorCode = genServiceCode("queryShortLine") + (err?.code || "99")
    return genServiceError(ErrorCode, err)
  }
}
export { addShortLinkService, updateShortLinkService, getShortLinkService }
