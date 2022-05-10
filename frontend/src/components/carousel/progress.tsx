import React, { useCallback, useEffect, useRef } from 'react';
import './progress.css';

type ProgressPropsType = {
  active: boolean; // 启动动画
  duration: number; // 动画时长
  className?: string; // class name
  onFinish?: () => void; // 动画结束时触发
}

function Progress(props: ProgressPropsType) {
  const { active, duration, className = '', onFinish } = props;
  const animationRef = useRef<Animation | null>(null);
  const onAnimationFinish = useCallback(() => {
    if (onFinish) {
      onFinish();
    }
  }, [onFinish]);
  const barRef = useRef<HTMLDivElement>(null);
  useEffect(() => {
    let animation = animationRef.current;
    if (active && barRef.current && (!animation || animation.playState === 'finished')) {
      if (!animation) {
        animation = barRef.current.animate([
          { width: '0%' },
          { width: '100%' }
        ], {
          duration
        });
        animationRef.current = animation;
      } else {
        animation.play();
      }
    }
    animation?.addEventListener('finish', onAnimationFinish);
    return () => {
      animation?.removeEventListener('finish', onAnimationFinish);
    }
  }, [active, onAnimationFinish]);
  return (
    <div className={`progress-container ${className}`}>
      <div className="progress-bar" ref={barRef}></div>
    </div>
  )
}

export default Progress;