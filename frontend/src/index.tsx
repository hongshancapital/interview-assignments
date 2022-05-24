import React from "react";
import ReactDOM from "react-dom/client";
import { StoryBoard } from "storyboard";
import "styles/index.css";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <React.StrictMode>
    <StoryBoard />
  </React.StrictMode>
);
