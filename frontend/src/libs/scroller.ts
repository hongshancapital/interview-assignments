import Timmer,{Recorder} from './timmer'

class Scroller{
  public interval:number = 0;
  public currentPlay:number = 0;
  public isLast:boolean = false;
  private _wrapper: HTMLElement;
  private _timmer:Timmer|null = null;
  private _onChange?:(scroller:Scroller)=>void;
  private _countingCallback?:Recorder;
  constructor(params:{
    wrapper:HTMLElement;
    interval:number;
    onChange?:(scroller:Scroller)=>void;
    countingCallback?:Recorder;
  }){

    //Object.assign(this,params)
    this.interval = params.interval
    this._wrapper = params.wrapper
    if(params.interval<= 0){return}
    this._onChange = params.onChange
    this._countingCallback = params.countingCallback
    //启动滚屏
    this.start()
  }

  /*@Todo Stevie
    如果这里滚动动画需要自定义速度和效果，则需要定义easing函数来计算每帧移动的offset。
    并通过requestAnimationFrame递归调用scroll函数完成。
    这里简单用css trick:scroll-behavior完成
  */
  //滚屏计算
  scroll = () => {
    const index = this._wrapper.scrollLeft/this._wrapper.clientWidth
    const cnt = this._wrapper.childElementCount - 1
    if(index === cnt){//到底复位
      this._wrapper.scrollLeft = 0
    }else{
      this._wrapper.scrollLeft += this._wrapper.clientWidth;
    }

    this.currentPlay = index === cnt ?0:index + 1
  }

  start = () => {
     this._timmer = new Timmer(this.interval,()=>{
      this.scroll()
      this._onChange && this._onChange(this)
    })
    this._timmer.onSubscribe((percent)=>{
      this._countingCallback && this._countingCallback(percent,this.currentPlay)
    })
  }

  stop = () => {
    if(this._timmer){
      this._timmer.clear()
      this._timmer.removeSubscribe()
    }

  }
}
export default Scroller
