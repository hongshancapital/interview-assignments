import md5 from "md5";

let urlIdAlphabet =
  "AaBbCcDdEeFfGgHhIiJj-KkLlMmNnOoPpQqRrSsTtUuVvWwXxYy_Zz0123456789";

export let shortId = () => {
  let id = "";
  let i = 8;
  while (i--) {
    id += urlIdAlphabet[(Math.random() * 64) | 0]; // 也可以用crypto.randomFillSync 替换，随机更严谨
  }
  return id;
};

export let shortStableId = (origUrl) => {
  let id = "";
  const str = md5(origUrl);
  let tmp = "";
  for (let i = 0; i < 4; i++) {
    let val = parseInt(str.substr(i * 8, 8), 16) || 0x3fffffff;
    tmp += val.toString(2).substr(0, 30);
  }
  for (let n = 0; n < 8; n++) {
    let index = parseInt(tmp.substr(n * 6, 6), 2);
    id += urlIdAlphabet[index | 0];
  }
  return id;
};
