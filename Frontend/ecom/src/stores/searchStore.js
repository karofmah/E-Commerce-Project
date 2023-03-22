import { reactive } from 'vue';

const state = reactive({
  keyword: '',
});

function getKeyword() {
  return state.keyword;
}

function setKeyword(keyword) {
  state.keyword = keyword;
}

export { getKeyword, setKeyword };