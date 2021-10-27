import React from 'react';
import './style.css';

interface type {
  iconType: 'iphone' | 'airpods' | 'tablet';
}

export default function Image({ iconType }: type) {
  return (
    <div className={`image_wrap ${iconType}`} />
  );
}
