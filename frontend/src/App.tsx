import React, { useCallback, useMemo, useState } from 'react';
import "./App.css";
import {CAROUSEL_LIST} from './carouselComponent/constants/index';
import PageItem from './carouselComponent/pageItem/index';
import Indicator from './carouselComponent/indicator/index';

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

  return <div className="App">
     <div
        className="carousel"
        style={carouselStyle}
        onClick={goToNextPage}
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
      <Indicator
        activeIndex={currentIndex}
        itemsCount={list.length}
        onAnimationEnd={goToNextPage}
      />
  </div>;
}

export default App;
