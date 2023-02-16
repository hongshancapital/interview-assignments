// 国际化语言包

import en from './lang/en';
import zh from './lang/zh';

// 默认语言
const defaultLanguage = 'zh';

export type languages = 'en' | 'zh';

export interface LangConfig {
    readonly [name: string]: string
}

export interface LocaleConfig {
    [language: string]: {
        name: string
        pack: LangConfig
    },
}

// 语言列表
export const locales = {
    en: {
        name: 'English',
        pack: en
    },
    zh: {
        name: '中文',
        pack: zh
    }
} as LocaleConfig;

// 获取浏览器默认语言
const getBrowserLanguage = (): string => {
    let lang: string = navigator.language;
    if (locales[lang]) return lang;
    try {
        lang = navigator.language.split('-')[0];
    } catch (e) {
    }
    return lang;
};
// 获取当前语言
const getLanguage = (): string => {
    return String(locales[localStorage.getItem('language') || ''] ? localStorage.getItem('language') : (getBrowserLanguage() || defaultLanguage));
};
// 默认语言
export const defaultLocale: string = getLanguage();

// 设置语言
export const saveDefaultLocale = (locale: languages) => {
    localStorage.setItem('language', locale);
};

// 获取国际化文本方法
export const intl = (ops: string | { id: string }) => {
    if (typeof ops === 'object') ops = ops.id;
    const langugae: string = getLanguage();
    const pack = locales[langugae]?.pack;
    return pack[ops] || ops;
};

export default locales;
