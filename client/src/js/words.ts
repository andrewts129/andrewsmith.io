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

        const isWhitespace = (s: String) => s.trim() === '';
        const writeNextCharacter = (): void => {
            if (characterBuffer.length > 0) {
                output += characterBuffer.shift();
                wordsContainer.innerText = output;

                let restOfWordPlaceholderText = '';
                for (const character of characterBuffer) {
                    const x = isWhitespace(character)
                    if (x) {
                        break;
                    } else {
                        restOfWordPlaceholderText += character;
                    }
                }

                const restOfWordPlaceholder = document.createElement('span');
                restOfWordPlaceholder.innerText = restOfWordPlaceholderText;
                wordsContainer.appendChild(restOfWordPlaceholder);
            }
        };

        writeNextCharacter();
        setInterval(writeNextCharacter, 80);
    };

    window.onload = main;
}
