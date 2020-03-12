window.onload = async () => {
    const startDate = new Date(2019, 10, 4, 18, 27);

    let currentData = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

    const margin = {top: 20, right: 20, bottom: 20, left: 20};
    const width = 750 - margin.left - margin.right;
    const height = 500 - margin.top - margin.bottom;

    const x = d3.scaleBand()
        .domain([...currentData.keys()])
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
        .attr("translate", "translate(" + margin.left + ", " + margin.top + ")");

    const t = svg.transition().duration(500);

    const getDurationString = (start, end) => {
        const diff = new Date(end - start);

        let seconds = diff.getSeconds();
        let minutes = diff.getMinutes();
        let hours = diff.getHours();
        let days = diff.getDate();
        let months = diff.getMonth();
        let years = diff.getFullYear() - 1970;

        let result = "";

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

    const redrawArray = async (newArray) => {
        svg.selectAll("rect")
            .data(newArray, (d) => d)
            .join((enter) =>
                    enter.append("rect")
                        .attr("fill", "steelblue")
                        .attr("x", (d, i) => x(i)),
                (update) =>
                    update.call(update => update.transition(t)
                    .attr("x", (d, i) => x(i))
                )
            )
            .attr("y", (d) => y(d))
            .attr("width", x.bandwidth())
            .attr("height", (d) => y(0) - y(d));
    };

    const redrawCompletions = async (newCompletions) => {
        if (newCompletions === "1") {
            document.getElementById("num-completions").innerText = "" + newCompletions + " time"
        } else {
            document.getElementById("num-completions").innerText = "" + newCompletions + " times"
        }
    };

    const updateTimeDisplay = () => {
        document.getElementById("total-duration").innerText = getDurationString(startDate, new Date());
    };

    const stateEventSource = new EventSource('/api/bogosort/state');
    stateEventSource.onmessage = event => {
        const afterSemicolonSplit = event.data.split(';');
        const newArray = afterSemicolonSplit[0].split(',').map(n => parseInt(n));
        const newCompletions = parseInt(afterSemicolonSplit[1]);

        redrawArray(newArray);
        redrawCompletions(newCompletions);
    };

    updateTimeDisplay();
    setInterval(updateTimeDisplay, 1000);
};
