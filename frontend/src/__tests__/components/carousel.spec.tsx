import React from "react";
import { render } from "@testing-library/react";

import AirpodsImage from "../../assets/airpods.png";
import IPhoneImgage from "../../assets/iphone.png";
import TabletImage from "../../assets/tablet.png";

import { Carousel } from "../../components/carousel";

import type { ICarouselItem } from "../../components/carousel";

describe("carousel component test", () => {
  const items: ICarouselItem[] = [
    {
      title: "xPhone",
      subtitle: "Lots to love. Less to spend. \n Starting at $399.",
      backgroundImage: IPhoneImgage,
      backgroundColor: "black",
    },
    {
      title: "Tablet",
      subtitle: "Just the right amount of everything.",
      backgroundImage: TabletImage,
    },
    {
      title: `Buy a Tablet or xPhone for college. \n Get arPods`,
      backgroundImage: AirpodsImage,
    },
  ];
  test("carousel component props: items", () => {
    const { getByText } = render(<Carousel items={items} />);
    expect(getByText(items[1].title).textContent).toBe("Tablet");
  });
});
