document.getElementById('conversionForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const base = document.getElementById('base').value.toUpperCase();
    const target = document.getElementById('target').value.toUpperCase();
    const amount = document.getElementById('amount').value;

    try {
        const response = await fetch(`/api/convert?base=${base}&target=${target}&amount=${amount}`);
        const result = await response.json();

        if (response.ok) {
            document.getElementById('result').innerText = `Converted Amount: ${result}`;
        } else {
            document.getElementById('result').innerText = `Error: ${result.message}`;
        }
    } catch (error) {
        document.getElementById('result').innerText = `An error occurred: ${error.message}`;
    }
});
