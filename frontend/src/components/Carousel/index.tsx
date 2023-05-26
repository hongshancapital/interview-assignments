import styled from "styled-components";
import {CSSProperties, ReactNode, useEffect, useRef, useState, UIEvent, useCallback} from "react";
import {useDebounceCallback, useSize} from "./hooks";
import NavBar from "./NavBar";
import { CarouselDirection } from "./type";

const getScrollBarStyle = (props: {$hideScrollBar: boolean}) => {
  if (props.$hideScrollBar) {
    return `
      -ms-overflow-style: none;
      scrollbar-width: none;
      
      &::-webkit-scrollbar {
        display: none;
      }
    `;
  }
  return ``
}

const getSnapType = (props: {$direction: CarouselDirection; $snap: boolean}) => {
  if (!props.$snap) return 'none';
  if (props.$direction === CarouselDirection.Horizontal) {
    return 'x mandatory';
  } else {
    return 'y mandatory';
  }
}

const Container = styled.div`
  position: relative;
  width: 100vw;
  height: 100vh;
`

const Scroll = styled.div<{ $direction: CarouselDirection; $snap: boolean; $hideScrollBar: boolean }>`
  position: relative;
  width: 100%;
  height: 100%;
  overflow-x: ${props => props.$direction === CarouselDirection.Horizontal ? 'auto' : 'hidden' };
  overflow-y: ${props => props.$direction === CarouselDirection.Vertical ? 'auto' : 'hidden' };
  display: flex;
  flex-direction: ${props => props.$direction === CarouselDirection.Vertical ? 'column' : 'row'};
  scroll-snap-type: ${getSnapType};
  ${getScrollBarStyle}
`

const Item = styled.div<{ $snap: boolean; $direction: CarouselDirection; }>`
  flex: 1 0 100%;
  width: 100%;
  height: 100%;
  scroll-snap-align: center;
  scroll-snap-stop: always;
`

interface Props<T extends unknown> {
  /**
   * @description 需要渲染的列表数据
   */
  data: ReadonlyArray<T>;

  /**
   * @description 生成唯一的key
   * @param item
   * @param index
   */
  keyExtractor(item: T, index: number): string | number;

  /**
   * @description render item
   * @param item
   */
  renderItem(item: { item: T; index: number }): ReactNode;

  /**
   * @description 容器样式类
   */
  containerClassName?: string;
  /**
   * @description 容器的样式
   */
  containerStyle?: CSSProperties;
  /**
   * @description carousel 方向
   */
  direction?: CarouselDirection;
  /**
   * @description 子元素的样式类
   */
  itemClassName?: string;
  /**
   * @description 子元素的样式表
   */
  itemStyle?: CSSProperties;
  /**
   * @description 是否开启吸附
   */
  enableSnap?: boolean;
  /**
   * @description 是否倒放
   * @tip 只有在`enableSnap`开启的情况下才会生效
   */
  inverted?: boolean;
  /**
   * @description 是否自动播放
   * @tip 只有在`enableSnap`开启的情况下才会生效
   */
  autoPlay?: boolean;
  /**
   * @description 自动播放的延时
   * @tip 只有在`enableSnap`开启的情况下才会生效
   */
  autoplayDelay?: number;
  /**
   * @description 首次展示第几个
   * @tip 只有在`enableSnap`开启的情况下才会生效
   */
  firstItem?: number;
  /**
   * @description 是否开启循环
   * @tip 只有在`enableSnap`开启的情况下才会生效
   */
  loop?: boolean;
  /**
   * @description 隐藏滚动条
   */
  hideScrollBar?: boolean;
}

export default function Carousel<T>(props: Props<T>) {
  const {
    direction = CarouselDirection.Horizontal,
    data,
    enableSnap = true,
    containerClassName,
    containerStyle,
    itemClassName,
    itemStyle,
    keyExtractor,
    renderItem,
    firstItem = 1,
    autoplayDelay = 3000,
    loop = true,
    inverted,
    hideScrollBar = true
  } = props;
  const [current, setCurrent] = useState(() => {
    return firstItem <= 1 ? 0 : firstItem - 1;
  });
  const [scrolling, setScrolling] = useState(false);
  const scrollTimer = useRef<NodeJS.Timer>();
  const itemRef = useRef<HTMLDivElement>(null);
  const itemSize = useSize<HTMLDivElement>(itemRef);
  const containerRef = useRef<HTMLDivElement>(null);
  const onScroll = useDebounceCallback(_onScroll, [], 100);
  const finishAnimation = useCallback(_finishAnimation, [current]);

  function _onScroll(e: UIEvent<HTMLDivElement>) {
    scrollTimer.current && clearTimeout(scrollTimer.current);
    setScrolling(true);
    if (direction === CarouselDirection.Vertical && itemSize.current.height) {
      const _current = ~~((e.target as HTMLDivElement).scrollTop / itemSize.current.height);
      setCurrent(_current);
    }
    if (direction === CarouselDirection.Horizontal && itemSize.current.width) {
      const _current = ~~((e.target as HTMLDivElement).scrollLeft / itemSize.current.width);
      setCurrent(_current);
    }
    scrollTimer.current = setTimeout(() => {
      setScrolling(false)
    }, 300);
  }

  function initFirstItem() {
    if (!enableSnap) return;
    const scrollOption: ScrollToOptions = {};
    if (direction === CarouselDirection.Horizontal && itemSize.current.width) {
      scrollOption.left = current * itemSize.current.width;
    }
    if (direction === CarouselDirection.Vertical && itemSize.current.height) {
      scrollOption.top = current * itemSize.current.height;
    }
    containerRef.current?.scrollTo(scrollOption)
  }

  function _finishAnimation() {
    if (!loop && (current === 0 || current === data.length - 1)) return;
    if (inverted) {
      if (current === 0) {
        scrollToIndex(data.length - 1);
      } else {
        scrollToIndex(current - 1);
      }
    } else {
      if (current === data.length - 1) {
        scrollToIndex(0);
      } else {
        scrollToIndex(current + 1);
      }
    }
  }

  function scrollToIndex(index: number) {
    if (direction === CarouselDirection.Horizontal && itemSize.current.width) {
      containerRef.current?.scrollTo({
        left: index * itemSize.current.width,
        behavior: 'smooth'
      })
    }
    if (direction === CarouselDirection.Vertical && itemSize.current.height) {
      containerRef.current?.scrollTo({
        top: index * itemSize.current.height,
        behavior: 'smooth'
      })
    }
  }

  useEffect(() => {
    if (current > 0) {
      initFirstItem();
    } else {}
  }, []);

  return (
    <Container className={containerClassName} style={containerStyle}>
      <Scroll
        ref={containerRef}
        onScroll={onScroll}
        $direction={direction}
        $snap={enableSnap}
        $hideScrollBar={hideScrollBar}
      >
        {data.map((item, index) => (
          <Item
            ref={itemRef}
            className={itemClassName}
            style={itemStyle}
            $direction={direction}
            $snap={enableSnap}
            key={keyExtractor(item, index)}
          >
            {renderItem({item, index})}
          </Item>
        ))}
      </Scroll>
      <NavBar
        data={data}
        finishAnimation={finishAnimation}
        keyExtractor={keyExtractor}
        scrolling={scrolling}
        autoplayDelay={autoplayDelay}
        current={current}
        direction={direction}
      />
    </Container>

  );
}
