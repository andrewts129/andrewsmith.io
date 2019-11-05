window.onload = async function () {
    async function fetchCurrentArray() {
        const response = await fetch("bogosort/current");
        return response.json()
    }

    const startDate = new Date(2019, 11, 4, 18, 27);

    let array = await fetchCurrentArray();

    const margin = {top: 20, right: 20, bottom: 20, left: 20};
    const width = 750 - margin.left - margin.right;
    const height = 500 - margin.top - margin.bottom;

    const x = d3.scaleBand()
        .domain([...array.keys()])
        .range([0, width])
        .padding(0.1);
    const y = d3.scaleLinear()
        .domain([d3.min(array), d3.max(array)])
        .range([height, 0]);

    const svg = d3.select("body")
        .append("svg")
        .attr("width", width + margin.left + margin.right)
        .attr("height", height + margin.top + margin.bottom)
        .append("g")
        .attr("translate", "translate(" + margin.left + ", " + margin.top + ")");


    const redraw = async () => {
        array = await fetchCurrentArray();
        const t = svg.transition().duration(500);

        svg.selectAll("rect")
            .data(array, (d) => d)
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
    };

    await redraw();
    setInterval(redraw, 1000);
};