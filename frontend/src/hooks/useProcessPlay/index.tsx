import { useState, useMemo, useCallback, useEffect } from 'react';

interface Params {
  duration?: number; // 周期时间
  loop?: boolean; // 是否循环播放
  onStart?: () => void; // 播放为 0% 触发
  onEnd?: () => void;  // 播放为 100% 触发
}

function useProcessPlay(params: Params = {}) {
  const {
    duration = 5000,
    loop = true,
    onStart = () => {},
    onEnd = () => {},
  } = params;
  const [value, setValue] = useState<number>(0);
  const [state, setState] = useState(true);
  const [_onStart] = useState(() => onStart);
  const [_onEnd] = useState(() => onEnd);

  const stepValue = useMemo(() => { // 简单计算每帧增加数量
    return Number((100 * 16.67 / duration ).toFixed(4))
  }, [duration]);

  useEffect(() => { // 简单触发事件
    if (value === 100) {
      _onEnd()
    } else if (value === 0) {
      _onStart()
    }
  }, [value, _onStart, _onEnd]);

  useEffect(() => {
    if (!state) {
      return;
    }
    let rafId: null|number = null
    let canceled = false;
    const cancel = () => { // 取消播放
      rafId && window.cancelAnimationFrame(rafId);
      rafId = null;
      canceled = true;
    }
    const play = () => { // 递归播放
      if (canceled) {
        return;
      }
      rafId = window.requestAnimationFrame(play)
      setValue((val) => {
        if (val === 100) {
          return 0;
        }
        const nextValue = val + stepValue;
        if (nextValue >= 100) {
          if (!loop) {
            cancel()
          }
          return 100;
        }
        return nextValue;
      });
    }
    setTimeout(play);
    return cancel;
  }, [state, stepValue, loop])

  const start = useCallback(() => setState(true), []);
  const stop = useCallback(() => setState(false), []);
  const reset = useCallback(() => {
     setValue(0)
  }, []);
  return {
    value,
    setValue,
    start,
    stop,
    reset
  };
}

export default useProcessPlay;
