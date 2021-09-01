import React, { ReactNode } from "react";
import Carousel from "./component/Carousel";

import "./App.css";

const data = [
  {
    title: "示例标题1",
    description: (
      <span>
        换行的
        <br />
        描述
      </span>
    ),
    theme: "black",
  },
  { title: "示例标题2" },
  {
    title: "示例标题3",
    icon: <img src="https://www.baidu.com/img/flexible/logo/pc/result.png" />,
  },
  { title: "示例标题4", icon: "test" },
];

function App() {
  return (
    <div className="App">
      <Carousel data={data} />
    </div>
  );
}

export default App;
