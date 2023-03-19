import tabletIcon from "../../assets/tablet-icon.png";
import { isIdUniq } from "../utlis/isIdUniq";

describe("测试isIdUniq方法", () => {
  test("测试id不重复的情况", () => {
    const mockData = [
      {
        id: 1,
        title: ["Tablet"],
        description: ["Just the right amount of everything."],
        icon: tabletIcon,
        backgroundColor: "#fafafa",
      },
      {
        id: 3,
        title: ["Tablet"],
        description: ["Just the right amount of everything."],
        icon: tabletIcon,
        backgroundColor: "#fafafa",
      },
      {
        id: 5,
        title: ["Tablet"],
        description: ["Just the right amount of everything."],
        icon: tabletIcon,
        backgroundColor: "#fafafa",
      },
    ];
    const res = isIdUniq(mockData);
    expect(res).toBeTruthy();
  });

  test("测试id重复的情况", () => {
    const mockData = [
      {
        id: 1,
        title: ["Tablet"],
        description: ["Just the right amount of everything."],
        icon: tabletIcon,
        backgroundColor: "#fafafa",
      },
      {
        id: 1,
        title: ["Tablet"],
        description: ["Just the right amount of everything."],
        icon: tabletIcon,
        backgroundColor: "#fafafa",
      },
      {
        id: 5,
        title: ["Tablet"],
        description: ["Just the right amount of everything."],
        icon: tabletIcon,
        backgroundColor: "#fafafa",
      },
    ];
    const res = isIdUniq(mockData);
    expect(res).toBeFalsy();
  });
});
