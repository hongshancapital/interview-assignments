import React from "react";
import classnames from 'classnames';
import imageInfo from "../../../common/images";
import "./Indicator.css";

interface Props {
  activeIndex: number,
  onUpdateIndex:(v:number) => void
}

const Indicator = (props: Props) => {

  const { activeIndex,onUpdateIndex} = props;

  //点击indicator
  const handleCarouselIndex = (index: number) => {
    onUpdateIndex(index);
  };

  return (
      <div className='indicator'>
        {imageInfo.map((item, index) => {
          return (
            <div
              className='indicator_outer'
              onClick={() => handleCarouselIndex(index)}
              key={item.title}
            >
              <div
                className={classnames({
                  'indicator_inside': true,
                  'animateStyle': index === activeIndex
                })}
              />
            </div>
          );
        })}
      </div>
  );
};

export default Indicator;

