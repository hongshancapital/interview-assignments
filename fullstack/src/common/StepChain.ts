export interface ChainAction {
  next: (name: string) => void,
  break: () => void
}

interface ChainFunction<C> {
  (context: C, action: ChainAction): Promise<void> | void;
}

export class StepChain<C> {
  chainListIndexMap: any = {};

  chainList: { name: string; fn: ChainFunction<C> }[] = [];

  lockStatus = false;

  lock() {
    this.lockStatus = true;
  }

  unLock() {
    this.lockStatus = false;
  }

  add(name: string, fn: ChainFunction<C>) {
    if (this.lockStatus) return;
    this.chainList.push({ name, fn });
    this.cache();
  }

  cache() {
    this.chainListIndexMap = {};
    for (let i in this.chainList) {
      this.chainListIndexMap[this.chainList[i].name] = i;
    }
  }

  clear() {
    if (this.lockStatus) return;
    this.chainList = [];
    this.cache();
  }

  remove(name: string) {
    if (this.lockStatus) return;
    let index = this.chainListIndexMap[name];
    if (index) {
      return;
    }
    this.chainList.splice(index, 1);
    this.cache();
  }

  before(targetName: string, name: string, fn: ChainFunction<C>) {
    if (this.lockStatus) return;
    let index = this.chainListIndexMap[targetName];
    if (!index) {
      console.warn("not found");
      return;
    }
    this.chainList.splice(index, 0, { name, fn });
    this.cache();
  }

  after(targetName: string, name: string, fn: ChainFunction<C>) {
    if (this.lockStatus) return;
    let index = this.chainListIndexMap[targetName];
    if (!index) {
      console.warn("not found");
      return;
    }
    this.chainList.splice(index + 1, 0, { name, fn });
    this.cache();
  }

  async handle(context: C): Promise<void> {
    for (let i = 0; i < this.chainList.length; i++) {
      await this.chainList[i].fn(context, {
        next: (name: string) => {
          i = this.chainListIndexMap[name] - 1;
        },
        break: () => {
          i = this.chainList.length;
        }
      });
    }
  }
}