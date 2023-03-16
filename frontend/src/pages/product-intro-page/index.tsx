import { Carousel } from "../../components/carousel";

import { data } from "./data";
import "./index.css";

const ProductIntro = () => {
  return (
    <Carousel autoplay>
      {data.map((item) => {
        return (
          <div
            key={item.productId}
            className="product-item-container"
            style={{ backgroundColor: `${item.backgroundColor}` }}
          >
            <img
              src={item.imageUrl}
              alt={item.title}
              className="product-item-image"
            />
            <div
              style={{ color: `${item.color}` }}
              className="product-item-intro"
            >
              <div className="product-item-intro-title">{item.title}</div>
              {item.description && (
                <div className="product-item-intro-description">
                  {item.description}
                </div>
              )}
            </div>
          </div>
        );
      })}
    </Carousel>
  );
};

export default ProductIntro;
