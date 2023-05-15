import "./App.css";
import Carousel from "./components/Carousel/Carousel";
import { useState } from "react";

function App() {
  const [carousels] = useState([
    <section
      style={{
        backgroundColor: "#000000",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-around",
        alignItems: "center",
        color: "#ffffff",
        height: "100vh",
        width: "100%",
        fontSize: "24px",
      }}
    >
      <div>
        <p style={{ fontSize: "40px", fontWeight: "bold" }}>XPhone</p>
        <p>Lots to love.Less to spend.</p>
        <p>Starting at $399.</p>
      </div>
      {/* img */}
    </section>,
    <section
      style={{
        backgroundColor: "#ffffff",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-around",
        alignItems: "center",
        color: "#262626",
        height: "100vh",
        width: "100%",
        fontSize: "24px",
      }}
    >
      <div>
        <p style={{ fontWeight: "bold", fontSize: "40px" }}>Tablet</p>
        <p>Just the right amount of everything.</p>
      </div>
      {/* img */}
    </section>,
    <section
      style={{
        backgroundColor: "#f1f1f3",
        display: "flex",
        flexDirection: "column",
        justifyContent: "space-around",
        alignItems: "center",
        color: "#262626",
        height: "100vh",
        width: "100%",
        fontSize: "48px",
        fontWeight: "bold",
      }}
    >
      <div>
        <p>Buy a Tablet or XPhone for college.</p>
        <p>Get airPods.</p>
      </div>
      {/* img */}
    </section>,
  ]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [autoplay] = useState(3000);
  const [transitionOptions] = useState({
    duration: 1000,
    timingFunction: "cubic-bezier(0.16, 1, 0.3, 1)",
    delay: 0,
  });

  return (
    <div className="App">
      {
        <Carousel
          carousels={carousels}
          currentIndex={currentIndex}
          setCurrentIndex={setCurrentIndex}
          autoplay={autoplay}
          transitionOptions={transitionOptions}
        />
        /* write your component here */
      }
    </div>
  );
}

export default App;
