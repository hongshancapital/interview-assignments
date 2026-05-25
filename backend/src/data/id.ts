import { ErrorIdMaxExceed, ErrorIdSystemNotCreated } from "../biz/errors/id";
import IdModel, { SystemNameId } from "./models/id";
import { encodeBase58 } from "../util/base58";

// 58^8 - 1, 最多八位字符表示短链
export const maxId = BigInt(128063081718015);

export const newId = async () => {
    const newId = await IdModel.findOneAndUpdate(
        { name: SystemNameId },
        { "$inc": { id: 1 } },
        { new: true, fields: "id", upsert: true }
    ).exec()

    if (newId === null) {
        throw ErrorIdSystemNotCreated;
    } else {
        if (newId.id > maxId) {
            throw ErrorIdMaxExceed;
        }

        return encodeBase58(newId.id)
    }
}