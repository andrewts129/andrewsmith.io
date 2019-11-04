window.onload = async function () {
    async function fetchCurrentArray() {
        return fetch("bogosort/current");
    }

    const startDate = new Date(2019, 11, 4, 18, 27);

    let array = await fetchCurrentArray();
    setInterval(async function() {
        array = await fetchCurrentArray();
    }, 1000);
};