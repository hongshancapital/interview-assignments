import React, {
  PropsWithChildren,
  cloneElement,
  useEffect,
  useCallback,
  useState,
  useMemo,
  Children,
} from "react";
import "./carousel.scss";

interface CarouselProps {
  stayDuration?: number;
}

export default function Carousel({
  stayDuration = 2000,
  children,
}: PropsWithChildren<CarouselProps>) {
  const [cur, setCur] = useState(-1);

  const slideCount = useMemo(() => Children.count(children), [children]);

  const slideToNext = useCallback(
    (nextIndex) => {
      const _next = nextIndex >= slideCount ? 0 : nextIndex;
      setCur(nextIndex >= slideCount ? 0 : nextIndex);
      setTimeout(() => {
        slideToNext(_next + 1);
      }, stayDuration);
    },
    [slideCount, stayDuration]
  );

  useEffect(() => {
    slideToNext(0);
  }, []);

  return (
    <div className="carousel-container">
      {Array.isArray(children) ? (
        <div
          className="carousel-content"
          style={{ width: `${children.length}00%`, left: `-${cur}00%` }}
        >
          {children.map((v, index) =>
            cloneElement(v, {
              ...v.props,
              className: `carousel-item ${v.props?.className}`,
              key: index,
            })
          )}
        </div>
      ) : null}
      <div className="carousel-panel">
        {Array.isArray(children)
          ? children.map((item, order) => {
              const isActive = order === cur;
              return (
                <div
                  key={`${order}-PROGRESS`}
                  className="carousel-progress-wrapper"
                >
                  <div
                    className="carousel-progress"
                    style={{
                      width: isActive ? "100%" : 0,
                      transition: isActive
                        ? `${stayDuration / 1000}s width`
                        : "",
                    }}
                  />
                </div>
              );
            })
          : null}
      </div>
    </div>
  );
}
