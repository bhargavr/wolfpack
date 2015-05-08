$(function() {

    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: 'Monday',
            Blazers: 13503
        }, {
            period: 'Tuesday',
            Blazers: 23997
        }, {
            period: 'Wednesday',
            Blazers: 15432
        }, {
            period: 'Thrusday',
            Blazers: 23456
        }],
        xkey: 'period',
        ykeys: ['Blazers'],
        labels: ['Blazers'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });

    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Monday",
            value: 3045
        }, {
            label: "Tuesday",
            value: 4508
        }, {
            label: "Wednesday",
            value: 8450
        }],
        resize: true
    });

    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: 'Monday',
            a: 13503
        }, {
            y: 'Tuesday',
            a: 23997
        }, {
            y: 'Wednesday',
            a: 15432
        }, {
            y: 'Thrusday',
            a: 23456
        }],
        xkey: 'y',
        ykeys: ['a'],
        labels: ['Steps'],
        hideHover: 'auto',
        resize: true
    });

});
