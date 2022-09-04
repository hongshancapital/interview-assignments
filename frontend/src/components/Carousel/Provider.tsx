import * as React from "react";

const CarouselNavigateContext = React.createContext<{
  totalPaper: number;
  setTotalPaper: React.Dispatch<React.SetStateAction<number>>;
  currentPaper: number;
  carouselConfig: CarouselProviderProps;
  gotoPrev: () => void;
  gotoNext: () => void;
  goto: (paper: number) => void;
}>({
  totalPaper: 0,
  setTotalPaper: () => {},
  currentPaper: 0,
  carouselConfig: {
    cycle: false,
    auto: false,
    speed: 3000,
    transitionDuration: 300,
  },
  gotoPrev: () => {},
  gotoNext: () => {},
  goto: () => {},
});
export const useCarouselNavigate = () =>
  React.useContext(CarouselNavigateContext);

export interface CarouselProviderProps {
  /* 自动轮播，默认为 false */
  auto?: boolean;
  /* 自动轮播速度，默认 3000，即 3000ms，仅当 auto 为 true 时生效 */
  speed?: number;
  /* 是否循环播放，默认为 false */
  cycle?: boolean;
  /* 过渡动画速度，默认 300，即 300ms */
  transitionDuration?: number;
}

const Provider: React.FC<CarouselProviderProps> = (props) => {
  const {
    children,
    cycle = false,
    auto = false,
    speed = 3000,
    transitionDuration = 300,
  } = props;

  const [currentPaper, setCurrentPaper] = React.useState(0);
  const [totalPaper, setTotalPaper] = React.useState(0);

  const gotoPrev = React.useCallback(() => {
    if (!cycle && currentPaper === 0) {
      return;
    }
    if (cycle && currentPaper === 0) {
      setCurrentPaper(totalPaper - 1);
    } else {
      setCurrentPaper((prev) => prev - 1);
    }
  }, [cycle, currentPaper, totalPaper]);

  const gotoNext = React.useCallback(() => {
    if (!cycle && currentPaper === totalPaper - 1) {
      return;
    }
    if (cycle && currentPaper === totalPaper - 1) {
      setCurrentPaper(0);
    } else {
      setCurrentPaper((prev) => prev + 1);
    }
  }, [cycle, currentPaper, totalPaper]);

  const goto = React.useCallback(
    (paper: number) => {
      if (paper === currentPaper) {
        return;
      }
      if (paper > totalPaper - 1) {
        throw new Error("跳转目标大于轮播项总数！");
      }
      setCurrentPaper(paper);
    },
    [currentPaper, totalPaper]
  );

  return (
    <CarouselNavigateContext.Provider
      value={{
        totalPaper,
        setTotalPaper,
        currentPaper,
        carouselConfig: { auto, speed, cycle, transitionDuration },
        gotoPrev,
        gotoNext,
        goto,
      }}
    >
      {children}
    </CarouselNavigateContext.Provider>
  );
};

export default Provider;
