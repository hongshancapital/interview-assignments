import airpods from '../../assets/airpods.png';
import React from 'react';
import './index.css';


export default function Airpods() {
  return (
    <div className="airpods">
      <p className='airpods-title'>Buy a Tablet or xPhone for college.</p >
      <p className='airpods-desc'>Get arPods.</p >
      <div className="airpods-image-container">
        < img className='airpods-img' key="airpods" src={airpods} alt="airpods" />
      </div>
    </div>
  )
}