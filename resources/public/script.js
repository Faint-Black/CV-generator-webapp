function sendText() {
    const input = document.getElementById("text-input");
    const output = document.getElementById("text-output");

    fetch('/api/submit', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ text: input.value })
    })
        .then(response => response.text())
        .then(data => {
            output.value = data;
            output.style.height = 'auto';
            output.style.height = output.scrollHeight + 'px';
        });
}
