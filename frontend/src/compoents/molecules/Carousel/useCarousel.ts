import {CarouselConfig} from "./Carousel";
import {RefObject, useEffect, useRef, useState} from "react";
import {usePersistFn} from '../../../hooks'
import {Timer, TimerState} from '../../../services'

const timer = new Timer()

export interface CarouselChangeInfo {
  startIdx: number,
  nextIdx: number,
}

export const useCarouselController = (config: CarouselConfig, childLength: number, carouselDomRef: RefObject<HTMLDivElement>) => {
  const { infinite = false, autoPlay = true, dir = 'rtl', duration = 3000, hoverPause = true } = config
  const [idxInfo, setIdxInfo] = useState<CarouselChangeInfo>({ startIdx: 1, nextIdx: 1});
  const [playCount, setPlayCount] = useState(0)
  const intervalTimerRef = useRef<NodeJS.Timeout>()
  const timeoutTimerRef = useRef<NodeJS.Timeout>()

  const changeCarousel = usePersistFn((action: 'prev' | 'next') => {
    const { nextIdx } = idxInfo
    if (action === 'next') {
      setIdxInfo({
        startIdx: nextIdx === childLength ? 0 : nextIdx,
        nextIdx: nextIdx === childLength ? 1 : nextIdx + 1,
      })
    }
    if (action === 'prev') {
      setIdxInfo({
        startIdx: nextIdx === 0 ? childLength : nextIdx,
        nextIdx: nextIdx === 0 ? childLength - 1 : nextIdx - 1,
      })
    }
    // Restart the timing while switching carousel
    timer.reset();
    timer.start();
  })

  const playCarousel = usePersistFn(() => {
    if (intervalTimerRef.current) {
      clearInterval(intervalTimerRef.current);
    }
    intervalTimerRef.current = setInterval(() => {
      changeCarousel(dir === 'rtl' ? 'next' : 'prev')
      if (!infinite) {
        if (playCount === childLength - 1 && intervalTimerRef.current) {
          clearInterval(intervalTimerRef.current)
        }
        setPlayCount(count => count + 1)
      }
    }, duration)
  })

  // start
  useEffect(() => {
    if (autoPlay) {
      timer.start();
      playCarousel()
    }
    return () => {
      if (intervalTimerRef.current) {
        clearInterval(intervalTimerRef.current)
      }
    }
  }, [autoPlay, playCarousel])

  // bindMouseEvent
  const pausePlay = usePersistFn(() => {
    if (timer.timerState === TimerState.timing && intervalTimerRef.current) {
      clearInterval(intervalTimerRef.current)
      timer.pause();
      // The last timer may have not been confirmed, it is destroyed here.
      if (timeoutTimerRef.current) {
        clearTimeout(timeoutTimerRef.current)
      }
    }
  })

  const resumePlay = usePersistFn(() => {
    const passedTime = timer.getPassedTime()
    if (timer.timerState === TimerState.suspend && passedTime < duration) {
      // The last timer may have not been confirmed, it is destroyed here.
      if (timeoutTimerRef.current) {
        clearTimeout(timeoutTimerRef.current)
      }
      timer.resume();
      timeoutTimerRef.current = setTimeout(() => {
        changeCarousel(dir === 'rtl' ? 'next' : 'prev')
        playCarousel()
      }, duration - passedTime)
    }
  })

  useEffect(() => {
    const dom = carouselDomRef.current
    if (hoverPause && dom) {
      dom.addEventListener('mouseenter', pausePlay)
      dom.addEventListener('mouseleave', resumePlay)
      document.addEventListener('visibilitychange', resumePlay)
    }
    return () => {
      if (hoverPause && dom) {
        dom.removeEventListener('mouseenter', pausePlay)
        dom.removeEventListener('mouseleave', resumePlay)
        document.removeEventListener('visibilitychange', resumePlay)
        if (timeoutTimerRef.current) {
          clearTimeout(timeoutTimerRef.current)
        }
      }
    }
  }, [carouselDomRef, hoverPause, pausePlay, resumePlay])

  // handle change idx
  const changeCarouselIdx = (idx: number) => {
    if (idx > 0 && idx <= childLength) {
      setIdxInfo({
        startIdx: idxInfo.nextIdx,
        nextIdx: idx,
      })
      timer.reset();
      timer.start();
      pausePlay();
      resumePlay();
    }
  }

  return {
    idxInfo,
    changeCarouselIdx
  }
}

export const useIndicators = (idxInfo: CarouselChangeInfo, duration: number, showIndicators: boolean) => {
  // indicator progress
  const [curProgress, setCurProgress] = useState(0)

  useEffect(() => {
    let reqsId: number
    const cb = () => {
      const passedTime = timer.getPassedTime();
      if (timer.timerState === TimerState.timing && passedTime <= duration) {
        setCurProgress(+(passedTime / duration * 100).toFixed(2))
      }
      reqsId = window.requestAnimationFrame(cb)
    }
    if (showIndicators) {
      reqsId = window.requestAnimationFrame(cb)
    }
    return () => {
      window.cancelAnimationFrame(reqsId)
    }
  }, [duration, idxInfo, showIndicators])

  return {
    curProgress
  }
}

export const useCarouselTransition = (idxInfo: CarouselChangeInfo, childLength: number) => {

  const [animationStyle, setAnimationStyle] = useState<any>({
    transform: `translate3d(${-idxInfo.nextIdx}00%, 0,0)`,
    transition: `transform 1000 ms`
  })

  const handleSetAnimationStyle = usePersistFn(() => {
    const { startIdx, nextIdx } = idxInfo
    if ((startIdx === 0 && nextIdx === 1) || (startIdx === childLength && nextIdx === childLength - 1)) {
      setAnimationStyle({
        transform: `translate3d(${-startIdx}00%, 0,0)`,
      })
      setTimeout(() => {
        setAnimationStyle({
          transform: `translate3d(${-nextIdx}00%, 0,0)`,
          transition: `transform 1000ms`
        })
      }, 20)
    } else {
      setAnimationStyle({
        transform: `translate3d(${-nextIdx}00%, 0,0)`,
        transition: `transform 1000ms`
      })
    }
  })

  useEffect(() => {
    handleSetAnimationStyle()
  }, [handleSetAnimationStyle, idxInfo])

  return {
    animationStyle
  }
}