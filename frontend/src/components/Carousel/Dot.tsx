import React, { ReactElement } from 'react';

interface DotProps {
  index: number,
  isMotion: Boolean,
  onClick?: Function
};

function Dot({ index, isMotion, onClick }: DotProps): ReactElement {
  const handleClick = () => {
    if (onClick) {
      onClick(index);
    }
  }

  return (
    <li className='carousel-dot' onClick={handleClick}>
      <em className={isMotion ? 'animation' : ''}>*</em>
    </li>
  );
}

export default Dot;