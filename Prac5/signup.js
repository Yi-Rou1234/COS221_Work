function validateSignupForm() {
    // Get form inputs
    var firstName = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var password1 = document.getElementById("password1").value;
    var password2 = document.getElementById("password2").value;
    
    // Validate first name
    if (firstName === "") {
      alert("Please enter your first name.");
      return false;
    }
    
    // Validate last name
    if (lastName === "") {
      alert("Please enter your last name.");
      return false;
    }
    
    // Validate email
    if (email === "") {
      alert("Please enter your email.");
      return false;
    }
    
    // Check if email is valid
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!email.match(emailRegex)) {
      alert("Please enter a valid email address.");
      return false;
    }
    
    // Validate password
    if (password1 === "") {
      alert("Please enter a password.");
      return false;
    }

    //check password
    if (password1 !== password2) {
        alert("Password does not match");
        return false;
    }
    
    // Check if password is strong enough (at least 8 characters)
    if (password1.length < 8) {
      alert("Password should be at least 8 characters long.");
      return false;
    }
    
    // If all validations pass, the form is valid
    alert("Signup form is valid!");
    return true;
  }
  