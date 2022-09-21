import React, { ReactElement } from 'react';

interface ProgressBarItemProps {
  id: number;
  index: number;
  delay: number;
  currentIndex: number;
  handleClickProgressBar: (index: number) => void;
}

function ProgressBarItem(props: ProgressBarItemProps): ReactElement {

  const { id, index, delay, currentIndex, handleClickProgressBar } = props;
  
  return (
    
    <div
      key={id}
      className="progress-bar-item"
      data-testid={`progress-bar-item-${id}`}
      onClick={() => handleClickProgressBar(index)}
    >

      <span
        className={currentIndex === index ? 'active' : ''}
        style={{
          animationDuration: `${currentIndex === index ? delay : 0}ms`
        }}
      >
      </span>
      
    </div>

  )
}

export default ProgressBarItem;