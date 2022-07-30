import React, { CSSProperties, FC } from "react";
import Banner, { BannerProp_Inter } from "./banner";
import "./index.css";

export interface Banner_Inter extends BannerProp_Inter {
  id: number;
}

export interface BannerListProps_Inter {
  banners: Banner_Inter[];
  currentIndex?: number;
}

const BannerList: FC<BannerListProps_Inter> = ({
  banners,
  currentIndex = 0,
}) => {
  
  const style: CSSProperties = {
    left: `-${currentIndex}00%`,
  };

  return (
    <div
      data-testid="banner-list"
      className="banner-list-control"
      style={style}
    >
      {banners.map((bannerInfo, index) => {
        const { id, ...info } = bannerInfo;
        return <Banner {...info} key={id} index={index} />;
      })}
    </div>
  );
};

export default BannerList;
