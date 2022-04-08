import React from 'react';
import classNames from 'classnames';
import './index.scss';

export interface IItemProps {
  title: string;
  subTitle?: string;
  img: string;
  className?: string;
}

export default function Item(props: IItemProps) {
  const { title, subTitle, img, className } = props;

  return (
    <div className={classNames('item', className)}>
      <div>
        <h1 className="title">{title}</h1>
        {subTitle && <div className="subTitle">{subTitle}</div>}
      </div>
      <img src={img} alt="img" className="img" />
    </div>
  )
}
