import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import { carouselItems } from "./mock";

ReactDOM.render(
  <React.StrictMode>
    <App carouselItems={carouselItems} />
  </React.StrictMode>,
  document.getElementById("root"),
);
