export const formateTextToArray = (text: string | string[] | undefined): string[] => {
  let result: string[] = [];
  if (text) {
    if (Array.isArray(text)) {
      result = text;
    } else {
      result = [text];
    }
  }
  return result;
};

export const boxRole = "carousel-box";

export const flagRole = "carousel-flag";
