import React from 'react';
import tabletImg from '../assets/tablet.png';
import './page.css';

const Tablet: React.FC<Record<string, never>> = () => {
  return (
    <div className='container'
      style={{ backgroundImage: `url(${tabletImg})`, color: 'black' }}>
      <div className='title'>Tablet</div>
      <div className='text'>Just the right amount of everything.</div>
    </div>
  );
};

export default Tablet;