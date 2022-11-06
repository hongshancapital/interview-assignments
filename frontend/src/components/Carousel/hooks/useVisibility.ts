import { useEffect, useState } from 'react';

function getHiddenProp() {
  var prefixes = ['webkit', 'moz', 'ms', 'o'];
  if ('hidden' in document) {
    return 'hidden';
  }

  for (var i = 0; i < prefixes.length; i++) {
    if (prefixes[i] + 'Hidden' in document) {
      return prefixes[i] + 'Hidden';
    }
  }

  return null;
}
const visProp: any = getHiddenProp();
const evtname = visProp.replace(/[H|h]idden/, '') + 'visibilitychange';

const useVisibility = () => {
  const [hidden, setHidden] = useState(false);

  const changeVisibility = () => {
    setHidden(document.hidden);
  };

  useEffect(() => {
    document.addEventListener(evtname, changeVisibility);

    return () => {
      document.removeEventListener(evtname, changeVisibility);
    };
  }, []);

  return {
    hidden,
    setHidden
  };
};

export default useVisibility;
