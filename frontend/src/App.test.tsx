import { DataItem } from "./assets/data";
import { render, screen, cleanup, waitFor } from "@testing-library/react";
import Carousel from "./carousel/Carousel";

afterEach(cleanup);

// 验证文本是否渲染
test("view dom render normal", () => {
  const data: DataItem[] = [
    {
      bgStyle: {},
      title: [
        ["qqq", {}],
        ["qqqq", {}],
      ],
      imgage: {},
      id: "aaa",
    },
    {
      bgStyle: {},
      title: [
        ["www", {}],
        ["wwww", {}],
      ],
      imgage: {},
      id: "bbb",
    },
  ];
  render(<Carousel datasource={data} width={1920} />);
  // screen.debug();
  expect(screen.getByText("qqq")).toBeInTheDocument();
  expect(screen.getByText("qqqq")).toBeInTheDocument();
  expect(screen.getByText("www")).toBeInTheDocument();
  expect(screen.getByText("wwww")).toBeInTheDocument();
});

// 验证轮播效果，判断依据，transform: translate(x,x) 值
test("view carousel execute normal", async () => {
  const INTERVAL = 1000;
  const data: DataItem[] = [
    {
      bgStyle: {},
      title: [
        ["qqq", {}],
        ["qqqq", {}],
      ],
      imgage: {},
      id: "aaa",
    },
    {
      bgStyle: {},
      title: [
        ["www", {}],
        ["wwww", {}],
      ],
      imgage: {},
      id: "bbb",
    },
    {
      bgStyle: {},
      title: [
        ["haha", {}],
        ["nihao", {}],
      ],
      imgage: {},
      id: "ccc",
    },
  ];
  const { getByTestId } = render(
    <Carousel datasource={data} width={1920} interval={INTERVAL} />
  );

  // screen.debug();

  let aaa = getByTestId("aaa");
  let bbb = getByTestId("bbb");
  let ccc = getByTestId("ccc");

  expect(aaa).toHaveStyle({
    transform: "translate(0px, 0px)",
  });
  expect(bbb).toHaveStyle({
    transform: "translate(1920px, 0px)",
  });
  expect(ccc).toHaveStyle({
    transform: "translate(3840px, 0px)",
  });

  // 等待 INTERVAL 后，验证执行结果
  await waitFor(
    () => {
      screen.debug();
      expect(aaa).toHaveStyle({
        transform: "translate(-1920px, 0px)",
      });
      expect(bbb).toHaveStyle({
        transform: "translate(0px, 0px)",
      });
      expect(ccc).toHaveStyle({
        transform: "translate(1920px, 0px)",
      });
    },
    {
      timeout: INTERVAL,
    }
  );

  await waitFor(
    () => {
      screen.debug();
      expect(aaa).toHaveStyle({
        transform: "translate(-3840px, 0px)",
      });
      expect(bbb).toHaveStyle({
        transform: "translate(-1920px, 0px)",
      });
      expect(ccc).toHaveStyle({
        transform: "translate(0px, 0px)",
      });
    },
    {
      timeout: INTERVAL * 2,
    }
  );

  await waitFor(
    () => {
      screen.debug();
      expect(aaa).toHaveStyle({
        transform: "translate(0px, 0px)",
      });
      expect(bbb).toHaveStyle({
        transform: "translate(1920px, 0px)",
      });
      expect(ccc).toHaveStyle({
        transform: "translate(3840px, 0px)",
      });
    },
    {
      timeout: INTERVAL * 3,
    }
  );
});
