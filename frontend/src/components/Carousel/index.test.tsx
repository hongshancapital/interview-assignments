import React from 'react';
import { render } from "@testing-library/react";
import Carousel,{DataProps} from "./index";
const list: DataProps['list'] = [
    {
        class: 'iphone',
        title: ['xPhone'],
        text: ['Lots to love.Less to spend.','Starting at $399.']
    },
    {
        class: 'tablet',
        title: ['Tablet'],
        text: ['Just the right amount of everything.'],
    },
    {
        class: 'airpods',
        title: ['Buy a Tablet or xPhone for college.','Get arPods.'],
    }
]
describe("Carousel test", () => {
  it("render every item's title and text", () => {
    const { getByText } = render(<Carousel list={list} />);

    list.forEach((i) => {
        i.title.forEach((c) => {
            const title = getByText(c);
            expect(title).toBeInTheDocument();
        });
        i.text?.forEach((c) => {
            const text = getByText(c);
            expect(text).toBeInTheDocument();
        });
    });
  });

  it("render progress", () => {
    const { container } = render(<Carousel list={list}  />);
    const progressElem = container.querySelector(".dots-box");
    expect(progressElem).toBeInTheDocument();

    const itemElem = progressElem?.querySelector(".dots-item");
    expect(itemElem).toBeInTheDocument();
  });
});