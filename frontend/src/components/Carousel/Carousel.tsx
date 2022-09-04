import * as React from "react";
import { useCarouselNavigate } from "./Provider";
import throttle from "./utils/throttle";

const Carousel: React.FC = (props) => {
  const { children } = props;
  const papersChildren = React.useMemo(
    () => (Array.isArray(children) ? children : [children]),
    [children]
  );

  if (papersChildren.some((item) => item.type.displayName !== "Paper")) {
    throw new Error("Carousel 只接受 Paper 作为子组件！");
  }

  const [wrapperWidth, setWrapperWidth] = React.useState(0);
  const wrapperRef = React.useRef<HTMLDivElement>();

  const { gotoNext, currentPaper, carouselConfig, setTotalPaper, totalPaper } =
    useCarouselNavigate();
  const { auto, speed, transitionDuration, cycle } = carouselConfig;

  React.useEffect(() => {
    setTotalPaper(papersChildren.length);
  }, [papersChildren, setTotalPaper]);

  React.useEffect(() => {
    const throttledSetWrapperWidth = throttle(() => {
      setWrapperWidth(wrapperRef.current?.clientWidth ?? 0);
    });
    window.addEventListener("resize", throttledSetWrapperWidth);
    return () => {
      window.removeEventListener("resize", throttledSetWrapperWidth);
    };
  }, []);

  React.useEffect(() => {
    let timer: any;
    if (auto) {
      timer = setInterval(() => {
        gotoNext();
      }, speed);
    }
    if (!cycle && timer && currentPaper === totalPaper - 1) {
      clearInterval(timer);
    }
    return () => {
      if (timer) {
        clearInterval(timer);
      }
    };
  }, [auto, gotoNext, speed, cycle, currentPaper, totalPaper]);

  return (
    <div
      className="carousel-wrapper"
      ref={(ref) => {
        if (ref) {
          wrapperRef.current = ref;
          setWrapperWidth(wrapperRef.current.clientWidth ?? 0);
        }
      }}
    >
      <div
        data-testid="carousel-content"
        className="carousel-content"
        style={{
          width: `${wrapperWidth * papersChildren.length}px`,
          transform: `translate3d(${
            currentPaper * (wrapperWidth ?? 0) * -1
          }px, 0, 0)`,
          transitionDuration: `${transitionDuration}ms`,
        }}
      >
        {papersChildren}
      </div>
    </div>
  );
};

export default Carousel;
