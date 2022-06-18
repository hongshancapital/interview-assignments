/** pluginDriver types */

export enum HookType {
  first,
  sync,
  async,
  parallel
}

interface BaseHookConf {
  type: HookType;
  params: any[];
  result?: any;
}

interface ResultHookConf extends BaseHookConf {
  type: HookType.first;
  params: any[];
  result: any;
}

export interface HooksConfig {
  [name: string]: BaseHookConf | ResultHookConf
}

// todo 暂不支持 reduce
export type Result<T extends HookType, V> = T extends HookType.async
  ? Promise<void> : T extends HookType.parallel
  ? Promise<void[]> : T extends HookType.first
  ? V : void;

interface Hook<C extends HooksConfig, N extends keyof C, T> {
  (this: T, ...args: C[N]['params']): Result<C[N]['type'], C[N]['result']> | C[N]['result']
}

export type Plugin<C extends HooksConfig, T> = {
  [N in keyof C]?: Hook<C, N, T>
}

export type PluginMap<C extends HooksConfig, T> = {
  [N in keyof C]: {
    type: C[N]['type'];
    hooks: Hook<C, N, T>[];
  }
}

/** carousel types */

export type Status = -1 | 0 | 1 | 2 | 3 | 4 // -1 init; 0 mounted 1 waiting 2 jump 3 pause 4 unmount

export interface CoreResult {
  task: Promise<void>;
  data: number;
}

export type CarouselHookConfig = {
  init: {
    type: HookType.async;
    params: [];
  }
  mounted: {
    type: HookType.sync;
    params: [];
  }
  wait: {
    type: HookType.first;
    params: [number]; // currentIndex
    result: CoreResult;
  }
  waiting: {
    type: HookType.parallel;
    params: [number, number]; // time; currentIndex
  }
  jump: {
    type: HookType.first;
    params: [number]; // current index
    result: CoreResult;
  }
  jumping: {
    type: HookType.parallel;
    params: [number, number]; // [time, current index]
  }
  jumpEnd: {
    type: HookType.sync;
    params: [number]; // current index
  }
  pause: {
    type: HookType.sync;
    params: [];
  }
  unmount: {
    type: HookType.sync;
    params: [];
  }
}

export type CoreHook = 'wait' | 'jump'
