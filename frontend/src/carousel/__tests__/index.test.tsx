import tabletIcon from "../../assets/tablet-icon.png";
import { act, fireEvent, render } from "@testing-library/react";
import { Carousel } from "../index";
import xPhoneIcon from "../../assets/iphone-icon.png";
import airPodsIcon from "../../assets/airpods-icon.png";

describe("测试carousel组件", () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  beforeAll(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.clearAllTimers();
    jest.restoreAllMocks();
  });

  afterAll(() => {
    jest.useRealTimers();
  });

  const mockData = [
    {
      id: 1,
      title: ["xPhone123"],
      backgroundColor: "#111111",
      description: ["Lots to love. Less to spend.", "Starting at $399."],
      textColor: "#fafafa",
      icon: xPhoneIcon,
    },
    {
      id: 3,
      title: ["Tablet234"],
      description: ["Just the right amount of everything."],
      icon: tabletIcon,
      backgroundColor: "#fafafa",
    },
    {
      id: 5,
      title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
      backgroundImage: airPodsIcon,
      backgroundColor: "#f0f0f2",
      icon: airPodsIcon,
    },
  ];

  const mockDataWithSameId = [
    {
      id: 1,
      title: ["xPhone123"],
      backgroundColor: "#111111",
      description: ["Lots to love. Less to spend.", "Starting at $399."],
      textColor: "#fafafa",
      icon: xPhoneIcon,
    },
    {
      id: 1,
      title: ["Tablet234"],
      description: ["Just the right amount of everything."],
      icon: tabletIcon,
      backgroundColor: "#fafafa",
    },
  ];
  test("测试carousel是否渲染着正确数量的slide和indicator", () => {
    const { container } = render(<Carousel data={mockData} />);

    const slideContainer = container.querySelector(
      "[class^=carousel-transform-container]"
    );

    expect(slideContainer?.childNodes.length).toEqual(mockData.length);

    const indicatorContainer = container.querySelector(
      "[class^=carousel-indicator-container]"
    )?.firstChild;

    expect(indicatorContainer?.childNodes.length).toEqual(mockData.length);
  });
  test("测试carousel的自动播放", () => {
    const { container } = render(<Carousel data={mockData} />);

    const slideContainer = container.querySelector(
      "[class^=carousel-transform-container]"
    );

    expect(slideContainer).toHaveStyle(`transform: translateX(0%)`);

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(-100%)`);

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(-200%)`);

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(0%)`);
  });

  test("测试carousel的点击", () => {
    const { container } = render(<Carousel data={mockData} />);
    jest.spyOn(global, "clearInterval");
    const slideContainer = container.querySelector(
      "[class^=carousel-transform-container]"
    );

    const indicatorContainer = container.querySelector(
      "[class^=carousel-indicator-container]"
    )?.firstChild;
    // 点击第二个slide
    act(() => {
      fireEvent.click(indicatorContainer?.childNodes[1]!);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(-100%)`);
    expect(clearInterval).toHaveBeenCalledTimes(1);
    // 再次点击第二个slide
    act(() => {
      fireEvent.click(indicatorContainer?.childNodes[1]!);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(-100%)`);
    expect(clearInterval).toHaveBeenCalledTimes(2);
    // 点击回第1个slide
    act(() => {
      fireEvent.click(indicatorContainer?.childNodes[0]!);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(0%)`);
    expect(clearInterval).toHaveBeenCalledTimes(3);
    // 等待slide自动走到第二个，然后再点击第三个slide
    act(() => {
      jest.advanceTimersToNextTimer();
      fireEvent.click(indicatorContainer?.childNodes[2]!);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(-200%)`);
    expect(clearInterval).toHaveBeenCalledTimes(4);
    // 等待slide即将走完第三个slide，然后再点击第三个slide
    act(() => {
      jest.advanceTimersByTime(2000);
      fireEvent.click(indicatorContainer?.childNodes[2]!);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(-200%)`);
    expect(clearInterval).toHaveBeenCalledTimes(5);
  });

  test("测试id相同时是否报错", () => {
    const consoleSpy = jest
      .spyOn(console, "error")
      .mockImplementation(() => {});

    render(<Carousel data={mockDataWithSameId} />);

    expect(consoleSpy).toHaveBeenCalled();
  });

  test("测试autoplay参数为false", () => {
    const { container } = render(<Carousel data={mockData} autoplay={false} />);
    const slideContainer = container.querySelector(
      "[class^=carousel-transform-container]"
    );

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(0%)`);
  });

  test("测试仅有一条数据", () => {
    const { container } = render(<Carousel data={mockData.slice(0, 1)} />);

    const slideContainer = container.querySelector(
      "[class^=carousel-transform-container]"
    );

    act(() => {
      jest.advanceTimersByTime(3000);
    });

    expect(slideContainer).toHaveStyle(`transform: translateX(0%)`);
  });

  test("测试数据为空", () => {
    const { container } = render(<Carousel data={[]} />);

    const slideContainer = container.querySelector(
      "[class^=carousel-transform-container]"
    );

    expect(slideContainer?.childNodes.length).toEqual(0);

    const indicatorContainer = container.querySelector(
      "[class^=carousel-indicator-container]"
    )?.firstChild;

    expect(indicatorContainer?.childNodes.length).toEqual(0);
  });
});
