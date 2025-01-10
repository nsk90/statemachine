// Sample state machine data (for demonstration)
const stateMachineData = {
  nodes: [
    { id: 'RedState', group: 1 },
    { id: 'YellowState', group: 1 },
    { id: 'GreenState', group: 1 }
  ],
  links: [
    { source: 'RedState', target: 'YellowState', value: 1 },
    { source: 'YellowState', target: 'GreenState', value: 1 }
  ]
};

// Setup the SVG canvas dimensions
const width = 800;
const height = 600;

const svg = d3.select("#viz")
  .append("svg")
  .attr("width", width)
  .attr("height", height);

const simulation = d3.forceSimulation(stateMachineData.nodes)
  .force("link", d3.forceLink(stateMachineData.links).id(d => d.id))
  .force("charge", d3.forceManyBody().strength(-400))
  .force("center", d3.forceCenter(width / 2, height / 2));

const link = svg.append("g")
  .attr("class", "links")
  .selectAll("line")
  .data(stateMachineData.links)
  .enter().append("line")
  .attr("class", "link");

const node = svg.append("g")
  .attr("class", "nodes")
  .selectAll("g")
  .data(stateMachineData.nodes)
  .enter().append("g");

node.append("circle")
  .attr("r", 10)
  .attr("fill", d => '#69b3a2');

node.append("text")
  .attr("dy", -10)
  .text(d => d.id);

simulation
  .nodes(stateMachineData.nodes)
  .on("tick", ticked);

simulation.force("link")
  .links(stateMachineData.links);

function ticked() {
  link
    .attr("x1", d => d.source.x)
    .attr("y1", d => d.source.y)
    .attr("x2", d => d.target.x)
    .attr("y2", d => d.target.y);

  node
    .attr("transform", d => `translate(${d.x},${d.y})`);
}
