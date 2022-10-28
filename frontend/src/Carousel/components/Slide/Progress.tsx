import React from 'react';
import ItemLine from './ItemLine'
import './Progress.scss'

type ProgressProps = {
    activeIndex: number,
    slideList: Carousel.slideItem[] 
}
const Progress = ({activeIndex,slideList}:ProgressProps) => {

  return (
    <div className='progress'>
      {
          slideList.map((v,i) => <ItemLine key={i} actived={ i===activeIndex } />)
      }
    </div>
  );
}

export default Progress;
