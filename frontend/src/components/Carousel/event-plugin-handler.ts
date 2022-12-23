type PureEvent = Omit<React.DOMAttributes<HTMLDivElement>, 'children'|'dangerouslySetInnerHTML'>;
type EventName = keyof PureEvent;
type TriggerEvent = Parameters<Required<PureEvent>[EventName]>[0]
type TriggerCallback = (e: TriggerEvent) => void;
type EventEmitter = {
  [key in EventName]?: PureEvent[key][]
}

type EventPlugin<T> = {
    [key in EventName]?: (e: TriggerEvent, context: T)=>void
}

const eventPluginHandler = <T>(eventPlugins: EventPlugin<T>[], context: T) => {
  const eventEmitter = eventPlugins.reduce((obj, eventObj)=>{
    Object.entries(eventObj).forEach(([evtName, cb]) => {
      if(!(evtName in obj)) {
        obj[evtName as EventName] = [] as TriggerCallback[];
      }
      const decoCb = (e: TriggerEvent) => {
        cb(e, context);
      };
      (obj[evtName as EventName]!).push(decoCb);
      return obj;
    });
    return obj;
  }, {} as EventEmitter);
  const eventObj = Object.entries(eventEmitter).reduce((obj, [name, cbArr]) => {
    const callback = (e: TriggerEvent) => {
      (cbArr as TriggerCallback[]).forEach(cb=>cb(e));
    };
    obj[name as EventName] = callback;
    return obj;
  }, {} as PureEvent);
  return eventObj;
};

export type {
  EventPlugin
};
export default eventPluginHandler;