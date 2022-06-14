import React, { memo, useState, useEffect } from 'react';
import Core from './core/index'
import { corePluginFactory } from './plugin'
import { useDot } from './hooks'
import { useOnce } from '../../utils/share'
import type { PropsWithChildren, FunctionComponent } from 'react';
import { CarouselProps } from './type'
import './index.css';

const ITEM_NAME = 'CarouselItem'

export default function Carousel(props: PropsWithChildren<CarouselProps>) {
  const {
    interval = 2000,
    stepTime = 300,
    dots = true,
    dotPosition = 'bottom',
    dotJump = true
  } = props;
  let items: React.ReactNode[] = [];
  if (Array.isArray(props.children)) {
    items = props.children.filter(item => item.type.displayName === ITEM_NAME);
  }
  const [translate, setTranslate] = useState(-100);
  const coreData = useOnce(() => {
    const { data, plugin } = corePluginFactory({
      interval,
      stepTime,
      setTranslate(index) {
        setTranslate(-(index + 1) * 100)
      }
    });
    const carousel = new Core(plugin);
    carousel.init(items.length);
    return {
      ...data,
      carousel
    };
  });

  const dotData = useDot({
    useDot: !!dots,
    dotJump,
    carousel: coreData.carousel
  });

  useEffect(() => {
    coreData.carousel.mount();
    return () => {
      coreData.carousel.unmount();
    }
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);
  const dotJumpHandler = (e: React.MouseEvent) => {
    const dataset = (e.target as HTMLElement).dataset;
    const index = dataset?.index;
    index && dotData.onClick(+index);
  }
  return (
    <div className="carousel-container">
      <div
        className="carousel-content"
        style={{
          transform: `translate(${translate}%, 0)`,
          transition: `transform 0.3s linear`
        }}
        onTransitionEnd={coreData.getTransitionEnd()}
      >
        {items.length && items[items.length - 1]}
        {items}
        {items.length && items[0]}
      </div>
      {dots && <div
        className={`carousel-dots carousel-dots-${dotPosition}`}
        onClick={dotJumpHandler}
      >
        {
          items.map(
            (_, i) =>
              <div
                className="carousel-dot"
                key={i}
                data-index={i}
              >
                <div className="carousel-dot-progress" style={{width: `${dotData.getProgress(i)}%`}}></div>
              </div>
          )
        }
      </div>}
    </div>
  )
}

const CarouselItem: FunctionComponent<{}> = memo((props) => {
  return (
    <div className="carousel-item">{props.children}</div>
  );
})

CarouselItem.displayName = ITEM_NAME


export { CarouselItem }