import React from 'react';
import '../styles/PageItem.css';
import Image from './Image';
import { CarouselItem } from '../types';

type Props = CarouselItem;

function PageItem({
  iconType, fontColor = '#000000', backgroundColor = '#ffffff', title, text,
}: Props) {
  return (
    <div className="page-item" style={{ backgroundColor, color: fontColor }}>
      <div className="page-flex-item" />
      <div>
        <div className="title">
          {title?.map((item) => <div key={item}>{item}</div>)}
        </div>
        <div className="text">
          {text?.map((item) => <div key={item}>{item}</div>)}
        </div>
      </div>
      <div className="page-flex-item" />
      <div>
        <Image iconType={iconType} />
      </div>
    </div>
  );
}

export default PageItem;
