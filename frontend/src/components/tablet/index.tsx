import tablet from '../../assets/tablet.png';
import React from 'react';
import './index.css';


export default function Tablet() {
  return (
    <div className="tablet">
      <p className='tablet-title'>Tablet</p>
      <p className='tablet-desc'>Just the right amount of everything.</p >
      <div className="tablet-image-container">
        < img className='tablet-img' key="tablet" src={tablet} alt="tablet" />
      </div>
    </div>
  )
}