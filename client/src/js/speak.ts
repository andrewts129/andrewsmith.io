namespace Speak {
    const onSubmit = async (event: any): Promise<void> => {
        const textBox = document.getElementById('textBox') as HTMLInputElement
        const submitButton = document.getElementById('submitButton') as HTMLInputElement;

        textBox.disabled = true;
        submitButton.disabled = true;

        const text = textBox.value;
        const url = `https://voice.andrewsmith.io/${encodeURIComponent(text)}`;
        const audio = new Audio(url);

        audio.addEventListener('canplaythrough', () => {
            audio.play();
        });

        audio.addEventListener('ended', () => {
            textBox.disabled = false;
            submitButton.disabled = false;
        })

        event.preventDefault();
    }

    const main = (): void => {
        document.getElementById('form').addEventListener('submit', onSubmit)
    };

    window.onload = main;
}
