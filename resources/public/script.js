function sendText() {
    const input = document.getElementById("text-input").value;

    fetch('/api/submit', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({ text: input })
    }).then(response => response.text()).then(data => {
        alert("Server responded: " + data);
    });
}
