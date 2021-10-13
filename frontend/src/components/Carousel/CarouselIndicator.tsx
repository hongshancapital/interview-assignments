import React, { useEffect, useState } from 'react';
import { prefix, debug } from '../util';

export const CarouselIndicator: React.FC<{ step: number; count: number }> = React.memo((props) => {
  const [start, setStart] = useState(false);

  debug('[CarouselIndicator:render]: props.step = %s', props.step);

  let items = new Array(props.count).fill('');

  useEffect(() => {
    setStart(true);
  }, []);

  return (
    <div className={prefix('carousel-indicator')}>
      {items.map((_, index) => {
        const clss = ['carousel-indicator-item'];
        props.step === index && start && clss.push('carousel-indicator-item__active');
        return <span className={prefix(clss.join(' '))} key={index} />;
      })}
    </div>
  );
});
