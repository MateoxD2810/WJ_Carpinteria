new Morris.Line({
    // ID of the element in which to draw the chart.
    element: 'david',
    // Chart data records -- each entry in this array corresponds to a point on
    // the chart.
    data: [
      { year: '2008', value: 20, value2: 10 },
      { year: '2009', value: 10, value2:  34 },
      { year: '2010', value: 5, value2:  50 },
      { year: '2011', value: 5, value2: 40 },
      { year: '2012', value: 20, value2: 10 }
    ],
    // The name of the data record attribute that contains x-values.
    xkey: 'year',
    // A list of names of data record attributes that contain y-values.
    ykeys: ['value', 'value2'],
    // Labels for the ykeys -- will be displayed when you hover over the
    // chart.
    labels: ['Mesa de centro', 'Armario'],
    resize:true,
    lineColors: ['#2CC025', '#CF2828']
  });

  Morris.Area({
    element: 'frost',
    data: [
      {x: '2010 Q4', y: 3, z: 7},
      {x: '2011 Q1', y: 3, z: 4},
      {x: '2011 Q2', y: null, z: 1},
      {x: '2011 Q3', y: 2, z: 5},
      {x: '2011 Q4', y: 8, z: 2},
      {x: '2012 Q1', y: 4, z: 4}
    ],
    xkey: 'x',
    ykeys: ['y', 'z'],
    labels: ['Y', 'Z'],
    resize:true,
    lineColors: ['#2CC025', '#CF2828']
});

Morris.Bar({
    element: 'car',
    data: [
      {x: '2011 Q1', y: 3, z: 2, a: 3},
      {x: '2011 Q2', y: 2, z: null, a: 1},
      {x: '2011 Q3', y: 0, z: 2, a: 4},
      {x: '2011 Q4', y: 2, z: 4, a: 3}
    ],
    xkey: 'x',
    ykeys: ['y', 'z', 'a'],
    labels: ['Y', 'Z', 'A'],
    resize:true,
    lineColors: ['#2CC025', '#CF2828']
  });

  Morris.Bar({
    element: 'moto',
    data: [
      {x: '2011 Q1', y: 0},
      {x: '2011 Q2', y: 1},
      {x: '2011 Q3', y: 2},
      {x: '2011 Q4', y: 3},
      {x: '2012 Q1', y: 4},
      {x: '2012 Q2', y: 5},
      {x: '2012 Q3', y: 6},
      {x: '2012 Q4', y: 7},
      {x: '2013 Q1', y: 8}
    ],
    xkey: 'x',
    ykeys: ['y'],
    labels: ['Y'],
    resize:true,
    lineColors: ['#2CC025', '#CF2828'],
  });

  var day_data = [
    {"period": "2012-10-01", "licensed": 3407, "sorned": 660},
    {"period": "2012-09-30", "licensed": 3351, "sorned": 629},
    {"period": "2012-09-29", "licensed": 3269, "sorned": 618},
    {"period": "2012-09-20", "licensed": 3246, "sorned": 661},
    {"period": "2012-09-19", "licensed": 3257, "sorned": 667},
    {"period": "2012-09-18", "licensed": 3248, "sorned": 627},
    {"period": "2012-09-17", "licensed": 3171, "sorned": 660},
    {"period": "2012-09-16", "licensed": 3171, "sorned": 676},
    {"period": "2012-09-15", "licensed": 3201, "sorned": 656},
    {"period": "2012-09-10", "licensed": 3215, "sorned": 622}
  ];
  Morris.Bar({
    element: 'avion',
    data: day_data,
    xkey: 'period',
    ykeys: ['licensed', 'sorned'],
    labels: ['Licensed', 'SORN'],
    xLabelAngle: 60,
    resize:true,
    lineColors: ['#2CC025', '#CF2828']
  });

  Morris.Donut({
    element: 'tren',
    data: [
      {value: 70, label: 'foo'},
      {value: 15, label: 'bar'},
      {value: 10, label: 'baz'},
      {value: 5, label: 'A really really long label'}
    ],
    backgroundColor: '#ccc',
    labelColor: '#060',
    colors: [
      '#0BA462',
      '#39B580',
      '#67C69D',
      '#95D7BB'
    ],
    resize:true,
    lineColors: ['#2CC025', '#CF2828'],
  });

  Morris.Bar({
    element: 'dog',
    data: [
      {x: '2011 Q1', y: 3, z: 2, a: 3},
      {x: '2011 Q2', y: 2, z: null, a: 1},
      {x: '2011 Q3', y: 0, z: 2, a: 4},
      {x: '2011 Q4', y: 2, z: 4, a: 3}
    ],
    xkey: 'x',
    ykeys: ['y', 'z', 'a'],
    labels: ['Y', 'Z', 'A'],
    stacked: true,
    resize:true,
    lineColors: ['#2CC025', '#CF2828']
  });