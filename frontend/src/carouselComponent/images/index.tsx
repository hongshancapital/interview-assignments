import React from 'react';
import './style.css';


export default function Image({ iconType }:any) {
  return (
    <div className={`imageWrapper ${iconType}`} />
  );
}
