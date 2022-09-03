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

  const { gotoNext, currentPaper, carouselConfig, setTotalPaper } =
    useCarouselNavigate();
  const { auto, speed, transitionDuration } = carouselConfig;

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
    return () => {
      if (timer) {
        clearInterval(timer);
      }
    };
  }, [auto, gotoNext, speed]);

  return (
    <div
      className="carousel-wrapper"
      ref={(ref) => {
        if (ref) {
          wrapperRef.current = ref;
          setWrapperWidth(ref.clientWidth ?? 0);
        }
      }}
    >
      <div
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
