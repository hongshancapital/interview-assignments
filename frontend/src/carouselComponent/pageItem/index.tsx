import React from 'react';
import './style.css';
import Images from '../images/index'

interface type {
  iconType: 'iphone' | 'airpods' | 'tablet';
  fontColor?: string;
  backgroundColor?: string;
  title?: string[];
  text?: string[];
}

type Props = type;

export default function PageItem({
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
        <Images iconType={iconType} />
      </div>
    </div>
  );
}