function sendText() {
    const input_name = document.getElementById("input-name");
    const input_title = document.getElementById("input-title");
    const output = document.getElementById("latex-output");

    fetch('/api/submit', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            name: input_name.value,
            title: input_title.value,
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
