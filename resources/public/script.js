function sendText() {
    const input_name = document.getElementById("input-name").value;
    const input_title = document.getElementById("input-title").value;
    const input_contacts = collectContactEntries();

    const output = document.getElementById("latex-output");
    fetch('/api/submit', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            name: input_name,
            title: input_title,
            contacts: input_contacts,
        })
    })
        .then(response => response.text())
        .then(data => {
            output.innerHTML = data;
            output.style.height = 'auto';
            output.style.height = output.scrollHeight + 'px';
            output.scrollIntoView({ behavior: "smooth" });
        });
}

function collectContactEntries() {
    const entries_texts = Array.from(document.querySelectorAll('#contact-link-form .contact-link-entry input'));
    const entries_options = Array.from(document.querySelectorAll('#contact-link-form .contact-link-entry select'));
    const combined = entries_texts.map((text, i) => ({
        info: text.value,
        type: entries_options[i].value
    }));
    return combined;
}

function plusContactLink() {
    const container = document.getElementById('contact-link-form');
    const newEntry = document.createElement('div');
    newEntry.className = 'contact-link-entry';
    newEntry.innerHTML = `<div style="display: flex;">
<select>
  <option value="github">Github</option>
  <option value="email">Email</option>
  <option value="linkedin">LinkedIn</option>
  <option value="phone">Phone Number</option>
  <option value="location">Location</option>
</select><input type="text" placeholder="Example: https://github.com/greenskin">
</div>`;
    container.appendChild(newEntry);
}

function minusContactLink() {
    const container = document.getElementById('contact-link-form');
    const child = container.querySelector('.contact-link-entry');
    if (child) {
        container.removeChild(child);
    }
}
