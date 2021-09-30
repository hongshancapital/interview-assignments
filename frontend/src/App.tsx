import React, { FC, useEffect, useState } from "react";
import "./App.css";
import {
  DemoWithControl,
  DemoWithCycle,
  DemoWithIndicators,
  DemoAppleSlides,
  DemoCyclePaused,
  DemoKeyboardControl,
} from "./components/doc/examples/CarouselDemos";
const examples: {
  [example: string]: FC;
} = {
  control: () => <DemoWithControl />,
  cycle: () => <DemoWithCycle />,
  indicators: () => <DemoWithIndicators />,
  appleProducts: () => <DemoAppleSlides />,
  cyclePaused: () => <DemoCyclePaused />,
  keyboardControl: () => <DemoKeyboardControl />,
};
const getCurrentPosition = () => `${window.location.hash.split("#")[1]||''}`;
function App() {
  const [example, setExample] = useState(getCurrentPosition());
  const CurrentExample = examples[example];
  useEffect(() => {
    const syncExample = () => {
      const e = `${window.location.hash.split("#")[1] || ""}`;
      setExample(e);
    };
    window.addEventListener("hashchange", syncExample);
    return () => window.removeEventListener("hashchange", syncExample);
  }, []);
  useEffect(() => {
    const pos = getCurrentPosition();
    if (example !== pos) {
      window.location.hash = example;
    }
  }, [example]);
  return (
    <div className="App bg-gray-600 relative text-white">
      {CurrentExample ? (
        <>
          <div className="w-full h-full flex justify-center">
            <CurrentExample />
            <div
              className="back
              flex flex-col justify-center items-center
              hover:bg-gray-100 hover:bg-opacity-10
              w-8 h-8
              cursor-pointer"
              onClick={() => setExample("")}
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-6 w-6"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M10 19l-7-7m0 0l7-7m-7 7h18"
                />
              </svg>
            </div>
          </div>
        </>
      ) : (
        <ul className="flex flex-col text-xl justify-center list-disc nav">
          {Object.keys(examples).map((example) => {
            return (
              <li key={example}>
                <button
                  className="hover:underline cursor-pointer"
                  onClick={() => setExample(example)}
                >
                  {example}
                </button>
              </li>
            );
          })}
        </ul>
      )}
    </div>
  );
}

export default App;
