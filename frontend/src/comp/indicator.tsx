import { CSSProperties, TransitionEventHandler, useLayoutEffect, useState } from 'react';
import './indicator.css';

type IndicatorProps = {
  count: number;
  current: number;
  duration: number;
  onTransitionEnd: TransitionEventHandler<HTMLDivElement>;
}

export default function Indicator({ count, current, duration, onTransitionEnd } : IndicatorProps) {
  const status = new Array(count).fill(null).map((_, idx) => idx <= current);

  return (
    <div className="indicator" style={{ transitionDuration: `${duration}ms` }} onTransitionEnd={onTransitionEnd}>
      {status.map((active, idx) => (
        <Block
          key={`${idx}`}
          active={active}
        />
      ))}
    </div>
  );
}

type IndicatorBlockProps = {
  active: boolean;
}

const finalStyle: CSSProperties = {
  transform: 'translateX(0)',
}

function Block({ active }: IndicatorBlockProps){
  const [style, setStyle] = useState<CSSProperties>({});

  useLayoutEffect(
    () => {
      setTimeout(
        () => {
          setStyle(active ? finalStyle : {});
        },
        17, // wait a frame, to ensure everything is ready
      )
    },
    [active]
  )
  return (
    <div className="indicator-block">
      {active && <div className="indicator-block-inner" style={style} />}
    </div>
  )
}