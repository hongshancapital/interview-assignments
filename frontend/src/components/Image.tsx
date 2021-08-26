import React from 'react';
import '../styles/Image.css';
import { IconType } from '../types';

type Props = {
  iconType: IconType;
};

function Image({ iconType }:Props) {
  return (
    <div className={`imageWrapper ${iconType}`} />
  );
}

export default Image;
