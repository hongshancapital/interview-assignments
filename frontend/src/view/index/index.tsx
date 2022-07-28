import React, { Fragment } from "react";
import Carousel from "../../components/carousel";
const contentStyle: React.CSSProperties = {
  height: "100vw",
  color: "#fff",
  lineHeight: "100vh",
  textAlign: "center",
  background: "#364d79",
  width: "100vw",
};
const Index = () => {
  return (
    <Fragment>
      {/* <div className="index_container">6666</div> */}
      <Carousel>
        <div>
          <h3 style={contentStyle}>1</h3>
        </div>
        <div>
          <h3 style={contentStyle}>2</h3>
        </div>
        <div>
          <h3 style={contentStyle}>3</h3>
        </div>
        <div>
          <h3 style={contentStyle}>4</h3>
        </div>
      </Carousel>
    </Fragment>
  );
};
export default Index;
