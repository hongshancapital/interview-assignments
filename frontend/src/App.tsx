import React, { cloneElement, forwardRef, useEffect, useImperativeHandle, useMemo, useRef, useState } from 'react';
import './App.css';

const swipeData = [{
  type: 'phone',
  title: 'xPhone',
  intro: ['Lots to love. Less to spend.', 'Starting at $399.']
}, {
  type: 'tablet',
  title: 'Tablet',
  intro: ['Just the right amount of everything.']
}, {
  type: 'airpods',
  title: '',
  intro: ['Buy a Tablet or xPhone for college.', 'Get AirPods.']
}]

const SwiperItem: React.FC<{ children?: React.ReactNode; }> = ({ children }) => {
  return <div className="swiper_item">{children}</div>;
}

interface ISwiperProps {
  inititalIndex?: number;
  autoplay?: boolean;
  showPagation?: boolean;
  children?: React.ReactNode[];
  onChange?: (i: number) => void;
}

interface ISwiperFn {
  next: () => void;
  prev: () => void;
  swipeTo: (i: number) => void;
}

const Swiper = forwardRef<ISwiperFn, ISwiperProps>(({
  inititalIndex,
  autoplay,
  showPagation,
  children,
  onChange
}, ref) => {
  const [idx, setIdx] = useState(inititalIndex || 0);
  const idxRef = useRef(inititalIndex || 0);
  const swiperItems = useRef<HTMLDivElement>(null)
  const dotsRef = useRef<HTMLDivElement>(null)
  const timer: any = useRef(null);

  const count = useMemo(() => React.Children.count(children), [children]);

  const setStyle = (index: number, duration: number) => {
    const w = swiperItems.current?.offsetWidth;
    swiperItems.current!.style.transform = `translate3d(${-(index + 1) * w!}px, 0, 0)`;
    swiperItems.current!.style.transitionDuration = `${duration}ms`;
  }

  const swipe = (i: number, duration: number) => {
    updateIdx(i)
    setStyle(i, duration)

    if (idxRef.current > count - 1) {
      updateIdx(0)
      setStyle(-1, 0)
      setTimeout(() => {
        updateIdx(0)
        setStyle(0, 500)
      }, 100);
    }

    if(idxRef.current < 0) {
      updateIdx(count)
      setStyle(count, 0)
      setTimeout(() => {
        updateIdx(count - 1)
        setStyle(count - 1, 500)
      }, 100);
    }
  }

  const updateIdx = (i: number) => {
    idxRef.current = i;
    setIdx(i)
    onChange?.(i)
  }

  const startAutoplay = () => {
    timer.current = setInterval(() => {
      const i = idxRef.current + 1;
      swipe(i, 500)
    }, 2500);
  }

  const clear = () => {
    clearInterval(timer.current);
    timer.current = null
  }

  useEffect(() => {
    setStyle(inititalIndex!, 0)
    autoplay && startAutoplay();
    return clear;
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [autoplay, inititalIndex]);

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

  const renderDots = () => {
    return (
      <div className="dots">
        {new Array(count).fill(0).map((item, i) => (
          <div
            className="dots_item"
            onClick={() => {
              updateIdx(i)
              setStyle(i, 500)
            }}
            key={i}
          >
            <div
              className={['inner_dots_item', i === idx ? 'active' : ''].join(' ')}
              ref={dotsRef}
              onAnimationEnd={() => {
                const i = idxRef.current + 1;
                swipeTo(i);
                startAutoplay();
              }}
            />
          </div>
        ))}
      </div>
    )
  }

  const swipeTo = (i: number) => {
    clear();
    swipe(i, 500);
  }

  const next = () => {
    const i = idxRef.current + 1;
    swipeTo(i);
  }

  const prev = () => {
    const i = idxRef.current - 1;
    swipeTo(i);
  }

  useImperativeHandle(ref, () => ({
    next,
    prev,
    swipeTo
  }));

  return (
    <div className="swiper">
      <div className="swiper_items" ref={swiperItems}>
        {renderContent()}
      </div>
      {showPagation && renderDots()}
    </div>
  )
});

function App() {
  const swiperRef = useRef<ISwiperFn>(null)

  return (
    <div className='App'>
      <div className="btns">
        <button onClick={() => swiperRef?.current?.prev()}>prev</button>
        <button onClick={() => swiperRef?.current?.next()}>next</button>
        <button onClick={() => swiperRef?.current?.swipeTo(2)}>swipe to</button>
      </div>

      <Swiper
        inititalIndex={0}
        autoplay={true}
        showPagation={true}
        onChange={(i: number) => {
          // console.log(i, 'i');
        }}
        ref={swiperRef}
      >
        {swipeData.map((item, i) => (
          <SwiperItem key={i}>
            <div className={`${item.type}`}>
              {item.title && <div className={`${item.type}_title`}>{item.title}</div>}
              <div className={`${item.type}_intro`}>
                {item.intro.map((text, idx) => (
                  <p key={idx}>{text}</p>
                ))}
              </div>
              <div className={`${item.type}_img`} />
            </div>
          </SwiperItem>
        ))}
      </Swiper>
    </div>
  );
}

export default App;
