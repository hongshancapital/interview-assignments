const clone = (obj: any): any => JSON.parse(JSON.stringify(obj));

const isEmpty = (sth: any): boolean => {
  return (
    sth !== false &&
    (sth === null || sth === "" || sth === undefined || sth.length === 0)
  );
};

const sliderSort = (num: number): Array<Array<number>> => {
  let re: Array<Array<number>> = [],
    origin: Array<any> = [];
  for (let i = 0; i < num; i++) {
    origin.push(i + 1 === num ? -1 : i);
  }
  for (let j = 0; j < num; j++) {
    if (j !== 0) {
      let _last = origin.pop();
      origin.unshift(_last);
    }
    re.push(clone(origin));
  }
  return re;
};

const numberArray = (num: number): Array<number> => {
  let re: Array<number> = [];
  for (let i = 0; i < num; i++) {
    re.push(i);
  }
  return re;
};

export { isEmpty, sliderSort, numberArray };
