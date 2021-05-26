ready(function () {
  var listItems = document.querySelectorAll("#language-list li");
  listItems.forEach(setLangCookie);
});

function setLangCookie(item) {
  item.addEventListener("click", (event) => {
    document.cookie = "LANG=" + event.currentTarget.dataset.lang;
  });
}

function ready(fn) {
  if (
    document.readyState === "complete" ||
    document.readyState === "interactive"
  ) {
    setTimeout(fn, 1);
  } else {
    document.addEventListener("DOMContentLoaded", fn);
  }
}
