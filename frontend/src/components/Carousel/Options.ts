export class Options {
  // 可索引类型，该类下的所有属性的key必须是字符串，值为any
  [key: string]: any;
  
  
  loop = true;
  
  // 切换速度
  speed = 1;
  
  // 自动播放间隔
  delay = 3000;
  
  autoplay = false;
  
  // 自定义事件
  onTransitionEnd: (index: number) => void;
  onInit: () => void;
  
  
  // 合并默认配置
  merge(options?: { [key: string]: any }) {
    if (options) {
      for (let key in options) {
        this[key] = options[key];
      }
    }
    return this;
  }
}