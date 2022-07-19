import React, { useImperativeHandle, useRef, useState, useMemo } from 'react';

interface SliderItemProps {
  readonly style?: React.CSSProperties;
  children: React.ReactNode;
}

export interface SliderItemRef {
  setOffset: React.Dispatch<React.SetStateAction<number>>;
}

const SliderItem = React.forwardRef<SliderItemRef, SliderItemProps>((props, ref) => {
  const { children, style } = props;
  const [offset, setOffset] = useState(0);
  const swipeItemRef = useRef<HTMLDivElement>(null);

  useImperativeHandle(ref, () => {
    return {
      setOffset,
    };
  });

  const itemStyle = useMemo(() => {
    return {
      transform: offset ? `translate${'X'}(${offset}px)` : '',
      ...style,
    };
  }, [offset, style]);

  return (
    <div ref={swipeItemRef} className='slider__item' style={itemStyle}>
      {children}
    </div>
  );
});

export default SliderItem;
