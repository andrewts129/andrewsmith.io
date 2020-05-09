import * as d3 from 'd3';

interface State {
    array: number[]
    completions: number
}

const main = (): void => {
    const stateEventSource = new EventSource('/api/bogosort/state');
    stateEventSource.onmessage = (event) => {
        const afterSemicolonSplit = event.data.split(';');
        const newArray = afterSemicolonSplit[0].split(',').map((n: string) => parseInt(n));
        const newCompletions = parseInt(afterSemicolonSplit[1]);

        stateQueue.push({array: newArray, completions: newCompletions});
    };

    const startDate = new Date(2019, 10, 4, 18, 27);

    let currentData = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

    const margin = {top: 20, right: 20, bottom: 20, left: 20};
    const width = 750 - margin.left - margin.right;
    const height = 500 - margin.top - margin.bottom;

    // Queue data from the server as it comes in, then pop and draw once a second
    // Smooths the animation when the network or server sees small delays
    const stateQueue: State[] = [];

    const x = d3.scaleBand()
        .domain([...currentData.keys()].map(x => x.toString()))
        .range([0, width])
        .padding(0.1);
    const y = d3.scaleLinear()
        .domain([d3.min(currentData), d3.max(currentData)])
        .range([height, 0]);

    const svg = d3.select("#bogo-container")
        .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("translate", `translate(${margin.left}, ${margin.top})`);

    const t = svg.transition().duration(500);

    const getDurationString = (start: Date, end: Date): string => {
        const diff = new Date(end.valueOf() - start.valueOf());

        let seconds = diff.getSeconds();
        let minutes = diff.getMinutes();
        let hours = diff.getHours();
        let days = diff.getDate();
        let months = diff.getMonth();
        let years = diff.getFullYear() - 1970;

        let result;

        if (seconds === 1) {
            result = "1 second"
        } else {
            result = "" + seconds + " seconds"
        }

        if (minutes === 1) {
            result = "1 minute and " + result
        } else if (minutes > 1) {
            result = "" + minutes + " minutes and " + result
        }

        if (hours === 1) {
            result = "1 hour, " + result
        } else if (hours > 1) {
            result = "" + hours + " hours, " + result
        }

        if (days === 1) {
            result = "1 day, " + result
        } else if (days > 1) {
            result = "" + days + " days, " + result
        }

        if (months === 1) {
            result = "1 month, " + result
        } else if (months > 1) {
            result = "" + months + " months, " + result
        }

        if (years === 1) {
            result = "1 year, " + result
        } else if (years > 1) {
            result = "" + years + " years, " + result
        }

        return result
    };

    const redrawArray = (newArray: number[]): void => {
        svg.selectAll("rect")
            .data(newArray, (d) => d.toString())
            .join((enter) =>
                    enter.append("rect")
                        .attr("fill", "steelblue")
                        .attr("x", (d, i) => x(i.toString())),
                (update) =>
                    update.call(update => update.transition(t)
                        .attr("x", (d, i) => x(i.toString()))
                    )
            )
            .attr("y", (d) => y(d))
            .attr("width", x.bandwidth())
            .attr("height", (d) => y(0) - y(d));
    };

    const redrawCompletions = (newCompletions: any) => {
        if (newCompletions === "1") {
            document.getElementById("num-completions").innerText = "" + newCompletions + " time"
        } else {
            document.getElementById("num-completions").innerText = "" + newCompletions + " times"
        }
    };

    const updateTimeDisplay = (): void => {
        document.getElementById("total-duration").innerText = getDurationString(startDate, new Date());
    };

    const redraw = (): void => {
        if (stateQueue.length > 0) {
            const state = stateQueue.shift();
            redrawArray(state.array);
            redrawCompletions(state.completions);
        }
    };

    updateTimeDisplay();
    setInterval(updateTimeDisplay, 1000);
    redraw();
    setInterval(redraw, 1000);
};

window.onload = main;
