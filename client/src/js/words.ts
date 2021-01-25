namespace Words {
    const main = (): void => {
        const characterBuffer: Array<String> = [];

        const eventSource = new EventSource('/words/stream');
        eventSource.onmessage = (event) => {
            const characters = Array.from(event.data as String);
            characterBuffer.push(...characters, ' ') // Put a space between this sentence and the next
        };

        let output = '';
        const wordsContainer = document.getElementById('wordsContainer');

        const writeNextCharacter = (): void => {
            if (characterBuffer.length > 0) {
                output = output + characterBuffer.shift();
                wordsContainer.innerText = output;
            }
        };

        writeNextCharacter();
        setInterval(writeNextCharacter, 100);
    };

    window.onload = main;
}
