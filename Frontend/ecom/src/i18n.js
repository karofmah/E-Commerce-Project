import { createI18n } from 'vue-i18n';
import en from './locales/en';
import no from './locales/no';
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
    no,
    es,
    de,
    fr,
  },
});