import React from 'react';
import './index.css';

interface CarouselProps {
  currentIndex: number,
  totalNumber: number,
}

const getProgressHtml = (currentIndex: number, totalNumber: number) => {
  const progressHtml = [];
  for (let i = 0; i < totalNumber; i++) {
    progressHtml.push(<div className='defaultBar' key={i}><span className={ i === currentIndex ? 'active' : ''} style={{ width: '1px'}}></span></div>);
  }
  return progressHtml;
}

const ProgressBar: React.FC<CarouselProps> = ({
  currentIndex = 0,
  totalNumber = 0,
}) => {

  return (
    <div className='progressBar'>
      { getProgressHtml(currentIndex, totalNumber) }
    </div>
  );
};

export default ProgressBar;