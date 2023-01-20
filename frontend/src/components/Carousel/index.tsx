import { useEffect } from 'react';
import './index.css';
type Props = {
  dataSource: Array<any>;
  renderItemFunction?: Function;
  currentIndex?: number;
  auto?: boolean;
  autoPlayTime?: number;
  loop?: boolean;
  onChange: (index: number) => void;
};

const renderItems = (
  dataSource: Array<any>,
  currentIndex: number,
  renderItemFunction?: Function
) => {
  if (renderItemFunction instanceof Function) {
    return dataSource.map((item, index) => {
      return (
        <div
          className={`slider ${currentIndex === index ? 'active' : ''}`}
          key={index}
        >
          {renderItemFunction(item)}
        </div>
      );
    });
  }
  return (
    <div style={{ width: '100%', height: '100%', overflow: 'hidden' }}>
      {dataSource[currentIndex]}
    </div>
  );
};

const renderDots = (len: number, currentIndex: number, time: number = 0) => {
  const items = new Array(len).fill(0).map((item, index) => (
    <div key={index} className={`dot`}>
      <div
        className={`dot-content ${index === currentIndex ? 'dot-active' : ''}`}
        style={{ transitionDuration: `${index === currentIndex ? time : 0}s` }}
      ></div>
    </div>
  ));
  return <div className="flex absolute dotContainer">{items}</div>;
};

function Carousel(props: Props) {
  const {
    dataSource,
    renderItemFunction,
    currentIndex = 0,
    auto = true,
    loop = true,
    autoPlayTime = 3000,
    onChange,
  } = props;
  const item = renderItems(dataSource, currentIndex, renderItemFunction);

  // 自动循环轮播
  useEffect(() => {
    let virtualIndex = currentIndex;
    if (auto) {
      if (currentIndex === dataSource.length - 1 && !loop) {
        // 当自动轮播刚开始展示的页面为最后一张时
        return;
      } else {
        const timer = setInterval(() => {
          onChange(++virtualIndex % dataSource.length);
          if (!loop && virtualIndex === dataSource.length - 1) {
            timer && clearInterval(timer);
          }
        }, autoPlayTime);
      }
    }
  }, []);

  return (
    <div style={{ width: '100vw', height: '100vh' }} className="flex">
      {item}
      {renderDots(dataSource.length, currentIndex, autoPlayTime / 1000)}
    </div>
  );
}

export default Carousel;
