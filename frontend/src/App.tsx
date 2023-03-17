import React from "react";
import "./App.css";
import tabletIcon from "./assets/tablet-icon.png";
import airPodsIcon from "./assets/airpods-icon.png";
import xPhoneIcon from "./assets/iphone-icon.png";
import { Carousel } from "./carousel";

const xPhoneData = {
  id: 1,
  title: ["xPhone"],
  backgroundColor: "#111111",
  description: ["Lots to love. Less to spend.", "Starting at $399."],
  textColor: "#fafafa",
  icon: xPhoneIcon,
};

const tabletData = {
  id: 3,
  title: ["Tablet"],
  description: ["Just the right amount of everything."],
  icon: tabletIcon,
  backgroundColor: "#FAFAFA",
};

const airPodsData = {
  id: 2,
  title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
  backgroundImage: airPodsIcon,
  backgroundColor: "#f0f0f2",
  icon: airPodsIcon,
  // image: airPod,
};

const testData = [xPhoneData, tabletData, airPodsData];

function App() {
  return (
    <div className="App">
      <Carousel delay={3000} data={testData} width={"100vw"} height={"100vh"} />
    </div>
  );
}

export default App;
