(function() {
  "use strict";

  window.addEventListener("load", init);

  function init() {
    console.log("Window loaded!"); // Log when the window is fully loaded

    // Attach event listeners to the buttons
    document.getElementById("encrypt-it").addEventListener("click", handleEncryptClick);
    document.getElementById("reset").addEventListener("click", handleReset);
  }

  function handleEncryptClick() {
    let inputText = document.getElementById("input-text").value; // Get text from textarea
    let encryptedText = shiftCipher(inputText); // Encrypt the text
    document.getElementById("result").textContent = encryptedText; // Output the encrypted text
    console.log("Encryption done!"); // Log encryption completion
  }

  function handleReset() {
    document.getElementById("input-text").value = ''; // Clear the textarea
    document.getElementById("result").textContent = ''; // Clear the result paragraph
    console.log("Reset button clicked!"); // Log reset action
  }

  /**
   * Encrypts the provided text using a simple shift cipher (shifts each letter by 1).
   * 'z' becomes 'a', and all other letters shift one place in the alphabet.
   * Non-alphabetical characters remain unchanged.
   * @param {string} text The text to encrypt.
   * @return {string} The encrypted text.
   */
  function shiftCipher(text) {
    text = text.toLowerCase();
    let result = "";
    for (let i = 0; i < text.length; i++) {
      let char = text[i];
      if (char >= 'a' && char <= 'z') {
        // Shift character code by 1, wrap around 'z' to 'a'
        let shifted = (char === 'z') ? 'a' : String.fromCharCode(char.charCodeAt(0) + 1);
        result += shifted;
      } else {
        result += char; // Add non-alphabetical characters unchanged
      }
    }
    return result;
  }

})();
