import {FC, useEffect} from 'react'
import { useState, useCallback } from 'react'
import './item.css'

type props = {
  title: string,
  imgUrl: string,
  dec: string
}

const Item:FC <props> = (props)=> {
  const {title, imgUrl, dec} = props
  const [dark, setDark] = useState<boolean>(true)
  // backgroundImage: `url(${imgUrl})`
  
  const toJudgeImage = useCallback((imgSrc: string)=> {
    // 加载图片
    const img = new Image();
    
    img.onload = function() {
      // 创建 Canvas 元素
      const canvas = document.createElement('canvas');
      canvas.width = img.width;
      canvas.height = img.height;

      // 在 Canvas 上绘制图片
      const ctx = canvas.getContext('2d');
      ctx!.drawImage(img, 0, 0);

      // 获取所有像素的颜色值
      let imageData = ctx!.getImageData(0, 0, img.width, img.height);
      let pixels = imageData.data;

      // 计算所有像素的亮度值
      let brightness = 0;
      for (let i = 0; i < pixels.length; i += 4) {
        let r = pixels[i];
        let g = pixels[i + 1];
        let b = pixels[i + 2];
        brightness += 0.299 * r + 0.587 * g + 0.114 * b;
      }
      brightness = brightness / (pixels.length / 4);

      // 根据亮度值更改字体颜色
      let textColor = brightness <= 128 ? true : false;

      setDark(textColor)
    };
    
    img.src = imgSrc;
  }, [])

  useEffect(()=>{
    toJudgeImage(imgUrl)
  }, [imgUrl, toJudgeImage])

  return (
    <div className='w-100 h-100 background-image' 
      style={{flex: "0 0 100%"}}
    >
      <div className='w-100 h-100 relative'>
        <div data-testid="item-test" className={dark? ' absolute margin-top-30 left right dark': 'absolute margin-top-30 left right'}>
          <pre><h1 >{`${title}`}</h1></pre>
          <pre><h3>{`${dec}`}</h3></pre>
        </div>
        <img className='img' width="100%" height="100%" alt="img" src={imgUrl} />
      </div>
    </div>
  )
}

export default Item