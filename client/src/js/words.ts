namespace Words {
    const main = (): void => {
        const characterBuffer: Array<string> = [];

        const eventSource = new EventSource('/words/stream');
        eventSource.onmessage = (event) => {
            const characters = Array.from(event.data as string);
            characterBuffer.push(...characters, ' ') // Put a space between this sentence and the next
        };

        const textVisibleContainer = document.getElementById('text');
        let textVisible = '';

        const textInvisibleContainer = document.getElementById('textInvisible');
        let textInvisible = '';

        const writeNextCharacter = (): void => {
            while (characterBuffer.length > 0 && textInvisible.length < 50) {
                textInvisible += characterBuffer.shift();
            }

            if (textInvisible.length > 0) {
                const characterToDraw = textInvisible.charAt(0);
                textInvisible = textInvisible.slice(1);
                textVisible += characterToDraw;
            }

            textVisibleContainer.firstChild.nodeValue = textVisible;
            textInvisibleContainer.innerText = textInvisible;
        };

        writeNextCharacter();
        setInterval(writeNextCharacter, 80);
    };

    window.onload = main;
}
