import React, { CSSProperties, useEffect, useState } from 'react';
import { prefix, debug } from '../util';

export const CarouselIndicator: React.FC<{ step: number; count: number; duration: number }> = React.memo(
  ({ step, count, duration }) => {
    const [start, setStart] = useState(false);

    debug('[CarouselIndicator:render]: props.step = %s', step);

    let items = new Array(count).fill('');

    useEffect(() => {
      setStart(true);
    }, []);

    return (
      <div className={prefix('carousel-indicator')}>
        {items.map((_, index) => {
          const clss = ['carousel-indicator-item'];
          const styles: CSSProperties = {};
          if (step === index && start) {
            clss.push('carousel-indicator-item__active');
            styles.transitionDuration = duration + 'ms';
          }
          return (
            <span className={prefix(clss.join(' '))} key={index}>
              <i style={styles} />
            </span>
          );
        })}
      </div>
    );
  }
);
