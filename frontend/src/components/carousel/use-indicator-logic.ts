import React, {useMemo} from "react";

// 进度指示器本身需要设置 transition: none;transform:translateX(-100%);
export const useIndicatorLogic = (duration?: number) => {
  const progressStyle = useMemo<React.CSSProperties>(() => {
    if(duration && duration > 0){
      return {
        transition: `transform ${duration}ms linear`,
        transform: 'translateX(0)'
      }
    }else {
      return {}
    }
  },[duration])

  return {progressStyle}
}