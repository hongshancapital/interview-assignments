import React, { useCallback, useMemo, useState } from 'react';
import '../styles/App.css';
import PageItem from './PageItem';
import IndicatorLines from './IndicatorLines';
import { CAROUSEL_LIST } from '../constants';

function App() {
  const DEFAULT_INDEX = 0;
  const [currentIndex, setCurrentIndex] = useState(DEFAULT_INDEX);
  const list = CAROUSEL_LIST;

  const carouselStyle = useMemo(() => ({
    width: `${list.length * 100}vw`,
    transform: `translateX(-${currentIndex * 100}vw)`,
  }), [list, currentIndex]);

  const goToNextPage = useCallback(
    () => setCurrentIndex((index) => (index + 1) % list.length), [list.length],
  );

  return (
    <div
      className="App"
      onClick={goToNextPage}
    >
      <div
        className="carousel"
        style={carouselStyle}
      >
        {list.map(({
          title, text, iconType, backgroundColor, fontColor,
        }) => (
          <PageItem
            key={iconType}
            fontColor={fontColor}
            title={title}
            text={text}
            iconType={iconType}
            backgroundColor={backgroundColor}
          />
        ))}
      </div>
      <IndicatorLines
        activeIndex={currentIndex}
        itemsCount={list.length}
        onAnimationEnd={goToNextPage}
      />
    </div>
  );
}

export default App;
