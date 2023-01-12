let urlIdAlphabet =
  "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz0123456789";

export let shortId = () => {
  let id = "";
  let i = 8;
  while (i--) {
    id += urlIdAlphabet[(Math.random() * 64) | 0]; // 也可以用crypto.randomFillSync 替换，随机更严谨
  }
  return id;
};
