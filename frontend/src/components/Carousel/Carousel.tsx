import React, {
  useRef,
  useMemo,
  useState,
  useCallback,
} from 'react';
import PropTypes from 'prop-types';
import classnames from 'classnames';

type Item = {
  img: string,
  title?: string,
  text?: string,
};

type CarouselProps = {
  className?: string,
  list: Item[] | string[],
  switchDuration?: number,
  previewDuration?: number,
};

const useEventCallback = <T,>(fn: T) => {
  const ref = useRef<T>(fn);

  ref.current = fn;

  return useCallback(() => {
    typeof ref.current === 'function' && ref.current();
  }, [ref]) as T;
};

const Carousel = React.forwardRef<HTMLDivElement, CarouselProps>((props, ref) => {
  const {
    className,
    list: propsList,
    switchDuration = 0.5,
    previewDuration = 2,
  } = props;

  const cls = classnames(
    className,
    'components-carousel-render'
  );

  const [currentIndex, setCurrentIndex] = useState(0);

  const list = useMemo(() => {
    if (!propsList) {
      return [];
    }

    return propsList.map((item) => {
      return typeof item === 'string'
        ? { img: item }
        : item;
    });
  }, [propsList]);

  const style = useMemo(() => {
    return {
      '--switch-duration': `${switchDuration}s`,
      '--preview-duration': `${previewDuration}s`,
    } as React.CSSProperties;
  }, [switchDuration, previewDuration]);

  const onTransitionEnd = useEventCallback(() => {
    setCurrentIndex((prevCurrentIndex) => {
      const { length } = list;

      return (prevCurrentIndex + 0.5) % length;
    });
  });

  const onAnimationEnd = useEventCallback(() => {
    setCurrentIndex((prevCurrentIndex) => {
      const { length } = list;

      return (prevCurrentIndex + 0.5) % length;
    });
  });

  const renderImgs = () => {
    const count = Math.ceil(currentIndex) > list.length - 1
      ? 0
      : Math.ceil(currentIndex);

    const testId = `carousel-imgs-${count}`;

    const imgsStyle = {
      transform: `translateX(${-100 * count}%)`,
    };

    const items = list.map((item, index) => {
      const { img, title = '', text = '' } = item;

      const itemTestId = `carousel-imgs-${img}`;
      const imgStyle = { backgroundImage: `url(${img})` };

      return (
        <div className="imgs-item" key={index} style={imgStyle} data-testid={itemTestId}>
          <div className="info">
            <div className="title">{ title }</div>
            <div className="text">{ text }</div>
          </div>
        </div>
      );
    });

    return (
      <div className="carousel-imgs" onTransitionEnd={onTransitionEnd} style={imgsStyle} data-testid={testId}>
        { items }
      </div>
    );
  };

  const renderAnchors = () => {
    const items = list.map((a, index) => {
      const itemTestId = `carousel-anchors-${index}`;

      const itemCls = classnames(
        'anchors-item',
        index === Math.floor(currentIndex) ? 'animated' : '',
      );

      const onClickItem = () => {
        setCurrentIndex(index - 0.5);
      };

      return (
        <div
          key={index}
          className={itemCls}
          data-testid={itemTestId}
          onClick={onClickItem}
          onAnimationEnd={onAnimationEnd}
        />
      );
    });

    return (
      <div className="carousel-anchors">
        { items }
      </div>
    );
  }

  return (
    <div ref={ref} className={cls} style={style}>
      { renderImgs() }
      { renderAnchors() }
    </div>
  );
});

export default Carousel;
