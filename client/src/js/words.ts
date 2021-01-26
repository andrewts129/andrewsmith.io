namespace Words {
    const main = (): void => {
        const characterBuffer: Array<String> = [];

        const eventSource = new EventSource('/words/stream');
        eventSource.onmessage = (event) => {
            const characters = Array.from(event.data as String);
            characterBuffer.push(...characters, ' ') // Put a space between this sentence and the next
        };

        let visibleOutput = '';
        const visibleContainer = document.getElementById('visibleCharContainer');
        const invisibleContainer = document.getElementById('invisibleCharContainer');

        const isWhitespace = (s: String) => s.trim() === '';
        const writeNextCharacter = (): void => {
            if (characterBuffer.length > 0) {
                visibleOutput += characterBuffer.shift();
                visibleContainer.innerText = visibleOutput;

                let restOfWordPlaceholderText = '';
                for (const character of characterBuffer) {
                    const x = isWhitespace(character)
                    if (x) {
                        break;
                    } else {
                        restOfWordPlaceholderText += character;
                    }
                }

                invisibleContainer.innerText = restOfWordPlaceholderText;
            }
        };

        writeNextCharacter();
        setInterval(writeNextCharacter, 80);
    };

    window.onload = main;
}
