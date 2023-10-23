export class Gid {
  constructor(config: { instanceId?: 0 | 1 | 2 | 3; machineId?: 0 | 1 } = {}) {
    const { instanceId = 0, machineId = 0 } = config;
    if (instanceId >= 4 || instanceId < 0) throw Error("instanceId must >= 0 & < 4");
    if (machineId >= 4 || machineId < 0) throw Error("instanceId must be 0 or 1");
    this.instanceId = instanceId;
    this.machineId = machineId;
  }

  // 1bit
  private machineId = 0;

  // 2bit
  private instanceId = 0;

  // 41bit
  private timestap = Date.now();

  // 4bit, QPS = 1000 * 16
  private counter = 0;

  // custom base64 encoding
  private characterSet =
    "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_";

  bitToBase64(id: string) {
    const fixedId = this.fixLen(id, 48);
    let base64Str = "";
    for (let i = 0; i < 48; i += 6) {
      const index = parseInt(fixedId.slice(i, i + 6), 2);
      base64Str += this.characterSet[index];
    }
    return base64Str;
  }

  generate() {
    let current = Date.now();
    if (this.counter < 15) {
      this.counter++;
    } else {
      this.counter = 0;
      while (current === this.timestap) {
        // wait next ms
        current = Date.now();
      }
    }
    this.timestap = current;
    return `${this.machineId.toString(2)}${this.fixLen(
      this.instanceId.toString(2),
      2
    )}${this.fixLen(this.timestap.toString(2), 41)}${this.fixLen(
      this.counter.toString(2),
      4
    )}`;
  }

  fixLen(str: string, len: number) {
    while (str.length < len) {
      str = `0${str}`;
    }
    return str;
  }
}
