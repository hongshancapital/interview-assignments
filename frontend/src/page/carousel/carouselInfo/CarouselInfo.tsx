import React from "react";
import classnames from 'classnames';
import imageInfo from "../../../common/images";
import "./CarouselInfo.css";

interface Props {
  activeIndex: number
}

const CarouselInfo = (props: Props) => {
  const { activeIndex } = props;

  return (
    <>
      {imageInfo?.map((item,index) => {
        return (
          <div
            key={item.id}
            className="image_container"
            style={{ transform: `translateX(-${activeIndex * 100}%)` }}
          >
            <div
              className={classnames({
                  'carousel_info': true,
                  'fontColor':index===0
                })}
            >
              <h1
                className='carousel_info_title'
              >{item.title}</h1>
              <p
                className={classnames({
                  'carousel_info_desc': true,
                  'descFont':index===2
                })}
              >{item.describe}</p>
              <p className="carousel_info_desc">{item.price}</p>
            </div>
            <img src={item.image} alt={item.describe}  className="info_image" />
          </div>
          );
      })}
    </>
  );
};

export default CarouselInfo;

