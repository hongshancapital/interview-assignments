import React, { FC, CSSProperties } from 'react';
import css from './index.module.scss';
import cx from 'classnames';

interface IIndicator {
  style?: CSSProperties;
  count: number;
  duration?: number;
  current?: number;
  setCur?: (value: number) => void;
};

const Indicator: FC<IIndicator> = ({
  style,
  count,
  duration = 3000,
  current,
  setCur
}) => {

  return (
    <div className={css.wrapper} style={style}>
      {Array(count).fill(undefined).map((_, index) => (
        <div
          className={css.item}
          key={index}
          onClick={() => setCur && setCur(index)}
        >
          <div
            className={cx({ [css.activeItem]: index === current })}
            style={{ transitionDuration: index === current ? `${duration}ms` : '' }}
          />
        </div>
      ))}
    </div>
  );
};

export default Indicator;
