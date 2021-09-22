
export function tuple<T extends string[]>(...args: T): T {
  return args;
}
export const MUTATION_STATE_KEYS = tuple(
  'mutation',
  'animation',
  'transition',
  'scroll',
  'resize',
);

export type MutationStateKeys = typeof MUTATION_STATE_KEYS[number];

export type IMutationState = Record<MutationStateKeys, boolean>;

export type IMutationHandler = Record<MutationStateKeys, VoidFunction>;

export interface IMutationHandlerCallback extends IMutationState {
  listener: VoidFunction;
}

class MutationHandler {
  private timer: number | null = null;

  private state: IMutationState;

  private listenedState: IMutationState;

  private readonly unbindFns: Partial<IMutationHandler> = {};

  private readonly callbackList: IMutationHandlerCallback[] = [];

  constructor() {
    this.state = {
      mutation: false,
      animation: false,
      transition: false,
      scroll: false,
      resize: false,
    };

    this.listenedState = {
      mutation: false,
      animation: false,
      transition: false,
      scroll: false,
      resize: false,
    };
  }

  public listen(): void {
    const newState: IMutationState = {
      mutation: false,
      animation: false,
      transition: false,
      scroll: false,
      resize: false,
    };

    this.callbackList.forEach((listener) => {
      MUTATION_STATE_KEYS.forEach((key) => {
        if (listener[key]) {
          newState[key] = true;
        }
      });
    });

    this.processMutation(this.state.mutation, newState.mutation);
    this.processTransition(this.state.transition, newState.transition);
    this.processScroll(this.state.scroll, newState.scroll);
    this.processAnimation(this.state.animation, newState.animation);
    this.processResize(this.state.resize, newState.resize);
    this.state = newState;
  }

  public process(type: MutationStateKeys): void {
    this.listenedState[type] = true;

    if (this.timer != null) {
      cancelAnimationFrame(this.timer);
    }

    this.timer = requestAnimationFrame(() => this.dispatch());
  }

  public addListener(callback: VoidFunction, options: IMutationState): void {
    if (this.callbackList.some((item) => item.listener === callback)) {
      throw new Error('function must be bind once');
    }

    this.callbackList.push({
      ...options,
      listener: callback,
    });

    this.listen();
  }

  public removeListener(callback: VoidFunction): void {
    const index = this.callbackList.findIndex(
      (item) => item.listener === callback,
    );

    if (index < 0) {
      return;
    }

    this.callbackList.splice(index, 1);

    this.listen();
  }

  private dispatch(): void {
    const state = this.listenedState;
    const callbackList = this.callbackList.filter((item) =>
      MUTATION_STATE_KEYS.some((key) => item[key] && state[key]),
    );

    this.listenedState = {
      mutation: false,
      animation: false,
      transition: false,
      scroll: false,
      resize: false,
    };

    callbackList.forEach((item) => item.listener());
  }

  // 属性变化
  private processMutation(oldState: boolean, newState: boolean): void {
    if (oldState === newState) {
      return;
    }

    if (newState) {
      const ob = new MutationObserver(() => this.process('mutation'));

      ob.observe(document, {
        attributes: true,
        characterData: true,
        childList: true,
        subtree: true,
      });

      this.unbindFns.mutation = () => ob.disconnect();
      return;
    }

    this.unbindFns.mutation && this.unbindFns.mutation();
    this.unbindFns.mutation = undefined;
  }

  // 渐变动画
  private processTransition(oldState: boolean, newState: boolean): void {
    if (oldState === newState) {
      return;
    }

    if (newState) {
      const event =
        'ontransitionend' in document ? 'transitionend' : 'webkitTransitionEnd';

      const callback = (): void => this.process('transition');
      const options = {
        capture: true,
        passive: true,
      };

      document.addEventListener(event, callback, options);

      this.unbindFns.transition = () => {
        document.removeEventListener(event, callback, options);
      };

      return;
    }

    this.unbindFns.transition && this.unbindFns.transition();
    this.unbindFns.transition = undefined;
  }

  // 逐帧动画
  private processAnimation(oldState: boolean, newState: boolean): void {
    if (oldState === newState) {
      return;
    }

    if (newState) {
      const event =
        'onanimationend' in document ? 'animationend' : 'webkitAnimationEnd';

      const callback = (): void => this.process('animation');
      const options = {
        capture: true,
        passive: true,
      };

      document.addEventListener(event, callback, options);

      this.unbindFns.animation = () => {
        document.removeEventListener(event, callback, options);
      };

      return;
    }

    this.unbindFns.animation && this.unbindFns.animation();
    this.unbindFns.animation = undefined;
  }

  // 元素滚动
  private processScroll(oldState: boolean, newState: boolean): void {
    if (oldState === newState) {
      return;
    }

    if (newState) {
      const callback = (): void => this.process('scroll');
      const options = {
        capture: true,
        passive: true,
      };

      document.addEventListener('scroll', callback, options);

      this.unbindFns.scroll = () => {
        document.removeEventListener('scroll', callback, options);
      };

      return;
    }

    this.unbindFns.scroll && this.unbindFns.scroll();
    this.unbindFns.scroll = undefined;
  }

  // 窗体大小变化
  private processResize(oldState: boolean, newState: boolean): void {
    if (oldState === newState) {
      return;
    }

    if (newState) {
      const callback = (): void => this.process('resize');
      const options = {
        capture: true,
        passive: true,
      };

      window.addEventListener('resize', callback, options);

      this.unbindFns.resize = () => {
        window.removeEventListener('resize', callback, options);
      };

      return;
    }

    this.unbindFns.resize && this.unbindFns.resize();
    this.unbindFns.resize = undefined;
  }
}

const handler = new MutationHandler();

/**
 * 添加元素变化事件监控
 *
 * @param callback 回调函数
 * @param options 监控选项
 */
export function addMutationHandler(
  callback: VoidFunction,
  options: Partial<IMutationState>,
): void {
  handler.addListener(callback, {
    mutation: false,
    animation: false,
    transition: false,
    scroll: false,
    resize: false,
    ...options,
  });
}

export function removeMutationHandler(callback: VoidFunction): void {
  handler.removeListener(callback);
}
