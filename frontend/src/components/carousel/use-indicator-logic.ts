import React, {useEffect, useMemo, useState} from "react";
import {CarouselProps} from "./type";

// 进度指示器本身需要设置 transition: none;transform:translateX(-100%);
export const useIndicatorLogic = (props: CarouselProps) => {
  const { duration, children } = props

  const [progressStyle, setProgressStyle] = useState<React.CSSProperties>({})

  useEffect(() => {
    if(duration && duration > 0){
      setProgressStyle({
        transition: `transform ${duration}ms linear`,
        transform: 'translateX(0)'
      })
    }else{
      setProgressStyle({})
    }
  },[duration])

  const childCount = useMemo(() => React.Children.count(children), [children])

  return {progressStyle, childCount}
}