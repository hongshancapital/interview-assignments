import React, {
  useRef,
  useState,
  useEffect,
  useMemo,
  useCallback,
  Children,
} from "react";
import Indicator from "./Indicator";
import "./Carousel.css";

interface CarouselProps {
  children: React.ReactNode;
  autoplay?: boolean;
  switchDuration?: number;
  previewDuration?: number;
  onPageSelected?: (position: number) => void;
}

const Carousel = ({
  children,
  autoplay = false,
  switchDuration = 0.5,
  previewDuration = 3,
  onPageSelected,
}: CarouselProps) => {
  const containerRef = useRef<HTMLDivElement>(null);
  const [containerRect, setContainerRect] = useState<DOMRect>();
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  const previousIndex = useRef<number>(0);
  const timeoutIdRef = useRef<number | null>(null);
  const childrenArray = useMemo(() => Children.toArray(children), [children]);
  const clearTimeoutIfNeeded = useCallback(() => {
    if (timeoutIdRef.current) {
      window.clearTimeout(timeoutIdRef.current);
      timeoutIdRef.current = null;
    }
  }, []);
  const next = useCallback(() => {
    clearTimeoutIfNeeded();
    setCurrentIndex((i) => {
      return i === childrenArray.length - 1 ? 0 : i + 1;
    });
  }, [childrenArray, clearTimeoutIfNeeded]);
  const goTo = useCallback(
    (index: number) => {
      clearTimeoutIfNeeded();
      setCurrentIndex(index);
    },
    [clearTimeoutIfNeeded]
  );
  useEffect(() => {
    if (onPageSelected && previousIndex.current !== currentIndex) {
      onPageSelected(currentIndex);
    }
    previousIndex.current = currentIndex;
  }, [onPageSelected, currentIndex]);
  useEffect(() => {
    if (autoplay) {
      timeoutIdRef.current = window.setTimeout(() => {
        next();
      }, previewDuration * 1000);
    }
    return () => {
      if (timeoutIdRef.current) {
        clearTimeout(timeoutIdRef.current);
        timeoutIdRef.current = null;
      }
    };
  }, [autoplay, currentIndex, previewDuration, next]);
  useEffect(() => {
    const getContainerRect = () => {
      if (containerRef.current) {
        const rect = containerRef.current.getBoundingClientRect();
        setContainerRect(rect);
      }
    };
    getContainerRect();
    const resizeFn = () => {
      getContainerRect();
    };
    window.addEventListener("resize", resizeFn);
    return () => {
      window.removeEventListener("resize", resizeFn);
    };
  }, []);
  return (
    <div ref={containerRef} className="carousel-container">
      <div
        style={{
          width: (containerRect?.width ?? 0) * childrenArray.length,
          left: `-${currentIndex * 100}%`,
          transition: `left ${switchDuration}s linear`,
        }}
        className="carousel-children-container"
      >
        {childrenArray.map((v, i) => {
          return (
            <div
              key={i}
              style={{ width: containerRect?.width ?? 0, flexShrink: 0 }}
              data-testid="carousel-item"
              data-selected={currentIndex === i}
            >
              {v}
            </div>
          );
        })}
      </div>
      {
        <Indicator
          autoplay={autoplay}
          currentIndex={currentIndex}
          childrenCount={childrenArray.length}
          previewDuration={previewDuration}
          onClick={(index) => {
            goTo(index);
          }}
        />
      }
    </div>
  );
};

export default Carousel;
