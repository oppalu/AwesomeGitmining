$(document).ready(function () {
    var $result = $("#result");
    $.ajax({
        //请求方式为get
        type: "GET",
        //json文件位置
        url: "/js/data.json",
        //返回数据格式为json
        dataType: "json",
        //请求成功完成后要执行的方法
        success: function (data) {
            var width = 800;	//SVG绘制区域的宽度
            var height = 800;	//SVG绘制区域的高度
            var svg = d3.select("#local")			//选择<body>
                .append("svg")			//在<body>中添加<svg>
                .attr("width", width)	//设定<svg>的宽度属性
                .attr("height", height);

            var pack = d3.layout.pack()
                .size([width, height])
                .sort(null)
                .value(function (d) {
                    return d.weight;
                })
                .padding(2);


            var nodes = pack.nodes(data);
            console.log(nodes);

            var color = d3.scale.category20c();

            var bubbles = svg.selectAll(".bubble")
                .data(nodes.filter(function (d) {
                    return !d.children;
                }))
                .enter()
                .append("g")
                .attr("class", "bubble");

            bubbles.append("circle")
                .style("fill", function (d, i) {
                    return color(i);
                })
                .attr("cx", function (d) {
                    return d.x;
                })
                .attr("cy", function (d) {
                    return d.y;
                })
                .attr("r", function (d) {
                    return d.r;
                });
                
            bubbles.append("text")
                .attr("x", function (d) {
                    return d.x;
                })
                .attr("y", function (d) {
                    return d.y;
                })
                .text(function (d) {
                    return d.name;

                });
            
        }
    })
    
});