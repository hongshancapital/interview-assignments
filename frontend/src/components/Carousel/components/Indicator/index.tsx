import { CSSProperties, startTransition, useEffect, useState } from 'react';
import { IndicatorProps } from '../../types';
import styles from './index.module.scss';

function Indicator({ isCurrent, duration }: IndicatorProps) {
  const [inlineStyle, setInlineStyle] = useState<CSSProperties>({});
  useEffect(() => {
    if (isCurrent) {
      startTransition(() => {
        setInlineStyle({
          transition: `transform ${duration}ms`,
          transform: 'translateX(0)',
        });
      });
    } else {
      setInlineStyle({
        transition: 'none'
      });
    }
  }, [isCurrent, duration]);

  return (
    <div className={styles.Indicator}>
      <div className={styles.Highlight} style={inlineStyle} />
    </div>
  )
}

export default Indicator;