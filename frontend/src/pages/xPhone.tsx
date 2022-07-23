import React from 'react';
import phoneImg from '../assets/iphone.png';
import './page.css';

const Phone: React.FC<Record<string, never>> = () => {
  return (
    <div className='container'
      style={{ backgroundImage: `url(${phoneImg})`, color: 'white' }}>
      <div className='title'>xPhone</div>
      <div className='text'>Lots to love.Less to spend.</div>
      <div className='text'>Staring at $399.</div>
    </div>
  );
};

export default Phone;