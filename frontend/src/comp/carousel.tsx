import { Children, PropsWithChildren, TransitionEventHandler, useEffect, useMemo, useRef, useState } from "react"
import classNames from 'classnames';

import './carousel.css';
import Indicator from "./indicator";
import { useStepper } from "../logic/stepper";

export type CarouselProps = PropsWithChildren<{
  className?: string;
  duration?: number;
}>;

export default function CarouselBox(props: CarouselProps) {
  const { children: sourceChildren, className, duration = 5000 } = props;
  const children = useMemo(
    () => Children.map(
      sourceChildren,
      (child, idx) => <div key={`${idx}`} className="carousel-panel">{child}</div>,
    ),
    [sourceChildren]
  );
  const count = useMemo(() => Children.count(sourceChildren), [sourceChildren]);
  const viewportRef = useRef<HTMLDivElement>(null);

  const next = useStepper(count);
  const [current, setCurrent] = useState(0);
  const transitonStyle = useMemo(
    () => ({ transform: `translateX(-${current * 100}%)` }),
    [current]
  );

  useEffect(
    () => {
      const timer = setTimeout(
        () => {
          const step = next();
          setCurrent(step);
        },
        0,
      );
      return () => clearTimeout(timer);
    },
    [next]
  );

  const onNext: TransitionEventHandler<HTMLDivElement> = (event) => {
    const step = next();
    if (step === 0) {
      setTimeout(
        () => setCurrent(step),
        33,
      );
      setCurrent(-1); // reset when index is overflowed
    } else {
      setCurrent(step);
    }
  }

  return (
    <div className={classNames('carousel', className)}>
      <div className="carousel-viewport" ref={viewportRef} style={transitonStyle}>
        {children}
      </div>
      <Indicator count={count} current={current} duration={duration} onTransitionEnd={onNext} />
    </div>
  )
}