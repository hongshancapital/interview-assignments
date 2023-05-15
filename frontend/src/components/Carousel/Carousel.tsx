import { useEffect, useState } from "react";
import "./Carousel.css";
function Carousel({
  carousels,
  currentIndex,
  autoplay,
  transitionOptions,
  setCurrentIndex,
}: {
  carousels: any[];
  currentIndex: number;
  autoplay: number;
  transitionOptions: {
    duration: number;
    timingFunction: string;
    delay: number;
  };
  setCurrentIndex: Function;
}) {
  const [initStatus, setInitStatus] = useState(false);
  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentIndex(
        currentIndex + 1 < carousels.length ? currentIndex + 1 : 0
      );
    }, autoplay);
    setTimeout(() => {
      setInitStatus(true);
    }, 4);
    return () => {
      setInitStatus(false);
      clearInterval(timer);
    };
  }, [currentIndex, autoplay, carousels.length, setCurrentIndex]);
  return (
    <div className="carousel">
      <div
        className="carousel__wrapper"
        style={{
          transition: `transform ${transitionOptions.duration + 500}ms ${
            transitionOptions.timingFunction
          } ${transitionOptions.delay}ms`,
          transform: `translateX(-${100 * currentIndex}%)`,
        }}
      >
        {carousels.map((item, index) => {
          return (
            <div key={index} className="carousel__wrapper__item">
              {item}
            </div>
          );
        })}
      </div>
      <div
        className="carousel__pagination"
        style={{ left: `calc(50% - ${(56 * carousels.length) / 2}px)` }}
      >
        {carousels.map((item, index) => {
          return (
            <div
              key={index}
              className="carousel__pagination__item"
              style={{ width: "48px", margin: "0 4px", height: "3px" }}
            >
              <div
                style={{
                  transition:
                    index === currentIndex
                      ? `transform ${autoplay}ms linear`
                      : "",
                }}
                className={[
                  "carousel__pagination__item__bar",
                  initStatus && index === currentIndex
                    ? "carousel__pagination__item__bar--moving"
                    : "",
                ].join(" ")}
              ></div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
export default Carousel;
