import { EventEmitter } from "../utils/EventEmiter";

class Swiper extends EventEmitter {

  /*
   * 轮播图切换图片的速度 毫秒
   */
  private speed: number | undefined = 3000;

  /*
   * 自动抡博停留时间 毫秒
   */
  private delay: number | undefined = 3000;

  /*
   * 鼠标悬停是否停止播放
   */
  private pause: boolean | undefined = true;

  /*
   * 是否自动播放
   */
  private autoPlay: boolean | undefined = true;

  /*
   * 是否显示轮播图点
   */
  private dots: boolean | undefined = true;

  /*
   * 当前索引
   */
  private index: number = 0;

  /*
   * 轮播图资源
   */
  private sliderList: Array<SliderProps> = []

  constructor(props: SwiperProps) {
    super()
    const { speed, delay, pause, autoPlay, dots, sliderList } = props;
    this.speed = speed ?? 3000;
    this.delay = delay ?? 3000;
    this.pause = pause ?? true;
    this.dots = dots ?? true;
    this.autoPlay = autoPlay ?? true;
    this.index = 0;
    this.sliderList = sliderList;
    this.initFinish()
  }

  initFinish() {
    new Promise((resolve, reject) => {
      resolve(true)
    }).then(() => {
      this.emit('finished')
    })
  }

  // 暂停播放
  onPause() {
    this.pause = true;
    this.emit('pause', true)
  }

  // 开始轮博
  onPlay() {
    this.pause = false;
    this.emit('pause', false)
  }

  // 添加轮播图资源
  append(item: SliderProps) {
    this.sliderList.push(item);
    this.emit('sliderChange', this.sliderList)
  }

  // 获取轮播图列表
  getSliderList() {
    return this.sliderList;
  }

  // 获取轮播图数量
  getSliderCount() {
    return this.sliderList.length;
  }

  // 是否自动轮播
  isAutoPlay() {
    return this.autoPlay;
  }

  // 是否展示dots
  getSliderDots() {
    return this.dots;
  }

  // 速度
  getSliderSpeed() {
    return this.speed;
  }

  // 轮播图间隔时间
  getSliderDelay() {
    return this.delay;
  }

  // 当前轮播图索引
  getSliderIndex() {
    return this.index;
  }

  // 设置轮播图索引
  setSliderIndex(index: number) {
    this.index = index;
    this.emit('indexChanged', index)
  }

  // 获取播放速度
  getSpeed() {
    return this.speed;
  }

}

export default Swiper;