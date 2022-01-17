interface TimerInt{
  resume:()=>void;
  clear:()=>void;
  onSubscribe:(event:Recorder) =>void//定时器执行百分比订阅
  removeSubscribe:() => void//移除订阅
}
/**
@type Recorder 计时器百分比回调函数
*/
export type Recorder = (percent: number,id?:number) => void

/**
@class Timmer
@parameter interval 定时器时间间隔
@parameter callback 定时器回调
@parameter preExecFn 定时器运行之前调用相关回调，默认关闭
*/
class Timmer implements TimerInt{
  private _timmer:ReturnType<typeof setTimeout>|null = null;
  private _callback?:()=>void;
  private _event: null|Recorder = null;//定时器白分器订阅处理
  private _percent = 0;//时间百分比
  private _interval: number = 0;

  public set interval(value: number) {
      this.clear()
      this._interval = value;
      this._percent = 0;
  }
  public get interval(): number {
      return this._interval;
  }

  constructor(
    interval: number,
    callback?:()=>void,
    preExecFn:boolean = false
  ){
    this._callback = callback
    //定时器调用启动之前执行一次
    if(preExecFn === true){
      this._callback && this._callback()
    }
    //启动定时器
    this._exec(interval)
  }

  //@Todo stevie 可以优化成阻塞式定时器
  //执行任务
  private _exec = (interval: number) => {
    this._interval = interval
    return this._timmer = setTimeout(()=>{
        //百分器回调，回传当前定时进度
        this._percent += 1
        if(this._event){
          this._event(this._percent)
        }
        //执行定时器回调任务
        if(this._percent === 100){
          this._callback && this._callback()
          this._percent = 0
        }

        this.clear()
        this._exec(interval)
      },interval/100)
  }
  //定时器百分比订阅
  onSubscribe = (event:Recorder) => {
    this._event = event
  }
  //移除订阅
  removeSubscribe = () => {
    this._event = null
  }
  //恢复
  resume = () => {
    this._exec(this.interval)
  }
  //清除
  clear = () =>{
    this._timmer && clearTimeout(this._timmer)
  }
}
export default Timmer
