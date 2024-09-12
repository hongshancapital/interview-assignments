import React, {
  FC,
  MouseEventHandler,
  ReactNode,
  useEffect,
  useRef,
} from 'react';
import './carousel.css';

export type Props = {
  // 宽、高
  width?: string;
  height?: string;

  // dataSource 渲染的轮播项
  dataSource: Array<any>;
  renderItem?: (data: any) => ReactNode;

  // 轮播方向
  direction?: 'vertical' | 'horizontal';

  // 自动播放
  autoPlay?: boolean;
  // 每帧自动播放时间 单位ms
  interval?: number;
  // 循环播放
  loop?: boolean;

  // 当前展示帧数
  activeIndex: number;
  onChange: (index: number) => void;
};

export const Carousel: FC<Props> = ({
  width = '100%',
  height = '100%',
  direction = 'horizontal',
  dataSource,
  renderItem = (item) => item,
  loop = true,
  autoPlay = true,
  interval = 3000,
  activeIndex = 0,
  onChange,
}) => {
  // TODO: defaultIndex大于dataSource长度

  // 自动播放部分

  const timerRef = useRef<NodeJS.Timer>();
  useEffect(() => {
    // let timer: NodeJS.Timer;
    /**
     * 开发模式下 useEffect会执行两次造成定时器不能清除
     */
    if (autoPlay) {
      if (activeIndex >= dataSource.length && !loop) {
        // 当前页是最后一帧并且不循环时
        return;
      } else {
        let virtualIndex = activeIndex;
        // intervalTimer && clearInterval(intervalTimer);
        timerRef.current && clearInterval(timerRef.current);
        timerRef.current = setInterval(() => {
          // console.log('virtualIndex:', virtualIndex);
          onChange(++virtualIndex % dataSource.length);
          if (!loop && virtualIndex === dataSource.length - 1) {
            timerRef.current && clearInterval(timerRef.current);
          }
        }, interval);
      }
    }
    return () => timerRef.current && clearInterval(timerRef.current);
  }, []);
  /**
   * 底部轮播标注
   */

  const renderDots = () => {
    const items = dataSource.map((item, index) => (
      <div key={`dot-${index}`} className={`dot`}>
        <div
          className={`dot-content ${index === activeIndex ? 'dot-active' : ''}`}
          style={{
            transitionDuration: `${
              index === activeIndex ? interval / 1000 : 0
            }s`,
          }}
        ></div>
      </div>
    ));
    return <div className="flex absolute dotContainer">{items}</div>;
  };

  /**
   * 处理滑动事件
   */

  let mouseDowned = false;
  let startX = 0;
  let endX = 0;
  const onMouseDown: MouseEventHandler = (e) => {
    mouseDowned = true;
    startX = e.clientX;
    console.log('鼠标按下:', startX);
  };

  const onMouseMove: MouseEventHandler = (e) => {
    if (mouseDowned) {
      // console.log('鼠标移动:', e.clientX);
    }
  };

  const onMouseUpOrLeave: MouseEventHandler = (e) => {
    if (mouseDowned) {
      mouseDowned = false;
      endX = e.clientX;
      const distance = endX - startX;
      if (Math.abs(distance) > 50) {
        const index = activeIndex + dataSource.length;
        // 发生滑动后清除定时器
        timerRef.current && clearInterval(timerRef.current);
        distance > 0
          ? onChange((index - 1) % dataSource.length)
          : onChange((index + 1) % dataSource.length);
      }
      console.log('鼠标移动距离', endX - startX);
    }
  };

  return (
    <div className={direction} style={{ width, height }}>
      {dataSource.map((item, key) => {
        return (
          <div
            className={`slider ${key === activeIndex ? 'active' : ''}`}
            draggable={false}
            key={key}
            onMouseDown={onMouseDown}
            onMouseUp={onMouseUpOrLeave}
            onMouseLeave={onMouseUpOrLeave}
            onMouseMove={onMouseMove}
            // onClick={onMouseUpOrLeave}
          >
            {renderItem(item)}
          </div>
        );
      })}
      {renderDots()}
    </div>
  );
};

export default Carousel;
