import './style.css';

import React, { Fragment } from 'react';
import Color from 'color';
import { CarouselItemProps } from './interface';

function Carousel(props: CarouselItemProps) {
  const { 
    title, 
    description, 
    backImage,
    backImageStyle = {},
    backColor = '#000', 
    prefixCls = 'carousel-item',
  } = props;
  const color = new Color(backColor);

  // 灰度心理学公式
  const ratio = (0.2126 * color.red() + 0.7152 * color.green() + 0.0722 * color.blue()) / 255;
  const fontColor = ratio > 0.5 ? '#000' : '#FFF';

  return (
    <li
      key={title}
      className={prefixCls}
      style={{
        backgroundColor: `${color.hex()}`,
        backgroundImage: `url(${backImage})`,
        backgroundSize: backImageStyle.size,
        backgroundPosition: backImageStyle.position,
      }}
    >
      <h2
        className={`${prefixCls}-title`}
        style={{ color: fontColor }}
      >
        {title.split('\n').map((str) => (
          <Fragment key={str}>
            {str}<br />
          </Fragment>
        ))}
      </h2>
      {!!description && (
        <p 
          className={`${prefixCls}-desc`}
          style={{ color: fontColor }}
        >
          {description.split('\n').map((str) => (
            <Fragment key={str}>
              {str}<br />
            </Fragment>
          ))}
        </p>
      )}
    </li>
  );
}

export default Carousel;
