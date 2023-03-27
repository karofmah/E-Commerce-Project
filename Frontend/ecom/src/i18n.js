import { createI18n } from 'vue-i18n';
import en from './locales/en';
import nb from './locales/nb';
import es from './locales/es';
import de from './locales/de';
import fr from './locales/fr';

export const i18n = createI18n({
  locale: window.navigator.language.split("-")[0],
  fallbackLocale: "en",
  legacy: false,
  silentTranslationWarn: true,
  messages: {
    en,
    nb,
    es,
    de,
    fr,
  },
});