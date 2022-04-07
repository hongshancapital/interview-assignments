import iphone from '../../assets/iphone.png';
import React from 'react';
import './index.css';


export default function Phone() {
  return (
    <div className="phone">
      <p className='phone-title'>xPhone</p >
      <p className='phone-desc'>Lots to love. Less to spend.</p >
      <p className='phone-desc phone-desc-1'>Starting at $399.</p >
      <div className="phone-image-container">
        < img className='phone-img' key="iphone" src={iphone} alt="iphone" />
      </div>
    </div>
  )
}