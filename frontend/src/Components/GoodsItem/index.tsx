import React, { memo } from 'react';
import { GoodsItemType } from './config';
import './index.css';

const toArray = <T,>(arg: T | T[]): T[] => (Array.isArray(arg) ? arg : [arg]);

const GoodsItem: React.FC<GoodsItemType> = props => {
  const { title, description, styles } = props;

  return (
    <div className="goods-item" style={styles}>
      <div className="title-container">
        {toArray(title).map(item => (
          <div key={item}>{item}</div>
        ))}
      </div>
      <div className="description-container">
        {description &&
          toArray(description).map(item => <div key={item}>{item}</div>)}
      </div>
    </div>
  );
};

export default GoodsItem;
