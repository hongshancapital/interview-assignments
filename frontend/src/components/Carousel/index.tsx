import React, {useEffect } from "react";
import styled from 'styled-components'; 
import CarouselCom from './Carousel';
import AirPodsImg from '@/assets/airpods.png';
import IphoneImg from '@/assets/iphone.png';
import TabletImg from '@/assets/tablet.png';

const Wrapper = styled.div `
  .container {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    .box {
      width: 800px;
      height: 300px;
      margin: 0 auto;

      .carousel {
        position: relative;
        width: 100%;
        height: 100%;
        overflow: hidden;

        .carousel-wrap {
          display: flex;
          height: 100%;
          position: absolute;
          left: 0;
          top: 0;
          transition-property: left;

          .carousel-slide {
            display: flex;
            width: 100%;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            text-align: center;
            position: relative;
            .description {
              position: absolute;
              color: #fff;
              top: 15%;
              left: 50%;
              transform: translateX(-50%);
              .black {
                color: #000;
              }
              .bold {
                font-weight: bold;
                font-size: 24px;
              }
            }
            img {
              display: block;
              width: 100%;
              height: 100%;
            }            
          }
        }

        .carousel-pagination-wrap {
          position: absolute;
          width: 100%;
          text-align: center;
          left: 0;
          bottom: 10px;

          .progress {
            display: inline-block;
            width: 48px;
            height: 2px;
            background-color: #ccc;
            margin: 0 4px;
            cursor: pointer;
          }

          .progress-active {
            position: relative;
            width: 48px;
          
            .progress-child {
              position: absolute;
              left: 0;
              top: 0;
              width: 100%;
              opacity: 1;
              height: 2px;
              animation: transColor 3s linear 0s infinite;
              background-color: #fff;
            }
          }
        }
      }
    }
    @keyframes transColor {
      from {
        width: 0;
      }

      to {
        width: 100%;
      }
    }  
`

const Carousel: React.FunctionComponent  = () => {

  useEffect(() => {
    const carousels = document.getElementsByClassName('carousel');  

    const carousel = new CarouselCom(
      carousels[0] as HTMLElement, 
      {
        speed: 1,
        loop: true,
        autoplay: true,
        delay: 3000,
      });

      carousel.on('transitionEnd', function (index:Number) {
        console.log('transitionEnd：' + index);
      }); 
  }, [])

  return (
    <Wrapper>
      <div className="container">
        <div className="box">
          <div className="carousel">
            <div className="carousel-wrap">
              <div className="carousel-slide">
                <div className="description">
                  <p>xPhone</p>
                  <p>Lots to love. Less to spend</p>
                  <p>Starting at $399</p>
                </div>
                <img src={IphoneImg} alt="" />
              </div>
              <div className="carousel-slide">
                <div className="description">
                  <p className="black bold">Tablet</p>
                  <p className="black">Just the right acount of everything.</p>
                </div>
                <img src={AirPodsImg} alt="" />
              </div>
              <div className="carousel-slide">
                <div className="description">
                  <p className="black bold">Buy a Tablet or xPhone for college</p>
                  <p className="black bold">Get airpods.</p>
                </div>
                <img src={TabletImg} alt="" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </Wrapper>
  );
}

export default Carousel;
