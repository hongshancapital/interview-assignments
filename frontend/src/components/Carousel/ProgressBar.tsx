import React, { ReactElement } from 'react';
import ProgressBarItem from './ProgressBarItem';

interface ProgressBarProps {
  delay: number;
  carouselData: Array<Object>
  currentIndex: number;
  clickProgressBar: (clickIndex: number) => void;
}

function ProgressBar(props: ProgressBarProps): ReactElement {

  const {
    delay,
    carouselData,
    currentIndex,
    clickProgressBar,
  } = props;

  const handleClickProgressBar = (clickIndex: number) => {
    if (clickIndex === currentIndex) return;
    clickProgressBar(clickIndex);
  }

  return (
    <div className="progress-bar" data-testid="progress-bar">
      {
        carouselData.map((item: any, index: number) => (

          <ProgressBarItem
            key={item.id}
            id={item.id}
            index={index}
            delay={delay}
            currentIndex={currentIndex}
            handleClickProgressBar={handleClickProgressBar}
          />

        ))
      }
    </div>
  )
}

export default ProgressBar;