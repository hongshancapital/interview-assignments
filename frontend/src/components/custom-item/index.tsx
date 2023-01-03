import React from 'react';
import './index.css';

export const CustomItem: React.FC<CustomItemProps> = (props) => {
  const { item = {
    titles: [],
    descs: [],
    img_url: '',
    style: {}
  } } = props

  return (
    <div className="custom-item" style={{ ...item.style }}>
      <div className="custom-item-container">
        {
          item.titles.length > 0 &&
          item.titles.map(title => <p className="title" key={title}>{title}</p>)
        }
        {
          item.descs.length > 0 &&
          item.descs.map(desc => <p className="text" key={desc}>{desc}</p>)
        }
        <img className="custom-item-img" src={item.img_url} height="100%" />
      </div>
    </div>
  )
}

export interface CustomItemProps {
  item: {
    titles: string[],
    descs: string[],
    img_url: string
    style: {
      background: string
      color: string
    }
  }
}