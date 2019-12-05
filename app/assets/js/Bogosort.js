window.onload = async function () {
    async function fetchData() {
        const response = await fetch("bogosort/data");
        return response.json()
    }

    const startDate = new Date(2019, 10, 4, 18, 27);

    let currentData = await fetchData(true);

    const margin = {top: 20, right: 20, bottom: 20, left: 20};
    const width = 750 - margin.left - margin.right;
    const height = 500 - margin.top - margin.bottom;

    const x = d3.scaleBand()
        .domain([...currentData.array.keys()])
        .range([0, width])
        .padding(0.1);
    const y = d3.scaleLinear()
        .domain([d3.min(currentData.array), d3.max(currentData.array)])
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
        let result = "";

        if (diff.getSeconds() === 1) {
            result = "1 second"
        } else {
            result = "" + diff.getSeconds() + " seconds"
        }

        if (diff.getMinutes() === 1) {
            result = "1 minute and " + result
        } else if (diff.getMinutes() > 1) {
            result = "" + diff.getMinutes() + " minutes and " + result
        }

        if (diff.getHours() === 1) {
            result = "1 hour, " + result
        } else if (diff.getHours() > 1) {
            result = "" + diff.getHours() + " hours, " + result
        }

        if (diff.getDate() === 1) {
            result = "1 day, " + result
        } else if (diff.getDate() > 1) {
            result = "" + diff.getDate() + " days, " + result
        }

        if (diff.getMonth() === 1) {
            result = "1 month, " + result
        } else if (diff.getMonth() > 1) {
            result = "" + diff.getMonth() + " months, " + result
        }

        if (diff.getFullYear() === 1) {
            result = "1 year, " + result
        } else if (diff.getFullYear() > 1) {
            result = "" + diff.getFullYear() + " years, " + result
        }

        return result
    };

    const redraw = async () => {
        const newData = await fetchData();

        svg.selectAll("rect")
            .data(newData.array, (d) => d)
            .join(
                (enter) =>
                    enter.append("rect")
                        .attr("fill", "steelblue")
                        .attr("x", (d, i) => x(i))
                ,
                (update) => update.call(update => update.transition(t)
                    .attr("x", (d, i) => x(i))
                )
            )
            .attr("y", (d) => y(d))
            .attr("width", x.bandwidth())
            .attr("height", (d) => y(0) - y(d));

        if (newData.numCompletions === 1) {
            document.getElementById("num-completions").innerText = "" + newData.numCompletions + " time"
        } else {
            document.getElementById("num-completions").innerText = "" + newData.numCompletions + " times"
        }

        document.getElementById("total-duration").innerText = getDurationString(startDate, new Date());

        currentData = newData;
    };

    await redraw();
    setInterval(redraw, 1000);
};