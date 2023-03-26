import { createI18n } from 'vue-i18n';
import en from './locales/en';
import no from './locales/no';

export const i18n = createI18n({
  locale: 'en',
  legacy: false,
  messages: {
    en,
    no,
  },
});