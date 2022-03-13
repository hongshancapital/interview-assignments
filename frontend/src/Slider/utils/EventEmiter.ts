type keyValue = { [key: string]: Array<Function> };
export class EventEmitter {
  private _events: keyValue = {};

  /**
   * 触发函数
   * @param type
   * @param args
   * @returns
   */
  emit(type: string, ...args: any[]): boolean {
    // If there is no 'error' event listener then throw.
    if (type === 'error') {
      if (
        !this._events[type] ||
        (Array.isArray(this._events[type]) && !this._events[type].length)
      ) {
        if (args[0] instanceof Error) {
          throw args[0]; // Unhandled 'error' event
        } else {
          throw new Error("Uncaught, unspecified 'error' event.");
        }
      }
    }
    if (this._events[type]) {
      this._events[type].forEach((handler: Function) => handler.apply(this, args));
      return true;
    }
    return false;
  }

  /**
   * 绑定函数
   * @param type {string}
   * @param listener {function}
   * @param isCallback {boolean} // 如果isCallback为真，则返回一个subscribe函数
   * @returns 返回subscribe或emit
   */
  addListener(type: string, listener: Function, isCallback?: boolean): EventEmitter | Function {
    if ('function' !== typeof listener) {
      throw new Error('addListener only takes instances of Function');
    }

    if (!this._events[type]) this._events[type] = [];
    else if (this._events[type].indexOf(listener) >= 0) return this;
    this._events[type].push(listener);
    if (isCallback) {
      return () => {
        this._events[type] = this._events[type].filter((x) => x != listener);
      };
    }
    return this;
  }

  /**
   * 解绑函数
   * @param type {string} 类型
   * @param listener {function} 函数
   * @returns
   */
  removeListener(type: string, listener: Function): EventEmitter {
    var events = this._events;
    if (!events[type]) return this;
    var listeners: any[] = events[type];
    var i = listeners.indexOf(listener);
    if (i >= 0) listeners.splice(i, 1);
    return this;
  }

  /**
   * 解绑指定类型全部函数
   * @param type {string} 类型
   * @returns
   */
  removeAllListener(type: string) {
    this._events[type] = [];
    return this;
  }
}
