import React from 'react'
import './index.css'

const prefix = 'indicator';
interface IProps {
  total: number,
  activeIndex: number,
  animationTime: number
}
const Indicator: React.FC<IProps> = (props) => {

  const { total, activeIndex, animationTime } = props;
  if (total < 2) {
    return null
  }
  return (
    <div className={`${prefix}__container`}>
      {(new Array(total)).fill(0).map((item, index) => (
        <div className={`${prefix}__line`}>
          <div
            className={index === activeIndex ? `${prefix}__line-active` : ''}
            style={{ animationDuration: `${animationTime}s` }}
          />
        </div>
      ))}
    </div>
  )
}

export default Indicator