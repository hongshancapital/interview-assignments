export const POSTFIX: string = '%%#5percent#%%';

export const addPostfix = (url: string) : string => {
  return url ? `${url}${POSTFIX}` : '';
}

export const removePostfix = (url: string) : string => {
  const regex = new RegExp(`(${POSTFIX})+$`);

  return url.replace(regex, '');
}

export const SHORT_DOMAIN: string = 'http://localhost:8080/';

export const addShortDomain = (url: string) : string => {
  return url ? `${SHORT_DOMAIN}${url}` : '';
};

export const removeShortDomain = (url: string) : string => {
  const regex = new RegExp(`^${SHORT_DOMAIN}`);

  return url.replace(regex, '');
}