import { useCallback, useEffect, useRef, useState } from 'react';
import { isDef, getTimestamp } from '../common';

interface PercentCalcProps {
    duration: number; // 一个周期为duration(单位：ms)
    stoped?: boolean; // 是否停止
    paused?: boolean; // 是否暂停
    onEnd?: ()=>void; // 进度100%时的回调
}
/**
 * 和播放器的进度条逻辑一致，三个参数可以当作播放时长、停止、暂停
 * @param props PercentCalcProps
 * @return percent : number
 */
const usePercentCalc = (props: PercentCalcProps) => {
  const { duration, stoped = false, paused = false, onEnd } = props;
  const [percent, setPercent] = useState(0);
  const startTimeRef = useRef<number|null>(null);
  const pausedTimeRef = useRef<number|null>(null);
  const pausedDuration = useRef<number>(0);
  // 是否暂停计算时间
  const checkToStop = useCallback(()=>{
    if(stoped) {
      // 重置数据
      startTimeRef.current = null;
      pausedTimeRef.current = null;
      pausedDuration.current = 0;
      setPercent(0);
      return true;
    }
    // 暂停时中止计算，记录暂停时间
    if(paused) {
      if(!isDef(pausedTimeRef.current)) {
        pausedTimeRef.current = getTimestamp();
      }
      return true;
    }
    return false;
  }, [stoped, paused]);
  // 封装setPercent方法，使其只能赋0~100的值，且控制percent的精度
  const updateValidatePercent = useCallback((val: number) => {
    const getValidayePercent = () => {
      if(val<=0) return 0;
      if(val>=100) return 100;
      return val;
    };
    const oneDecimalPlace = (num: number) => (~~(num * 10)) / 10;
    const validatePercent = oneDecimalPlace(getValidayePercent());
    setPercent(validatePercent);
  }, []);
  // 计算percent并赋值
  const calcPercent = useCallback(()=> {
    if(!isDef(startTimeRef.current)) {
      startTimeRef.current = getTimestamp();
    }
    if(isDef(pausedTimeRef.current)) {
      pausedDuration.current += getTimestamp() - (pausedTimeRef.current as number);
      pausedTimeRef.current = null;
    }
    const diffTime = getTimestamp() - (startTimeRef.current as number) - pausedDuration.current;
    const calcPercent = diffTime/duration * 100;
    updateValidatePercent(calcPercent);
    return calcPercent > 100;
  }, [duration, updateValidatePercent]);
  // 利用raf在屏幕刷新前当前进度
  useEffect(()=>{
    // 停止时充值数据
    if(checkToStop()) return;
    // 正常状态下利用raf轮询计算当前百分比
    let raf: number;
    const loopFunc = ()=> {
      const stopLoop = calcPercent();
      if(stopLoop) {
        onEnd && onEnd();
        return;
      }
      raf = requestAnimationFrame(loopFunc);
    };
    loopFunc();
    return ()=>cancelAnimationFrame(raf);
  }, [checkToStop, calcPercent, onEnd]);

  return percent;
};

export type {
  PercentCalcProps
};
export default usePercentCalc;