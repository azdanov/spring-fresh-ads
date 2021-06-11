ready(function () {
  var listItems = document.querySelectorAll("#language-list li");
  listItems.forEach(setLangCookie);

  initToasts();
});

function setLangCookie(item) {
  item.addEventListener("click", function (event) {
    document.cookie = "LANG=" + event.currentTarget.dataset.lang;
  });
}

function initToasts() {
  var toastElList = [].slice.call(document.querySelectorAll(".toast"));
  var toastList = toastElList.map(function (toastEl) {
    return new bootstrap.Toast(toastEl);
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
