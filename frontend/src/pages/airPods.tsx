import React from 'react';
import airPodsImg from '../assets/airpods.png';
import './page.css';

const AirPods: React.FC<Record<string, never>> = () => {
  return (
    <div className='container'
      style={{ backgroundImage: `url(${airPodsImg})`, color: 'black' }}>
      <div className='title'>Buy a Tablet or xPhone for college.</div>
      <div className='title'>Get airPods.</div>
    </div>
  );
};

export default AirPods;