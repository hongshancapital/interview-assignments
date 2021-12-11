import React, { CSSProperties, useEffect, useState } from 'react';
import { isMouseInRect, useCounter, useMouse } from '../../hooks';
import './style.scss';
import type {
  CarouselContainerProps,
  CarouselIndicatorProps,
  CarouselNaviProps,
  CarouselProps,
  CarouselTransitionProps,
} from './type';

/* ---------------------------------- Util ---------------------------------- */

const CssPrefix: string = 'cj';

/**
 * 转换css类名，自动添加css类名前辍
 * @param cssClassNames
 */
export function prefix(cssClassNames: string | string[]): string {
  const _classnames = typeof cssClassNames === 'string' ? cssClassNames.replace(/\s+/g, ' ').split(' ') : cssClassNames;

  if (Array.isArray(_classnames)) {
    return _classnames.map((it) => `${CssPrefix}-${it}`).join(' ');
  }
  throw new Error('prefix 参数无效');
}

/* ---------------------------- CarouselContainer --------------------------- */

const CarouselContainer: React.FC<CarouselContainerProps> = React.forwardRef(
  ({ width = '100%', height = '100%', children }, forwardedRef) => {
    return (
      <div ref={forwardedRef as any} className={prefix('carousel carousel-container')} style={{ width, height }}>
        {children}
      </div>
    );
  }
);

/* ------------------------------ CarouselItem ------------------------------ */

const CarouselItem: React.FC<{}> = function ({ children }) {
  return <div className={prefix('carousel-item')}>{children}</div>;
};

/* ------------------------------ CarouselNavi ------------------------------ */

const CarouselNavi: React.FC<CarouselNaviProps> = function ({ onPre, onNext }) {
  return (
    <>
      <div className={prefix('carousel-navi carousel-navi__next')} onClick={onNext}>
        right &gt;{' '}
      </div>
      <div className={prefix('carousel-navi carousel-navi__prev')} onClick={onPre}>
        {' '}
        &lt; left
      </div>
    </>
  );
};

/* ---------------------------- CarouselIndicator --------------------------- */

export const CarouselIndicator: React.FC<CarouselIndicatorProps> = React.memo(({ step, count, duration, onClick }) => {
  const [start, setStart] = useState(false);

  console.log(step, start, count, duration);

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
          <span
            className={prefix(clss.join(' '))}
            key={'carousel-indicator-item' + index}
            onClick={() => onClick && onClick(index)}
          >
            <i style={styles} />
          </span>
        );
      })}
    </div>
  );
});

/* -------------------------------- Animation util ------------------------------- */

const slide = (offsetX = 0, offsetY = 0, duration = 0) => ({
  transform: `translate3d(${offsetX}00%, ${offsetY}00%, 0)`,
  transitionDuration: duration * 1000 + 'ms',
});

const slideX = (offsetX = 0, duration = 0) => slide(offsetX, 0, duration);

/* --------------------------- CarouselTransition --------------------------- */

const CarouselTransition: React.FC<CarouselTransitionProps> = function ({
  step = 0,
  animation = 'slideLeft',
  children,
}) {
  const slideDir = { slideLeft: -1, slideRight: 1 }[animation];

  const animStyle = slideX(slideDir * step, 1);

  console.log(step, animStyle);

  // 1. css direction: rtl | ltr
  // 2. html dir="rtl" or dir="ltr"
  // rtl 控制 CarouselItem 从右向左排列 例如:  3 2 |1|
  // ltr 控制 CarouselItem 从左向右排列 例如:      |1| 2 3
  const dir = animation === 'slideRight' ? 'rtl' : 'ltr';

  return (
    <div className={prefix('carousel-transition')} style={animStyle} dir={dir}>
      {children}
    </div>
  );
};

/* --------------------------- DefineCarouselProps -------------------------- */

const defineCarouselProps = (props: CarouselProps): CarouselProps => {
  return props;
};

/* -------------------------------------------------------------------------- */
/* Carousel                                                                   */
/* -------------------------------------------------------------------------- */

const Carousel: React.FC<CarouselProps> = function (props) {
  /* 属性默认值 */
  const {
    width = '100%',
    height = '100%',
    overPause = false,
    showIndicator = true,
    showNavi = false,
    duration = 3000,
    animation = 'slideLeft',
    children,
  } = props;

  const count = React.Children.count(children);
  const rectRef = React.useRef<HTMLElement>();

  const counterOptions = {
    duration,
    maxCount: count,
    pauseWhen: () => {
      if (!overPause) return false;
      if (!rectRef.current) return false;
      return isMouseInRect(rectRef.current, 10);
    },
  };

  useMouse();
  const [step, { increase, decrease, setCurrent }] = useCounter(counterOptions);

  console.log(step, animation);

  return (
    <CarouselContainer ref={rectRef as React.Ref<HTMLElement>} {...{ width, height }}>
      <CarouselTransition {...{ step, animation }}>{children}</CarouselTransition>
      {showNavi && <CarouselNavi onPre={decrease} onNext={increase} />}
      {showIndicator && <CarouselIndicator {...{ step, duration, count, onClick: setCurrent }} />}
    </CarouselContainer>
  );
};

/* --------------------------------- Export --------------------------------- */

export { Carousel, CarouselItem, defineCarouselProps };
