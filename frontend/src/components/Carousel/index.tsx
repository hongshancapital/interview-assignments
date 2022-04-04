/*
 * @Author: 钟媛
 * @Date: 2022-04-04 10:55:05
 * @LastEditTime: 2022-04-04 11:34:07
 * @Description:
 */
import React from "react";
import CarouselContainer from "./CarouselContainer";
import "./index.scss";

interface IPanelItemProps {
  title: string; // 标题；
  desc?: string; // 描述
  picStyleInfo: string; // 图片信息
}

const PanelList: IPanelItemProps[] = [
  {
    title: "xPhone",
    desc: "Lots to love. Less to spend.\nStarting at $399.",
    picStyleInfo: "panel--first",
  },
  {
    title: "Tablet",
    desc: "Just the right amount of everything.",
    picStyleInfo: "panel--second",
  },
  {
    title: "Buy a tablet or xPhone for college.\nGet airPods",
    desc: "",
    picStyleInfo: "panel--third",
  },
];

function CarouselItem({ title, desc, picStyleInfo }: IPanelItemProps) {
  return (
    <div className={`panel ${picStyleInfo}`}>
      <p>{title}</p>
      {desc ? <span>{desc}</span> : null}
    </div>
  );
}

function Carousel() {
  const Items = PanelList.map((item, index) => (
    <CarouselItem {...item} key={index} />
  ));
  return <CarouselContainer items={Items} />;
}

export default Carousel;
