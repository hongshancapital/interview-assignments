import "./App.css";
import { Carousel } from "./carousel";
import airPodsIcon from "./assets/airpods.png";
import iphoneIcon from "./assets/iphone.png";
import tabletIcon from "./assets/tablet.png";
import styled from "styled-components";
import { useMemo } from "react";

const StyledApp = styled.div`
  position: relative;
  text-align: center;
  font-size: 28px;
  font-weight: 400;
  width: 100vw;
  height: 100vh;

  .banner-box {
    position: relative;
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: space-between;

    > span {
      font-size: 42px;
      font-weight: 500;
      z-index: 1;

      :first-child {
        margin-top: 160px;
      }
    }

    .banner {
      width: 100px;
      margin-bottom: 150px;
    }

    &.dark {
      color: black;
    }

    &.light {
      color: white;
    }
  }
`;

function App() {
  const airPodsContent = useMemo(
    () => (
      <div
        className="banner-box dark"
        style={{
          background: "#F1F1F3",
        }}
      >
        <span>
          Buy a Tablet or xPhone for college. <br /> Get airPods.
        </span>
        <img src={airPodsIcon} alt="airPodsIcon" className="banner" />
      </div>
    ),
    []
  );
  const xPhoneContent = useMemo(
    () => (
      <div
        className="banner-box light"
        style={{
          background: "#111",
        }}
      >
        <span>xPhone</span>
        <span>
          Lots to love.Less to spend. <br /> Startint at $399.
        </span>
        <img src={iphoneIcon} alt="iphoneIcon" className="banner" />
      </div>
    ),
    []
  );
  const tabletIconContent = useMemo(
    () => (
      <div
        className="banner-box dark"
        style={{
          background: "#FAFAFA",
        }}
      >
        <span>Tablet</span>
        <span>Just the right amount of everything.</span>
        <img src={tabletIcon} alt="tabletIcon" className="banner" />
      </div>
    ),
    []
  );

  return (
    <StyledApp className="App">
      <Carousel
        list={[
          { id: "airPods", element: airPodsContent },
          { id: "xPhone", element: xPhoneContent },
          { id: "tabletIcon", element: tabletIconContent },
        ]}
      />
    </StyledApp>
  );
}

export default App;
