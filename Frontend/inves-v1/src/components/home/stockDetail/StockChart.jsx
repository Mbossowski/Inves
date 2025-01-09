import React, { useRef, useEffect, useState } from "react";
import ApexCharts from "react-apexcharts";


const StockChart = ({ data, setOpenPrice }) => {
 
  const [chartOptions, setChartOptions] = useState({
    chart: {
      type: 'candlestick',
      height: 350
    },
    title: {
      text: ``,
      align: 'left'
    },
    xaxis: {
      type: 'datetime',
    },
    yaxis: {
      title: {
        text: 'Price',
      },
    },
    tooltip: {
      enabled: true,
    },
  });

  const [chartSeries, setChartSeries] = useState([
    {
      data: [],
    },
  ]);

  

  useEffect(() => {
    if (!data || data.length === 0) return;
    setOpenPrice(data[0].lastOpen);
    // Konwertowanie danych na format świeczkowy
    const candlestickData = data.map((item) => ({
      x: new Date(item.date).getTime(), // Czas w formacie timestamp
      y: [item.lastOpen, item.lastHigh, item.lastLow, item.lastClose], // [Open, High, Low, Close]
    }));

    setChartSeries([
      {
        data: candlestickData,
      },
    ]);
  }, [data]);


  return (
    <div>
      
      <ApexCharts
        options={chartOptions}
        series={chartSeries}
        type="candlestick"
        height={350}
      />
    </div>
  );
};

export default StockChart;
