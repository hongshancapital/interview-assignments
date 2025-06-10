import type { Size } from "../types/common-types";

export const getImgSizeByUrl = (imgPath: string) => {
  return new Promise<Size>((resolve, reject) => {
    if (imgPath) {
      const img = new Image();
      img.src = imgPath;
      img.onload = () => {
        resolve({
          width: img.width,
          height: img.height,
        });
      };
      img.onerror = reject;
    } else {
      reject({ width: 0, height: 0 });
    }
  });
};

export const calcImgsRealSize = ({
  imgsSize,
  viewSize,
}: {
  imgsSize: Array<Size>;
  viewSize: Size;
}) => {
  const viewRatio = viewSize.width / viewSize.height;
  let scale = Number.MIN_SAFE_INTEGER;
  let imgsScale = [];
  let maxImgRatio = { value: 0, idx: 0 },
    minImgRatio = { value: Number.MAX_SAFE_INTEGER, idx: 0 };

  imgsSize.forEach((imgSize, idx) => {
    const imgRatio = imgSize.width / imgSize.height;
    let curScale = 0;
    if (imgRatio > viewRatio) {
      curScale = viewSize.height / imgSize.height;
    } else {
      curScale = viewSize.width / imgSize.width;
    }

    scale = Math.max(curScale, scale);

    // if (imgRatio < minImgRatio.value) {
    //   minImgRatio = { value: imgRatio, idx };
    // }

    // if (imgRatio > maxImgRatio.value) {
    //   maxImgRatio = { value: imgRatio, idx };
    // }
  });

  // if (viewRatio > 1) {
  //   scale = viewSize.width / imgsSize[minImgRatio.idx].width;
  // } else {
  //   scale = viewSize.height / imgsSize[maxImgRatio.idx].height;
  // }

  return imgsSize.map((imgSize) => ({
    width: imgSize.width * scale,
    height: imgSize.height * scale,
  }));
};
