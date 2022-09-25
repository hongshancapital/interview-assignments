import { createConnection, Model } from "mongoose";
import { config } from "../../config";
import { to62 } from "../../utils/to62";
import { IDSchema } from "./id.model";

/**
 * ID 发布器服务
 */
export class IDService {
  readonly suffix: string;

  constructor(readonly nodeId: number, readonly model: Model<IDSchema>) {
    this.suffix = to62(this.nodeId).padStart(2, "0");
  }

  /**
   * 生成 ID
   * @returns 格式 {6位自增}{2位节点后缀}
   */
  async generate() {
    const doc = await this.model.findOneAndUpdate(
      { nodeId: this.nodeId },
      { $inc: { count: 1 } },
      { upsert: true, new: true }
    );

    // `doc` 一定存在, 因为 `upsert`
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    const count = to62(doc!.count).padStart(6, "0");

    return `${count}${this.suffix}`;
  }
}

let instance: IDService | undefined;

export function getIDService() {
  const nodeId = config.NODE_ID;

  // 约定 server 节点范围为 [0,100]
  if (nodeId < 0 || nodeId >= 100) {
    throw new Error(`nodeId 超出范围`);
  }

  if (!instance) {
    const connection = createConnection(config.ID_MONGODB_URI);
    const model = connection.model("id_counter", IDSchema);
    instance = new IDService(nodeId, model);
  }

  return instance;
}
