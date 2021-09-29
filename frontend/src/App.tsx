import React, { FC, useState } from "react";
import "./App.css";
import {
  DemoWithControl,
  DemoWithCycle,
  DemoWithIndicators,
  DemoAppleSlides,
} from "./components/doc/examples/WithControls";
const examples: {
  [example: string]: FC;
} = {
  WithControl: () => <DemoWithControl />,
  WithCycle: () => <DemoWithCycle />,
  WithIndicators: () => <DemoWithIndicators />,
  WithAppleProductSlides: () => <DemoAppleSlides />,
};
function App() {
  const [example, setExample] = useState("");
  const CurrentExample = examples[example];
  return (
    <div className="App bg-gray-600 relative text-white">
      {example ? (
        <>
        <div className="w-full h-full flex justify-center">
          <CurrentExample />
          <div className="back flex flex-col justify-center items-center hover:bg-gray-100 hover:bg-opacity-10 w-8 h-8" onClick={() => setExample('')}>
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
              <li>
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
