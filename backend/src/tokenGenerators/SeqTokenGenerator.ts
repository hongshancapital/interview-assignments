import _ from "lodash";
import RedisFactory from "../cache/RedisFactory";
import configs from "../configs";
import { digital10to62, digital62to10 } from "../utils/digitalUtil";
import TokenGenerator from "./TokenGenerator";

// 使用 62 进制随机递增，实现号牌生成
// 号牌最大8位，第一位是机器码，剩下 7 位可以表示范围为 0-ZZZZZZZ，10 进制范围为 0-3521614606207，足够使用
class SeqTokenGenerator implements TokenGenerator {
  private static INIT_SEQ = 100;
  private static generator: SeqTokenGenerator | null;

  latestSeq!: number;
  machineId!: string;
  store: number[] = [];
  batchCount!: number;

  public static async getInstance() {
    if (SeqTokenGenerator.generator) return SeqTokenGenerator.generator;

    const generator = new SeqTokenGenerator();
    generator.machineId = configs.generator.machineId;
    generator.batchCount = configs.generator.batchCount;
    generator.latestSeq = await SeqTokenGenerator.getLatestSeqFromRedis(generator.machineId);
    SeqTokenGenerator.generator = generator;

    console.log(`SeqToken Generator instance is ${JSON.stringify(generator)}`);
    return SeqTokenGenerator.generator;
  }

  // redis 中存储了各机器最新的短链接，当重启服务时，从中获取生成器的初始值
  private static async getLatestSeqFromRedis(machineId: string): Promise<number> {
    const latestShortUrl = await RedisFactory.getClient().get(`machineId:${machineId}:latestShortUrl`);
    if (!latestShortUrl) {
      return SeqTokenGenerator.INIT_SEQ;
    }

    const latestSeq62 = latestShortUrl.substring(1);
    return digital62to10(latestSeq62);
  }

  public static reset() {
    SeqTokenGenerator.generator = null;
  }

  public generateToken():string {
    let num = this.store.shift();
    if (!num) {
      this.batchGenerateSeqToken();
      num = this.store.shift();
    }
    return this.machineId + digital10to62(num as number);
  }

  private batchGenerateSeqToken() {
    for (let i = 0; i < this.batchCount; i++) {
      this.store.push(this.generateSeqToken());
    }
  }

  private generateSeqToken() {
    // 随机添加数
    this.latestSeq += _.random(1, 10, false);
    return this.latestSeq;
  }
}

export default SeqTokenGenerator;