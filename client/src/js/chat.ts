namespace Chat {
    interface State {
        messageBuffer: string[]
        mySubmission: string | undefined
    }

    const state: State = {
        messageBuffer: [],
        mySubmission: undefined
    }

    const blockWhileSubmissionPending = async (): Promise<void> => {
        return new Promise((resolve) => {
            const check = () => {
                if (!state.mySubmission) {
                    resolve();
                } else {
                    setTimeout(check, 100);
                }
            }

            check();
        });
    }

    const onSubmit = async (event: any): Promise<void> => {
        event.preventDefault();

        const textBox = document.getElementById('textBox') as HTMLInputElement
        const submitButton = document.getElementById('submitButton') as HTMLInputElement;

        textBox.disabled = true;
        submitButton.disabled = true;

        const text = textBox.value;

        const response = await fetch('/messages/add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({
                'Body': text
            })
        });

        if (response.ok) {
            state.mySubmission = text;
            await blockWhileSubmissionPending();
        } else {
            alert('Message failed!');
        }

        textBox.value = '';
        textBox.disabled = false;
        submitButton.disabled = false;
    }

    const speak = async (text: string): Promise<void> => {
        if (text.length > 0) {
            const url = `https://voice.andrewsmith.io/${encodeURIComponent(text)}`;
            const audio = new Audio(url);

            audio.addEventListener('canplaythrough', () => {
                audio.play();
            });

            return new Promise((resolve, reject) => {
                audio.addEventListener('ended', () => {
                    resolve();
                });

                audio.addEventListener('error', () => {
                    reject();
                })
            });
        }
    }

    const popFromBuffer = async () => {
        if (state.messageBuffer.length > 0) {
            const message = state.messageBuffer.shift();

            try {
                await speak(message);
            } catch (e) {
                console.log(e);
                alert('Error');
            }

            if (message === state.mySubmission) {
                state.mySubmission = undefined;
            }
        }
    }

    const onClickAnywhere = (): void => {
        document.body.removeEventListener('click', onClickAnywhere, true);
        document.getElementById('introContainer').remove();

        const template = document.getElementById('contentTemplate') as HTMLTemplateElement
        const form = template.content.cloneNode(true);
        document.getElementsByClassName('hero-body')[0].appendChild(form);

        document.getElementById('')
        document.getElementById('form').addEventListener('submit', onSubmit);

        popFromBuffer();
        setInterval(popFromBuffer, 300)
    }

    const main = (): void => {
        const messageEventSource = new EventSource('/messages/stream');
        messageEventSource.onmessage = (event) => {
            state.messageBuffer.push(event.data);
        };

        document.body.addEventListener('click', onClickAnywhere, true);
    };

    window.onload = main;
}
