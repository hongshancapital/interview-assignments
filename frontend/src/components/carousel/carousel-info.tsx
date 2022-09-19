import React from "react";
import { PREFIX_CLS } from "./constans";

export interface CarouselInfoProps {
  title?: string | React.ReactNode;
  description?: string | React.ReactNode;
  image?: string;
  color?: string;
}

export const CarouselInfo: React.FC<CarouselInfoProps> = ({
  title = "",
  description = "",
  image = "",
  color = "#000",
}) => {
  return (
    <React.Fragment>
      <div className={`${PREFIX_CLS}-item_info`} style={{ color }}>
        {title ? (
          <div className={`${PREFIX_CLS}-item_title`}>{title}</div>
        ) : null}
        {description ? (
          <div className={`${PREFIX_CLS}-item_description`}>{description}</div>
        ) : null}
      </div>
      {image ? (
        <div className={`${PREFIX_CLS}-item_image`}>
          <img src={image} alt='图片' />
        </div>
      ) : null}
    </React.Fragment>
  );
};
