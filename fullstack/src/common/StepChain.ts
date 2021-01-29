export interface ChainAction {
  next: (name: string) => void,
  break: () => void
}

interface ChainFunction<C> {
  (context: C, action: ChainAction): Promise<void> | void;
}

export class StepChain<C> {
  //记录名称对应的索引
  chainListIndexMap: any = {};
  //链列表
  chainList: { name: string; fn: ChainFunction<C> }[] = [];
  //锁
  lockStatus = false;

  //关锁 关锁之后无法对链列表进行操作
  lock() {
    this.lockStatus = true;
  }

  //开锁
  unLock() {
    this.lockStatus = false;
  }

  /**
   * 添加一个链
   * @param name 名称
   * @param fn 函数
   */
  add(name: string, fn: ChainFunction<C>) {
    if (this.lockStatus) return;
    this.chainList.push({ name, fn });
    this.cache();
  }

  /**
   * 缓存index
   */
  cache() {
    this.chainListIndexMap = {};
    for (let i in this.chainList) {
      this.chainListIndexMap[this.chainList[i].name] = i;
    }
  }

  /**
   * 清除所有
   */
  clear() {
    if (this.lockStatus) return;
    this.chainList = [];
    this.cache();
  }

  /**
   * 删除一项
   * @param name
   */
  remove(name: string) {
    if (this.lockStatus) return;
    let index = this.chainListIndexMap[name];
    if (index) {
      return;
    }
    this.chainList.splice(index, 1);
    this.cache();
  }

  /**
   * 在目标之后插入
   * @param targetName 目标名称
   * @param name
   * @param fn
   */
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

  /**
   * 在目标之后插入
   * @param targetName 目标名称
   * @param name
   * @param fn
   */
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

  /**
   * 执行方法
   * @param context 上下文
   */
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