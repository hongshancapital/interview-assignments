import React, {
  useReducer,
  useEffect,
  useRef,
  useMemo,
  forwardRef,
  useImperativeHandle,
  Ref,
  useCallback,
} from "react";
import CarouselItem from "./CarouselItem";
import CarouselDotItem from "./CarouselDotItem";
import { CarouselContext, CarouselDispatchContext } from "./CarouselContext";
import { INIT_STATE } from "./enum";
import carouselReducer from "./reducer";
import { getTransform } from "./util";
import { ICarouselProps, ICarouselMethods } from "./interface";
import "./index.scss";

function Carousel(props: ICarouselProps, ref: Ref<ICarouselMethods>) {
  const { interval = 3000, transitionDuration = 500 } = props;
  const children = useMemo(
    () =>
      React.Children.toArray(props.children).filter((child) =>
        React.isValidElement(child)
      ),
    [props.children]
  );
  const count = useMemo(() => children.length, [children]);
  const [state, dispatch] = useReducer(carouselReducer, {
    ...INIT_STATE,
    count,
  });
  const timerRef = useRef<NodeJS.Timer | null>(null);
  const carouselRef = useRef<HTMLDivElement>(null);
  const prevCount = useRef<number>(count);
  const onWindowResize = () =>
    dispatch({
      type: "resize",
      payload: {
        width: carouselRef.current!.offsetWidth,
        height: carouselRef.current!.offsetHeight,
      },
    });
  const clearTimer = () => {
    if (timerRef.current) {
      clearInterval(timerRef.current);
      timerRef.current = null;
    }
  };
  const doLoop = useCallback(() => {
    clearTimer();
    timerRef.current = setInterval(
      () =>
        dispatch({
          type: "next",
        }),
      interval
    );
  }, [interval]);
  const prev = useCallback(() => {
    dispatch({ type: "prev" });
    doLoop();
  }, [doLoop]);
  const next = useCallback(() => {
    dispatch({ type: "next" });
    doLoop();
  }, [doLoop]);
  const goTo = useCallback(
    (to: number) => {
      dispatch({ type: "to", payload: { current: to } });
      doLoop();
    },
    [doLoop]
  );
  const innerContext = useMemo(
    () => ({ prev, next, goTo }),
    [prev, next, goTo]
  );
  useImperativeHandle(ref, () => innerContext);
  useEffect(() => {
    if (prevCount.current !== count) {
      dispatch({
        type: "setCount",
        payload: { count },
      });
      prevCount.current = count;
      goTo(0);
    }
  }, [count, goTo]);
  useEffect(() => {
    doLoop();
    return () => {
      clearTimer();
    };
  }, [doLoop]);
  useEffect(() => {
    onWindowResize();
    window.addEventListener("resize", onWindowResize);
    return () => {
      window.removeEventListener("resize", onWindowResize);
    };
  }, []);
  const slidesStyle = getTransform(
    state.current * state.width,
    transitionDuration
  );
  return (
    <CarouselContext.Provider value={state}>
      <CarouselDispatchContext.Provider value={innerContext}>
        <div ref={carouselRef} className="carousel">
          <div className="carousel-slides" style={slidesStyle}>
            {children.map((child, idx) => (
              <CarouselItem key={idx} active={idx === state.current}>
                {child}
              </CarouselItem>
            ))}
          </div>
          {children.length > 1 && (
            <div className="carousel-dots">
              {children.map((_, idx) => (
                <CarouselDotItem
                  interval={interval}
                  key={idx}
                  active={idx === state.current}
                />
              ))}
            </div>
          )}
        </div>
      </CarouselDispatchContext.Provider>
    </CarouselContext.Provider>
  );
}

export default forwardRef(Carousel);
