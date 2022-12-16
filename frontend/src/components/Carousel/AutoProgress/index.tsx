import React, { useMemo } from 'react';
import Progress, { ProgressProps } from './Progress';
import usePercentCalc, { PercentCalcProps } from './usePercentCalc';
import { parseClassName } from '../common';
import './index.scss';

interface AutoProgressProps extends Omit<ProgressProps, 'percent'>, PercentCalcProps {
    onEnd?: () => void;
}

const AutoProgress = (props: AutoProgressProps) => {
  const {
    duration,
    stoped = false,
    paused = false,
    onEnd,
    className,
    ...extraProgressProps
  } = props;
  const calcPercentProps = useMemo(()=>({
    duration, stoped, paused, onEnd
  }), [duration, stoped, paused, onEnd]);
  const calcPercent = usePercentCalc(calcPercentProps);
  const wrapperClassName = useMemo(()=> {
    const classNameArr = ['auto-progress', className];
    return parseClassName(classNameArr);
  }, [className]);
  return (
        <Progress
            {...extraProgressProps}
            className={wrapperClassName}
            percent={calcPercent}
        />
  );
};


export type {
  AutoProgressProps
};
export default React.memo(AutoProgress);