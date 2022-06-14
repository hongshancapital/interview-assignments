interface SettingType {
  content: Array<String|Function|Element>; // 内容区,可以是图片URL或react component
  intervalTime:number //自动轮播间隔时间
}

export type { SettingType };
