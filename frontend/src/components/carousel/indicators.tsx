import React, { FC, useMemo, memo } from 'react';
import classnames from 'classnames';
import style from './index.module.scss';

export interface SelectFunction {
  (index: number): void
}

interface IndicatorsProps {
  activeIndex: number,
  count: number,
  progressDuration: number,
  onSelect: SelectFunction,
};
const Indicators: FC<IndicatorsProps> = memo(({
  activeIndex = 0,
  count,
  progressDuration = 3,
  onSelect,
}) => {
  const ranges = useMemo(() => {
    const ranges = new Array(count);
    ranges.fill(0)
    return ranges;
  }, [count]);

  const indicatorProgressStyle = useMemo(() => ({ animationDuration: `${progressDuration}s` }), [progressDuration]);
  const onClick = (index: number) => () => {
    onSelect(index);
  }
  return (
    <ul className={style.Indicators}>
      {
        ranges.map((_, index) =>
          <li key={index} role="link" onClick={onClick(index)} className={classnames(style.Indicator, { [style.IndicatorActive]: activeIndex === index })}>
            <div style={indicatorProgressStyle} />
          </li>
        )
      }
    </ul>
  )
})

export default Indicators;