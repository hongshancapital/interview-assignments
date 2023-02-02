import React, {
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
  memo
} from "react";
import classNames from "classnames";
import "./style/index.css";
export interface ICarouselProps {
  current?: number;  //初始化值
  easing?: 'linear' | 'ease' | 'ease-in' | 'ease-out' | 'ease-in-out'; //切换效果的转速曲线
  autoplay?: boolean; //是否自动播放
  onChange?: (slide: number) => void; //切换面板的回调，返回索引
  dots?: boolean; //是否显示面板指示点
  interval?:number; // 切换间隔时间
  children?: React.ReactNode[];
}

export interface ICarouselRef {
  goTo: (slide: number) => void;
  next: () => void;
  prev: () => void;
}

const InternalCarousel : React.ForwardRefRenderFunction<ICarouselRef, ICarouselProps> = (props,ref) => {
  const {
    onChange,
    current = 0,
    autoplay,
    easing = 'ease',
    children,
    dots = true,
    interval = 2000
  } = props;
  const [currentIndex, setCurrentIndex] = useState(current);
  const timerRef = useRef<ReturnType<typeof setInterval> | null>(null);
  const CarouselLength = React.Children.count(children);


  useEffect(()=>{
    if(CarouselLength === 0){
      console.warn('没有任何面板')
    }
    //避免越界
    if(current > CarouselLength-1){
      setCurrentIndex(CarouselLength-1)
    }
  },[current,CarouselLength])


  const handleClickDot = useCallback(
    (index: number) => () => {
      setCurrentIndex(index);
      onChange?.(index);
    },
    [onChange]
  );

  React.useImperativeHandle(
    ref,
    () => ({
      goTo: (index) => {
        if(index > CarouselLength-1){
          console.warn('超出最大面板值')
          return
        }
        handleClickDot(index)();
        onChange?.(index);
      },
      prev: () => {
        if (currentIndex > 0) {
          setCurrentIndex((currentIndex) => currentIndex - 1);
          onChange?.(currentIndex - 1);
        } else {
          setCurrentIndex(CarouselLength - 1);
          onChange?.(CarouselLength - 1);
        }
      },
      next: () => {
        if (currentIndex < CarouselLength - 1) {
          setCurrentIndex((currentIndex) => currentIndex + 1);
          onChange?.(currentIndex + 1);
        } else {
          setCurrentIndex(0);
          onChange?.(0);
        }
      },
    }),
    [currentIndex,CarouselLength,onChange,handleClickDot]
  );


  useEffect(() => {
    if (autoplay) {
      if (typeof interval !== "number" || interval < 0) {
        return;
      }
      timerRef.current = setInterval(() => {
        if (currentIndex >= CarouselLength - 1) {
          setCurrentIndex(0);
          onChange?.(0);
        } else {
          setCurrentIndex((currentIndex) => currentIndex + 1);
          onChange?.(currentIndex + 1);
        }
      }, interval);

      return () => {
        if (timerRef.current) {
          clearInterval(timerRef.current);
        }
      };
    }
  }, [autoplay, interval, currentIndex, onChange,CarouselLength]);

  // console.log("当前展示面板:", currentIndex, ",总面板数:",CarouselLength);

  const offset = useMemo(
    () => `calc(${-1 * currentIndex} * 100vw)`,
    [currentIndex]
  );


  function renderChildren() {
    return React.Children.map(new Array(CarouselLength), (child:React.ReactNode, index:number) => (
      <Dot
        key={index}
        active={index === currentIndex}
        onClick={handleClickDot(index)}
      />
    ));
  }

  const carouselListStyle:React.CSSProperties = {
    left: offset,
    transitionTimingFunction: easing,
  }

  return (
    <div className="carousel-container">
      <ul className="carousel-list" style={carouselListStyle}>
        {children}
      </ul>
      {dots ? (
        <ul className="carousel-dot">{renderChildren()}</ul>
      ) : null}
    </div>
  );
};

interface IDotProps {
  key: string | number;
  active: boolean;
  onClick?: () => void;
}

const Dot: React.FC<IDotProps> = memo(({ active, ...restProps }) => {
  const className = classNames("carousel-dot-item", { active});
  return <li {...restProps} className={className}></li>;
});

const Carousel = React.forwardRef<ICarouselRef, ICarouselProps>(InternalCarousel);


export default Carousel;
