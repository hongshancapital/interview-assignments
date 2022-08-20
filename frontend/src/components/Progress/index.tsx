/*
 * @Author: xzg 
 * @Date: 2022-08-20 01:39:41 
 * @Last Modified by: xzg
 * @Last Modified time: 2022-08-20 12:53:00
 */
import { useRef, useEffect } from 'react';
import React from 'react';
import './styles.css'


const DEFAULT_TIME = 3000
const DEFAULT_WIDTH = 50
const DEFAULT_HEIGHT = 4
/**
 * 页面指示器组件
 * @param pid 指示器对应的poster的id {number}
 * @param time 指示器进度条完成一次需要的时间，单位秒 {number}
 * @param posterIndex 当前显示的posterIndex的下标 {number}
 * @param pWidth 进度条宽度 {number}
 * @returns 
 */
export type Props = {
  pid: number,
  time?: number,
  posterIndex: number,
  pWidth?: number,
  pHeight?: number,
  autoPlay?: boolean
}

/**
 * @param time 进度条执行一次所需时间 {number}
 * @param pWidth 进度条长度 {number}
 * @param pHeight 进度条宽度 {number}
 * @param pid 轮播图id {number} 
 * @param autoPlay 是否自动播放 {boolean}
 * @param posterIndex 当前poster下标 {number}
 */
function Progress(
  { time = DEFAULT_TIME, pWidth = DEFAULT_WIDTH, pHeight = DEFAULT_HEIGHT, pid, autoPlay, posterIndex }: Props
) {
  const progressRef = useRef<HTMLDivElement>(null);
  function setProgressBarWidth(scaleXNum: number) {
    if (progressRef.current) {
      progressRef.current.style.transform = "scaleX(" + scaleXNum + "%)"
    }
  }

  useEffect(() => {
    let start: number;
    let scaleXNum: number = 0;
    if (!autoPlay) return;
    function increaseRate(timestamp: number) {
      if (start === undefined) {
        start = timestamp;
      }
      let elapsed: number = timestamp - start;
      if (elapsed > time) {
        elapsed = 0
      }
      scaleXNum = elapsed / time * 100;
      setProgressBarWidth(scaleXNum);
      window.requestAnimationFrame(increaseRate);
    }
    window.requestAnimationFrame(increaseRate);
  }, [posterIndex, autoPlay, time])

  return (
    <div className="Progress">
      <div className="progress-wrap">
        <div className="progress-bar progress-outer"
          style={{ width: pWidth, height: pHeight, borderRadius: pHeight }}>
          <div
            className={`progress-bar progress-inner ${pid === posterIndex ? 'progress-active' : 'progress-inactive'}`}
            style={{ width: pWidth, height: pHeight, borderRadius: pHeight }}
            ref={progressRef}
          >
          </div>
        </div>
      </div>
    </div>
  );
}

export default Progress;