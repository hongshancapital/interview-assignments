import React, { useEffect, useState } from "react";
import Carousel from "../components/carousel";
import "./style.scss";

function AppleAd() {
  const [width, setWidth] = useState<number>(window.innerWidth);
  const [height, setHeight] = useState<number>(window.innerHeight);

  useEffect(() => {
    let timeoutId: NodeJS.Timeout;
    let lastRunTime = 0;
    const updateSize = () => {
      setWidth(window.innerWidth);
      setHeight(window.innerHeight);
      lastRunTime = Date.now();
    };
    const handleResize = () => {
      let now = Date.now();
      clearTimeout(timeoutId);
      if (now - lastRunTime > 100) {
        updateSize();
        return;
      }
      timeoutId = setTimeout(updateSize, 100);
    };
    window.addEventListener("resize", handleResize);
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  return (
    <div>
      <Carousel width={width} height={height} className="ad-carousel" autoplay>
        <IphoneAd></IphoneAd>
        <TabletAd></TabletAd>
        <ArPodsAd></ArPodsAd>
      </Carousel>
    </div>
  );
}
/**
 * phone 广告
 */
function IphoneAd() {
  return (
    <div className="ad-container phone-bg">
      <div className="phone-content ad-content">
        <p style={{ fontSize: "58px", marginBottom: "14px", fontWeight: 600 }}>
          xPhone
        </p>
        <p style={{ fontSize: "29px", lineHeight: "32px" }}>
          Lots to love. Less to spend.
        </p>
        <p style={{ fontSize: "29px", lineHeight: "32px" }}>
          Starting at $399.
        </p>
        <div className="phone-icon"></div>
      </div>
    </div>
  );
}

/**
 * tablet 广告
 */
function TabletAd() {
  return (
    <div className="ad-container tablet-bg">
      <div className="tablet-content ad-content">
        <p style={{ fontSize: "56px", marginBottom: "13px", fontWeight: 640 }}>
          Tablet
        </p>
        <p style={{ fontSize: "29px", lineHeight: "32px" }}>
          Just the right amount of everything.
        </p>

        <div className="tablet-icon"></div>
      </div>
    </div>
  );
}

/**
 * arPods 广告
 */
function ArPodsAd() {
  return (
    <div className="ad-container arPods-bg">
      <div className="tablet-content ad-content">
        <p style={{ fontSize: "56px", marginBottom: "0px", fontWeight: 640 }}>
          Buy a Tablet or xPhone for college.
        </p>
        <p style={{ fontSize: "56px", fontWeight: 640 }}>Get arPods.</p>

        <div className="arPods-icon"></div>
      </div>
    </div>
  );
}

export default AppleAd;
