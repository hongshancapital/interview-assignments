import React, { FC } from "react";
import Carousel from ".";
import img1 from "../../assets/airpods.png";
import img2 from "../../assets/iphone.png";
import img3 from "../../assets/tablet.png";



const CarouselTest: FC = () => {
  return (
    <div className="test" style={{ width: "100%", height: "100%" }} id="test">
      <Carousel
        imgSrcs={[img1, img2, img3]}
        duration={500}
        autoDuration={3000}
        width={800}
        height={500}
      />
    </div>
  );
};

export default CarouselTest;
