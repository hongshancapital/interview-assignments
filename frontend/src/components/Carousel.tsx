import { FC, CSSProperties, useState, useEffect } from 'react';
import { roller } from '../utils';
import './Carousel.css';

export type ProcessBarProps = {
  total: number,
  activeIndex: number,
};

/**
 * 轮播图下方进度条组件
 */
export const ProcessBar: FC<ProcessBarProps> = (props) => {
  const dots = new Array(props.total).fill(0);

  return (
    <div className="process">
      {
        dots.map((item, index) => (
          <div
            key={index}
            className={`process-dot${index === props.activeIndex ? ' active' : ''}`}
          >
            <div className="process-dot-mask"></div>
          </div>
        ))
      }
    </div>
  );
};

export type CarouselProps = {
  children: JSX.Element | JSX.Element[],
};

/**
 * 提取出的轮播图控制逻辑
 * @param total 轮播图片数量
 * @param duration 单张图片的展示时间，默认2秒
 * @returns CarouselInnerState 轮播图组件内部状态
 */
export const useCarousel = (total: number, duration = 2000) => {
  const [activeIndex, setActiveIndex] = useState(0);

  useEffect(() => {
    let timmer: ReturnType<typeof setTimeout>;
    const indexUpdater = () => {
      timmer = setTimeout(
        () => {
          // 当前图片展示时间结束时，使当前展示图片的index指向下一张图片
          setActiveIndex((index) => roller(total, index));
          // 递归调用更新器
          indexUpdater();
        },
        duration,
      );
    };
    indexUpdater();
    return () => clearTimeout(timmer);
  }, [total, duration]);

  return { total, activeIndex };
};

/**
 * 轮播图组件
 */
export const Carousel: FC<CarouselProps> = (props) => {
  // 将单个子元素和多个子元素的情况都转为数组
  const pages = [props.children].flat();
  // 获取通过children传入的页面数量
  const itemCount = pages.length;

  // 组件状态
  const state = useCarousel(itemCount, 3000);

  // 容器偏移style
  // 通过一个负的left值和 css 的 duration 属性实现翻页动效
  const containerStyle: CSSProperties = {
    left: `${-state.activeIndex * 100}vw`,
  };

  return (
    <div className="carousel">
      <div className="carousel-container" style={containerStyle}>
        {
          pages.map((child, index) => (
            // 将传入的子元素全部用一个div包裹，保证轮播的每页大小相同
            <div className="carousel-page" key={index}>
              {child}
            </div>
          ))
        }
      </div>
      <ProcessBar total={state.total} activeIndex={state.activeIndex} />
    </div>
  );
};
