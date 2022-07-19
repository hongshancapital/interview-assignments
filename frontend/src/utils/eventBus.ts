import { ListenIndex, ListenObj } from "../components/Carousel/CommonTypes";


class EventBus {
  private listenObj: ListenObj = {};
  private static _instance: EventBus;
  public static getInstance() {
    if (!this._instance) {
      this._instance = new EventBus();
    }
    return this._instance;
  }
  public on(eventName: string, cb: (e: any) => void): ListenIndex {
    if (!this.listenObj[eventName]) {
      this.listenObj[eventName] = [];
    }
    this.listenObj[eventName].push(cb);
    return {
      eventName: eventName,
      index: this.listenObj[eventName].length - 1,
    };
  }
  public emit(eventName: string, params: any) {
    if (this.listenObj[eventName]) {
      this.listenObj[eventName].forEach((cb) => {
        if (typeof cb === "function") {
          cb(params);
        }
      });
    }
  }
  public un(listenIndex: ListenIndex) {
    if (this.listenObj[listenIndex.eventName]) {
      this.listenObj[listenIndex.eventName][listenIndex.index] = null;
    }
  }
}

export default EventBus.getInstance();
