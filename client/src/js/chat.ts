namespace Chat {
    const messageQueue: string[] = [];

    const onSubmit = async (event: any): Promise<void> => {
        event.preventDefault();

        const textBox = document.getElementById('textBox') as HTMLInputElement
        const submitButton = document.getElementById('submitButton') as HTMLInputElement;

        textBox.disabled = true;
        submitButton.disabled = true;

        const text = textBox.value;
        await speak(text);

        textBox.disabled = false;
        submitButton.disabled = false;
    }

    const speak = async (text: string): Promise<void> => {
        const url = `https://voice.andrewsmith.io/${encodeURIComponent(text)}`;
        const audio = new Audio(url);

        audio.addEventListener('canplaythrough', () => {
            audio.play();
        });

        return new Promise((resolve) => {
            audio.addEventListener('ended', () => {
                resolve();
            });
        });
    }

    const pop = async () => {
        if (messageQueue.length > 0) {
            const message = messageQueue.shift();
            await speak(message);
        }

        setTimeout(pop, 200);
    }

    const main = (): void => {
        document.getElementById('form').addEventListener('submit', onSubmit);

        const messageEventSource = new EventSource('/messages/stream');
        messageEventSource.onmessage = (event) => {
            messageQueue.push(event.data);
        };

        // Recursively repeats with delay in between calls
        pop();
    };

    window.onload = main;
}
