import { FC, CSSProperties, ReactNode } from 'react';

import './index.css';

export interface PannelProps {
  style?: CSSProperties;
  title?: ReactNode;
  subTitle?: ReactNode;
  imgUrl?: string;
}

const Pannel: FC<PannelProps> = ({
  style,
  title,
  subTitle,
  imgUrl
}) => {
  return (
    <div className='pannel-wrapper' style={style}>
      <div className='content'>
        {
          title && (
            <div className='title title-wrapper'>{title}</div>
          )
        }
        {
          subTitle && (
            <div className='text text-wrapper'>{subTitle}</div>
          )
        }
      </div>
      {
        imgUrl && (
          <div className='img-wrapper'>
            <img className='img' alt="" src={imgUrl}/>
          </div>
        )
      }
    </div>
  )
}

export default Pannel;
