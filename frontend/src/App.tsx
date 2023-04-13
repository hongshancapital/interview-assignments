import React, { cloneElement, useEffect, useMemo, useRef, useState } from 'react';
import './App.css';

const SwiperItem: React.FC<{ children?: React.ReactNode; }> = ({ children }) => {
  return <div className="swiper_item">{children}</div>;
}

const Swiper: React.FC<{ children: React.ReactNode[] }> = ({ children }) => {
  const [idx, setIdx] = useState(0);
  const idxRef = useRef(0);
  const swiperItems = useRef<HTMLDivElement>(null)
  const timer: any = useRef(0);

  const count = useMemo(() => React.Children.count(children), [children]);

  const setStyle = (index: number, duration: number) => {
    const w = swiperItems.current?.offsetWidth;
    swiperItems.current!.style.transform = `translate3d(${-(index + 1) * w!}px, 0, 0)`;
    swiperItems.current!.style.transitionDuration = `${duration}ms`;
  }

  const swipe = (duration: number) => {
    const i = idxRef.current + 1;
    updateIdx(i)
    setStyle(i, duration)

    if (idxRef.current > count - 1) {
      updateIdx(0)
      setStyle(-1, 0)
      setTimeout(() => {
        updateIdx(0)
        setStyle(0, 500)
      }, 0);
    }
  }

  const updateIdx = (i: number) => {
    idxRef.current = i;
    setIdx(i)
  }

  const autoplay = () => {
    timer.current = setInterval(() => {
      swipe(500)
    }, 2500);
  }


  useEffect(() => {
    setStyle(0, 0)
    autoplay();
  }, []);

  // 31231
  const renderContent = () => {
    const items = ([] as React.ReactNode[]).concat(children);
    const first = items[0];
    const last = items[items.length - 1];
    items.push(first);
    items.unshift(last);

    const newItems = React.Children.map(items, (elem: any, index) => {
      return cloneElement(elem, {
        key: index,
        className: elem.props.className
      });
    });

    return newItems;
  }

  const renderPagation = () => {
    return (
      <div className="pagination">
        {new Array(count).fill(0).map((item, i) => (
          <div 
            className={['pagination_item', i === idx ? 'active' : ''].join(' ')}
            onClick={() => {
              updateIdx(i)
              setStyle(i, 500)
            }}
            key={i}
          />
        ))}
      </div>
    )
  }

  return (
    <div className="swiper">
      <div className="swiper_items" ref={swiperItems}>
        {renderContent()}
      </div>
      {renderPagation()}
    </div>
  )
}

function App() {
  return (
    <div className='App'>
      <Swiper>
        <SwiperItem>
          <div className="phone">
            <div className="phone_title">xPhone</div>
            <div className="phone_intro">
              <p>Lots to love. Less to spend.</p>
              <p>Starting at $399.</p>
            </div>
            <div className="phone_img" />
          </div>
        </SwiperItem>
        <SwiperItem>
          <div className="tablet">
            <div className="tablet_title">Tablet</div>
            <div className="tablet_intro">Just the right amount of everything</div>
            <div className="tablet_img" />
          </div>
        </SwiperItem>
        <SwiperItem>
          <div className="airpods">
            <div className="airpods_intro">
              <p>Buy a Tablet or xPhone for college.</p>
              <p>Get ariPods.</p>
            </div>
            <div className="airpods_img" />
          </div>
        </SwiperItem>
      </Swiper>
    </div>
  );
}

export default App;
