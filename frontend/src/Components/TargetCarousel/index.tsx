import React, { FC } from "react";
import Carousel from "../Carousel";
import "./index.scss";

interface PanelItemProps {
  title: string;
  subTitle?: string;
  className?: string;
}

const PanelItem: FC<PanelItemProps> = ({
  title,
  subTitle,
  className,
}) => {
  return (
    <div className={`slide ${className}`}>
      <h1>{title}</h1>
      {subTitle && <p>{subTitle}</p>}
    </div>
  );
};


export const TargetConfig: PanelItemProps[] = [
  {
    title: "xPhone",
    subTitle: "Lots to love. Less to spend.\nStarting at $399.",
    className: "slide--first",
  },
  {
    title: "Tablet",
    subTitle: "Just the right amount of everything.",
    className: "slide--second",
  },
  {
    title: "Buy a tablet or xPhone for college.\nGet airPods",
    subTitle: "",
    className: "slide--third",
  },
];

const TargetCarousel = () => {
  let slideItems: JSX.Element[] = TargetConfig.map((item) => {
    return <PanelItem {...item} />;
  });

  return <Carousel slideItems={slideItems} />;
};

export default TargetCarousel;
