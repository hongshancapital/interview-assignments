import Immutable, { is } from "immutable";

export const deepEqual = (prevProps: object, nextProps: object): boolean => {
  if (Object.keys(prevProps).length !== Object.keys(nextProps).length) {
    return false;
  }

  const prevProp = Immutable.fromJS(prevProps);
  const nextProp = Immutable.fromJS(nextProps);
  return is(prevProp, nextProp);
};
