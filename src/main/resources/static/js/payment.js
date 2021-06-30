// A reference to Stripe.js initialized with your real test publishable API key.
var stripe = Stripe(publicKey);

// Disable the button until we have Stripe set up on the page
document.querySelector("#submit").disabled = true;

var elements = stripe.elements();
var style = {
  base: {
    color: "#32325d",
    fontFamily:
      'system-ui,-apple-system,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans","Liberation Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji"',
    fontSmoothing: "antialiased",
    fontSize: "16px",
    "::placeholder": {
      color: "#32325d",
    },
  },
  invalid: {
    fontFamily:
      'system-ui,-apple-system,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans","Liberation Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji"',
    color: "#fa755a",
    iconColor: "#fa755a",
  },
};

var card = elements.create("card", { style: style });

// Stripe injects an iframe into the DOM
card.mount("#card-element");
card.on("change", function (event) {
  // Disable the Pay button if there are no card details in the Element
  document.querySelector("#submit").disabled = event.empty;
  document.querySelector("#card-error").textContent = event.error
    ? event.error.message
    : "";
});

var form = document.getElementById("payment-form");
form.addEventListener("submit", function (event) {
  event.preventDefault();
  // Complete payment when the submit button is clicked
  payWithCard(stripe, card, clientSecret);
});

// Calls stripe.confirmCardPayment
// If the card requires authentication Stripe shows a pop-up modal to
// prompt the user to enter authentication details without leaving your page.
var payWithCard = function (stripe, card, clientSecret) {
  loading(true);
  stripe
    .confirmCardPayment(clientSecret, {
      receipt_email: userEmail,
      payment_method: {
        card: card,
        billing_details: {
          email: userEmail,
        },
      },
    })
    .then(function (result) {
      if (result.error) {
        // Show error to your customer
        showError(result.error.message);
      } else {
        // The payment succeeded!
        orderComplete(result.paymentIntent.id);
      }
    });
};

/* ------- UI helpers ------- */
// Shows a success message when the payment is complete
var orderComplete = function (paymentIntentId) {
  loading(false);
  document.querySelector(".result-message").classList.remove("d-none");
  document.querySelector("#submit").disabled = true;
};

// Show the customer the error from Stripe if their card fails to charge
var showError = function (errorMsgText) {
  loading(false);
  var errorMsg = document.querySelector("#card-error");
  errorMsg.textContent = errorMsgText;
  setTimeout(function () {
    errorMsg.textContent = "";
  }, 4000);
};

// Show a spinner on payment submission
var loading = function (isLoading) {
  if (isLoading) {
    // Disable the button and show a spinner
    document.querySelector("#submit").disabled = true;
    document.querySelector("#spinner").classList.remove("d-none");
    document.querySelector("#button-text").classList.add("d-none");
  } else {
    document.querySelector("#submit").disabled = false;
    document.querySelector("#spinner").classList.add("d-none");
    document.querySelector("#button-text").classList.remove("d-none");
  }
};
