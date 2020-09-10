import React from "react";

import { createUseStyles } from "utils";
import { Carousel } from "components";

const useStyles = createUseStyles(({ css }) => ({
    container: css`
        width: 800px;
        height: 600px;
    `,
    card: css`
        position: relative;
        width: 100%;
        height: 100%;
        .pic {
            width: 100%; 
            height: 100%;
            object-fit: cover;
            object-position: center bottom;
        }
        .info {
            position: absolute;
            left: 0;
            top: 60px;
            width: 100%;
            text-align: center;
            &.light {
                color: #ffffff;
            }
            &.dark {
                color: #000000;
            }
            .title {
                font-size: 30px;
                font-weight: 500;
            }
            .desc {
                font-size: 22px;
                font-weight: 500;
                margin-top: 20px;
            }            
        }
    `
}));

const App = () => {

    const classes = useStyles();

    return (
        <div className={classes.container}>
            <Carousel ms={5000}>
                <div className={classes.card}>
                    <img alt="" className="pic" src={require("assets/airpods.png")} />
                    <div className="info dark">
                        <div className="title">
                            Buy a Tablet or xPhone for college.
                            <br />
                            Get arPods.
                        </div>
                    </div>
                </div>
                <div className={classes.card}>
                    <img alt="" className="pic" src={require("assets/iphone.png")} />
                    <div className="info light">
                        <div className="title ">
                            xPhone
                        </div>
                        <div className="desc">
                            Lots to love.Less to spend.
                            <br />
                            Starting at $399.
                        </div>
                    </div>
                </div>
                <div className={classes.card}>
                    <img alt="" className="pic" src={require("assets/tablet.png")} />
                    <div className="info dark">
                        <div className="title">Tablet</div>
                        <div className="desc">Just the right amount of everything</div>
                    </div>
                </div>
            </Carousel>
        </div>
    );
}

export default App;
