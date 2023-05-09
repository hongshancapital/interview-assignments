/*
 * @Description: 图片组件
 * @Author: cmh
 * @Date: 2023-03-02 14:02:57
 * @LastEditTime: 2023-03-02 14:45:35
 * @LastEditors: cmh
 */
import style from "./CarouselInfo.module.css";
/**
 * @param {title} title 标题
 * @param {describe} describe 描述
 * @param {image} image 图片
 * @returns 图片组件
 */
export const CarouselInfo = ({ title = "", describe = "", image = "" }) => {
    return (
      <div className={style.carousel_info_container}>
        <div className={style.carousel_info_info}>
          <div className={style.carousel_info_title}>{title}</div>
          {describe? <span>{describe}</span> :null}
        </div>
        <img src={image} className={style.carousel_info_image} />
      </div>
    );
  };