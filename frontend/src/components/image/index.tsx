import React from 'react';
import './index.scss';
import { IconType } from '../../types';

type Props = {
  iconType: IconType;
};

function Image({ iconType }:Props) {
  return (
    <div className={`image  image-${iconType}`} />
  );
}

export default Image;
