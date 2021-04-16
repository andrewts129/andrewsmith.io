namespace Words {
    const main = (): void => {
        const paragraphsBuffer: Array<string> = [];

        const eventSource = new EventSource('/words/stream');
        eventSource.onmessage = (event) => {
            paragraphsBuffer.push(event.data as string)
        };

        const textContainer = document.getElementById('textContainer');

        const textActiveVisibleContainer = document.getElementById('activeText');
        let textActiveVisible = '';

        const textActiveInvisibleContainer = document.getElementById('textInvisible');
        let textActiveInvisible = '';

        const saveActiveText = (): void => {
            const newTextContainer = document.createElement('p')
            newTextContainer.innerText = textActiveVisible;
            textActiveVisible = '';
            textActiveVisibleContainer.firstChild.nodeValue = '';

            textContainer.insertBefore(newTextContainer, textActiveVisibleContainer);
        }

        let drawnFirstCharacter = false;

        const writeNextCharacter = (): void => {
            if (paragraphsBuffer.length > 0 && textActiveInvisible.length == 0) {
                if (drawnFirstCharacter) {
                    saveActiveText();
                }
                
                textActiveInvisible = paragraphsBuffer.shift();
            }

            if (textActiveInvisible.length > 0) {
                const characterToDraw = textActiveInvisible.charAt(0);
                textActiveInvisible = textActiveInvisible.slice(1);
                textActiveVisible += characterToDraw;
                drawnFirstCharacter = true;
            }

            textActiveVisibleContainer.firstChild.nodeValue = textActiveVisible;
            textActiveInvisibleContainer.innerText = textActiveInvisible;
        };

        writeNextCharacter();
        setInterval(writeNextCharacter, 80);
    };

    window.onload = main;
}
