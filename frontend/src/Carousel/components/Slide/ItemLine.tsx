import React from 'react';
import './ItemLine.scss';


type ItemLineProps = {
    actived: boolean
};

const ItemLine = ({ actived }:ItemLineProps) => {

  return (
    <span className="line-item">
      <i className={`progress ${actived ? 'actived' :'' }`}></i>
    </span>
  );
}

export default React.memo(ItemLine);
