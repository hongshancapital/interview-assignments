import React from "react";
import { render } from "@testing-library/react";
import BannerList, { Banner_Inter } from ".";

const bannerDatas: Banner_Inter[] = [
  {
    id: 1,
    imgUrl: "",
    title: "1",
    text: "1",
    backgroundColor: "#f00"
  },
  {
    id: 2,
    imgUrl: "",
    title: "2",
    text: "1",
    backgroundColor: "#f00"
  },
  {
    id: 3,
    imgUrl: "",
    title: "3",
    text: "1",
    backgroundColor: "#f00"
  },
];

test("子组件数量正确", () => {
  const { getAllByRole } = render(<BannerList banners={bannerDatas} />);
  const els = getAllByRole('listitem')
  expect(els.length).toBe(bannerDatas.length) 
});
