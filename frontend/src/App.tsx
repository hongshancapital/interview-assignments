import React from "react";
import "./App.css";
import { DemoAppleSlides } from "./components/doc/examples/AppleProductInfo";
function App() {
  return (
    <div className="App bg-gray-600 relative text-white">
      <div className="w-full h-full flex justify-center">
        <DemoAppleSlides />
      </div>
    </div>
  );
}

export default App;
