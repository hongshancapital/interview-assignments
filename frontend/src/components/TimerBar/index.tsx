import React, { useState, useEffect } from 'react';
import './index.scss'

interface ITimerBar {
  // 是否循环播放，如果不传，默认为true
  isCycle?: boolean;
  // 时间间隔，不传默认为1000ms
  time?: number;
  // 时间条数量，默认为1
  num?: number;
  // 到时 回调
  onTime?: (index: number) => any;
  className?: string;
}

const TimerBar = (props: ITimerBar) => {
  const { isCycle = true, time = 3000, num = 3, onTime, className = '' } = props;
  // 初始化进度条变量，如果有三个进度条，变量则为[0,0,0]
  const perArr = [...Array(num)].map(i => 0)
  const [percentage, setPercentage] = useState([...perArr])
  const arr = [...Array(num)].map((_, index) => index);

  useEffect(() => {
    const tick = time/20
    // 进度条index
    let curBar = 0 
    const interval = setInterval(()=>{
      // 当前进度条百分比
      let per = percentage[curBar]
      if(per >= 100) {
        curBar ++
        if(curBar >= num) {
          //所有进度条循环完毕
          if(isCycle) {
            // 如果循环播放，则重置进度，重头开始
            curBar = 0
            arr.forEach(v => {
              percentage[v] = 0
            })
            setPercentage([...perArr])
          } else {
            // 不循环则清空定时器
            clearInterval(interval)
          }
        }
        onTime?.(curBar)
      } else {
        percentage[curBar] += 5
        // console.log([...percentage]);
        setPercentage([...percentage])
      }
    },tick)

    return () => {
      // 组件销毁时 清空定时器
      clearInterval(interval)
    }
  }, [])

  return (<div className={`${className + ' '}tbContainer`}>
    {arr.map((v, index) => (<div className="timeBar" key={index}>
    <div style={{width: `${percentage[index]}%`}} className="goingLine"/>
  </div>))}
  </div>)
}

export { TimerBar };