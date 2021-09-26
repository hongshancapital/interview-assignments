import React from "react";
import { Caroussel } from "../components/Carousel";

function getRandomColor() {
  var letters = "0123456789ABCDEF";
  var color = "#";
  for (var i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}

export const Slides = () => {
  const items = [
    {
      title: "test-1",
      bgImage: "",
      bgColor: getRandomColor(),
    },
    {
      title: "test-2",
      bgImage: "",
      bgColor: getRandomColor(),
    },
    {
      title: "test-3",
      bgImage: "",
      bgColor: getRandomColor(),
    },
    {
      title: "test-4",
      bgImage: "",
      bgColor: getRandomColor(),
    },
  ];
  const getItems = () => {
    return items.map(({ title, bgColor, bgImage }) => {
      const style = {
        backgroundColor: bgColor,
        backgroundImage: bgImage,
      };
      return (
        <div className="w-full h-full flex items-center" style={style}>
          <h3 className="w-full text-center leading-normal">{title}</h3>
        </div>
      );
    });
  };
  return (
    <Caroussel
      items={getItems()}
      interval={2000}
      keyboard
      indicators
      control
      pasued="hover"
      cycle
    />
  );
};
