import React, {
  Children,
  cloneElement,
  FC,
  ReactNode,
  TransitionEventHandler,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import { useResizeObserver } from "../../hooks";

import styles from "./index.module.css";
import CarouselItem from "./CarouselItem";
import Indicator from "./Indicator";

interface CarouselProps {
  children: ReactNode;
  autoplay?: boolean;
  delay?: number;
  onChange?: (index: number) => void;
}

const Carousel: FC<CarouselProps> = (props) => {
  const { children, delay = 3000, autoplay = false, onChange } = props;
  const count = Children.count(children);
  const ref = useRef<HTMLDivElement>(null);
  const [index, setIndex] = useState(0);
  const size = useResizeObserver(ref);
  // translate offset
  const offset = useMemo<number>(() => {
    if (!size) return 0;
    return size.width * index;
  }, [size, index]);
  // closure trap
  const onChangeRef = useRef<CarouselProps["onChange"]>();
  onChangeRef.current = onChange;

  const carouselPanelStyle = {
    transform: `translate3d(-${offset}px,0px,0px)`,
    transition: index > 0 || !autoplay ? "transform 400ms ease-in" : "none",
  };

  const renderItems = () => {
    const newChildren = Children.map(children, (child, index) => {
      return (
        <CarouselItem key={index} style={size}>
          {child}
        </CarouselItem>
      );
    });
    if (newChildren && newChildren.length) {
      newChildren.push(
        cloneElement(newChildren[0], { key: newChildren.length })
      );
    }
    return newChildren;
  };

  const handleTransitionEnd: TransitionEventHandler = () => {
    // Scroll circularly in one direction
    if (index >= count) {
      setIndex(0);
    }
  };
  const gotoIndex = (i: number) => {
    if (i === index) return;
    let realIndex = i;
    if (i >= count) realIndex = 0;
    setIndex(i);
    onChangeRef.current && onChangeRef.current(realIndex);
  };

  useEffect(() => {
    if (!autoplay) return;
    const timer = setTimeout(() => {
      gotoIndex(index + 1);
    }, delay);
    return () => {
      clearTimeout(timer);
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [index, delay, autoplay]);

  return (
    <div ref={ref} className={styles.carousel}>
      <div
        style={carouselPanelStyle}
        className={styles.carouselPanel}
        onTransitionEnd={handleTransitionEnd}
      >
        {renderItems()}
      </div>
      <Indicator
        activeIndex={index < count ? index : 0}
        delay={delay}
        count={count}
        autoplay={autoplay}
        onChange={gotoIndex}
      />
    </div>
  );
};

export default Carousel;
