type EventName = keyof Omit<React.DOMAttributes<HTMLDivElement>, 'children'|'dangerouslySetInnerHTML'>
type EventCallback = (e: Event) => void;
type EventTrigger<T> = (e: Event, context: T) => void;

type EventPlugin<T> = {
    [key in EventName]?: EventTrigger<T>
}

const eventPluginHandler = <T>(eventPlugins: EventPlugin<T>[], context: T) => {
  const eventEmitter = eventPlugins.reduce((obj, eventObj)=>{
    Object.entries(eventObj).forEach(([evtName, cb]) => {
      if(!(evtName in obj)) {
        obj[evtName as EventName] = [] as EventCallback[];
      }
      const decoCb: EventCallback = (e: Event) => {
        cb(e, context);
      };
      (obj[evtName as EventName]).push(decoCb);
      return obj;
    });
    return obj;
  }, {} as Record<EventName, EventCallback[]>);
  const eventObj = Object.entries(eventEmitter).reduce((obj, [name, cbArr]) => {
    const cb: React.EventHandler<any> = ((e: Event) => {
      cbArr.forEach(cb=>cb(e));
    });
    obj[name as EventName] = cb;
    return obj;
  }, {} as React.DOMAttributes<HTMLDivElement>);
  return eventObj;
};

export type {
  EventPlugin
};
export default eventPluginHandler;