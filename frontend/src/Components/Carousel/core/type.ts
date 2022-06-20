export enum Status {
  beforeInit,
  init,
  mounted,
  waiting,
  jump,
  pause,
  unmount,
}

export interface CoreResult {
  task: Promise<void>;
  data: number;
}

export interface HookConf {
  init: [];
  mounted: [];
  waiting: [number, number]; // time; currentIndex
  jumping: [number, number]; // time; currentIndex
  jumpEnd: [number]; // current index
  pause: [];
  unmount: [];
}

export interface Hook<C, P extends Array<any> = []> {
  (this: C, ...args: P): Promise<void> | void;
}

export type HookMap<C> = {
  [K in keyof HookConf]: Hook<C, HookConf[K]>[];
}

export type Hooks<C> = {
  [K in keyof HookConf]?: Hook<C, HookConf[K]>;
}

export interface CoreHook<C> {
  wait(this: C): CoreResult;
  jump(this: C, index: number): CoreResult;
}
