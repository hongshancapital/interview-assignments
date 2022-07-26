import { renderHook } from "@testing-library/react-hooks";
import { useSlide } from "../hooks";

test("should render ", async () => {
  const items = [
    {
      title: "xPhone",
      subTitle: ["Lots to love. Less to spend.", "Starting at $399."],
      image: "",
      backColor: "#111111",
      foreColor: "white",
    },
    {
      title: "Tablet",
      subTitle: "Just the right amount of everything.",
      image: "",
      backColor: "#fafafa",
      foreColor: "black",
    },
    {
      title: ["Buy a Tablet or xPhone for college.", "Get arPods"],
      image: "",
      backColor: "#f2f2f3",
      foreColor: "black",
    },
  ];
  const { result } = renderHook(() => useSlide(items, 1000, 1000));

  expect(result.current[0]).toBe(0);
  expect(result.current[1]).toBe(false);

  await new Promise((r) => setTimeout(r, 2100));

  expect(result.current[0]).toBe(1);
});
