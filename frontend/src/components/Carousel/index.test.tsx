import React from "react";
import { render } from "@testing-library/react";
import Carousel from ".";

import { getSlideDataList } from "../../getSlideDataList";
import { getSlideFunctionByData } from "../../getSlideFunctionByData";



  describe(" Carousel 组件测试", () => {
    const slideDataList=getSlideDataList()
    const itemList=slideDataList.map(slide => getSlideFunctionByData(slide))
    
    test("组件图片正常渲染", () => {
      const { container } = render(<Carousel itemList={itemList} />);
      const firstPicture = container.querySelectorAll(`.slide-wrapper`);
      expect(firstPicture.length).toBe(3);
      const indecator = container.querySelector(".indecator-wrapper");
      expect(indecator).toBeInTheDocument();
    });

    test("组件 indicator 正常渲染", () => {
      const { container } = render(<Carousel itemList={itemList} />);
      const indecator = container.querySelector(".indecator-wrapper");
      expect(indecator).toBeInTheDocument();
    });

    test("indicator 隐藏的场景", () => {
      const { container } = render(
        <Carousel itemList={itemList} showIndicator={false} />
      );
      const indecator = container.querySelector(".indecator-wrapper");
      expect(indecator).toBe(null);
    });
  });
