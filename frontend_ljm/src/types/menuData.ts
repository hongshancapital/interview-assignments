type MenuItem = {
  page: string;
  title: string;
  exact: boolean;
  icon: string;
  url: string;
};

export type MenuData = MenuItem[][];

export type MenuChildData = {
  [key: string]: {
    title: string;
    exact: boolean;
    icon: string;
    url: string;
    child: {
      title: string;
      exact: boolean;
      url: string;
    }[];
  }[];
};
