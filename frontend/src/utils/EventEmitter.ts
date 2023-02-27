
type EventsMap = {
  [name: string]: [Function, Object][];
}

type TypesMap = {
  [type: string]: string;
}

export default class EventEmitter {
  private events: EventsMap;
  private eventTypes: TypesMap;
  
  constructor(names: string[]) {
    this.events = {};
    this.eventTypes = {};
    names.forEach(type => this.eventTypes[type] = type);
  }
  
  on(type: string, fn: Function, context = this) {
    if (!this.events[type]) {
      this.events[type] = [];
    }
    
    // 保存type事件对应的函数
    this.events[type].push([fn, context]);
  }
  
  // 触发type事件
  trigger(type: string, ...args: any[]) {
    let events = this.events[type];
    if (!events) return;
    
    let len = events.length;
    let eventsCopy = events.slice();
    let ret;
    for (let i = 0; i < len; i++) {
      let event = eventsCopy[i];
      let [fn, context] = event;
      if (fn) {
        ret = fn.apply(context, args);
        if (ret === true) break;
      }
    }
  }
}