declare module 'template-js/components' {
  type NotifyParam = { content: string; duration?: number };
  type NotifyFunc = (param: NotifyParam) => void;
  class Notify {
    static success: NotifyFunc;
    static error: NotifyFunc;
  }
}
