import React from "react";
import { Carousel } from "../../Carousel";
import iphone from "../../../assets/iphone.png";
import tablet from "../../../assets/tablet.png";
import airpods from "../../../assets/airpods.png";

import "./style.css";
export const DemoAppleSlides = () => {
  const getAppleProducts = () => {
    const slidesDescription = [
      {
        caption: ["Xphone"],
        customCSS: "xPhone text-white bg-contain bg-no-repeat",
        description: ["Lots to Love. Less to spend.", "Starting as $399"],
        bgImg: iphone,
      },
      {
        caption: ["Tablet"],
        customCSS: "Tablet text-black bg-contain bg-no-repeat",
        description: ["Just the right amount of everything."],
        bgImg: tablet,
      },
      {
        caption: ["Buy a Tablet or xPhone for college.", "Get arPods"],
        customCSS: "arPods text-black bg-contain bg-no-repeat",
        bgImg: airpods,
      },
    ];
    return slidesDescription.map(
      ({ caption, customCSS, description, bgImg }) => {
        return (
          <div
            className={`w-full h-full flex flex-col ${customCSS}`}
            style={{
              backgroundImage: `url(${bgImg})`,
            }}
          >
            <div className="w-full flex flex-col items-center description">
              {caption.map((c) => (
                <h1 className="text-6xl leading-normal font-bold" key={c}>{c}</h1>
              ))}
              {description?.map((d) => (
                <p className="text-4xl font-normal" key={d}>{d}</p>
              ))}
            </div>
          </div>
        );
      }
    );
  };
  return (
    <Carousel items={getAppleProducts()} customCSS="apple-products" cycle interval={3000} indicators />
  );
};